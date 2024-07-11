<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/admin/include/header_popup.jsp" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<style>
	    .container_1 {
	        max-width: 550px; /* 컨테이너의 최대 너비를 설정. */
	        margin: auto; /* 가운데 정렬을 위해 margin을 auto. */
	        padding: 20px; /* 컨테이너 내부의 여백을 추가. */
	    }
	
	    .form-group {
	        margin-bottom: 20px;
	        display: flex; /* 입력란과 버튼을 인라인으로 표시. */
	        align-items: center; /* 입력 요소들을 수직 중앙 정렬. */
	    }
	
	    .form-group label {
	        margin-right: 10px; /* 라벨과 입력 요소 사이의 간격을 설정. */
	        width: 100px;
	    }
	
	    .form-group .form-control {
	        flex: 1; /* 입력 요소와 버튼을 동일한 너비로 설정. */
	    }
	
	    .form-group button {
	        margin-left: auto; /* 버튼을 오른쪽으로 이동. */
	        width: 150px;
	    }
	</style>
</head>
<body>
<div class="container_1">
	<h2 align="center">게시글 작성</h2>
	<br>
	<form action="/admin/notice/noticeadd" method="post">
		<div class="form-group">
        	<label for="subject">제목</label>
        	<input type="text" class="form-control" id="subject" name="subject" required>
       	</div>
        <div class="form-group">
            <label for="content">내용</label>
            <input style="height:300px" type="text" class="form-control" id="content" name="content" required>
        </div>
        <div class="form-group">
            <label for="file">첨부파일</label>
            <input type="text" class="form-control" id="file" name="file">
        </div>        
        <button type="submit" class="btn btn-primary">게시글추가</button>
    </form>
</div>
</body>
</html>
