<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>상세보기</title>
    <%@ include file="/include/header.jsp" %>
    <style>
        /* 푸터를 화면 하단에 고정 */
        footer {
            position: fixed;
            bottom: 0;
            width: 100%;
			}
		.container4{
			position: relative;
			width: 100%;
			height: 100%;
			vertical-align: center;
			padding-bottom: 120px
		}
		.carousel-control.left, .carousel-control.right {
		    background: #fff;
		    border: none; /* 테두리 제거 */
		    border-radius: 50%; /* 동그라미 모양으로 변경 */
		    box-shadow: none; /* 음영 제거 */
        	}
        .btn-gr{
        	position: fixed;
        	float: right;
        	margin-left: 800px;
        	margin-top: 400px;
        }
		/* 아이콘 크기 조절 */
		.bi-heart, .bi-eye, .bi-clock {
		    font-size: 24px;
		    margin-right: 80px;
		}
    </style>
    <script type="text/javascript">
	 	// 아이템에 좋아요를 추가하는 함수
	    function likeItem(itemId) {
	        $.ajax({
	            url: "/sell/likeItem", //전송받을 페이지 경로
	            type: "post", //데이터 전송 방식
	            dataType: "text",
	            data: "itemId=" + itemId,
	            error: function () { //실패일 경우
	                console.log("실패");
	            },
	            success: function (text) { //성공일 경우
	                console.log("성공");
	                // 좋아요 수를 업데이트하고 페이지를 새로고침
	                window.location.reload();
	            }
	        });
	    }
    </script>
</head>
<body>
<div class="container4">
    <div id="myCarousel" class="carousel slide" data-ride="carousel">
        <!-- Indicators -->
        <ol class="carousel-indicators">
            <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
            <li data-target="#myCarousel" data-slide-to="1"></li>
            <li data-target="#myCarousel" data-slide-to="2"></li>
            <li data-target="#myCarousel" data-slide-to="3"></li>
            <li data-target="#myCarousel" data-slide-to="4"></li>
        </ol>
        <!-- 슬라이드 내용 -->
        <div class="carousel-inner">
            <div class="item active">
                <img class="item_img" src="/upload_item/${item.file1}" alt="First slide">
            </div>
            <div class="item">
                <img class="item_img" src="/upload_item/${item.file2}" alt="Second slide">
            </div>
            <div class="item">
                <img class="item_img" src="/upload_item/${item.file3}" alt="Third slide">
            </div>
            <div class="item">
                <img class="item_img" src="/upload_item/${item.file4}" alt="Fourth slide">
            </div>
            <div class="item">
                <img class="item_img" src="/upload_item/${item.file5}" alt="Fifth slide">
            </div>
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
        <p>${item.description}</p>
    </div>
    <!-- 수정 및 삭제 버튼 -->
	<div class="btn-gr">
	    <a href="/sell/modify?item_id=${item.itemId}"><button class="btn btn-primary">수정</button></a>
	    <a href="/sell/delete?item_id=${item.itemId}"><button class="btn btn-danger">삭제</button></a>
	</div>
</div>
</body>
<footer>
	<script src="/js/clock.js"></script>
    <%@ include file="/include/footer.jsp" %>
</footer>
</html>
