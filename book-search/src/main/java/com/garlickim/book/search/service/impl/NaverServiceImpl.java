package com.garlickim.book.search.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.garlickim.book.search.domain.vo.Book;
import com.garlickim.book.search.domain.vo.BookSearch;
import com.garlickim.book.search.domain.vo.Document;
import com.garlickim.book.search.domain.vo.naver.NaverDocument;
import com.garlickim.book.search.exception.BookSearchException;
import com.garlickim.book.search.service.ApiService;

@Service
public class NaverServiceImpl extends ApiService
{

    @Autowired
    ObjectMapper objectMapper;

    @Value("${naver.api.url}")
    String       apiUrl;

    @Override
    protected String getUrl()
    {
        return this.apiUrl;
    }





    @Override
    public String getQueryParameter(BookSearch bookSearch)
    {
        String queryParameter = "";

        switch ( bookSearch.getType() )
        {
            case "1":
                queryParameter = "d_titl=";
                break;
            case "2":
                queryParameter = "d_isbn=";
                break;
            case "3":
                queryParameter = "d_publ=";
                break;
            case "4":
                queryParameter = "d_auth=";
                break;
        }

        try
        {
            return queryParameter + URLEncoder.encode(bookSearch.getKeyword(), "UTF-8");
        }
        catch ( UnsupportedEncodingException e )
        {
            throw new BookSearchException();
        }
    }





    @Override
    public void setProperty(HttpURLConnection connection)
    {
        try
        {
            connection.setRequestProperty("X-Naver-Client-Id", "2GNd6CCIbCzgAJt7MGWF");
            connection.setRequestProperty("X-Naver-Client-Secret", "hOqmSBFivh");
            connection.setRequestMethod("GET");
        }
        catch ( ProtocolException e )
        {
            throw new BookSearchException("SET PROPERTY ERROR", e);
        }
    }





    @Override
    public Document convertBook(String str)
    {
        NaverDocument naverDocument = null;
        try
        {
            naverDocument = this.objectMapper.readValue(str, NaverDocument.class);
        }
        catch ( IOException e )
        {
            throw new BookSearchException("READVALUE ERROR", e);
        }

        List<Book> books = naverDocument.getItems()
                                        .stream()
                                        .map(naverBook -> Book.builder()
                                                              .title(naverBook.getTitle())
                                                              .description(naverBook.getDescription())
                                                              .isbn(naverBook.getIsbn())
                                                              .publishDate(naverBook.getPubdate())
                                                              .authors(naverBook.getAuthor()
                                                                                .split(""))
                                                              .publisher(naverBook.getPublisher())
                                                              .price(naverBook.getPrice())
                                                              .salesPrice(naverBook.getDiscount())
                                                              .build())
                                        .collect(Collectors.toList());

        Boolean isEnd = (naverDocument.getTotal() / 10 == naverDocument.getStart()) ? false : true;

        return Document.builder()
                       .books(books)
                       .isEnd(isEnd)
                       .totalCnt(naverDocument.getTotal())
                       .pageableCnt(naverDocument.getDisplay())
                       .build();
    }
}
