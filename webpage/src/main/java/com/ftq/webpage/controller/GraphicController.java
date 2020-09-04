package com.ftq.webpage.controller;

import com.ftq.webpage.pojo.City;
import com.ftq.webpage.pojo.Province;
import com.ftq.webpage.result.Result;
import com.ftq.webpage.result.ResultFactory;
import com.ftq.webpage.service.CityService;
import com.ftq.webpage.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;


@Controller
public class GraphicController {

    @Autowired
    private CityService cityService;
    @Autowired
    private ProvinceService provinceService;

    // 获取releaseDate介于start与end之间的市县招标数据
    @PostMapping("/api/visualization/city/projects")
    @ResponseBody
    private Result getCityProjects(@RequestBody Map<String, String> dateRange) {
        return ResultFactory.buildSuccessResult(cityService.getProjects(dateRange.get("start"), dateRange.get("end")));
    }

    // 获取releaseDate介于start与end之间的省级招标数据
    @PostMapping("/api/visualization/province/projects")
    @ResponseBody
    private Result getProvinceProjects(@RequestBody Map<String, String> dateRange) {
        return ResultFactory.buildSuccessResult(provinceService.getProjects(dateRange.get("start"), dateRange.get("end")));
    }

}
