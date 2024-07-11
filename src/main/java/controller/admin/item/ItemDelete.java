package controller.admin.item;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ItemDAO;
import dto.Item;

@WebServlet("/admin/item/delete")
public class ItemDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public ItemDelete() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String stritemId = request.getParameter("itemId");		
		int itemId = Integer.parseInt(stritemId);
		
		ItemDAO dao = new ItemDAO();
		
		dao.deleteItem(itemId);
		
		response.sendRedirect("list");
	}
}
