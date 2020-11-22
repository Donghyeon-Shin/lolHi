<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageName" value="비밀번호 확인" />
<%@ include file="../../part/head.jspf"%>

<script>
	var confirmPwFormSubmitDone = false;

	function confirmPwFormSubmit(form) {

		if (confirmPwFormSubmitDone) {
			alert('처리중 입니다.');

			return;
		}

		form.loginPw.value = form.loginPw.value.trim();

		if (form.loginPw.value.length == 0) {
			alert('비밀번호를 입력해주세요.');
			form.loginPw.focus();

			return;
		}

		form.submit();
		confirmPwFormSubmitDone = true;

	}
</script>


<div class="checkPw-box con-min-width">
	<div class="con">

		<form class="form-box-type-1" action="doCheckPw" method="POST"
			onsubmit="confirmPwFormSubmit(this); return false;">

			<input type="hidden" name="redirectUrl" value="${redirectUrl}" />

			<div>
				<div>
					<span>비밀번호</span>
				</div>
				<div>
					<input type="text" name="loginPw" maxlength="30"
						placeholder="비밀번호를 입력해주세요." />
				</div>
			</div>

			<div>
				<div>
					<span>확인</span>
				</div>
				<div>
					<input type="submit" value="확인">
				</div>
			</div>
		</form>
	</div>
</div>



<%@ include file="../../part/foot.jspf"%>