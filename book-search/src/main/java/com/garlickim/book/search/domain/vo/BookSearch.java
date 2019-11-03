package com.garlickim.book.search.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookSearch
{
    private String  type;

    private String  keyword;

    private Integer page;
}
