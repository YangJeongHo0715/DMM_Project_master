package controller.chat;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import dao.ChatRoomDAO;
import dao.MessageDAO;

@ServerEndpoint("/chat/server")
public class ChatEndpoint {
    private static final Set<Session> sessions = Collections.newSetFromMap(new ConcurrentHashMap<>());
    
    
    @OnOpen
    public void onOpen(Session session) {
        // 클라이언트가 연결되었을 때 호출되는 메서드
        System.out.println("클라이언트가 연결되었습니다. 세션 ID: " + session.getId());

        // 연결된 클라이언트의 세션을 세션 목록에 추가합니다.
        sessions.add(session);
  
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        // 클라이언트로부터 메시지를 받았을 때 호출되는 메서드
        System.out.println("클라이언트로부터 메시지를 받았습니다: " + message);
        
        // 메시지에서 itemId와 실제 메시지 내용을 추출
        String[] messageParts = message.split("&");
        int itemId = Integer.parseInt(messageParts[0].split("=")[1]);
        String userName = messageParts[1].split("=")[1];
        String seller = messageParts[2].split("=")[1];
        String buyer = messageParts[3].split("=")[1];
        String actualMessage = messageParts[4].split("=")[1];

        //메세지 저장 관련 변수
        String sender = "";
        String reciever = "";
        int roomId = 0;
        
        ChatRoomDAO dao = new ChatRoomDAO();
        MessageDAO dao2 = new MessageDAO();
        
        
        //전송자와 받는자 구분
        if(!userName.equals(seller)){
            sender = userName;
            reciever = seller;
            System.out.println(sender);
            System.out.println(reciever);
            
        }else if(userName.equals(seller)) {
            sender = userName;
            reciever = buyer;
            System.out.println(sender);
            System.out.println(reciever);
        }
        
		//채팅방 생성
        dao.createChatRoom(itemId, buyer, seller);
        
        //roomId 추출
        roomId = dao.roomId(itemId, userName, buyer, seller);
        
        // 읽음/안 읽음 상태 업데이트
        dao2.isRead(userName, roomId);

        // 채팅 저장
        dao2.saveMessage(itemId, roomId, sender, reciever, actualMessage);
        
        
        // 클라이언트에게 응답 메시지를 보냅니다.
        for (Session s : sessions) {
            s.getBasicRemote().sendText(userName + ":" + actualMessage);
        }
    }

    @OnClose
    public void onClose(Session session) {
        // 클라이언트가 연결을 종료했을 때 호출되는 메서드
        System.out.println("클라이언트와의 연결이 종료되었습니다. 세션 ID: " + session.getId());

        // 연결이 종료된 클라이언트의 세션을 세션 목록에서 제거.
        sessions.remove(session);
    }
    
    @OnError
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    public static void sendMessageToAll(String message, int item_id, int room_id) {
    	MessageDAO dao = new MessageDAO();
    	String sender = "system";
    	String receiver = "system";
    	dao.saveMessage(item_id, room_id, sender, receiver, message);
    	// 읽음/안 읽음 상태 업데이트
        dao.isRead(sender, room_id);
        for (Session session : sessions) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
