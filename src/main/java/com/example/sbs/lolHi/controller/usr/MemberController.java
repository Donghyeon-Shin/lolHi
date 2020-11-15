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
		
		String encryptPw = SecurityUtil.encryptSHA256((String)(param.get("loginPw")));
		
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
	public String showDoLogin(HttpSession session, Model model, @RequestParam Map<String, Object> param ) {

		String loginId = Util.getAsStr(param.get("loginId"), "");

		String encryptPw = SecurityUtil.encryptSHA256((String)(param.get("loginPw")));
		
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
		} else if (member.getLoginPw().equals(encryptPw) == false) {

			model.addAttribute("msg", "비밀번호를 정확히 입력해주세요.");
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

		model.addAttribute("msg", String.format(" 로그아웃 되었습니다." ));
		model.addAttribute("replaceUri", "/usr/home/main");
		return "common/redirect";
	}
	
	@RequestMapping("usr/member/confirmPw")
	public String showConfirmPw(Model model, String url) {
		
		model.addAttribute("url", url);
		
		return "usr/member/confirmPw";
	}
	
	@RequestMapping("usr/member/doConfirmPw")
	public String showDoConfirmPw(HttpServletRequest req, Model model, String loginPw, String url) {
		
		int loginedMemberId = (int)req.getAttribute("loginedMemberId");
		
		Member member = memberService.getMemberById(loginedMemberId);
		String encryptPw = SecurityUtil.encryptSHA256(loginPw);
		
		if ( !member.getLoginPw().equals(encryptPw)) {
			model.addAttribute("msg", String.format(" 비밀번호가 올바르지 않습니다." ));
			model.addAttribute("historyBack", true);
			return "common/redirect";
		}
		
		model.addAttribute("msg", String.format("확인 되었습니다."));
		model.addAttribute("replaceUri", url);
		return "common/redirect";
	}
	
	
	@RequestMapping("usr/member/modify")
	public String showModify() {
	
		return "usr/member/modify";
	}
	
	@RequestMapping("usr/member/doModify")
	public String showDoModify(HttpServletRequest req, Model model, @RequestParam Map<String, Object> param) {
		
		int loginedMemberId = (int)req.getAttribute("loginedMemberId");
		
		param.put("id", loginedMemberId);
		
		// 해킹 방지
		param.remove("loginId");
		param.remove("loginPw");
	
		memberService.doModify(param);
		
		
		model.addAttribute("msg", String.format(" 수정되었습니다." ));
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
		
		if ( member == null ) {
			model.addAttribute("msg", String.format("정보와 일치하는 회원이 존재하지 않습니다." ));
			model.addAttribute("historyBack", true);
			return "common/redirect";
		}
		
		model.addAttribute("msg", String.format(" 로그인 아이디 : %s, 가입날짜 : %s",member.getLoginId(), member.getRegDate()));
		model.addAttribute("replaceUri", "/usr/member/login");
		return "common/redirect";
	}
	
	@RequestMapping("usr/member/findLoginPw")
	public String showFindLoginPw() {
		
		return "usr/member/findLoginPw";
	}
	
	@RequestMapping("usr/member/doFindLoginPw")
	public String showDoFindLoginPw(Model model,@RequestParam Map<String, Object> param) {
		
		String loginId = Util.getAsStr(param.get("loginId"), "");
		String email = Util.getAsStr(param.get("email"), "");
		
		Member member = memberService.getMemberByLoginId(loginId);

		if ( member == null || ! member.getEmail().equals(email)) {
			model.addAttribute("msg", " 로그인 아이디 또는 이메일이 존재하지 않습니다");
			model.addAttribute("historyBack", true);
			return "common/redirect";
		}
		
		ResultData setTempPasswordAndNotifyRsData = memberService.setTempPasswordAndNotify(member);
		
		
		model.addAttribute("msg", String.format(setTempPasswordAndNotifyRsData.getMsg()));
		model.addAttribute("replaceUri", "/usr/member/login");
		return "common/redirect";
	}
}
