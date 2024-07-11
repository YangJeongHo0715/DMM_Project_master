package controller.board;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDAO;


@WebServlet("/board/noticeDelete")
public class NoticeDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public NoticeDelete() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int uid = Integer.parseInt(request.getParameter("uid"));
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		BoardDAO dao = new BoardDAO();	
		
		dao.deleteNotice(uid);
		
		out.print("<script>");
		out.print("alert('성공적으로 삭제되었습니다!');");
		out.print("location.href='/board/notice';");
		out.print("</script>");	
	}

}
