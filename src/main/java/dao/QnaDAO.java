package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import dto.QnA;

public class QnaDAO {
	//사용할 객체를 미리 선언
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs;
						
	//오늘 날짜
	Date today = new Date();
	SimpleDateFormat cal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String signdate = cal.format(today);
			
	DBconnectDAO dbcon = new DBconnectDAO(); //데이터 베이스 사용
	
	
	//부모글 리스트
	public ArrayList<QnA> getQcoment(int startRow, int endRow) {
		dbcon.getCon();
		
		ArrayList<QnA> list_1 = new ArrayList<QnA>();
		
		try {
			String sql = "SELECT * FROM qna where re_step = 0 ORDER BY ref ASC limit ?,?";
			
			pstmt = dbcon.conn.prepareStatement(sql);
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2 , endRow);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				QnA qna = new QnA();
				
				qna.setNum(rs.getInt("num"));
				qna.setSubject(rs.getString("subject"));
				qna.setRegdate(rs.getString("reg_date"));
				qna.setRef(rs.getInt("ref"));
				qna.setRestep(rs.getInt("re_step"));
				qna.setRelevel(rs.getInt("re_level"));
				qna.setContent(rs.getString("content"));
				qna.setStatus(rs.getInt("status"));
				qna.setWriter(rs.getString("writer"));
				qna.setFile(rs.getString("file"));
				
				list_1.add(qna);
			}
			rs.close();
			pstmt.close();
			dbcon.conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list_1;
	}//부모글 리스트 끝
	
	
	//자식글 리스트
	public ArrayList<QnA> getAcoment(int startRow2, int endRow2) {
		dbcon.getCon();
		
		ArrayList<QnA> list_2 = new ArrayList<QnA>();
		
		try {
			String sql = "SELECT * FROM qna where re_step != 0 ORDER BY ref ASC, re_step ASC, re_level ASC limit ?,?";
			
			pstmt = dbcon.conn.prepareStatement(sql);
			
			pstmt.setInt(1, startRow2);
			pstmt.setInt(2 , endRow2);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				QnA qna = new QnA();
				
				qna.setNum(rs.getInt("num"));
				qna.setSubject(rs.getString("subject"));
				qna.setRegdate(rs.getString("reg_date"));
				qna.setRef(rs.getInt("ref"));
				qna.setRestep(rs.getInt("re_step"));
				qna.setRelevel(rs.getInt("re_level"));
				qna.setContent(rs.getString("content"));
				qna.setStatus(rs.getInt("status"));
				qna.setWriter(rs.getString("writer"));
				qna.setFile(rs.getString("file"));
				
				list_2.add(qna);
			}
			rs.close();
			pstmt.close();
			dbcon.conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list_2;
	}//자식글 리스트 끝
	
	//부모글 총 갯수
	public int getAllQcount() {
		dbcon.getCon();
		
		int Qcount = 0;
		try {
			String sql = "select count(*) from qna where re_step = 0";
			
			pstmt = dbcon.conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Qcount = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
			dbcon.conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Qcount;
	}//부모글 총 갯수 끝
	
	//자식글 총 객수
	public int getAllAcount(){
		dbcon.getCon();
		
		int Acount = 0;
		try {
			String sql = "select count(*) from qna where re_step != 0";
			
			pstmt = dbcon.conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Acount = rs.getInt(1);				
			}
			rs.close();
			pstmt.close();
			dbcon.conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Acount;
	}//자식글 총 갯수 끝

	
	//게시글 상세보기
	public QnA onePost(int num) {
		dbcon.getCon();
		
		QnA qna = new QnA();
		try {
			String sql = "select * from qna where num = ?";
			pstmt = dbcon.conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				qna.setNum(rs.getInt("num"));
				qna.setSubject(rs.getString("subject"));
				qna.setRegdate(rs.getString("reg_date"));
				qna.setRef(rs.getInt("ref"));
				qna.setRestep(rs.getInt("re_step"));
				qna.setRelevel(rs.getInt("re_level"));
				qna.setContent(rs.getString("content"));
				qna.setStatus(rs.getInt("status"));
				qna.setWriter(rs.getString("writer"));
				qna.setFile(rs.getString("file"));				
			}
			rs.close();
			pstmt.close();
			dbcon.conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return qna;
	}
}
