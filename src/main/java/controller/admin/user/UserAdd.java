package controller.admin.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AdminDAO;
import dto.User;

@WebServlet("/admin/user/useradd")
public class UserAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UserAdd() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dis = request.getRequestDispatcher("useradd.jsp");
		dis.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String email = request.getParameter("user_email");
		
		//오늘 날짜
		LocalDate today = LocalDate.now();
		String todayString = today.toString();
		
		User user = new User();
		
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
		user.setJoinDate(todayString);
		user.setLevel(request.getParameter("level"));
		user.setUserStatus(request.getParameter("user_status"));
		user.setUserLoginStatus(request.getParameter("user_login_status"));
		
		AdminDAO dao = new AdminDAO();
		
		int num = dao.loginSelect(email);
		
		//출력 구문
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		if(num == 1) { //아이디 존재
			out.print("<script>");
			out.print("alert('존재하는 아이디입니다.');");
			out.print("history.back();");
			out.print("</script>");
		}else {
			dao.insertUser(user);

			out.print("<script>");
			out.print("alert('회원이 정상적으로 추가되었습니다.');");
			out.print("location.href='/admin/user/list';");
			out.print("</script>");
		}
	}
}
