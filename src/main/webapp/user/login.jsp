<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <!-- jQuery Library (다운그레이드된 버전) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	
	<!-- Bootstrap JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    
    <!-- 네이버 로그인 API 불러오기 -->
    <script src="https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.2.js" charset="utf-8"></script>

	<script>
	    $(document).ready(function() {
	        // 네이버 로그인 버튼 클릭 시 동작 설정
	        $(document).on("click", "#naverLogin", function(event) {
	            event.preventDefault();
	            // 네이버 로그인 페이지로 이동
	            window.location.href = "/user/naver";
	        });
	    });
	</script>
</head>
<body>
    <div class="login-container">
        <h2 class="text-center mb-4">로그인</h2>
        <!-- 일반 로그인 폼 -->
        <form action="/user/login" method="POST">
            <div class="form-group">
                <input type="text" class="form-control" name="email" placeholder="이메일을 입력하세요.">
            </div>
            <div class="form-group">
                <input type="password" class="form-control" name="password" placeholder="비밀번호를 입력하세요.">
            </div>
            <button type="submit" class="btn btn-primary btn-block login-btn">로그인</button>
        </form>
        <hr>
        <p class="text-center">또는</p>
        <!-- 카카오 로그인 버튼 -->
        <button type="button" onclick="kakaoLogin()" class="btn btn-warning btn-block social-login-btn">카카오 로그인</button>
        <!-- 네이버 로그인 버튼 -->
        <button type="button" id="naverLogin" class="btn btn-success btn-block social-login-btn">네이버 로그인</button>
    </div>
</body>
</html>
