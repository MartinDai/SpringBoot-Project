package com.doodl6.springboot.web.service.elasticsearch.repository;

import com.doodl6.springboot.web.service.elasticsearch.vo.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ArticleRepository extends ElasticsearchRepository<Article, Long> {

    Iterable<Article> findByTitle(String title);

}
