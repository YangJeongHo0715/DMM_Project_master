package controller.cart;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CartDAO;

@WebServlet("/cart/delete")
public class CartDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public CartDelete() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int itemId = Integer.parseInt(request.getParameter("itemId"));
		
		CartDAO dao = new CartDAO();
		
		dao.deleteWish(itemId);
		
		response.sendRedirect("/");
	}
}
