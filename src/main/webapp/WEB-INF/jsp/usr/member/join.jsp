<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<c:set var = "pageName" value = "회원가입" />
	<%@ include file = "../../part/head.jspf" %>
	
	<script>

		var joinFormSubmitDone = false;
		
		function joinFormSubmit(form) {

				if ( joinFormSubmitDone ) {
					alert('처리중 입니다.');

					return;
				}
			
				form.loginId.value = form.loginId.value.trim();

				if ( form.loginId.value.length == 0 ) {
						alert('아이디를 입력해주세요.');
						form.loginId.focus();

						return;
					}

				form.loginPw.value = form.loginPw.value.trim();

				if ( form.loginPw.value.length == 0 ) {
						alert('비밀번호를 입력해주세요.');
						form.loginPw.focus();

						return;
					}

				form.loginPwConfirm.value = form.loginPwConfirm.value.trim();

				if ( form.loginPwConfirm.value.length == 0 ) {
						alert('비밀번호 확인을 입력해주세요.');
						form.loginPwConfirm.focus();

						return;
					}

				if ( form.loginPwConfirm.value != form.loginPw.value ) {
					
					alert('비밀번호가 일치하지 않습니다.');
					form.loginPwConfirm.focus();

					return;
				}
				
				form.name.value = form.name.value.trim();

				if ( form.name.value.length == 0 ) {
						alert('이름을 입력해주세요.');
						form.name.focus();

						return;
				}

				form.email.value = form.email.value.trim();

				if ( form.email.value.length == 0 ) {
						alert('이메일을 입력해주세요.');
						form.email.focus();

						return;
				}

				form.submit();
				joinFormSubmitDone = true;
			
			}
	</script>
	
		<form action="doJoin" method="POST"
		onsubmit="joinFormSubmit(this); return false;">
		
		<table>
		
			<tbody>
				<tr>
					<th>아이디</th>
					<td><input type="text" name = "loginId" maxlength="30" placeholder="아이디를 입력해주세요."/></td>
				</tr>
				
				<tr>
					<th>비밀번호</th>
					<td><input type="text" name = "loginPw" maxlength="30" placeholder="비밀번호를 입력해주세요."/></td>
				</tr>
				
				<tr>
					<th>비밀번호 확인</th>
					<td><input type="text" name = "loginPwConfirm" maxlength="30" placeholder="비밀번호를 입력해주세요."/></td>
				</tr>
				
				<tr>
					<th>이름</th>
					<td><input type="text" name = "name" maxlength="30" placeholder="이름을 입력해주세요."/></td>
				</tr>
				
				<tr>
					<th>이메일</th>
					<td><input type="email" name = "email" maxlength="50" placeholder="이메일을 입력해주세요."/></td>
				</tr>
				
				<tr>
					<th>작성</th>
					<td><input type="submit" value="작성"></td>
				</tr>
			</tbody>
		
		</table>
	</form>
	
	<%@ include file = "../../part/foot.jspf" %>