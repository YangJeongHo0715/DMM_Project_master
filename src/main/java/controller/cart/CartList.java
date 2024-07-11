package controller.cart;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CartDAO;
import dto.Wishlist;

@WebServlet("/cart/list")
public class CartList extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public CartList() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String session_email = (String)session.getAttribute("email");
		
		if(session_email == null || session_email.equals("")) { //로그인 처리 안 했을 경우 경고
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();

			out.print("<script>");
			out.print("alert('로그인을 해주세요!');");
			out.print("location.href='/';");
			out.print("</script>");
		
		}else {
			int session_id = Integer.parseInt(request.getParameter("session_id"));
			
			//한 페이지 보여질 글의 갯수
			int pageSize = 10;
	
			//현재 보여지는 페이지의 넘버 값 처리
			int pageNum = 1;
			if(request.getParameter("pageNum") != null) {
				pageNum = Integer.parseInt(request.getParameter("pageNum"));
			}
	
			//전체 게시글 갯수 초기화
			int count = 0;
	
			//페이지 내에서 보여질 넘버링 숫자 처리 초기화
			int number = 0;
	
			CartDAO dao = new CartDAO();
	
	
			//전체 게시글 수
			count = dao.getAllcount(session_id);
	
			//현재 보여질 페이지 limit 값 설정
			int startRow = (pageNum - 1) * pageSize;
			int endRow = pageSize;
	
			ArrayList<Wishlist> list = dao.getAllList(session_id, startRow, endRow);
	
			//넘버링 숫자
			number = count - (pageNum - 1) * pageSize;
	
			request.setAttribute("list", list);
			request.setAttribute("number", number);
			request.setAttribute("pageSize", pageSize);
			request.setAttribute("count", count);
			request.setAttribute("pageNum", pageNum);
	
			RequestDispatcher dis = request.getRequestDispatcher("list.jsp");
			dis.forward(request, response);
		}
	}
}
