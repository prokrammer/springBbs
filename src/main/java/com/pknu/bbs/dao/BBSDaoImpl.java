package com.pknu.bbs.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.pknu.bbs.comment.CommentDto;
import com.pknu.bbs.dto.BBSDto;
import com.pknu.bbs.mapper.ContentRowMapper;
import com.pknu.bbs.mapper.ListRowMapper;


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
	HashMap paramMap;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	SqlMapClientTemplate smct;
	
	@Override
	public int getTotalCount() {
//		return (Integer)smct.queryForObject("getArticleCount");
		return (int)smct.queryForObject("getArticleCount");
	}
	
	public List<BBSDto> getArticleList(int startRow, int endRow){
		paramMap = new HashMap<>();
		paramMap.put("startRow", startRow);
		paramMap.put("endRow", endRow);
		return smct.queryForList("getArticleList", paramMap);/*jdbcTemplate.query(sql.toString(), new Object[]{startRow,endRow}, new ListRowMapper())*/
	}
	public int loginCheck(String id, String pass) throws SQLException {
		int loginStatus =0;
		String dbPass = (String)smct.queryForObject("login", id);
								
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
	public void write(BBSDto article) throws ServletException, IOException{
		smct.insert("write", article);
	}
	
	


	
	@Override
	public BBSDto getContent(String articleNum) {
		
		BBSDto article = (BBSDto)smct.queryForObject("getContent",articleNum);
		int comment=0;
		try {
			comment = commentsCount(Integer.parseInt(articleNum));
		} catch (NumberFormatException | SQLException e) {
			// TODO Auto-generated catch block
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
		
		smct.update("posUpdate", paramMap);

		System.out.println(article);
		smct.insert("reply", article);
		
	}
	
	@Override
	public void delete(String articleNum) throws SQLException{
		smct.delete("deleteArticle", articleNum);
	}

	public BBSDto getUpdateArticle(String articleNum) throws SQLException{
		return (BBSDto)smct.queryForObject("getUpdateArticle",articleNum);
	}

	public void getUpdateArticle(String articleNum, String title, String content) throws SQLException {
		System.out.println(articleNum + title + content);
		paramMap = new HashMap<>();
		paramMap.put("articleNum", articleNum);
		paramMap.put("title",title);
		paramMap.put("content", content);
		
		smct.update("updateArticle", paramMap);
		
	}

	public String join(String id, String pass) throws SQLException {
		String result;
		con = odbc.getConnection();
		String query = "select id from login where id = ?";
		pstmt = con.prepareStatement(query);
		pstmt.setString(1, id);
		rs = pstmt.executeQuery();
		if(rs.next()) {
			result = "중복된 아이디 입니다.";
			System.out.println(result);
		} else {
			StringBuffer sql = new StringBuffer();
			sql.append("insert into login values(?,?)");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			pstmt.setString(2, pass);
			int gab = pstmt.executeUpdate();
			result = "회원가입이 되었습니다.";
			System.out.println(gab + "행이 입력되었습니다.");
		}
		streamClose();
		return result;
	}
	
	public ArrayList<LoginDto> getAllLogin() throws IOException, SQLException {
		con = odbc.getConnection();
		String query = "select * from login";
		pstmt = con.prepareStatement(query);
		rs = pstmt.executeQuery();
		loginArrayList = new ArrayList<>();
		while(rs.next()) {
			logindto = new LoginDto();
			logindto.setId(rs.getString("id"));
			logindto.setPass(rs.getString("pass"));
			loginArrayList.add(logindto);
		}
		System.out.println(loginArrayList);
		return loginArrayList;
	}
	
	public void streamClose() throws SQLException {
		if(rs != null) {
			rs.close();
		}
		pstmt.close();
		con.close();
	}
	
	public int commentsCount(int articleNum) throws SQLException {
		con = odbc.getConnection();
		ResultSet rsc;
		String query = "select count(*) from comments where articlenum = ?";
		pstmt = con.prepareStatement(query);
		pstmt.setInt(1, articleNum);
		rsc = pstmt.executeQuery();
		int count = 0;
		if(rsc.next()) {
			count = rsc.getInt(1);
		}
		pstmt.close();
		rsc.close();
		con.close();
		return count;
	}

	public LoginDto getOneLogin(String id) throws IOException, SQLException{
		con = odbc.getConnection();
		String query = "select * from login where id = ?";
		pstmt = con.prepareStatement(query);
		pstmt.setString(1, id);
		rs = pstmt.executeQuery();
		LoginDto logindto1 = new LoginDto();
		if(rs.next()) {
			logindto1.setId(rs.getString("id"));
			logindto1.setPass(rs.getString("pass"));
		}
		if(logindto1.getId()==null) {
			logindto1.setId("해당ID없음");
			logindto1.setPass("해당ID없음");
		}

		System.out.println(logindto1);
		return logindto1;
	}

	public void writeContent(String id, String articleNum, String content) {
		con = odbc.getConnection();
		System.out.println("writeContent실행");
		String query = "insert into comments values(comment_seq.nextval,?,?,sysdate,?)";
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, content);
			pstmt.setString(3, articleNum);
			int result = pstmt.executeUpdate();
			System.out.println(result + "행 입력되었습니다.");
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<CommentDto> getComments(String articleNum, String commentRow) throws SQLException{
		con = odbc.getConnection();
		StringBuffer sql = new StringBuffer(); 
//		sql.append("select obbs.* ");
//		sql.append("from (select rownum rum, ibbs.* ");
//		sql.append("	  from (select article_num,id,title,depth,hit,write_date ");
//		sql.append("			from bbs ");
//		sql.append("			order by group_id desc, pos) ibbs ");
//		sql.append(") obbs ");
//		sql.append("where rum between ? and ? ");
		sql.append("select ocom.* ");
		sql.append("from (select rownum rum, icom.* ");
		sql.append("	  from (select * from comments) icom ");
		sql.append("	  where articleNum = ?");
		sql.append("	  order by rum) ocom ");
		sql.append("where rum between 1 and ?");
		
		/*sql.append("select * ");
		sql.append("from (select * ");
		sql.append("from comments ");
		sql.append("where articleNum=? )");
		sql.append("where rownum between 1 and ?");*/
		
		pstmt = con.prepareStatement(sql.toString());
		pstmt.setString(1, articleNum);
		pstmt.setString(2, commentRow);
		rs = pstmt.executeQuery();
		comArrayList = new ArrayList<>();
		
		while(rs.next()) {
			CommentDto cdto = new CommentDto();
			cdto.setCommentNum(rs.getInt("commentnum"));
			cdto.setId(rs.getString("id"));
			cdto.setCommentContent(rs.getString("commentcontent"));
			cdto.setCommentDate(rs.getString("commentdate"));
			cdto.setArticleNum(rs.getInt("articlenum"));
			comArrayList.add(cdto);
		}
		streamClose();		
		return comArrayList;
	}
}
