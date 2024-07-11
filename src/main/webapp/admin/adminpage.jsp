<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/include/header.jsp" %>
<%@ include file="/admin/include/header_popup.jsp" %>
<style>
    /* 기본 스타일 */
    body {
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 0;
    }
    .container {
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
        padding: 8px;
        text-align: left;
    }
    table th {
        background-color: #f2f2f2;
    }
    .order-box {
	    background-color: #f2f2f2;
	    border-radius: 10px;
	    padding: 20px;
	    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	    text-align: center;
	}
	.order-box h2 {
	    margin-bottom: 10px;
	    color: #333;
	}
	.order-count {
	    font-size: 36px;
	    font-weight: bold;
	    color: #007bff;
	}
	.order-label {
	    font-size: 18px;
	    color: #666;
	}
</style>	
	    <div class="container">
	        <h1>관리자 페이지 입니다.</h1>
	        <div class="content">
	        	<h3>전체 주문통계</h3>
	        	<div align=right>
	            <span><a href="/admin/item/list">[상품관리 바로가기]</a></span>            
	            </div>
	            <div class="order-box">
				  <h2>전체 상품 현황</h2>
				  <div class="order-count">${item_count }</div>
				  <div class="order-label">상품 수</div>
				</div>
	        </div>
	        <div class="content">
		            <h3>거래 상태</h3>
		            <div align=right>
		            <span><a href="/admin/transaction/list">[거래관리 바로가기]</a></span>
		            </div>
					<div class="order-box">
					    <div class="row">
					    	<c:forEach var="status_cnt" items="${status_cnt }">
					        <div class="col-md-4">
					            <div class="order-label">취소됨</div>
					            <div class="order-count">${status_cnt.cancel_count}</div>
					        </div>
					        <div class="col-md-4">
					            <div class="order-label">예약중</div>
					            <div class="order-count">${status_cnt.booking_count}</div>
					        </div>
					        <div class="col-md-4">
					            <div class="order-label">판매완료</div>
					            <div class="order-count">${status_cnt.complete_count}</div>
					        </div>
					        </c:forEach>
					    </div>
					</div>
			</div>
	        <div class="content">
	            <h3>최근 가입한 유저</h3>
	            <div align=right>
	            <span><a href="/admin/user/list">[회원관리 바로가기]</a></span>
	            </div>
	            <div>
	            <table style="width:100%; height:15px; color:white; font-size:12px;">
		        	<tr bgcolor="#40BEA7" height="30">
		        		<td style="color:white">no.</td>
		        		<td style="color:white">이메일</td>
		        		<td style="color:white">이름</td>
		        		<td style="color:white">닉네임</td>
		        		<td style="color:white">전화번호</td>
		        		<td style="color:white">생년월일</td>
		        		<td style="color:white">성별</td>
		        		<td style="color:white">레벨</td>
		        		<td style="color:white">가입날짜</td>
		        		<td style="color:white">상태</td>
		        		<td style="color:white">회원</td>
		        		<td style="color:white">mode</td>
		        	</tr>        
		        <c:set var="number" value="${number }" />
					<c:forEach var="list" items="${list }"><!-- 뿌리는곳  -->
					<c:if test="${list.userStatus ne '탈퇴'}"><!--유저의 상태가 탈퇴가 아닌경우에만 보임 -->
					<tr height="24">
						<td align="center" style="color:black">${number }</td>
						<td align="center" style="color:black">${list.userEmail }</td>
						<td align="center" style="color:black">${list.userName }</td>
						<td align="center" style="color:black">${list.userNick }</td>
						<td align="center" style="color:black">${list.phone }</td>
						<td align="center" style="color:black">${list.userAge }</td>
						<td align="center" style="color:black">${list.gender }</td>
						<td align="center" style="color:black">${list.level }</td>
						<td align="center" style="color:black">${list.joinDate }</td>	
						<td align="center">
							<c:choose>
								<c:when test="${list.userStatus eq '정지'}">
									<font color=red>
										${list.userStatus }
									</font>
								</c:when>
								<c:otherwise>
									<font color=green>					
										${list.userStatus }
									</font>
								</c:otherwise>
							</c:choose>
						</td>
						<td align="center" style="color:black">${list.userLoginStatus }</td>
						<td align="center" style="color:black">
							<span onclick="popup('${list.userEmail }')" style="cursor:pointer">[상세]</span>
							<span onclick="popup_modify('${list.userEmail }')" style="cursor:pointer">[수정]</span>
							<span onclick="id_delete('${list.userEmail }')" style="cursor:pointer">[삭제]</span>
						</td>
					</tr>
					<c:set var="number" value="${number - 1 }" />
					</c:if>
					</c:forEach>
				</table>
	            </div>
	        </div>        
    </div>
</body>
</html>
<%@ include file="/admin/include/footer.jsp" %>