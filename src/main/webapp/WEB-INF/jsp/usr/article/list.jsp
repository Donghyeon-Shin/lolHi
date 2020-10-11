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
	
	<c:forEach items="${articles}" var = "article">
		<div>
			<a href="detail?id=${article.id }">번호 : ${article.id }</a>
			<br />
			생성날짜 : ${article.regDate }
			<br />
			갱신날짜 : ${article.updateDate }
			<br />
			제목 : ${article.title }
			<br />
			내용 : ${article.body }
			<br />
		</div>
		<hr />
	
	</c:forEach>


	<div class="page-menu text-align-center margin-top-30">
	
		<a href="list?page=1">◀◀</a>
	
		<c:forEach begin="1" end="${totalPage}" var="currentPage">
		
			<c:forEach  begin="1" end = "4" var = "number">
				
				<c:if test="${currentPage == page}">
					
					<c:if test="${currentPage - 5 + number >= 1}">
					
						<a href="list?page=${currentPage - 5 + number}">${currentPage - 5 + number}</a>
						
					</c:if>
					
					
				</c:if>
				
			</c:forEach>
			
			<c:if test="${currentPage == page}">
					
					<a href="list?page=${currentPage}">${currentPage}</a>
					
			</c:if>
		
			<c:forEach  begin="1" end = "5" var = "number">
				
				<c:if test="${currentPage == page}">
					
					<c:if test="${currentPage + number <= totalPage}">
					
						<a href="list?page=${currentPage + number}">${currentPage + number}</a>
					
					</c:if>
					
				</c:if>
				
			</c:forEach>
			

			
		</c:forEach>
					
				
					
		<a href="list?page=${totalPage}">▶▶</a>
	
	</div>

	
	<%@ include file="../../part/foot.jspf"%> 