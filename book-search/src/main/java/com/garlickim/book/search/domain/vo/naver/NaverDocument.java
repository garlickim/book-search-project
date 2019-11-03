package com.garlickim.book.search.domain.vo.naver;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class NaverDocument implements Serializable
{
    private static final long serialVersionUID = -4797280770711119475L;

    private String            lastBuildDate;

    private List<NaverBook>   items;

    private Integer           total;
    private Integer           start;
    private Integer           display;
}
