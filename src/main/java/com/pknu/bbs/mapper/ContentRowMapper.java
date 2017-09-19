package com.pknu.bbs.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.pknu.bbs.dto.BBSDto;

public class ContentRowMapper implements RowMapper<BBSDto> {
	BBSDto article=null;
	@Override
	public BBSDto mapRow(ResultSet rs, int arg1) throws SQLException {		
		article = new BBSDto();
		article.setArticleNum(rs.getInt("article_num"));
		article.setId(rs.getString("id"));
		article.setTitle(rs.getString("title"));
		article.setContent(rs.getString("content"));
		article.setDepth(rs.getInt("depth"));
		article.setHit(rs.getInt("hit"));
		article.setGroupId(rs.getInt("group_id"));
		article.setPos(rs.getInt("pos"));
		article.setWriteDate(rs.getTimestamp("write_date"));
		article.setFname(rs.getString("fname"));
		return article;		
	}
}
