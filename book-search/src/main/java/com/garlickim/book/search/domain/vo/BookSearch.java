package com.garlickim.book.search.domain.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookSearch
{
    private String  type;

    private String  keyword;

    private Integer page;
}
