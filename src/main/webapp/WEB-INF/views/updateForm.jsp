<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
<head>
<meta charset="utf-8">
<title>수정하기</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</head>
<body>
<div class="col-md-3 col-sm-2"></div>
<div class="col-md-6 col-sm-8">
<center>
	<form action="/bbs/update.bbs" method="post">
		<input type="hidden" name="articleNum" value="${article.articleNum}">
		<input type="hidden" name="pageNum" value="${pageNum}">
		<table border="2" class = "table table-bordered">
			<tr>
				<td>글쓴이 :</td>	<td>${id}</td>
			</tr>
			<tr>
				<td>제목 :</td>
				<td><input style="width: 100%" type="text" name="title" value="${article.title}"></td>
			</tr>
			<tr>
				<td colspan="2">
				<textarea style="width: 100%" rows="20" name="content">${article.content}</textarea>
				</td>
			</tr>
			<tr>
				<td><input type="submit" value="수정하기"></td>
				<td><input type="reset" value="수정하기취소"></td>
			</tr>
		</table>
	</form>
	</center>
	</div>
</body>
</html>












