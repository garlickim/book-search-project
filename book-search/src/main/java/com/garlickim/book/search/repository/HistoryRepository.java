package com.garlickim.book.search.repository;

import com.garlickim.book.search.domain.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface HistoryRepository extends JpaRepository<History, Integer>
{
    ArrayList<History> findByUsernameOrderBySearchTimeDesc(String username);
}
