package com.garlickim.book.search.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.garlickim.book.search.domain.entity.History;

public interface HistoryRepository extends JpaRepository<History, Integer>
{

    ArrayList<History> findByUsernameOrderBySearchTimeDesc(String username);





    @Query(nativeQuery = true,
           value = "SELECT TOP 10 KEYWORD, COUNT(*) CNT FROM HISTORY GROUP BY KEYWORD ORDER BY CNT DESC, KEYWORD")
    ArrayList<HistoryStatistics> findByKeywordAndCount();

}
