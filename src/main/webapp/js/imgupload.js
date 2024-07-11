function changeValue(input, previewId) {
    var preview = input.previousElementSibling; // 업로드 버튼 이전의 요소를 미리보기 컨테이너로 사용합니다.

    // 이미지 추가
    var file = input.files[0];
    var reader = new FileReader();

    reader.onload = function (e) {
        var imgBox = document.createElement('div');
        imgBox.className = 'img_box';
        var img = document.createElement('img');
        img.src = e.target.result;
        img.className = 'preview-image';
        imgBox.appendChild(img);
        imgBox.onclick = function() {
            if (confirm("이미지를 삭제하시겠습니까?")) {
                imgBox.parentNode.removeChild(imgBox); // 이미지를 삭제함
                input.value = null; // 다시 파일 선택 가능하게 함
            }
        };

        // 아래 두 줄을 수정하여 미리보기 이미지를 업로드 버튼 바로 위에 생성하도록 함
        imgBox.style.position = 'absolute'; // 미리보기 이미지를 절대 위치로 설정
        imgBox.style.top = '0'; // 미리보기 이미지를 버튼의 상단에 배치
        imgBox.style.left = '0'; // 미리보기 이미지를 버튼의 왼쪽에 배치

        // 아래 두 줄을 추가하여 버튼 위에 이미지가 겹쳐지도록 함
        imgBox.style.zIndex = '2'; // 미리보기 이미지를 버튼 위에 표시
        input.style.zIndex = '1'; // 업로드 버튼을 미리보기 이미지 아래에 배치

        preview.appendChild(imgBox); // 미리보기 이미지를 업로드 버튼 바로 아래에 추가
    };

    if (file) { // 파일이 존재하는지 확인
        reader.readAsDataURL(file);
    }
}