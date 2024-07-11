package controller.board;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDAO;
import dto.QnA;


@WebServlet("/board/qnaView")
public class QnAView extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public QnAView() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int num = Integer.parseInt(request.getParameter("num"));
		
		BoardDAO dao = new BoardDAO();
		QnA qna = dao.getBoard(num);
		
		request.setAttribute("qna", qna);
		
		RequestDispatcher dis = request.getRequestDispatcher("qnaView.jsp");
		dis.forward(request, response);
	}
}
