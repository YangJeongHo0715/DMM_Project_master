<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/admin/include/header_popup.jsp" %>
<style>
	.box{
		width:50%;
		float: left;
	}
	.clear{
		clear: both;
	}
	.divider{
		margin: 0px;
    	padding: 0px;
	}
	.info-label {
	    font-weight: bold;
	    width: 20%;
	    float: left;
	}	
	.info-input {
	    width: 70%;
	    border:1px solid #d5d5d5;
	}
	.info-row {
	    width: 100%;
	    margin-bottom: 5px; /* 각 항목 사이의 간격 조정 */
	    margin-top:5px;
	}
	.modify_btn{
		color:white;
		border-radius:5px;
		background-color:#337AB7;
		border:1px;
		width:70px;
		height:25px;
	}	
</style>
<div>
<h1 align=center style="color:#777;font-size:22px;font-weight:bold;">[회원수정]</h1>
</div>
<form action="/admin/user/modify" method="post" onsubmit="return user_modify()">
<div style="margin-left:50px;margin-right:50px;">
	<div height=30 style="color:#4b8b99;font-size:13px;font-weight:bold;">회원 계정 정보</div>
	<hr class="divider">
    <div class="info-row">
        <label for="user-email" class="info-label">이메일</label>
        <input id="user-email" class="info-input" type="email" name="user_email" value="${user.userEmail }" readonly>
    </div>
    <hr class="divider">
    <div class="info-row">
        <label for="password" class="info-label">비밀번호</label>
        <input id="user_pass" class="info-input" type="pass" name="user_pass" value="${user.password }">
    </div>
    <div style="height:20px;"></div>
    <div height=30 style="color:#4b8b99;font-size:13px;font-weight:bold;">개인 정보 수정</div>
    <hr class="divider">
    <div class="info-row">
        <label for="user_name" class="info-label">이름</label>
        <input id="user_name" class="info-input" type="text" name="user_name" value="${user.userName }">
    </div>
    <hr class="divider">
    <div class="info-row">
        <label for="user_nick" class="info-label">닉네임</label>
        <input id="user_nick" class="info-input" type="text" name="user_nick" value="${user.userNick }">
    </div>
    <hr class="divider">
    <div class="info-row">
        <label for="phone" class="info-label">전화번호</label>
        <input id="phone" class="info-input" type="tel" name="phone" value="${user.phone }">
    </div>
    <hr class="divider">
    <div class="info-row">
        <label for="user_age" class="info-label">생년월일</label>
        <input id="user_age" class="info-input" type="date" name="user_age" value="${user.userAge }">
    </div>
    <hr class="divider">
    <div class="info-row">
        <label for="user_gender" class="info-label">성별</label>
        <input type="radio" name="user_gender" value="female" <c:if test="${user.gender == 'female' }">checked</c:if>>여자
		<input type="radio" name="user_gender" value="male" <c:if test="${user.gender == 'male'}">checked</c:if>>남자
    </div>
    <hr class="divider">
    <div class="info-row">
        <label for="zip_code" class="info-label">우편주소</label>
        <input style="width:40%;border:1px solid #d5d5d5;" type="text" id="zip_code" name="zip_code" value="${user.zipCode}">
        <input type="button" class="modify_btn" onclick="openZipSearch();" value="주소검색">
    </div>
    <hr class="divider">
    <div class="info-row">
        <label for="address" class="info-label">주소</label>
        <input id="addr" class="info-input" type="text" name="address" value="${user.address }">
    </div>
    <hr class="divider">
    <div class="info-row">
        <label for="addressDetail" class="info-label">상세주소</label>
        <input id="addressDetail" class="info-input" type="text" name="addressDetail" value="${user.addressDetail }">
    </div>
    <hr class="divider">
    <div class="info-row">
        <label for="level" class="info-label">레벨</label>
        <select name="level" style="width:10%;border:1px solid #d5d5d5;">
			<c:forEach var="num" begin="1" end="10">
				<option value="${num }">${num }</option>
			</c:forEach>
		</select>
    </div>
    <hr class="divider">
    <div class="info-row">
        <label for="level" class="info-label">상태</label>
        <input type="radio" name="user_status" value="정상" <c:if test="${user.userStatus  == '정상'}">checked</c:if> >정상
		<input type="radio" name="user_status" value="정지" <c:if test="${user.userStatus == '정지'}">checked</c:if>>정지
		<input type="radio" name="user_status" value="탈퇴" <c:if test="${user.userStatus  == '탈퇴'}">checked</c:if>>탈퇴
    </div>
    <hr class="divider">
    <div class="info-row">
        <label for="level" class="info-label">소셜</label>
        <input type="radio" name="user_login_status" value="일반" <c:if test="${user.userLoginStatus  == '일반'}">checked</c:if> >일반
		<input type="radio" name="user_login_status" value="소셜" <c:if test="${user.userLoginStatus == '소셜'}">checked</c:if>>소셜
    </div>
    <hr class="divider">
    <div style="height:20px;"></div>
    <div align=center><button type="submit" class="modify_btn">회원수정</button></div>
</form>