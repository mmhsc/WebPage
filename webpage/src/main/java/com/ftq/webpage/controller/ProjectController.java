package com.ftq.webpage.controller;

import com.ftq.webpage.pojo.City;
import com.ftq.webpage.pojo.Province;
import com.ftq.webpage.pojo.User;
import com.ftq.webpage.service.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
public class ProjectController {

    @Autowired
    private UserService userService;
    @Autowired
    private CityService cityService;
    @Autowired
    private ProvinceService provinceService;
    @Autowired
    private KeywordService keywordService;
    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping(value = "/api/city/projects")
    @ResponseBody
    public List<City> getCityProjects() {
        Subject subject = SecurityUtils.getSubject();
        User user = userService.getUserByName(subject.getPrincipal().toString());
        List<Integer> kidList = subscriptionService.getCategoryId(user.getId());
        List<String> categories = keywordService.getCategories(kidList);
        return cityService.getAllByCategories(categories);
    }

    @GetMapping(value = "/api/province/projects")
    @ResponseBody
    public List<Province> getProvinceProjects() {
        Subject subject = SecurityUtils.getSubject();
        User user = userService.getUserByName(subject.getPrincipal().toString());
        List<Integer> kidList = subscriptionService.getCategoryId(user.getId());
        List<String> categories = keywordService.getCategories(kidList);
        return provinceService.getAllByCategories(categories);
    }

    @PostMapping(value = "/api/city/projects/searchByCategory")
    @ResponseBody
    public List<City> searchCityProjectsByCategory(@RequestBody Map<String, String> data) throws Exception{
        String category = data.get("category");
        if (category.equals("全部"))   return getCityProjects();

        Subject subject = SecurityUtils.getSubject();
        User user = userService.getUserByName(subject.getPrincipal().toString());
        List<Integer> kidList = subscriptionService.getCategoryId(user.getId());
        List<String> categories = keywordService.getCategories(kidList);
        if (categories.contains(category)) return cityService.getAllByCategory(category);
        else return new LinkedList<City>();
    }


    @PostMapping(value = "/api/province/projects/searchByCategory")
    @ResponseBody
    public List<Province> searchProvinceProjectsByCategory(@RequestBody Map<String, String> data) throws Exception{
        String category = data.get("category");
        if (category.equals("全部"))   return getProvinceProjects();

        Subject subject = SecurityUtils.getSubject();
        User user = userService.getUserByName(subject.getPrincipal().toString());
        List<Integer> kidList = subscriptionService.getCategoryId(user.getId());
        List<String> categories = keywordService.getCategories(kidList);
        if (categories.contains(category)) return provinceService.getAllByCategory(category);
        else return new LinkedList<Province>();
    }

    @PostMapping(value = "/api/city/projects/searchByDate")
    @ResponseBody
    public List<City> searchCityProjectsByDate(@RequestBody Map<String, String> dateRange) {
        return cityService.getAllByDate(dateRange.get("start"), dateRange.get("end"));
    }

    @PostMapping(value = "/api/province/projects/searchByDate")
    @ResponseBody
    public List<Province> searchProvinceProjectsByDate(@RequestBody Map<String, String> dateRange) {
        return provinceService.getAllByDate(dateRange.get("start"), dateRange.get("end"));
    }

}
