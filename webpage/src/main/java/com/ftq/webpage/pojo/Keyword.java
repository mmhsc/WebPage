package com.ftq.webpage.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "keywords")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    private String keyword;
    private int pid;

}
