package com.example.sbs.lolHi.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.sbs.lolHi.dto.Article;

@Mapper
public interface ArticleDao {

	List<Article> getArticles();

}
