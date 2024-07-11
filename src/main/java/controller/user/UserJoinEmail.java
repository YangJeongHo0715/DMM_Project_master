package controller.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;

@WebServlet("/user/email_ok")
public class UserJoinEmail extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UserJoinEmail() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String msg = "";

        try {
            // UserDAO 객체 생성 및 데이터베이스 연결 초기화
            UserDAO dao = new UserDAO();

            // 이메일 유효성 확인
            if (email != null && !email.isEmpty()) {
                // 이메일 조회
                boolean emailExists = dao.checkEmailExists(email);

                // 이메일 존재 여부에 따라 메시지 설정
                if (emailExists) {
                    msg = "1"; // 이미 등록된 이메일
                } else {
                    msg = "0"; // 사용 가능한 이메일
                }
            } else {
                msg = "-2"; // 이메일이 null 또는 빈 문자열인 경우
            }
        } catch (SQLException e) {
            // SQLException 처리
            e.printStackTrace(); // 예외 내용을 출력하거나 적절한 처리를 수행.
            msg = "-1"; // 예외가 발생한 경우 메시지를 설정.
        }

        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(msg);
    }

}
