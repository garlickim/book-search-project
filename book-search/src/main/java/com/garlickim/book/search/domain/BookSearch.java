package com.garlickim.book.search.domain;

import lombok.Data;

@Data
public class BookSearch
{
    private String  type;

    private String  keyword;

    private Integer page;
}
