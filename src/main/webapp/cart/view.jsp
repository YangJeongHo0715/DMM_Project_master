<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<table width=100% height=50 border=0>
	<tr>
		<td align=center style="color:#777;font-size:14px;font-weight:bold;">[상품정보]</td>
	</tr>
</table>

<center>
<table width="500" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td colspan=2 height=30 style="color:#4b8b99;font-size:13px;font-weight:bold;">상품 정보</td>
	</tr>	
	<tr><td colspan=2 height=1 bgcolor=e9e9e9></td></tr>
	<tr>
		<td height=26><b>상품명</b></td>
		<td>${list.title }</td>
	</tr>
	<tr><td colspan=2 height=1 bgcolor=e9e9e9></td></tr>
	<tr>
		<td height=26><b>판매자</b></td>
		<td>${list.sellerId }</td>
	</tr>
	<tr><td colspan=2 height=1 bgcolor=e9e9e9></td></tr>
	<tr>
		<td height=26><b>상품 이미지</b></td>
		<td align="center" style="height:70px;"><img style="width:100%;height:100%" src="/upload_item/${list.file}"></td>
	</tr>
	<tr><td colspan=2 height=1 bgcolor=e9e9e9></td></tr>
	<tr>
		<td height=26><b>가격</b></td>
		<td>${list.price }</td>
	</tr>
	<tr><td colspan=2 height=1 bgcolor=e9e9e9></td></tr>	        
	<tr>
		<td height=26><b>상태</b></td>
		<td>${list.status }</td>
	</tr>
	<tr><td colspan=2 height=1 bgcolor=e9e9e9></td></tr>	        
	<tr>
		<td height=26><b>등록일</b></td>
		<td>${list.listingdate }</td>
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