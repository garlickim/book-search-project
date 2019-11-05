function validateSignup() {
    if (!$.trim($("#username").val())) {
        alert("아이디를 입력하세요");
        $("#username").val("").focus();
        return false;
    }
    if (!$.trim($("#password").val())) {
        alert("비밀번호를 입력하세요");
        $("#password").val("").focus();
        return false;
    }

    var isValid = false;

    $.ajax({
        method: "get",
        url: "/users/duplicate/" + $("#username").val(),
        contentType: "application/json;charset=UTF-8",
        async: false,
        success: function (isExistUsername) {   // success callback function
            if (isExistUsername) {
                alert("동일한 ID가 존재합니다");
                isValid = true;
            }
        },
        error: function (jqXhr, textStatus, errorMessage) { // error callback
            alert(errorMessage);
        }
    });

    return !isValid;

}