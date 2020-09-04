package com.ftq.webpage.service;

import com.ftq.webpage.dao.ProvinceDAO;
import com.ftq.webpage.pojo.Project;
import com.ftq.webpage.pojo.Province;
import com.ftq.webpage.pojo.SearchCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinceService {

    @Autowired
    ProvinceDAO provinceDAO;

    public long count(SearchCondition condition) {
        return provinceDAO.countByCategoryInAndReleasedateBetween(condition.getCategories(), condition.getStart(), condition.getEnd());
    }

    public List<Province> getProjects(String start, String end) {
        return provinceDAO.findByReleasedateBetween(start, end);
    }

    public List<Province> getProjects(SearchCondition condition) {
        Sort sort = Sort.by(Sort.Direction.DESC, "releasedate");
        return provinceDAO.findAllByCategoryInAndReleasedateBetween(condition.getCategories(), condition.getStart(),
                condition.getEnd(), PageRequest.of(condition.getCurrentPage() - 1, condition.getPagesize(), sort));
    }
}
