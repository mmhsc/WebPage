package com.ftq.webpage.dao;

import com.ftq.webpage.pojo.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeywordDAO extends JpaRepository<Keyword, Integer> {

    Keyword getById(int id);
    List<Keyword> getByPid(int pid);
    Keyword getByKeyword(String keyword);

}
