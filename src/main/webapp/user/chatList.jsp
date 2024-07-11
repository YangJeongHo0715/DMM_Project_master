<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>채팅리스트</title>
    <%@ include file="/include/header.jsp" %>
    <style>
        /* 푸터를 화면 하단에 고정 */
        footer {
            position: fixed;
            bottom: 0;
            width: 100%;
        }
        .chat-room {
            cursor: pointer;
        }
        .chat-room:hover {
            background-color: #f5f5f5;
        }
        .unread-count {
            display: inline-block;
            width: 20px;
            height: 20px;
            background-color: red;
            color: white;
            text-align: center;
            border-radius: 50%;
            font-size: 12px;
            line-height: 20px;
            position: relative;
            top: -5px;
            margin-left: 5px;
        }
        .justify-content-center{
        	margin-left: 45%;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>채팅방 리스트</h2>
	<ul class="list-group">
		<c:forEach var="chatRoom" items="${list}">
		    <li class="list-group-item chat-room" onclick="openChatPopup('${chatRoom.itemId}', '${chatRoom.roomId}', '${chatRoom.item.sellerId}', '${chatRoom.buyer}')">
		        <img src="/upload_item/${chatRoom.item.file1}" style="width:80px; height:80px;" alt=""/>
		        ${chatRoom.item.title}(${chatRoom.item.status})&nbsp;&nbsp;&nbsp;구매신청인 : ${chatRoom.buyer} &nbsp;&nbsp;&nbsp;판매자 : ${chatRoom.seller}
                <span class="unread-count">${chatRoom.readCount}</span> <!-- 읽지 않은 메시지 개수 표시 -->
		    </li>
    	        <input type="hidden" id="itemId" value="${chatRoom.itemId}"/>
		</c:forEach>
	</ul>
</div>
<!-- 페이징 부분 -->
<div class="justify-content-center">
    <ul class="pagination">
        <%-- 이전 페이지로 이동하는 링크 --%>
        <li class="page-item ${pageNum eq 1 ? 'disabled' : ''}">
            <a class="page-link" href="/user/chatList?pageNum=${pageNum - 1}" tabindex="-1" aria-disabled="true">이전</a>
        </li>
        <%-- 페이지 번호 표시 --%>
        <c:forEach var="i" begin="1" end="${(count + pageSize - 1) / pageSize}">
            <li class="page-item ${pageNum eq i ? 'active' : ''}">
                <a class="page-link" href="/user/chatList?pageNum=${i}">${i}</a>
            </li>
        </c:forEach>
        <%-- 다음 페이지로 이동하는 링크 --%>
        <li class="page-item ${pageNum * pageSize ge count ? 'disabled' : ''}">
            <a class="page-link" href="/user/chatList?pageNum=${pageNum + 1}">다음</a>
        </li>
    </ul>
</div>	
<footer>
    <%@ include file="/include/footer.jsp" %>
    <script>
	    function openChatPopup(itemId, roomId, sellerId, buyer) {
	        var popup = window.open("/chat/chat.jsp?item_id=" + itemId + "&room_id="+ roomId + "&seller_id="+ sellerId + "&buyer=" + buyer, "ChatPopup", "width=450,height=600,top=window.innerHeight-600,left=window.innerWidth-400");
	        if (!popup) {
	            alert("팝업 창이 차단되었습니다. 팝업 차단을 해제하고 다시 시도하세요.");
	        }
	    }
    </script>
</footer>
</body>
</html>
