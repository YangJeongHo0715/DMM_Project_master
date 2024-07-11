// Geolocation API를 사용하여 사용자의 현재 위치를 가져오는 함수
function getLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(showPosition);
    } else {
        alert("Geolocation is not supported by this browser.");
    }
}

// 사용자의 위치를 가져와서 hidden input에 할당하는 함수
function showPosition(position) {
    var latitude = position.coords.latitude; // 위도
    var longitude = position.coords.longitude; // 경도

    // 위도와 경도를 hidden input에 할당
    document.getElementById("latitude").value = latitude;
    document.getElementById("longitude").value = longitude;
}

// 페이지 로드 시 사용자의 위치를 가져오는 함수 호출
window.onload = function() {
    getLocation();
};