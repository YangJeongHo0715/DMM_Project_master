var socket = new WebSocket("ws://localhost:8080/chat/server");

window.onload = function() {
    // 웹소켓이 연결될 때 실행되는 함수
    socket.onopen = function(event) {
        console.log("WebSocket 연결이 열렸습니다.");
    };

    // 웹소켓으로부터 메시지를 받았을 때 실행되는 함수
    socket.onmessage = function(event) {
        var message = event.data;
        displayMessage(message);
    };

    // 웹소켓 에러 발생 시 실행되는 함수
    socket.onerror = function(event) {
        console.error("WebSocket 에러 발생:", event);
    };

    // 웹소켓 연결이 닫혔을 때 실행되는 함수
    socket.onclose = function(event) {
        console.log("WebSocket 연결이 닫혔습니다.");
    };
};
    
// 메시지를 서버로 보내는 함수
function sendMessage(itemId, sellerId, message) {
    var messageObject = {
        type: "start_chat",
        item_id: itemId,
        seller_id: sellerId,
        message: message
    };

    var messageJSON = JSON.stringify(messageObject);
    socket.send(messageJSON);
}
    
// 받은 메시지를 화면에 표시하는 함수
function displayMessage(message) {
    var messageDisplay = document.getElementById("messageDisplay");
    var messageElement = document.createElement("div");
    messageElement.classList.add("message");

    if (message.includes("Me: ")) {
        // 만약 메시지가 'Me: '로 시작한다면, 사용자의 메시지이므로 클래스를 'self'로 설정
        messageElement.classList.add("self");
        // 'Me: ' 부분은 제거하여 실제 메시지만을 표시
        message = message.replace("Me: ", "");
    } else {
        // 그렇지 않으면 상대방의 메시지이므로 클래스를 'sender'로 설정
        messageElement.classList.add("sender");
    }

    // 텍스트 내용으로 메시지 요소를 설정
    messageElement.textContent = message;
    
    // 메시지 요소를 메시지 표시 영역에 추가
    messageDisplay.appendChild(messageElement);

    // 메시지 표시 영역을 가장 아래로 스크롤하여 새로운 메시지가 보이도록 함
    messageDisplay.scrollTop = messageDisplay.scrollHeight;
}
