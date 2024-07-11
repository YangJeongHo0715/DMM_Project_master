package controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import dto.User;

@WebServlet("/user/kakao")
public class LoginKakao extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	String email = request.getParameter("useremail");
    	String input = "Y";
    	UserDAO dao = new UserDAO();
        
    	int num = dao.loginSelect(email);

        if (num != 1) { // 아이디가 존재하지 않을 경우
        	
            dao.insertMemberApi(email); // 정보를 DB에 추가
            User user = dao.oneUser(email); // 회원정보 조회
            
            HttpSession session = request.getSession();
            
            session.setAttribute("id", user.getUserId());
            session.setAttribute("email", user.getUserEmail());
            session.setAttribute("name", user.getUserName());
            session.setAttribute("level", user.getLevel());
            session.setAttribute("user_status", user.getUserStatus());
            session.setAttribute("login_status", user.getUserLoginStatus());
			session.setAttribute("sellerId", user.getUserEmail()); // 판매자 ID로 이메일 사용
			session.setAttribute("joindate", user.getJoinDate());

			
			String joindate = user.getJoinDate().replace("-", "");
			
			session.setAttribute("wishlist", joindate + user.getUserId());
			dao.isOnline(input, email);
			
            // 아이디가 존재하지 않을 때의 처리
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("0");

            
        } else { // 아이디가 존재할 경우
        	
        	User user = dao.oneUser(email); // 회원정보 조회
        	
            // 여기서 추가적인 작업이 필요하다면 수행할 수 있습니다.
            HttpSession session = request.getSession();
            
            session.setAttribute("id", user.getUserId());
            session.setAttribute("email", user.getUserEmail());
            session.setAttribute("name", user.getUserName());
            session.setAttribute("level", user.getLevel());
            session.setAttribute("user_status", user.getUserStatus());
            session.setAttribute("login_status", user.getUserLoginStatus());
			session.setAttribute("joindate", user.getJoinDate());

			
			String joindate = user.getJoinDate().replace("-", "");
			
			session.setAttribute("wishlist", joindate + user.getUserId());
			dao.isOnline(input, email);
			
			// 아이디가 존재할 때의 처리
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("1");
            
        }
    }
}
