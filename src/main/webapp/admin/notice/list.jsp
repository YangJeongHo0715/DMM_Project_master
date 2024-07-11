<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/admin/include/header_popup.jsp" %>
<%@ include file="/admin/include/header.jsp" %>
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
    	window.open("view?uid="+str,"view","width=600,height=650,left="+popupX+",top="+popupY);
    }
  	//게시글 수정
    function popup_modify(str){
    	window.open("modify?uid="+str,"view","width=600,height=650,left="+popupX+",top="+popupY);
    }
    
    //게시글 삭제
    function id_delete(str){
    	result = confirm("삭제 하시겠습니까?");
    	
    	if(result == true){
    		location.href="delete?uid="+str;
    	}    	
    }    
</script>
    <div class="container">
        <h1>공지사항 관리 페이지 입니다.</h1>
        <div class="content">
            <h2>게시판 목록</h2>
            <div align=right>
            <span><a href="javascript:void(0);" onclick="openModal('/admin/notice/noticeadd')"><i class="fa fa-user-plus"></i>공지글추가</a></span>
            </div>
            <div>
            	<div>총 게시물 수 : ${count }</div>
            	<div>
            		<form>
            		<div align=right>
            			<span>
            				<select name="field" style="height:20px;">
								<option value="subject" <c:if test="${field == 'subject' }">selected</c:if>>제목</option>
								<option value="content" <c:if test="${field == 'content' }">selected</c:if>>내용</option>
							</select>
							<input name="search" value="${search }" style="height:22px;">
							<button>검색</button>
            			</span>
            		</div>
            		</form>
            	</div>
            </div>
        </div>
        <div class="content">
        <table style="width:100%; height:15px; color:white; font-size:12px;">
        	<tr bgcolor="#40BEA7" height="30">
        		<td style="color:white">no.</td>
        		<td style="color:white">작성자</td>
        		<td style="color:white">제목</td>
        		<td style="color:white">올라온 날짜</td>
        		<td style="color:white">mode</td>
        	</tr>        
        <c:set var="number" value="${number }" />
			<c:forEach var="list" items="${list }"><!-- 뿌리는곳  -->
			<tr height="24">
				<td align="center">${number }</td>
				<td align="center">${list.writer }</td>
				<td align="center">${list.subject }</td>
				<td align="center">${list.signdate }</td>
				<td align="center">
					<span onclick="popup('${list.uid }')" style="cursor:pointer">[상세]</span>
					<span onclick="popup_modify('${list.uid }')" style="cursor:pointer">[수정]</span>
					<span onclick="id_delete('${list.uid }')" style="cursor:pointer">[삭제]</span>
				</td>
			</tr>
			<tr><td colspan=5 style="bgcolor:#e5ecef;"></td></tr>
			<c:set var="number" value="${number - 1 }" />
			</c:forEach>
		</table>
        </div>
        <!-- 페이징 처리 -->
	<style>
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
        <div>
        <table>
			<tr>
				<td>
					<c:if test="${count>0 }">
						<c:set var="pageCount" value="${count / pageSize + (count % pageSize == 0 ? 0 : 1) }" />
						<fmt:parseNumber var="pageCount" value="${pageCount }" integerOnly="true" />
						<!-- fmt:parseNumber : 문자열을 숫자로 변환해 주는 기능을 제공하는 태그 -->
						<!-- integerOnly : true , false 정수만 출력할 것인지를 묻는 속성 -->
					
						<!-- 2개의 변수 초기화 -->
						<c:set var="startPage" value="${1 }" />
						<c:set var="pageBlock" value="${3 }" />	
					
						<!-- 두번째 이상 블럭을 실행 할 경우 startPage 값 변경 부분-->
						<c:if test="${pageNum > pageBlock }">
							<!-- 결과를 정수형으로 리턴 받아야 하기 때문에 fmt -->
							<fmt:parseNumber var="result" value="${pageNum / pageBlock - (pageNum % pageBlock == 0 ? 1:0) }" integerOnly="true"/>
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
							<a href="list?pageNum=${startPage - pageBlock }&field=${field}&search=${search}">[이전] </a>
						</c:if>
					
						<!-- 페이징 링크 -->
						<c:forEach var="i" begin="${startPage }" end="${endPage }">
							<c:choose>
								<c:when test="${pageNum == i }">
									<a href="list?pageNum=${i }&field=${field}&search=${search}"><span class="page_on"><font color=white><b>${i }</b></font></span></a>
								</c:when>
								<c:otherwise>
									<a href="list?pageNum=${i }&field=${field}&search=${search}"><span class="page_off">${i }</span></a>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					
						<!-- 다음 링크 -->
						<c:if test="${endPage < pageCount }">
							<a href="list?pageNum=${startPage + pageBlock }&field=${field}&search=${search}">[다음] </a>
						</c:if>
					</c:if>
				</td>
			</tr>
		</table>
        </div>       
</div>
<%@ include file="/admin/include/footer.jsp" %>