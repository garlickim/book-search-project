package com.garlickim.book.search.domain.vo.kakao;

import lombok.Data;

@Data
public class Meta
{
    private Boolean is_end;

    private Integer pageable_count;

    private Integer total_count;
}
