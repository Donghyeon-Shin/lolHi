package com.example.sbs.lolHi.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sbs.lolHi.dao.ArticleDao;
import com.example.sbs.lolHi.dto.Article;
import com.example.sbs.lolHi.dto.Board;
import com.example.sbs.lolHi.dto.Member;
import com.example.sbs.lolHi.util.Util;

@Service
public class ArticleService {

	
	@Autowired
	private ArticleDao articleDao;
	

	public List<Article> getForPrintArticlesById(Member actorMember, Map<String, Object> param) {

		int page = Util.getAsInt(param.get("page"), 1);

		int itemsCountInAPage = Util.getAsInt(param.get("itemsCountInAPage"), 10);
		
		if ( itemsCountInAPage > 100 ) {
			itemsCountInAPage = 100;
		} else if ( itemsCountInAPage < 1 ){
			itemsCountInAPage = 1;
		}
		
		int limitFrom = (page - 1 ) * itemsCountInAPage;
		
		int limitTake = itemsCountInAPage;
		
		param.put("limitFrom", limitFrom);
		param.put("limitTake", limitTake);
		
		List<Article> articles = articleDao.getForPrintArticlesById(param);

		for ( Article article : articles ) {
			if ( article.getExtra() == null ) {
				article.setExtra(new HashMap<>()); 
			}

			boolean actorCanDelete = false;
			
			if ( actorMember != null ) {
				actorCanDelete = actorMember.getId() == article.getMemberId();
			}
			boolean actorCanModify = actorCanDelete;

			article.getExtra().put("actorCanDelete", actorCanDelete);
			article.getExtra().put("actorCanModify", actorCanModify);
		}
		
		return articles;
	}

	public Article getForPrintArticleById(Member actorMember, int id) {
		
		Article article = articleDao.getForPrintArticleById(id);
		
		if ( article.getExtra() == null ) {
			article.setExtra(new HashMap<>()); 
		}

		boolean actorCanDelete = false;
		
		if ( actorMember != null ) {
			actorCanDelete = actorMember.getId() == article.getMemberId();
		}
		boolean actorCanModify = actorCanDelete;

		article.getExtra().put("actorCanDelete", actorCanDelete);
		article.getExtra().put("actorCanModify", actorCanModify);
		
		return article;
	}

	public void doDeleteArticleById(int id) {
		// TODO Auto-generated method stub
		articleDao.doDeleteArticleById(id);
	}

	public void doWrite(Map<String, Object> param) {
		articleDao.doWrite(param);
	}

	public void doModify(Map<String, Object> param) {
		articleDao.doModify(param);
		
	}

	public int getTotalCount(Map<String, Object> param) {
		
		return articleDao.getTotalCount(param);
	}

	public Board getBoard(String boardCode) {
		// TODO Auto-generated method stub
		return articleDao.getBoard(boardCode);
	}

}
