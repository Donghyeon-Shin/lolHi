package com.example.sbs.lolHi.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sbs.lolHi.dao.ArticleDao;
import com.example.sbs.lolHi.dto.Article;
import com.example.sbs.lolHi.util.Util;

@Service
public class ArticleService {

	
	@Autowired
	private ArticleDao articleDao;
	

	public List<Article> getForPrintArticlesById(Map<String, Object> param) {

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
		
		return articleDao.getForPrintArticlesById(param);
	}

	public Article getForPrintArticleById(int id) {
		return articleDao.getForPrintArticleById(id);
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

	public int getTotalCount() {
		
		return articleDao.getTotalCount();
	}


}
