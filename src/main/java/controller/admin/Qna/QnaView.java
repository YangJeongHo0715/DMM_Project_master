package controller.admin.Qna;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.QnaDAO;
import dto.QnA;

@WebServlet("/admin/qna/view")
public class QnaView extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public QnaView() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int num = Integer.parseInt(request.getParameter("num"));
		
		QnaDAO dao = new QnaDAO ();
		QnA qna = dao.onePost(num);
		
		request.setAttribute("qna", qna);
		
		RequestDispatcher dis = request.getRequestDispatcher("view.jsp");
		dis.forward(request, response);		
	}
}
