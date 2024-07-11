function openZipSearch() {
    new daum.Postcode({
        oncomplete: function(data) {     
            var addr = ''; 
            if (data.userSelectedType === 'R') { 
                addr = data.roadAddress;
            } else {
                addr = data.jibunAddress;
            }

            $("#zip_code").val(data.zonecode);
            $("#addr").val(addr);
            $("#addr_dtl").val("");
            $("#addr_dtl").focus();
        }
    }).open();
}
