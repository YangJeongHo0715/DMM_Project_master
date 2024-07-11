package controller.admin.category;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CategoryDAO;
import dto.Category;

@WebServlet("/admin/category/modify")
public class CategoryModify extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public CategoryModify() {
        super();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		String number = request.getParameter("number");
		String ca_id = request.getParameter("ca_id");
		String ca_name = request.getParameter("ca_name");
		String ca_use = request.getParameter("ca_use["+number+"]");
		
		Category ca = new Category();
		
		ca.setCaId(ca_id);
		ca.setCaName(ca_name);
		ca.setCaUse(ca_use);
		
		CategoryDAO dao = new CategoryDAO();
		dao.updateCategory(ca);
		
		response.sendRedirect("list");	
	}
}
