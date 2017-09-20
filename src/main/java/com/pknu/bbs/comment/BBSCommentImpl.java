package com.pknu.bbs.comment;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pknu.bbs.dao.BBSDao;

@Service
public class BBSCommentImpl implements BBSComment{
	
	@Autowired
	BBSDao bbsdao;
	
	public void read(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	String articleNum = req.getParameter("articleNum");
	String commentRow = req.getParameter("commentRow");
	List<CommentDto> commentList = null;
	try {
		commentList = bbsdao.getComments(articleNum,commentRow);
	} catch (Exception e) {
		e.printStackTrace();
	}
	resp.setCharacterEncoding("utf-8");
	JSONArray jb = new JSONArray(commentList);
	PrintWriter pw = resp.getWriter();
	pw.println(jb.toString());
	
	}
	
	public void write(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String content = req.getParameter("commentContent");
		String articleNum = req.getParameter("articleNum");
		String id = (String) req.getSession().getAttribute("id");
		System.out.println("라이트임플");
		bbsdao.writeContent(id, articleNum, content);
		List<CommentDto> commentList = null;
		try {
			commentList = bbsdao.getComments(articleNum, "10");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		resp.setCharacterEncoding("utf-8");
		JSONArray jb = new JSONArray(commentList);
		PrintWriter pw = resp.getWriter();
		pw.println(jb.toString());
		
	}
}
