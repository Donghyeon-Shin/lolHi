package com.example.sbs.lolHi.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.sbs.lolHi.dao.MemberDao;
import com.example.sbs.lolHi.dto.Member;
import com.example.sbs.lolHi.dto.ResultData;
import com.example.sbs.lolHi.util.SecurityUtil;
import com.example.sbs.lolHi.util.Util;

@Service
public class MemberService {

	@Value("${custom.siteUrl}")
	private String siteUrl;

	@Value("${custom.siteName}")
	private String siteName;
	
	@Value("${custom.siteLoginUri}")
	private String siteLoginUri;

	@Autowired
	private MemberDao memberDao;

	@Autowired
	private MailService mailService;

	@Autowired
	private AttrService attrService;

	public Member doLoginByloginId(String loginId) {
		return memberDao.doLoginByloginId(loginId);
	}

	public void join(Map<String, Object> param) {
		memberDao.join(param);

		int id = Util.getAsInt(param.get("id"));

		String authCode = genEmailAuthCode(id);

		sendJoinCompleteMail(id, (String) param.get("email"), authCode);

	}

	private String genEmailAuthCode(int actorId) {

		String authCode = UUID.randomUUID().toString();

		attrService.setValue("member__" + actorId + "__extra__emailAuthCode", authCode);

		return authCode;
	}

	private void sendJoinCompleteMail(int actorId, String email, String authCode) {
		String mailTitle = String.format("[%s] 가입이 완료되었습니다. 이메일인증을 진행해주세요.", siteName);

		StringBuilder mailBodySb = new StringBuilder();
		mailBodySb.append("<h1>가입이 완료되었습니다.</h1>");
		mailBodySb.append("<div>아래 인증코드를 클릭하여 이메일인증을 마무리 해주세요.</div>");

		String doAuthEmailUrl = siteUrl + "/usr/member/doAuthEmail?authCode=" + authCode + "&email=" + email+ "&actorId=" + actorId;

		mailBodySb.append(String.format("<p><a href=\"%s\" target=\"_blank\">인증하기</a></p>", doAuthEmailUrl));

		mailService.send(email, mailTitle, mailBodySb.toString());
	}

	public boolean isJoinAvailableLoginId(String loginId) {

		Member member = memberDao.getMemberByLoginId(loginId);

		if (member == null) {
			return true;
		}

		return false;
	}

	public Member getMemberById(int id) {

		return memberDao.getMemberById(id);
	}

	public Member getMemberByLoginId(String loginId) {
		
		return memberDao.getMemberByLoginId(loginId);
	}

	public void doModify(Map<String, Object> param) {

		memberDao.doModify(param);

	}

	public boolean isJoinAvailableName(String name, String email) {
		Member member = memberDao.getMemberByNameAndEmail(name, email);

		if (name == null || name.length() == 0) {
			return true;
		}

		if (email == null || email.length() == 0) {
			return true;
		}

		if (member == null) {
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

		// 임시비밀번호를 SHA-256 암호화해서 DB에 전달
		String encryptPassword = SecurityUtil.encryptSHA256(tempLoginPw);

		memberDao.ChangePasswordByloginId(member.getLoginId(), encryptPassword);
		StringBuilder mailBodySb = new StringBuilder();
		mailBodySb.append("<h1>임시비밀번호를 발송하였습니다.</h1>");
		mailBodySb.append(String.format("로그인 아이디 : %s", member.getLoginId()));
		mailBodySb.append(String.format("<br>임시비밀번호 : %s</br>", tempLoginPw));
		mailBodySb.append(String.format("<br><a href=\"%s\" target=\"_blank\">%s</a>로 이동</br>", siteLoginUri, siteName));
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

	public String genCheckLoginPwAuthCode(int actorId) {
		String authCode = UUID.randomUUID().toString();
		attrService.setValue("member__" + actorId + "__extra__modifyPrivateAuthCode", authCode,
				Util.getDateStrLater(60 * 60));

		return authCode;
	}

	public ResultData checkValidCheckLoginPwAuthCode(int actorId, String checkLoginPwAuthCode) {
		if (attrService.getValue("member__" + actorId + "__extra__modifyPrivateAuthCode")
				.equals(checkLoginPwAuthCode)) {
			return new ResultData("S-1", "유효한 키 입니다.");
		}

		return new ResultData("F-1", "유효하지 않은 키 입니다.");
	}

	public void doDelete(int loginedMemberId) {
		memberDao.doDelete(loginedMemberId);
	}

	public String getEmailAuthCode(int actorId) {
		return attrService.getValue("member__" + actorId + "__extra__emailAuthCode");
	}

	public void saveAuthedEmail(int actorId, String email) {
		attrService.setValue("member__" + actorId + "__extra__authedEmail", email);
	}

	public String getAuthedEmail(int actorId) {
		return attrService.getValue("member__" + actorId + "__extra__authedEmail");
	}

}
