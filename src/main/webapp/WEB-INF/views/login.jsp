<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>
<meta charset="utf-8">
<title>로그인 하세요..^^</title>
</head>
<body>

	<form action="/bbs/login.bbs" method="post" id="loginForm">
		<input type="hidden" name="pageNum" value="${pageNum}">
		<label for="id"> I D : <input type="text" name="id" id="id"></label><br />
		<label for="pass">PASS : <input type="text" name="pass" id="pass"></label><br/><br/> 
		<input type="submit" value="로그인"> 
		<input type="reset" value="취소"> 
		<input type="button" value="회원가입" onclick="document.location.href='/bbs/joinForm.bbs'">
	</form>
</body>
</html>