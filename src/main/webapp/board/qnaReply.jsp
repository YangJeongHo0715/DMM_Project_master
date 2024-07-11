<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% int num = Integer.parseInt(request.getParameter("num")); %>
<!DOCTYPE html>
<html>
<head>
    <title>답글쓰기</title>
    <%@ include file="/include/header.jsp" %>
    <style>
       /* 푸터를 화면 하단에 고정 */
        footer {
           position: fixed;
           bottom: 0;
           width: 100%;
        }
        .container8 {
            padding-top: 20px; /* 위쪽 여백 설정 */
            width: 100%;
            text-align: center; /* 가운데 정렬 설정 */
        }
        form {
            width: 60%;
            margin: 0 auto; /* 가운데 정렬 */
        }
        /* 버튼 스타일 */
        .submit-button {
            margin-top: 20px;
           	float: right; /* 오른쪽으로 이동 */
        }
    </style> 
</head>
<body>
<div class="container8">
    <h2>글쓰기</h2>
    <form action="/board/qnaReply?parentId=<%=num %>" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label for="subject">제목</label>
            <input type="text" class="form-control" id="subject" name="subject">
        </div>
        <div class="form-group">
            <label for="content">내용</label>
            <textarea class="form-control" id="content" name="content" rows="5"></textarea>
        </div>
        <div class="form-group">
            <label for="file">첨부 파일</label>
            <input type="file" id="file" name="file">
        </div>
        <button type="submit" class="btn btn-primary submit-button">답글쓰기</button>
    </form>
</div>
</body>
<footer>
    <%@ include file="/include/footer.jsp" %>
</footer>
</html>
