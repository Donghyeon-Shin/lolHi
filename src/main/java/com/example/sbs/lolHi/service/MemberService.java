package com.example.sbs.lolHi.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.sbs.lolHi.dao.MemberDao;
import com.example.sbs.lolHi.dto.Member;
import com.example.sbs.lolHi.dto.ResultData;
import com.example.sbs.lolHi.util.SecurityUtil;

@Service
public class MemberService {

	@Value("${custom.siteMainUri}")
	private String siteMainUri;
	
	@Value("${custom.siteName}")
	private String siteName;
	
	@Autowired
	private MemberDao memberDao;

	@Autowired
	private MailService mailService;
	
	public Member doLoginByloginId(String loginId) {
		// TODO Auto-generated method stub
		return memberDao.doLoginByloginId(loginId);
	}


	public void join(Map<String, Object> param) {
		memberDao.join(param);
		
		sendJoinCompleteMail((String)param.get("email"));
	}
	
	private void sendJoinCompleteMail(String email) {
		String mailTitle = String.format("[%s] 가입이 완료되었습니다.", siteName);

		StringBuilder mailBodySb = new StringBuilder();
		mailBodySb.append("<h1>가입이 완료되었습니다.</h1>");
		mailBodySb.append(String.format("<p><a href=\"%s\" target=\"_blank\">%s</a>로 이동</p>", siteMainUri, siteName));

		mailService.send(email, mailTitle, mailBodySb.toString());
	}

	public boolean isJoinAvailableLoginId(String loginId) {

		Member member = memberDao.getMemberByLoginId(loginId);

		if ( member == null ) {
			return true;
		}

		return false;
	}

	public Member getMemberById(int id) {

		return memberDao.getMemberById(id);
	}
	
	public Member getMemberByLoginId(String loginId) {
		// TODO Auto-generated method stub
		return memberDao.getMemberByLoginId(loginId);
	}

	public void doModify(Map<String, Object> param) {
		
		memberDao.doModify(param);
		
	}

	public boolean isJoinAvailableName(String name, String email) {
		Member member = memberDao.getMemberByNameAndEmail(name, email);

		if ( name == null || name.length() == 0 ) {
			return true;
		}
		
		if ( email == null || email.length() == 0 ) {
			return true;
		}
		
		if ( member == null ) {
			return true;
		}

		return false;
	}

	public Member getMemberByNameAndEmail(String name, String email) {
		
		return memberDao.getMemberByNameAndEmail(name, email);
		
	}

	public ResultData setTempPasswordAndNotify(Member member) {
		
		Random r = new Random();
		String tempLoginPw = (10000 + r.nextInt(90000)) + "";
		
		String mailTitle = String.format("[%s] 임시 비밀번호를 발송하였습니다.", siteName);
		
		//임시비밀번호를 SHA-256 암호화해서 DB에 전달 
		String encryptPassword = SecurityUtil.encryptSHA256(tempLoginPw);
		
		memberDao.ChangePasswordByloginId(member.getLoginId() , encryptPassword);
		StringBuilder mailBodySb = new StringBuilder();
		mailBodySb.append("<h1>임시비밀번호를 발송하였습니다.</h1>");
		mailBodySb.append(String.format("로그인 아이디 : %s", member.getLoginId()));
		mailBodySb.append(String.format("<br>임시비밀번호 : %s</br>", tempLoginPw));
		mailBodySb.append(String.format("<br><a href=\"%s\" target=\"_blank\">%s</a>로 이동</br>", siteMainUri, siteName));
		mailService.send(member.getEmail(), mailTitle, mailBodySb.toString());
	
		ResultData sendResultData = mailService.send(member.getEmail(), mailTitle, mailBodySb.toString());

		if (sendResultData.isFail()) {
			return new ResultData("F-1", "메일발송에 실패했습니다.");
		}
		
		Map<String, Object> modifyParam = new HashMap<>();
		modifyParam.put("loginPw", encryptPassword);
		modifyParam.put("id", member.getId());
		memberDao.doModify(modifyParam);
		
		return new ResultData("S-1", "임시 패스워드를 메일로 발송하였습니다.");
	}




}
