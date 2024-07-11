package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dto.ChatRoom;
import dto.Item;

public class ChatRoomDAO {
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs;
	
	DBconnectDAO dbcon = new DBconnectDAO();
	
	
	//채팅방 생성
	public void createChatRoom(int itemId, String buyer, String seller) {
	    dbcon.getCon();
	    
	    try {
	        // 판매자와 구매자가 동일한 경우 채팅방 생성하지 않음
	        if (!buyer.equals(seller)) {
	            // 이미 채팅방이 존재하는지 확인
	            if (chatRoomExists(itemId, buyer, seller)) {
	                String sql = "insert into chatroom (item_id, buyer, seller) values (?, ?, ?)";
	                pstmt = dbcon.conn.prepareStatement(sql);
	                pstmt.setInt(1, itemId);
	                pstmt.setString(2, buyer);
	                pstmt.setString(3, seller);
	                pstmt.executeUpdate();
	            } else {
	                System.out.println("채팅방이 이미 존재합니다.");
	            }
	        } else {
	            System.out.println("판매자와 구매자가 동일하여 채팅방을 생성할 수 없습니다.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}//채팅방 생성 끝

	
	// 채팅방이 이미 존재하는지 확인하는 메서드
	private boolean chatRoomExists(int itemId, String buyer, String seller) {
	    boolean exists = false;
	    int count = 0;
	    try {
	        String sql = "select count(*) from chatroom where item_id = ? and buyer = ? and seller = ?";
	        pstmt = dbcon.conn.prepareStatement(sql);
	        pstmt.setInt(1, itemId);
	        pstmt.setString(2, buyer);
	        pstmt.setString(3, seller);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            count = rs.getInt(1);
	            if(count == 0) {
	            	exists = true;
	            }  
	            System.out.println("존재여부 : " + exists);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return exists;
	}// 채팅방이 이미 존재하는지 확인하는 메서드 끝
	
	
	//room_id추출
	public int roomId(int itemId, String userName, String buyer, String seller){
		dbcon.getCon();
		
		int room_id = 0;
		
		try {
			String sql = "select * from chatroom where item_id = ? and (buyer = ? or buyer = ?) and seller = ?";
			pstmt = dbcon.conn.prepareStatement(sql);
			
			pstmt.setInt(1, itemId);
			pstmt.setString(2, userName);
			pstmt.setString(3, buyer);
			pstmt.setString(4, seller);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				room_id = rs.getInt(1);
			}
			
			rs.close();
			pstmt.close();
			dbcon.conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return room_id;
	}//room_id추출
	
	
	//한개의 채팅 정보
	public ChatRoom oneChat(int roomId) {
		dbcon.getCon();
	
		ChatRoom chat = new ChatRoom();
		
		try {
			String sql = "select * from chatroom where room_id = ?";
			pstmt = dbcon.conn.prepareStatement(sql);
			
			pstmt.setInt(1, roomId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				chat.setRoomId(rs.getInt("room_id"));
				chat.setItemId(rs.getInt("item_id"));
				chat.setBuyer(rs.getString("buyer"));
				chat.setSeller(rs.getString("seller"));
			}
			
			rs.close();
			pstmt.close();
			dbcon.conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return chat;
	}//한개의 채팅 정보 끝
	
	
	//채팅방 목록 가져오기 (아이템 정보 포함)
    public ArrayList<ChatRoom> getChatRooms(String email, int startRow, int endRow) {
    	ArrayList<ChatRoom> chatRooms = new ArrayList<>();
        dbcon.getCon();

        try {
            String sql = "select c.*, i.* from chatroom c inner join item i on c.item_id = i.item_id where c.buyer = ? or c.seller = ? limit ?,?";
            pstmt = dbcon.conn.prepareStatement(sql);
            
            pstmt.setString(1, email);
            pstmt.setString(2, email);
            pstmt.setInt(3, startRow);
            pstmt.setInt(4, endRow);
            
            rs = pstmt.executeQuery();

            while (rs.next()) {
            	int read_count = 0;
                ChatRoom chatRoom = new ChatRoom();
        		MessageDAO dao = new MessageDAO();
                chatRoom.setRoomId(rs.getInt("c.room_id"));
                chatRoom.setItemId(rs.getInt("c.item_id"));
                chatRoom.setBuyer(rs.getString("c.buyer"));
                chatRoom.setSeller(rs.getString("c.seller"));   
                read_count = dao.getUnreadMessageCount(rs.getInt("c.room_id"), email);
                chatRoom.setReadCount(read_count);

                // 아이템 정보 설정
                Item item = new Item();
                item.setItemId(rs.getInt("i.item_id"));
                item.setTitle(rs.getString("i.title"));
                item.setFile1(rs.getString("i.file1"));
                item.setStatus(rs.getString("i.status"));
                item.setSellerId(rs.getString("i.seller_id"));

                chatRoom.setItem(item);
                chatRooms.add(chatRoom);
            }

            rs.close();
            pstmt.close();
            dbcon.conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return chatRooms;
    }//채팅방 목록 가져오기 (아이템 정보 포함) 끝
    
    
    //전체 채팅 수
  	public int getAllcount(String email){
  		dbcon.getCon();
  		
  		int count = 0;
  		
  		try {
  			String sql = "select count(*) from chatroom where buyer = ? or seller = ?";
  			pstmt = dbcon.conn.prepareStatement(sql);
  			pstmt.setString(1, email);
  			pstmt.setString(2, email);
  			
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
}
