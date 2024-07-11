package dao;

import java.lang.reflect.Member;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import dto.User;

public class AdminDAO {

	//사용할 객체를 미리 선언
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs;
			
	//오늘 날짜
	Date today = new Date();
	SimpleDateFormat cal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String signdate = cal.format(today);
	
	DBconnectDAO dbcon = new DBconnectDAO(); //데이터 베이스 사용
	
	
	//총 회원 수
	public int getAllcount(String field,String search) {
		dbcon.getCon();
		
		int count = 0;
		
		try {
			String sql = "select count(*) from user where user_status <> '탈퇴'"; //상태가 탈퇴인 회원을 제외한 모두
			if(search != null && !search.equals("")) {
				sql = "select count(*) from user where user_status <> '탈퇴' and "+field+" like '%"+search+"%'";
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
	}//총 회원 수 끝
	
	//회원목록
	public ArrayList<User> getAllMember(int startRow,int endRow,String field,String search) {
			dbcon.getCon();
				
			ArrayList<User> list = new ArrayList<User>();
				
			try {
				String sql = "select * from user where user_status <> '탈퇴' order by join_date desc limit ?,?";
				if(search != null && !search.equals("")) {
					sql = "select * from user where user_status <> '탈퇴' and "+field+" like '%"+search+"%' order by join_date desc limit ?,?";
				}
				
				pstmt = dbcon.conn.prepareStatement(sql);
				
				pstmt.setInt(1 , startRow);
				pstmt.setInt(2 , endRow);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					User user = new User();
					
					user.setUserEmail(rs.getString("user_email"));
					user.setUserName(rs.getString("user_name"));
					user.setUserNick(rs.getString("user_nick"));
					user.setUserAge(rs.getString("user_age"));
					user.setGender(rs.getString("gender"));
					user.setPassword(rs.getString("password"));
					user.setPhone(rs.getString("phone"));
					user.setZipCode(rs.getString("zip_code"));
					user.setAddress(rs.getString("address"));
					user.setAddressDetail(rs.getString("address_detail"));
					user.setJoinDate(rs.getString("join_date"));
					user.setLevel(rs.getString("level"));
					user.setUserStatus(rs.getString("user_status"));
					user.setUserLoginStatus(rs.getString("user_login_status"));
						
					list.add(user);						
				}		
				rs.close();
				pstmt.close();
				dbcon.conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		return list;	
	}//회원목록 끝
	
	//한명의 정보 보기
	public User oneUser(String user_email) {
		dbcon.getCon();
		
		User user = new User();
		
		
		try {
			String sql = "select * from user where user_email=?";
			pstmt = dbcon.conn.prepareStatement(sql);
			
			pstmt.setString(1, user_email);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				user.setUserEmail(rs.getString("user_email"));
				user.setUserName(rs.getString("user_name"));
				user.setUserNick(rs.getString("user_nick"));
				user.setUserAge(rs.getString("user_age"));
				user.setGender(rs.getString("gender"));
				user.setPassword(rs.getString("password"));
				user.setPhone(rs.getString("phone"));
				user.setZipCode(rs.getString("zip_code"));
				user.setAddress(rs.getString("address"));
				user.setAddressDetail(rs.getString("address_detail"));
				user.setJoinDate(rs.getString("join_date"));
				user.setLevel(rs.getString("level"));
				user.setUserStatus(rs.getString("user_status"));
				user.setUserLoginStatus(rs.getString("user_login_status"));
			}
			
			rs.close();
			pstmt.close();
			dbcon.conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;		
	}//한명의 회원 정보 끝
	
	//회원 추가시 아이디 중복확인
		public int loginSelect(String email) {
			dbcon.getCon();
			
			int num = 0;
			
			try {
				String sql = "select count(*) from user where user_email = ?";
				pstmt = dbcon.conn.prepareStatement(sql);
				
				pstmt.setString(1, email);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					num = rs.getInt(1);
				}
				
				rs.close();
				pstmt.close();
				dbcon.conn.close();
				
			}catch(Exception e) {
					e.printStackTrace();
				}
			return num;
	}//회원 추가시 아이디 중복확인 끝
	
	// 회원추가
	public void insertUser(User user) {
		dbcon.getCon(); // db 연결
		
		try {
			String sql = "insert into user (user_email, user_name, user_nick, user_age, gender, password, phone, zip_code, address, address_detail, join_date, level, user_status, user_login_status) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = dbcon.conn.prepareStatement(sql);
			
			//객체담기
			pstmt.setString(1, user.getUserEmail());
			pstmt.setString(2, user.getUserName());
			pstmt.setString(3, user.getUserNick());
			pstmt.setString(4, user.getUserAge());
			pstmt.setString(5, user.getGender());
			pstmt.setString(6, user.getPassword());
			pstmt.setString(7, user.getPhone());
			pstmt.setString(8, user.getZipCode());
			pstmt.setString(9, user.getAddress());
			pstmt.setString(10, user.getAddressDetail());
			pstmt.setString(11, user.getJoinDate());
			pstmt.setString(12, user.getLevel());
			pstmt.setString(13, user.getUserStatus());
			pstmt.setString(14, user.getUserLoginStatus());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			dbcon.conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// 회원추가 끝	
		
	//회원 정보 수정
	public void updateUser(User user) {
		dbcon.getCon();		
		
		try {
			String sql="update user set user_name = ?, user_nick = ?, user_age =?, gender = ?, password =?, phone= ?, zip_code= ?, address= ?, address_detail= ?, level= ?, user_status= ?, user_login_status= ? where user_email = ?";
			pstmt =dbcon.conn.prepareStatement(sql);
				
			pstmt.setString(1,user.getUserName());
			pstmt.setString(2, user.getUserNick());
			pstmt.setString(3,user.getUserAge());
			pstmt.setString(4, user.getGender());
			pstmt.setString(5,user.getPassword());
			pstmt.setString(6, user.getPhone());
			pstmt.setString(7,user.getZipCode());
			pstmt.setString(8,user.getAddress());
			pstmt.setString(9,user.getAddressDetail());
			pstmt.setString(10,user.getLevel());
			pstmt.setString(11,user.getUserStatus());
			pstmt.setString(12,user.getUserLoginStatus());
			pstmt.setString(13,user.getUserEmail());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			dbcon.conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//회원삭제
	public void deleteUser(String user_email) {
		dbcon.getCon();
		
		try {
			String sql = "update user set user_status = '탈퇴' where user_email =?";
			
			pstmt = dbcon.conn.prepareStatement(sql);
			
			pstmt.setString(1, user_email);
			
			pstmt.executeUpdate();
			
			pstmt.close();
			dbcon.conn.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//회원삭제 끝
}
