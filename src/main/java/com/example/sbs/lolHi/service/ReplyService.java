package com.example.sbs.lolHi.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sbs.lolHi.dao.ReplyDao;
import com.example.sbs.lolHi.dto.Member;
import com.example.sbs.lolHi.dto.Reply;

@Service
public class ReplyService {
	
	@Autowired
	private ReplyDao replyDao;

	public void doWrite(Map<String, Object> param) {
		
		replyDao.doWrite(param);
	}

	public List<Reply> getForPrintRepliesById(Member actorMember, String relTypeCode, int relId) {
		
		List<Reply> replies = replyDao.getForPrintRepliesById(relTypeCode, relId);
		

		for (Reply reply : replies) {
			if (reply.getExtra() == null) {
				reply.setExtra(new HashMap<>());
			}

			boolean actorCanDelete = false;

			if (actorMember != null) {
				actorCanDelete = actorMember.getId() == reply.getMemberId();
			}

			boolean actorCanModify = actorCanDelete;

			reply.getExtra().put("actorCanDelete", actorCanDelete);
			reply.getExtra().put("actorCanModify", actorCanModify);
			
			System.out.println(reply.getExtra().get("actorCanDelete"));
		}
		
		return replies;
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
	
	public Reply getForPrintReply(Member actorMember, int id) {
		Reply reply = getReplyById(id);

		if (reply == null) {
			return null;
		}

		if (reply.getExtra() == null) {
			reply.setExtra(new HashMap<>());
		}

		boolean actorCanDelete = false;

		if (actorMember != null) {
			actorCanDelete = actorMember.getId() == reply.getMemberId();
		}

		boolean actorCanModify = actorCanDelete;
		
		reply.getExtra().put("actorCanDelete", actorCanDelete);
		reply.getExtra().put("actorCanModify", actorCanModify);

		return reply;
	}

}
