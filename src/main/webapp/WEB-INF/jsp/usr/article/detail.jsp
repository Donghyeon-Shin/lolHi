<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageName" value="${board.name} -  ${article.id}번 상세 페이지" />

<%@ include file="../../part/head.jspf"%>

<div class="article-detail-box con-min-width">
	<div class="con">

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
				<span>조회수</span>
			</div>
			<div>
				<span> ${article.hit}</span>
			</div>
		</div>

		<div>
			<div>
				<span>작성날짜</span>
			</div>
			<div>
				<span> ${article.regDate}</span>
			</div>
		</div>

		<div>
			<div>
				<span>갱신날짜</span>
			</div>
			<div>
				<span> ${article.updateDate}</span>
			</div>
		</div>

		<div>
			<div>
				<span>제목</span>
			</div>
			<div>
				<span>${article.forPrintTitle}</span>
			</div>
		</div>


		<div>
			<div>
				<span>작성자</span>
			</div>
			<div>
				<span>${article.extra.writer}</span>
			</div>
		</div>



		<div>
			<div>
				<span>내용</span>
			</div>
			<div>
				<span>${article.forPrintBody}</span>
			</div>
		</div>

		<hr />
	</div>


</div>



<div class="sub-list-bar">
	<div class="con">

		<c:if test="${ article.extra.actorCanDelete }">
			<a href="modify?id=${article.id}">게시글 수정</a>
		</c:if>
		<c:if test="${  article.extra.actorCanModify }">
			<a onclick="if ( confirm('삭제하시겠습니까?') == false ) return false"
				href="doDelete?id=${article.id}">게시글 삭제</a>
		</c:if>
	</div>
</div>

<!-- 댓글 작성 -->

<div class="con-min-width">
	<div class="con">
		<h2>댓글 작성</h2>
	</div>
</div>

<div class="write-reply-box con-min-width">
	<div class="con">

		<form class="form-box-type-1" action="../reply/doWrite" method="POST">


			<input type="hidden" name="redirectUrl" value="${currentUri}" />
			<input type="hidden" name="relTypeCode" value="article" />
			<input type="hidden" name="relId" value="${article.id}" />



			<div>
				<div>
					<span>내용</span>
				</div>
				<div>
					<textarea maxlength="2000" name="body" placeholder="댓글을 적어주세요."></textarea>
				</div>
			</div>

			<div>
				<div>
					<span>작성</span>
				</div>
				<div>

					<input onclick="if ( confirm('작성하시겠습니까?') == false) return false;"
						type="submit" value="작성" />
				</div>
			</div>

		</form>

		<hr />
	</div>
</div>

<!-- 댓글 리스트 -->
<c:if test="${replyExists}">

	<div class="con-min-width">
		<div class="con">
			<h2>댓글 목록</h2>
		</div>
	</div>

	<div class="article-reply-list-box con-min-width">
		<div class="con">

			<div class="article-reply-list-box__head">
				<div class="article-reply-list-box__row">
					<div>
						<span>번호</span>
					</div>
					<div>
						<span>작성날짜</span>
					</div>
					<div>
						<span>작성자</span>
					</div>
					<div>
						<span>내용</span>
					</div>
					<div>
						<span>작업</span>
					</div>
				</div>
			</div>

			<div class="article-reply-list-box__body">

				<c:forEach items="${articleReplies}" var="articleReply">
					<div class="article-reply-list-box__row">
						<div>
							<span>${articleReply.id}</span>
						</div>
						<div>
							<span>${articleReply.regDate}</span>
						</div>
						<div>
							<span>${articleReply.extra.writer}</span>
						</div>
						<div>
							<span>${articleReply.getForPrintBody()}</span>
						</div>
						<div>
						
							<c:if test="${articleReply.extra.actorCanDelete}">
								<a onclick="if ( confirm('삭제하시겠습니까?') == false ) return false"
									href="../reply/doDelete?id=${articleReply.id}&redirectUrl=${encodedCurrentUri}">삭제하기</a>
							</c:if>
							<c:if test="${articleReply.extra.actorCanModify}">
								<a
									href="../reply/modify?id=${articleReply.id}&redirectUrl=${encodedCurrentUri}">수정하기</a>
							</c:if>
						</div>
					</div>
				</c:forEach>

			</div>
		</div>
	</div>

</c:if>


<%@ include file="../../part/foot.jspf"%>