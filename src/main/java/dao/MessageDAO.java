package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import dto.ChatRoom;
import dto.Messages;

public class MessageDAO {
	//사용할 객체를 미리 선언
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs;
				
	//오늘 날짜
	Date today = new Date();
	SimpleDateFormat cal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String chatdate = cal.format(today);
		
	DBconnectDAO dbcon = new DBconnectDAO(); //데이터 베이스 사용
	
	
	// 채팅 내용 저장
	public void saveMessage(int itemId, int roomId, String sender, String receiver, String message) {
	    dbcon.getCon();
	    
	    try {
	        String sql = "insert into messages (item_id, room_id, message, sender, receiver, time, is_read) values (?, ?, ?, ?, ?, ?, 'N')";
	        pstmt = dbcon.conn.prepareStatement(sql);
	        
	        ChatRoomDAO dao = new ChatRoomDAO();
	        
	        pstmt.setInt(1, itemId);
	        pstmt.setInt(2, roomId);
	        pstmt.setString(3, message);
	        pstmt.setString(4, sender);
	        pstmt.setString(5, receiver);
	        pstmt.setString(6, chatdate);
	        
	        pstmt.executeUpdate();
	        
	        pstmt.close();
	        dbcon.conn.close();
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }   
	}// 채팅 내용 저장 끝
	
	
	//채팅내역 가져오기
    public ArrayList<Messages> getChatMessages(int itmeId, int roomId) {
    	ArrayList<Messages> msg = new ArrayList<>();
        dbcon.getCon();

        try {
            String sql = "select * from messages where item_id = ? and room_id = ?";
            pstmt = dbcon.conn.prepareStatement(sql);
            
            pstmt.setInt(1, itmeId);
            pstmt.setInt(2, roomId);
            
            rs = pstmt.executeQuery();

            while (rs.next()) {
            	Messages message = new Messages();
            	message.setItemId(rs.getInt("item_id"));
            	message.setRoomId(rs.getInt("room_id"));
            	message.setMessage(rs.getString("message"));
            	message.setSender(rs.getString("sender"));
            	message.setReceiver(rs.getString("receiver"));
            	message.setTime(rs.getString("time"));
            	
            	msg.add(message);
            }
            rs.close();
            pstmt.close();
            dbcon.conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }//채팅내역 가져오기 끝
    
    
    //안읽은 채팅 갯수
    public int getUnreadMessageCount(int roomId, String receiver){
        dbcon.getCon();
        
    	int unreadCount = 0;
    	
        try {
        	String sql = "select count(*) from messages where room_id = ? and receiver = ? and is_read = 'N'";
        	pstmt = dbcon.conn.prepareStatement(sql);
        	pstmt.setInt(1, roomId);
        	pstmt.setString(2, receiver);
        	
        	rs = pstmt.executeQuery();
        	
        	if(rs.next()) {
        		unreadCount = rs.getInt(1);
        	}
        	
        	rs.close();
        	pstmt.close();
        	dbcon.conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return unreadCount;
    }//안읽은 채팅 갯수 끝
    
    
    // 읽음/안 읽음 상태를 업데이트하는 메서드
    public void isRead(String reciever, int room_id){
    	dbcon.getCon();
    	
    	try {
			String sql = "update messages set is_read = 'Y' where receiver = ? and room_id = ?";
			pstmt = dbcon.conn.prepareStatement(sql);
			pstmt.setString(1, reciever);
			pstmt.setInt(2, room_id);
			
			pstmt.executeUpdate();
			
			pstmt.close();
			dbcon.conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }// 읽음/안 읽음 상태를 업데이트하는 메서드 끝

}
