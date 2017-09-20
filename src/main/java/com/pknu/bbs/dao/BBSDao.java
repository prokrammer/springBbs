package com.pknu.bbs.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;

import com.pknu.bbs.comment.CommentDto;
import com.pknu.bbs.dto.BBSDto;

public interface BBSDao {

	int getTotalCount();

	List<BBSDto> getArticleList(int startRow, int endRow) throws SQLException;
	
	BBSDto getContent(String articleNum) throws SQLException;
	
	void write(BBSDto article) throws ServletException, IOException;
	
	String join(String id, String pass) throws SQLException;
	
	List<CommentDto> getComments(String articleNum, String commentRow) throws SQLException;
	
	void writeContent(String id, String articleNum, String content);
	
	void delete(String articleNum) throws SQLException;
	
	BBSDto getUpdateArticle(String articleNum) throws SQLException;
	
	void getUpdateArticle(String articleNum, String title, String content) throws SQLException;
	
	void reply(BBSDto article) throws SQLException;

	int loginCheck(String id, String pass) throws SQLException;
	
}
