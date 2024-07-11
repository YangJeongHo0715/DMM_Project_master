package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import dto.Category;
import dto.Item;

public class ItemDAO {
	//사용할 객체를 미리 선언
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs;
				
	//오늘 날짜
	Date today = new Date();
	SimpleDateFormat cal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String signdate = cal.format(today);
		
	DBconnectDAO dbcon = new DBconnectDAO(); //데이터 베이스 사용
	
	
	//총 상품 수
	public int getAllcount(String field,String search) {
		dbcon.getCon();
		
		int item_count = 0;
		
		try {
			String sql = "select count(*) from item"; 
			if(search != null && !search.equals("")) {
				sql = "select count(*) from item where "+field+" like '%"+search+"%'";
			}
			
			pstmt = dbcon.conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				item_count = rs.getInt(1);
			}
			
			rs.close();
			pstmt.close();
			dbcon.conn.close();				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return item_count;
	}//총 상품 수 끝
	
	
	//상품 목록
	public ArrayList<Item> getAllItem(int startRow,int endRow,String field,String search) {
			dbcon.getCon();
					
			ArrayList<Item> list = new ArrayList<Item>();
				
			try {
				String sql = "select * from item order by listing_date desc limit ?,?";
				if(search != null && !search.equals("")) {
					sql = "select * from item where "+field+" like '%"+search+"%' order by listing_date desc limit ?,?";
				}
				
				pstmt = dbcon.conn.prepareStatement(sql);
					
				pstmt.setInt(1 , startRow);
				pstmt.setInt(2 , endRow);
					
				rs = pstmt.executeQuery();
					
				while(rs.next()) {
					Item item = new Item();
						
					item.setItemId(rs.getInt("item_id"));
					item.setSellerId(rs.getString("seller_id"));
					item.setCaId(rs.getString("ca_id"));
					item.setTitle(rs.getString("title"));
					item.setDescription(rs.getString("description"));
					item.setPrice(rs.getString("price"));
					item.setConditionItem(rs.getString("condition_item"));
					item.setListingDate(rs.getString("listing_date"));
					item.setStatus(rs.getString("status"));
					item.setFile1(rs.getString("file1"));
					item.setFile2(rs.getString("file2"));
					item.setFile3(rs.getString("file3"));
					item.setFile4(rs.getString("file4"));
					item.setFile5(rs.getString("file5"));
					item.setLatitude(rs.getString("latitude"));
					item.setLongitude(rs.getString("longitude"));
					item.setLocation(rs.getString("location"));
							
					list.add(item);						
				}		
				rs.close();
				pstmt.close();
				dbcon.conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		return list;	
	}//상품목록 끝
	
	
	//한개의 상품 정보
	public Item oneItem(int itemId) {
		dbcon.getCon();
		
		Item item = new Item();
		
		try {
			String sql = "select * from item where item_id=?";
			pstmt = dbcon.conn.prepareStatement(sql);
			
			pstmt.setInt(1, itemId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				item.setItemId(rs.getInt("item_id"));
				item.setSellerId(rs.getString("seller_id"));
				item.setCaId(rs.getString("ca_id"));
				item.setTitle(rs.getString("title"));
				item.setDescription(rs.getString("description"));
				item.setPrice(rs.getString("price"));
				item.setConditionItem(rs.getString("condition_item"));
				item.setListingDate(rs.getString("listing_date"));
				item.setStatus(rs.getString("status"));
				item.setFile1(rs.getString("file1"));
				item.setFile2(rs.getString("file2"));
				item.setFile3(rs.getString("file3"));
				item.setFile4(rs.getString("file4"));
				item.setFile5(rs.getString("file5"));
				item.setLatitude(rs.getString("latitude"));
				item.setLongitude(rs.getString("longitude"));
				item.setLocation(rs.getString("location"));
			}
			
			rs.close();
			pstmt.close();
			dbcon.conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return item;
	}//한개의 상품 정보 끝
	
	
	//상품 삭제
	public void deleteItem(int itemId) {
		dbcon.getCon();
		
		try {
			String sql = "delete from item where item_id=?";
			
			pstmt = dbcon.conn.prepareStatement(sql);
			
			pstmt.setInt(1, itemId);
			
			pstmt.executeUpdate();
			
			pstmt.close();
			dbcon.conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//상품 삭제 끝	
}
