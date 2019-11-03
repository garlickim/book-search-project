package com.garlickim.book.search.domain.vo.kakao;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class KakaoDocument implements Serializable
{
    private static final long serialVersionUID = -3637676514790132259L;

    private List<KakaoBook>   documents;

    private Meta              meta;
}
