package com.garlickim.book.search.service.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.garlickim.book.search.domain.vo.BookSearch;
import com.garlickim.book.search.domain.vo.Document;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KakaoServiceImplTest
{
    @Autowired
    KakaoServiceImpl kakaoService;





    @Test
    public void getUrl()
    {
        Assert.assertEquals("https://dapi.kakao.com/v3/search/book?", this.kakaoService.getUrl());
    }





    @Test
    public void getQueryParameter()
    {
        Assert.assertEquals("target=title&query=%EC%9E%90%EB%A3%8C%EA%B5%AC%EC%A1%B0&page=1",
                            this.kakaoService.getQueryParameter(BookSearch.builder()
                                                                          .keyword("자료구조")
                                                                          .type("1")
                                                                          .page(1)
                                                                          .build()));
    }





    @Test
    public void setProperty()
    {
        try
        {
            URL url = new URL("");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            this.kakaoService.setProperty(connection);

            Assert.assertEquals("KakaoAK 54a6c384ec25e398d6b7e5fba3fad494", connection.getRequestProperty("Authorization"));
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }





    @Test
    public void convertBook()
    {
        String str = "{\"documents\":[{\"authors\":[\"이지영\"],\"contents\":\"『C로 배우는 쉬운 자료구조』는 자료를 구조화하는 다양한 방법을 단계별 그림과 삽화를 통해 쉽게 설명하고, 자료구조의 핵심 알고리즘을 C프로그램으로 구현해 보도록 구성했다. Self Test 코너를 통해 학습자 스스로 배운 내용을 체크해볼 수 있으며 다양한 유형의 문제를 수록해 각종 자격증이나 취업 준비하는 독학생에게 도움을 준다.\",\"datetime\":\"2016-07-28T00:00:00.000+09:00\",\"isbn\":\"1156642698 9791156642695\",\"price\":27000,\"publisher\":\"한빛아카데미\",\"sale_price\":25920,\"status\":\"정상판매\",\"thumbnail\":\"https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F1566965%3Ftimestamp%3D20190130232603\",\"title\":\"C로 배우는 쉬운 자료구조(개정판 3판)(IT Cookbook 203)\",\"translators\":[],\"url\":\"https://search.daum.net/search?w=bookpage\\u0026bookId=1566965\\u0026q=C%EB%A1%9C+%EB%B0%B0%EC%9A%B0%EB%8A%94+%EC%89%AC%EC%9A%B4+%EC%9E%90%EB%A3%8C%EA%B5%AC%EC%A1%B0%28%EA%B0%9C%EC%A0%95%ED%8C%90+3%ED%8C%90%29%28IT+Cookbook+203%29\"},{\"authors\":[\"천인국\",\"공용해\",\"허상호\"],\"contents\":\"▶ 이 책은 자료구조를 다룬 이론서입니다.\",\"datetime\":\"2019-02-22T00:00:00.000+09:00\",\"isbn\":\"8970509712 9788970509716\",\"price\":29000,\"publisher\":\"생능출판사\",\"sale_price\":27550,\"status\":\"정상판매\",\"thumbnail\":\"https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F4892560\",\"title\":\"자료구조(C언어로 쉽게 풀어 쓴)(개정판 3판)\",\"translators\":[],\"url\":\"https://search.daum.net/search?w=bookpage\\u0026bookId=4892560\\u0026q=%EC%9E%90%EB%A3%8C%EA%B5%AC%EC%A1%B0%28C%EC%96%B8%EC%96%B4%EB%A1%9C+%EC%89%BD%EA%B2%8C+%ED%92%80%EC%96%B4+%EC%93%B4%29%28%EA%B0%9C%EC%A0%95%ED%8C%90+3%ED%8C%90%29\"}],\"meta\":{\"is_end\":true,\"pageable_count\":2,\"total_count\":2}}";

        Document document = this.kakaoService.convertBook(str);
        List<String> list = document.getBooks()
                                    .stream()
                                    .map(book -> book.getTitle())
                                    .collect(Collectors.toList());

        Assert.assertTrue(list.contains("C로 배우는 쉬운 자료구조(개정판 3판)(IT Cookbook 203)"));
        Assert.assertNotNull(document.getBooks());
        Assert.assertNotNull(document.getTotalCnt());
        Assert.assertNotNull(document.getPageableCnt());

    }





    @Test
    public void searchBook()
    {
        Document document = this.kakaoService.searchBook(BookSearch.builder()
                                                                   .keyword("자료구조")
                                                                   .type("1")
                                                                   .page(1)
                                                                   .build());

        Assert.assertNotNull(document.getBooks());
    }
}
