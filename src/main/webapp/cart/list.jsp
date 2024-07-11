<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/include/header.jsp" %>
</head>
<style>
    /* 푸터를 화면 하단에 고정 */
    footer {
        position: fixed;
        bottom: 0;
        width: 100%;
    }
    .container1 {
        max-width: 1200px;
        margin: 0 auto;
        padding: 20px;
    }
    h1 {
        text-align: center;
    }
    /* 네비게이션 메뉴 스타일 */
    nav {
        background-color: #333;
    }
    nav ul {
        list-style-type: none;
        padding: 0;
        margin: 0;
        text-align: center;
    }
    nav ul li {
        display: inline;
    }
    nav ul li a {
        display: inline-block;
        padding: 10px 20px;
        color: #fff;
        text-decoration: none;
    }
    nav ul li a:hover {
        background-color: #555;
    }
    /* 콘텐츠 영역 스타일 */
    .content {
        margin-top: 20px;
    }
    /* 테이블 스타일 */
    table {
        width: 100%;
        border-collapse: collapse;
    }
    table th, table td {
        border: 1px solid #ddd;
        padding: 5px;
        text-align: left;
        color:black;
    }
    table th {
        background-color: #f2f2f2;
    }
</style>
<!-- 모달 창 -->
<div id="myModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeModal()">&times;</span>
        <div id="modalContent"></div>
    </div>
</div>
<script>	
	// 장바구니 품목 삭제 
	function wishlist_delete(itemId){
		var result = confirm("삭제 하시겠습니까?");
		
		if(result == true){
			location.href="delete?itemId="+itemId;
		}    	
	}
</script>
<body>
<div class="container1">
        <div class="content">
          	<div>총 찜한 상품 수 : ${count }</div>
        </div>
        <div class="content">
        <table style="width:100%; height:15px; color:white; font-size:12px;">
        	<tr bgcolor="#40BEA7" height="30">
        		<td style="color:white">상품</td>
        		<td style="color:white">상품명</td>
        		<td style="color:white">판매자</td>
        		<td style="color:white">가격</td>
        		<td style="color:white">상태</td>
        		<td style="color:white">등록일</td>
        		<td style="color:white">mode</td>
        	</tr>        
        <c:set var="number" value="${number }" />
			<c:forEach var="list" items="${list }"><!-- 뿌리는곳  -->
			<tr height="24">
				<td align="center" style="width:50px; height:50px;"><img style="width:100%;height:100%" src="/upload_item/${list.file}"></td>
				<td align="center"><a href="/user/sellingView?item_id=${list.itemId}&pageNum=${pageNum}">${list.title }</a></td>
				<td align="center">${list.sellerId }</td>
				<td align="center">${list.price }</td>
				<td align="center">
				<c:choose>
					<c:when test="${list.status eq '판매중' }">
						<font color="green">
							${list.status}
						</font>
					</c:when>
					<c:when test="${list.status eq '판매완료' }">
						<font color="blue">
							${list.status}
						</font>
					</c:when>
					<c:otherwise>
						<font color="red">
							${list.status}
						</font>
					</c:otherwise>	
				</c:choose>
				</td>
				<td align="center">${list.listingdate }</td>
				<td align="center">
				<span onclick="wishlist_delete(${list.itemId })" style="cursor:pointer">[삭제]</span>
				</td>
			</tr>
			<tr><td colspan=8 style="bgcolor:#e5ecef;"></td></tr>
			<c:set var="number" value="${number - 1 }" />
			</c:forEach>
		</table>
        </div>
        <!-- 페이징 처리 -->
	<style>
		.page_on {
			padding:0px 5px;
			color:red;
			border:1px solid #40BEA7;
			font-weight:bold;
			background-color:#40BEA7;
		}
		.page_off {
			padding:0px 5px;
			color:black;
			border:1px solid black;
		}
	</style> 
        <div>
        <table>
			<tr>
				<td>
					<c:if test="${count>0 }">
						<c:set var="pageCount" value="${count / pageSize + (count % pageSize == 0 ? 0 : 1) }" />
						<fmt:parseNumber var="pageCount" value="${pageCount }" integerOnly="true" />
						<!-- fmt:parseNumber : 문자열을 숫자로 변환해 주는 기능을 제공하는 태그 -->
						<!-- integerOnly : true , false 정수만 출력할 것인지를 묻는 속성 -->
					
						<!-- 2개의 변수 초기화 -->
						<c:set var="startPage" value="${1 }" />
						<c:set var="pageBlock" value="${3 }" />	
					
						<!-- 두번째 이상 블럭을 실행 할 경우 startPage 값 변경 부분-->
						<c:if test="${pageNum > pageBlock }">
							<!-- 결과를 정수형으로 리턴 받아야 하기 때문에 fmt -->
							<fmt:parseNumber var="result" value="${pageNum / pageBlock - (pageNum % pageBlock == 0 ? 1:0) }" integerOnly="true"/>
							<c:set var="startPage" value="${result * pageBlock + 1 }" />
						</c:if>	
					
						<!-- endPage 값 설정 부분 -->
						<c:set var="endPage" value="${startPage + pageBlock - 1 }" />
						<!-- 마지막 블럭일 경우 endPage 값 설정 부분 -->
						<c:if test="${endPage > pageCount }">
							<c:set var="endPage" value="${pageCount }" />
						</c:if>
					
						<!-- 이전 링크 -->
						<c:if test="${startPage > pageBlock }">
							<a href="list?session_id=${sessionScope.wishlist}&pageNum=${startPage - pageBlock }&field=${field}&search=${search}">[이전] </a>
						</c:if>
					
						<!-- 페이징 링크 -->
						<c:forEach var="i" begin="${startPage }" end="${endPage }">
							<c:choose>
								<c:when test="${pageNum == i }">
									<a href="list?session_id=${sessionScope.wishlist}&pageNum=${i }&field=${field}&search=${search}"><span class="page_on"><font color=white><b>${i }</b></font></span></a>
								</c:when>
								<c:otherwise>
									<a href="list?session_id=${sessionScope.wishlist}&pageNum=${i }&field=${field}&search=${search}"><span class="page_off">${i }</span></a>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<!-- 다음 링크 -->
						<c:if test="${endPage < pageCount }">
							<a href="list?session_id=${sessionScope.wishlist}&pageNum=${startPage + pageBlock }&field=${field}&search=${search}">[다음] </a>
						</c:if>
					</c:if>
				</td>
			</tr>
		</table>
        </div>       
</div>
</body>
<footer>
	<%@ include file="/include/footer.jsp" %>
</footer>
</html>