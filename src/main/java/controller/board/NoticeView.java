package controller.board;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDAO;
import dto.Notice;


@WebServlet("/board/noticeView")
public class NoticeView extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public NoticeView() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int uid = Integer.parseInt(request.getParameter("uid"));
		
		BoardDAO dao = new BoardDAO();
		Notice notice = dao.oneNotice(uid);
		
		request.setAttribute("notice", notice);
		
		RequestDispatcher dis = request.getRequestDispatcher("noticeView.jsp");
		dis.forward(request, response);
	}
}
