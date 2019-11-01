package com.garlickim.book.search.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.garlickim.book.search.domain.BookSearch;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.garlickim.book.search.domain.Book;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

@Service
public class SearchService {
    @Value("${kakao.api.url}")
    String kakaoApiUrl;

    @Value("${kakao.api.key}")
    String kakaoApiKey;

    @Value("${naver.api.url}")
    String naverApiUrl;

    enum ApiType {
        kakao, naver;
    }

    public Book searchBook(BookSearch bookSearch) throws Exception {
        try {
            return callApi(ApiType.kakao, kakaoApiUrl, makeQueryParam(ApiType.kakao, bookSearch));
        } catch (Exception e) {
            return callApi(ApiType.naver, naverApiUrl, makeQueryParam(ApiType.naver, bookSearch));
        }
    }

    public String makeQueryParam(ApiType type, BookSearch bookSearch) throws UnsupportedEncodingException{

        String targetKakao = "&target=";
        String queryNaver = "";

        switch (bookSearch.getType()) {
            case "1":
                targetKakao += "title";
                queryNaver = "d_titl";
                break;
            case "2":
                targetKakao += "isbn";
                queryNaver = "d_isbn";
                break;
            case "3":
                targetKakao += "publisher";
                queryNaver = "d_publ";
                break;
            case "4":
                targetKakao += "person";
                queryNaver = "d_auth";
                break;
            default:
                targetKakao = "";
        }

        if (ApiType.kakao.equals(type)) {
            return "query=" + URLEncoder.encode(bookSearch.getKeyword(), "UTF-8") + targetKakao;
        } else {
            return queryNaver + URLEncoder.encode(bookSearch.getKeyword(), "UTF-8");
        }

    }

    // 네이버 연동 부분은 검토 필요
    public Book callApi(ApiType type, String apiUrl, String queryParam) throws Exception {
        URL url = new URL(apiUrl + queryParam);

        final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        try (AutoCloseable a = () -> conn.disconnect()) {
            conn.setRequestMethod("GET");

            if (ApiType.kakao.equals(type)) {
                conn.setRequestProperty("Authorization", kakaoApiKey);
            } else {
                conn.setRequestProperty("X-Naver-Client-Id", "2GNd6CCIbCzgAJt7MGWF");
                conn.setRequestProperty("X-Naver-Client-Secret", "hOqmSBFivh");
            }

            if (conn.getResponseCode() != 200) {
                throw new NullPointerException();
            }

            String inputLine = null;
            StringBuffer sb = new StringBuffer();

            try (final BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
                while ((inputLine = in.readLine()) != null) {
                    sb.append(inputLine);
                }
            }

            if (ApiType.kakao.equals(type)) {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(sb.toString(), Book.class);
            } else {
                JAXBContext context = JAXBContext.newInstance(Book.class);

                Unmarshaller unmarshaller = context.createUnmarshaller();
                return (Book) unmarshaller.unmarshal(new StringReader(sb.toString()));
            }
        }
    }

}
