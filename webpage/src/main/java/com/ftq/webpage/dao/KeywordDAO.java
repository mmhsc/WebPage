package com.ftq.webpage.dao;

import com.ftq.webpage.pojo.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeywordDAO extends JpaRepository<Keyword, Integer> {

    public Keyword getById(int id);
    public List<Keyword> getByPid(int pid);
    public Keyword getByKeyword(String keyword);

}
