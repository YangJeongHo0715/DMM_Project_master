package controller.sell;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SellDAO;
import dto.Item;


@WebServlet("/sell/view")
public class SellView extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public SellView() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int item_id = Integer.parseInt(request.getParameter("item_id"));
		
		SellDAO dao = new SellDAO();
		dao.increaseReadCount(item_id); //조회수 증가
		
		Item item = dao.oneItem(item_id);
		
		request.setAttribute("item", item);
		
		RequestDispatcher dis = request.getRequestDispatcher("sellView.jsp");
		dis.forward(request, response);
	}
}
