<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dto.Item" %>
<%@ page import="dao.ItemDAO" %>
<%
    String itemId = request.getParameter("item_id");
	String roomId = request.getParameter("room_id");
	String sellerId = request.getParameter("seller_id");
	String buyer = request.getParameter("buyer");
	String user = (String)session.getAttribute("email");
	
	ItemDAO dao = new ItemDAO();
	Item item = dao.oneItem(Integer.parseInt(itemId));
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chatting</title>
    <!-- 위치 가져오기  -->
    <script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=7efcfd2a287e0858fbb479563959d613&libraries=services"></script>
  	<script src="/js/geolocation.js"></script>
  	<script src="/js/kakaogeolocation.js"></script>
  	
    <!-- Bootstrap JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
   
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
	<script src="http://code.jquery.com/jquery-migrate-1.4.1.min.js"></script>
    <style>
        /* Chat box */
        .chat-box {
            width: 300px;
            height: 400px;
            border: 1px solid #ccc;
            border-radius: 5px;
            overflow-y: scroll;
            padding: 10px;
            margin: 20px auto;
        }
        /* Chat messages */
        .chat-message {
            margin-bottom: 10px;
            display: flex;
            align-items: flex-start;
        }
        .chat-message p {
            background-color: #f1f0f0;
            border-radius: 5px;
            padding: 5px 10px;
            word-wrap: break-word;
            max-width: 70%;
            margin: 5px;
        }
        .chat-message.current-user p {
            background-color: #f1f0f0;
            float: right;
        }
        .chat-message.other-user p {
            background-color: #cae5e5;
            float: left;
        }
        /* Message input */
        .message-input {
            width: calc(100% - 20px);
            margin: 0 auto;
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px;
            border-top: 1px solid #ccc;
        }
        .message-input input {
            flex: 1;
            border: none;
            padding: 8px;
            border-radius: 20px;
            outline: none;
        }
        .message-input button {
            background-color: #40BEA7;
            border: none;
            color: white;
            padding: 8px 15px;
            border-radius: 20px;
            cursor: pointer;
        }
        /* Popup */
        .popup {
            position: fixed;
            left: 50%;
            top: 50%;
            transform: translate(-50%, -50%);
            background-color: #fff;
            border: 1px solid #ccc;
            border-radius: 5px;
            padding: 20px;
            z-index: 1000;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .popup-overlay {
            position: fixed;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
            top: 0;
            left: 0;
            z-index: 999;
        }
        #imagePreview {
		    max-width: 100px; /* 원하는 최대 너비 설정 */
		    max-height: 100px; /* 원하는 최대 높이 설정 */
		}
		#btnLocation {
			position: relative;
		    border: none; /* 테두리 없애기 */
		    background-color: transparent; /* 배경색 투명하게 설정 */
		    bottom: 27px;
		    left: 155px;
		}
    </style>
