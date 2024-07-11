package controller.user;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ChatRoomDAO;
import dao.MessageDAO;
import dao.SellDAO;
import dto.ChatRoom;
import dto.Item;

@WebServlet("/user/chatList")
public class UserChatList extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UserChatList() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		HttpSession session = request.getSession();
		String email = (String)session.getAttribute("email");
		
		//한 페이지 보여질 글의 갯수
		int pageSize = 4;
				
		//현재 보여지는 페이지의 넘버 값 처리
		int pageNum = 1;
		if(request.getParameter("pageNum") != null) {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}
		
		//전체 게시글 갯수 초기화
		int count = 0;

		ChatRoomDAO dao = new ChatRoomDAO();
		
		count = dao.getAllcount(email);
		
		//현재 보여질 페이지 limit 값 설정
		int startRow = (pageNum - 1) * pageSize;
		int endRow = pageSize;
		
		ArrayList<ChatRoom> list = dao.getChatRooms(email, startRow, endRow);

		request.setAttribute("list", list);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("count", count);
		request.setAttribute("pageNum", pageNum);
		
		RequestDispatcher dis = request.getRequestDispatcher("chatList.jsp");
		dis.forward(request, response);
	}
}
