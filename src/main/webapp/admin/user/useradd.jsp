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
	<h2 align="center">회원추가</h2>
	<br>
	<form action="/admin/user/useradd" method="POST" onsubmit="return user_modify()">
		<div class="form-group">
		    <label for="user_email">이메일</label>
		    <input type="email" class="form-control" id="user_email" name="user_email" required>
		</div>
		<div class="form-group">
        	<label for="password">비밀번호</label>
        	<input type="password" class="form-control" id="user_pass" name="user_pass">
       	</div>
        <div class="form-group">
            <label for="user_name">이름</label>
            <input type="text" class="form-control" id="user_name" name="user_name">
        </div>
        <div class="form-group">
            <label for="user_nick">닉네임</label>
            <input type="text" class="form-control" id="user_nick" name="user_nick">
        </div>
        <div class="form-group">
            <label for="user_age">생년월일</label>
            <input type="date" class="form-control" id="user_age" name="user_age">
        </div>
        <div class="form-group">
            <label for="gender">성별</label>
            <select class="form-control" id="user_gender" name="user_gender">
                <option>male</option>
                <option>female</option>
            </select>
        </div>
        <div class="form-group">
            <label for="gender">레벨</label>
            <select class="form-control" id="level" name="level">
                <option>1</option>
                <option>10</option>
            </select>
        </div>
        <div class="form-group">
            <label for="gender">상태</label>
            <select class="form-control" id="user_status" name="user_status">
                <option>정상</option>
                <option>정지</option>
                <option>탈퇴</option>
            </select>
        </div>
        <div class="form-group">
            <label for="gender">소셜</label>
            <select class="form-control" id="user_login_status" name="user_login_status">
            	<option>일반</option>
                <option>kakao</option>
                <option>naver</option>
            </select>
        </div>
        <div class="form-group">
            <label for="phone">휴대전화번호</label>
            <input type="tel" class="form-control" id="user_phone" name="user_phone">
        </div>
        <div class="form-group">
            <label for="zip_code">우편번호</label>
            <input type="text" class="form-control" id="zip_code" name="zip_code">
            <button type="button" class="btn btn-primary" onclick="openZipSearch()">주소검색</button>
        </div>
        <div class="form-group">
            <label for="address">주소</label>
            <input type="text" class="form-control" id="addr" name="address">
        </div>
        <div class="form-group">
            <label for="address_detail">상세주소</label>
            <input type="text" class="form-control" id="addr_dtl" name="address_detail">
        </div>
        <button type="submit" class="btn btn-primary">회원추가</button>
    </form>
</div>
</body>
</html>
