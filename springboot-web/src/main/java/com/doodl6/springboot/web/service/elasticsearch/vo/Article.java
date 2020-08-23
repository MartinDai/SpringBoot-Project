package com.doodl6.springboot.web.service.elasticsearch.vo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

@Getter
@Setter
@Document(indexName = "blog")
public class Article {

    @Id
    private Long id;

    private String title;

    private String category;

    private String content;

    private Date publishTime;

}
