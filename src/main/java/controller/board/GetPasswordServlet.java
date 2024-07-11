package controller.board;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// 서버 측에서 비밀번호를 가져오는 엔드포인트 핸들러 예시 (Java Servlet 예시)

@WebServlet("/board/getpassword")
public class GetPasswordServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // 세션에서 비밀번호를 가져옴
        String correctPassword = (String) session.getAttribute("password");
        System.out.println(correctPassword);

        // JSON 형태로 응답을 보냄
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print("{\"password\": \"" + correctPassword + "\"}");
        out.flush();
    }
}