</head>
<body>
    <div class="chat-box" id="chatBox">
        <!-- Chat messages will be displayed here -->
    </div>
    <!-- 메세지 입력 -->
    <div class="message-input">
        <input type="hidden" id="itemId" value="<%=itemId%>">
        <input type="hidden" id="roomId" value="<%=roomId%>">
        <input type="hidden" id="userNameInput" value="<%=user%>">
        <input type="hidden" id="sellerId" value="<%=sellerId%>">
        <input type="hidden" id="buyer" value="<%=buyer%>"> 
        <input type="text" id="messageInput" placeholder="메시지를 입력하세요...">
        <%if(item.getStatus().equals("판매중")){ %>
        <!-- 거래 예약 버튼 -->
        <button id="chatButton" onclick="openPopup()"><i class="fas fa-calendar-plus"></i></button>
        <%}else if(item.getStatus().equals("예약중")){ %>
        <!-- 예약 취소 버튼 -->
        <button id="chatButton" onclick="formCancel('<%=itemId%>', '<%=roomId%>')"><i class="bi bi-x-circle-fill"></i></button>
        <%} %>
		<!-- 전송 버튼 -->
		<button id="chatButton" onclick="sendMessage()"><i class="fas fa-paper-plane"></i></button>
    </div>
    <!-- 메세지 입력 끝 -->
    <!-- 팝업 -->
    <div class="popup-overlay" id="popupOverlay" style="display: none;">
	    <div class="popup" id="popup" style="display: none;">
		    <span style="position:relative; margin-left: 200px;"><i class="bi bi-x" onclick="closePopup()"></i></span>
		    <div class="panel panel-default">
		        <div class="panel-heading">
		            <h3 class="panel-title">예약하기</h3>
		        </div>
		        <div class="panel-body">
		            <form id="reservationForm" method="POST" action="/chat/reserve">
		                <div class="form-group">
		                    <label for="reservationDate">거래 일자</label>
		                    <input type="date" class="form-control" id="date" name="date">
		                </div>
		                <div class="form-group">
		                    <label for="reservationLocation">거래 장소</label>
		                    <!-- 위도(hidden) 입력 폼 -->
					        <input type="hidden" id="latitude" name="latitude">
					        <!-- 경도(hidden) 입력 폼 -->
					        <input type="hidden" id="longitude" name="longitude">
		                    <input type="text" class="form-control" id="location" name="location">
		                    <button type="button" id="btnLocation"><i class="bi bi-crosshair"></i></button>
		                </div>
        				<input type="hidden" name="room_id" value="<%=roomId%>">
		                <input type="hidden" name="item_id" value="<%=itemId%>">
				        <input type="hidden" name="seller_id" value="<%=sellerId%>">
				        <input type="hidden" name="buyer_id" value="<%=buyer%>"> 
		                <button type="submit" onclick="closePopup()" class="btn btn-primary">예약</button>
		            </form>
	        	</div>
	    	</div>
		</div>
    </div>
     <!-- 팝업 끝 -->
    <div class="popup" id="popup" style="display: none;">
        <div class="chat-box" id="chatHistory">
            <!-- 채팅 내역 출력장소 -->
        </div>
        <button onclick="closePopup()">Close</button>
    </div>
	<script>
		var sellerId = document.getElementById("sellerId").value;
		var userName = document.getElementById("userNameInput").value;
		
	    function openPopup() {
	    	if(sellerId != userName){ //자기자신은 팝업 못열게
		        document.getElementById("popupOverlay").style.display = "block";
		        document.getElementById("popup").style.display = "block";
	    	}
	    }
	
	    function closePopup() {
	        document.getElementById("popupOverlay").style.display = "none";
	        document.getElementById("popup").style.display = "none";
	    }
	    
	</script>
    <script>
        var userName = document.getElementById("userNameInput").value;
        var itemId = document.getElementById("itemId").value;
        var roomId = document.getElementById("roomId").value;
        var sellerId = document.getElementById("sellerId").value;
        var buyer = document.getElementById("buyer").value;

        // WebSocket 연결 및 메시지 전송 함수
        var webSocket = new WebSocket("ws://localhost:8080/chat/server");

        webSocket.onmessage = function(event) {
            var chatBox = document.getElementById("chatBox");
            var message = event.data;
            var messageClass = "chat-message";
            
            
            if (message.includes("예약이 완료되었습니다.")) { // 서버로부터 받은 메시지가 예약 정보인 경우에만 출력
                var messageElement = document.createElement("div");
                var item_id = document.getElementById("itemId").value;
                var room_id = document.getElementById("roomId").value;
                messageElement.className = messageClass;
                messageElement.innerHTML = "<p>" + message + "</p>";
                messageElement.innerHTML = "<button onclick='formResult(\"" + item_id + "\", \"" + room_id + "\")'>거래완료</button>";
                chatBox.appendChild(messageElement);
            } else if(message.includes("예약이 취소되었습니다.")){ // 서버로부터 받은 메시지가 취소 정보인 경우에만 출력
            	var messageElement = document.createElement("div");
                messageElement.className = messageClass;
                messageElement.innerHTML = "<p>" + message + "</p>";
                chatBox.appendChild(messageElement);
            } else if(message.includes("거래가 완료되었습니다.")){ // 서버로부터 받은 메시지가 거래완료 정보인 경우에만 출력
            	var messageElement = document.createElement("div");
                messageElement.className = messageClass;
                messageElement.innerHTML = "<p>" + message + "</p>";
                chatBox.appendChild(messageElement);
            } else {
                // 일반적인 메시지 출력
                if (message.startsWith(userName)) {
                    messageClass += " current-user";
                } else {
                    messageClass += " other-user";
                }

                var messageElement = document.createElement("div");
                messageElement.className = messageClass;
                messageElement.innerHTML = "<p>" + message + "</p>";
                chatBox.appendChild(messageElement);
            }
            
            chatBox.scrollTop = chatBox.scrollHeight;
        };
    
        // 메시지 전송
        function sendMessage() {
            var messageInput = document.getElementById("messageInput");
            var message = messageInput.value;
            var fullMessage = "itemId=" + itemId + "&userName=" + userName + "&sellerId=" + sellerId + "&buyer=" + buyer + "&message=" + message;
            webSocket.send(fullMessage);
            console.log(fullMessage);
            messageInput.value = "";
        }
        </script>
        <script>
	     	// 페이지가 로드되면 실행될 함수
	        window.onload = function() {
	        	// 새로고침(1번만 실행)
	     		if (self.name != 'reload') {
	                 self.name = 'reload';
	                 self.location.reload(true);
	             }
	             else self.name = ''; 
	            // loadChatHistory 함수 호출
	            loadChatHistory(itemId, roomId); // itemId와 roomId는 어디서 가져오는지에 따라서 값을 지정.
	        };
	        function loadChatHistory(itemId, roomId) {
	            console.log("페이지 로드");
	            // AJAX를 사용하여 서버에 요청.
	            $.ajax({
	                type: "GET",
	                url: "/chat/history", // 과거 채팅 내역을 가져올 서버의 URL
	                data: { itemId: itemId, roomId: roomId }, // 채팅방 정보를 서버에 전달.
	                success: function(response) {
	                    // 성공적으로 과거 채팅 내역을 받아왔을 때, 화면에 표시.
	                    $("#chatBox").html(response); // 채팅 내역을 표시할 곳의 ID를 사용.
	                },
	                error: function(xhr, status, error) {
	                    // 에러가 발생했을 때의 처리
	                    console.error("Error loading chat history:", error);
	                }
	            });
	        }
    </script>
    <script>
	    function formResult(item_id, room_id) {
	        console.log("페이지 로드");
	        // AJAX를 사용하여 서버에 요청.
	        $.ajax({
	            type: "POST",
	            url: "/chat/result", // 과거 채팅 내역을 가져올 서버의 URL
	            data: { item_id: item_id, room_id: room_id }, // 채팅방 정보를 서버에 전달.
	            success: function(response) {
	            	console.log("전송 완료");
	            },
	            error: function(xhr, status, error) {
	                // 에러가 발생했을 때의 처리
	                console.error("Error loading chat history:", error);
	            }
	        });
	    }
    </script>
    <script>
	    function formCancel(item_id, room_id) {
	        console.log("페이지 로드");
	        // AJAX를 사용하여 서버에 요청.
	        $.ajax({
	            type: "POST",
	            url: "/chat/cancel", // 과거 채팅 내역을 가져올 서버의 URL
	            data: { item_id: item_id, room_id: room_id }, // 채팅방 정보를 서버에 전달.
	            success: function(response) {
	            	console.log("전송 완료");
	            },
	            error: function(xhr, status, error) {
	                // 에러가 발생했을 때의 처리
	                console.error("Error loading chat history:", error);
	            }
	        });
		}
    </script>
</body>
</html>
