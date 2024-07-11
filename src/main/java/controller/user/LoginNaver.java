package controller.user;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/user/naver")
public class LoginNaver extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 네이버 OAuth 2.0 인증 코드 받기
        String clientId = "Ok_djcYI5qyYV2eOmtMn";
        String redirectUri = "http://localhost:8080/user/naver/callback"; // 콜백 URL
        String state = UUID.randomUUID().toString(); // CSRF 방지를 위한 랜덤한 상태 토큰

        // 사용자를 네이버 로그인 페이지로 리디렉션
        String naverLoginUrl = "https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=" + clientId + "&redirect_uri=" + redirectUri + "&state=" + state;
        response.sendRedirect(naverLoginUrl);
    }
}
