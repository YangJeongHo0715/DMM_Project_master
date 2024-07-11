<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>상세보기</title>
    <%@ include file="/include/header.jsp" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    <script src="/js/likeheart.js"></script>
    <style>
        .container4 {
            position: relative;
            width: 100%;
            height: 100%;
            vertical-align: center;
            bottom: -30px;
        }
        .carousel-control.left, .carousel-control.right {
            background: #fff;
            border: none; /* 테두리 제거 */
            border-radius: 50%; /* 동그라미 모양으로 변경 */
            box-shadow: none; /* 음영 제거 */
        }
        .btn-gr {
            margin-top: 450px;
        }
        /* 아이콘 크기 조절 */
        .bi-heart, .bi-eye, .bi-clock {
            font-size: 24px;
            margin-right: 60px;
        }
        .item{
        	position:absolute;
        }
        #myCarousel{
        	float: left;
        	margin-left: -800px;
        	width: 950px;
        	height: 400px;
        	
        }
        .item_info{
        	clear: both;
        	position : absolute;
        	bottom : 30px;
        }
        .empty{
        	padding-left: 1000px;
        }
    </style>
</head>
<body>
<div class="container4">
	<div class="item">
	    <div id="myCarousel" class="carousel slide" data-ride="carousel">
	        <!-- 슬라이드 내용 -->
	        <div class="carousel-inner">
		        <c:choose>
		           	<c:when test="${not empty item.file1}">
			            <div class="item active">
			                <img class="item_img" src="/upload_item/${item.file1}" alt="First slide">
			            </div>
		         	</c:when>
		           	<c:otherwise>
		               <!-- 사진없는 경우 경우에는 아무것도 표시하지 않음 -->
		           	</c:otherwise>
		       	</c:choose>
		       	<c:choose>
		           	<c:when test="${not empty item.file2}">
			            <div class="item">
			                <img class="item_img" src="/upload_item/${item.file2}" alt="Second slide">
			            </div>
		         	</c:when>
		           	<c:otherwise>
		               <!-- 사진없는 경우 경우에는 아무것도 표시하지 않음 -->
		           	</c:otherwise>
		       	</c:choose>
		       	<c:choose>
		           	<c:when test="${not empty item.file3}">
			            <div class="item">
			                <img class="item_img" src="/upload_item/${item.file3}" alt="Third slide">
			            </div>
		         	</c:when>
		           	<c:otherwise>
		               <!-- 사진없는 경우 경우에는 아무것도 표시하지 않음 -->
		           	</c:otherwise>
		       	</c:choose>
		       	<c:choose>
		           	<c:when test="${not empty item.file4}">
			            <div class="item">
			                <img class="item_img" src="/upload_item/${item.file4}" alt="First slide">
			            </div>
		         	</c:when>
		           	<c:otherwise>
		               <!-- 사진없는 경우 경우에는 아무것도 표시하지 않음 -->
		           	</c:otherwise>
		       	</c:choose>
		       	<c:choose>
		           	<c:when test="${not empty item.file5}">
			            <div class="item">
			                <img class="item_img" src="/upload_item/${item.file5}" alt="First slide">
			            </div>
		         	</c:when>
		           	<c:otherwise>
		               <!-- 사진없는 경우 경우에는 아무것도 표시하지 않음 -->
		           	</c:otherwise>
		       	</c:choose>
	        </div>
	        <!-- 슬라이드 버튼 -->
	        <a class="left carousel-control" href="#myCarousel" data-slide="prev"></a>
	        <a class="right carousel-control" href="#myCarousel" data-slide="next"></a>
	    </div>
	    <!-- 제목과 상세 정보 -->
	    <div class="item_info">
	        <h1>${item.title}</h1>
	        <h2><strong>가격 : </strong> <fmt:formatNumber value="${item.price}" pattern="#,###" />원</h2>
	        <hr style="width:500px; margin-left: -20px;">
	        <!-- 상세보기 페이지의 하트 아이콘에 클릭 이벤트를 추가 -->
	        <i class="bi bi-heart" onclick="likeItem(${item.itemId})">&nbsp;&nbsp;&nbsp;${item.userLike}</i>
	        <i class="bi bi-eye">&nbsp;&nbsp;&nbsp;${item.readcount}</i>
	        <span id="clock"></span>
	        <br>
	        <br>
	        <p><strong>거래장소 : </strong>${item.location}</p>
	        <p><strong>상태 : </strong>${item.conditionItem}</p>
	        <p><strong>물품설명</strong></p>
	        <% pageContext.setAttribute("LF", "\n"); //개행처리 %>
	        ${fn:replace(item.description, LF, "<br>") }
	        <input type="hidden" id= "wishlist" value="${sessionScope.wishlist}"/>
	        <input type="hidden" id= "id" value="${sessionScope.id}"/>
	        <input type="hidden" id="itemId" value="${item.itemId}"/>
	        <input type="hidden" id="price" value="${item.price}"/>
	        <input type="hidden" id="listingDate" value="${item.listingDate}"/>
	        <input type="hidden" id="status" value="${item.status}"/>
	        <input type="hidden" id="sellerId" value="${item.sellerId}"/>
	        <input type="hidden" id="file" value="${item.file1}"/>
	        <input type="hidden" id="title" value="${item.title}"/>
	    </div>
    </div>
    <!-- 채팅 버튼 -->
	<div class="btn-gr">
		<button class="btn btn-primary" id="viewButton" onclick="window.location.href = '/viewAll';"><i class="bi bi-list"></i></button>
		<span class="empty"></span>
         <c:choose>
           	<c:when test="${not empty sessionScope.email and sessionScope.email ne item.sellerId}">
               	<button class="btn btn-primary" id="cartButton">위시리스트 담기</button>
				<button class="btn btn-primary" id="chatButton" onclick="openChatPopup('${item.itemId}', '${room_id}', '${item.sellerId}', '${sessionScope.email}')">채팅하기</button>
         	</c:when>
           	<c:otherwise>
               <!-- 로그인 안한 경우 경우에는 아무것도 표시하지 않음 -->
               <span class="empty2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
           	</c:otherwise>
       	</c:choose>
	</div>
