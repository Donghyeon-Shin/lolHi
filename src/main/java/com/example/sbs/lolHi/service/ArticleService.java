package com.example.sbs.lolHi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sbs.lolHi.dao.ArticleDao;
import com.example.sbs.lolHi.dto.Article;

@Service
public class ArticleService {

	@Autowired
	private ArticleDao articleDao;

	public List<Article> getArticles() {

		return articleDao.getArticles();
	}

}
