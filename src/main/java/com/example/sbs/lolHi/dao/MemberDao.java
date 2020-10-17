package com.example.sbs.lolHi.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.sbs.lolHi.dto.Member;

@Mapper
public interface MemberDao {

	Member join(Map<String, Object> param);

}
