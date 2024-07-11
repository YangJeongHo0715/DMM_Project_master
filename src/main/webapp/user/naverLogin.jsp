<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
//URL의 해시 부분을 가져오는 함수
function getHashParams() {
    var hashParams = {};
    var hash = window.location.hash.substring(1);
    var hashArray = hash.split('&');
    for (var i = 0; i < hashArray.length; i++) {
        var keyValue = hashArray[i].split('=');
        hashParams[keyValue[0]] = decodeURIComponent(keyValue[1]);
    }
    return hashParams;
}

// 네이버 로그인 후 처리 함수
function handleNaverLogin() {
    var hashParams = getHashParams();
    if (hashParams.hasOwnProperty('access_token')) {
        var accessToken = hashParams.access_token;
        // 여기에 access token을 사용하여 서버에 요청을 보내거나 다른 작업을 수행하는 코드를 추가하세요.
        console.log("네이버 로그인 성공! Access Token: " + accessToken);
    } else {
        console.error("네이버 로그인에 실패하였거나 사용자가 취소하였습니다.");
    }
}

// 페이지 로드 시 네이버 로그인 처리 함수 호출
document.addEventListener("DOMContentLoaded", function() {
    handleNaverLogin();
});
</script>
</head>
<body>

</body>
</html>