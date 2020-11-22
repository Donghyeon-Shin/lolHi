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


<div class="changeLoginPw-box con-min-width">
	<div class="con">

		<form class="form-box-type-1" action="./doChangeLoginPw" method="POST"
			onsubmit="ChangePwFormSubmit(this); return false;">

			<div>
				<input type="hidden" name="checkLoginPwAuthCode"
					value="${checkLoginPwAuthCode}" />
			</div>

			<div>
				<div>
					<span>새 비밀번호</span>
				</div>
				<div>
					<input type="text" name="loginPw" maxlength="30"
						placeholder="비밀번호를 입력해주세요." />
				</div>
			</div>

			<div>
				<div>
					<span> 새 비밀번호 확인 : </span>
				</div>
				<div>
					<input type="password" name="loginPwConfirm" maxlength="30"
						placeholder="비밀번호 확인을 입력해주세요." />
				</div>
			</div>

			<div>
				<div>
					<span>변경</span>
				</div>
				<div>
					<input type="submit" onclick="if ( confirm('변경하시겠습니까?') == false) return false;" value="변경">
				</div>
			</div>
		</form>

	</div>
</div>


<%@ include file="../../part/foot.jspf"%>