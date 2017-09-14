package com.pknu.bbs.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;

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
	
/*	public ArrayList<BBSDto> list() throws SQLException {
		con = odbc.getConnection();
		StringBuffer sql = new StringBuffer(); 
		sql.append("select obbs.* from bbs");
		
		
		pstmt = con.prepareStatement(sql.toString());
		pstmt.setInt(1, 1);
		pstmt.setInt(2, 1);
		rs = pstmt.executeQuery();
		bdtoArrayList = new ArrayList<>();
//		System.out.println(rs);
		
		while(rs.next()) {
			BBSDto bbsd = new BBSDto();
			bbsd.setArticleNum(rs.getInt("article_num"));
			bbsd.setId(rs.getString("id"));
			bbsd.setTitle(rs.getString("title"));
			bbsd.setContent(rs.getString("content"));
			bbsd.setDepth(rs.getInt("depth"));
			bbsd.setHit(rs.getInt("hit"));
			bbsd.setGroupid(rs.getInt("group_id"));
			bbsd.setPos(rs.getInt("pos"));
			bbsd.setWriteDate(rs.getTimestamp("write_date"));
			bbsd.setFname(rs.getString("fname"));
			bdtoArrayList.add(bbsd);
//			System.out.println(bbsd);
		}
//		System.out.println(bdtoArrayList);
		rs.close();
		pstmt.close();
		return bdtoArrayList;
	}*/
	public ArrayList<BBSDto> getArticleList(int startRow, int endRow) throws SQLException {
		con = odbc.getConnection();
		StringBuffer sql = new StringBuffer(); 
		sql.append("select obbs.* ");
		sql.append("from (select rownum rum, ibbs.* ");
		sql.append("	  from (select article_num,id,title,depth,hit,write_date ");
		sql.append("			from bbs ");
		sql.append("			order by group_id desc, pos) ibbs ");
		sql.append(") obbs ");
		sql.append("where rum between ? and ? ");
		
		pstmt = con.prepareStatement(sql.toString());
		pstmt.setInt(1, startRow);
		pstmt.setInt(2, endRow);
		rs = pstmt.executeQuery();
		bdtoArrayList = new ArrayList<>();
//		System.out.println(rs);
		
		while(rs.next()) {
			BBSDto bbsd = new BBSDto();
			bbsd.setArticleNum(rs.getInt("article_num"));
			bbsd.setId(rs.getString("id"));
			bbsd.setTitle(rs.getString("title"));
			bbsd.setDepth(rs.getInt("depth"));
			bbsd.setHit(rs.getInt("hit"));
			bbsd.setWriteDate(rs.getTimestamp("write_date"));
			bdtoArrayList.add(bbsd);
//			bbsd.setContent(rs.getString("content"));
//			bbsd.setGroupid(rs.getInt("group_id"));
//			bbsd.setPos(rs.getInt("pos"));
//			bbsd.setFname(rs.getString("fname"));
//			System.out.println(bbsd);
			bbsd.setCommentCount(commentsCount(bbsd.getArticleNum()));
		}
//		System.out.println(bdtoArrayList);
		streamClose();		
		return bdtoArrayList;
	}
	public void write(BBSDto article) throws ServletException, IOException{
		con = odbc.getConnection();
		
		BBSDto bsd = article; 
		String sql = "insert into bbs values(ARTICLE_NUM_SEQUENCE.NEXTVAL,?,?,?,0,0,ARTICLE_NUM_SEQUENCE.CURRVAL,0,sysdate,?)";
		int result;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bsd.getId());
			pstmt.setString(2, bsd.getTitle());
			pstmt.setString(3, bsd.getContent());
			pstmt.setString(4, bsd.getFname());
			
			result = pstmt.executeUpdate();
			System.out.println(result + "���� �ԷµǾ����ϴ�.");
			streamClose();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int getTotalCount() throws SQLException {
		con = odbc.getConnection();
		String sql = "SELECT COUNT(*) FROM BBS";
		pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		int totalCount = 0;
		
		if(rs.next()) {
			totalCount = rs.getInt(1);
		}
		streamClose();
		return totalCount;
	}

	public int loginCheck(String id, String pass) throws SQLException {
		con = odbc.getConnection();
		String sql = "SELECT pass FROM login WHERE id = ? ";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		rs = pstmt.executeQuery();
		int result = 0;
		if(rs.next()) {
			if(pass.equals(rs.getString("pass"))) {
					result = LoginStatus.LOGIN_SUCCESS;
			} else {
				result = LoginStatus.LOGIN_FAIL;
			}
		} else {
			result = LoginStatus.LOGIN_NOTFOUNDID;
		}
		streamClose();
		return result;
	}

	public BBSDto getContent(String articleNum) throws SQLException{
		con = odbc.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM bbs WHERE article_num = ?");
		pstmt = con.prepareStatement(sql.toString());
		pstmt.setString(1, articleNum);
		rs = pstmt.executeQuery();
		BBSDto bbsd = new BBSDto();
		if(rs.next()) {
			bbsd.setArticleNum(rs.getInt("article_num"));
			bbsd.setId(rs.getString("id"));
			bbsd.setTitle(rs.getString("title"));
			bbsd.setContent(rs.getString("content"));
			bbsd.setDepth(rs.getInt("depth"));
			bbsd.setHit(rs.getInt("hit"));
			bbsd.setGroupId(rs.getInt("group_id"));
			bbsd.setPos(rs.getInt("pos"));
			bbsd.setWriteDate(rs.getTimestamp("write_date"));
			bbsd.setFname(rs.getString("fname"));
//			System.out.println(bbsd);
		}
		
		
		sql = new StringBuffer();
		sql.append("SELECT COUNT(*) ");
		sql.append("from comments ");
		sql.append("where articlenum=?");
		pstmt=con.prepareStatement(sql.toString());
		pstmt.setString(1, articleNum);
		
		rs = pstmt.executeQuery();
		if(rs.next()) {
			bbsd.setCommentCount(rs.getInt(1));
		}
		
		
		streamClose();
		return bbsd;
	}

	public void reply(BBSDto article) throws SQLException{
con = odbc.getConnection();

		BBSDto bsd = article;
		int result;
		
		StringBuffer query = new StringBuffer();
		
		query.append("update bbs ");
		query.append("set pos = pos + 1 ");
		query.append("where pos > ? and group_id = ?");
		pstmt = con.prepareStatement(query.toString());
		pstmt.setInt(1, bsd.getPos());
		pstmt.setInt(2, bsd.getGroupId());
		result = pstmt.executeUpdate();
		
		System.out.println(result + "���� �ԷµǾ����ϴ�.");
		
		String sql = "insert into bbs values(ARTICLE_NUM_SEQUENCE.NEXTVAL,?,?,?,? + 1,0,?,? + 1,sysdate,?)";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, bsd.getId());
		pstmt.setString(2, bsd.getTitle());
		pstmt.setString(3, bsd.getContent());
		pstmt.setInt(4, bsd.getDepth());
		pstmt.setInt(5, bsd.getGroupId());
		pstmt.setInt(6, bsd.getPos());
		pstmt.setString(7, bsd.getFname());
			
		result = pstmt.executeUpdate();
			
		System.out.println(result + "���� �ԷµǾ����ϴ�.");
		
		streamClose();
	}

	public void delete(String articleNum) throws SQLException{
		con = odbc.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("delete from bbs where article_num = ?");
		
		pstmt = con.prepareStatement(sql.toString());
		pstmt.setString(1, articleNum);
		
		int result = pstmt.executeUpdate();
		System.out.println(result + "���� �����Ǿ����ϴ�.");
		streamClose();
	}

	public BBSDto getUpdateArticle(String articleNum) throws SQLException{
		con = odbc.getConnection();
		StringBuffer query;
		query = new StringBuffer();
		query.append("SELECT TITLE, CONTENT FROM BBS WHERE ARTICLE_NUM=?");
		pstmt=con.prepareStatement(query.toString());
		pstmt.setString(1, articleNum);
		rs=pstmt.executeQuery();
		BBSDto article = new BBSDto();
		if(rs.next()){
			article= new BBSDto();		
			article.setArticleNum(Integer.parseInt(articleNum));
			article.setTitle(rs.getString("title"));
			article.setContent(rs.getString("content"));				
		}
		streamClose();
		return article;
	}

	public void getUpdateArticle(String articleNum, String title, String content) throws SQLException {
		con = odbc.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("update bbs ");
		sql.append("set title = ?, content = ? ");
		sql.append("where article_Num = ?");
		pstmt = con.prepareStatement(sql.toString());
		pstmt.setString(1, title);
		pstmt.setString(2, content);
		pstmt.setString(3, articleNum);
		pstmt.executeUpdate();
		streamClose();
	}

	public String join(String id, String pass) throws SQLException {
		String result;
		con = odbc.getConnection();
		String query = "select id from login where id = ?";
		pstmt = con.prepareStatement(query);
		pstmt.setString(1, id);
		rs = pstmt.executeQuery();
		if(rs.next()) {
			result = "�ߺ��� ���̵� �Դϴ�.";
			System.out.println(result);
		} else {
			StringBuffer sql = new StringBuffer();
			sql.append("insert into login values(?,?)");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			pstmt.setString(2, pass);
			int gab = pstmt.executeUpdate();
			result = "ȸ�������� �Ǿ����ϴ�.";
			System.out.println(gab + "���� �ԷµǾ����ϴ�.");
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
			logindto1.setId("�ش�ID����");
			logindto1.setPass("�ش�ID����");
		}

		System.out.println(logindto1);
		return logindto1;
	}

	public void writeContent(String id, String articleNum, String content) {
		con = odbc.getConnection();
		System.out.println("writeContent����");
		String query = "insert into comments values(comment_seq.nextval,?,?,sysdate,?)";
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, content);
			pstmt.setString(3, articleNum);
			int result = pstmt.executeUpdate();
			System.out.println(result + "�� �ԷµǾ����ϴ�.");
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
