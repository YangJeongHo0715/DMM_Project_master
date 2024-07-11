package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import dto.Category;
import dto.Item;

public class SellDAO {
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs;
	
	DBconnectDAO dbcon = new DBconnectDAO();
	
	//상품 등록 메서드
	public void insertItem(Item item) {
		dbcon.getCon();
		
		try {
			String sql = "insert into item (title, latitude, longitude, FILE1, FILE2, FILE3, FILE4, FILE5, location, price, condition_item, status, ca_id, description, listing_date, seller_id, readcount, user_like) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmt = dbcon.conn.prepareStatement(sql);
			
			pstmt.setString(1, item.getTitle());
			pstmt.setString(2, item.getLatitude());
			pstmt.setString(3, item.getLongitude());
			pstmt.setString(4, item.getFile1());
			pstmt.setString(5, item.getFile2());
			pstmt.setString(6, item.getFile3());
			pstmt.setString(7, item.getFile4());
			pstmt.setString(8, item.getFile5());
			pstmt.setString(9, item.getLocation());
			pstmt.setString(10,item.getPrice());
			pstmt.setString(11,item.getConditionItem());
			pstmt.setString(12,item.getStatus());
			pstmt.setString(13,item.getCaId());
			pstmt.setString(14,item.getDescription());
			pstmt.setString(15,item.getListingDate());
			pstmt.setString(16, item.getSellerId());
			pstmt.setInt(17, item.getReadcount());
			pstmt.setInt(18, item.getUserLike());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			dbcon.conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//상품 등록 메서드 끝
	
	
	//한개의 아이템
	public Item oneItem(int item_id) {
		dbcon.getCon();
		
		Item item = new Item();
		
		try {
			String sql = "select * from item where item_id = ?";
			pstmt = dbcon.conn.prepareStatement(sql);
			
			pstmt.setInt(1, item_id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				item.setItemId(rs.getInt("item_id"));
				item.setSellerId(rs.getString("seller_id"));
				item.setCaId(rs.getString("ca_id"));
				item.setTitle(rs.getString("title"));
				item.setPrice(rs.getString("price"));
				item.setDescription(rs.getString("description"));
				item.setConditionItem(rs.getString("condition_item"));
				item.setListingDate(rs.getString("listing_date"));
				item.setStatus(rs.getString("status"));
				item.setFile1(rs.getString("FILE1"));
				item.setFile2(rs.getString("FILE2"));
				item.setFile3(rs.getString("FILE3"));
				item.setFile4(rs.getString("FILE4"));
				item.setFile5(rs.getString("FILE5"));
				item.setLocation(rs.getString("location"));
				item.setUserLike(rs.getInt("user_like"));
				item.setReadcount(rs.getInt("readcount"));
			}
			
			pstmt.close();
			dbcon.conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return item;
	}
	
	//한명의 아이템 판매목록
	public ArrayList<Item> getSellItem(String email, int startRow, int endRow){
		dbcon.getCon();
		
		ArrayList<Item> list = new ArrayList<Item>();
		
		try {
			String sql = "select * from item where seller_id = ? order by listing_date desc limit ?,?";	
			pstmt = dbcon.conn.prepareStatement(sql);
			
			pstmt.setString(1, email);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
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
	}//한명의 아이템 판매목록 끝
	
	
	//한명의 아이템 구매목록
	public ArrayList<Item> getBuyItem(String email, int startRow, int endRow){
		dbcon.getCon();
		
		ArrayList<Item> list = new ArrayList<Item>();
		
		try {
			String sql = "select * from item where buyer_id = ? and status = '판매완료' order by listing_date desc limit ?,?";	
			pstmt = dbcon.conn.prepareStatement(sql);
			
			pstmt.setString(1, email);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
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
	}//한명의 아이템 구매목록 끝
	
	
	//판매 전체 게시물 수
	public int getAllCount(String email){
		dbcon.getCon();
		
		int count = 0;
		
		try {
			String sql = "select count(*) from item where seller_id = ?";
			pstmt = dbcon.conn.prepareStatement(sql);
			pstmt.setString(1, email);
			
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
	}//판매 전체 게시물 수 끝
	
	
	//구매 전체 게시물 수
	public int getAllCount2(String email){
		dbcon.getCon();
		
		int count = 0;
		
		try {
			String sql = "select count(*) from item where buyer_id = ?";
			pstmt = dbcon.conn.prepareStatement(sql);
			pstmt.setString(1, email);
			
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
	}//구매 전체 게시물 수 끝
	
	
	//카테고리 대분류
	public ArrayList<Category> getCategory_1(){
		dbcon.getCon();
		
		ArrayList<Category> ca_1 = new ArrayList<Category>();
		
		try {
			String sql = "select * from ca where length(ca_id)=2 and ca_use='Y'";
			pstmt = dbcon.conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				Category ca = new Category();
				
				ca.setCaId(rs.getString("ca_id"));
				ca.setCaName(rs.getString("ca_name"));
				
				ca_1.add(ca);
			}
			
			rs.close();
			pstmt.close();
			dbcon.conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ca_1;
	}//카테고리 대분류 끝
	
	//카테고리 중분류
	public ArrayList<Category> getCategory_2(String ca_id){
		dbcon.getCon();
		
		ArrayList<Category> ca_2 = new ArrayList<Category>();
		
		try {
			String sql = "select * from ca where left(ca_id,2)=? and length(ca_id)=4 and ca_use='Y'";
			pstmt = dbcon.conn.prepareStatement(sql);
			pstmt.setString(1, ca_id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				Category ca = new Category();
				
				ca.setCaId(rs.getString("ca_id"));
				ca.setCaName(rs.getString("ca_name"));
				
				ca_2.add(ca);
			}
			
			rs.close();
			pstmt.close();
			dbcon.conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ca_2;
	}//카테고리 중분류 끝
	
	//아이템 삭제
	public void deleteItem(String item_id){
		dbcon.getCon();
		
		try {
			String sql = "delete from item where item_id = ?";
			pstmt = dbcon.conn.prepareStatement(sql);
			pstmt.setString(1, item_id);
			pstmt.executeUpdate();

			pstmt.close();
			dbcon.conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}//아이템 삭제 끝
	
	//아이템 수정
	public void modifyItem(Item item){
		dbcon.getCon();
		
		try {
			String sql = "update item set title=?, FILE1=?, FILE2=?, FILE3=?, FILE4=?, FILE5=?, price=?, condition_item=?, status=?, ca_id=?, description=?, listing_date=?, seller_id=? where item_id=?";
			pstmt = dbcon.conn.prepareStatement(sql);
			
			pstmt.setString(1, item.getTitle());
			pstmt.setString(2, item.getFile1());
			pstmt.setString(3, item.getFile2());
			pstmt.setString(4, item.getFile3());
			pstmt.setString(5, item.getFile4());
			pstmt.setString(6, item.getFile5());
 			pstmt.setString(7,item.getPrice());
			pstmt.setString(8,item.getConditionItem());
			pstmt.setString(9,item.getStatus());
			pstmt.setString(10,item.getCaId());
			pstmt.setString(11,item.getDescription());
			pstmt.setString(12,item.getListingDate());
			pstmt.setString(13, item.getSellerId());
			pstmt.setInt(14, item.getItemId());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			dbcon.conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//아이템 수정 끝
	
	
	//게시글 좋아요 가져오기
	public int getItemLikes(int item_id) {
	    dbcon.getCon();
	    int count = 0;

	    try {
	        String sql = "select user_like from item where item_id = ?";
	        pstmt = dbcon.conn.prepareStatement(sql);
	        pstmt.setInt(1, item_id);

	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            count = rs.getInt("user_like"); // 현재 좋아요 수를 가져옴
	        }

	        pstmt.close();
	        dbcon.conn.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return count;
	}//게시글 좋아요 가져오기 끝
	
	
	// 게시글 좋아요 업데이트
	public void updateItemLikes(int item_id, int like_count){
	    dbcon.getCon();
	    
	    try {
	        String sql = "update item set user_like = ? where item_id = ?";
	        pstmt = dbcon.conn.prepareStatement(sql);
	        pstmt.setInt(1, like_count);
	        pstmt.setInt(2, item_id);
	        
	        pstmt.executeUpdate();
	        
	        pstmt.close();
	        dbcon.conn.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}// 게시글 좋아요 업데이트 끝
	
	
	//조회수 기능
	public void increaseReadCount(int item_id){
		dbcon.getCon();
		
		try {
			String sql = "update item set readcount = readcount + 1 where item_id = ?";
            pstmt = dbcon.conn.prepareStatement(sql);
            pstmt.setInt(1, item_id);
            pstmt.executeUpdate();
            
            pstmt.close();
	        dbcon.conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//조회수 기능 끝

	
}
