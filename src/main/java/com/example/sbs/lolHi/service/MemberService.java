package com.example.sbs.lolHi.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sbs.lolHi.dao.MemberDao;
import com.example.sbs.lolHi.dto.Member;

@Service
public class MemberService {

	@Autowired
	private MemberDao memberDao;
	
	public Member login(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return memberDao.login(param);
	}

	public void join(Map<String, Object> param) {
		memberDao.join(param);
	}

	public boolean isJoinAvailableLoginId(String loginId) {

		Member member = memberDao.getMemberByLoginId(loginId);

		if ( member == null ) {
			return true;
		}

		return false;
	}

}