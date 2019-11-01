package com.garlickim.book.search.account;

import com.garlickim.book.search.domain.Account;
import com.garlickim.book.search.domain.History;
import com.garlickim.book.search.repository.HistoryRepository;
import com.garlickim.book.search.service.AccountService;
import com.garlickim.book.search.service.SearchService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HistoryTest {

    @Autowired
    AccountService accountService;

    @Autowired
    SearchService searchService;

    @Autowired
    HistoryRepository historyRepository;

    @Before
    public void before() {
        accountService.createNew(Account.builder().username("garlic").password("garlic").build());

        historyRepository.save(History.builder().username("garlic").keyword("테스트1").build());
        historyRepository.save(History.builder().username("garlic").keyword("테스트2").build());
        historyRepository.save(History.builder().username("garlic").keyword("테스트3").build());

        historyRepository.save(History.builder().username("garlic2").keyword("테스트").build());
    }


    @Test
    public void keywordHistoryTest1(){
        List<History> list = searchService.findKeywordHistory("garlic");

        Assert.assertEquals("테스트3", list.get(0).getKeyword());
        Assert.assertEquals("테스트2", list.get(1).getKeyword());
        Assert.assertEquals("테스트1", list.get(2).getKeyword());
    }


}
