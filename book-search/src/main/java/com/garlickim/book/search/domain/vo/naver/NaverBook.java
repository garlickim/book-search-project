package com.garlickim.book.search.domain.vo.naver;

import java.io.Serializable;

import lombok.Data;

@Data
public class NaverBook implements Serializable
{
    private static final long serialVersionUID = 146644986057115837L;

    // 도서 제목
    private String            title;

    // 검색 결과 문서의 하이퍼텍스트 link
    private String            link;

    // 도서 표지 썸네일 URL
    private String            image;

    // 도서 저자
    private String            author;

    // 도서 정가
    private Integer           price;

    // 도서 할인 가격
    private Integer           discount;

    // 출판사
    private String            publisher;

    // 출간일
    private String            pubdate;

    // 국제 표준 도서번호
    private String            isbn;

    // 도서 소개
    private String            description;

}
