// 책 검색
function searchBooks(type, keyword, page, complete) {
    if (keyword != lastKeyword) {
        books = {};
        lastKeyword = keyword;
    }

    if (books[page]) {
        showSearchResult({books: books[page], totalCnt: books['totalCnt']}, type, keyword, page);
    } else {
        $.ajax({
            method: "post",
            url: "/book/search",
            data: JSON.stringify({
                type: type,
                keyword: keyword,
                page: page
            }),
            contentType: "application/json;charset=UTF-8",
            success: function (data) {   // success callback function
                books[page] = data.books;
                books['totalCnt'] = data.totalCnt;
                showSearchResult(data, type, keyword, page);
            },
            error: function (jqXhr, textStatus, errorMessage) { // error callback
                alert(errorMessage);
            },
            complete: complete
        })
    }
}

function showSearchResult(data, type, keyword, page) {
    $("#divContents").html("<div>" +
        "   <div id='bookList'></div>" +
        "   <div id='pageList'></div>" +
        "</div>");
    showBookList(data.books, type, keyword, page);
    showPageList(type, keyword, data.totalCnt, page);
}

var books = {};
var lastKeyword = '';


function showBookList(bookList, type, keyword, page) {
    $("#bookList").html("");
    $.tmpl(booksTemplate, {list: bookList, type: type, keyword: keyword, page: page}).appendTo("#bookList");
}

function showBookDetail(index, type, keyword, page) {
    $('#divContents').html('');
    $.tmpl(bookDetailTemplate, {
        book: books[page][index],
        type: type,
        keyword: keyword,
        page: page
    }).appendTo('#divContents');
}

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
        addPage(next, '>>');
    }

    $("#pageList").html("");
    $.tmpl(pageTemplate, pages).appendTo("#pageList");
}

var booksTemplate = "{{each list}}" +
    "<div><a class='book' onclick=\"showBookDetail(${$index}, '${type}', '${keyword}', ${page})\">" +
    "   <span>${(page - 1) * 10 + $index + 1}</span> /" +
    "   <span>${title}</span> /" +
    "   <span>${authors}</span>" +
    "</a></div>" +
    "{{/each}}";

var bookDetailTemplate = "<div>" +
    "<table>" +
    "   <tr>" +
    "       <td>제목</td>" +
    "       <td>${book.title}</td>" +
    "   </tr>" +
    "   {{if book.thumbUrl != null}}" +
    "   <tr>" +
    "       <td></td>" +
    "       <td><img src='${book.thumbUrl}'/></td>" +
    "   </tr>" +
    "   {{/if}}" +
    "   <tr>" +
    "       <td>소개</td>" +
    "       <td>${book.description}</td>" +
    "   </tr>" +
    "   <tr>" +
    "       <td>ISBN</td>" +
    "       <td>${book.isbn}</td>" +
    "   </tr>" +
    "   <tr>" +
    "       <td>저자</td>" +
    "       <td>${book.authors}</td>" +
    "   </tr>" +
    "   <tr>" +
    "       <td>출판사</td>" +
    "       <td>${book.publisher}</td>" +
    "   </tr>" +
    "   <tr>" +
    "       <td>출판일</td>" +
    "       <td>${book.publishDate}</td>" +
    "   </tr>" +
    "   <tr>" +
    "       <td>정가</td>" +
    "       <td>${book.price}</td>" +
    "   </tr>" +
    "   <tr>" +
    "       <td>판매가</td>" +
    "       <td>${book.salesPrice}</td>" +
    "   </tr>" +
    "</table>" +
    "</div>" +
    "<div>" +
    "   <br/>" +
    "   <a onclick=\"searchBooks('${type}', '${keyword}', ${page})\">목록으로</a>" +
    "</div>";

var pageTemplate = "<span>" +
    "{{if pageNum == currentPage}}" +
    "<b>${pageText}</b>" +
    "{{else}}" +
    "<a onclick=\"searchBooks('${type}', '${keyword}', ${pageNum})\">${pageText}</a>" +
    "{{/if}}" +
    "</span>";
