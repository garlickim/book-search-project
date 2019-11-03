package com.garlickim.book.search.domain.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Document implements Serializable
{
    private static final long serialVersionUID = -3637676514790132259L;

    private List<Book>        books;

    private Integer           pageableCnt;

    private Integer           totalCnt;
}
