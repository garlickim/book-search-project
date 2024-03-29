package com.garlickim.book.search.domain.vo.kakao;

import java.io.Serializable;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class KakaoBook implements Serializable
{
    private static final long serialVersionUID = -7331060734989726074L;

    // 도서 제목
    private String            title;

    // 도서 소개
    private String            contents;

    // 도서 상세 URL
    private String            url;

    // 국제 표준 도서번호(ISBN10 ISBN13) (ISBN10,ISBN13 중 하나 이상 존재하며, ' '(공백)을 구분자로 출력됩니다)
    private String            isbn;

    // 도서 출판날짜. ISO 8601. [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz]
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
                timezone = "Asia/Seoul")
    private Instant           datetime;

    // 도서 저자 리스트
    private List<String>      authors;

    // 도서 출판사
    private String            publisher;

    // 도서 번역자 리스트
    private String[]          translators;

    // 도서 정가
    private Integer           price;

    // 도서 판매가
    private Integer           sale_price;

    // 도서 표지 썸네일 URL
    private String            thumbnail;

    // 도서 판매 상태 정보
    private String            status;





    // Instant type의 datetime을 yyyy-MM-dd 포멧의 String으로 변경
    public String getDatetime()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                                                       .withLocale(Locale.getDefault())
                                                       .withZone(TimeZone.getTimeZone("Asia/Seoul")
                                                                         .toZoneId());
        return formatter.format(this.datetime);
    }

}
