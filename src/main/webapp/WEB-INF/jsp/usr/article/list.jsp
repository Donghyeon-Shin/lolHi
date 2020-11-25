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
	<c:set var="goFirstBtnNeedToShow" value="${page > pageMenuSize + 1 }" />
	<c:set var="goLastBtnNeedToShow" value="true" />
	
	<!-- 최대 2페이지가 넘어가면 그냥 최대 페이지를 보여주도록 -->
	<c:set var="TwoMaxPage" value="${page + 2 }" />
	<c:if test="${page + 2 > totalPage}">
		<c:set var="TwoMaxPage" value="${totalPage}" />
	</c:if>

	<!-- 최소 2페이지가 넘어가면 그냥 최소 페이지를 보여주도록 -->
	<c:set var="TwoMinPage" value="${page - 2}" />
	<c:if test="${page - 2 < 1}">
		<c:set var="TwoMinPage" value="1" />
	</c:if>
	


	<!-- 최대 5페이지가 넘어가면 그냥 최대 페이지를 보여주도록 -->
	<c:set var="FiveMaxPage" value="${page + 5 }" />
	<c:if test="${page + 5 > totalPage}">
		<c:set var="FiveMaxPage" value="${totalPage}" />
	</c:if>

	<!-- 최소 5페이지가 넘어가면 그냥 최소 페이지를 보여주도록 -->
	<c:set var="FiveMinPage" value="${page - 5}" />
	<c:if test="${page - 5 < 1}">
		<c:set var="FiveMinPage" value="1" />
	</c:if>


	<div>
		<c:if test="${goFirstBtnNeedToShow}">
			<a
				href="?page=1&boardCode=${board.code}&searchType=${param.searchType}&searchKeyword=${param.searchKeyword}">◀◀</a>
		</c:if>
	</div>

	<div class = "article-page-box__leftNext">
		<a
			href="?page=${FiveMinPage}&boardCode=${board.code}&searchType=${param.searchType}&searchKeyword=${param.searchKeyword}">◀</a>

	</div>

	<c:forEach var="i" begin="${TwoMinPage}" end="${TwoMaxPage}">
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

	<div class = "article-page-box__rightNext">
		<c:set var="MaxPage" value="${page + 5 }" />
		<c:if test="${page + 5 > totalPage}">
			<c:set var="MaxPage" value="${totalPage}" />
		</c:if>

		<a
			href="?page=${FiveMaxPage}&boardCode=${board.code}&searchType=${param.searchType}&searchKeyword=${param.searchKeyword}">▶</a>
	</div>

	<div>
		<c:if test="${goLastBtnNeedToShow}">
			<a
				href="?page=${totalPage}&boardCode=${board.code}&searchType=${param.searchType}&searchKeyword=${param.searchKeyword}">▶▶</a>
		</c:if>

	</div>
</div>
<%@ include file="../../part/foot.jspf"%>
