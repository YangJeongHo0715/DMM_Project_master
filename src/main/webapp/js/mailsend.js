    function sendEmail() {
        var userEmail = document.getElementById("user_email").value;
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/mail/mailSend", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.onreadystatechange = function() {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    var verificationCode = xhr.responseText; // 서버로부터 받은 인증번호
                    document.getElementById("check_number").value = verificationCode; // hidden 입력란에 인증번호 설정
                    alert("인증 이메일을 보냈습니다. 이메일을 확인하여 인증번호를 입력해주세요.");
                    document.getElementById("send_email_button").style.display = "none"; // 보내기 버튼 숨김
                    document.getElementById("verify_button").style.display = "inline-block"; // 확인 버튼 표시
                } else {
                    console.error("Request failed:", xhr.statusText);
                }
            }
        };
        var params = "user_email=" + encodeURIComponent(userEmail);
        xhr.send(params);
    }