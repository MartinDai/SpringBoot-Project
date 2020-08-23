package com.doodl6.springboot.web.service.elasticsearch.repository;

import com.doodl6.springboot.web.service.elasticsearch.vo.Article;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article, Long> {

    Iterable<Article> findByTitle(String title);

}
