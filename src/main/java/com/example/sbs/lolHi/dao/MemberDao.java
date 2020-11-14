package com.example.sbs.lolHi.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.sbs.lolHi.dto.Member;

@Mapper
public interface MemberDao {

	Member login(Map<String, Object> param);

	void join(Map<String, Object> param);

	Member getMemberByLoginId(@Param("loginId") String loginId);

	Member getMemberById(int id);

	void doModify(Map<String, Object> param);

	Member getMemberByName(String name);

	Member getMemberByEmail(String loginEmail);

}
