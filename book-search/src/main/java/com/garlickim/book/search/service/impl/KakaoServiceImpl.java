package com.garlickim.book.search.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.garlickim.book.search.domain.BookSearch;
import com.garlickim.book.search.domain.KakaoDocument;
import com.garlickim.book.search.exception.BookSearchException;
import com.garlickim.book.search.service.ApiService;
import com.garlickim.book.search.vo.Book;
import com.garlickim.book.search.vo.Document;

@Service
public class KakaoServiceImpl implements ApiService
{

    @Autowired
    ObjectMapper objectMapper;

    @Value("${kakao.api.url}")
    String       apiUrl;

    @Value("${kakao.api.key}")
    String       kakaoApiKey;

    @Override
    public String getQueryParameter(BookSearch bookSearch)
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

        try
        {
            return "query=" + URLEncoder.encode(bookSearch.getKeyword(), "UTF-8") + queryParameter;
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
            connection.setRequestProperty("Authorization", this.kakaoApiKey);
            connection.setRequestMethod("GET");
        }
        catch ( ProtocolException e )
        {
            throw new BookSearchException("SET PROPERTY ERROR");
        }
    }





    @Override
    public Document searchBook(BookSearch bookSearch)
    {
        try
        {
            URL url = new URL(this.apiUrl + getQueryParameter(bookSearch));
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            try ( AutoCloseable a = () -> connection.disconnect() )
            {

                setProperty(connection);

                if ( connection.getResponseCode() != 200 )
                {
                    throw new BookSearchException();
                }

                String inputLine = null;
                StringBuffer sb = new StringBuffer();

                try ( final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8")) )
                {
                    while ( (inputLine = in.readLine()) != null )
                    {
                        sb.append(inputLine);
                    }
                }

                return convertBook(sb.toString());
            }

        }
        catch ( MalformedURLException e )
        {
            throw new BookSearchException("URL CREATE ERROR");
        }
        catch ( IOException e )
        {
            throw new BookSearchException("URL CONNECTION OPEN ERROR");
        }
        catch ( Exception e )
        {
            throw new BookSearchException("URL CONNECTION OPEN ERROR");
        }
    }





    @Override
    public Document convertBook(String str) throws IOException
    {
        KakaoDocument kakaoDocument = this.objectMapper.readValue(str, KakaoDocument.class);

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
                       .isEnd(kakaoDocument.getMeta()
                                           .getIs_end())
                       .totalCnt(kakaoDocument.getMeta()
                                              .getTotal_count())
                       .pageableCnt(kakaoDocument.getMeta()
                                                 .getPageable_count())
                       .build();
    }
}
