<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>답글쓰기</title>
</head>
<body> 
뎁스:${replyDto.depth }<br>
포지션:${replyDto.pos }<br>
페이지넘:${pageNum }
<form action="/bbs/reply.bbs" method="post">
	<input type="hidden" name="pageNum" value="${pageNum}">                 
    <input type="hidden" name="depth" value="${replyDto.depth}">
    <input type="hidden" name="pos" value="${replyDto.pos}">
    <input type="hidden" name="groupId" value="${replyDto.groupId}">
	<table border="2" width="200">  
		<tr>
 			 <td>글쓴이 :</td>
 			 <td>${id}</td>
 		</tr>
 		<tr>	 
		 <td>제목 : </td>
		 <td><input type="text" name="title" value="[Re]"></td>			 
		</tr>
		<tr>
		  <td colspan="2">
		  <textarea cols="50" rows="20" name="content" ></textarea>
		  </td>
	    </tr> 	    
	    <tr>
	      <td>첨부 : </td>
	      <td><input type="file"  name="fname" ></td>
	    </tr>
	    <tr>
	      <td><input type="submit" value="글쓰기"></td>
	      <td><input type="reset" value="글쓰기취소">&nbsp<input type="button" value="목록으로" onclick="document.location.href='/bbs/list.bbs?pageNum=${pageNum}'"></td>	
	            	 
	    </tr>		
	</table>	
</form>
</body>
</html>