package com.garlickim.book.search.controller;

import com.garlickim.book.search.account.Account;
import com.garlickim.book.search.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class RouteController {

    @Autowired
    AccountService accountService;

    @GetMapping("/")
    public String index(Model model, Principal principal) {
        if (principal == null) {
            return "login";
        } else {
            return "info";
        }
    }

    // 회원가입 페이지
    @GetMapping("/signup")
    public String signup(Model model, Principal principal) {
            return "signup";
    }

    // 회원가입 로직
    @PostMapping("/user/signup")
    public String signupFlow(Account account) {
        accountService.createNew(account);
        return "redirect:/";
    }

}
