package controller.admin.Qna;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.QnaDAO;
import dto.QnA;

@WebServlet("/admin/qna/list")
public class QnaList extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public QnaList() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//한 페이지 보여질 글의 갯수
		int QpageSize = 6;
		int ApageSize = 6;
				
		//현재 보여지는 페이지의 넘버 값 처리
		int QpageNum = 1;
		if(request.getParameter("QpageNum") != null) {
			QpageNum = Integer.parseInt(request.getParameter("QpageNum"));
		}
		int ApageNum = 1;
		if(request.getParameter("ApageNum") != null) {
			ApageNum = Integer.parseInt(request.getParameter("ApageNum"));
		}
		
		QnaDAO dao = new QnaDAO();
				
		//부모글 갯수
		int Qcount = dao.getAllQcount();
		//자식글 갯수
		int Acount = dao.getAllAcount();			
		
		//넘버링
		int number_1 = Qcount - (QpageNum - 1) * QpageSize;
		int number_2 = Acount - (ApageNum - 1) * ApageSize;
		
		//현재 보여질 페이지 limit 값 설정
		int startRow = (QpageNum - 1) * QpageSize;
		int endRow = QpageSize;
		
		int startRow2 = (ApageNum - 1) * ApageSize;
		int endRow2 = ApageSize;
				
		ArrayList<QnA> list_1 = dao.getQcoment(startRow, endRow);
		ArrayList<QnA> list_2 = dao.getAcoment(startRow2, endRow2);
				
		request.setAttribute("list_1", list_1);
		request.setAttribute("list_2", list_2);
		request.setAttribute("Qcount", Qcount);
		request.setAttribute("Acount", Acount);
		request.setAttribute("number", number_1);
		request.setAttribute("number2", number_2);
		request.setAttribute("QpageSize", QpageSize);
		request.setAttribute("QpageNum", QpageNum);
		request.setAttribute("ApageSize", ApageSize);
		request.setAttribute("ApageNum", ApageNum);

		RequestDispatcher dis = request.getRequestDispatcher("list.jsp");
		dis.forward(request, response);
	}
}
