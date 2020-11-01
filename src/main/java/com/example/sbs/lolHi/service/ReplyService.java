package com.example.sbs.lolHi.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sbs.lolHi.dao.ReplyDao;
import com.example.sbs.lolHi.dto.Reply;

@Service
public class ReplyService {
	
	@Autowired
	private ReplyDao replyDao;

	public void doWrite(Map<String, Object> param) {
		
		replyDao.doWrite(param);
	}

	public List<Reply> getForPrintRepliesById(String relTypeCode, int relId) {		
		return replyDao.getForPrintRepliesById(relTypeCode, relId);
	}

	public Reply getReplyById(int id) {
		return replyDao.getReplyById(id);
	}

	public void doDelete(int id) {
		replyDao.doDelete(id);
	}

	public void doModify(Map<String, Object> param) {
		replyDao.doModify(param);
	}

}
