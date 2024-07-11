package controller.sell;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SellDAO;

@WebServlet("/sell/likeItem")
public class LikeItem extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LikeItem() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 클라이언트로부터 전송된 데이터를 추출.
        String itemIdStr = request.getParameter("itemId");
        if (itemIdStr == null) {
            // 만약 아이템 ID가 전송되지 않았다면 오류 응답
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        
        try {
            // 전송된 아이템 ID를 정수형으로 변환
            int item_id = Integer.parseInt(itemIdStr);
            int like_count = 0;
            
            SellDAO dao = new SellDAO();
            
            like_count = dao.getItemLikes(item_id);
            
            like_count++;
            
            dao.updateItemLikes(item_id, like_count);
            
            // 아이템에 대한 좋아요 처리가 완료되면 성공 응답
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (NumberFormatException e) {
            // 만약 전송된 아이템 ID가 잘못된 형식이라면 오류 응답
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
