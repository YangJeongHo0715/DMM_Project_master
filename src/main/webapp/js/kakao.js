Kakao.init('7efcfd2a287e0858fbb479563959d613');

function kakaoLogin() {
    Kakao.Auth.login({
        success: function (response) {
            Kakao.API.request({
                url: '/v2/user/me',
                success: function (response) {
                    var email = response.kakao_account.email;
                    $.ajax({
                        url: "/user/kakao",
                        type: "get",
                        dataType: "text",
                        data: "useremail=" + email,
                        success: function (result) {
                            if (result === '1') {
                                location.href = "/";
                            } else {
                                location.href = "/social/modify";
                            }
                        }
                    });
                },
                fail: function (error) {
                    console.log(error);
                }
            });
        },
        fail: function (error) {
            console.log(error);
        }
    });
}
