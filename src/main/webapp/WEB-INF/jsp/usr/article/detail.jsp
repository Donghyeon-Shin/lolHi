<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 ${article.id}번 </title>
</head>
<body>
	<h1>게시물 ${article.id}번 </h1>
	
	<ul>
		<li>번호 : ${article.id}</li>
		<li>생성날짜 : ${article.regDate}</li>
		<li>갱신날짜 : ${article.updateDate}</li>
		<li>제목 : ${article.title}</li>
		<li>내용 : ${article.body}</li>
	</ul>
	
	<a href="list">돌아가기</a>
	<a href="modify?id=${article.id}">게시글 수정</a>
	<a href="doDelete?id=${article.id}">게시글 삭제</a>

</body>
</html>