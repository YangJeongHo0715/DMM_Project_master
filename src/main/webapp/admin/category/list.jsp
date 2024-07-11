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
</script>
<div class="container">
	<h1>카테고리관리 페이지입니다.</h1>
	<div class="content">
		<h2>카테고리 목록</h2>
		<div align=right>
		<span><a href="#" onclick="openModal('/admin/category/categoryadd')"><i class="fa fa-user-plus"></i>카테고리 추가</a></span>
		</div>
		<div>
			<div>총 카테고리 수 : ${count }</div>
			<div>
            	<form method="get">
            	<div align=right>
            		<span>
           				<select name="field" style="height:20px;">
							<option value="ca_id" <c:if test="${field == 'ca_id' }">selected</c:if>>분류코드</option>
							<option value="ca_name" <c:if test="${field == 'ca_name' }">selected</c:if>>분류명</option>
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
				<td>No</td>
				<td>분류코드</td>
				<td>분류명</td>
				<td>판매가능</td>
				<td>관리</td>
			</tr>
			<c:set var="number" value="${number }" />
			<c:forEach var="list" items="${list }">
			<form action="modify" method="post">
			<input type="hidden" name="number" value="${number }">
			<tr height="24">
				<td align="center" style="color:black">${number }</td>
				<td align="center" style="color:black">
					<!-- 들여쓰기 설명 - fn:length() - 컬렉션의 항목 수 또는 문자열의 문자 수를 반환합니다. -->
					<c:set var="id_len" value="${list.caId }" />
					<c:forEach var="id_num" begin="1" end="${fn:length(id_len)-2}" step="1">
						&nbsp;
					</c:forEach>
					<input name="ca_id" value="${list.caId }" class="ca_id" readonly></td>
				<td align="center" style="color:black"><input name="ca_name" value="${list.caName }"></td>
				<td align="center" style="color:black">
					<input type="radio" name="ca_use[${number }]" value="Y" <c:if test='${list.caUse eq "Y"}'>checked</c:if>>Y
					<input type="radio" name="ca_use[${number }]" value="N" <c:if test='${list.caUse eq "N"}'>checked</c:if>>N
				</td>
				<td align="center" style="color:black">
					<button>수정</button>
					<input type="button" value="삭제" onclick="location.href='delete?ca_id=${list.caId }'">
				</td>
			</tr>
			<tr><td colspan=5 height=1 bgcolor="#e5ecef"></td></tr>
			</form>
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
		<div class="content">
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
					
						<!-- 다음 페이지 블럭이 존재 할 경우 startPage 값 변경 부분-->
						<c:if test="${pageNum > pageBlock }">
							<!-- 결과를 정수형으로 리턴 받아야 하기 대문에 fmt -->
							<fmt:parseNumber var="result" value="${pageNum / pageBlock - (pageNum % pageBlock == 0 ? 1:0) }" integerOnly="true"/>
							<c:set var="startPage" value="${result * pageBlock + 1 }" />
						</c:if>	
					
						<!-- endPage 값 설정 부분 -->
						<c:set var="endPage" value="${startPage + pageBlock - 1 }" />
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