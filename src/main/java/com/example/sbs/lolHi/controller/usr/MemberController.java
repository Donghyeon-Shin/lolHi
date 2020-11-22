package com.example.sbs.lolHi.controller.usr;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.sbs.lolHi.dto.Member;
import com.example.sbs.lolHi.dto.ResultData;
import com.example.sbs.lolHi.service.MemberService;
import com.example.sbs.lolHi.util.SecurityUtil;
import com.example.sbs.lolHi.util.Util;

@Controller
public class MemberController {

	@Autowired
	private MemberService memberService;

	@RequestMapping("usr/member/join")
	public String showJoin() {
		return "usr/member/join";
	}

	@RequestMapping("usr/member/doJoin")
	public String showDoJoin(@RequestParam Map<String, Object> param, Model model) {

		String loginId = Util.getAsStr(param.get("loginId"), "");
		String name = Util.getAsStr(param.get("name"), "");
		String email = Util.getAsStr(param.get("email"), "");

		String encryptPw = SecurityUtil.encryptSHA256((String) (param.get("loginPw")));

		param.put("loginPw", encryptPw);

		if (loginId.length() == 0) {

			model.addAttribute("msg", "로그인 아이디를 입력해주세요.");
			model.addAttribute("historyBack", true);
			return "common/redirect";

		}

		boolean isJoinAvailableLoginId = memberService.isJoinAvailableLoginId(loginId);
		boolean isJoinAvailableNameAndEmail = memberService.isJoinAvailableName(name, email);

		if (isJoinAvailableLoginId == false) {

			model.addAttribute("msg", String.format(" %s(은)는 이미 사용중인 아아디 입니다.", loginId));
			model.addAttribute("historyBack", true);
			return "common/redirect";

		}

		if (isJoinAvailableNameAndEmail == false) {

			model.addAttribute("msg", String.format(" 이미 존재하는 회원의 정보입니다."));
			model.addAttribute("historyBack", true);
			return "common/redirect";

		}

		memberService.join(param);

		model.addAttribute("msg", String.format(" %s님 가입되었습니다.", name));
		model.addAttribute("replaceUri", "/usr/home/main");
		return "common/redirect";
	}

	@RequestMapping("usr/member/login")
	public String showLogin() {

		return "usr/member/login";
	}

	@RequestMapping("usr/member/doLogin")
	public String showDoLogin(HttpSession session, Model model, @RequestParam Map<String, Object> param) {

		String loginId = Util.getAsStr(param.get("loginId"), "");

		String encryptPw = SecurityUtil.encryptSHA256((String) (param.get("loginPw")));

		if (loginId.length() == 0) {

			model.addAttribute("msg", "로그인 아이디를 입력해주세요.");
			model.addAttribute("historyBack", true);
			return "common/redirect";
		}

		Member member = memberService.doLoginByloginId(loginId);

		if (member == null) {
			model.addAttribute("msg", String.format(" %s(은)는 존재하지 않는 아아디 입니다", loginId));
			model.addAttribute("historyBack", true);
			return "common/redirect";
		} else if (!member.getLoginPw().equals(encryptPw)) {

			model.addAttribute("msg", "비밀번호를 정확히 입력해주세요.");
			model.addAttribute("historyBack", true);
			return "common/redirect";
		}
		
		String authedEmail = memberService.getAuthedEmail(member.getId());

		if (authedEmail.equals(member.getEmail()) == false) {
			model.addAttribute("msg", String.format("이메일 인증 후 시도해주세요."));
			model.addAttribute("historyBack", true);
			return "common/redirect";
		}


		session.setAttribute("loginedMemberId", member.getId());

		model.addAttribute("msg", String.format("%s님 환영합니다.", member.getName()));
		model.addAttribute("replaceUri", "../home/main");
		return "common/redirect";

	}

	@RequestMapping("usr/member/doLogout")
	public String showDoLogout(HttpSession session, Model model) {

		session.removeAttribute("loginedMemberId");

		model.addAttribute("msg", String.format(" 로그아웃 되었습니다."));
		model.addAttribute("replaceUri", "/usr/home/main");
		return "common/redirect";
	}

	@RequestMapping("usr/member/checkPw")
	public String showCheckPw(Model model, String redirectUrl) {

		model.addAttribute("redirectUrl", redirectUrl);

		return "usr/member/checkPw";
	}

	@RequestMapping("usr/member/doCheckPw")
	public String showDoCheckPw(HttpServletRequest req, Model model, String loginPw, String redirectUrl) {

		Member loginedMember = (Member) req.getAttribute("loginedMember");
		String encryptPw = SecurityUtil.encryptSHA256(loginPw);

		if (!loginedMember.getLoginPw().equals(encryptPw)) {
			model.addAttribute("msg", String.format(" 비밀번호가 올바르지 않습니다."));
			model.addAttribute("historyBack", true);
			return "common/redirect";
		}

		String authCode = memberService.genCheckLoginPwAuthCode(loginedMember.getId());

		if (redirectUrl == null || redirectUrl.length() == 0) {
			redirectUrl = "/usr/home/main";
		}

		redirectUrl = Util.getNewUri(redirectUrl, "checkLoginPwAuthCode", authCode);

		model.addAttribute("msg", String.format("확인되었습니다."));
		model.addAttribute("replaceUri", redirectUrl);
		return "common/redirect";
	}

