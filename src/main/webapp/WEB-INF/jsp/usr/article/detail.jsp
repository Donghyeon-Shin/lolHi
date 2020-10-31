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
		<li>작성자 : ${article.extra.writer}</li>
		<li>내용 : ${article.body}</li>
	</ul>
	
	<form action="doWriteReply" method="POST">
	
			<input type="hidden" name = "articleId" value="${article.id}"/>
					
			<textarea name = "body" placeholder="댓글을 적어주세요."></textarea>
			<button type = "submit">작성</button> 
	</form>
	
	<table border = "1">
		<thead>
			<tr>
				<th>작성자</th>
				<th>날짜</th>
				<th>내용</th>
				<th>비고</th>
			</tr>
		</thead>
		
		
		<tbody>
			<c:forEach items="${articleReplys}" var="articleReply">
				<tr>
					<td>${articleReply.extra.writer}</td>
					<td>${articleReply.regDate}</td>
					<td>${articleReply.body}</td>
					<td>삭제하기</td>
				</tr>
			</c:forEach>
		
		</tbody>
		
	</table>
	
	
	
	<a href="list">돌아가기</a>
	
	<c:if test="${ article.memberId == loginedMemberId }">
		<a href="modify?id=${article.id}">게시글 수정</a>
	</c:if>	
	<a onclick="if ( confirm('삭제하시겠습니까?') == false ) return false" href="doDelete?id=${article.id}">게시글 삭제</a>
	
	<%@ include file = "../../part/foot.jspf" %>
	