<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${article.id }번게시물수정</title>
</head>
<body>
	<h1>${article.id }번게시물수정</h1>

	<form action="./doModify" method="POST">


		<input type="hidden" name="id" value="${article.id}" />
		<table>

			<tbody>

				<tr>

					<th>번호</th>
					<td>${article.id}</td>
				</tr>

				<tr>
					<th>제목</th>
					<td><input type="text" name="title" placeholder="제목을 입력해주세요."
						value="${article.title}" /></td>
				</tr>

				<tr>
					<th>내용</th>
					<td><textarea name="body" placeholder="내용을 입력해주세요.">${article.body}</textarea></td>
				</tr>

				<tr>
					<th>수정</th>
					<td><input type="submit" value="수정"></td>
				</tr>
			</tbody>

		</table>
	</form>

</body>
</html>