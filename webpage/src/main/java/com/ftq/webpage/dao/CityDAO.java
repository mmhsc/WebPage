package com.ftq.webpage.dao;

import com.ftq.webpage.pojo.City;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityDAO extends JpaRepository<City, Integer> {

    List<City> findByReleasedateBetween(String start, String end);

    long countByCategoryInAndReleasedateBetween(List<String> categories, String start, String end);

    List<City> findAllByCategoryInAndReleasedateBetween(List<String> categories, String start, String end, Pageable pageable);

}
