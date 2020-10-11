package com.example.sbs.lolHi.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sbs.lolHi.dao.ArticleDao;
import com.example.sbs.lolHi.dto.Article;

@Service
public class ArticleService {
	
	int pageNumbering = 0;
	
	@Autowired
	private ArticleDao articleDao;

	public List<Article> getArticles(int limitCount, int limitFrom) {

		if ( limitFrom > 0 ) {
			pageNumbering = (limitFrom * 10);
		} else {
			pageNumbering = 0;
		}
		
		return articleDao.getArticles(limitCount, pageNumbering);
		
		
	}

	public Article getArticle(int id) {
		return articleDao.getArticle(id);
	}

	public void DoDeleteArticle(int id) {
		// TODO Auto-generated method stub
		articleDao.doDeleteArticle(id);
	}

	public void write(Map<String, Object> param) {
		articleDao.write(param);
	}

	public void modify(Map<String, Object> param) {
		articleDao.modify(param);
		
	}

	public int getArticlesCount() {
		
		return articleDao.getArticlesCount();
	}

	public List<Article> getArticles(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}


}
