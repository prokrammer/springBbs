package com.pknu.bbs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pknu.bbs.comment.CommentDto;
import com.pknu.bbs.dto.BBSDto;


@Repository
public class BBSDaoImpl implements BBSDao {
	OracleDBConnector odbc = OracleDBConnector.getInstance();
	PreparedStatement pstmt;
	Connection con;
	ResultSet rs;
	ArrayList<BBSDto> bdtoArrayList;
	ArrayList<LoginDto> loginArrayList;
	LoginDto logindto;
	ArrayList<CommentDto> comArrayList;
	StringBuffer query;
	HashMap<Object,Object> paramMap;
	
	@Autowired
	SqlSessionTemplate seqSession;
	
	@Override
	public int getTotalCount() {
		return seqSession.selectOne("getTotalCount");
	}
	
	@Override
	public List<BBSDto> getArticleList(int startRow, int endRow){
		paramMap = new HashMap<>();
		paramMap.put("startRow", startRow);
		paramMap.put("endRow", endRow);
		List<BBSDto> list = seqSession.selectList("getArticleList", paramMap);/*jdbcTemplate.query(sql.toString(), new Object[]{startRow,endRow}, new ListRowMapper())*/
		for(BBSDto bbsdto:list) {
			bbsdto.setCommentCount((long)commentsCount(bbsdto.getArticleNum()));
		}
		return list;
	}
	
	@Override
	public int loginCheck(String id, String pass) {
		int loginStatus =0;
		String dbPass = (String)seqSession.selectOne("login", id);
								
		if(dbPass!=null){
			if(pass.equals(dbPass)){
				loginStatus=LoginStatus.LOGIN_SUCCESS;				
			}else{
				loginStatus=LoginStatus.LOGIN_FAIL;
			}			
		}else{
			loginStatus=LoginStatus.LOGIN_NOTFOUNDID;
		}		
			
		return loginStatus;
	}
	public void write(BBSDto article) {
		seqSession.insert("write", article);
	}
	
	@Override
	public BBSDto getContent(String articleNum) {
		
		BBSDto article = (BBSDto)seqSession.selectOne("getContent",articleNum);
		int comment=0;
		try {
			comment = commentsCount(Integer.parseInt(articleNum));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		long x = (long)comment;
		article.setCommentCount(x);
		return article;
	}	
	
	@Override
	public void reply(BBSDto article) {
		paramMap.put("pos", article.getPos());
		paramMap.put("groupId", article.getGroupId());
		
		seqSession.update("posUpdate", paramMap);

		System.out.println(article);
		seqSession.insert("reply", article);
		
	}
	
	@Override
	public void delete(String articleNum) {
		seqSession.delete("deleteArticle", articleNum);
	}
	
	@Override
	public BBSDto getUpdateArticle(String articleNum) {
		return (BBSDto)seqSession.selectOne("getUpdateArticle",articleNum);
	}
	
	@Override
	public void getUpdateArticle(String articleNum, String title, String content) {
		System.out.println(articleNum + title + content);
		paramMap = new HashMap<>();
		paramMap.put("articleNum", articleNum);
		paramMap.put("title",title);
		paramMap.put("content", content);
		
		seqSession.update("updateArticle", paramMap);
		
	}
	
	@Override
	public String join(String id, String pass) {
		String result;
		result = seqSession.selectOne("joinCheck",id);
		if(result!=null) {
			result="중복된 아이디 입니다.";
		} else {
			paramMap = new HashMap<>();
			paramMap.put("id", id);
			paramMap.put("pass", pass);
			
			seqSession.insert("join", paramMap);
			result = "회원가입이 되었습니다.";
		}
		return result;

	}
	
	public int commentsCount(int articleNum) {
		return seqSession.selectOne("commentsCount", articleNum);
	}

	


	public void writeContent(String id, String articleNum, String content) {
		paramMap = new HashMap<>();
		paramMap.put("id", id);
		paramMap.put("content", content);
		paramMap.put("articleNum", articleNum);
		
		seqSession.insert("writeContent",paramMap);
	}
	public List<CommentDto> getComments(String articleNum, String commentRow) throws SQLException{

		paramMap = new HashMap<>();
		paramMap.put("articleNum", articleNum);
		paramMap.put("commentRow", commentRow);
		return seqSession.selectList("getComments", paramMap);
	}
}
