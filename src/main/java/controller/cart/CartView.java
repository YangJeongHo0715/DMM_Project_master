package controller.cart;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CartDAO;
import dto.Wishlist;

@WebServlet("/cart/view")
public class CartView extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public CartView() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int itemId = Integer.parseInt(request.getParameter("itemId"));
		
		CartDAO dao = new CartDAO();
		Wishlist wish = dao.oneWish(itemId);
		
		request.setAttribute("list", wish);
		
		RequestDispatcher dis = request.getRequestDispatcher("view.jsp");
		dis.forward(request, response);
	}
}
