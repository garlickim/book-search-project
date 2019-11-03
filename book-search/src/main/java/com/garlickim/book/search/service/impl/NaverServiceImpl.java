package com.garlickim.book.search.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.garlickim.book.search.domain.BookSearch;
import com.garlickim.book.search.domain.KakaoDocument;
import com.garlickim.book.search.exception.BookSearchException;
import com.garlickim.book.search.service.ApiService;
import com.garlickim.book.search.vo.Document;

@Service
public class NaverServiceImpl implements ApiService
{

    @Autowired
    ObjectMapper objectMapper;

    @Value("${naver.api.url}")
    String       apiUrl;

    @Override
    public String getQueryParameter(BookSearch bookSearch)
    {
        String queryParameter = "&target=";

        switch ( bookSearch.getType() )
        {
            case "1":
                queryParameter = "d_titl";
                break;
            case "2":
                queryParameter = "d_isbn";
                break;
            case "3":
                queryParameter = "d_publ";
                break;
            case "4":
                queryParameter = "d_auth";
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

                JAXBContext context = JAXBContext.newInstance(KakaoDocument.class);

                Unmarshaller unmarshaller = context.createUnmarshaller();
                // return (KakaoDocument) unmarshaller.unmarshal(new StringReader(sb.toString()));

                return null;

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
    public Document convertBook(String str)
    {
        return null;
    }
}
