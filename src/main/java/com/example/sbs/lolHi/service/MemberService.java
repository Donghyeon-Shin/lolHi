package com.example.sbs.lolHi.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.sbs.lolHi.dao.MemberDao;
import com.example.sbs.lolHi.dto.Member;
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

	public boolean CheckLoginIdAndEmail(String loginId, String email) {

		Member member = memberDao.getMemberByLoginId(loginId);
		
		if ( member == null || ! member.getEmail().equals(email)) {
			return false;
		}
		
		int password = (int)(Math.random() * 8999) + 1000;
		
		
		String mailTitle = String.format("[%s] 임시 비밀번호를 발송하였습니다.", siteName);

		//임시비밀번호를 SHA-256 암호화해서 DB에 전달 
		String encryptPassword = SecurityUtil.encryptSHA256(Integer.toString(password));
		
		memberDao.ChangePasswordByloginId(loginId , encryptPassword);
		
		StringBuilder mailBodySb = new StringBuilder();
		mailBodySb.append("<h1>임시비밀번호를 발송하였습니다.</h1>");
		mailBodySb.append(String.format("임시비밀번호 : %d", password));
		mailBodySb.append(String.format("<br><a href=\"%s\" target=\"_blank\">%s</a>로 이동</br>", siteMainUri, siteName));

		mailService.send(email, mailTitle, mailBodySb.toString());
		
		return true;
	}



}
