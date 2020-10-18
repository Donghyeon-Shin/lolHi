<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<c:set var = "pageName" value = "게시물 리스트" />
	
	<%@ include file="../../part/head.jspf"%>

	<a href="write">게시글 생성</a>
	
	<div class="con margin-top-30">
	<span>
		<span>총 게시물 수 : </span>
		<span>${totalCount}</span>
			</span>
			<span>/</span>
		<span>
			<span>현재 페이지 : </span>
			<span style="color:red;">${page}</span>
		</span>
	
	</div>
	
	<div class="con">
		<form action="" name="searchForm">
		<input type="hidden" name="page" value="1">
		<select name="searchType">
		
			<option value="title">제목</option>
			<option value="body">내용</option>
			<option value="titleAndBody">제목+내용</option>
			
		</select>
		
		<script>
			if ( typeof param.searchType == 'undefined' ) {
				param.searchType = 'title';
			}
		
			$('form[name="searchForm"] select[name="searchType"]').val(param.searchType);
		</script>
		
		<input type="text" name="searchKeyword" placeholder="검색어를 입력해주세요." value="${param.searchKeyword}">
		
		<input type="submit" value="검색">
		
		</form>
	</div>
	
	
	<c:forEach items="${articles}" var = "article">
		<div>
			<a href="detail?id=${article.id }">번호 : ${article.id }</a>
			<br />
			작성자 : ${article.memberId}
			<br />
			생성날짜 : ${article.regDate }
			<br />
			갱신날짜 : ${article.updateDate }
			<br />
			제목 : ${article.title }
			<br />
			내용 : ${article.body }
			<br />
			
			<c:if test="${article.memberId == loginedMemberId}">
				<a onclick="if ( confirm('삭제하시겠습니까?') == false ) return false" href="doDelete?id=${article.id}">삭제</a>
			</c:if>
		</div>
		<hr />
	
	</c:forEach>

	<style>
		.selected{
			color : red;
		}
	</style>
	
	<c:set var = "goFirstBtnNeedToShow" value = "${page > pageMenuSize + 1 }"/>
	<c:set var = "goLastBtnNeedToShow" value = "true"/>
	
	<c:if test = "${goFirstBtnNeedToShow}">
		<a href="?page=1">◀◀</a>	
	</c:if>
	
	<c:forEach var = "i" begin = "${pageMenuStart}" end = "${pageMenuEnd }">
		<c:set var = "className" value = "${i == page ? 'selected' : ''}"/>
		<a class = "className" href="?page=${i}">${i}</a>
		
		<c:if test = "${ i == totalPage }">
			<c:set var = "goLastBtnNeedToShow" value = "false"/>
		</c:if>
	</c:forEach>
	
	<c:if test = "${goLastBtnNeedToShow}">
		<a href="?page=${totalPage }">▶▶</a>	
	</c:if>
	
	<%@ include file="../../part/foot.jspf"%> 