package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import dto.Item;

public class IndexDAO {
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs;
	
	DBconnectDAO dbcon = new DBconnectDAO();
	
	//전체 게시물 수
		public int getAllCount(String ca_id){
			dbcon.getCon();
			
			int count = 0;
			
			try {
				String sql = "select count(*) from item";
				if(ca_id != null && !ca_id.equals("")) { //카테고리를 선택하지 않았을때
					sql = "select count(*) from item where ca_id = ?";
					pstmt = dbcon.conn.prepareStatement(sql);
					pstmt.setString(1, ca_id);
				}else {
					sql = "select count(*) from item";
					pstmt = dbcon.conn.prepareStatement(sql);
					
				}
							
				rs = pstmt.executeQuery();
				
				if(rs.next()){
					count = rs.getInt(1);
				}
				
				rs.close();
				pstmt.close();
				dbcon.conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return count;
		}//전체 게시물 수 끝
	
	
	//인기상품 리스트 출력
	public ArrayList<Item> getBestItem(int startRow, int endRow, String search){
		dbcon.getCon();
		
		ArrayList<Item> list = new ArrayList<Item>();
		
		try {
			String sql = "select * from item where readcount >= 40 and user_like >= 30 and status = '판매중'";
			if(search != null && !search.equals("")) {
				sql +=" and title like ?";				
			}
			
			sql += " order by listing_date desc limit ?,?";
			
			pstmt = dbcon.conn.prepareStatement(sql);
			
			int parameterIndex = 1;
			
			if(search != null && !search.trim().isEmpty()) {
				pstmt.setString(parameterIndex++, "%" + search + "%");
			}
			
			pstmt.setInt(parameterIndex++, startRow);
			pstmt.setInt(parameterIndex++, endRow);
			
			rs = pstmt.executeQuery();
						
			while(rs.next()) {
				Item item = new Item();
				
				item.setItemId(rs.getInt("item_id"));
				item.setSellerId(rs.getString("seller_id"));
				item.setCaId(rs.getString("ca_id"));
				item.setTitle(rs.getString("title"));
				item.setPrice(rs.getString("price"));
				item.setStatus(rs.getString("status"));
				item.setListingDate(rs.getString("listing_date"));
				item.setFile1(rs.getString("FILE1"));
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
	}//인기상품 리스트 출력 끝
	
	
	// 최신상품 리스트 출력
	public ArrayList<Item> getTrendItem(int startRow, int endRow, String search) {
	    dbcon.getCon();

	    ArrayList<Item> list = new ArrayList<Item>();

	    try {
	        String sql = "select * from item where status = '판매중'";
	        if (search != null && !search.equals("")) {
	            sql += " and title like ?";
	        }
	        sql += " order by listing_date desc limit ?,?";
	        
	        pstmt = dbcon.conn.prepareStatement(sql);
	        
	        int parameterIndex = 1;
	        
	        if (search != null && !search.trim().isEmpty()) {
	            pstmt.setString(parameterIndex++, "%" + search + "%");
	        }
	        
	        pstmt.setInt(parameterIndex++, startRow);
	        pstmt.setInt(parameterIndex++, endRow);
	        
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            Item item = new Item();

	            item.setItemId(rs.getInt("item_id"));
	            item.setSellerId(rs.getString("seller_id"));
	            item.setCaId(rs.getString("ca_id"));
	            item.setTitle(rs.getString("title"));
	            item.setPrice(rs.getString("price"));
	            item.setStatus(rs.getString("status"));
	            item.setListingDate(rs.getString("listing_date"));
	            item.setFile1(rs.getString("FILE1"));
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
	}
	// 최신상품 리스트 출력 끝
	
	
	//전체상품 리스트 출력
		public ArrayList<Item> getAllItem(int startRow, int endRow, String ca_id){
			dbcon.getCon();
			
			ArrayList<Item> list = new ArrayList<Item>();
			
			try {
				String sql = "select * from item where status = '판매중'";	
				
				if(ca_id == null || ca_id.equals("") ) {
					sql += "and 1=1 "; //전체상품
				}else {
					if(ca_id.length() == 2) {
						sql += "and ca_id like ? "; //대분류 카테고리 선택시
					} else {
						sql += "and ca_id = ? "; //나머지의 경우 중분류 카테고리 가져옴
					}
				}
				
				sql += " order by listing_date desc limit ?,?";
				
				pstmt = dbcon.conn.prepareStatement(sql);
				
				int parameterIndex = 1;			
				
				if(ca_id != null && !ca_id.equals("")) {
					if(ca_id.length() == 2) {
						pstmt.setString(parameterIndex++, ca_id + "%");
					}else {
						pstmt.setString(parameterIndex++, ca_id);
					}
				}		
				
				pstmt.setInt(parameterIndex++, startRow);
		        pstmt.setInt(parameterIndex++, endRow);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					Item item = new Item();
					
					item.setItemId(rs.getInt("item_id"));
					item.setSellerId(rs.getString("seller_id"));
					item.setCaId(rs.getString("ca_id"));
					item.setTitle(rs.getString("title"));
					item.setPrice(rs.getString("price"));
					item.setStatus(rs.getString("status"));
					item.setListingDate(rs.getString("listing_date"));
					item.setFile1(rs.getString("FILE1"));
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
		}//전체상품 리스트 출력 끝		
	}
