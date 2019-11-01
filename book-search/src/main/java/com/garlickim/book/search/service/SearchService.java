package com.garlickim.book.search.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.garlickim.book.search.domain.Book;

@Service
public class SearchService
{

    public Book searchBook(String queryParam) throws Exception
    {
        String header = "KakaoAK 54a6c384ec25e398d6b7e5fba3fad494";
        String apiURL = "https://dapi.kakao.com/v3/search/book?" + queryParam;
        URL url = new URL(apiURL);

        final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        try ( AutoCloseable a = () -> conn.disconnect() )
        {
            conn.setUseCaches(false);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", header);

            if ( conn.getResponseCode() != 200 )
            {
                return new Book();
            }

            String inputLine = null;
            StringBuffer sb = new StringBuffer();

            try ( final BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8")) )
            {
                while ( (inputLine = in.readLine()) != null )
                {
                    sb.append(inputLine);
                }
            }

            ObjectMapper objectMapper = new ObjectMapper();
            Book book = objectMapper.readValue(sb.toString(), Book.class);

            return book;
        }
    }

}
