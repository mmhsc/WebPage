package com.ftq.webpage.pojo;

import lombok.Data;

import java.util.List;

@Data
public class SearchCondition {

    private List<String> categories;
    private String start;
    private String end;
    private int currentPage;
    private int pagesize;

}
