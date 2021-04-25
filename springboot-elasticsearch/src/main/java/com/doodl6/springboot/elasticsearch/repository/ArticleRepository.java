package com.doodl6.springboot.elasticsearch.repository;

import com.doodl6.springboot.elasticsearch.vo.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ArticleRepository extends ElasticsearchRepository<Article, Long> {

    Iterable<Article> findByTitle(String title);

}
