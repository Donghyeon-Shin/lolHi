<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageName" value="메인페이지" />

<%@ include file="../../part/head.jspf"%>

<div>
	<a href="../article/list?boardCode=free">자유게시판으로 이동</a>
</div>

<div>
	<a href="../article/list?boardCode=notice">공지사항으로 이동</a>
</div>



<%@ include file="../../part/foot.jspf"%>
