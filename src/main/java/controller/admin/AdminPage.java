package controller.admin;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AdminDAO;
import dao.ItemDAO;
import dao.TransactionDAO;
import dto.StatusCount;
import dto.User;

@WebServlet("/admin/")
public class AdminPage extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AdminPage() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//한페이지당 출력할 회원 수
		int pageSize = 5;
			
		//현재 페이지
		int pageNum = 1;
		if(request.getParameter("pageNum") != null) {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}
			
		//검색어
		String field = request.getParameter("field");
		String search = request.getParameter("search");
			
		AdminDAO dao = new AdminDAO();
		ItemDAO dao2 = new ItemDAO();
		TransactionDAO dao3 = new TransactionDAO();
		
		//전체 회원 수
		int count = dao.getAllcount(field,search);
		
		//전체 상품 수
		int item_count = dao2.getAllcount(field,search);
		
		
		//넘버링
		int number = count - (pageNum - 1) * pageSize; 
				
		//현재 보여질 페이지 limit 값 설정
		int startRow = (pageNum - 1) * pageSize;
		int endRow = pageSize;
						
		ArrayList<User> list = dao.getAllMember(startRow,endRow, field, search);		
		
		ArrayList<StatusCount> status_cnt = dao3.getCountByStatus();
		
		request.setAttribute("list", list);
		request.setAttribute("status_cnt", status_cnt);				
		request.setAttribute("number", number);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("count", count);
		request.setAttribute("item_count", item_count);
		request.setAttribute("pageNum", pageNum);
		
		request.setAttribute("field", field);
		request.setAttribute("search", search);
		
		RequestDispatcher dis = request.getRequestDispatcher("adminpage.jsp");
		dis.forward(request, response);
	}
}
