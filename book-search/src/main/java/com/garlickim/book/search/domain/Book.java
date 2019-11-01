package com.garlickim.book.search.domain;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class Book implements Serializable
{
    private static final long serialVersionUID = -3637676514790132259L;

    private List<Document>    documents;

    private Meta              meta;
}
