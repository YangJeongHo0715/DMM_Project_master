package controller.board;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.BoardDAO;
import dto.Notice;


@WebServlet("/board/noticeModify")
public class NoticeModify extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public NoticeModify() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int uid = Integer.parseInt(request.getParameter("uid"));		

		BoardDAO dao = new BoardDAO();
		Notice notice = dao.oneNotice(uid);	
		
		request.setAttribute("notice", notice);
		
		RequestDispatcher dis = request.getRequestDispatcher("noticeModify.jsp");
		dis.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		//오늘 날짜
		java.util.Date today = new java.util.Date();
		SimpleDateFormat cal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String signdate = cal.format(today);
		
		HttpSession session = request.getSession();
		String session_email = (String) session.getAttribute("email");
		
		//저장 경로 (사용자 item 폴더에 저장 처리)
		String uploadPath="D:\\Dev\\Project_KTE\\DMM_Project_master\\src\\main\\webapp\\upload_notice";
		int size=10*1024*1024;

		MultipartRequest multi = new MultipartRequest(request,uploadPath,size,"utf-8",new DefaultFileRenamePolicy());
		
		Notice notice = new Notice();
		
		notice.setSubject(multi.getParameter("subject"));
		notice.setContent(multi.getParameter("content"));
		notice.setSigndate(signdate);

		Enumeration files=multi.getFileNames();
		
		String file = (String)files.nextElement();
		String file_rename = multi.getFilesystemName(file);
		if(file_rename == null) { file_rename = ""; }
		
		notice.setFile(file_rename);
		notice.setWriter(session_email);
		
		BoardDAO dao = new BoardDAO();
		dao.modifyNotice(notice);
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		out.print("<script>");
		out.print("alert('게시글을 성공적으로 수정하였습니다.!');");
		out.print("location.href='/board/notice';");
		out.print("</script>");
	}

}
