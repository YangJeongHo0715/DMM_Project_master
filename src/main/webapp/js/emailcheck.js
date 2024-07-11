$(document).ready(function(){
	$("#user_email").keyup(function(event){		
		if (!(event.keyCode >=37 && event.keyCode<=40)) {
			$(this).val($(this).val().replace(/[^_a-z0-9@.]/gi,"")); //@와 .(점)을 포함하여 영어, 숫자, _만 가능
		}
		// AJAX를 사용하여 서버로 이메일 전송
		$.ajax({
			url: "/user/email_ok", // 서버로 전송할 경로
			type: "post", // 데이터 전송 방식
			dataType: "text",
			data: "email="+$("#user_email").val(), // 전송할 데이터: 입력한 이메일
			error:function(){ // 요청이 실패한 경우
				alert("요청 실패");
			},
			success:function(num){ // 요청이 성공한 경우
				var msg = ""; // 메시지 초기화
				if(num == "1") {
					msg = "<font color=red>이미 등록된 이메일입니다.</font>";
				}else if(num == "0") {
					msg = "<font color=blue>사용 가능한 이메일입니다.</font>";
				}
				$("#email_result").html(msg); // 메시지를 출력할 요소에 메시지 삽입
			}
		});	
	});
});
