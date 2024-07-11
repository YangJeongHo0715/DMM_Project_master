package controller.admin.item;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.ItemDAO;
import dto.Item;

@WebServlet("/admin/item/list")
public class ItemList extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ItemList() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//한페이지당 출력할 상품 수
		int pageSize = 10;
			
		//현재 페이지
		int pageNum = 1;
		if(request.getParameter("pageNum") != null) {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}
		
		//전체 상품 갯수 초기화
		int item_count = 0;
		
		//페이지 내 보여질 넘버링 숫자 초기화
		int number = 0;
		
		//검색어
		String field = request.getParameter("field");
		String search = request.getParameter("search");
				
		ItemDAO dao = new ItemDAO();
				
		//전체 상품 수
		item_count = dao.getAllcount(field,search);
				
		//넘버링
		number = item_count - (pageNum - 1) * pageSize; 
				
		//현재 보여질 페이지 limit 값 설정
		int startRow = (pageNum - 1) * pageSize;
		int endRow = pageSize;
						
		ArrayList<Item> list = dao.getAllItem(startRow,endRow, field, search);
						
		request.setAttribute("list", list);
						
		request.setAttribute("number", number);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("item_count", item_count);
		request.setAttribute("pageNum", pageNum);
						
		request.setAttribute("field", field);
		request.setAttribute("search", search);
		
		
		
		RequestDispatcher dis = request.getRequestDispatcher("list.jsp");
		dis.forward(request, response);
	}
}
