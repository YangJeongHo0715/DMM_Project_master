package controller.board;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDAO;
import dto.Notice;


@WebServlet("/board/notice")
public class NoticeMain extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public NoticeMain() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//한 페이지당 출력 게시물 수
		int pageSize = 5;

		//현재 페이지
		int pageNum = 1;
		if(request.getParameter("pageNum") != null) {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}

		BoardDAO dao = new BoardDAO();	

		//전체 게시글 수
		int count = dao.getAllcount();

		//넘버링
		int number = count - (pageNum - 1) * pageSize;

		//현재 보여질 페이지 limit 값 설정
		int startRow = (pageNum - 1) * pageSize;
		int endRow = pageSize;
		
		
		ArrayList<Notice> list = dao.getAllNotice(startRow, endRow);	
		
		request.setAttribute("list", list);
		request.setAttribute("number", number);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("count", count);
		request.setAttribute("pageNum", pageNum);
	
	
		RequestDispatcher dis = request.getRequestDispatcher("notice.jsp");
		dis.forward(request, response);
	}
}
