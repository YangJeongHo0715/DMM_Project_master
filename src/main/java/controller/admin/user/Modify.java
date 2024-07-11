package controller.admin.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AdminDAO;
import dto.User;

@WebServlet("/admin/user/modify")
public class Modify extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public Modify() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user_email = request.getParameter("user_email");
		
		AdminDAO dao = new AdminDAO();
		User user = dao.oneUser(user_email);
		
		request.setAttribute("user", user); 
		
		RequestDispatcher dis = request.getRequestDispatcher("modify.jsp");
		dis.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		User user = new User();
		
		user.setUserEmail(request.getParameter("user_email"));
		user.setPassword(request.getParameter("user_pass"));
		user.setUserName(request.getParameter("user_name"));
		user.setUserNick(request.getParameter("user_nick"));
		user.setPhone(request.getParameter("phone"));
		user.setUserAge(request.getParameter("user_age"));
		user.setGender(request.getParameter("user_gender"));		
		user.setZipCode(request.getParameter("zip_code"));
		user.setAddress(request.getParameter("address"));
		user.setAddressDetail(request.getParameter("address_detail"));
		user.setLevel(request.getParameter("level"));
		user.setUserStatus(request.getParameter("user_status"));
		user.setUserLoginStatus(request.getParameter("user_login_status"));
		
		AdminDAO dao = new AdminDAO();
		dao.updateUser(user); // 회원 수정
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		out.print("<script>");
		out.print("alert('회원수정이 정상적으로 완료되었습니다.');");
		out.print("opener.location.reload();"); //부모창 새로고침
		out.print("self.close();"); //자식창 닫기
		out.print("</script>");		
	}

}
