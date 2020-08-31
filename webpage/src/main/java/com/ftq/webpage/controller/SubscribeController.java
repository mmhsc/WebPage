package com.ftq.webpage.controller;

import com.ftq.webpage.entity.SubscriptionEntity;
import com.ftq.webpage.pojo.User;
import com.ftq.webpage.result.Result;
import com.ftq.webpage.result.ResultFactory;
import com.ftq.webpage.service.KeywordService;
import com.ftq.webpage.service.SubscriptionService;
import com.ftq.webpage.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class SubscribeController {

    @Autowired
    private UserService userService;
    @Autowired
    private KeywordService keywordService;
    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping(value = "/api/subscribe")
    @ResponseBody
    public Result subscribe(@RequestBody Map<String, List<String>> keywords) {
        System.out.println(keywords);
        Subject subject = SecurityUtils.getSubject();
        User user = userService.getUserByName(subject.getPrincipal().toString());
        subscriptionService.deleteAllByUid(user.getId());
        for(String keyword : keywords.get("keywords")) {
            SubscriptionEntity subscriptionEntity = new SubscriptionEntity();
            subscriptionEntity.setUid(user.getId());
            subscriptionEntity.setKid(keywordService.getId(keyword));
            subscriptionService.add(subscriptionEntity);
        }
        return ResultFactory.buildSuccessResult("修改成功");
    }

    @GetMapping(value = "api/categories")
    @ResponseBody
    public List<String> getCategories() {
        return keywordService.getAllCategory();
    }

    @GetMapping(value = "/api/categories/{username}")
    @ResponseBody
    public List<String> getCategories(@PathVariable String username) {
        Subject subject = SecurityUtils.getSubject();
        User user = userService.getUserByName(subject.getPrincipal().toString());
        List<Integer> kidList = subscriptionService.getCategoryId(user.getId());
        return keywordService.getCategories(kidList);
    }

}
