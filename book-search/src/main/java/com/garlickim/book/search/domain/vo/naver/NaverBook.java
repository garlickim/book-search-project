package com.garlickim.book.search.domain.vo.naver;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

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
    @JsonFormat(pattern = "yyyyMMdd",
                timezone = "Asia/Seoul")
    private Date              pubdate;

    // 국제 표준 도서번호
    private String            isbn;

    // 도서 소개
    private String            description;





    // Date type의 pubdate을 yyyy-MM-dd 포멧의 String으로 변경
    public String getPubdate()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(this.pubdate);
    }





    // naver api 호출시, title에 <br>, </br> 태그 존재하여 삭제 후 get
    public String getTitle()
    {
        return this.title.replaceAll("<b>", "")
                         .replaceAll("</b>", "");
    }





    // naver api 호출시, description에 <br>, </br> 태그 존재하여 삭제 후 get
    public String getDescription()
    {
        return this.description.replaceAll("<b>", "")
                               .replaceAll("</b>", "");
    }
}
