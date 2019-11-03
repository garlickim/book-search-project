package com.garlickim.book.search.account;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.garlickim.book.search.domain.vo.BookSearch;
import com.garlickim.book.search.domain.vo.Document;
import com.garlickim.book.search.service.impl.KakaoServiceImpl;
import com.garlickim.book.search.service.impl.NaverServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookSearchTest
{

    @Autowired
    KakaoServiceImpl kakaoService;

    @Autowired
    NaverServiceImpl naverService;

    @Test
    public void searchKakaoTest1()
    {
        Document document = this.kakaoService.searchBook(BookSearch.builder()
                                                                   .type("1")
                                                                   .keyword("자료구조")
                                                                   .page(1)
                                                                   .build());

        Assert.assertNotNull(document.getBooks());
    }





    @Test
    public void searchNaverTest1()
    {
        Document document = this.naverService.searchBook(BookSearch.builder()
                                                                   .type("1")
                                                                   .keyword("자료구조")
                                                                   .page(1)
                                                                   .build());

        Assert.assertNotNull(document.getBooks());
    }
}
