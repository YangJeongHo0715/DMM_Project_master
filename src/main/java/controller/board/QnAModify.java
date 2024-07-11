package controller.board;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
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
import dto.QnA;

@WebServlet("/board/qnaModify")
public class QnAModify extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public QnAModify() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int num = Integer.parseInt(request.getParameter("num"));
		
		BoardDAO dao = new BoardDAO();
		QnA qna = dao.getBoard(num);
		
		request.setAttribute("qna", qna);
		
		RequestDispatcher dis = request.getRequestDispatcher("qnaModify.jsp");
		dis.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		//오늘 날짜 
		java.util.Date today = new java.util.Date();
		SimpleDateFormat cal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String signdate = cal.format(today);
		
		HttpSession session = request.getSession();
		String session_email =(String) session.getAttribute("email");
		
		//저장경로
		String uploadPath="D:\\Dev\\Project_KTE\\DMM_Project_master\\src\\main\\webapp\\upload_notice";
		int size=10*1024*1024;
		
		MultipartRequest multi = new MultipartRequest(request,uploadPath,size,"utf-8",new DefaultFileRenamePolicy());
		
		QnA qna = new QnA();
		
		qna.setNum(Integer.parseInt(multi.getParameter("num")));
		qna.setSubject(multi.getParameter("subject"));
		qna.setContent(multi.getParameter("content"));
		qna.setRegdate(signdate);
		
		Enumeration files =multi.getFileNames();
		
		String file = (String)files.nextElement();
		String file_rename = multi.getFilesystemName(file);
		if(file_rename == null) { file_rename = ""; }
		
		qna.setFile(file_rename);
		qna.setWriter(session_email);
		
		BoardDAO dao = new BoardDAO();
		dao.update(qna.getNum(), qna.getSubject(),qna.getContent());
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		out.print("<script>");
		out.print("alert('게시글을 성공적으로 수정하였습니다!');");
		out.print("location.href='/board/QnA';");
		out.print("</script>");
	}

}
