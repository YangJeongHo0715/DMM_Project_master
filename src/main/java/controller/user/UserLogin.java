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

@WebServlet("/user/login")
public class UserLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UserLogin() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dis = request.getRequestDispatcher("login.jsp");
        dis.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String email = request.getParameter("email");
        String pass = request.getParameter("password");
        String input = "Y";

        UserDAO dao = new UserDAO();

        int num = dao.loginSelect(email);

        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();

        if (num == 1) { // 아이디 존재
            User user = dao.loginUserSelect(email, pass); // 로그인 액션(회원정보)

            if (user != null && user.getUserStatus() != null && !user.getUserStatus().equals("정상")) {

                out.print("<script>");
                out.print("alert('" + user.getUserStatus() + "상태입니다. 관리자에게 문의해주세요.');");
                out.print("history.back();");
                out.print("</script>");

            } else if (user != null && user.getUserEmail() != null) {// 아이디, 비번 매칭성공

                HttpSession session = request.getSession(); // 세션 생성
                session.setAttribute("id", user.getUserId());
                session.setAttribute("email", user.getUserEmail());
                session.setAttribute("name", user.getUserName());
                session.setAttribute("level", user.getLevel());
                session.setAttribute("user_status", user.getUserStatus());
                session.setAttribute("login_status", user.getUserLoginStatus());
				session.setAttribute("joindate", user.getJoinDate());
				session.setAttribute("password", user.getPassword());
				
				String joindate = user.getJoinDate().replace("-", "");
				
				session.setAttribute("wishlist", joindate + user.getUserId());
                
                dao.isOnline(input, email);

                out.print("<script>");
                out.print("alert('" + user.getUserName() + "님 환영합니다.');");
                out.print("location.href='/';");
                out.print("</script>");

            } else {
                out.print("<script>");
                out.print("alert('비밀번호가 틀렸습니다.');");
                out.print("history.back();");
                out.print("</script>");
            }
        } else {
            out.print("<script>");
            out.print("alert('존재하지 않는 아이디입니다.');");
            out.print("history.back();");
            out.print("</script>");
        }
    }   
}
