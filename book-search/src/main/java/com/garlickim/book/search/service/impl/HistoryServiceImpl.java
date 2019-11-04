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

    // 키워드 검색 이력 저장
    @Override
    public void saveHistory(History history)
    {
        this.historyRepository.save(history);
    }





    // 키워드 검색 이력 조회
    @Override
    public List<History> findKeywordHistory(String username)
    {
        ArrayList<History> histories = this.historyRepository.findByUsernameOrderBySearchTimeDesc(username);

        return histories;
    }





    // 인기 검색어 조회
    @Override
    public List<HistoryStatistics> findByKeywordAndCount()
    {
        return this.historyRepository.findByKeywordAndCount();
    }
}
