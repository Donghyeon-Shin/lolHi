<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<c:set var = "pageName" value = "게시물 리스트" />
	
	<%@ include file="../../part/head.jspf"%>

	<a href="write">게시글 생성</a>
	
	<c:forEach items="${articles}" var = "article">
		<div>
			<a href="detail?id=${article.id }">번호 : ${article.id }</a>
			<br />
			생성날짜 : ${article.regDate }
			<br />
			갱신날짜 : ${article.updateDate }
			<br />
			제목 : ${article.title }
			<br />
			내용 : ${article.body }
		</div>
		
		<hr />
	</c:forEach>
	
	<%@ include file="../../part/foot.jspf"%> 