    // 사용자의 현재 위치 가져오기
    navigator.geolocation.getCurrentPosition(function(position) {
        // 현재 위치의 위도와 경도 가져오기
        var latitude = position.coords.latitude;
        var longitude = position.coords.longitude;
        
        // 위도와 경도를 input 태그에 업데이트
        document.getElementById("latitude").value = latitude;
        document.getElementById("longitude").value = longitude;
        
        // 위도와 경도를 이용하여 주소 가져오기
        getAddress(latitude, longitude);
    });

    // 위도와 경도를 이용하여 주소 가져오기
    function getAddress(latitude, longitude) {
        var geocoder = new kakao.maps.services.Geocoder();

        // 좌표를 주소로 변환
        geocoder.coord2Address(longitude, latitude, function (result, status) {
            if (status === kakao.maps.services.Status.OK) {
                var address = result[0].address.address_name;

                // 주소를 HTML 요소에 표시
                document.getElementById("location").innerHTML = "현재 위치: " + address;
            } else {
                // 에러 처리
                document.getElementById("location").innerHTML = "주소를 가져오지 못했습니다.";
            }
        });
    }