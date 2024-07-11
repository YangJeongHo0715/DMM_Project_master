package controller.sell;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SellDAO;


@WebServlet("/sell/delete")
public class SellDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public SellDelete() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String item_id = request.getParameter("item_id");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		SellDAO dao = new SellDAO();
		
		dao.deleteItem(item_id);
		
		out.print("<script>");
		out.print("alert('성공적으로 삭제되었습니다!');");
		out.print("location.href='/user/selling';");
		out.print("</script>");
	}
}
