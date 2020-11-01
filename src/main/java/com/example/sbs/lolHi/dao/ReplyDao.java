package com.example.sbs.lolHi.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.sbs.lolHi.dto.Reply;

@Mapper
public interface ReplyDao {

	void doWrite(Map<String, Object> param);
	
	List<Reply> getForPrintRepliesById( @Param("relTypeCode") String relTypeCode, @Param("relId") int relId);

	Reply getReplyById( @Param("id") int id);

	void doDelete( @Param("id") int id);

	void doModify( Map<String, Object> param);
	
}
