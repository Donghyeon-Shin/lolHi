package com.example.sbs.lolHi.controller.usr;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	@ResponseBody
	public String showDoJoin(@RequestParam Map<String, Object> param) {
						
		String loginId = Util.getAsStr(param.get("loginId"), "");
		
		if ( loginId.length() == 0 ) {
			return String.format("<script> alert('로그인 아이디를 입력해주세요.'); history.back(); </script>");
		}
				
		boolean isJoinAvailableLoginId  = memberService.isJoinAvailableLoginId(loginId);
		
		if ( isJoinAvailableLoginId == false ) {
			
			return String.format("<script> alert('%s(은)는 이미 사용중인 아이디 입니다.'); history.back(); </script>", loginId);
			
		}
		
		memberService.join(param);
		
		int id = Util.getAsInt(param.get("id"));
		
		return String.format("<script>alert('%d번 회원이 생성되었습니다.'); location.replace('../article/list'); </script>", id);
	}


	@RequestMapping("usr/member/login")
	public String showLogin() {
				
		return "usr/member/login";
	}
	
	@RequestMapping("usr/member/doLogin")
	@ResponseBody
	public String showDoLogin(@RequestParam Map<String, Object> param, HttpSession session) {
		
		String loginId = Util.getAsStr(param.get("loginId"), "");
		
		Member member = memberService.login(param);
		
		String loginPw = (String)(param.get("loginPw"));
		
		if ( member == null ) {
			return "<script>alert('존재하지 않는 회원 입니다'); history.back();</script>";
		} else if ( member.getLoginPw().equals(loginPw) == false) {
			return "<script>alert('존재하지 않는 로그인 비번 입니다'); history.back();</script>";
		} else if ( member.getLoginId().equals(loginId) == false ) {
			return "<script>alert('존재하지 않는 로그인 아이디 입니다'); history.back();</script>";
		}
		
		session.setAttribute("loginedMemberId", member.getId());
		session.setAttribute("loginedMemberName", member.getName());
		
		return String.format("<script>alert('%s님 환영합니다'); location.replace('../article/list')</script>", member.getName());
	}
	
	@RequestMapping("usr/member/doLogout")
	@ResponseBody
	public String showDoLogout(HttpSession session) {
				
		session.removeAttribute("loginedMemberId");
		session.removeAttribute("loginedMemberName");
		
		return String.format("<script>alert('로그아웃 되었습니다.'); location.replace('../article/list') </script>");
	}

	
}
