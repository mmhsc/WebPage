package com.ftq.webpage.dao;

import com.ftq.webpage.pojo.Province;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProvinceDAO extends JpaRepository<Province, Integer> {

    List<Province> findByReleasedateBetween(String start, String end);

    long countByCategoryInAndReleasedateBetween(List<String> categories, String start, String end);

    List<Province> findAllByCategoryInAndReleasedateBetween(List<String> categories, String start, String end, Pageable pageable);

}
