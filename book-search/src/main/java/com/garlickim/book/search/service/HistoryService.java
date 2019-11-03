package com.garlickim.book.search.service;

import java.util.List;

import com.garlickim.book.search.domain.entity.History;
import com.garlickim.book.search.repository.HistoryStatistics;

public interface HistoryService
{

    void saveHistroy(History history);





    List<History> findKeywordHistory(String username);





    List<HistoryStatistics> findByKeywordAndCount();

}
