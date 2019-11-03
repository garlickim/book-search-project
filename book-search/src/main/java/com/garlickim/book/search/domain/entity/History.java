package com.garlickim.book.search.domain.entity;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class History
{
    @Id
    @GeneratedValue
    private Integer id;

    private String  username;

    private String  keyword;

    @Builder.Default
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",
                timezone = "Asia/Seoul")
    private Instant searchTime = Instant.now();

}
