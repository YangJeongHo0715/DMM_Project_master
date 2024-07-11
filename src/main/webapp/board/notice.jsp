<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>공지사항</title>
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
    </style> 
</head>
<body>
<div class="container7">
	<h2>공지사항</h2>
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
        	<div align="left"><b>Total : ${count }</b></div>
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
                <c:set var="number" value="${number }" />
                <c:forEach var="list" items="${list }">
                    <tr>
                        <td>${number}</td>
                        <td><a style="color:black;"href="/board/noticeView?uid=${list.uid}&pageNum=${pageNum}">${list.subject}</a></td>
                        <td><c:set var="formattedDate" value="${fn:substring(list.signdate, 0, 10)}" />${formattedDate}</td>
                        <td>${list.writer}</td>
                    </tr>
                <c:set var="number" value="${number - 1 }" />
                </c:forEach>
                </tbody>
            </table>
            <!-- 글쓰기 버튼 -->
            <c:choose>
                <c:when test="${not empty sessionScope.level and sessionScope.level eq '10'}">
            		<a href="/board/noticeWrite" ><button class="btn btn-primary write-button">글쓰기</button></a>               
            	</c:when>
                <c:otherwise>
                    <!-- 관리자가 아닌 경우에는 아무것도 표시하지 않음 -->
                </c:otherwise>
            </c:choose>
        </div>
    </div>
 	<!-- 페이징 부분 -->
	<div class="row justify-content-center">
	    <ul class="pagination">
	        <%-- 이전 페이지로 이동하는 링크 --%>
	        <li class="page-item ${pageNum eq 1 ? 'disabled' : ''}">
	            <a class="page-link" href="/board/notice?pageNum=${pageNum - 1}" tabindex="-1" aria-disabled="true">이전</a>
	        </li>
	        <%-- 페이지 번호 표시 --%>
	        <c:forEach var="i" begin="1" end="${(count + pageSize - 1) / pageSize}">
	            <li class="page-item ${pageNum eq i ? 'active' : ''}">
	                <a class="page-link" href="/board/notice?pageNum=${i}">${i}</a>
	            </li>
	        </c:forEach>
	        <%-- 다음 페이지로 이동하는 링크 --%>
	        <li class="page-item ${pageNum * pageSize ge count ? 'disabled' : ''}">
	            <a class="page-link" href="/board/notice?pageNum=${pageNum + 1}">다음</a>
	        </li>
	    </ul>
	</div>
</div>
</body>
<footer>
    <%@ include file="/include/footer.jsp" %>
</footer>
</html>
