<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>QnA</title>
    <%@ include file="/include/header.jsp" %>
    <style>
        /* 푸터를 화면 하단에 고정 */
        footer {
            position: fixed;
            bottom: 0;
            width: 100%;
        }
        .container7 {
            padding-top: 20px; /* 위쪽 여백 설정 */
            width: 100%;
            text-align: center; /* 가운데 정렬 설정 */
        }
        h2 {
            margin-top: 20px; /* 아래쪽 여백 설정 */
            margin-bottom: 40px; /* h2 태그 아래 여백 설정 */
        }
        /* 표의 너비를 페이지의 60%로 설정 */
        table, th {
            width: 60%;
            text-align: center;
        }
        /* 글쓰기 버튼 스타일 */
        .write-button {
            margin-top: 20px;
            float: right; /* 오른쪽으로 이동 */
        }
        .reply {
            display: inline-block;
            margin-right: 5px;
            
        }
    </style> 	
</head>
<body>
<div class="container7">
    <h2>문의사항</h2>
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <div align="left"><b>Total : ${cnt }</b></div>
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th style="width:10%;">No</th>
                        <th style="width:40%;">Subject</th>
                        <th style="width:20%;">Signdate</th>
                        <th style="width:30%;">Writer</th>
                    </tr>
                </thead>
                <tbody>
                <c:set var="number" value="${number}" />
				<c:forEach var="qna" items="${list}">
				    <tr>
				        <td>${number}</td>
				        <td>
				            <c:if test="${qna.restep ne 0}">
				                <span class="reply"><i class="bi bi-arrow-return-right"></i></span>
				            </c:if>
				            <a style="color:black; cursor:pointer;" onclick="checkPassword(${qna.num})">${qna.subject }</a>
				        </td>
				        <td>${qna.regdate}</td>
				        <td>${qna.writer}</td>
				    </tr>
    			<c:set var="number" value="${number - 1}" /> 
				</c:forEach>


                </tbody>
            </table> 
            <!-- 글쓰기 버튼 -->
             <c:choose>
             	<c:when test="${not empty sessionScope.email}">
             		<a href="/board/qnaWrite?parentId=${parentId}" ><button class="btn btn-primary write-button">글쓰기</button></a>
             	</c:when>
        	 	<c:otherwise>
       				 <!-- 세션 이메일값이 존재하지 않는 경우 아무것도 표시하지 않음 -->
    			</c:otherwise>	
 			 </c:choose>
        </div>
    </div>
    
    <!-- 페이징 부분 -->
	<div class="row justify-content-center">
	    <ul class="pagination">
	        <%-- 이전 페이지로 이동하는 링크 --%>
	        <li class="page-item ${pageNum eq 1 ? 'disabled' : ''}">
	            <a class="page-link" href="/board/QnA?pageNum=${pageNum - 1}" tabindex="-1" aria-disabled="true">이전</a>
	        </li>
	        <%-- 페이지 번호 표시 --%>
	         <c:forEach var="i" begin="1" end="${(cnt + pageSize - 1) / pageSize}">
              <%-- 현재 페이지와 end 페이지가 같은 경우에만 출력 --%>
    			<c:if test="${pageNum eq i}">
        			<li class="page-item ${pageNum eq i ? 'active' : ''}">
            		<a class="page-link" href="/board/QnA?pageNum=${i}">${i}</a>
        			</li>
   				 </c:if>
			</c:forEach>
	        <%-- 다음 페이지로 이동하는 링크 --%>
	        <li class="page-item ${pageNum * pageSize ge count ? 'disabled' : ''}">
	            <a class="page-link" href="/board/QnA?pageNum=${pageNum + 1}">다음</a>
	        </li>
	    </ul>
	</div>
</div>
</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
        function checkPassword(qnaNum) {
            var passwordInput = prompt("비밀번호를 입력하세요:");
    
            if (passwordInput === null) {
                // 사용자가 취소 버튼을 누른 경우
                alert("비밀번호 입력이 취소되었습니다.");
                return; // 함수 종료
            }
    
            // AJAX를 사용하여 서버에서 비밀번호를 받아옴
            $.ajax({
                type: "GET",
                url: "/board/getpassword", // 비밀번호를 받아오는 서버의 URL
                data: { qnaNum: qnaNum }, // 게시글 번호 전달
                success: function(response) {
                    var correctPassword = response.password;
    
                    if (passwordInput === correctPassword) {
                        // 비밀번호가 일치하는 경우
                        window.location.href = "/board/qnaView?num=" + qnaNum;
                    } else {
                        // 비밀번호가 일치하지 않는 경우
                        alert("잘못된 비밀번호입니다.");
                    }
                },
                error: function() {
                    // AJAX 요청에 실패한 경우
                    alert("비밀번호를 가져오는 데 실패했습니다.");
                }
            });
        }
    </script>
<footer>
    <%@ include file="/include/footer.jsp" %>
</footer>
</html>
