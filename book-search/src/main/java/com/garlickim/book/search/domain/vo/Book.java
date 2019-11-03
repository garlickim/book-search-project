package com.garlickim.book.search.domain.vo;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Book implements Serializable
{
    private static final long serialVersionUID = 2963544831536092666L;

    // 도서 제목
    private String            title;

    // 도서 소개
    private String            description;

    // 국제 표준 도서번호(ISBN10 ISBN13) (ISBN10,ISBN13 중 하나 이상 존재하며, ' '(공백)을 구분자로 출력됩니다)
    private String            isbn;

    // 도서 출판날짜. ISO 8601. [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz]
    private String            publishDate;

    // 도서 저자 리스트
    private String[]          authors;

    // 도서 출판사
    private String            publisher;

    // 도서 정가
    private Integer           price;

    // 도서 판매가
    private Integer           salesPrice;

    // 도서 표지 썸네일 URL
    private String            thumbUrl;
}