	@RequestMapping("usr/member/modify")
	public String showModify(Model model, HttpServletRequest req, String checkLoginPwAuthCode) {

		if (checkLoginPwAuthCode == null || checkLoginPwAuthCode.length() == 0) {
			model.addAttribute("historyBack", true);
			model.addAttribute("msg", "비밀번호 체크 인증코드가 없습니다.");
			return "common/redirect";
		}

		int loginedMemberId = (int) req.getAttribute("loginedMemberId");

		ResultData checkValidCheckPasswordAuthCodeResultData = memberService
				.checkValidCheckLoginPwAuthCode(loginedMemberId, checkLoginPwAuthCode);

		if (checkValidCheckPasswordAuthCodeResultData.isFail()) {
			model.addAttribute("historyBack", true);
			model.addAttribute("msg", checkValidCheckPasswordAuthCodeResultData.getMsg());
			return "common/redirect";
		}
		model.addAttribute("checkLoginPwAuthCode", checkLoginPwAuthCode);

		return "usr/member/modify";
	}

	@RequestMapping("usr/member/doModify")
	public String showDoModify(HttpServletRequest req, Model model, @RequestParam Map<String, Object> param,
			String checkLoginPwAuthCode) {

		if (checkLoginPwAuthCode == null || checkLoginPwAuthCode.length() == 0) {
			model.addAttribute("historyBack", true);
			model.addAttribute("msg", "비밀번호 체크 인증코드가 없습니다.");
			return "common/redirect";
		}

		int loginedMemberId = (int) req.getAttribute("loginedMemberId");

		ResultData checkValidCheckPasswordAuthCodeResultData = memberService
				.checkValidCheckLoginPwAuthCode(loginedMemberId, checkLoginPwAuthCode);

		if (checkValidCheckPasswordAuthCodeResultData.isFail()) {
			model.addAttribute("historyBack", true);
			model.addAttribute("msg", checkValidCheckPasswordAuthCodeResultData.getMsg());
			return "common/redirect";
		}

		param.put("id", loginedMemberId);

		// 해킹 방지
		param.remove("loginId");
		param.remove("loginPw");

		memberService.doModify(param);

		model.addAttribute("msg", String.format(" 수정되었습니다."));
		model.addAttribute("replaceUri", "/usr/home/main");
		return "common/redirect";
	}

	@RequestMapping("usr/member/findLoginId")
	public String showFindLoginId() {

		return "usr/member/findLoginId";
	}

	@RequestMapping("usr/member/doFindLoginId")
	public String showDoFindLoginId(Model model, @RequestParam Map<String, Object> param) {

		String name = Util.getAsStr(param.get("name"), "");
		String email = Util.getAsStr(param.get("email"), "");

		Member member = memberService.getMemberByNameAndEmail(name, email);

		if (member == null) {
			model.addAttribute("msg", String.format("정보와 일치하는 회원이 존재하지 않습니다."));
			model.addAttribute("historyBack", true);
			return "common/redirect";
		}

		model.addAttribute("msg", String.format(" 로그인 아이디 : %s, 가입날짜 : %s", member.getLoginId(), member.getRegDate()));
		model.addAttribute("replaceUri", "/usr/member/login");
		return "common/redirect";
	}

	@RequestMapping("usr/member/findLoginPw")
	public String showFindLoginPw() {

		return "usr/member/findLoginPw";
	}

	@RequestMapping("usr/member/doFindLoginPw")
	public String showDoFindLoginPw(Model model, @RequestParam Map<String, Object> param) {

		String loginId = Util.getAsStr(param.get("loginId"), "");
		String email = Util.getAsStr(param.get("email"), "");

		Member member = memberService.getMemberByLoginId(loginId);

		if (member == null || !member.getEmail().equals(email)) {
			model.addAttribute("msg", " 로그인 아이디 또는 이메일이 존재하지 않습니다");
			model.addAttribute("historyBack", true);
			return "common/redirect";
		}

		ResultData setTempPasswordAndNotifyRsData = memberService.setTempPasswordAndNotify(member);

		model.addAttribute("msg", String.format(setTempPasswordAndNotifyRsData.getMsg()));
		model.addAttribute("replaceUri", "/usr/member/login");
		return "common/redirect";
	}

