<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/header.jsp" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<div class="container_1">
	<h2 align="center">회원수정</h2>
	<br>	
    <form action="modify" method="POST" onsubmit="return user_modify()">
        <div class="form-group">
            <label for="user_email">이메일</label>
            <input type="email" class="form-control" id="user_email" name="user_email" readonly value="${modify.userEmail}">
        </div>
        <br>
        <div class="form-group">
            <label for="user_name">이름</label>
            <input type="text" class="form-control" id="user_name" name="user_name" value="${modify.userName}">
        </div>
        <br>
        <div class="form-group">
            <label for="user_nick">닉네임</label>
            <input type="text" class="form-control" id="user_nick" name="user_nick" value="${modify.userNick}">
        </div>
        <br>
        <div class="form-group">
            <label for="user_age">생년월일</label>
            <input type="date" class="form-control" id="user_age" name="user_age" value="${modify.userAge}">
        </div>
        <br>
        <div class="form-group">
            <label for="gender">성별</label>
			<select class="form-control" id="user_gender" name="user_gender">
			    <c:if test="${modify.gender eq 'male'}">
			        <option>male</option>
			        <option selected>female</option>
			    </c:if>
			    <c:if test="${modify.gender ne 'male'}">
			        <option selected>female</option>
			        <option>male</option>
			    </c:if>
			</select>
        </div>
        <br>
        <div class="form-group">
        	<label for="password">비밀번호</label>
        	<input type="password" class="form-control" id="user_pass" name="user_pass" value="${modify.password}">
       	</div>
       	<br>
        <div class="form-group" style="margin:0">
        	<label for="password">비밀번호확인</label>
        	<input type="password" class="form-control" id="re_pass" required name="re_pass">                        
        </div>
        <br>
        <div>
        	<span id="pass_check"><br></span>
        </div>
        <br>
        <div class="form-group">
            <label for="phone">휴대전화번호</label>
            <input type="tel" class="form-control" id="user_phone" name="user_phone" value="${modify.phone}">
        </div>
        <br>
        <div class="form-group">
            <label for="zip_code">우편번호</label>
            <input type="text" class="form-control" id="zip_code" name="zip_code" value="${modify.zipCode}">
            <button type="button" class="btn btn-primary" onclick="openZipSearch();">주소검색</button>
        </div>
        <br>
        <div class="form-group">
            <label for="address">주소</label>
            <input type="text" class="form-control" id="addr" name="address" value="${modify.address}">
        </div>
        <br>
        <div class="form-group">
            <label for="address_detail">상세주소</label>
            <input type="text" class="form-control" id="addr_dtl" name="address_detail" value="${modify.addressDetail}" >
        </div>
        <br>
        <button type="submit" class="btn btn-primary">수정하기</button>
    </form>
</div>
</body>
</html>
<%@ include file="/include/footer.jsp" %>