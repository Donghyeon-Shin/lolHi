<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageName" value="${article.id }번게시물 수정" />
<%@ include file="../../part/head.jspf"%>

<script>
	var modifyFormSubmitDone = false;

	function modifyFormSubmit(form) {

		if (modifyFormSubmitDone) {
			alert('처리중입니다.');
			return;
		}

		form.title.value = form.title.value.trim();

		if (form.title.value.length == 0) {

			alert('제목을 입력해주세요.');

			form.title.focus();
			return;
		}

		form.body.value = form.body.value.trim();

		if (form.body.value.length == 0) {

			alert('내용을 입력해주세요.');

			form.body.focus();
			return;
		}

		form.submit();

		modifyFormSubmitDone = true;
	}
</script>

<form action="./doModify" method="POST" onsubmit="modifyFormSubmit(this); return false;">


	<input type="hidden" name="id" value="${article.id}" />

	<div>번호 : ${article.id}</div>
	<div>
		제목 : <input type="text" name="title" maxlength="50" placeholder="제목을 입력해주세요."
			value="${article.title}" />
	</div>

	<div>
		내용 :
		<textarea name="body" placeholder="내용을 입력해주세요.">${article.body}</textarea>
	</div>
	<div>
		<button onclick="if ( confirm('수정하시겠습니까?') == false) return false;"
			type="submit">수정</button>
	</div>

</form>

<a onclick="history.back();">뒤로가기</a>

<%@ include file="../../part/foot.jspf"%>