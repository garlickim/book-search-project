package com.garlickim.book.search.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.garlickim.book.search.domain.entity.Account;
import com.garlickim.book.search.service.impl.AccountServiceImpl;

@Controller
public class ViewController
{
    @Autowired
    AccountServiceImpl accountService;

    @GetMapping("/")
    public String mainView(Model model, Principal principal)
    {
        model.addAttribute("username", principal.getName());
        return "main";
    }





    // 로그인 페이지
    @GetMapping("/user/login")
    public String loginView()
    {
        return "login";
    }





    // 회원가입 페이지
    @GetMapping("/user/signup")
    public String viewSignUp()
    {
        return "signup";
    }





    // 회원가입 로직
    @PostMapping("/users")
    public String procSignup(Account account)
    {
        this.accountService.createNew(account);
        return "redirect:/";
    }
}
