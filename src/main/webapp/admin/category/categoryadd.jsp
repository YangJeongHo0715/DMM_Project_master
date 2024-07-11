<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/admin/include/header_popup.jsp" %>
<style>
    .container_1 {
        max-width: 550px; /* 컨테이너의 최대 너비를 설정. */
        margin: auto; /* 가운데 정렬을 위해 margin을 auto. */
        padding: 20px; /* 컨테이너 내부의 여백을 추가. */
    }

    .form-group {
        margin-bottom: 20px;
        display: flex; /* 입력란과 버튼을 인라인으로 표시. */
        align-items: center; /* 입력 요소들을 수직 중앙 정렬. */
    }

    .form-group label {
        margin-right: 10px; /* 라벨과 입력 요소 사이의 간격을 설정. */
        width: 100px;
    }

    .form-group .form-control {
        flex: 1; /* 입력 요소와 버튼을 동일한 너비로 설정. */
    }

    .form-group button {
        margin-left: auto; /* 버튼을 오른쪽으로 이동. */
        width: 150px;
    }
</style>
<body>
<div class="container_1">
	<h1>카테고리 추가</h1>
	<br>
	<form name="category" action="/admin/category/categoryadd" method="post" onsubmit="return category_ok()">
		<div class="form-group">
			<label for="ca_id">분류코드</label>
		    <input type="text" class="form-control" id="ca_id" name="ca_id" required>
		</div>
		<div class="form-group">
			<label for="ca_name">분류명</label>
		    <input type="text" class="form-control" id="ca_name" name="ca_name" required>
		</div>
		<div class="form-group">
			<label for="ca_use">판매가능</label>
		    <input type="radio" id="ca_use" name="ca_use" value="Y" checked>Y
			<input type="radio" id="ca_use" name="ca_use" value="N">N
		</div>
		<button type="submit" class="btn btn-primary">카테고리 추가</button>	
	</form>
</div>
