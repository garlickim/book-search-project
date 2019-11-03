package com.garlickim.book.search.account;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.garlickim.book.search.domain.entity.History;
import com.garlickim.book.search.repository.HistoryRepository;
import com.garlickim.book.search.repository.HistoryStatistics;
import com.garlickim.book.search.service.impl.AccountServiceImpl;
import com.garlickim.book.search.service.impl.HistoryServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HistoryTest
{

    @Autowired
    AccountServiceImpl accountService;

    @Autowired
    HistoryServiceImpl historyService;

    @Autowired
    HistoryRepository  historyRepository;

    @Before
    public void before()
    {
        this.historyRepository.deleteAll();

        IntStream.range(0, 3)
                 .forEach(i -> this.historyRepository.save(History.builder()
                                                                  .username("garlic")
                                                                  .keyword("토익")
                                                                  .build()));
        IntStream.range(0, 1)
                 .forEach(i -> this.historyRepository.save(History.builder()
                                                                  .username("garlic2")
                                                                  .keyword("오픽")
                                                                  .build()));
        IntStream.range(0, 1)
                 .forEach(i -> this.historyRepository.save(History.builder()
                                                                  .username("garlic")
                                                                  .keyword("텝스")
                                                                  .build()));
        IntStream.range(0, 1)
                 .forEach(i -> this.historyRepository.save(History.builder()
                                                                  .username("garlic")
                                                                  .keyword("토플")
                                                                  .build()));
    }





    @Test
    public void keywordHistoryTest1()
    {
        List<History> list = this.historyService.findKeywordHistory("garlic");

        Assert.assertEquals("토플",
                            list.get(0)
                                .getKeyword());
        Assert.assertEquals("텝스",
                            list.get(1)
                                .getKeyword());
        Assert.assertEquals("토익",
                            list.get(2)
                                .getKeyword());
    }





    @Test
    public void rankKeywordsTest1()
    {
        List<HistoryStatistics> byKeywordAndCount = this.historyService.findByKeywordAndCount();

        Assert.assertEquals("토익",
                            byKeywordAndCount.get(0)
                                             .getKeyword());
        Assert.assertEquals(Integer.valueOf(3),
                            byKeywordAndCount.get(0)
                                             .getCnt());
    }





    @Test
    public void rankKeywordsTest2()
    {

        IntStream.range(0, 4)
                 .forEach(i -> this.historyRepository.save(History.builder()
                                                                  .username("garlic")
                                                                  .keyword("자료구조")
                                                                  .build()));
        IntStream.range(0, 2)
                 .forEach(i -> this.historyRepository.save(History.builder()
                                                                  .username("garlic")
                                                                  .keyword("경제학")
                                                                  .build()));
        IntStream.range(0, 3)
                 .forEach(i -> this.historyRepository.save(History.builder()
                                                                  .username("garlic")
                                                                  .keyword("미술")
                                                                  .build()));
        IntStream.range(0, 1)
                 .forEach(i -> this.historyRepository.save(History.builder()
                                                                  .username("garlic")
                                                                  .keyword("음악")
                                                                  .build()));
        IntStream.range(0, 6)
                 .forEach(i -> this.historyRepository.save(History.builder()
                                                                  .username("garlic")
                                                                  .keyword("토론")
                                                                  .build()));
        IntStream.range(0, 2)
                 .forEach(i -> this.historyRepository.save(History.builder()
                                                                  .username("garlic")
                                                                  .keyword("열정")
                                                                  .build()));
        IntStream.range(0, 7)
                 .forEach(i -> this.historyRepository.save(History.builder()
                                                                  .username("garlic")
                                                                  .keyword("자기개발")
                                                                  .build()));
        IntStream.range(0, 1)
                 .forEach(i -> this.historyRepository.save(History.builder()
                                                                  .username("garlic")
                                                                  .keyword("스터디")
                                                                  .build()));
        IntStream.range(0, 1)
                 .forEach(i -> this.historyRepository.save(History.builder()
                                                                  .username("garlic")
                                                                  .keyword("정보통신")
                                                                  .build()));
        IntStream.range(0, 3)
                 .forEach(i -> this.historyRepository.save(History.builder()
                                                                  .username("garlic")
                                                                  .keyword("알고리즘")
                                                                  .build()));
        IntStream.range(0, 1)
                 .forEach(i -> this.historyRepository.save(History.builder()
                                                                  .username("garlic")
                                                                  .keyword("기술분석")
                                                                  .build()));
        IntStream.range(0, 2)
                 .forEach(i -> this.historyRepository.save(History.builder()
                                                                  .username("garlic")
                                                                  .keyword("JSP")
                                                                  .build()));

        List<HistoryStatistics> byKeywordAndCount = this.historyService.findByKeywordAndCount();

        Assert.assertEquals(10, byKeywordAndCount.size());

        Assert.assertEquals("자기개발",
                            byKeywordAndCount.get(0)
                                             .getKeyword());
        Assert.assertEquals(Integer.valueOf(7),
                            byKeywordAndCount.get(0)
                                             .getCnt());

        Assert.assertEquals("자료구조",
                            byKeywordAndCount.get(2)
                                             .getKeyword());
        Assert.assertEquals(Integer.valueOf(4),
                            byKeywordAndCount.get(2)
                                             .getCnt());

        Assert.assertEquals("알고리즘",
                            byKeywordAndCount.get(4)
                                             .getKeyword());
        Assert.assertEquals(Integer.valueOf(3),
                            byKeywordAndCount.get(4)
                                             .getCnt());

    }

}
