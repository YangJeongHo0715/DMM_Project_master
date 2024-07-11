package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import dto.Item;
import dto.Transaction;

public class TranDAO {
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs;
	
	DBconnectDAO dbcon = new DBconnectDAO();
	
	//트랜젝션 중복 방지
	public int selectCountTran(String buyer, int item_id) {
		dbcon.getCon();
		int count = 0;
		
		try {
			String sql = "select count(*) from transaction where buyer_id = ? and item_id =?";
			pstmt = dbcon.conn.prepareStatement(sql);
			
			pstmt.setString(1, buyer);
			pstmt.setInt(2, item_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}//트랜젝션 중복 방지 끝
	
	
	//거래 트랜젝션 신규등록
	public void insertTran(Transaction tran, String buyer, int item_id){
		dbcon.getCon();
			try {
				String sql = "insert into transaction (seller_id, buyer_id, item_id, deal_date, transaction_date, location, status) values(?, ?, ?, ?, ?, ?, ?)";
				
				pstmt = dbcon.conn.prepareStatement(sql);
				
				pstmt.setString(1, tran.getSellerId());
				pstmt.setString(2, tran.getBuyerId());
				pstmt.setInt(3, tran.getItemId());
				pstmt.setString(4, tran.getDealDate());
				pstmt.setString(5, tran.getTransactionDate());
				pstmt.setString(6,  tran.getLocation());
				pstmt.setString(7, tran.getStatus());
				
				pstmt.executeUpdate();
				
				// 아이템 테이블에 상태 예약중으로 변경
				if(tran.getStatus().equals("예약중")) {
					String sql2 = "update item set status = ? where item_id =?";
					pstmt = dbcon.conn.prepareStatement(sql2);
					
					pstmt.setString(1, tran.getStatus());
					pstmt.setInt(2, tran.getItemId());
					
					pstmt.executeUpdate();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}	
	}//거래 트랜젝션 신규등록 끝
	
	
	// 트랜젝션 업데이트
	public void updateTran(Transaction tran, int item_id, String buyer) {
		dbcon.getCon();
		try {
			String sql = "update transaction set transaction_date = ?, status = ? where item_id = ? and buyer_id = ?";
			pstmt = dbcon.conn.prepareStatement(sql);
			
			pstmt.setString(1, tran.getTransactionDate());
			pstmt.setString(2, tran.getStatus());
			pstmt.setInt(3, item_id);
			pstmt.setString(4, buyer);
			
			pstmt.executeUpdate();
			
			// 아이템 테이블에 상태 거래완료로 변경
			if(tran.getStatus().equals("판매완료")) {
				String sql2 = "update item set status = ?, buyer_id = ? where item_id =?";
				pstmt = dbcon.conn.prepareStatement(sql2);
				
				pstmt.setString(1, tran.getStatus());
				pstmt.setString(2, buyer);
				pstmt.setInt(3, item_id);
				
				pstmt.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}// 트랜젝션 업데이트 끝
	
	
	// 트랜젝션 취소
	public void cancelTran(Transaction tran, int item_id, String buyer) {
		dbcon.getCon();
		try {
			String sql = "update transaction set transaction_date = ?, status = ? where item_id = ? and buyer_id = ?";
			pstmt = dbcon.conn.prepareStatement(sql);
			
			pstmt.setString(1, tran.getTransactionDate());
			pstmt.setString(2, tran.getStatus());
			pstmt.setInt(3, item_id);
			pstmt.setString(4, buyer);
			
			pstmt.executeUpdate();
			
			// 아이템 테이블에 상태 거래완료로 변경
			if(tran.getStatus().equals("취소")) {
				String sql2 = "update item set status = ? where item_id =?";
				pstmt = dbcon.conn.prepareStatement(sql2);
				
				pstmt.setString(1, "판매중");
				pstmt.setInt(2, item_id);
				
				pstmt.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}// 트랜젝션 취소 끝
	
	
	// 트랜젝션 예약상태 업데이트
	public void reserveTran(Transaction tran, int item_id, String buyer) {
		dbcon.getCon();
		try {
			String sql = "update transaction set transaction_date = ?, status = ? where item_id = ? and buyer_id = ?";
			pstmt = dbcon.conn.prepareStatement(sql);
			
			pstmt.setString(1, tran.getTransactionDate());
			pstmt.setString(2, tran.getStatus());
			pstmt.setInt(3, item_id);
			pstmt.setString(4, buyer);
			
			pstmt.executeUpdate();
			
			// 아이템 테이블에 상태 예약중으로 변경
			if(tran.getStatus().equals("예약중")) {
				String sql2 = "update item set status = ?, buyer_id = ? where item_id =?";
				pstmt = dbcon.conn.prepareStatement(sql2);
				
				pstmt.setString(1, tran.getStatus());
				pstmt.setString(2, buyer);
				pstmt.setInt(3, item_id);
				
				pstmt.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}// 트랜젝션 업데이트 끝
	
	
	//한개의 트랜젝션 정보
	public Transaction oneTran(String buyer_id, int item_id) {
		dbcon.getCon();
		
		Transaction tran = new Transaction();
		
		try {
			String sql = "select * from transaction where buyer_id = ? and item_id = ?";
			pstmt = dbcon.conn.prepareStatement(sql);
			
			pstmt.setString(1, buyer_id);
			pstmt.setInt(2, item_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				tran.setTransactionId(rs.getInt("transaction_id"));
				tran.setSellerId(rs.getString("seller_id"));
				tran.setBuyerId(rs.getString("buyer_id"));
				tran.setItemId(rs.getInt("item_id"));
				tran.setDealDate(rs.getString("deal_date"));
				tran.setTransactionDate(rs.getString("transaction_date"));
				tran.setLocation(rs.getString("location"));
				tran.setStatus(rs.getString("status"));
			}
			
			rs.close();
			pstmt.close();
			dbcon.conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return tran;
	}//한개의 트랜젝션 정보 끝

}
