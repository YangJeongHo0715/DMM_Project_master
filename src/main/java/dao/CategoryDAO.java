package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dto.Category;

public class CategoryDAO {

	//사용할 객체를 미리 선언
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs;
	
	DBconnectDAO dbcon = new DBconnectDAO(); //데이터 베이스 사용
	
	//총 카테고리 수
	public int getAllcount(String field, String search) {
		dbcon.getCon();
		
		int count = 0;
		
		try {
			String sql = "";
			
			if(search != null && !search.equals("")) {
				sql = "select count(*) from ca where "+field+" like '%"+search+"%' ";
			}else {
				sql = "select count(*) from ca";
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
	}//총 카테고리 수 끝
	
	//목록
	public ArrayList<Category> getAllCategory(int startRow,int endRow,String field,String search) {
		dbcon.getCon();
		
		ArrayList<Category> list = new ArrayList<Category>();
		
		try {
			String sql = "";
			
			if(search != null && !search.equals("")) {
				sql = "select * from ca where "+field+" like '%"+search+"%' order by ca_id asc limit ?,?";
			}else {
				sql = "select * from ca order by ca_id asc limit ?,?";
			}
			
			pstmt = dbcon.conn.prepareStatement(sql);
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Category ca = new Category();
				
				ca.setCaId(rs.getString("ca_id"));
				ca.setCaName(rs.getString("ca_name"));
				ca.setCaUse(rs.getString("ca_use"));
				
				list.add(ca);
			}
			
			rs.close();
			pstmt.close();
			dbcon.conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}//목록 끝
	
	//추가
	public void insertCategory(Category ca) {
		dbcon.getCon();
		
		try {
			String sql = "insert into ca values(?,?,?)";
			pstmt = dbcon.conn.prepareStatement(sql);
			
			pstmt.setString(1, ca.getCaId());
			pstmt.setString(2, ca.getCaName());
			pstmt.setString(3, ca.getCaUse());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			dbcon.conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//추가 끝
	
	//수정
	public void updateCategory(Category ca) {
		dbcon.getCon();

		try {
			String sql = "update ca set ca_name=?,ca_use=? where ca_id=?";
			pstmt = dbcon.conn.prepareStatement(sql);

			pstmt.setString(1, ca.getCaName());
			pstmt.setString(2, ca.getCaUse());
			pstmt.setString(3, ca.getCaId());

			pstmt.executeUpdate();
			
			pstmt.close();
			dbcon.conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}//수정 끝
	
	//삭제
	public void oneDelete(String ca_id) {
		dbcon.getCon();

		try {
			String sql = "delete from ca where ca_id=?";
			pstmt = dbcon.conn.prepareStatement(sql);

			pstmt.setString(1, ca_id);

			pstmt.executeUpdate();
			
			pstmt.close();
			dbcon.conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//카테고리 대분류 불러오기 메소드
	public List<Category> getAllCategories_1(){
		dbcon.getCon();
		
		List<Category> categories = new ArrayList<>();
		
		try {
			String sql = "select * from ca where length(ca_id)=2 and ca_use='Y'";
			pstmt = dbcon.conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Category ca = new Category();
				
				ca.setCaId(rs.getString("ca_id"));
				ca.setCaName(rs.getString("ca_name"));
				
				categories.add(ca);
            }
			
			rs.close();
			pstmt.close();
			dbcon.conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return categories;
	}
	
	
	//카테고리 중분류 불러오기 메소드
		public List<Category> getAllCategories_2(String ca_id){
			dbcon.getCon();
			
			List<Category> categories = new ArrayList<>();
			
			try {
				String sql = "select * from ca where left(ca_id,2)=? and length(ca_id)=4 and ca_use='Y'";
				pstmt = dbcon.conn.prepareStatement(sql);
				
				pstmt.setString(1, ca_id);
				
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					Category ca = new Category();
					
					ca.setCaId(rs.getString("ca_id"));
					ca.setCaName(rs.getString("ca_name"));
					
					categories.add(ca);
	            }
				
				rs.close();
				pstmt.close();
				dbcon.conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return categories;
		}
}
