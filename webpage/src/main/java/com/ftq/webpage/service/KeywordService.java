package com.ftq.webpage.service;

import com.ftq.webpage.dao.KeywordDAO;
import com.ftq.webpage.pojo.Keyword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class KeywordService {

    @Autowired
    private KeywordDAO keywordDAO;

    public List<String> getAllCategory() {
        List<String> categories = new LinkedList<>();
        for(Keyword keyword : keywordDAO.getByPid(0)) {
            categories.add(keyword.getKeyword());
        }
        return categories;
    }

    public String getCategory(int kid) {
        return keywordDAO.getById(kid).getKeyword();
    }

    public int getId(String keyword) {
        return keywordDAO.getByKeyword(keyword).getId();
    }

    public List<String> getCategories(List<Integer> kids) {
        List<String> categories = new LinkedList<>();
        for (int kid :kids) {
            categories.add(getCategory(kid));
        }
        return categories;
    }

}
