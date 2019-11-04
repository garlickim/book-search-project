function showKeywordsRank() {
    $.ajax({
        method: "get",
        url: "/keywords",
        contentType: "application/json;charset=UTF-8",
        success: function (data) {   // success callback function
            $("#divKeywords>div").html("");
            $.tmpl(keywordsRankTemplate, data).appendTo("#divKeywords>div");
        },
        error: function (jqXhr, textStatus, errorMessage) { // error callback
            alert(errorMessage);
        }
    });
}

var keywordsRankTemplate = "<div>" +
    "   <span>${keyword}</span>" +
    "   <span>(${cnt})</span>" +
    "</div>";

