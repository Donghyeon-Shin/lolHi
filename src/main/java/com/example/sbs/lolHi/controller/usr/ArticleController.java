package com.example.sbs.lolHi.controller.usr;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.sbs.lolHi.dto.Article;
import com.example.sbs.lolHi.dto.Board;
import com.example.sbs.lolHi.dto.Member;
import com.example.sbs.lolHi.dto.Reply;
import com.example.sbs.lolHi.service.ArticleService;
import com.example.sbs.lolHi.service.ReplyService;
import com.example.sbs.lolHi.util.Util;

@Controller
public class ArticleController {
	
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ReplyService replyService;

	@RequestMapping("usr/article/list")
	public String showList( HttpServletRequest req, Model model, @RequestParam Map<String, Object> param, String searchKeyword, String searchType, String boardCode ) {
		
		Member loginedMember = (Member)req.getAttribute("loginedMember");
		
		if ( boardCode == null ) {
			boardCode = "free";
		}
		
		param.put("boardCode", boardCode);
		
		Board board = articleService.getBoard(boardCode);
		
		List<Article> articles = articleService.getForPrintArticlesById(loginedMember, param);

		int totalCount = (int)articleService.getTotalCount(param);
		int itemsCountInAPage = 10;
		
		int totalPage = (int)Math.ceil((double)totalCount / itemsCountInAPage);
		
		int page = Util.getAsInt(param.get("page"), 1);
		
		int pageMenuSize = 5;
		
		int pageMenuStart = page - pageMenuSize ;
		
		if ( pageMenuStart < 0 ) {
			pageMenuStart = 1;
		}
		
		int pageMenuEnd = page + pageMenuSize;
		
		if ( pageMenuEnd > totalPage ) {
			pageMenuEnd = totalPage;
		}
		
		model.addAttribute("articles", articles);
		model.addAttribute("board",board);
		model.addAllAttributes(param);
		
		param.put("itemsCountInAPage", itemsCountInAPage);
		
		model.addAttribute("pageMenuSize", pageMenuSize);
		model.addAttribute("pageMenuStart", pageMenuStart);
		model.addAttribute("pageMenuEnd", pageMenuEnd);
		model.addAttribute("page", page);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("totalPage", totalPage);
		
		if ( searchKeyword != null ) {
			searchKeyword = searchKeyword.trim();
		}
		
		if ( searchType != null ) {
			searchType = searchType.trim();
		}
		
		param.put("searchKeyword", searchKeyword);
		
		param.put("searchType", searchType);
		
		return "usr/article/list";
	}
	
	@RequestMapping("usr/article/detail")
	public String showDetail( HttpServletRequest req, Model model, int id, String listUrl) {
		
		Member loginedMember = (Member)req.getAttribute("loginedMember");
		
		Article article = articleService.getForPrintArticleById(loginedMember, id);
		
		List<Reply> replies = replyService.getForPrintRepliesById("article", id);
		
		boolean replyExists = false;
		
		if ( replies.size() != 0 ) {
			replyExists = true;
		}
		
		if ( listUrl == null ) {
			listUrl = "list";
		}
			
		model.addAttribute("article", article);
		model.addAttribute("articleReplies", replies);
		model.addAttribute("replyExists", replyExists);
		model.addAttribute("listUrl", listUrl);
		
		return "usr/article/detail";
	}

	@RequestMapping("usr/article/doDelete")
	public String showDoDelete( HttpServletRequest req, @RequestParam("id") int id, Model model) {
		
		Member loginedMember = (Member)req.getAttribute("loginedMember");
		
		Article article = articleService.getForPrintArticleById(loginedMember, id);
		
		if ( (boolean)article.getExtra().get("actorCanModify") == false ) {
			
			model.addAttribute("msg", "권한이 없습니다.");
			model.addAttribute("historyBack", true);
			return "common/redirect";
		}

		articleService.doDeleteArticleById(id);
		
		model.addAttribute("msg", String.format("%d번 글이 삭제되었습니다.", id));
		model.addAttribute("replaceUri", "/usr/article/list");
		return "common/redirect";
	}
	
	@RequestMapping("usr/article/write")
	public String showWrite(HttpServletRequest req, Model model, String boardCode) {
		
		if ( boardCode == null ) {
			
			model.addAttribute("msg", "올바른 경로가 아닙니다.");
			model.addAttribute("historyBack", true);
			return "common/redirect";
			
		}
		
		Board board = articleService.getBoard(boardCode);
		
		model.addAttribute("board", board);
		
		return "usr/article/write";
	}
	
	@RequestMapping("usr/article/doWrite")
	public String showDoWrite(HttpServletRequest req,  Model model, @RequestParam Map<String, Object> param) {

		int loginedMemberId = (int)req.getAttribute("loginedMemberId");;
	
		param.put("loginedMemberId", loginedMemberId);
		
		Board board = articleService.getBoard((String)param.get("boardCode"));
		
		param.put("boardId", board.getId());
		
		System.out.println("boardId : " + param.get("boardId"));
		
		articleService.doWrite(param);
		
		int id = Util.getAsInt(param.get("id"));
		
		
		model.addAttribute("msg", String.format("%d번 글이 생성되었습니다.", id));
		model.addAttribute("replaceUri", String.format("/usr/article/list?boardCode=%s", board.getCode()));
		return "common/redirect";
	}
	
	@RequestMapping("usr/article/modify")
	public String showModify(HttpServletRequest req, Model model, @RequestParam("id") int id) {
		
		Member loginedMember = (Member)req.getAttribute("loginedMember");
		
		Article article = articleService.getForPrintArticleById(loginedMember, id);
		
		if ( (boolean)article.getExtra().get("actorCanModify") == false ) {
			
			model.addAttribute("msg", "권한이 없습니다.");
			model.addAttribute("historyBack", true);
			return "common/redirect";
		}
		
		model.addAttribute("article", article);
		
		return "usr/article/modify";
	}
	
	@RequestMapping("usr/article/doModify")
	public String showDoModify(HttpServletRequest req,  Model model, @RequestParam Map<String, Object> param ) {
		
		Member loginedMember = (Member)req.getAttribute("loginedMember");
		
		int id = Util.getAsInt(param.get("id"));
		
		Article article = articleService.getForPrintArticleById(loginedMember, id);
		
		if ( (boolean)article.getExtra().get("actorCanModify") == false ) {
			
			model.addAttribute("msg", "권한이 없습니다.");
			model.addAttribute("historyBack", true);
			return "common/redirect";
		}
		
		articleService.doModify(param);
		
		model.addAttribute("msg", String.format("%d번 글이 생성되었습니다.", id));
		model.addAttribute("replaceUri", String.format("/usr/article/detail?id=%d", id));
		return "common/redirect";
	}
	
}
