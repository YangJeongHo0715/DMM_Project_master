package controller.mail;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/mail/mailSend")
public class MailSend extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public MailSend() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        String receiver = request.getParameter("user_email");

        //gmail 아이디(메일주소), 앱 비밀번호(2단계 인증) 발급받은 16자리
        String user = "yjh04722@gmail.com";
        String password = "mscbsryzvcnxsmbk";

        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();

        try {
            Properties p = new Properties(); //서버 정보를 p객체에 저장

            p.put("mail.smtp.starttls.enable","true");
            p.put("mail.smtp.host","smtp.gmail.com"); //gmail.com
            p.put("mail.smtp.auth", "true");
            p.put("mail.smtp.port", "587"); //gmail 포트번호

            //인증정보 생성
            Session s = Session.getInstance(p, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, password);
                }
            });

            Message m = new MimeMessage(s); //s객체를 사용하여 전송할 m객체 생성

            Address receiver_address = new InternetAddress(receiver); //받는 사람
            
            // 인증번호 생성 및 세션에 담기
            String verificationCode = generateVerificationCode();
            HttpSession session = request.getSession();
            session.setAttribute("verificationCode", verificationCode);
            
            // 클라이언트에게 인증번호 전달
            out.print(verificationCode); // 인증번호를 직접 출력하는 대신 JSON 형태로 전달하는 것이 좋습니다.
            
            //메일 전송에 필요한 설정 부분
            m.setHeader("content-type", "text/html;charset=utf-8");
            m.addRecipient(Message.RecipientType.TO, receiver_address);
            m.setSubject("인증번호 발송");
            m.setContent("인증번호: " + verificationCode, "text/html;charset=utf-8");
            m.setSentDate(new Date());

            Transport.send(m); //메세지를 메일로 전송

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 서버 오류 응답 코드 설정
            out.print("메일 전송 실패"); // 전송 실패 메시지 출력
        }
    }

    public static String generateVerificationCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10)); // 0부터 9까지의 숫자 중 하나를 랜덤하게 선택하여 추가
        }
        return sb.toString();
    }
}

