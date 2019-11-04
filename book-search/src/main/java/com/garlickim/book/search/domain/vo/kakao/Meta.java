package com.garlickim.book.search.domain.vo.kakao;

import java.io.Serializable;

import lombok.Data;

@Data
public class Meta implements Serializable
{
    private static final long serialVersionUID = 7710103965137212625L;

    private Boolean           is_end;

    private Integer           pageable_count;

    private Integer           total_count;
}
