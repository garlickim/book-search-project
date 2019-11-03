package com.garlickim.book.search.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.garlickim.book.search.domain.entity.History;
import com.garlickim.book.search.repository.HistoryRepository;
import com.garlickim.book.search.repository.HistoryStatistics;
import com.garlickim.book.search.service.HistoryService;

@Service
public class HistoryServiceImpl implements HistoryService
{

    @Autowired
    HistoryRepository historyRepository;

    public void saveHistroy(History history)
    {
        historyRepository.save(history);
    }





    public List<History> findKeywordHistory(String username)
    {
        ArrayList<History> histories = historyRepository.findByUsernameOrderBySearchTimeDesc(username);

        return histories;
    }





    public List<HistoryStatistics> findByKeywordAndCount()
    {
        return historyRepository.findByKeywordAndCount();
    }
}
