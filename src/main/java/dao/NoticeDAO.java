package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import dto.Notice;

public class NoticeDAO {
	//사용할 객체를 미리 선언
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs;
					
	//오늘 날짜
	Date today = new Date();
	SimpleDateFormat cal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String signdate = cal.format(today);
		
	DBconnectDAO dbcon = new DBconnectDAO(); //데이터 베이스 사용
	
	//총 게시물 수
	public int getAllcount(String field,String search) {
		dbcon.getCon();
		
		int count = 0;
		try {
			String sql = "select count(*) from notice";
			if(search != null && !search.equals("")) {
				sql = "select count(*) from notice where "+field+" like '%"+search+"%'";
			}
			
			pstmt = dbcon.conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
			rs.close();
			pstmt.close();
			dbcon.conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return count;		
	}
	
	//게시물 목록
	public ArrayList<Notice> getAllNotice(int startRow,int endRow,String field,String search){
		dbcon.getCon();
		
		ArrayList<Notice> list = new ArrayList<Notice>();
		
		try {
			String sql = "select * from notice order by signdate desc limit ?,?";
			if(search != null && !search.equals("")) {
				sql = "select * from notice where "+field+" like '%"+search+"%' order by signdate desc limit ?,?";
			}
			
			pstmt = dbcon.conn.prepareStatement(sql);
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2 , endRow);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Notice notice = new Notice();
				
				notice.setUid(rs.getInt("uid"));
				notice.setSubject(rs.getString("subject"));
				notice.setContent(rs.getString("content"));
				notice.setSigndate(rs.getString("signdate"));
				notice.setRef(rs.getInt("ref"));
				notice.setFile(rs.getString("file"));
				notice.setWriter(rs.getString("writer"));
				
				list.add(notice);
			}
			rs.close();
			pstmt.close();
			dbcon.conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//한개의 게시글
	public Notice onePost(int uid) {
		dbcon.getCon();
		
		Notice notice = new Notice();
		
		try {
			String sql = "select * from notice where uid=? order by signdate desc";
			pstmt=dbcon.conn.prepareStatement(sql);
			
			pstmt.setInt(1, uid);
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				notice.setUid(rs.getInt("uid"));
				notice.setSubject(rs.getString("subject"));
				notice.setContent(rs.getString("content"));
				notice.setSigndate(rs.getString("signdate"));
				notice.setRef(rs.getInt("ref"));
				notice.setFile(rs.getString("file"));
				notice.setWriter(rs.getString("writer"));
			}
			rs.close();
			pstmt.close();
			dbcon.conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return notice;
	}//한개의 게시글 끝
	
	//게시글 삭제
	public void deletePost(int uid) {
		dbcon.getCon();
		
		try {
			String sql = "delete from notice where uid=?";
			
			pstmt = dbcon.conn.prepareStatement(sql);
			
			pstmt.setInt(1, uid);
			
			pstmt.executeUpdate();
			
			pstmt.close();
			dbcon.conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//게시글 삭제 끝
	
	//게시글 수정
	public void updatePost(Notice notice) {
		dbcon.getCon();
		
		try {
			String sql = "update notice set writer=?, content=?, subject=? where uid=?";
			pstmt = dbcon.conn.prepareStatement(sql);
			
			pstmt.setString(1, notice.getWriter());
			pstmt.setString(2, notice.getContent());
			pstmt.setString(3, notice.getSubject());
			pstmt.setInt(4, notice.getUid());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			dbcon.conn.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//게시글 수정 끝
	
	//게시글 추가
	public void insertPost(Notice notice){
		dbcon.getCon();
		
		try {
			String sql = "insert into notice (uid,subject,content,signdate,file,writer) value(?,?,?,?,?,?)";
			pstmt = dbcon.conn.prepareStatement(sql);
			
			pstmt.setInt(1, notice.getUid());
			pstmt.setString(2, notice.getSubject());
			pstmt.setString(3, notice.getContent());
			pstmt.setString(3, signdate);
			pstmt.setString(4, notice.getFile());
			pstmt.setString(5, notice.getWriter());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			dbcon.conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//게시글 추가 끝
}
