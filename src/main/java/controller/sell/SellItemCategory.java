package controller.sell;

import java.io.IOException;
import java.util.ArrayList;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SellDAO;
import dto.Category;


@WebServlet("/sell/category_ok")
public class SellItemCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SellItemCategory() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ca_id = request.getParameter("ca_id");

		SellDAO dao = new SellDAO();
		
		ArrayList<Category> ca_2 = dao.getCategory_2(ca_id); //카테고리(대분류)를 이용해 중분류 추출
		
		request.setAttribute("ca_2", ca_2);

		RequestDispatcher dis = request.getRequestDispatcher("cate2.jsp");
		dis.forward(request, response);
	}

}
