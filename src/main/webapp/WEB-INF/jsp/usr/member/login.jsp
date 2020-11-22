<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageName" value="로그인" />
<%@ include file="../../part/head.jspf"%>

<script>
	var loginFormSubmitDone = false;

	function loginFormSubmit(form) {

		if (loginFormSubmitDone) {
			alert('처리중입니다.');
			return;
		}

		form.loginId.value = form.loginId.value.trim();

		if (form.loginId.value.length == 0) {

			alert('로그인 아이디를 입력해주세요.');

			form.loginId.focus();
			return;
		}

		form.loginPw.value = form.loginPw.value.trim();

		if (form.loginPw.value.length == 0) {

			alert('로그인 비밀번호를 입력해주세요.');

			form.loginPw.focus();
			return;
		}

		form.submit();

		loginFormSubmitDone = true;
	}
</script>

<div class="login-box con-min-width">
	<div class="con">
		<form class="form-box-type-1" action="doLogin" method="POST"
			onsubmit="joinFormSubmit(this); return false;">

			<div>
				<div>
					<span>아이디</span>
				</div>
				<div>
					<input type="text" name="loginId" placeholder="아이디를 입력해주세요." />
				</div>
			</div>
			<div>
				<div>
					<span>비밀번호</span>
				</div>
				<div>
					<input type="password" name="loginPw" placeholder="비밀번호를 입력해주세요." />
				</div>
			</div>
			<div>
				<div>
					<span>로그인</span>
				</div>
				<div>
					<input type="submit" value="로그인">
				</div>
			</div>

		</form>
	</div>
</div>


<%@ include file="../../part/foot.jspf"%>