package controller.cart;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CartDAO;
import dto.Wishlist;

@WebServlet("/cart/cartadd")
public class CartAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CartAdd() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		int session_id = Integer.parseInt(request.getParameter("session_id"));
		int item_id = Integer.parseInt(request.getParameter("itemId"));
		
		
		CartDAO dao = new CartDAO();		
		Wishlist wish = new Wishlist();
		
		wish.setSessionId(Integer.parseInt(request.getParameter("session_id")));
		wish.setUserId(Integer.parseInt(request.getParameter("id")));
		wish.setItemId(Integer.parseInt(request.getParameter("itemId")));
		wish.setPrice(request.getParameter("price"));
		
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("listingDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.0"));
        String listingdate = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		wish.setListingdate(listingdate);
		wish.setStatus(request.getParameter("status"));
		wish.setSellerId(request.getParameter("seller_id"));
		wish.setFile(request.getParameter("file"));
		wish.setTitle(request.getParameter("title"));

		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
	    int num = dao.ItemSelect(session_id, item_id);
		
	    if(num == 0) { //장바구니에 같은 상품 존재 x
	    	out.print(0);
	    	System.out.println(wish);
	    	dao.insertCart(wish);		
			
	    }else {	
	    	out.print(1);	
		}	
	}
}
