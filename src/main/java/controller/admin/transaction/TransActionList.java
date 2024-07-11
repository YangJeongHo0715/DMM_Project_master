package controller.admin.transaction;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TransactionDAO;
import dto.Transaction;




@WebServlet("/admin/transaction/list")
public class TransActionList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public TransActionList() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//한페이지당 출력할 게시물 수
		int pageSize = 10;
							
		//현재 페이지
		int pageNum = 1;
		if(request.getParameter("pageNum") != null) {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}
							
		//검색어
		String field = request.getParameter("field");
		String search = request.getParameter("search");
								
		TransactionDAO dao = new TransactionDAO();
								
		//전체 게시물 수
		int transaction_count = dao.getAllcount(field,search);
								
		//넘버링
		int number = transaction_count - (pageNum - 1) * pageSize; 
								
		//현재 보여질 페이지 limit 값 설정
		int startRow = (pageNum - 1) * pageSize;
		int endRow = pageSize;
										
		ArrayList<Transaction> list = dao.getAlltransaction(startRow,endRow, field, search);
									
		request.setAttribute("list", list);
										
		request.setAttribute("number", number);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("transaction_count", transaction_count);
		request.setAttribute("pageNum", pageNum);
								
		request.setAttribute("field", field);
		request.setAttribute("search", search);
		
		RequestDispatcher dis = request.getRequestDispatcher("list.jsp");
		dis.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
