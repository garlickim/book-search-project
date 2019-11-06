package com.garlickim.book.search.book;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.garlickim.book.search.domain.vo.BookSearch;
import com.garlickim.book.search.domain.vo.Document;
import com.garlickim.book.search.exception.BookSearchException;
import com.garlickim.book.search.service.impl.KakaoServiceImpl;
import com.garlickim.book.search.service.impl.NaverServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookSearchTest
{

    @Autowired
    KakaoServiceImpl         kakaoService;

    @Autowired
    NaverServiceImpl         naverService;

    @Rule
    public ExpectedException expectedExcetption = ExpectedException.none();





    // kakao api 호출시, 정상 작동 확인
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





    // naver api 호출시, 정상 작동 확인
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





    // 잘못된 BookSearch Type 전송시 에러 발생
    @Test
    public void searchNaverTest2()
    {
        BookSearch search = BookSearch.builder()
                                      .type("0")
                                      .keyword("자료구조")
                                      .page(1)
                                      .build();

        this.expectedExcetption.expect(BookSearchException.class);

        this.naverService.searchBook(search);
    }
}