package com.garlickim.book.search.repository;

import com.garlickim.book.search.domain.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface HistoryRepository extends JpaRepository<History, Integer> {

    ArrayList<History> findByUsernameOrderBySearchTimeDesc(String username);

    @Query(nativeQuery = true, value = "SELECT TOP 10 KEYWORD, COUNT(*) CNT FROM HISTORY GROUP BY KEYWORD ORDER BY CNT DESC, KEYWORD")
    ArrayList<HistoryStatistics> findByKeywordAndCount();


}
