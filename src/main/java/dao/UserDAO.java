package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import dto.User;

public class UserDAO {
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs;
	
	DBconnectDAO dbcon = new DBconnectDAO();
	
	
	//로그인 
	public User loginUserSelect(String email, String pass) {
		dbcon.getCon(); //db 연결 
		
		User user = new User(); //dto 연결 
		
		try {
			String sql="select * from user where user_email = ? and password = ?";
			pstmt = dbcon.conn.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, pass);
			
			rs = pstmt.executeQuery(); 
			
			if(rs.next()) {
				user.setUserId(rs.getInt("user_id"));
				user.setUserEmail(rs.getString("user_email"));
				user.setUserName(rs.getString("user_name"));
				user.setLevel(rs.getString("level"));
				user.setUserStatus(rs.getString("user_status"));
				user.setUserLoginStatus(rs.getString("user_login_status"));	
				user.setJoinDate(rs.getString("join_date"));
				user.setPassword(rs.getString("password"));
			}
			
			rs.close();
			pstmt.close();
			dbcon.conn.close();
			
		} catch (Exception e) {
				e.printStackTrace();
			}	
			return user;	
	}//로그인 끝 
	
	
	//아이디 존재 여부
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
	}//아이디 존재여부 끝
	
	
	//회원 한명 정보
	public User oneUser(String email){
		dbcon.getCon();
		
		User user = new User();
		
		try {
			String sql = "select * from user where user_email = ?";
			pstmt = dbcon.conn.prepareStatement(sql);
			
			pstmt.setString(1, email);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				user.setUserId(rs.getInt("user_id"));
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
				user.setLevel(rs.getString("level"));
				user.setUserStatus(rs.getString("user_status"));
				user.setUserLoginStatus(rs.getString("user_login_status"));
				user.setJoinDate(rs.getString("join_date"));
				user.setOnlineStatus(rs.getString("online_status"));
			}
			
			rs.close();
			pstmt.close();
			dbcon.conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return user;
	}//회원 한명 정보 끝

	
	//회원 수정
	public void updateUser(User user) {
		dbcon.getCon(); //db 연결 
		
		try {
			String sql = "update user set user_name = ?, user_nick = ?, user_age =?, gender = ?, password =?, phone= ?, zip_code= ?, address= ?, address_detail= ? where user_email = ?";
			pstmt = dbcon.conn.prepareStatement(sql);
			
			pstmt.setString(1,user.getUserName());
			pstmt.setString(2,user.getUserNick());
			pstmt.setString(3,user.getUserAge());
			pstmt.setString(4,user.getGender());
			pstmt.setString(5,user.getPassword());
			pstmt.setString(6,user.getPhone());
			pstmt.setString(7,user.getZipCode());
			pstmt.setString(8,user.getAddress());
			pstmt.setString(9,user.getAddressDetail());
			pstmt.setString(10,user.getUserEmail());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			dbcon.conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//회원 수정 끝
	
	
	//가입된 이메일 확인 여부
	public boolean checkEmailExists(String email) throws SQLException {
	    boolean exists = false;
	    dbcon.getCon(); // db 연결

	    try {
	        String sql = "select count(*) from user where user_email = ?";
	        pstmt = dbcon.conn.prepareStatement(sql);
	        pstmt.setString(1, email);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            int count = rs.getInt(1);
	            exists = (count > 0); // count가 0보다 크면 이미 존재하는 이메일
	        }
	    } finally {
	        // 사용한 자원 반환
	        if (rs != null) {
	            rs.close();
	        }
	        if (pstmt != null) {
	            pstmt.close();
	        }
	        if (dbcon.conn != null) {
	        	dbcon.conn.close();
	        }
	    }
	    return exists;
	}//가입된 이메일 확인 여부 끝
	
	
	//회원가입
	public void insertMember(User user) {
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
	}//회원가입 끝
	
	
	//api회원가입
	public void insertMemberApi(String email) {
		dbcon.getCon(); // db 연결
		LocalDate today = LocalDate.now();
		String todayString = today.toString();

		try {
			String sql = "insert into user (user_email, user_name, user_nick, user_age, gender, password, phone, zip_code, address, address_detail, join_date, level, user_status, user_login_status) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = dbcon.conn.prepareStatement(sql);
			
			//객체담기
			pstmt.setString(1, email);
			pstmt.setString(2, "소셜");
			pstmt.setString(3, "");
			pstmt.setString(4, "1900-01-01");
			pstmt.setString(5, "male");
			pstmt.setString(6, "");
			pstmt.setString(7, "");
			pstmt.setString(8, "");
			pstmt.setString(9, "");
			pstmt.setString(10, "");
			pstmt.setString(11, todayString);
			pstmt.setString(12, "1");
			pstmt.setString(13, "정상");
			pstmt.setString(14, "소셜");
			
			pstmt.executeUpdate();
			
			pstmt.close();
			dbcon.conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}//api회원가입 끝
	
	
	//onlie 여부
	public void isOnline(String input, String email) {
		dbcon.getCon();
		
		try {
			if(input.equals("Y")) {
				String sql = "update user set online_status = ? where user_email = ?";
				pstmt = dbcon.conn.prepareStatement(sql);
				pstmt.setString(1, input);
				pstmt.setString(2, email);
				
				pstmt.executeUpdate();
			}else {
				String sql = "update user set online_status = ? where user_email = ?";
				pstmt = dbcon.conn.prepareStatement(sql);
				pstmt.setString(1, input);
				pstmt.setString(2, email);
				
				pstmt.executeUpdate();
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//onlie 여부 끝
	
}