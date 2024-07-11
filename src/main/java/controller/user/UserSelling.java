package controller.user;

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

import dao.SellDAO;
import dto.Item;

@WebServlet("/user/selling")
public class UserSelling extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UserSelling() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String session_email = (String)session.getAttribute("email");
		
		if(session_email == null) { //로그인 처리 안 했을 경우 경고
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();

			out.print("<script>");
			out.print("alert('로그인을 해주세요!');");
			out.print("location.href='/';");
			out.print("</script>");
		
		}else {
			//한 페이지 보여질 글의 갯수
			int pageSize = 4;
			
			//현재 보여지는 페이지의 넘버 값 처리
			int pageNum = 1;
			if(request.getParameter("pageNum") != null) {
				pageNum = Integer.parseInt(request.getParameter("pageNum"));
			}
			
			//전체 게시글 갯수 초기화
			int count1 = 0;
			int count2 = 0;
			
			
			SellDAO dao = new SellDAO();
			
			count1 = dao.getAllCount(session_email);
			count2 = dao.getAllCount2(session_email);
			
			//현재 보여질 페이지 limit 값 설정
			int startRow = (pageNum - 1) * pageSize;
			int endRow = pageSize;
			
			ArrayList<Item> list1 = dao.getSellItem(session_email, startRow, endRow);
			ArrayList<Item> list2 = dao.getBuyItem(session_email, startRow, endRow);
			
			request.setAttribute("list1", list1);
			request.setAttribute("list2", list2);
			request.setAttribute("pageSize", pageSize);
			request.setAttribute("count1", count1);
			request.setAttribute("count2", count2);
			request.setAttribute("pageNum", pageNum);
			
			RequestDispatcher dis = request.getRequestDispatcher("selling.jsp");
			dis.forward(request, response);
		}
	}
}
