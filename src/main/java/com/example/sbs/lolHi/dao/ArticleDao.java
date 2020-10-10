package com.example.sbs.lolHi.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.sbs.lolHi.dto.Article;

@Mapper
public interface ArticleDao {

	List<Article> getArticles();

	Article getArticle(@Param("id") int id);

	void doDeleteArticle(@Param("id") int id);

	void write(Map<String, Object> param);

	void modify(Map<String, Object> param);


}
