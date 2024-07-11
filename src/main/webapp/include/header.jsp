<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dove Market</title>
    <%@ include file="/include/lib.jsp" %>
    <style type="text/css">
    #myModal {
    	z-index: 1050; /* 모달 창이 다른 요소 위에 올라가도록 z-index 값을 조정합니다. */
	}
	.modal-backdrop {
    	z-index: 1040; /* 모달 배경이 다른 요소에 의해 가려지지 않도록 z-index 값을 조정합니다. */
	} 
    </style>
    <!-- 모달 열기 함수 -->
    <script>
        $(document).ready(function(){
            function openModal(url) {
                jQuery('#myModal').modal('show');
                jQuery('#myModal .modal-body').load(url); // 해당 URL을 로드하여 모달 내용으로 표시
            }

            // 모달 닫기 함수
            function closeModal() {
                jQuery('#myModal').modal('hide');
            }

            window.openModal = openModal;
            window.closeModal = closeModal;
        });
    </script>
</head>
<body>
    <!-- 모달 창 -->
    <div id="myModal" class="modal fade" role="dialog">
    <div class="modal-dialog modal-lg"> <!-- modal-lg 클래스를 추가하여 큰 모달 창으로 설정 -->
        <!-- 모달 내용 -->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <!-- 모달 내용이 여기에 로드됨. -->
            </div>
        </div>
	</div>
	</div>
    <!--top -->
    <div class="top-bar">
        <div class="container">
            <div class="row">
				<div class="col-md-6">
				    <div class="logo">
				        <a href="/">
				            <img src="/img/dovemarket_logo_p_1-removebg-preview.png" alt="logo" style="width: 100px;">
				        </a>
				    </div>
				</div>
                <div class="col-md-6">
                    <div class="action pull-right">
                        <ul style="margin-top: 14px;">
                            <c:choose>
                                <c:when test="${not empty sessionScope.level and sessionScope.level eq '10'}">
                                    <li><a href="/admin/"><i class="bi bi-person-circle"></i>관리자</a></li>
                                </c:when>
                                <c:otherwise>
                                    <!-- 관리자가 아닌 경우에는 아무것도 표시하지 않음 -->
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${not empty sessionScope.email}">
                                    <li><a href="/user/logout"><i class="bi bi-box-arrow-right"></i>로그아웃</a></li>
                                   	<li><a href="/user/selling"><i class="bi bi-bag-heart"></i>판매/구매내역</a></li>
                            		<li><a href="/user/chatList"><i class="bi bi-chat-dots"></i>채팅</a></li>
                                    <c:choose>
	                                    <c:when test="${not empty sessionScope.login_status and sessionScope.login_status eq '소셜'}">
	                                    	<li><a href="/social/modify"><i class="bi bi-pencil-square"></i>${sessionScope.name}님</a></li>
	                                	</c:when>
	                                	<c:otherwise>
	                                    	<li><a href="/user/modify"><i class="bi bi-pencil-square"></i>${sessionScope.name}님</a></li>
	                                	</c:otherwise>
                                	</c:choose>
                                </c:when>
                                <c:otherwise>
                                    <li><a href="javascript:void(0);" onclick="openModal('/user/join');"><i class="fa fa-user-plus"></i>회원가입</a></li>
                                    <li><a href="javascript:void(0);" onclick="openModal('/user/login');"><i class="fa fa-user"></i>로그인</a></li>
                                </c:otherwise> 
                            </c:choose>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--end top-->
</body>
</html>
