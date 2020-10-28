package com.example.sbs.lolHi.controller.usr;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.sbs.lolHi.dto.Article;
import com.example.sbs.lolHi.service.ArticleService;
import com.example.sbs.lolHi.util.Util;

@Controller
public class ArticleController {
	
	@Autowired
	private ArticleService articleService;

	@RequestMapping("usr/article/list")
	public String showList( HttpSession session, Model model, @RequestParam Map<String, Object> param, String searchKeyword, String searchType ) {
		
		int loginedMemberId = 0;
		
		if ( session.getAttribute("loginedMemberId") != null ) {
			loginedMemberId = (int)session.getAttribute("loginedMemberId");
			model.addAttribute("loginedMemberId", loginedMemberId);
		} 


		List<Article> articles = articleService.getArticles(param);

		int totalCount = (int)articleService.getArticlesCount();
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
	public String showDetail( HttpSession session, Model model, @RequestParam("id") int id) {

		int loginedMemberId = 0;
		
		if ( session.getAttribute("loginedMemberId") != null ) {
			loginedMemberId = (int)session.getAttribute("loginedMemberId");
			model.addAttribute("loginedMemberId", loginedMemberId);
		} 
		
		Article article = articleService.getArticle(id);
		
		model.addAttribute("article", article);
		
		return "usr/article/detail";
	}

	@RequestMapping("usr/article/doDelete")
	public String showDoDelete( HttpServletRequest req, @RequestParam("id") int id, Model model) {
		
		int loginedMemberId = (int) req.getAttribute("isLogined");
		
		Article article = articleService.getArticle(id);
		
		if ( article.getMemberId() != loginedMemberId ) {
			
			model.addAttribute("msg", "권한이 없습니다.");
			model.addAttribute("historyBack", true);
			return "common/redirect";
		}

		articleService.DoDeleteArticle(id);
		
		model.addAttribute("msg", String.format("%d번 글이 삭제되었습니다.", id));
		model.addAttribute("replaceUri", "/usr/article/list");
		return "common/redirect";
	}
	
	@RequestMapping("usr/article/write")
	public String showWrite(HttpServletRequest req, Model model) {
		
		return "usr/article/write";
	}
	
	@RequestMapping("usr/article/doWrite")
	public String showDoWrite(HttpServletRequest req,  Model model, @RequestParam Map<String, Object> param) {

		int loginedMemberId = (int)req.getAttribute("loginedMemberId");;
	
		param.put("loginedMemberId", loginedMemberId);
		
		articleService.write(param);
		
		int id = Util.getAsInt(param.get("id"));
		
		
		model.addAttribute("msg", String.format("%d번 글이 생성되었습니다.", id));
		model.addAttribute("replaceUri", String.format("/usr/article/detail?id=%d", id));
		return "common/redirect";
	}
	
	@RequestMapping("usr/article/modify")
	public String showModify(HttpServletRequest req, Model model, @RequestParam("id") int id) {
		
		int loginedMemberId =  (int)req.getAttribute("loginedMemberId");;
		
		Article article = articleService.getArticle(id);
		
		
		if ( article.getMemberId() != loginedMemberId ) {
			
			model.addAttribute("msg", "권한이 없습니다.");
			model.addAttribute("historyBack", true);
			return "common/redirect";
		}
		
		model.addAttribute("article", article);
		
		return "usr/article/modify";
	}
	
	@RequestMapping("usr/article/doModify")
	public String showDoModify(HttpServletRequest req,  Model model, @RequestParam Map<String, Object> param ) {
		
		int loginedMemberId = (int)req.getAttribute("loginedMemberId");;
		
		int id = Util.getAsInt(param.get("id"));
		
		Article article = articleService.getArticle(id);
		
		if ( article.getMemberId() != loginedMemberId ) {
			
			model.addAttribute("msg", "권한이 없습니다.");
			model.addAttribute("historyBack", true);
			return "common/redirect";
		}
		
		articleService.modify(param);
		
		model.addAttribute("msg", String.format("%d번 글이 생성되었습니다.", id));
		model.addAttribute("replaceUri", String.format("/usr/article/detail?id=%d", id));
		return "common/redirect";
	}
}
