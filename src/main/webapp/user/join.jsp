<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/include/lib.jsp" %>
<!-- jQuery Library (다운그레이드된 버전) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>

<!-- Bootstrap JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

</head>
<body>
<div class="container_1">
	<h2 align="center">회원가입</h2>
	<br>
	<form action="/user/join" method="POST" onsubmit="return user_modify()">
		<div class="form-group">
		    <label for="user_email">이메일</label>
		    <input type="email" class="form-control" id="user_email" name="user_email">
		    <button type="button" id="send_email_button" class="btn btn-primary" onclick="sendEmail()">인증번호보내기</button>
		    <button type="button" id="verify_button" class="btn btn-primary" onclick="verifyNumber()" style="display: none;">인증번호확인</button>
		</div>
		<span id="email_result"></span>
		<br>
        <div class="form-group">
            <label for="check_email">인증번호</label>
            <input type="text" class="form-control" id="check_email" name="check_email">
            <input type="hidden" id="check_number" name="check_number" value="">
        </div>
        <br>
        <div class="form-group">
            <label for="user_name">이름</label>
            <input type="text" class="form-control" id="user_name" name="user_name">
        </div>
        <br>
        <div class="form-group">
            <label for="user_nick">닉네임</label>
            <input type="text" class="form-control" id="user_nick" name="user_nick">
        </div>
        <br>
        <div class="form-group">
            <label for="user_age">생년월일</label>
            <input type="date" class="form-control" id="user_age" name="user_age">
        </div>
        <br>
        <div class="form-group">
            <label for="gender">성별</label>
            <select class="form-control" id="user_gender" name="user_gender">
                <option>male</option>
                <option>female</option>
            </select>
        </div>
        <br>
        <div class="form-group">
        	<label for="password">비밀번호</label>
        	<input type="password" class="form-control" id="user_pass" name="user_pass">
       	</div>
       	<br>
        <div class="form-group" style="margin:0">
        	<label for="password">비밀번호확인</label>
        	<input type="password" class="form-control" id="re_pass" name="re_pass">                        
        </div>
        <br>
        <div>
        	<span id="pass_check"><br></span>
        </div>
        <br>
        <div class="form-group">
            <label for="phone">휴대전화번호</label>
            <input type="tel" class="form-control" id="user_phone" name="user_phone">
        </div>
        <br>
        <div class="form-group">
            <label for="zip_code">우편번호</label>
            <input type="text" class="form-control" id="zip_code" name="zip_code">
            <button type="button" class="btn btn-primary" onclick="openZipSearch()">주소검색</button>
        </div>
        <br>
        <div class="form-group">
            <label for="address">주소</label>
            <input type="text" class="form-control" id="addr" name="address">
        </div>
        <br>
        <div class="form-group">
            <label for="address_detail">상세주소</label>
            <input type="text" class="form-control" id="addr_dtl" name="address_detail">
        </div>
        <br>
        <button type="submit" class="btn btn-primary">가입하기</button>
    </form>
</div>
</body>
</html>