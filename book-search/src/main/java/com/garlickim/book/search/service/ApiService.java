package com.garlickim.book.search.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

import com.garlickim.book.search.domain.BookSearch;
import com.garlickim.book.search.vo.Document;

public interface ApiService
{

    String getQueryParameter(BookSearch bookSearch) throws UnsupportedEncodingException;





    void setProperty(HttpURLConnection connection);





    Document searchBook(BookSearch bookSearch);





    Document convertBook(String str) throws IOException;
}
