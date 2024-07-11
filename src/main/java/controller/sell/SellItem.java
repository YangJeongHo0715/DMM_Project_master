package controller.sell;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.SellDAO;
import dto.Item;
import dto.Category;


@WebServlet("/sell/sellitem")
public class SellItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public SellItem() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String session_email = (String)session.getAttribute("email");
		
		if(session_email == null) { //로그인 처리 안 했을 경우 경고
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();

			out.print("<script>");
			out.print("alert('로그인을 해주세요!');");
			out.print("location.href='/';");
			out.print("</script>");
		
		}else {
			
			SellDAO dao = new SellDAO();
			
			ArrayList<Category> ca_1 = dao.getCategory_1(); //카테고리 값
			
			request.setAttribute("ca_1", ca_1);
			
			RequestDispatcher dis = request.getRequestDispatcher("sellItem.jsp");
			dis.forward(request, response);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		//오늘 날짜
		java.util.Date today = new java.util.Date();
		SimpleDateFormat cal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String signdate = cal.format(today);
		
		HttpSession session = request.getSession();
		String session_email = (String) session.getAttribute("email");
		
		//저장 경로 (사용자 item 폴더에 저장 처리)
		String uploadPath="D:\\Dev\\Project_KTE\\DMM_Project_master\\src\\main\\webapp\\upload_item";
		int size=10*1024*1024;

		MultipartRequest multi = new MultipartRequest(request,uploadPath,size,"utf-8",new DefaultFileRenamePolicy());

		Item item = new Item();
		item.setTitle(multi.getParameter("title"));
		item.setLatitude(multi.getParameter("latitude"));
		item.setLongitude(multi.getParameter("longitude"));
		// 이미지 파일 넘기기
		Enumeration files = multi.getFileNames();
		
		String file1 = (String)files.nextElement();
		String file1_rename = multi.getFilesystemName(file1);
		if(file1_rename == null) { file1_rename = ""; }
		
		String file2 = (String)files.nextElement();
		String file2_rename = multi.getFilesystemName(file2);
		if(file2_rename == null) { file2_rename = ""; }
		
		String file3 = (String)files.nextElement();
		String file3_rename = multi.getFilesystemName(file3);
		if(file3_rename == null) { file3_rename = ""; }
		
		String file4 = (String)files.nextElement();
		String file4_rename = multi.getFilesystemName(file4);
		if(file4_rename == null) { file4_rename = ""; }	
		
		String file5 = (String)files.nextElement();
		String file5_rename = multi.getFilesystemName(file5);
		if(file5_rename == null) { file5_rename = ""; }	

		item.setFile1(file1_rename);
		item.setFile2(file2_rename);
		item.setFile3(file3_rename);
		item.setFile4(file4_rename);
		item.setFile5(file5_rename);
		
		item.setLocation(multi.getParameter("location"));
		item.setPrice(multi.getParameter("price"));
		item.setConditionItem(multi.getParameter("condition"));
		item.setStatus(multi.getParameter("status"));
		item.setCaId(multi.getParameter("category"));
		item.setDescription(multi.getParameter("description"));
		item.setListingDate(signdate);
		item.setSellerId(session_email);
		item.setReadcount(0);
		item.setUserLike(0);
		

		
		//dao 선언 및 상품 DB저장
		SellDAO dao = new SellDAO();		
		dao.insertItem(item); 
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();

		out.print("<script>");
		out.print("alert('게시글을 성공적으로 작성하였습니다!');");
		out.print("location.href='/user/selling';");
		out.print("</script>");
	}

}
