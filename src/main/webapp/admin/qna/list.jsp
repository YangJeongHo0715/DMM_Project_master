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
        padding: 5px;
        text-align: left;
        color:black;
    }
    table th {
        background-color: #f2f2f2;
    }    
	.page_on {
		padding:0px 5px;
		color:red;
		border:1px solid #40BEA7;
		font-weight:bold;
		background-color:#40BEA7;
	}
	.page_off {
		padding:0px 5px;
		color:black;
		border:1px solid black;
	}    
</style>

<!-- 모달 창 -->
<div id="myModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeModal()">&times;</span>
        <div id="modalContent"></div>
    </div>
</div>
<script>
<!-- 필요한 스크립트를 포함 -->
    // 모달 열기
    function openModal(url) {
        var modal = document.getElementById('myModal');
        modal.style.display = 'block';

        // Ajax 요청 보내기
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                document.getElementById('modalContent').innerHTML = this.responseText;
            }
        };
        xhttp.open('GET', url, true);
        xhttp.send();
    }

    // 모달 닫기
    function closeModal() {
        var modal = document.getElementById('myModal');
        modal.style.display = 'none';
    }
    
    var popupX = (window.screen.width / 2) - (600 / 2);
    var popupY= (window.screen.height /2) - (650 / 2);
    
    //게시글 상세 보기
    function popup(str){
    	window.open("view?num="+str,"view","width=600,height=650,left="+popupX+",top="+popupY);
    }
  	//게시글 수정
    function popup_modify(str){
    	window.open("modify?num="+str,"view","width=600,height=650,left="+popupX+",top="+popupY);
    }
    
    //게시글 삭제
    function question_delete(str){
    	
    	var result = confirm("삭제 하시겠습니까? \n (※부모글 삭제 시 자식글도 삭제해주셔야 합니다※)");
    	
    	if(result == true){
    		location.href= "delete?num="+ str;
    	}    	
    } 
