package com.pknu.bbs.comment.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pknu.bbs.comment.dto.CommentDto;

@Repository
public class CommentDaoImpl implements CommentDao {
	private String nameSpace="com.pknu.bbs.CommentMapper";
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public void insertComment(CommentDto comment) {
		sqlSession.insert(nameSpace + ".insertComment", comment); 
	}

	@Override
	public List<CommentDto> getComments(HashMap<String, Integer> commentMap) {
		return sqlSession.selectList(nameSpace + ".getComments", commentMap);
	}
	
	
	@Override
	public int commentsCount(int articleNum) {
		return sqlSession.selectOne(nameSpace+".commentsCount", articleNum);
	}
		/*
	@Override
	public void writeContent(HashMap<Object, Object> paramMap) {
		seqSession.insert(commentSpace+".writeContent",paramMap);
	}
	@Override
	public List<CommentDto> getComments(HashMap<Object,Object> paramMap) throws SQLException{
		return seqSession.selectList(commentSpace+".getComments", paramMap);
	}*/
}
