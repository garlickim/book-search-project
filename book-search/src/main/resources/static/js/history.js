// 히스토리 조회
function keywordHistory() {
    $.ajax({
        method: "get",
        url: "/users/" + $("#username").val() + "/keywords",
        contentType: "application/json;charset=UTF-8",
        success: function (data) {   // success callback function
            $("#divContents").html("");
            $.tmpl(historyListTemplate, data).appendTo("#divContents");
        },
        error: function (jqXhr, textStatus, errorMessage) { // error callback
            alert(errorMessage);
        }
    });
}

var historyListTemplate = "<div>" +
    "   <span>${keyword}</span>" +
    "   <span>(${searchTime})</span>" +
    "</div>";
