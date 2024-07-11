package controller.board;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDAO;


@WebServlet("/board/qnaDelete")
public class QnADelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public QnADelete() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out =response.getWriter();
		
		int num = Integer.parseInt(request.getParameter("num"));
		
		
		BoardDAO dao = new BoardDAO();
		//dao.delete(num);
		
		//out.print("<script>");
		//out.print("alert('성공적으로 삭제되었습니다!');");
		//out.print("location.href='/board/QnA';");
		//out.print("</script>");
		
		int deleteResult = dao.delete(num);
		if (deleteResult == 1) {
		    // 삭제 성공 시
		    out.print("<script>alert('삭제되었습니다.');</script>");
		    out.print("<script>location.href='/board/QnA';</script>");
		    // 다른 처리나 페이지 이동 등 추가 가능
		} else if (deleteResult == 0) {
		    // 삭제 불가능한 경우
		    out.print("<script>alert('답글이 존재하여 삭제할 수 없습니다.');</script>");
		    out.print("<script>location.href='/board/QnA';</script>");
		    // 다른 처리나 페이지 이동 등 추가 가능
		} else {
		    // 삭제 실패 시
		    out.print("<script>alert('삭제에 실패했습니다.');</script>");
		    out.print("<script>location.href='/board/QnA'</script>;");
		    // 다른 처리나 페이지 이동 등 추가 가능
		}

	
	}

}
