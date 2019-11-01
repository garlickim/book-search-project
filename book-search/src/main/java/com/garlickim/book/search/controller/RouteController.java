package com.garlickim.book.search.controller;

import com.garlickim.book.search.domain.Account;
import com.garlickim.book.search.domain.Book;
import com.garlickim.book.search.domain.BookSearch;
import com.garlickim.book.search.service.AccountService;
import com.garlickim.book.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class RouteController
{

    @Autowired
    AccountService accountService;

    @Autowired
    SearchService  searchService;





    @GetMapping("/")
    public String index(Model model, Principal principal)
    {
        return (principal == null) ? "login" : "main";
    }





    // 회원가입 페이지
    @GetMapping("/signup")
    public String viewSignUp(Model model, Principal principal)
    {
        return "signup";
    }





    // 회원가입 로직
    @PostMapping("/user/signup")
    public String procSignup(Account account)
    {
        this.accountService.createNew(account);
        return "redirect:/";
    }





    // 책검색 메인 페이지
    @GetMapping("/main")
    public String viewMain(Model model, Principal principal)
    {
        return (principal == null) ? "login" : "main";
    }




    // 도서 검색
    @PostMapping("/book/search")
    public ModelAndView procBookSearch(@RequestBody BookSearch bookSearch)
    {
        ModelAndView mav = new ModelAndView("fragments :: resultFragment");

        try
        {
            Book books = this.searchService.searchBook(bookSearch);
            mav.addObject("data", books);
        }
        catch ( Exception e )
        {
        }

        return mav;
    }

}
