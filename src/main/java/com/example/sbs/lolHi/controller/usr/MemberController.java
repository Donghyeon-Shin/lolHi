package com.example.sbs.lolHi.controller.usr;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.sbs.lolHi.dto.Article;
import com.example.sbs.lolHi.dto.Member;
import com.example.sbs.lolHi.service.ArticleService;
import com.example.sbs.lolHi.service.MemeberService;
import com.example.sbs.lolHi.util.Util;

@Controller
public class MemberController {
	
	@Autowired
	private MemeberService memeberService;

	@RequestMapping("usr/member/join")
	public String showJoin() {
				
		return "usr/member/join";
	}
	
	@RequestMapping("usr/member/doJoin")
	@ResponseBody
	public String showDoJoin(@RequestParam Map<String, Object> param) {
				
		Member member = memeberService.join(param);
		
		if ( member == null ) {
			return "<script>alert('존재하지 않는 회원입니다'); location.replace('join')</script>";
		}
		
		return String.format("<script>alert('%s님 환영합니다'); location.replace('../article/list')</script>", param.get("name"));
	}

}
