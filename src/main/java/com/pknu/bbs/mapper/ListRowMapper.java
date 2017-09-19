package com.pknu.bbs.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.pknu.bbs.dao.BBSDao;
import com.pknu.bbs.dto.BBSDto;

public class ListRowMapper implements RowMapper {

	BBSDto article=null;
	@Override
	public BBSDto mapRow(ResultSet rs, int arg1) throws SQLException {		
		article = new BBSDto();
		article.setArticleNum(rs.getInt("article_num"));
		article.setId(rs.getString("id"));
		article.setTitle(rs.getString("title"));			
		article.setDepth(rs.getInt("depth"));
		article.setHit(rs.getInt("hit"));			
		article.setWriteDate(rs.getTimestamp("write_date"));		
		return article;		
	}
	
}
