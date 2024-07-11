<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/include/header.jsp" %>
    <style>
        /* 푸터를 화면 하단에 고정 */
        footer {
            position: fixed;
            bottom: 0;
            width: 100%;
        }
		#buy{
			position:relative;
			top: -380px;
		}
        /* 판매하기 버튼 스타일 */
        .sell-button {
            position: fixed;
            right: 100px;
            bottom: 300px;
            z-index: 9999; /* 다른 요소 위에 표시하기 위해 */
        } 
		.page{
			position: fixed;
		}
    </style>
    <script>
		$(document).ready(function() {
		    // 페이지 로드 시 저장된 탭 상태 복원
		    var activeTab = localStorage.getItem('activeTab');
		    if (activeTab) {
		        $('.nav-tabs a[href="' + activeTab + '"]').tab('show');
		    }
		
		    // 탭 변경 시 탭 상태 저장
		    $('.nav-tabs a').on('shown.bs.tab', function(e) {
		        var activeTab = $(e.target).attr('href');
		        localStorage.setItem('activeTab', activeTab);
		    });
		});
	</script>
</head>
<body>
    <div class="container" style="margin-top: 40px;">
        <ul class="nav nav-tabs nav-product-tabs">
            <li class="nav-item">
                <a class="nav-link active" data-toggle="tab" href="#selling">판매내역</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" data-toggle="tab" href="#buy">구매내역</a>
            </li>
        </ul>
        <div class="tab-content" style="posision: relative;">
            <div id="selling" class="tab-pane fade show active">
                <div class="row">
                    <%-- 판매 목록 표시 부분 --%>
                    <c:forEach var="item" items="${list1}">
                        <div class="col-md-3 col-sm-4">
                            <div class="single-product">
                                <div class="product-block">
                                    <a href="/sell/view?item_id=${item.itemId}&pageNum=${pageNum}"><img src="/upload_item/${item.file1}" alt="" class="thumbnail"></a>
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
	            <!-- 페이징 부분 -->
	            <div class="page">
				<div class="row justify-content-center">
				    <ul class="pagination">
				        <%-- 이전 페이지로 이동하는 링크 --%>
				        <li class="page-item ${pageNum eq 1 ? 'disabled' : ''}">
				            <a class="page-link" href="/user/selling?pageNum=${pageNum - 1}" tabindex="-1" aria-disabled="true">이전</a>
				        </li>
				        <%-- 페이지 번호 표시 --%>
				        <c:forEach var="i" begin="1" end="${(count1 + pageSize - 1) / pageSize}">
				            <li class="page-item ${pageNum eq i ? 'active' : ''}">
				                <a class="page-link" href="/user/selling?pageNum=${i}">${i}</a>
				            </li>
				        </c:forEach>
				        <%-- 다음 페이지로 이동하는 링크 --%>
				        <li class="page-item ${pageNum * pageSize ge count1 ? 'disabled' : ''}">
				            <a class="page-link" href="/user/selling?pageNum=${pageNum + 1}">다음</a>
				        </li>
				    </ul>
				</div>
				</div>
            </div>
            <div id="buy" class="tab-pane fade">
                <div class="row">
                    <%-- 구매 목록 표시 부분 --%>
                    <c:forEach var="item" items="${list2}">
                        <div class="col-md-3 col-sm-4">
                            <div class="single-product">
                                <div class="product-block">
                                    <a href="/sell/view?item_id=${item.itemId}&pageNum=${pageNum}"><img src="/upload_item/${item.file1}" alt="" class="thumbnail"></a>
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
	            <!-- 페이징 부분 -->
	            <div class="page">
				<div class="row justify-content-center">
				    <ul class="pagination">
				        <%-- 이전 페이지로 이동하는 링크 --%>
				        <li class="page-item ${pageNum eq 1 ? 'disabled' : ''}">
				            <a class="page-link" href="/user/selling?pageNum=${pageNum - 1}" tabindex="-1" aria-disabled="true">이전</a>
				        </li>
				        <%-- 페이지 번호 표시 --%>
				        <c:forEach var="i" begin="1" end="${(count2 + pageSize - 1) / pageSize}">
				            <li class="page-item ${pageNum eq i ? 'active' : ''}">
				                <a class="page-link" href="/user/selling?pageNum=${i}">${i}</a>
				            </li>
				        </c:forEach>
				        <%-- 다음 페이지로 이동하는 링크 --%>
				        <li class="page-item ${pageNum * pageSize ge count2 ? 'disabled' : ''}">
				            <a class="page-link" href="/user/selling?pageNum=${pageNum + 1}">다음</a>
				        </li>
				    </ul>
				</div>
                </div>
            </div>
        </div>
    </div>
    <!-- 판매하기 버튼 -->
    <a href="/sell/sellitem" class="btn btn-primary sell-button" data-toggle="tooltip" data-placement="left" title="판매하기">
        <i class="bi bi-bag-plus-fill"></i>
    </a>
</body>
<footer>
    <%@ include file="/include/footer.jsp" %>
</footer>
</html>
