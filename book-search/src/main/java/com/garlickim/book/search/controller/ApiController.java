package com.garlickim.book.search.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.garlickim.book.search.domain.BookSearch;
import com.garlickim.book.search.domain.History;
import com.garlickim.book.search.repository.HistoryStatistics;
import com.garlickim.book.search.service.impl.HistoryServiceImpl;
import com.garlickim.book.search.service.impl.KakaoServiceImpl;
import com.garlickim.book.search.service.impl.NaverServiceImpl;
import com.garlickim.book.search.vo.Document;

@RestController
public class ApiController
{
    @Autowired
    HistoryServiceImpl historyService;

    @Autowired
    KakaoServiceImpl   kakaoService;

    @Autowired
    NaverServiceImpl   naverService;

    // 도서 검색
    @PostMapping("/book/search")
    public ModelAndView procBookSearch(Principal principal, @RequestBody BookSearch bookSearch)
    {
        ModelAndView mav = new ModelAndView("fragments :: resultFragment");
        Document books = this.kakaoService.searchBook(bookSearch);
        mav.addObject("data", books);

        // 검색 history save
        this.historyService.saveHistroy(History.builder()
                                               .username(principal.getName())
                                               .keyword(bookSearch.getKeyword())
                                               .build());

        return mav;
    }





    @GetMapping("/members/{username}/keywords")
    public List<History> procKeywordHistory(@PathVariable String username)
    {
        return this.historyService.findKeywordHistory(username);
    }





    @GetMapping("/keywords")
    public ModelAndView procKeywords()
    {
        ModelAndView mav = new ModelAndView("fragments :: keywordsFragment");

        List<HistoryStatistics> byKeywordAndCount = this.historyService.findByKeywordAndCount();

        mav.addObject("data", byKeywordAndCount);

        return mav;
    }

}
