package com.garlickim.book.search.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class Document implements Serializable {
    private static final long serialVersionUID = -3637676514790132259L;

    private List<Book> books;

    private Boolean isEnd;

    private Integer pageableCnt;

    private Integer totalCnt;
}
