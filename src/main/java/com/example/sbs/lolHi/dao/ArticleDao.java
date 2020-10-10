package com.example.sbs.lolHi.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.sbs.lolHi.dto.Article;

@Mapper
public interface ArticleDao {

	List<Article> getArticles();

	Article getArticle(@Param("id") int id);

	void doDeleteArticle(@Param("id") int id);

}
