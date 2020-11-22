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

<div class="modify-article-box con-min-width">
	<div class="con">

		<form class="form-box-type-1" action="./doModify" method="POST"
			onsubmit="modifyFormSubmit(this); return false;">


			<input type="hidden" name="id" value="${article.id}" />

			<div>
				<div>
					<span>번호</span>
				</div>
				<div>
					<span>${article.id}</span>
				</div>
			</div>

			<div>
				<div>
					<span>제목</span>
				</div>
				<div>
					<input type="text" name="title" maxlength="30"
						placeholder="제목을 입력해주세요." value="${article.title}" />
				</div>
			</div>

			<div>
				<div>
					<span>내용</span>
				</div>
				<div>
					<textarea name="body" maxlength="2000" placeholder="내용을 입력해주세요.">${article.body}</textarea>
				</div>
			</div>

			<div>
				<div>
					<span>수정</span>
				</div>
				<div>

					<input onclick="if ( confirm('수정하시겠습니까?') == false) return false;"
						type="submit" value="수정" />

				</div>
			</div>
		</form>

	</div>
</div>


<%@ include file="../../part/foot.jspf"%>