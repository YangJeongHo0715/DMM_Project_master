package controller.admin.category;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CategoryDAO;
import dto.Category;

@WebServlet("/admin/category/categoryadd")
public class CategoryAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public CategoryAdd() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher dis = request.getRequestDispatcher("categoryadd.jsp");
		dis.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		Category ca = new Category();

		ca.setCaId(request.getParameter("ca_id"));
		ca.setCaName(request.getParameter("ca_name"));
		ca.setCaUse(request.getParameter("ca_use"));
		
		CategoryDAO dao = new CategoryDAO();
		dao.insertCategory(ca);
		
		response.sendRedirect("list");
	}
}
