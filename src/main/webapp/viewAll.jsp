<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/include/header.jsp" %>
    <style>
        /* 추가적인 CSS 스타일을 여기에 작성하세요 */
        .sidebar {
            position: fixed;
            top: 18%; /* 원하는 위치로 조정 */
            left: 60px; /* 우측 여백 조정 */
            width: 200px; /* 사이드바 너비 조정 */
            height: auto;
            background-color: #F5F5F5;
            border: 1px solid #DDDDDD;
            border-radius: 10px;
            padding: 10px;
            display: flex;
        }
        #wishlist {
			position: fixed;
			top: 30%; /* 원하는 위치로 조정 */
			right: 200px;
			width: 100px;
			height: 160px;
			background-color: #F5F5F5;
			border: 1px solid #DDDDDD;
			border-radius: 10px;
			/* 텍스트를 가운데 정렬 */
			display: flex;
			justify-content: center;
			align-items: center;
		}
        .main-content {
            margin-right: 250px; /* 사이드바 너비 + 여백만큼 메인 콘텐츠를 우측으로 밀어줍니다. */
        }
        .justify-content-center {
            bottom: 300px;
            margin-left: 55%;
        }
    </style>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<!-- 화면 우측에 고정된 상자 -->
<div id="wishlist">
	<div>
		<span><a href="/cart/list?session_id=${sessionScope.wishlist}"><i class="bi bi-bag-heart"></i>위시리스트</a></span>
	</div>    
</div> 
<div class="container">
    <div class="row">
        <div class="col-md-9 main-content">
            <!-- 여기에 메인 콘텐츠 내용을 추가하세요 -->
            <div class="container3">
                <div class="featured-items">
                    <div class="container">
                        <div class="row" id="product-list">
                            <!-- 상품 목록 표시 -->
                            <c:forEach var="item" items="${list}">
                                <div class="col-md-3 col-sm-4">
                                    <div class="single-product">
                                        <div class="product-block">
                                            <a href="/user/sellingView?item_id=${item.itemId}&pageNum=${pageNum}">
                                                <img src="/upload_item/${item.file1}" alt="" class="thumbnail">
                                            </a>
                                            <div class="product-description text-center">
                                                <p class="title">${item.title}</p>
                                                <p class="title">(${item.status})</p>
                                                <p class="price"><fmt:formatNumber value="${item.price}" pattern="#,###" />원</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
                <!-- 페이징 부분 -->
                <div class="justify-content-center">
                    <ul class="pagination">
                        <!-- 이전 페이지로 이동하는 링크 -->
                        <li class="page-item ${pageNum eq 1 ? 'disabled' : ''}">
                            <c:choose>
                                <c:when test="${empty param.ca_id}">
                                    <a class="page-link" href="/viewAll?pageNum=${pageNum - 1}" tabindex="-1" aria-disabled="true">이전</a>
                                </c:when>
                                <c:otherwise>
                                    <a class="page-link" href="/viewAll?ca_id=${param.ca_id}&pageNum=${pageNum - 1}" tabindex="-1" aria-disabled="true">이전</a>
                                </c:otherwise>
                            </c:choose>
                        </li>
                        <!-- 페이지 번호 표시 -->
                        <c:forEach var="i" begin="1" end="${(count + pageSize - 1) / pageSize}">
                            <li class="page-item ${pageNum eq i ? 'active' : ''}">
                                <c:choose>
                                    <c:when test="${empty param.ca_id}">
                                        <a class="page-link" href="/viewAll?pageNum=${i}">${i}</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a class="page-link" href="/viewAll?ca_id=${param.ca_id}&pageNum=${i}">${i}</a>
                                    </c:otherwise>
                                </c:choose>
                            </li>
                        </c:forEach>
                        <!-- 다음 페이지로 이동하는 링크 -->
                        <li class="page-item ${pageNum * pageSize ge count ? 'disabled' : ''}">
                            <c:choose>
                                <c:when test="${empty param.ca_id} ">
                                    <a class="page-link" href="/viewAll?pageNum=${pageNum + 1}">다음</a>
                                </c:when>
                                <c:otherwise>
                                    <a class="page-link" href="/viewAll?ca_id=${param.ca_id}&pageNum=${pageNum + 1}">다음</a>
                                </c:otherwise>
                            </c:choose>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <!-- 사이드바 -->
        <div class="col-md-3">
            <div class="sidebar">
                <ul class="nav nav-pills nav-stacked">
                <li role="presentation"><a href="/viewAll">전체</a></li>
                    <!-- 카테고리 목록 표시 -->
                    <c:forEach var="category1" items="${categories1}">
                        <li class="category1" role="presentation"><a href="?ca_id=${category1.caId}">${category1.caName}</a></li>
	                    <c:forEach var="category2" items="${categories2}">    	
	                    	<c:if test="${category1.caId eq fn:substring(category2.caId,0,2)}">
	                        <li role="presentation"><a href="?ca_id=${category2.caId}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${category2.caName}</a></li>
	                        </c:if>
	                    </c:forEach>
                    </c:forEach>
                </ul>
            </div>
        </div>  
    </div> 
</div>
<script>
    // jQuery를 사용하여 이벤트 처리
    $(document).ready(function(){
        // 카테고리 1을 클릭했을 때
        $(".category1").click(function(){
            // 해당 카테고리의 드롭다운 메뉴가 열리도록 함
            $(this).toggleClass("open");
        });
    });
</script>
</body>
<footer>
    <%@ include file="/include/footer.jsp" %>
</footer>
</html>
