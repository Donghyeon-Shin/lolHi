package com.example.sbs.lolHi.controller.usr;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.sbs.lolHi.dto.Article;
import com.example.sbs.lolHi.service.ArticleService;
import com.example.sbs.lolHi.util.Util;

@Controller
public class ArticleController {
	
	@Autowired
	private ArticleService articleService;

	@RequestMapping("usr/article/list")
	public String showList(Model model, @RequestParam Map<String, Object> param, String searchKeyword, String searchType, HttpSession session) {
		
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
	public String showDetail(Model model, @RequestParam("id") int id, HttpSession session) {

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
	@ResponseBody
	public String showDoDelete(@RequestParam("id") int id) {

		articleService.DoDeleteArticle(id);
		
		return String.format("<script> alert('%d번 글이 삭제되었습니다.'); location.replace('list')</script>", id);
	}
	
	@RequestMapping("usr/article/write")
	public String showWrite(HttpSession session, Model model) {
		
		int loginedMemberId = 0;
		
		if ( session.getAttribute("loginedMemberId") != null ) {
			loginedMemberId = (int)session.getAttribute("loginedMemberId");
			model.addAttribute("loginedMemberId", loginedMemberId);
		}

		return "usr/article/write";
	}
	
	@RequestMapping("usr/article/doWrite")
	@ResponseBody
	public String showDoWrite(@RequestParam Map<String, Object> param, HttpSession session) {

		int loginedMemberId = (int)session.getAttribute("loginedMemberId");
		
		param.put("loginedMemberId", loginedMemberId);
		
		articleService.write(param);
		
		int id = Util.getAsInt(param.get("id"));
		
		return String.format("<script> alert('%d번 게시글이 생성되었습니다.'); location.replace('list?page=1')</script>", id);
	}
	
	@RequestMapping("usr/article/modify")
	public String showModify(Model model, @RequestParam("id") int id) {

		Article article = articleService.getArticle(id);
		
		model.addAttribute("article", article);
		
		return "usr/article/modify";
	}
	
	@RequestMapping("usr/article/doModify")
	@ResponseBody
	public String showDoModify(@RequestParam Map<String, Object> param) {
		
		articleService.modify(param);

		return String.format("<script> alert('%s글이 수정되었습니다.'); location.replace('list?page=1')</script>", param.get("id"));
	}
}
