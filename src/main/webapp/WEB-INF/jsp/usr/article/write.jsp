<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageName" value="${board.name} - 게시물 작성" />
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

<div class="write-article-box con-min-width">
	<div class="con">

		<form class="form-box-type-1" action="./doWrite" method="POST"
			onsubmit="writeFormSubmit(this); return false;">

			<input type="hidden" name="memberId" value="${loginedMemberId}" />
			<input type="hidden" name="boardCode" value="${board.code}" />

			<div>
				<div>
					<span>제목</span>
				</div>
				<div>
					<input type="text" name="title" maxlength="30"
						placeholder="제목을 입력해주세요." />
				</div>
			</div>

			<div>
				<div>
					<span>내용</span>
				</div>
				<div>
					<textarea maxlength="2000" name="body" placeholder="내용을 입력해주세요."></textarea>
				</div>
			</div>

			<div>
				<div>
					<span>작성</span>
				</div>
				<div>
					<input onclick="if ( confirm('작성하시겠습니까?') == false) return false;"
						type="submit" value="작성"/>
				</div>
			</div>
		</form>
	</div>
</div>

<%@ include file="../../part/foot.jspf"%>