package controller.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import dto.User;

@WebServlet("/user/modify")
public class UserModify extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public UserModify() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		String session_email = (String)session.getAttribute("email");
		
		if(session_email == null){
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();

			out.print("<script>");
			out.print("alert('잘못된 경로입니다. 로그인해주세요.');");
			out.print("location.href='/';");
			out.print("</script>");
		}else {
			UserDAO dao = new UserDAO();
			User user = dao.oneUser(session_email);
			
			request.setAttribute("modify", user);
			
			RequestDispatcher dis = request.getRequestDispatcher("modify.jsp");
			dis.forward(request, response);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		User user = new User();
		
		// form 태그에서 넘어온 변수를 객체에 담기
		user.setUserEmail(request.getParameter("user_email"));
		user.setUserName(request.getParameter("user_name"));
		user.setUserNick(request.getParameter("user_nick"));
		user.setUserAge(request.getParameter("user_age"));
		user.setGender(request.getParameter("user_gender"));
		user.setPassword(request.getParameter("user_pass"));
		user.setPhone(request.getParameter("user_phone"));
		user.setZipCode(request.getParameter("zip_code"));
		user.setAddress(request.getParameter("address"));
		user.setAddressDetail(request.getParameter("address_detail"));
		
		UserDAO dao = new UserDAO();
		dao.updateUser(user);
		
		out.print("<script>");
		out.print("alert('회원수정이 정상적으로 완료되었습니다.');");
		out.print("location.href='/'");
		out.print("</script>");
	}

}
