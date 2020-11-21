<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageName" value="비밀번호 변경" />
<%@ include file="../../part/head.jspf"%>

<script>
	var ChangePwFormSubmitDone = false;

	function ChangePwFormSubmit(form) {

		if (ChangePwFormSubmitDone) {
			alert('처리중 입니다.');

			return;
		}

		form.loginPw.value = form.loginPw.value.trim();

		if (form.loginPw.value.length == 0) {
			alert('비밀번호를 입력해주세요.');
			form.loginPw.focus();
			return;
		}

		form.loginPwConfirm.value = form.loginPwConfirm.value.trim();

		if (form.loginPwConfirm.value.length == 0) {
			alert('비밀번호 확인을 입력해주세요.');
			form.loginPwConfirm.focus();
			return;
		}

		if (form.loginPwConfirm.value.length != form.loginPw.value.length) {

			alert('비밀번호가 일치하지 않습니다.');
			form.loginPwConfirm.focus();
			return;

		}

		form.submit();
		modifyFormSubmitDone = true;

	}
</script>

<form action="./doChangeLoginPw" method="POST"
	onsubmit="ChangePwFormSubmit(this); return false;">

	<div>
		<input type="hidden" name="checkLoginPwAuthCode"
			value="${checkLoginPwAuthCode}" />
	</div>

	<div>
		새 비밀번호 : <input type="text" name="loginPw" maxlength="30"
			placeholder="비밀번호를 입력해주세요." />

	</div>

	<div>
		새 비밀번호 확인 : <input type="text" name="loginPwConfirm" maxlength="30"
			placeholder="비밀번호 확인을 입력해주세요." />
	</div>

	<div>
		<input type="submit" value="변경">
	</div>

</form>

<div>

	<a href="../home/main">돌아가기</a>
</div>

<%@ include file="../../part/foot.jspf"%>