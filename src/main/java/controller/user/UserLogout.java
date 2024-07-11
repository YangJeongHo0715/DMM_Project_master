package controller.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;


@WebServlet("/user/logout")
public class UserLogout extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
    public UserLogout() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//세션 생성	
		HttpSession session = request.getSession();
		
		String input = "N";
		String email = (String)session.getAttribute("email");
		
		UserDAO dao = new UserDAO();
		
		//모든 세션 삭제 
		session.invalidate();
		dao.isOnline(input, email);
	
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		out.print("<script>");
		out.print("alert('로그아웃이 정상적으로 완료되었습니다.');");
		out.print("location.href='/';");
		out.print("</script>");
			
	}

}