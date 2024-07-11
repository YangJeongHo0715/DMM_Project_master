package controller.chat;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.TranDAO;
import dto.Transaction;


@WebServlet("/chat/result")
public class ChatResult extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public ChatResult() {
        super();
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		
		//오늘 날짜
		java.util.Date today = new java.util.Date();
		SimpleDateFormat cal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String signdate = cal.format(today);
		
		int item_id = Integer.parseInt(request.getParameter("item_id"));
		int room_id = Integer.parseInt(request.getParameter("room_id"));
		String buyer = (String)session.getAttribute("email");
		
		Transaction tran = new Transaction();
		
		tran.setTransactionDate(signdate);
		tran.setStatus("판매완료");
		
		TranDAO dao = new TranDAO();
		//트랜젝션 추가
		dao.updateTran(tran, item_id, buyer);
		
		String message = "거래가 완료되었습니다.";

		ChatEndpoint.sendMessageToAll(message, item_id, room_id);
	}

}
