    function likeItem(itemId) {
        $.ajax({
            url: "/sell/likeItem", //전송받을 페이지 경로
            type: "post", //데이터 전송 방식
            dataType: "text",
            data: "itemId=" + itemId,
            error: function () { //실패일 경우
                console.log("실패");
            },
            success: function (text) { //성공일 경우
                console.log("성공");
                // 좋아요 수를 업데이트하고 페이지를 새로고침
                window.location.reload();
            }
        });
    }