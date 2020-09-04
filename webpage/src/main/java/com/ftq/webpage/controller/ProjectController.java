package com.ftq.webpage.controller;

import com.ftq.webpage.pojo.SearchCondition;
import com.ftq.webpage.result.Result;
import com.ftq.webpage.result.ResultFactory;
import com.ftq.webpage.service.CityService;
import com.ftq.webpage.service.KeywordService;
import com.ftq.webpage.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProjectController {

    @Autowired
    private CityService cityService;
    @Autowired
    private ProvinceService provinceService;
    @Autowired
    private KeywordService keywordService;

    @PostMapping(value = "/api/city/projects")
    @ResponseBody
    public Result getCityProjects(@RequestBody SearchCondition condition) {
        return ResultFactory.buildSuccessResult(cityService.getProjcts(condition));
    }

    @PostMapping(value = "/api/province/projects")
    @ResponseBody
    public Result getProvinceProjects(@RequestBody SearchCondition condition) {
        return ResultFactory.buildSuccessResult(provinceService.getProjects(condition));
    }

    @PostMapping(value = "/api/city/projects/num")
    @ResponseBody
    public Result getCityProjectNum(@RequestBody SearchCondition condition) {
        // 若未指定查询类别，则默认按所有类别进行查询
        if (condition.getCategories() == null) {
            condition.setCategories(keywordService.getAllCategory());
        }
        return ResultFactory.buildSuccessResult(cityService.count(condition));
    }

    @PostMapping(value = "/api/province/projects/num")
    @ResponseBody
    public Result getProvinceProjectNum(@RequestBody SearchCondition condition) {
        // 若未指定查询类别，则默认按所有类别进行查询
        if (condition.getCategories() == null) {
            condition.setCategories(keywordService.getAllCategory());
        }
        return ResultFactory.buildSuccessResult(provinceService.count(condition));
    }

}
