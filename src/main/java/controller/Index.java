package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.IndexDAO;
import dto.Item;


@WebServlet("")
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Index() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//한 페이지 보여질 글의 갯수
		int pageSize = 4;
		
		//현재 보여지는 페이지의 넘버 값 처리
		int pageNum = 1;
		if(request.getParameter("pageNum") != null) {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}
		
		//검색어
		String search = request.getParameter("search");
		
		//페이지 내에서 보여질 넘버링 숫자 처리 초기화
		int number = 0;
		
		IndexDAO dao = new IndexDAO();
		
		
		//현재 보여질 페이지 limit 값 설정
		int startRow = (pageNum - 1) * pageSize;
		int endRow = pageSize;
		
		ArrayList<Item> list_1 = dao.getBestItem(startRow, endRow, search);
		ArrayList<Item> list_2 = dao.getTrendItem(startRow, endRow, search);
		
		request.setAttribute("list_1", list_1);
		request.setAttribute("list_2", list_2);
		request.setAttribute("number", number);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("pageNum", pageNum);
		
		request.setAttribute("search", search);
		
		RequestDispatcher dis = request.getRequestDispatcher("index.jsp");
		dis.forward(request, response);
		
	}
}
