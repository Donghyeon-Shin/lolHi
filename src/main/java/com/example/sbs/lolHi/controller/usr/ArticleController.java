package com.example.sbs.lolHi.controller.usr;

import java.util.List;
import java.util.Map;

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
	public String showDoDelete(@RequestParam("id") int id, Model model, HttpSession session) {
		
		int loginedMemberId = 0;
		

		if ( session.getAttribute("loginedMemberId") != null ) {
			loginedMemberId = (int)session.getAttribute("loginedMemberId");
			model.addAttribute("loginedMemberId", loginedMemberId);
		}

		if ( loginedMemberId == 0 ) {
			model.addAttribute("msg", "로그인 후 이용해주세요.");
			model.addAttribute("replaceUri", "/usr/member/login");
			return "common/redirect";
		}

		articleService.DoDeleteArticle(id);
		
		model.addAttribute("msg", String.format("%d번 글이 삭제되었습니다.", id));
		model.addAttribute("replaceUri", "/usr/article/list");
		return "common/redirect";
	}
	
	@RequestMapping("usr/article/write")
	public String showWrite(HttpSession session, Model model) {
		
		int loginedMemberId = 0;
		
		if ( session.getAttribute("loginedMemberId") != null ) {
			loginedMemberId = (int)session.getAttribute("loginedMemberId");
			model.addAttribute("loginedMemberId", loginedMemberId);
		}

		if ( loginedMemberId == 0 ) {
			model.addAttribute("msg", "로그인 후 이용해주세요.");
			model.addAttribute("replaceUri", "/usr/member/login");
			return "common/redirect";
		}
		
		return "usr/article/write";
	}
	
	@RequestMapping("usr/article/doWrite")
	public String showDoWrite(@RequestParam Map<String, Object> param, HttpSession session, Model model) {

		int loginedMemberId = 0;
		

		if ( session.getAttribute("loginedMemberId") != null ) {
			loginedMemberId = (int)session.getAttribute("loginedMemberId");
			model.addAttribute("loginedMemberId", loginedMemberId);
		}

		if ( loginedMemberId == 0 ) {
			model.addAttribute("msg", "로그인 후 이용해주세요.");
			model.addAttribute("replaceUri", "/usr/member/login");
			return "common/redirect";
		}
		
		param.put("loginedMemberId", loginedMemberId);
		
		articleService.write(param);
		
		int id = Util.getAsInt(param.get("id"));
		
		
		model.addAttribute("msg", String.format("%d번 글이 생성되었습니다.", id));
		model.addAttribute("replaceUri", String.format("/usr/article/detail?id=%d", id));
		return "common/redirect";
	}
	
	@RequestMapping("usr/article/modify")
	public String showModify(Model model, @RequestParam("id") int id, HttpSession session) {
		
		int loginedMemberId = 0;
		

		if ( session.getAttribute("loginedMemberId") != null ) {
			loginedMemberId = (int)session.getAttribute("loginedMemberId");
			model.addAttribute("loginedMemberId", loginedMemberId);
		}

		if ( loginedMemberId == 0 ) {
			model.addAttribute("msg", "로그인 후 이용해주세요.");
			model.addAttribute("replaceUri", "/usr/member/login");
			return "common/redirect";
		}

		Article article = articleService.getArticle(id);
		
		model.addAttribute("article", article);
		
		return "usr/article/modify";
	}
	
	@RequestMapping("usr/article/doModify")
	public String showDoModify(@RequestParam Map<String, Object> param, Model model, HttpSession session) {
		
		int loginedMemberId = 0;
		

		if ( session.getAttribute("loginedMemberId") != null ) {
			loginedMemberId = (int)session.getAttribute("loginedMemberId");
			model.addAttribute("loginedMemberId", loginedMemberId);
		}

		if ( loginedMemberId == 0 ) {
			model.addAttribute("msg", "로그인 후 이용해주세요.");
			model.addAttribute("replaceUri", "/usr/member/login");
			return "common/redirect";
		}

		
		articleService.modify(param);
		
		int id = Util.getAsInt(param.get("id"));

		model.addAttribute("msg", String.format("%d번 글이 생성되었습니다.", id));
		model.addAttribute("replaceUri", String.format("/usr/article/detail?id=%d", id));
		return "common/redirect";
	}
}
