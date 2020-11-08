package com.example.sbs.lolHi.controller.usr;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.sbs.lolHi.dto.Member;
import com.example.sbs.lolHi.dto.Reply;
import com.example.sbs.lolHi.service.ReplyService;
import com.example.sbs.lolHi.util.Util;

@Controller
public class ReplyController {
	
	@Autowired
	private ReplyService replyService;
	
	@RequestMapping("usr/reply/doWrite")
	public String showDoWrite(HttpServletRequest req,  Model model, @RequestParam Map<String, Object> param, String redirectUrl) {

		int loginedMemberId = (int)req.getAttribute("loginedMemberId");
	
		param.put("memberId", loginedMemberId);
		
		replyService.doWrite(param);
		
		int id = Util.getAsInt(param.get("id"));
		
		String relTypeCode = (String)param.get("relTypeCode");
		
		int relId = Util.getAsInt(param.get("relId"));
		
		if ( redirectUrl == null || redirectUrl.length() == 0) {
			redirectUrl = String.format("/usr/%s/detail?id=%d", relTypeCode, relId);
		}
		
		
		
		model.addAttribute("msg", String.format("%d번 댓글이 생성되었습니다.", id));
		model.addAttribute("replaceUri", redirectUrl);
		return "common/redirect";
	}
	
	@RequestMapping("usr/reply/doDelete")
	public String showDoDelete(HttpServletRequest req,  Model model, int id, String redirectUrl) {
		
		Member loginedMember = (Member) req.getAttribute("loginedMember");
			
		Reply reply = replyService.getForPrintReply(loginedMember, id);
		
		if ( reply == null ) {
			
			model.addAttribute("msg", "존재하지 않는 댓글입니다.");
			model.addAttribute("historyBack", true);
			return "common/redirect";
			
		}
		
		if ((boolean) reply.getExtra().get("actorCanDelete") == false) {

			model.addAttribute("msg", "권한이 없습니다.");
			model.addAttribute("historyBack", true);
			return "common/redirect";
		}
		
		String relTypeCode = reply.getRelTypeCode();
		int relId = reply.getRelId();
		
		if ( redirectUrl == null || redirectUrl.length() == 0) {
			redirectUrl = String.format("/usr/%s/detail?id=%d", relTypeCode, relId);
		}
		
		
		replyService.doDelete(id);

		
		model.addAttribute("msg", String.format("%d번 댓글이 삭제되었습니다.", id));
		model.addAttribute("replaceUri", redirectUrl);
		return "common/redirect";
	}
	
	@RequestMapping("usr/reply/modify")
	public String showModify(HttpServletRequest req,  Model model, int id, String redirectUrl) { 
		
		Member loginedMember = (Member) req.getAttribute("loginedMember");
		
		Reply reply = replyService.getForPrintReply(loginedMember, id);
		
		if ( reply == null ) {
			
			model.addAttribute("msg", "존재하지 않는 댓글입니다.");
			model.addAttribute("historyBack", true);
			return "common/redirect";
			
		}
		
		if ((boolean) reply.getExtra().get("actorCanModify") == false) {

			model.addAttribute("msg", "권한이 없습니다.");
			model.addAttribute("historyBack", true);
			return "common/redirect";
		}
		
		model.addAttribute("reply", reply);
		model.addAttribute("redirectUrl", redirectUrl);
		
		return "usr/reply/modify";
	}
	
	@RequestMapping("usr/reply/doModify")
	public String showModify(HttpServletRequest req,  Model model, @RequestParam Map<String, Object> param, String redirectUrl ) { 
		
		Member loginedMember = (Member) req.getAttribute("loginedMember");
		
		int id = Util.getAsInt( param.get("id"));
		
		Reply reply = replyService.getForPrintReply(loginedMember, id);
		
		if ( reply == null ) {
			
			model.addAttribute("msg", "존재하지 않는 댓글입니다.");
			model.addAttribute("historyBack", true);
			return "common/redirect";
			
		}
		
		if ((boolean) reply.getExtra().get("actorCanModify") == false) {

			model.addAttribute("msg", "권한이 없습니다.");
			model.addAttribute("historyBack", true);
			return "common/redirect";
		}
		
		String relTypeCode = (String) reply.getRelTypeCode();
		int relId = (int)reply.getRelId();
		
		System.out.println(param.get("body"));
		
		replyService.doModify(param);
		
		if ( redirectUrl == null || redirectUrl.length() == 0 ) {
			redirectUrl = String.format("/usr/%s/detail?id=%d", relTypeCode, relId);
		}
		
		model.addAttribute("msg", String.format("%d번 댓글이 수정되었습니다.", id));
		model.addAttribute("replaceUri", redirectUrl);
		return "common/redirect";
	}
}
