package com.garlickim.book.search.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.garlickim.book.search.domain.entity.History;
import com.garlickim.book.search.domain.vo.BookSearch;
import com.garlickim.book.search.domain.vo.Document;
import com.garlickim.book.search.repository.HistoryStatistics;
import com.garlickim.book.search.service.impl.HistoryServiceImpl;
import com.garlickim.book.search.service.impl.KakaoServiceImpl;
import com.garlickim.book.search.service.impl.NaverServiceImpl;

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
        Document books = null;
        try
        {
            books = this.kakaoService.searchBook(bookSearch);
        }
        catch ( Exception e )
        {
            books = this.naverService.searchBook(bookSearch);
        }

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
