package com.example.sbs.lolHi.controller.usr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.sbs.lolHi.dto.Article;
import com.example.sbs.lolHi.service.ArticleService;

@Controller
public class ArticleController {
	
	@Autowired
	private ArticleService articleService;

	@RequestMapping("usr/article/list")
	public String showList(Model model) {
		
		List<Article> articles = articleService.getArticles();
		
		model.addAttribute("articles", articles);
		
		return "usr/article/list";
	}
	
	@RequestMapping("usr/article/detail")
	public String showDetail(@RequestParam("id") int id,Model model) {

		Article article = articleService.getArticle(id);
		
		model.addAttribute("article", article);
		
		return "usr/article/detail";
	}
	
	@RequestMapping("usr/article/doDelete")
	public String showDoDelete(@RequestParam("id") int id, Model model) {

		articleService.DoDeleteArticle(id);
		
		model.addAttribute("id", id);
		
		return "usr/article/doDelete";
	}
}
