package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import dto.StatusCount;
import dto.Transaction;

public class TransactionDAO {
	//사용할 객체를 미리 선언
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs;
		
	DBconnectDAO dbcon = new DBconnectDAO(); //데이터 베이스 사용
	
	
	//총거래 갯수
	public int getAllcount(String field, String search) {
		dbcon.getCon();
		
		int transaction_count = 0;
		try {
			String sql = "select count(*) from transaction";
			if(search != null && !search.equals("")) {
				sql = "select count(*) from transaction where "+field+" like '%"+search+"%'";				
			}
			
			pstmt = dbcon.conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				transaction_count = rs.getInt(1);
			}
			
			rs.close();
			pstmt.close();
			dbcon.conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return transaction_count;
	}//총 거래 갯수 끝
	
	//거래 목록
	public ArrayList<Transaction> getAlltransaction(int startRow,int endRow,String field,String search){
		dbcon.getCon();
		
		ArrayList<Transaction> list = new ArrayList<Transaction>();
		
		try {
			String sql = "select * from transaction order by deal_date desc limit ?,?";
			if(search != null && !search.equals("")) {
				sql = "select * from transaction where "+field+" like '%"+search+"%' order by deal_date desc limit ?,?";
			}
			
			pstmt = dbcon.conn.prepareStatement(sql);
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Transaction transation = new Transaction();
				
				transation.setTransactionId(rs.getInt("transaction_id"));
				transation.setSellerId(rs.getString("seller_id"));
				transation.setBuyerId(rs.getString("buyer_id"));
				transation.setItemId(rs.getInt("item_id"));
				transation.setDealDate(rs.getString("deal_date"));
				transation.setTransactionDate(rs.getString("transaction_date"));
				transation.setLocation(rs.getString("location"));
				transation.setStatus(rs.getString("status"));
				
				list.add(transation);
			}
			rs.close();
			pstmt.close();
			dbcon.conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}//거래 목록 끝
	
	//상태별 갯수
	public ArrayList<StatusCount> getCountByStatus() {
		dbcon.getCon();
		
		ArrayList<StatusCount> status_cnt = new ArrayList<>();
		try {
			String sql1 = "select count(*) from transaction where status = '취소'";
			PreparedStatement pstmt1 = dbcon.conn.prepareStatement(sql1);
			ResultSet rs1 = pstmt1.executeQuery();
			
			String sql2 = "select count(*) from transaction where status = '판매완료'";
			PreparedStatement pstmt2 = dbcon.conn.prepareStatement(sql2);
			ResultSet rs2 = pstmt2.executeQuery();
			
			String sql3 = "select count(*) from transaction where status = '예약중'";
			PreparedStatement pstmt3 = dbcon.conn.prepareStatement(sql3);
			ResultSet rs3 = pstmt3.executeQuery();
			
			if(rs1.next() && rs2.next() && rs3.next()) {
				StatusCount stct = new StatusCount();
				
				stct.setCancel_count(rs1.getInt(1));
				stct.setBooking_count(rs2.getInt(1));
				stct.setComplete_count(rs3.getInt(1));
				
				status_cnt.add(stct);
			}
			rs1.close();
			rs2.close();
			rs3.close();
			pstmt1.close();
			pstmt2.close();
			pstmt3.close();
			dbcon.conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status_cnt;
	}
}
