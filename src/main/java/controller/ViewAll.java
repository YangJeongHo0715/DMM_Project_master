package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CategoryDAO;
import dao.IndexDAO;
import dao.SellDAO;
import dto.Category;
import dto.Item;


@WebServlet("/viewAll")
public class ViewAll extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ViewAll() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//한 페이지 보여질 글의 갯수
		int pageSize = 8;
		
		//현재 보여지는 페이지의 넘버 값 처리
		int pageNum = 1;
		if(request.getParameter("pageNum") != null) {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}
		
		//전체 게시글 갯수 초기화
		int count = 0;
		
		//페이지 내에서 보여질 넘버링 숫자 처리 초기화
		int number = 0;
		
		IndexDAO dao = new IndexDAO();		
		
		//현재 보여질 페이지 limit 값 설정
		int startRow = (pageNum - 1) * pageSize;
		int endRow = pageSize;
		
		//넘버링 숫자
		number = count - (pageNum - 1) * pageSize;
		
		//카테고리 불러오기
		String ca_id = request.getParameter("ca_id");
		
		count = dao.getAllCount(ca_id);
		
        CategoryDAO dao2 = new CategoryDAO();
        List<Category> categories1 = dao2.getAllCategories_1(); 
        
        
        
        ArrayList<Item> list = dao.getAllItem(startRow, endRow, ca_id);
        
        
        List<Category> categories2 = dao2.getAllCategories_2(ca_id);          
        
        request.setAttribute("categories1", categories1);
        request.setAttribute("categories2", categories2);
		request.setAttribute("list", list);
		request.setAttribute("number", number);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("count", count);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("ca_id", ca_id);
		
		RequestDispatcher dis = request.getRequestDispatcher("viewAll.jsp");
		dis.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
