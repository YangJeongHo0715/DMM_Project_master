package controller.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ChatRoomDAO;
import dao.SellDAO;
import dao.UserDAO;
import dto.Item;
import dto.User;


@WebServlet("/user/sellingView")
public class UserSellingView extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public UserSellingView() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		int item_id = Integer.parseInt(request.getParameter("item_id"));
		System.out.println(item_id);
		
		int room_id = 0;
		String seller_id = request.getParameter("seller_id");
		String buyer_id = (String)session.getAttribute("email");
		String userName = "";
		
		SellDAO dao = new SellDAO();
		UserDAO dao2 = new UserDAO();
		ChatRoomDAO dao3 = new ChatRoomDAO();

		dao.increaseReadCount(item_id); //조회수 증가
		
		Item item = dao.oneItem(item_id);
		User user = dao2.oneUser(seller_id);
		
		//room_id추출
		room_id = dao3.roomId(item_id, userName, buyer_id, seller_id);
		
		request.setAttribute("item", item);
		request.setAttribute("user", user);
		request.setAttribute("room_id", room_id);
		
		RequestDispatcher dis = request.getRequestDispatcher("sellingView.jsp");
		dis.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
