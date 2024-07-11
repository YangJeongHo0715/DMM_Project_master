package controller.admin.notice;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.NoticeDAO;
import dto.Notice;

@WebServlet("/admin/notice/list")
public class NoticeList extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public NoticeList() {
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
						
		NoticeDAO dao = new NoticeDAO();
						
		//전체 게시물 수
		int count = dao.getAllcount(field,search);
						
		//넘버링
		int number = count - (pageNum - 1) * pageSize; 
						
		//현재 보여질 페이지 limit 값 설정
		int startRow = (pageNum - 1) * pageSize;
		int endRow = pageSize;
								
		ArrayList<Notice> list = dao.getAllNotice(startRow,endRow, field, search);
							
		request.setAttribute("list", list);
								
		request.setAttribute("number", number);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("count", count);
		request.setAttribute("pageNum", pageNum);
								
		request.setAttribute("field", field);
		request.setAttribute("search", search);
		
		RequestDispatcher dis = request.getRequestDispatcher("list.jsp");
		dis.forward(request, response);
	}
}
