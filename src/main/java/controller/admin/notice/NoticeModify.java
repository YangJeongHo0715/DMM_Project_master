package controller.admin.notice;

import java.io.Console;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.NoticeDAO;
import dto.Notice;

@WebServlet("/admin/notice/modify")
public class NoticeModify extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public NoticeModify() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int uid = Integer.parseInt(request.getParameter("uid"));
		
		NoticeDAO dao = new NoticeDAO();
		Notice notice = dao.onePost(uid);
		
		request.setAttribute("notice", notice);
		
		RequestDispatcher dis = request.getRequestDispatcher("modify.jsp");
		dis.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		int uid = Integer.parseInt(request.getParameter("uid"));
		
		Notice notice = new Notice();
		
		notice.setUid(uid);
		notice.setSubject(request.getParameter("subject"));
		notice.setContent(request.getParameter("content"));
		notice.setSigndate(request.getParameter("signdate"));
		notice.setFile(request.getParameter("file"));
		notice.setWriter(request.getParameter("writer"));
		
		NoticeDAO dao = new NoticeDAO ();
		dao.updatePost(notice); //게시글 수정
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		out.print("<script>");
		out.print("alert('게시글수정이 정상적으로 완료되었습니다.');");
		out.print("opener.location.reload();"); //부모창 새로고침
		out.print("self.close();"); //자식창 닫기
		out.print("</script>");	
	}
}
