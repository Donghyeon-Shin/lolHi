<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<c:set var = "pageName" value = "${article.id}번 상세 페이지"/>

	<%@ include file = "../../part/head.jspf" %>
	
	<ul>
		<li>번호 : ${article.id}</li>
		<li>생성날짜 : ${article.regDate}</li>
		<li>갱신날짜 : ${article.updateDate}</li>
		<li>제목 : ${article.title}</li>
		<li>내용 : ${article.body}</li>
	</ul>
	
	<a href="list?page=1">돌아가기</a>
	
	<c:if test="${ article.memberId == loginedMemberId }">
		<a href="modify?id=${article.id}">게시글 수정</a>
	</c:if>	
	<a onclick="if ( confirm('삭제하시겠습니까?') == false ) return false" href="doDelete?id=${article.id}">게시글 삭제</a>

	<%@ include file = "../../part/foot.jspf" %>
	