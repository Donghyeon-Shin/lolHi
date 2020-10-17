package com.example.sbs.lolHi.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sbs.lolHi.dao.MemberDao;
import com.example.sbs.lolHi.dto.Member;

@Service
public class MemeberService {

	@Autowired
	private MemberDao memberDao;
	
	public Member login(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return memberDao.login(param);
	}


}
