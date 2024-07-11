<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/include/header_popup.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<table width=100% height=50 border=0>
	<tr>
		<td align=center style="color:#777;font-size:14px;font-weight:bold;">[게시글정보]</td>
	</tr>
</table>

<center>
<table width="500" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td colspan=2 height=30 style="color:#4b8b99;font-size:13px;font-weight:bold;">게시글 정보</td>
	</tr>
	<tr><td colspan=2 height=1 bgcolor=e9e9e9></td></tr>
	<tr>
		<td width=100 height=26><b>작성자</b></td>
		<td>${notice.writer }</td>
	</tr>
	<tr><td colspan=2 height=1 bgcolor=e9e9e9></td></tr>
	<tr>
		<td width=100 height=26><b>제목</b></td>
		<td>${notice.subject }</td>
	</tr>
	<tr><td colspan=2 height=1 bgcolor=e9e9e9></td></tr>
	<tr>
		<td height=300><b>내용</b></td>
		<td>${notice.content }</td>
	</tr>
	<tr><td colspan=2 height=1 bgcolor=e9e9e9></td></tr>	        
	<tr>
		<td height=26><b>작성일자</b></td>
		<td>${notice.signdate }</td>
	</tr>
	<tr><td colspan=2 height=1 bgcolor=e9e9e9></td></tr>	        
	<tr>
		<td height=26><b>ref</b></td>
		<td>${notice.ref }</td>
	</tr>
	<tr><td colspan=2 height=1 bgcolor=e9e9e9></td></tr>
	<tr>
		<td height=26><b>첨부 파일</b></td>
		<td>${notice.file }</td>
	</tr>
</table>
<br>
<table widtd="96%" border="0" cellpadding="0" cellspacing="0" align=center>
	<tr>
		<td><button onclick="popup_close()">창 닫기</button></td>
	</tr>
</table>
</center>

<script>
function popup_close(){
	self.close();
}
</script>
</body>
</html>