</script>
    <div class="container">
        <h1>QnA 게시판 입니다.</h1>
        <div class="content">
        <h2>부모글 목록</h2>
        <div>부모글 갯 수 : ${Qcount }</div>
        </div>
        <div class="content">
        <table style="width:100%; height:15px; color:white; font-size:12px;">
        	<tr bgcolor="#40BEA7" height="30">
        		<td style="color:white; width:5%">no.</td>        		
        		<td style="color:white; width:6%">그룹 번호</td>
        		<td style="color:white; width:6%">게시 여부</td>
        		<td style="color:white" >제목</td>
        		<td style="color:white; width:15%">올라온 날짜</td>        		
        		<td style="color:white; width:20%">작성자</td>
        		<td style="color:white; width:10%">mode</td>
        	</tr>        
        	<c:set var="number" value="${number }" />
			<c:forEach var="list_1" items="${list_1 }"><!-- 뿌리는곳  -->
			<tr height="24">
				<td align="center">${number }</td>				
				<td align="center">${list_1.ref }</td>
				<td align="center">
					<c:choose>
						<c:when test="${list_1.status eq '1'}">
							<font color=green>
								게시
							</font>
						</c:when>
						<c:otherwise>
							<font color=red>					
								비게시
							</font>
						</c:otherwise>
					</c:choose>
				</td>
				<td align="center">${list_1.subject }</td>
				<td align="center">${list_1.regdate }</td>				
				<td align="center">${list_1.writer }</td>
				<td align="center">
					<span onclick="popup('${list_1.num }')" style="cursor:pointer">[상세]</span>
					<span onclick="question_delete('${list_1.num }')" style="cursor:pointer">[삭제]</span>
				</td>
			</tr>
			<tr><td colspan=7 style="bgcolor:#e5ecef;"></td></tr>
			<c:set var="number" value="${number - 1 }" />
			</c:forEach>
		</table>
		<!-- 페이징 처리 -->		
        <table>
			<tr>
				<td>
					<c:if test="${Qcount>0 }">
						<c:set var="pageCount" value="${Qcount / QpageSize + (Qcount % QpageSize == 0 ? 0 : 1) }" />
						<fmt:parseNumber var="pageCount" value="${pageCount }" integerOnly="true" />
						<!-- fmt:parseNumber : 문자열을 숫자로 변환해 주는 기능을 제공하는 태그 -->
						<!-- integerOnly : true , false 정수만 출력할 것인지를 묻는 속성 -->
					
						<!-- 2개의 변수 초기화 -->
						<c:set var="startPage" value="${1 }" />
						<c:set var="pageBlock" value="${3 }" />	
					
						<!-- 두번째 이상 블럭을 실행 할 경우 startPage 값 변경 부분-->
						<c:if test="${QpageNum > pageBlock }">
							<!-- 결과를 정수형으로 리턴 받아야 하기 때문에 fmt -->
							<fmt:parseNumber var="result" value="${QpageNum / pageBlock - (QpageNum % pageBlock == 0 ? 1:0) }" integerOnly="true"/>
							<c:set var="startPage" value="${result * pageBlock + 1 }" />
						</c:if>	
					
						<!-- endPage 값 설정 부분 -->
						<c:set var="endPage" value="${startPage + pageBlock - 1 }" />
						<!-- 마지막 블럭일 경우 endPage 값 설정 부분 -->
						<c:if test="${endPage > pageCount }">
							<c:set var="endPage" value="${pageCount }" />
						</c:if>
					
						<!-- 이전 링크 -->
						<c:if test="${startPage > pageBlock }">
							<a href="list?QpageNum=${startPage - pageBlock }">[이전] </a>
						</c:if>
					
						<!-- 페이징 링크 -->
						<c:forEach var="i" begin="${startPage }" end="${endPage }">
							<c:choose>
								<c:when test="${QpageNum == i }">
									<a href="list?QpageNum=${i }"><span class="page_on"><font color=white><b>${i }</b></font></span></a>
								</c:when>
								<c:otherwise>
									<a href="list?QpageNum=${i }"><span class="page_off">${i }</span></a>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					
						<!-- 다음 링크 -->
						<c:if test="${endPage < pageCount }">
							<a href="list?pageNum=${startPage + pageBlock }">[다음] </a>
						</c:if>
					</c:if>
				</td>
			</tr>
		</table>
        </div>
        <div class="content">
        <h2>자식글 목록</h2>
        <div>자식글 갯 수 : ${Acount }</div>
        </div>
        <div class="content">
        <table style="width:100%; height:15px; color:white; font-size:12px;">
        	<tr bgcolor="#40BEA7" height="30">
        		<td style="color:white; width:5%">no.</td>
        		<td style="color:white; width:6%">그룹 번호</td>
        		<td style="color:white; width:6%">답글 순서</td>
        		<td style="color:white">제목</td>        		        		
        		<td style="color:white; width:15%">올라온 날짜</td>        		
        		<td style="color:white; width:20%">작성자</td>
        		<td style="color:white; width:10%">mode</td>        		
        	</tr>        
        	<c:set var="number2" value="${number2 }" />
			<c:forEach var="list_2" items="${list_2 }"><!-- 뿌리는곳  -->
			<tr height="24">
				<td align="center">${number2 }</td>
				<td align="center">${list_2.ref }</td>
				<td align="center">${list_2.restep }</td>
				<td align="center">${list_2.subject }</td>				
				<td align="center">${list_2.regdate }</td>				
				<td align="center">${list_2.writer }</td>
				<td align="center">
					<span onclick="popup('${list_2.num }')" style="cursor:pointer">[상세]</span>
					<span onclick="question_delete('${list_2.num }')" style="cursor:pointer">[삭제]</span>
				</td>
			</tr>
			<tr><td colspan=7 style="bgcolor:#e5ecef;"></td></tr>
			<c:set var="number2" value="${number2 - 1 }" />
			</c:forEach>
		</table>
		<!-- 페이징 처리 -->		
		<table>
			<tr>
				<td>
					<c:if test="${Acount>0 }">
						<c:set var="pageCount" value="${Acount / ApageSize + (Acount % ApageSize == 0 ? 0 : 1) }" />
						<fmt:parseNumber var="pageCount" value="${pageCount }" integerOnly="true" />
						<!-- fmt:parseNumber : 문자열을 숫자로 변환해 주는 기능을 제공하는 태그 -->
						<!-- integerOnly : true , false 정수만 출력할 것인지를 묻는 속성 -->
					
						<!-- 2개의 변수 초기화 -->
						<c:set var="startPage" value="${1 }" />
						<c:set var="pageBlock" value="${3 }" />	
					
						<!-- 두번째 이상 블럭을 실행 할 경우 startPage 값 변경 부분-->
						<c:if test="${ApageNum > pageBlock }">
							<!-- 결과를 정수형으로 리턴 받아야 하기 때문에 fmt -->
							<fmt:parseNumber var="result" value="${ApageNum / pageBlock - (ApageNum % pageBlock == 0 ? 1:0) }" integerOnly="true"/>
							<c:set var="startPage" value="${result * pageBlock + 1 }" />
						</c:if>	
					
						<!-- endPage 값 설정 부분 -->
						<c:set var="endPage" value="${startPage + pageBlock - 1 }" />
						<!-- 마지막 블럭일 경우 endPage 값 설정 부분 -->
						<c:if test="${endPage > pageCount }">
							<c:set var="endPage" value="${pageCount }" />
						</c:if>
					
						<!-- 이전 링크 -->
						<c:if test="${startPage > pageBlock }">
							<a href="list?ApageNum=${startPage - pageBlock }">[이전] </a>
						</c:if>
					
						<!-- 페이징 링크 -->
						<c:forEach var="i" begin="${startPage }" end="${endPage }">
							<c:choose>
								<c:when test="${ApageNum == i }">
									<a href="list?ApageNum=${i }"><span class="page_on"><font color=white><b>${i }</b></font></span></a>
								</c:when>
								<c:otherwise>
									<a href="list?ApageNum=${i }"><span class="page_off">${i }</span></a>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					
						<!-- 다음 링크 -->
						<c:if test="${endPage < pageCount }">
							<a href="list?ApageNum=${startPage + pageBlock }">[다음] </a>
						</c:if>
					</c:if>
				</td>
			</tr>
		</table>
        </div>          
</div>
<%@ include file="/admin/include/footer.jsp" %>