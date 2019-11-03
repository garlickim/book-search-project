package com.garlickim.book.search.service;

import com.garlickim.book.search.domain.History;
import com.garlickim.book.search.repository.HistoryStatistics;

import java.util.List;

public interface HistoryService {

    void saveHistroy(History history);

    List<History> findKeywordHistory(String username);

    List<HistoryStatistics> findByKeywordAndCount();

}
