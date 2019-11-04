package com.garlickim.book.search.service.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.garlickim.book.search.domain.vo.Book;
import com.garlickim.book.search.domain.vo.BookSearch;
import com.garlickim.book.search.domain.vo.Document;
import com.garlickim.book.search.domain.vo.kakao.KakaoDocument;
import com.garlickim.book.search.exception.BookSearchException;
import com.garlickim.book.search.service.ApiService;

@Service
public class KakaoServiceImpl extends ApiService
{

    @Autowired
    ObjectMapper objectMapper;

    @Value("${kakao.api.url}")
    String       apiUrl;

    @Value("${kakao.api.key}")
    String       kakaoApiKey;

    @Override
    protected String getUrl()
    {
        return this.apiUrl;
    }





    // api 호출 쿼리 파라미터 생성
    @Override
    protected String getQueryParameter(BookSearch bookSearch)
    {
        String queryParameter = "&target=";

        switch ( bookSearch.getType() )
        {
            case "1":
                queryParameter += "title";
                break;
            case "2":
                queryParameter += "isbn";
                break;
            case "3":
                queryParameter += "publisher";
                break;
            case "4":
                queryParameter += "person";
                break;
            default:
                queryParameter = "";
        }

        queryParameter += Optional.ofNullable(bookSearch.getPage())
                                  .map(page -> "&page=" + page)
                                  .orElse("");

        return "query=" + URLEncoder.encode(bookSearch.getKeyword(), StandardCharsets.UTF_8) + queryParameter;
    }





    // header property 세팅
    @Override
    protected void setProperty(HttpURLConnection connection)
    {
        try
        {
            connection.setRequestProperty("Authorization", this.kakaoApiKey);
            connection.setRequestMethod("GET");
        }
        catch ( ProtocolException e )
        {
            throw new BookSearchException("SET PROPERTY ERROR", e);
        }
    }





    // 화면 VO 객체로 데이터 변환
    @Override
    public Document convertBook(String str)
    {
        KakaoDocument kakaoDocument = null;
        try
        {
            kakaoDocument = this.objectMapper.readValue(str, KakaoDocument.class);
        }
        catch ( IOException e )
        {
            throw new BookSearchException("READVALUE ERROR", e);
        }

        List<Book> books = kakaoDocument.getDocuments()
                                        .stream()
                                        .map(kakaoBook -> Book.builder()
                                                              .title(kakaoBook.getTitle())
                                                              .description(kakaoBook.getContents())
                                                              .isbn(kakaoBook.getIsbn())
                                                              .publishDate(kakaoBook.getDatetime())
                                                              .authors(kakaoBook.getAuthors())
                                                              .publisher(kakaoBook.getPublisher())
                                                              .price(kakaoBook.getPrice())
                                                              .salesPrice(kakaoBook.getSale_price())
                                                              .build())
                                        .collect(Collectors.toList());

        return Document.builder()
                       .books(books)
                       .totalCnt(kakaoDocument.getMeta()
                                              .getTotal_count())
                       .pageableCnt(kakaoDocument.getMeta()
                                                 .getPageable_count())
                       .build();
    }
}
