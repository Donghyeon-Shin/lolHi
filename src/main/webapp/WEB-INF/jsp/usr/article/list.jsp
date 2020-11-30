<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageName" value="${board.name} - 리스트" />

<%@ include file="../../part/head.jspf"%>

<div class="con-min-width">
	<div class="con">
		<form class="form-box-type-1" action="" name="searchForm">
			<input type="hidden" name="page" value="1">

			<div>
				<div>
					<span>
						<select name="searchType">

							<option value="title">제목</option>
							<option value="body">내용</option>
							<option value="titleAndBody">제목+내용</option>

						</select>

						<script>
							if (typeof param.searchType == 'undefined') {
								param.searchType = 'title';
							}

							$(
									'form[name="searchForm"] select[name="searchType"]')
									.val(param.searchType);
						</script>
					</span>
				</div>
				<div>
					<input type="text" name="searchKeyword" placeholder="검색어를 입력해주세요."
						value="${param.searchKeyword}">
				</div>
			</div>

			<div>
				<div>
					<span>검색</span>
				</div>
				<div>
					<input type="submit" value="검색">
				</div>
			</div>



		</form>

		<hr />
	</div>
</div>

<div class="article-info-bar con-min-width">
	<div class="con article-info-bar__total-count">
		<span>총 게시물 수 :</span>
		<span>${totalCount}</span>
	</div>
</div>

<div class="article-info-bar con-min-width">
	<div class="con present-page">
		<span>현제페이지 : </span>
		<span>${page} / ${totalPage}</span>
	</div>
</div>





<div class="article-list-box con-min-width">
	<div class="con">

		<div class="article-list-box__head">
			<div class="article-list-box__row">
				<div>
					<span>번호</span>
				</div>
				<div>
					<span>작성날짜</span>
				</div>
				<div>
					<span>갱신날짜</span>
				</div>
				<div>
					<span>작성자</span>
				</div>
				<div>
					<span>제목</span>
				</div>
				<div>
					<span>작업</span>
				</div>
				<div>
					<span>조회수</span>
				</div>
			</div>
		</div>

		<div class="article-list-box__body">
			<c:forEach items="${articles}" var="article">
				<c:set var="detailUrl"
					value="/usr/article-${board.code}/detail?id=${article.id}&listUrl=${encodedCurrentUri}" />

				<div class="article-list-box__row">
					<div>
						<a href="${detailUrl}">${article.id }</a>
					</div>
					<div>
						<span> ${article.regDate} </span>
					</div>
					<div>
						<span>${article.updateDate}</span>
					</div>
					<div>
						<span> ${article.extra.writer} </span>
					</div>
					<div>
						<a href="${detailUrl}">${article.title }</a>
					</div>
					<div>
						<c:if test="${ article.extra.actorCanDelete }">
							<a onclick="if ( confirm('삭제하시겠습니까?') == false ) return false"
								href="doDelete?id=${article.id}">삭제</a>
						</c:if>
					</div>
					<div>
						<span>${article.hit }</span>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</div>

<div class="article-page-box con-min-width">

	<!-- 현제 페이지를 페이지 크기로 나눠서 그 몫을 반올림해 페이지 넘버를 정함-->
	<c:set var="pageNumber" value="${Math.ceil(page / pageMenuSize)}" />
	
	<!-- 현재 페이지가 페이지 사이즈보다 크면 첫번째 페이지로 이동할 수 있음-->
	<c:set var="goFirstBtnNeedToShow" value="${page > pageMenuSize }" />

	<!-- 마지막 페이지로 이동하는 경우는 forEach의 마지막 경우 말고는 다 허용 -->
	<c:set var="goLastBtnNeedToShow" value="true" />

	<!-- 현제 페이지가 1보다 크면 왼쪽으로 한 페이지 움직일 수 있음-->
	<c:set var="goLeftNextBtnNeedToShow" value="${page > 1}" />

	<!-- 현제 페이지가 마지막 페이지보다 작으면 오른쪽으로 한 페이지 움직일 수 있음-->
	<c:set var="goRightNextBtnNeedToShow" value="${page < totalPage }" />

	<!-- 첫번째 페이지 이동 버튼 구현 -->
	<div>
		<c:if test="${goFirstBtnNeedToShow}">
			<a
				href="?page=1&boardCode=${board.code}&searchType=${param.searchType}&searchKeyword=${param.searchKeyword}">◀◀</a>
		</c:if>
	</div>

	<!-- 왼쪽 한 페이지 이동 버튼 구현 -->
	<div class="article-page-box__leftNext">
		<c:if test="${goLeftNextBtnNeedToShow}">
			<a
				href="?page=${page - 1}&boardCode=${board.code}&searchType=${param.searchType}&searchKeyword=${param.searchKeyword}">◀</a>
		</c:if>
	</div>

	<!-- 페이지 넘버를 통해 몇번째 페이지부터 보여줄지 계산 -->
	<c:set var="pageToStart" value="${(pageNumber - 1 ) * 5 + 1}" />

	<!-- 페이지 넘버를 통해 몇번째 페이지까지 보여줄지 계산 -->
	<c:set var="pageToEnd" value="${(pageNumber - 1 ) * 5 + 5}" />

	<!-- 만약 pageToEnd가 totalPage보다 크면 pageToEnd를 totalPage로 변경-->
	<c:if test="${pageToEnd >= totalPage}">
		<c:set var="pageToEnd" value="${totalPage}" />
	</c:if>

	<!-- 페이지 리스팅 구현 -->
	<c:forEach var="i" begin="${pageToStart}" end="${pageToEnd}">
		<div class="article-page-box__row">
			<c:if test="${page == i}">
				<a class="article-page-box__currentPage"
					href="?page=${i}&boardCode=${board.code}&searchType=${param.searchType}&searchKeyword=${param.searchKeyword}">${i}</a>
			</c:if>

			<c:if test="${page != i}">
				<a
					href="?page=${i}&boardCode=${board.code}&searchType=${param.searchType}&searchKeyword=${param.searchKeyword}">${i}</a>
			</c:if>


			<c:if test="${ i == totalPage }">
				<c:set var="goLastBtnNeedToShow" value="false" />
			</c:if>
		</div>
	</c:forEach>

	<!-- 오른쪽으로 한 페이지 이동 버튼 구현 -->
	<div class="article-page-box__rightNext">
		<c:if test="${goRightNextBtnNeedToShow}">
			<a
				href="?page=${page + 1}&boardCode=${board.code}&searchType=${param.searchType}&searchKeyword=${param.searchKeyword}">▶</a>
		</c:if>
	</div>

	<!-- 마지막 페이지 이동 버튼 구현 -->
	<div>
		<c:if test="${goLastBtnNeedToShow}">
			<a
				href="?page=${totalPage}&boardCode=${board.code}&searchType=${param.searchType}&searchKeyword=${param.searchKeyword}">▶▶</a>
		</c:if>

	</div>

</div>
<%@ include file="../../part/foot.jspf"%>
