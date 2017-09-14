<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	${pageNum}페이지
<form action="/bbs/join.bbs" method="post" id="join">
<input type="hidden" name="pageNum" value="${pageNum}">
<table>
	<tr><td><label for="id">I D : </td><td><input type="text" name="id" id="id"></label></td></tr>
	<tr><td><label for="pass">PASS : </td><td><input type="text" name="pass" id="pass"></label></td></tr>
</table>
	<input type="submit" value="회원가입">
	<input type="reset" value="취소">
	<input type="button" value="목록으로" onclick="document.location.href='/bbs/list.bbs?pageNum=<%-- ${pageNum} --%>1'">
	</form>
</body>
</html>