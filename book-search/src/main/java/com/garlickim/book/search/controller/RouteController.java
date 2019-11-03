package com.garlickim.book.search.controller;

import com.garlickim.book.search.domain.*;
import com.garlickim.book.search.repository.HistoryStatistics;
import com.garlickim.book.search.service.AccountService;
import com.garlickim.book.search.service.SearchService;
import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RouteController {

    @Autowired
    AccountService accountService;

    @Autowired
    SearchService searchService;


    @GetMapping("/")
    public String index(Model model, Principal principal) {
        return (principal == null) ? "login" : "main";
    }


    // 회원가입 페이지
    @GetMapping("/signup")
    public String viewSignUp(Model model, Principal principal) {
        return "signup";
    }


    // 회원가입 로직
    @PostMapping("/user/signup")
    public String procSignup(Account account) {
        this.accountService.createNew(account);
        return "redirect:/";
    }


    // 책검색 메인 페이지
    @GetMapping("/main")
    public String viewMain(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("username", principal.getName());
            return "main";
        }

        return "login";
    }


    // 도서 검색
    @PostMapping("/book/search")
    public ModelAndView procBookSearch(Principal principal, @RequestBody BookSearch bookSearch) {
        ModelAndView mav = new ModelAndView("fragments :: resultFragment");

        try {
            Book books = this.searchService.searchBook(bookSearch);
            mav.addObject("data", books);

            // 검색 history save
            searchService.saveHistroy(History.builder().username(principal.getName()).keyword(bookSearch.getKeyword()).build());

        } catch (Exception e) {
        }

        return mav;
    }

    @GetMapping("/members/{username}/keywords")
    public ModelAndView procKeywordHistory(@PathVariable String username) {
        ModelAndView mav = new ModelAndView("fragments :: historyFragment");

        List<History> list = searchService.findKeywordHistory(username);

        mav.addObject("data", list);

        return mav;
    }

    @GetMapping("/keywords")
    public ModelAndView procKeywords() {
        ModelAndView mav = new ModelAndView("fragments :: keywordsFragment");

        ArrayList<HistoryStatistics> byKeywordAndCount = searchService.findByKeywordAndCount();

        mav.addObject("data", byKeywordAndCount);

        return mav;
    }


}
