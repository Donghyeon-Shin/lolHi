<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<c:set var = "pageName" value = "게시물 작성" />
	<%@ include file = "../../part/head.jspf" %>
	
	<c:if test="${loginedMemberId == null }">
		
		<script>
			alert('로그인 후 이용해 주세요.');
			history.back();
		</script>
		
	</c:if>
	
	<form action="./doWrite" method="POST">
	
		<input type="hidden" name = "memberId" value="${loginedMemberId}"/>
		
		<table>
			<tbody>
				<tr>
					<th>제목</th>
					<td><input type="text" name = "title" placeholder="제목을 입력해주세요."/></td>
				</tr>
				
				<tr>
					<th>내용</th>
					<td><textarea name="body" placeholder="내용을 입력해주세요."  ></textarea></td>
				</tr>
				
				<tr>
					<th>작성</th>
					<td><input type="submit" value="작성"></td>
				</tr>
			</tbody>
		
		</table>
	</form>
	
	<a onclick="history.back();">뒤로가기</a>
	
	<%@ include file = "../../part/foot.jspf" %>