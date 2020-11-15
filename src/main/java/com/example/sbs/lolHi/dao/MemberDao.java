package com.example.sbs.lolHi.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.sbs.lolHi.dto.Member;

@Mapper
public interface MemberDao {

	Member doLoginByloginId(@Param("loginId")String loginId);

	void join(Map<String, Object> param);

	Member getMemberByLoginId(@Param("loginId") String loginId);

	Member getMemberById(int id);

	void doModify(Map<String, Object> param);
	
	Member getMemberByNameAndEmail(String name, String email);

	void ChangePasswordByloginId(@Param("loginId")String loginId,@Param("loginPw") String loginPw);

	

}
