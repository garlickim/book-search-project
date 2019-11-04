package com.garlickim.book.search.domain.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookSearch implements Serializable
{
    private static final long serialVersionUID = -4165458886390333095L;

    private String            type;

    private String            keyword;

    private Integer           page;
}
