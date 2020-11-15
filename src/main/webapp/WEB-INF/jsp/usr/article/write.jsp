<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageName" value="${board.name}-게시물 작성" />
<%@ include file="../../part/head.jspf"%>

<script>
	var writeFormSubmitDone = false;

	function writeFormSubmit(form) {

		if (writeFormSubmitDone) {
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

		writeFormSubmitDone = true;
	}
</script>

<form action="./doWrite" method="POST"
	onsubmit="writeFormSubmit(this); return false;">

	<input type="hidden" name="memberId" value="${loginedMemberId}" />
	<input type="hidden" name="boardCode" value="${board.code}" />

	<table>
		<tbody>
			<tr>
				<th>제목</th>
				<td><input type="text" name="title" maxlength="50" placeholder="제목을 입력해주세요." /></td>
			</tr>

			<tr>
				<th>내용</th>
				<td><textarea name="body" placeholder="내용을 입력해주세요."></textarea></td>
			</tr>

			<tr>
				<th>작성</th>
				<td><button onclick="if ( confirm('작성하시겠습니까?') == false) return false;"
					type="submit">작성</button></td>
			</tr>
		</tbody>

	</table>
</form>

<a onclick="history.back();">뒤로가기</a>

<%@ include file="../../part/foot.jspf"%>