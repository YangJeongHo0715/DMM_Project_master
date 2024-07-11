$(document).ready(function(){
    // 비밀번호 입력란 값이 변경될 때마다 실행
    $("#user_pass, #re_pass").keyup(function(event){    
        // 비밀번호 입력란과 비밀번호 확인 입력란의 값 가져오기
        var password = $("#user_pass").val();
        var confirmPassword = $("#re_pass").val();

        // 비밀번호와 비밀번호 확인이 일치하는지 확인
        if(password == confirmPassword) {
            // 일치할 경우 메시지를 파란색으로 표시
            $("#pass_check").html("<font color='blue'>비밀번호가 일치합니다.</font>");
        } else {
            // 불일치할 경우 메시지를 빨간색으로 표시
            $("#pass_check").html("<font color='red'>비밀번호가 일치하지 않습니다.</font>");           
        }
        
        if(password != confirmPassword) {
            // 폼 제출 방지
            event.preventDefault();
        }
    });
});

//8자리 이상, 대문자, 소문자, 숫자, 특수문자 모두 포함되어 있는 지 검사
var reg = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/;	       

function user_modify(){
	if(!user_name.value){
		alert("이름을 입력해주세요.");
		user_name.focus();
		return false;
	}
	if(!user_nick.value){
		alert("닉네임을 입력해주세요.");
		user_nick.focus();
		return false;
	}
	if(!user_age.value){
		alert("생년월일을 입력해주세요.");
		user_age.focus();
		return false;
	}
	if(!user_gender.value){
		alert("성별을 선택해주세요.");
		user_gender.focus();
		return false;
	}
	if(!(reg.test(user_pass.value))){
		alert("비밀번호는 8자리 이상이어야 하며, 대문자/소문자/숫자/특수문자를 모두 포함해야 합니다.");
		user_pass.focus();
		return false;
		
	}
	if(!user_pass.value){
		alert("비밀번호를 입력해주세요.");
		user_pass.focus();
		return false;
	}
	if(!user_phone.value){
		alert("전화번호를 입력해주세요.");
		user_phone.focus();
		return false;
	}
	if(!zip_code.value){
		alert("우편번호를 입력해주세요.");
		zip_code.focus();
		return false;
	}
	if(addr.value == ""){
		alert("주소를 입력해주세요.");
		addr.focus();
		return false;
	}
	if(user_pass.value != re_pass.value){ // 비밀번호와 비밀번호 확인이 같지 않다면
		re_pass.focus();
		return false;			
	}
	
	// 위의 모든 필드가 유효한 경우 실행
	return true;
}

// 인증번호 유효성 검사
function verifyNumber(){
    var enteredNumber = $("#check_email").val(); // 사용자가 입력한 인증번호
    var originalNumber = $("#check_number").val(); // hidden 필드에 저장된 원래 인증번호

    // 입력된 인증번호와 원래 인증번호가 일치하는지 확인
    if (enteredNumber !== originalNumber) {
        alert("올바른 인증번호를 입력하세요.");
        return false;
    }

    alert("인증이 정상적으로 완료되었습니다.");
    return true;
}