	@RequestMapping("usr/member/changeLoginPw")
	public String showChangePw(Model model, HttpServletRequest req, String checkLoginPwAuthCode) {

		if (checkLoginPwAuthCode == null || checkLoginPwAuthCode.length() == 0) {
			model.addAttribute("historyBack", true);
			model.addAttribute("msg", "비밀번호 체크 인증코드가 없습니다.");
			return "common/redirect";
		}

		int loginedMemberId = (int) req.getAttribute("loginedMemberId");

		ResultData checkValidCheckPasswordAuthCodeResultData = memberService
				.checkValidCheckLoginPwAuthCode(loginedMemberId, checkLoginPwAuthCode);

		if (checkValidCheckPasswordAuthCodeResultData.isFail()) {
			model.addAttribute("historyBack", true);
			model.addAttribute("msg", checkValidCheckPasswordAuthCodeResultData.getMsg());
			return "common/redirect";
		}

		model.addAttribute("checkLoginPwAuthCode", checkLoginPwAuthCode);

		return "usr/member/changeLoginPw";
	}

	@RequestMapping("usr/member/doChangeLoginPw")
	public String showDoChangePw(HttpServletRequest req, Model model, @RequestParam Map<String, Object> param,
			String checkLoginPwAuthCode) {

		int loginedMemberId = (int) req.getAttribute("loginedMemberId");

		if (checkLoginPwAuthCode == null || checkLoginPwAuthCode.length() == 0) {
			model.addAttribute("historyBack", true);
			model.addAttribute("msg", "비밀번호 체크 인증코드가 없습니다.");
			return "common/redirect";
		}

		ResultData checkValidCheckPasswordAuthCodeResultData = memberService
				.checkValidCheckLoginPwAuthCode(loginedMemberId, checkLoginPwAuthCode);

		if (checkValidCheckPasswordAuthCodeResultData.isFail()) {
			model.addAttribute("historyBack", true);
			model.addAttribute("msg", checkValidCheckPasswordAuthCodeResultData.getMsg());
			return "common/redirect";
		}

		Member member = memberService.getMemberById(loginedMemberId);

		String encryptPw = SecurityUtil.encryptSHA256((String) param.get("loginPw"));

		if (member.getLoginPw().equals(encryptPw)) {
			model.addAttribute("msg", String.format(" 현재 사용중인 로그인 비밀번호 입니다."));
			model.addAttribute("historyBack", true);
			return "common/redirect";
		}

		param.put("loginPw", encryptPw);
		param.put("id", member.getId());

		memberService.doModify(param);

		model.addAttribute("msg", String.format(" 비밀번호가 수정되었습니다."));
		model.addAttribute("replaceUri", "/usr/home/main");
		return "common/redirect";
	}

	@RequestMapping("usr/member/doDelete")
	public String showDoDelete(HttpServletRequest req, Model model, HttpSession session, String checkLoginPwAuthCode) {

		int loginedMemberId = (int) req.getAttribute("loginedMemberId");

		if (checkLoginPwAuthCode == null || checkLoginPwAuthCode.length() == 0) {
			model.addAttribute("historyBack", true);
			model.addAttribute("msg", "비밀번호 체크 인증코드가 없습니다.");
			return "common/redirect";
		}

		ResultData checkValidCheckPasswordAuthCodeResultData = memberService
				.checkValidCheckLoginPwAuthCode(loginedMemberId, checkLoginPwAuthCode);

		if (checkValidCheckPasswordAuthCodeResultData.isFail()) {
			model.addAttribute("historyBack", true);
			model.addAttribute("msg", checkValidCheckPasswordAuthCodeResultData.getMsg());
			return "common/redirect";
		}

		if (loginedMemberId == 1) {
			model.addAttribute("msg", "어드민은 탈퇴할 수 없습니다.");
			model.addAttribute("historyBack", true);
			return "common/redirect";
		}
		session.removeAttribute("loginedMemberId");

		memberService.doDelete(loginedMemberId);

		model.addAttribute("msg", " 성공적으로 탈퇴하였습니다.");
		model.addAttribute("replaceUri", "/usr/home/main");
		return "common/redirect";
	}
	
	@RequestMapping("/usr/member/doAuthEmail")
	public String showDoAuthEmail(Model model, int actorId, String email, String authCode) {
		Member member = memberService.getMemberById(actorId);

		if (member == null) {
			model.addAttribute("historyBack", true);
			model.addAttribute("msg", "존재하지 않는 회원입니다.");
			return "common/redirect";
		}

		if (member.getEmail().equals(email) == false) {
			model.addAttribute("historyBack", true);
			model.addAttribute("msg", "이메일이 일치하지 않습니다.");
			return "common/redirect";
		}

		String emailAuthCodeOnDb = memberService.getEmailAuthCode(actorId);

		if (authCode.equals(emailAuthCodeOnDb) == false) {
			model.addAttribute("historyBack", true);
			model.addAttribute("msg", "인증코드가 일치하지 않거나 만료되었습니다. 관리자에게 문의해주세요.");
			return "common/redirect";
		}

		memberService.saveAuthedEmail(actorId, email);

		model.addAttribute("msg", "이메일 인증에 성공하였습니다.");
		model.addAttribute("replaceUri", "/usr/home/main");
		return "common/redirect";
	}
	
}
