package controller.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import dao.UserDAO;
import dto.User;

@WebServlet("/user/naver/callback")
public class NaverLoginCallbackServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        String state = request.getParameter("state");

        // 네이버 OAuth 2.0 액세스 토큰 요청
        String clientId = "Ok_djcYI5qyYV2eOmtMn";
        String clientSecret = "5s3SnZnW9P";
        String redirectUri = "http://localhost:8080/user/naver/callback"; // 콜백 URL
        String tokenUrl = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&client_id=" + clientId + "&client_secret=" + clientSecret + "&code=" + code + "&state=" + state + "&redirect_uri=" + redirectUri;

        // 액세스 토큰 요청
        URL url = new URL(tokenUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        // 응답 읽기
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer responseBuffer = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            responseBuffer.append(inputLine);
        }
        in.close();

        // JSON 파싱하여 액세스 토큰 추출
        JSONObject tokenInfo = new JSONObject(responseBuffer.toString());
        String accessToken = tokenInfo.getString("access_token");

        // 액세스 토큰을 사용하여 사용자 정보 가져오기
        String apiUrl = "https://openapi.naver.com/v1/nid/me";
        URL userInfoUrl = new URL(apiUrl);
        HttpURLConnection userInfoCon = (HttpURLConnection) userInfoUrl.openConnection();
        userInfoCon.setRequestMethod("GET");
        userInfoCon.setRequestProperty("Authorization", "Bearer " + accessToken);

        // 응답 읽기
        BufferedReader userIn = new BufferedReader(new InputStreamReader(userInfoCon.getInputStream()));
        StringBuffer userResponseBuffer = new StringBuffer();
        while ((inputLine = userIn.readLine()) != null) {
            userResponseBuffer.append(inputLine);
        }
        userIn.close();

        // JSON 파싱하여 사용자 정보 추출
        JSONObject userInfo = new JSONObject(userResponseBuffer.toString());
        String email = userInfo.getJSONObject("response").getString("email");
        String input = "Y";

        // 사용자 정보를 이용하여 추가 작업 수행
        request.getSession().setAttribute("email", email);
        
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
			
			response.sendRedirect("/social/modify");

            
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
			response.sendRedirect("/");
            
        }
        
    }
}
