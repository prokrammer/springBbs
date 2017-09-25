package com.pknu.bbs.bbs.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pknu.bbs.bbs.dto.BBSDto;
import com.pknu.bbs.comment.CommentDto;


@Repository
public class BBSDaoImpl implements BBSDao {
	ArrayList<BBSDto> bdtoArrayList;
	ArrayList<LoginDto> loginArrayList;
	LoginDto logindto;
	ArrayList<CommentDto> comArrayList;
	StringBuffer query;
	HashMap<Object,Object> paramMap;
	
	@Autowired
	private SqlSessionTemplate seqSession;
	private String nameSpace="com.pknu.bbs.dao.BBSDao";
	private String commentSpace="com.pknu.bbs.CommentMapper";
	
	@Override
	public int getTotalCount() {
		return seqSession.selectOne(nameSpace + ".getTotalCount");
	}
	
	@Override
	public List<BBSDto> getArticleList(HashMap<Object,Object> paramMap){
		return seqSession.selectList(nameSpace + ".getArticleList", paramMap);
	}
	
	@Override
	public String loginCheck(String id) {
		return (String)seqSession.selectOne(nameSpace + ".login", id);
	}
	
	@Override
	public void write(BBSDto article) {
		seqSession.insert(nameSpace + ".write", article);
	}
	
	@Override
	public BBSDto getContent(String articleNum) {
		return (BBSDto)seqSession.selectOne(nameSpace + ".getContent",articleNum);
	}	
	
	@Override
	public void reply(HashMap<Object,Object> paramMap) {
		seqSession.update(nameSpace + ".posUpdate", paramMap);
	}
	
	@Override
	public void reply(BBSDto article) {
		seqSession.insert(nameSpace + ".reply", article);
	}
	
	@Override
	public void delete(String articleNum) {
		seqSession.delete(nameSpace + ".deleteArticle", articleNum);
	}
	
	@Override
	public BBSDto getUpdateArticle(String articleNum) {
		return (BBSDto)seqSession.selectOne(nameSpace + ".getUpdateArticle",articleNum);
	}
	
	@Override
	public void getUpdateArticle(HashMap<Object,Object> paramMap) {
		seqSession.update(nameSpace + ".updateArticle", paramMap);
	}
	
	@Override
	public String joinCheck(String id) {
		return seqSession.selectOne(nameSpace + ".joinCheck",id);
	}
	
	@Override
	public void join(HashMap<Object, Object> paramMap) {
			seqSession.insert(nameSpace + ".join", paramMap);
	}
	
	@Override
	public int commentsCount(int articleNum) {
		return seqSession.selectOne(commentSpace+".commentsCount", articleNum);
	}
	
	@Override
	public void writeContent(HashMap<Object, Object> paramMap) {
		seqSession.insert(commentSpace+".writeContent",paramMap);
	}
	@Override
	public List<CommentDto> getComments(HashMap<Object,Object> paramMap) throws SQLException{
		return seqSession.selectList(commentSpace+".getComments", paramMap);
	}
}
