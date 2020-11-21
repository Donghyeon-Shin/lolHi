<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<c:set var = "pageName" value = "회원정보 수정" />
	<%@ include file = "../../part/head.jspf" %>
	
	<script>

		var modifyFormSubmitDone = false;
		
		function modifyFormSubmit(form) {

				if ( modifyFormSubmitDone ) {
					alert('처리중 입니다.');

					return;
				}

				form.name.value = form.name.value.trim();

				if ( form.name.value.length == 0 ) {
						alert('이름을 입력해주세요.');
						form.name.focus();
						return;
					}

				form.submit();
				modifyFormSubmitDone = true;
			
			}
	</script>

	<form action="./doModify" method="POST" onsubmit="modifyFormSubmit(this); return false;">

		<div>
			번호 : ${loginedMember.id}
		</div>
		<div>
			가입날짜 : ${loginedMember.regDate}	
		</div>
		<div>
			로그인 아이디 : ${loginedMember.loginId}	
		</div>

		<div>
			이름 : <input type="text" name="name" maxlength="30" placeholder="이름을 입력해주세요."
						value="${requestScope.loginedMember.name}" />
		</div>
		<div>
			<input type="submit" value="수정">
		</div>
		
	</form>
	
	<div>
		<a href="../member/confirmPw?redirectUrl=/usr/member/changePw">비밀번호 변경</a>
	
		<a href="../home/main">돌아가기</a>
	</div>

	<%@ include file = "../../part/foot.jspf" %>