package com.example.sbs.lolHi.controller.usr;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.sbs.lolHi.dto.Member;
import com.example.sbs.lolHi.service.MemberService;
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

		if (loginId.length() == 0) {

			model.addAttribute("msg", "로그인 아이디를 입력해주세요.");
			model.addAttribute("historyBack", true);
			return "common/redirect";
		}

		boolean isJoinAvailableLoginId = memberService.isJoinAvailableLoginId(loginId);

		if (isJoinAvailableLoginId == false) {

			model.addAttribute("msg", String.format(" %s(은)는 이미 사용중인 아아디 입니다", loginId));
			model.addAttribute("historyBack", true);
			return "common/redirect";

		}

		memberService.join(param);
		
		model.addAttribute("msg", String.format(" %s님 가입되었습니다.", loginId));
		model.addAttribute("replaceUri", "/usr/article/list");
		return "common/redirect";
	}

	@RequestMapping("usr/member/login")
	public String showLogin() {

		return "usr/member/login";
	}

	@RequestMapping("usr/member/doLogin")
	public String showDoLogin(@RequestParam Map<String, Object> param, HttpSession session, Model model) {

		String loginId = Util.getAsStr(param.get("loginId"), "");

		String loginPw = (String) (param.get("loginPw"));

		if (loginId.length() == 0) {

			model.addAttribute("msg", "로그인 아이디를 입력해주세요.");
			model.addAttribute("historyBack", true);
			return "common/redirect";
		}

		Member member = memberService.login(param);

		if (member == null) {
			model.addAttribute("msg", String.format(" %s(은)는 존재하지 않는 아아디 입니다", loginId));
			model.addAttribute("historyBack", true);
			return "common/redirect";
		} else if (member.getLoginPw().equals(loginPw) == false) {

			model.addAttribute("msg", "비밀번호를 정확히 입력해주세요.");
			model.addAttribute("historyBack", true);
			return "common/redirect";
		}

		session.setAttribute("loginedMemberId", member.getId());
		session.setAttribute("loginedMemberName", member.getName());

		model.addAttribute("msg", String.format("%s님 환영합니다.", member.getName()));
		model.addAttribute("replaceUri", "../article/list");
		return "common/redirect";

	}

	@RequestMapping("usr/member/doLogout")
	public String showDoLogout(HttpSession session, Model model) {

		session.removeAttribute("loginedMemberId");
		session.removeAttribute("loginedMemberName");

		model.addAttribute("replaceUri", "/usr/article/list");
		return "common/redirect";
	}

}
