<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <%@ include file="/include/header.jsp" %>
  <script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=7efcfd2a287e0858fbb479563959d613&libraries=services"></script>
  <script src="/js/geolocation.js"></script>
  <script src="/js/kakaogeolocation.js"></script>
	  <script>
		function cate_2(cate){
			$.ajax({
				url: "/sell/category_ok", //전송받을 페이지 경로
				type: "post", //데이터 전송 방식
				dataType: "text",
				data: "ca_id="+cate,
				error:function(){ //실패일 경우
					alert("실패");
				},
				success:function(text){ //성공일 경우
					$("#category2_result").html(text);
				}
			});
		}
		function item_ok(){
			document.submit();
		}
	</script>
	<style>
		/* 카테고리 항목들을 라벨 옆으로 일렬로 정렬하는 CSS */
	    .category-container {
	    	width: 100%;
	    	display: flex;
	      	align-items: center;
	    }
	
	    .category-label {
	      	margin-right: 10px; /* 라벨과 입력 요소 사이의 간격 조절 */
	    }
	
	    .category-select {
	    	width: 80%;
	      	flex: 1; /* 라벨 옆에 남는 공간을 모두 차지 */
	    }
  	</style>
</head>
<body>
<div class="container" style="margin-top: 40px;">
	<h2>상품 판매</h2>
	<form action="/sell/sellitem" method="POST" enctype="multipart/form-data">
		 <div class="form-group">
		    <label for="title">상품 제목</label>
		    <input type="text" class="form-control" id="title" name="title" placeholder="상품 제목을 입력하세요">
		</div>
		<!-- 위도(hidden) 입력 폼 -->
        <input type="hidden" id="latitude" name="latitude">
        <!-- 경도(hidden) 입력 폼 -->
        <input type="hidden" id="longitude" name="longitude">
		<div id="form-group">
		    <label for="title">상품이미지</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;		   
			<!-- 첫번째 이미지 업로드 컨테이너 -->
			<div class="upload-container">
			    <div class="preview-images" id="preview-images1"></div>
			    <button type="button" class="btn-upload" onclick="document.getElementById('file1').click()">
			        <svg class="icon" width="48" height="48" viewBox="0 0 48 48" fill="currentColor" preserveAspectRatio="xMidYMid meet">
			            <path d="M11.952 9.778l2.397-5.994A1.778 1.778 0 0 1 16 2.667h16c.727 0 1.38.442 1.65 1.117l2.398 5.994h10.174c.982 0 1.778.796 1.778 1.778v32c0 .981-.796 1.777-1.778 1.777H1.778A1.778 1.778 0 0 1 0 43.556v-32c0-.982.796-1.778 1.778-1.778h10.174zM24 38c6.075 0 11-4.925 11-11s-4.925-11-11-11-11 4.925-11 11 4.925 11 11 11z"></path>
			        </svg>
			    </button>
			    <input type="file" id="file1" name="file1" onchange="changeValue(this, 'preview-images1')" style="display: none;" accept="image/*" />
			</div>			
			<!-- 두번째 이미지 업로드 컨테이너 -->
			<div class="upload-container">
			    <div class="preview-images" id="preview-images2"></div>
			    <button type="button" class="btn-upload" onclick="document.getElementById('file2').click()">
			        <svg class="icon" width="48" height="48" viewBox="0 0 48 48" fill="currentColor" preserveAspectRatio="xMidYMid meet">
			            <path d="M11.952 9.778l2.397-5.994A1.778 1.778 0 0 1 16 2.667h16c.727 0 1.38.442 1.65 1.117l2.398 5.994h10.174c.982 0 1.778.796 1.778 1.778v32c0 .981-.796 1.777-1.778 1.777H1.778A1.778 1.778 0 0 1 0 43.556v-32c0-.982.796-1.778 1.778-1.778h10.174zM24 38c6.075 0 11-4.925 11-11s-4.925-11-11-11-11 4.925-11 11 4.925 11 11 11z"></path>
			        </svg>
			    </button>
			    <input type="file" id="file2" name="file2" onchange="changeValue(this, 'preview-images2')" style="display: none;" accept="image/*" />
			</div>
			<!-- 세번째 이미지 업로드 컨테이너 -->
			<div class="upload-container">
			    <div class="preview-images" id="preview-images3"></div>
			    <button type="button" class="btn-upload" onclick="document.getElementById('file3').click()">
			        <svg class="icon" width="48" height="48" viewBox="0 0 48 48" fill="currentColor" preserveAspectRatio="xMidYMid meet">
			            <path d="M11.952 9.778l2.397-5.994A1.778 1.778 0 0 1 16 2.667h16c.727 0 1.38.442 1.65 1.117l2.398 5.994h10.174c.982 0 1.778.796 1.778 1.778v32c0 .981-.796 1.777-1.778 1.777H1.778A1.778 1.778 0 0 1 0 43.556v-32c0-.982.796-1.778 1.778-1.778h10.174zM24 38c6.075 0 11-4.925 11-11s-4.925-11-11-11-11 4.925-11 11 4.925 11 11 11z"></path>
			        </svg>
			    </button>
			    <input type="file" id="file3" name="file3" onchange="changeValue(this, 'preview-images3')" style="display: none;" accept="image/*" />
			</div>
			<!-- 네번째 이미지 업로드 컨테이너 -->
			<div class="upload-container">
			    <div class="preview-images" id="preview-images4"></div>
			    <button type="button" class="btn-upload" onclick="document.getElementById('file4').click()">
			        <svg class="icon" width="48" height="48" viewBox="0 0 48 48" fill="currentColor" preserveAspectRatio="xMidYMid meet">
			            <path d="M11.952 9.778l2.397-5.994A1.778 1.778 0 0 1 16 2.667h16c.727 0 1.38.442 1.65 1.117l2.398 5.994h10.174c.982 0 1.778.796 1.778 1.778v32c0 .981-.796 1.777-1.778 1.777H1.778A1.778 1.778 0 0 1 0 43.556v-32c0-.982.796-1.778 1.778-1.778h10.174zM24 38c6.075 0 11-4.925 11-11s-4.925-11-11-11-11 4.925-11 11 4.925 11 11 11z"></path>
			        </svg>
			    </button>
			    <input type="file" id="file4" name="file4" onchange="changeValue(this, 'preview-images4')" style="display: none;" accept="image/*" />
			</div>
			<!-- 다섯번째 이미지 업로드 컨테이너 -->
			<div class="upload-container">
			    <div class="preview-images" id="preview-images5"></div>
			    <button type="button" class="btn-upload" onclick="document.getElementById('file5').click()">
			        <svg class="icon" width="48" height="48" viewBox="0 0 48 48" fill="currentColor" preserveAspectRatio="xMidYMid meet">
			            <path d="M11.952 9.778l2.397-5.994A1.778 1.778 0 0 1 16 2.667h16c.727 0 1.38.442 1.65 1.117l2.398 5.994h10.174c.982 0 1.778.796 1.778 1.778v32c0 .981-.796 1.777-1.778 1.777H1.778A1.778 1.778 0 0 1 0 43.556v-32c0-.982.796-1.778 1.778-1.778h10.174zM24 38c6.075 0 11-4.925 11-11s-4.925-11-11-11-11 4.925-11 11 4.925 11 11 11z"></path>
			        </svg>
			    </button>
			    <input type="file" id="file5" name="file5" onchange="changeValue(this, 'preview-images5')" style="display: none;" accept="image/*" />
			</div>
		</div>		
	    <br>
	    <br>
	    <div class="form-group">
		    <label for="title">위치</label>
		    <input type="text" class="form-control" id="location" name="location">
		    <button type="button" id="btnLocation"><i class="bi bi-crosshair"></i></button>
		</div>
	    <div class="form-group">
               <label for="price">가격</label>
               <input type="text" class="form-control" id="price" name="price" placeholder="가격을 입력하세요">
           </div>
           <div class="form-group">
               <label for="condition">상품상태</label>
               <select class="form-control" id="condition" name="condition">
                   <option>신상</option>
                   <option>중고</option>
               </select>
           </div>
           <div class="form-group">
               <label for="status">상태</label>
               <input class="form-control" id="status" name="status" value="판매중" readonly>
           </div>
           <div class="form-group">
				<!-- 카테고리 항목들을 라벨 옆으로 일렬로 정렬하는 부분 -->
			   	<div class="category-container">
			        <label class="category-label" for="category">카테고리</label>
			        <select class="form-control category-select" id="category1" name="category1" onchange="cate_2(this.value)">
			          <option value="">=대분류 선택=</option>
			          	<c:forEach var="c" items="${ca_1 }">
							<option value="${c.caId }">${c.caName }</option>
						</c:forEach>
			        </select>
			        <span id="category2_result" class="category-select">
			          	<select class="form-control" id="category2" name="category">
			            	<option value="">=중분류 선택=</option>
			          	</select>
			        </span>
			   	</div>
           </div>
           <div class="form-group">
               <label for="description">상품설명</label>
               <textarea class="form-control" id="description" name="description" rows="3" placeholder="상품 설명을 입력하세요"></textarea>
           </div>
           <button type="submit" class="btn btn-primary">작성완료</button>
    </form>
</div>
</body>
<footer>
    <%@ include file="/include/footer.jsp" %>
</footer>
</html>
