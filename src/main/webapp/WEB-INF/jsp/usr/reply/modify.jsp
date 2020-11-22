<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageName" value="${reply.id }번 댓글 수정" />
<%@ include file="../../part/head.jspf"%>

<script>
	var modifyFormSubmitDone = false;

	function modifyFormSubmit(form) {

		if (modifyFormSubmitDone) {
			alert('처리중입니다.');
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

<div class="modify-reply-box con-min-width">
	<div class="con">

		<form class="form-box-type-1" action="./doModify" method="POST"
			onsubmit="modifyFormSubmit(this); return false;">


			<input type="hidden" name="redirectUrl" value="${redirectUrl}" />
			<input type="hidden" name="id" value="${reply.id}" />


			<div>
				<div>
					<span>번호</span>
				</div>
				<div>
					<span>${reply.id}</span>
				</div>
			</div>

			<div>
				<div>
					<span>번호</span>
				</div>
				<div>
					<span>${reply.id}</span>
				</div>
			</div>

			<div>
				<div>
					<span>내용</span>
				</div>
				<div>
					<textarea name="body" maxlength="300" placeholder="내용을 입력해주세요.">${reply.body}</textarea>
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