<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<c:set var = "pageName" value = "비밀번호 확인" />
	<%@ include file = "../../part/head.jspf" %>
	
	<script>

		var confirmPwFormSubmitDone = false;
		
		function confirmPwFormSubmit(form) {

				if ( confirmPwFormSubmitDone ) {
					alert('처리중 입니다.');

					return;
				}

				form.loginPw.value = form.loginPw.value.trim();

				if ( form.loginPw.value.length == 0 ) {
						alert('비밀번호를 입력해주세요.');
						form.loginPw.focus();

						return;
					}

				form.submit();
				confirmPwFormSubmitDone = true;
			
			}
	</script>
	
		<form action="doCheckPw" method="POST"
		onsubmit="confirmPwFormSubmit(this); return false;">
		
		<input type="hidden" name="redirectUrl" value="${redirectUrl}" />
		
		<table>
		
			<tbody>
				<tr>
					<th>비밀번호</th>
					<td><input type="text" name = "loginPw" maxlength="30" placeholder="비밀번호를 입력해주세요."/></td>
				</tr>
				
				<tr>
					<th>작성</th>
					<td><input type="submit" value="작성"></td>
				</tr>
			</tbody>
		
		</table>
	</form>
	

	<%@ include file = "../../part/foot.jspf" %>