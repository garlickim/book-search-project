package com.garlickim.book.search.service;

import com.garlickim.book.search.account.Account;
import com.garlickim.book.search.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// User 인증 클래스
@Service
public class AccountService implements UserDetailsService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;    // SecuriryConfig.java에서 Bcrypt를 기본 encoder로 설정하였다.

    // 사용자 인증 메소드
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException(username);
        }

        return User.builder().username(account.getUsername()).password(account.getPassword()).roles(account.getRole()).build();
    }

    public Account createNew(Account account) {
        account.encodePassword(passwordEncoder);
        account.setRole("USER");
        return this.accountRepository.save(account);
    }
}
