package controller.admin.category;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CategoryDAO;
import dto.Category;

@WebServlet("/admin/category/list")
public class CatrgoryList extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public CatrgoryList() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

		CategoryDAO dao = new CategoryDAO();

		//검색어
		String field = request.getParameter("field");
		String search = request.getParameter("search");

		//전체 게시글 수
		count = dao.getAllcount(field, search);

		//현재 보여질 페이지 limit 값 설정
		int startRow = (pageNum - 1) * pageSize;
		int endRow = pageSize;

		ArrayList<Category> list = dao.getAllCategory(startRow, endRow, field, search);

		//넘버링 숫자
		number = count - (pageNum - 1) * pageSize;

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
