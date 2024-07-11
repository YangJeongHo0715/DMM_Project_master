package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import dto.Wishlist;

public class CartDAO {
	
	//사용할 객체를 미리 선언
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs;
				
	//오늘 날짜
	Date today = new Date();
	SimpleDateFormat cal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String signdate = cal.format(today);
		
	DBconnectDAO dbcon = new DBconnectDAO(); //데이터 베이스 사용
	
	//장바구니 안 총 상품 수
	public int getAllcount(int session_id) {
		dbcon.getCon();
		
		int count = 0;
		
		try {
			String sql = "select count(*) from wishlist where session_id = ?";
			
			pstmt =dbcon.conn.prepareStatement(sql);
			
			pstmt.setInt(1, session_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}// 총 갯수 끝
	
	
	//장바구니 리스트
	public ArrayList<Wishlist> getAllList(int session_id,int startRow, int endRow){
		dbcon.getCon();
		
		ArrayList<Wishlist> list = new ArrayList<Wishlist>();
		
		try {
			String sql = "select * from wishlist where session_id =? order by listing_date desc limit ?,?";
						
			pstmt = dbcon.conn.prepareStatement(sql);
			
			pstmt.setInt(1, session_id);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Wishlist wish = new Wishlist();

				
				wish.setSessionId(rs.getInt("session_id"));
				wish.setUserId(rs.getInt("user_id"));
				wish.setItemId(rs.getInt("item_id"));
				wish.setPrice(rs.getString("price"));
				wish.setListingdate(rs.getString("listing_date"));
				wish.setStatus(rs.getString("status"));
				wish.setSellerId(rs.getString("seller_id"));
				wish.setFile(rs.getString("file"));
				wish.setTitle(rs.getString("title"));
				
				list.add(wish);				
			}
			
			rs.close();
			pstmt.close();
			dbcon.conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}//장바구니 리스트 끝
	
	
	//장바구니 삭제
	public void deletOne(int item_id) {
		dbcon.getCon();
		
		try {
			String sql = "delete from wishlist where item_id=?";
			
			pstmt = dbcon.conn.prepareStatement(sql);
			pstmt.setInt(1, item_id);
			
			pstmt.executeUpdate();
			
			pstmt.close();
			dbcon.conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//장바구니 삭제 끝
	
	
	//장바구니 중복 확인
	public int ItemSelect(int session_id, int item_id) {
		dbcon.getCon();
		
		int num = 0;
		
		try {
			String sql = "select count(*) from wishlist where session_id = ? and item_id = ?";
			
			pstmt = dbcon.conn.prepareStatement(sql);
			
			pstmt.setInt(1, session_id);
			pstmt.setInt(2, item_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				num = rs.getInt(1);
			}
			
			rs.close();
			pstmt.close();
			dbcon.conn.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}//장바구니 중복 끝
	
	
	//장바구니 추가
	public void insertCart(Wishlist wish) {
		dbcon.getCon();
		
		try {
			String sql = "insert into wishlist (session_id,user_id,item_id,price,listing_date,status,seller_id,file,title) values (?,?,?,?,?,?,?,?,?)";
			
			pstmt = dbcon.conn.prepareStatement(sql);
						
			pstmt.setInt(1, wish.getSessionId());
			pstmt.setInt(2, wish.getUserId());
			pstmt.setInt(3, wish.getItemId());
			pstmt.setString(4, wish.getPrice());
			pstmt.setString(5, wish.getListingdate());
			pstmt.setString(6, wish.getStatus());
			pstmt.setString(7, wish.getSellerId());
			pstmt.setString(8, wish.getFile());
			pstmt.setString(9, wish.getTitle());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			dbcon.conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//장바구니 추가 끝
	
	
	//장바구니 한개의 물품 상세 정보
	public Wishlist oneWish(int itemId) {
		dbcon.getCon();
		
		Wishlist wish = new Wishlist();
		
		try {
			String sql = "select * from wishlist where item_id = ?";
			pstmt = dbcon.conn.prepareStatement(sql);
			
			pstmt.setInt(1, itemId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				wish.setUserId(rs.getInt("user_id"));
				wish.setItemId(rs.getInt("item_id"));
				wish.setPrice(rs.getString("price"));
				wish.setListingdate(rs.getString("listing_date"));
				wish.setStatus(rs.getString("status"));
				wish.setSellerId(rs.getString("seller_id"));
				wish.setFile(rs.getString("file"));
				wish.setTitle(rs.getString("title"));
			}
			
			rs.close();
			pstmt.close();
			dbcon.conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return wish;
	}// 한개의 물품 상세 정보 끝
	
	//장바구니 삭제
	public void deleteWish(int itemId) {
		dbcon.getCon();
		
		try {
			String sql = "delete from wishlist where item_id=?";
			
			pstmt = dbcon.conn.prepareStatement(sql);
			
			pstmt.setInt(1, itemId);
			
			pstmt.executeUpdate();
			
			pstmt.close();
			dbcon.conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}//장바구니 삭제 끝
}
