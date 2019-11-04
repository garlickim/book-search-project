var bookList = "<div>" +
    "<div id='bookList'></div>" +
    "<div id='pageList'></div>" +
    "</div>";

function showSearchResult(type, keyword, data, page) {
    $("#divContents").html(bookList);
    showBookList(keyword, data.books, page);
    showPageList(type, keyword, data.totalCnt, page);
}

var books = {};
var lastKeyword = '';

var booksTemplate = "<div class='book'>" +
    "<span>${title}</span>" +
    "<span>${authors}</span>" +
    "</div>";


function showBookList(keyword, bookList) {
    $("#bookList").html("");
    $.tmpl(booksTemplate, bookList).appendTo("#bookList");
}

var pageTemplate = "<span>" +
    "{{if pageNum == currentPage}}" +
    "<b>${pageText}</b>" +
    "{{else}}" +
    "<a onclick=\"searchBooks('${type}', '${keyword}', ${pageNum})\">${pageText}</a>" +
    "{{/if}}" +
    "</span>";

function showPageList(type, keyword, totalCount, currentPage) {
    var displayItemCount = 10;
    var displayPageCount = 10;
    var totalPage = Math.ceil(totalCount / displayItemCount);    // 총 페이지 수
    var pageGroup = Math.ceil(currentPage / displayPageCount);    // 페이지 그룹
    var endPage = Math.min(pageGroup * displayPageCount, totalPage);    // 화면에 보여질 마지막 페이지 번호
    var startPage = Math.max(endPage - (displayPageCount - 1), 1);    // 화면에 보여질 첫번째 페이지 번호
    var next = endPage + 1;
    var prev = startPage - 1;

    var pages = [];

    function addPage(pageNum, pageText) {
        pages.push({
            type: type,
            keyword: keyword,
            pageNum: pageNum,
            pageText: pageText,
            currentPage: currentPage
        });
    }

    if (prev > 0) {
        addPage(prev, '<<');
    }
    for (var i = startPage; i <= endPage; i++) {
        addPage(i, i);
    }
    if (next < totalPage) {
        console.log(next, totalPage);
        addPage(next, '>>');
    }

    $("#pageList").html("");
    $.tmpl(pageTemplate, pages).appendTo("#pageList");
}
