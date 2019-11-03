package com.garlickim.book.search.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.garlickim.book.search.domain.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer>
{

    Account findByUsername(String username);
}
