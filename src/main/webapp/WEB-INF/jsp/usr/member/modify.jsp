<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageName" value="회원정보 수정" />
<%@ include file="../../part/head.jspf"%>

<script>
	var modifyFormSubmitDone = false;

	function modifyFormSubmit(form) {

		if (modifyFormSubmitDone) {
			alert('처리중 입니다.');

			return;
		}

		form.name.value = form.name.value.trim();

		if (form.name.value.length == 0) {
			alert('이름을 입력해주세요.');
			form.name.focus();
			return;
		}

		form.submit();
		modifyFormSubmitDone = true;

	}
</script>

<div class="modify-member-box con-min-width">
	<div class="con">
		<form class="form-box-type-1" action="./doModify" method="POST"
			onsubmit="modifyFormSubmit(this); return false;">

			<div>
				<input type="hidden" name="checkLoginPwAuthCode"
					value="${checkLoginPwAuthCode}" />
			</div>

			<div>
				<div>
					<span>번호</span>
				</div>
				<div>
					<span>${loginedMember.id}</span>
				</div>
			</div>

			<div>
				<div>
					<span>가입날짜</span>
				</div>
				<div>
					<span>${loginedMember.regDate}</span>
				</div>
			</div>

			<div>
				<div>
					<span>로그인 아이디</span>
				</div>
				<div>
					<span>${loginedMember.loginId}</span>
				</div>
			</div>

			<div>
				<div>
					<span>이름</span>
				</div>
				<div>
					<input type="text" name="name" maxlength="30"
						placeholder="이름을 입력해주세요."
						value="${requestScope.loginedMember.name}" />
				</div>
			</div>

			<div>
				<div>
					<span>수정</span>
				</div>
				<div>
					<input type="submit" onclick="if ( confirm('수정하시겠습니까?') == false) return false;" value="수정">
				</div>
			</div>

			<div>
				<div>
					<span>비밀번호 변경</span>
				</div>
				<div>
					<input type="button" value="비밀번호 변경"
						onclick="location.href='../member/changeLoginPw?checkLoginPwAuthCode=${checkLoginPwAuthCode}'" />
				</div>
			</div>
		</form>

	</div>
</div>


<div></div>

<%@ include file="../../part/foot.jspf"%>