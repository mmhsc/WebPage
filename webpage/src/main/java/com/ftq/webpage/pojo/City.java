package com.ftq.webpage.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "city")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class City extends Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    private String item;
    private String purchaser;
    private String phonenumber;
    private String category;
    private String location;
    private float budget;
    private String releasedate;

}