</div>
</body>
<footer>
	<script>
		//아이템 올린 시간을 계산하여 표시하는 함수
		function calculateListingTime(listingDate) {
		    // 현재 시간
		    var now = new Date();
		    // 아이템 올린 시간
		    var itemDate = new Date(listingDate);
		    // 시간 차이 계산 (밀리초 단위)
		    var timeDiff = now - itemDate;
		
		    // 시간 단위로 변환
		    var seconds = Math.floor(timeDiff / 1000);
		    var minutes = Math.floor(seconds / 60);
		    var hours = Math.floor(minutes / 60);
		    var days = Math.floor(hours / 24);
		
		    if (days > 0) {
		        return days + "일 전";
		    } else if (hours > 0) {
		        return hours + "시간 전";
		    } else if (minutes > 0) {
		        return minutes + "분 전";
		    } else {
		        return "방금 전";
		    }
		}
		
		// 아이템 올린 시간 표시
		var listingDate = "${item.listingDate}";
		var timeAgo = calculateListingTime(listingDate);
		document.getElementById("clock").innerHTML = '<i class="bi bi-clock">&nbsp;&nbsp;&nbsp;' + timeAgo + '</i>';
	</script>
	<script>
	    function openChatPopup(itemId, roomId, sellerId, buyer) {
	        var popup = window.open("/chat/chat.jsp?item_id=" + itemId + "&room_id="+ roomId + "&seller_id="+ sellerId + "&buyer=" + buyer, "ChatPopup", "width=450,height=600,top=window.innerHeight-600,left=window.innerWidth-400");
	        if (!popup) {
	            alert("팝업 창이 차단되었습니다. 팝업 차단을 해제하고 다시 시도하세요.");
	        }
	    }
	</script>
	<script type="text/javascript">
	    document.addEventListener("DOMContentLoaded", function() {
	        var isRequestSent = false; // 요청이 보내졌는지 여부를 나타내는 변수
	
	        // 클릭 이벤트 핸들러
	        document.getElementById('cartButton').addEventListener('click', function(event) {
	            // 이미 요청이 보내졌으면 더 이상의 요청을 보내지 않음
	            if (isRequestSent) {
	                event.preventDefault(); // 이벤트의 기본 동작(폼 제출 등)을 막음
	                return;
	            }
	
	            // 장바구니 담기
	            var session_id = document.getElementById("wishlist").value;
	            var id = document.getElementById("id").value;
	            var itemId = document.getElementById("itemId").value;
	            var price = document.getElementById("price").value;
	            var listingDate = document.getElementById("listingDate").value;
	            var status = document.getElementById("status").value;
	            var seller_id = document.getElementById("sellerId").value;
	            var file = document.getElementById("file").value;
	            var title = document.getElementById("title").value;
	
	            // AJAX 호출 전에 상태 변수 변경
	            isRequestSent = true;
	
	            $.ajax({
	                url: "/cart/cartadd", // 전송받을 페이지 경로
	                type: "POST", // 데이터 전송 방식
	                data: {
	                    session_id: session_id,
	                    id: id,
	                    itemId: itemId,
	                    price: price,
	                    listingDate: listingDate,
	                    status: status,
	                    seller_id : seller_id,
	                    file : file,
	                    title : title
	                },
	                dataType: "text", // 서버 응답의 형식
	                success: function(num) { // 성공일 경우
	                	if(num == 0) { //장바구니에 같은 상품 존재 x
	             			alert('위시리스트에 상품이 추가되었습니다.');     			
	             	    }else {
	             			alert('이미 상품이 위시리스트에 있습니다.');
	             		}
	                	history.back();
	                },
	                error: function() { // 실패일 경우
	                    alert("오류가 발생했습니다.");
	                },
	                complete: function() { // 요청 완료 후
	                    // 요청 완료 후 상태 변수 다시 false로 설정
	                    isRequestSent = false;
	                }
	            });
	
	            // 버튼 클릭 이벤트의 기본 동작(폼 제출 등)을 막음
	            event.preventDefault();
	        });
	    });
	</script>
    <%@ include file="/include/footer.jsp" %>
</footer>
</html>
