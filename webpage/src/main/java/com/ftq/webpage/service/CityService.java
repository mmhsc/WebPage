package com.ftq.webpage.service;

import com.ftq.webpage.dao.CityDAO;
import com.ftq.webpage.pojo.City;
import com.ftq.webpage.pojo.SearchCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    @Autowired
    CityDAO cityDAO;

    public long count(SearchCondition condition) {
        return cityDAO.countByCategoryInAndReleasedateBetween(condition.getCategories(), condition.getStart(), condition.getEnd());
    }

    public List<City> getProjcts(SearchCondition condition) {
        Sort sort = Sort.by(Sort.Direction.DESC, "releasedate");
        return cityDAO.findAllByCategoryInAndReleasedateBetween(condition.getCategories(), condition.getStart(),
                condition.getEnd(), PageRequest.of(condition.getCurrentPage() - 1, condition.getPagesize(), sort));
    }

    public List<City> getProjects(String start, String end) {
        return cityDAO.findByReleasedateBetween(start, end);
    }

}
