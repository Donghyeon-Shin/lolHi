package com.example.sbs.lolHi.controller.usr;

import java.util.List;
import java.util.Map;
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
	public String showList(Model model, @RequestParam Map<String, Object> param) {
				
		List<Article> articles = articleService.getArticles(param);
		
		model.addAttribute("articles", articles);
		
		model.addAllAttributes(param);
		
		return "usr/article/list";
	}
	
	@RequestMapping("usr/article/detail")
	public String showDetail(Model model, @RequestParam("id") int id) {

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
	public String showWrite() {

		return "usr/article/write";
	}
	
	@RequestMapping("usr/article/doWrite")
	@ResponseBody
	public String showDoWrite(@RequestParam Map<String, Object> param) {

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
