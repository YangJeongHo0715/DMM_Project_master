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
<h1 align=center style="color:#777;font-size:22px;font-weight:bold;">[게시글 수정]</h1>
</div>
<form action="/admin/notice/modify" method="post">
<div style="margin-left:50px;margin-right:50px;">
	<div height=30 style="color:#4b8b99;font-size:13px;font-weight:bold;">회원 계정 정보</div>
	<hr class="divider">
    <div class="info-row">
        <label for="writer" class="info-label">작성자</label>
        <input id="writer" class="info-input" type="text" name="writer" value="${notice.writer }" readonly>
        <input id="uid" class="info-input" type="hidden" name="uid" value="${notice.uid }">        
    </div>
    <hr class="divider">
    <div class="info-row">
        <label for="subject" class="info-label">제목</label>
        <input id="subject" class="info-input" type="text" name="subject" value="${notice.subject }">
    </div>
    <hr class="divider">
    <div class="info-row" style="height:300px;">
        <label for="content" class="info-label">내용</label>
        <input style="height:300px;" id="content" class="info-input" type="text" name="content" value="${notice.content }">
    </div>
    <hr class="divider">
    <div class="info-row">
        <label for="signdate" class="info-label">올라온 날짜</label>
        <input id="signdate" class="info-input" type="text" name="signdate" value="${notice.signdate }">
    </div>
    <hr class="divider">
    <div class="info-row">
        <label for="file" class="info-label">첨부파일</label>
        <input id="file" class="info-input" type="text" name="file" value="${notice.file }">
    </div>
    <hr class="divider">
    <div style="height:20px;"></div>
    <div align=center><button type="submit" class="modify_btn">수정</button></div>
</form>