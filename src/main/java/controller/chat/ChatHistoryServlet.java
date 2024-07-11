package controller.chat;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MessageDAO;
import dto.Messages;

@WebServlet("/chat/history")
public class ChatHistoryServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 클라이언트로부터 전달받은 채팅방 정보.
        int itemId = Integer.parseInt(request.getParameter("itemId"));
        int roomId = Integer.parseInt(request.getParameter("roomId"));
        
        MessageDAO dao = new MessageDAO();
        ArrayList<Messages> list = dao.getChatMessages(itemId, roomId);
        
        // HTML 형식으로 구성할 StringBuilder 객체를 생성.
        StringBuilder chatHistoryHTMLBuilder = new StringBuilder();

        for (Messages message : list) {
            // 채팅 메시지를 HTML 형식으로 변환하여 StringBuilder에 추가.
            chatHistoryHTMLBuilder.append("<div class='chat-message'>");
            chatHistoryHTMLBuilder.append("<p>");
            chatHistoryHTMLBuilder.append(message.getSender()).append(": ").append(message.getMessage()); // getContent()로 변경
            chatHistoryHTMLBuilder.append("</p>");
            chatHistoryHTMLBuilder.append("</div>");
        }
        
        // 완성된 HTML 문자열을 chatHistoryHTML 변수에 저장.
        String chatHistoryHTML = chatHistoryHTMLBuilder.toString();

        // 응답을 보냄.
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().write(chatHistoryHTML);
    }
}
