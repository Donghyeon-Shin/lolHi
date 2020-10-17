<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<c:set var = "pageName" value = "로그인" />
	<%@ include file = "../../part/head.jspf" %>
	
	<form action="./doLogin" method="POST">
		
		<table>
		
			<tbody>
				<tr>
					<th>아이디</th>
					<td><input type="text" name = "loginId" placeholder="아이디를 입력해주세요."/></td>
				</tr>
				
				<tr>
					<th>비밀번호</th>
					<td><input type="text" name = "loginPw" placeholder="비밀번호를 입력해주세요."/></td>
				</tr>
				
				<tr>
					<th>이름</th>
					<td><input type="text" name = "name" placeholder="이름을 입력해주세요."/></td>
				</tr>
				
				<tr>
					<th>작성</th>
					<td><input type="submit" value="작성"></td>
				</tr>
			</tbody>
		
		</table>
	</form>
	
	<%@ include file = "../../part/foot.jspf" %>