package com.example.sbs.lolHi.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.sbs.lolHi.dto.Article;
import com.example.sbs.lolHi.dto.ArticleReply;

@Mapper
public interface ArticleDao {


	List<Article> getForPrintArticlesById(Map<String, Object> param);
	
	Article getForPrintArticleById(@Param("id") int id);

	void doDeleteArticleById(@Param("id") int id);

	void doWrite(Map<String, Object> param);

	void doModify(Map<String, Object> param);

	int getTotalCount();

	void doWriteReply(Map<String, Object> param);

	List<ArticleReply> getForPrintArticleReplysById(int id);
	
	ArticleReply getArticleReplyById(int id);

	void doDeleteReply(int id);


}
