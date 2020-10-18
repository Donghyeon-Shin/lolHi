<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<c:set var = "pageName" value = "로그인" />
	<%@ include file = "../../part/head.jspf" %>
	
	<script>
		var loginFormSubmitDone = false;
	
		function loginFormSubmit(form) {

				if ( loginFormSubmitDone ) {
						alert('처리중입니다.');
						return;
					}

				form.loginId.value = form.loginId.value.trim();

				if ( form.loginId.value.length == 0 ) {
							
						alert('로그인 아이디를 입력해주세요.');

						form.loginId.focus();
						return;
					}

				form.loginPw.value = form.loginPw.value.trim();

				if ( form.loginPw.value.length == 0 ) {
							
						alert('로그인 비밀번호를 입력해주세요.');

						form.loginPw.focus();
						return;
					}

				form.submit();
				loginFormSubmitDone = true;
			}
	</script>
	
	<form action="./doLogin" method="POST" onclick="loginFormSubmit(this); return false">
		
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
					<th>작성</th>
					<td><input type="submit" value="작성"></td>
				</tr>
			</tbody>
		
		</table>
	</form>
	
	<%@ include file = "../../part/foot.jspf" %>