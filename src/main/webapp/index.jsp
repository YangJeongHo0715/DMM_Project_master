<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/header.jsp" %>
<!-- 위치정보 -->
<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=7efcfd2a287e0858fbb479563959d613&libraries=services"></script>
<script src="/js/geolocation.js"></script>
<script src="/js/indexlocation.js"></script>
<style>
	.carousel-inner .item {
	    background-size: cover; /* 배경 이미지를 화면에 맞게 조절합니다. */
	    background-position: center; /* 배경 이미지를 가운데 정렬합니다. */
	}
    /* 화면 우측에 고정된 작은 상자의 스타일 */
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
</style>
<!-- 화면 우측에 고정된 상자 -->
<div id="wishlist">
	<div>
		<span><a href="/cart/list?session_id=${sessionScope.wishlist}"><i class="bi bi-bag-heart"></i>위시리스트</a></span>
	</div>    
</div> 
<div class="container2">
    <!-- search -->
    <div class="row justify-content-center mt-3">
        <div class="col-md-6">
            <form class="search-container">
                <input  name="search" value="${search }" class="search-input form-control me-2" type="search" placeholder="검색어를 입력해주세요." aria-label="Search">
                <button class="search-button btn btn-outline-success" type="submit"><i class="bi bi-search"></i></button>
            </form>
        </div>
    </div>
    <!-- end search -->
    <!-- location -->
    <div class="current-location">
        <input type="hidden" id="latitude">
        <input type="hidden" id="longitude">
        <i class="bi bi-geo-alt-fill"></i>
        <h4 id="location">위치 정보 불러오는 중...</h4>
    </div>
    <!-- end location -->
</div>
<!--slider-->
<div class="slider">
    <div class="container">
        <div class="row">
            <div class="col-md-8 col-sm-8">
                <div class="slider big-slider">
                    <div id="featured" class="carousel slide" data-ride="carousel">
				        <!-- Indicators -->
				        <ol class="carousel-indicators">
				            <li data-target="#featured" data-slide-to="0" class="active"></li>
				            <li data-target="#featured" data-slide-to="1"></li>
				            <li data-target="#featured" data-slide-to="2"></li>
				            <li data-target="#featured" data-slide-to="3"></li>
				            <li data-target="#featured" data-slide-to="4"></li>
				        </ol>
				        <!-- Wrapper for slides -->
				        <div class="carousel-inner" role="listbox">
				            <div class="item active" style="background-image:url('img/155044dd.png')"></div>
				            <div class="item" style="background-image:url('img/155118.png')"></div>
				            <div class="item" style="background-image:url('img/155154.png')"></div>
				            <div class="item" style="background-image:url('img/155215.png')"></div>
				            <div class="item" style="background-image:url('img/155247.png')"></div>
				        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-4 col-sm-4">
                <div class="slider small-slider">
                    <div id="small-featured" class="carousel slide" data-ride="carousel">
						<!-- Indicators -->
				        <ol class="carousel-indicators">
				            <li data-target="#featured" data-slide-to="0" class="active"></li>
				            <li data-target="#featured" data-slide-to="1"></li>
				            <li data-target="#featured" data-slide-to="2"></li>
				            <li data-target="#featured" data-slide-to="3"></li>
				            <li data-target="#featured" data-slide-to="4"></li>
				        </ol>
				        <!-- Wrapper for slides -->
				        <div class="carousel-inner" role="listbox">
				            <div class="item active" style="background-image:url('img/ghhsdf1.JPG')"></div>
				            <div class="item" style="background-image:url('img/Scsad1.JPG')"></div>
				            <div class="item" style="background-image:url('img/sfsavawf1.JPG')"></div>
				            <div class="item" style="background-image:url('img/szvdg211.JPG')"></div>
				            <div class="item" style="background-image:url('img/wwqasfqf1.JPG')"></div>
				        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--end slider-->
<!-- items -->
<div class="featured-items">
    <div class="container">
        <div class="row">
            <ul class="nav nav-tabs nav-product-tabs">
                <li class="active"><a href="#best-seller" data-toggle="tab">인기 상품</a></li>
                <li><a href="#trending" data-toggle="tab">최근 상품</a></li>
                <li class="pull-right collection-url"><a href="/viewAll">View All <i class="fa fa-long-arrow-right"></i></a></li>
            </ul>
            <div class="tab-content">
                <%-- Best 목록 표시 부분 --%>
                <div class="tab-pane active" id="best-seller">
                	<c:choose>
				        <c:when test="${empty list_1}">
				            <div class="alert alert-warning" role="alert">
				                '${search}' 와(과) 관련된 상품이 없습니다.
				            </div>
				        </c:when>
				        <c:otherwise>
				            <c:forEach var="item" items="${list_1}">
				                <div class="col-md-3 col-sm-4">
				                    <div class="single-product">
				                        <div class="product-block">
				                            <a href="/user/sellingView?item_id=${item.itemId}&seller_id=${item.sellerId}&pageNum=${pageNum}">
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
				        </c:otherwise>
                	</c:choose>
                </div>
                <%-- Best 목록 표시 끝 --%>
                <!-- trending 목록 표시 -->
				<div class="tab-pane fade" id="trending">
				    <c:choose>
				        <c:when test="${empty list_2}">
				            <div class="alert alert-warning" role="alert">
				                '${search}' 와(과) 관련된 상품이 없습니다.
				            </div>
				        </c:when>
				        <c:otherwise>
				            <c:forEach var="nb_item" items="${list_2}">
				                <div class="col-md-3 col-sm-4">
				                    <div class="single-product">
				                        <div class="product-block">
				                            <a href="/user/sellingView?item_id=${nb_item.itemId}&pageNum=${pageNum}"><img src="/upload_item/${nb_item.file1}" alt="" class="thumbnail"></a>
				                            <div class="product-description text-center">
				                                <p class="title">${nb_item.title}</p>
				                                <p class="title">(${nb_item.status})</p>
				                                <p class="price"><fmt:formatNumber value="${nb_item.price}" pattern="#,###" />원</p>
				                            </div>
				                        </div>
				                    </div>
				                </div>
				            </c:forEach>
				        </c:otherwise>
				    </c:choose>
				</div>
				<!-- trending 목록 표시 끝-->
            </div>
        </div>
    </div>
</div>
<!-- end items -->
<%@ include file="/include/footer.jsp" %>