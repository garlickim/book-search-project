package com.garlickim.book.search.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.garlickim.book.search.domain.vo.BookSearch;
import com.garlickim.book.search.domain.vo.Document;
import com.garlickim.book.search.exception.BookSearchException;

public abstract class ApiService
{
    protected abstract String getUrl();





    protected abstract String getQueryParameter(BookSearch bookSearch);





    protected abstract void setProperty(HttpURLConnection connection);





    public Document searchBook(BookSearch bookSearch)
    {
        try
        {
            URL url = new URL(getUrl() + getQueryParameter(bookSearch));
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            try ( AutoCloseable a = () -> connection.disconnect() )
            {

                setProperty(connection);

                if ( connection.getResponseCode() != 200 )
                {
                    throw new BookSearchException("CONNECTION RESPONSE ERROR(NOT 200)");
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
            throw new BookSearchException("URL CREATE ERROR", e);
        }
        catch ( IOException e )
        {
            throw new BookSearchException("URL CONNECTION OPEN ERROR", e);
        }
        catch ( Exception e )
        {
            throw new BookSearchException("KAKAO SEARCH BOOK API ERROR", e);
        }
    };





    public abstract Document convertBook(String str);
}
