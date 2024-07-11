// Kakao Maps Geocoding API를 이용하여 위도와 경도를 주소로 변환하는 함수
function getLocationFromLatLng(latitude, longitude) {
    // Kakao 지도 API를 이용하여 위도와 경도를 주소로 변환
    var geocoder = new kakao.maps.services.Geocoder();
    var latlng = new kakao.maps.LatLng(latitude, longitude);
    
    // 변환된 주소 정보를 받아옴
    geocoder.coord2Address(longitude, latitude, function(result, status) {
        if (status === kakao.maps.services.Status.OK) {
            var fullAddress = result[0].address.address_name; // 전체 주소
            // 결과를 입력란에 표시
            document.getElementById("location").value = fullAddress;
        } else {
            console.error("Failed to convert coordinates to address.");
        }
    });
}

// 버튼 클릭 이벤트 핸들러
document.addEventListener("DOMContentLoaded", function() {
    // 현재 위치 가져오기
    if (navigator.geolocation) {
        // 버튼 클릭 이벤트 리스너 등록
        document.getElementById("btnLocation").addEventListener("click", function() {
            navigator.geolocation.getCurrentPosition(function(position) {
                // 현재 위치의 위도와 경도 가져오기
                var latitude = position.coords.latitude;
                var longitude = position.coords.longitude;

                // Kakao Maps Geocoding API를 이용하여 위도와 경도를 주소로 변환
                getLocationFromLatLng(latitude, longitude);
            }, function(error) {
                console.error("Failed to get current location:", error);
            });
        });
    } else {
        console.error("Geolocation is not supported by this browser.");
    }
});