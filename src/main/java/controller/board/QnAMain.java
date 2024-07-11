package controller.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDAO;
import dto.QnA;


@WebServlet("/board/QnA")
public class QnAMain extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public QnAMain() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BoardDAO dao = new BoardDAO();
        
        // 총 게시글 수
        int cnt = dao.getBoardCount();
        
        // 한 페이지에 출력될 글 수
        int pageSize = 5; 

        // 현재 페이지 정보 설정
        String pageNum = request.getParameter("pageNum");
        if (pageNum == null){
            pageNum = "1";
        }
        
        int currentPage = Integer.parseInt(pageNum);
        int startRow = (currentPage - 1) * pageSize + 1;
        int endRow = currentPage * pageSize;
        
        // 부모글의 ID 받아오기
        int parentId = 0;
        if (request.getParameter("parentId") != null) {
            parentId = Integer.parseInt(request.getParameter("parentId"));
        }
        
        //페이지 내에서 보여질 넘버링 숫자 처리 초기화
        int number = 0;
        
        //넘버링 숫자
        number = cnt - (Integer.parseInt(pageNum) - 1) * pageSize;
        
        List<QnA> list = dao.getBoardListDescending(startRow, endRow, parentId);
        int num = cnt - ((currentPage - 1) * pageSize);
        
        request.setAttribute("list", list);
        request.setAttribute("num", num);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("cnt", cnt);
        request.setAttribute("number", number);
        request.setAttribute("pageNum", pageNum);
        request.setAttribute("parentId", parentId); // 부모 글의 ID를 전달
        
        RequestDispatcher dis = request.getRequestDispatcher("qna.jsp");
        dis.forward(request, response);
    }


}
