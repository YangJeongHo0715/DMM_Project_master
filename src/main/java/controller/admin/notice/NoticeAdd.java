package controller.admin.notice;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.NoticeDAO;
import dto.Notice;

@WebServlet("/admin/notice/noticeadd")
public class NoticeAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public NoticeAdd() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dis = request.getRequestDispatcher("noticeadd.jsp");
		dis.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		//오늘 날짜
		LocalDate today = LocalDate.now();
		String todayString = today.toString();
		
		int uid = Integer.parseInt(request.getParameter("uid"));
		
		Notice notice = new Notice();
		
		notice.setUid(uid);
		notice.setSubject(request.getParameter("subject"));
		notice.setContent(request.getParameter("content"));
		notice.setSigndate(todayString);
		notice.setFile(request.getParameter("file"));
		notice.setWriter(request.getParameter("writer"));
		
		NoticeDAO dao = new NoticeDAO();

		//출력 구문
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		dao.insertPost(notice);
	}

}
