<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<c:set var = "pageName" value = "${article.id }번게시물 수정" />
	<%@ include file = "../../part/head.jspf" %>

	<form action="./doModify" method="POST">


		<input type="hidden" name="id" value="${article.id}" />
		
		<div>
		 	번호 : ${article.id}
		</div>
		<div>
			제목 : <input type="text" name="title" placeholder="제목을 입력해주세요."
						value="${article.title}" /> 		
		</div>

		<div>
			내용 : <textarea name="body" placeholder="내용을 입력해주세요.">${article.body}</textarea>
		</div>
		<div>
			<input type="submit" value="수정">
		</div>
		
	</form>
	
	<a onclick="history.back();">뒤로가기</a>

	<%@ include file = "../../part/foot.jspf" %>