package controller.chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.TranDAO;
import dto.Transaction;


@WebServlet("/chat/reserve")
public class ChatReserve extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ChatReserve() {
        super();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		
		//오늘 날짜
		java.util.Date today = new java.util.Date();
		SimpleDateFormat cal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String signdate = cal.format(today);
		
		String date = request.getParameter("date");
		String location = request.getParameter("location");
		int item_id = Integer.parseInt(request.getParameter("item_id"));
		int room_id = Integer.parseInt(request.getParameter("room_id"));
		String buyer = (String)session.getAttribute("email");
		
		
		Transaction tran = new Transaction();
		
		tran.setSellerId(request.getParameter("seller_id"));
		tran.setBuyerId(request.getParameter("buyer_id"));
		tran.setItemId(Integer.parseInt(request.getParameter("item_id")));
		tran.setDealDate(request.getParameter("date"));
		tran.setTransactionDate(signdate);
		tran.setLocation(request.getParameter("location"));
		tran.setStatus("예약중");
		
		TranDAO dao = new TranDAO();
		int count = dao.selectCountTran(buyer, item_id);
		Transaction tran2 = dao.oneTran(buyer, item_id);
		
		if(tran != null && tran2 != null && "취소".equals(tran2.getStatus())){
			
			dao.reserveTran(tran, item_id, buyer);
			
			String message = "예약이 완료되었습니다. \n 예약 일자: " + date + "\n 장소: " + location + 
			    		"<button onclick= 'formResult(" + item_id + "," + room_id + ")'>거래완료</button>";
 
		    ChatEndpoint.sendMessageToAll(message, item_id, room_id);
		    
		    response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			
			out.print("<script>");
			out.print("history.go(-1);");
			out.print("</script>");

			
		}else {
			if(count == 0) {
				//트랜젝션 추가
				dao.insertTran(tran, buyer, item_id);
				
				String message = "예약이 완료되었습니다. \n 예약 일자: " + date + "\n 장소: " + location + 
				    		"<button onclick= 'formResult(" + item_id + ")'>거래완료</button>";
	 
			    ChatEndpoint.sendMessageToAll(message, item_id, room_id);
			    
			    response.setContentType("text/html; charset=utf-8");
				PrintWriter out = response.getWriter();
				
				out.print("<script>");
				out.print("history.go(-1);");
				out.print("</script>");
				
			}else {
				response.setContentType("text/html; charset=utf-8");
				PrintWriter out = response.getWriter();
				
				out.print("<script>");
				out.print("alert('중복예약은 불가능합니다.');");
				out.print("history.go(-1);");
				out.print("</script>");
			}
		}
	}
}
