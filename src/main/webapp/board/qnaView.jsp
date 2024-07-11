<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>QnA 상세보기</title>
    <%@ include file="/include/header.jsp" %>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <style>
        /* 푸터를 화면 하단에 고정 */
        footer {
            position: fixed;
            bottom: 0;
            width: 100%;
        }
        .container9{
        	padding-top: 50px;
        	width: 100%;
        	height: 100%;
        }
        #panel-info{
        	height: 450px;
        }
        #pl-footer{
        	background-color:#D9EDF7;
       		position: relative;
            margin-top: 268px;
        }
        .btn_gr{
        	position: relative;
        	float: right;
        	margin-top: -45px;
        }
    </style>
</head>
<body>
<div class="container9">
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <div class="panel panel-info" id="panel-info">
                <div class="panel-heading">
                    <h3 class="panel-title">${qna.subject }</h3>
                </div>
                <div class="panel-body">
                    <p>${qna.content }</p>
                </div>
                <div class="panel-footer" id="pl-footer">
                    <p><strong>작성날짜 : </strong>${qna.regdate}</p>
                    <c:choose>
		                <c:when test="${qna.file ne ''}">
		            		<p><strong>첨부파일 : </strong><a href="/upload_notice/${qna.file }" >${qna.file}</a></p>               
		            	</c:when>
		                <c:otherwise>
		                    <p><strong>첨부파일 : </strong><span style="color:red;">첨부파일이 없습니다.</span></p> 
		                </c:otherwise>
		            </c:choose>
		            <!-- 수정 및 삭제 버튼 -->
                    <c:choose>
		                <c:when test="${not empty sessionScope.level and sessionScope.level eq '10' or sessionScope.email eq qna.writer}">
				            <div class="btn_gr">
				            	<a href="/board/qnaReply?num=${qna.num}"><button class="btn btn-success">답글</button></a>
							    <a href="/board/qnaModify?num=${qna.num}"><button class="btn btn-primary">수정</button></a>
							    <a href="/board/qnaDelete?num=${qna.num}"><button class="btn btn-danger">삭제</button></a>
							</div>
		            	</c:when>
		                <c:otherwise>
		                    <!-- 관리자가 아닌 경우에는 아무것도 표시하지 않음 -->
		                </c:otherwise>
		            </c:choose>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<footer>
    <%@ include file="/include/footer.jsp" %>
</footer>
</html>
