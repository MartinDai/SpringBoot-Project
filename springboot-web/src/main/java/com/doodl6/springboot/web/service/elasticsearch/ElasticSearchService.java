package com.doodl6.springboot.web.service.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.doodl6.springboot.web.service.elasticsearch.vo.Article;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * ES服务
 */
@Service
public class ElasticSearchService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final String BLOG_INDEX = "blog";

    private static final String ARTICLE_TYPE = "article";

//    @Resource
    private RestHighLevelClient restHighLevelClient;

    /**
     * 添加文章数据
     */
    public void addArticle(Article article) throws IOException {
        IndexRequest indexRequest = new IndexRequest(BLOG_INDEX, ARTICLE_TYPE);
        indexRequest.id(article.getId().toString());
        indexRequest.source(JSON.toJSONString(article), XContentType.JSON);
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

        logger.info("添加数据 | {}", JSON.toJSONString(indexResponse));
    }

    /**
     * 修改文章数据
     */
    public void updateArticle(Article article) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(BLOG_INDEX, ARTICLE_TYPE, article.getId().toString());
        updateRequest.doc(JSON.toJSONString(article), XContentType.JSON);
        UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);

        logger.info("修改数据 | {}", JSON.toJSONString(updateResponse));
    }

    /**
     * 删除文章数据
     */
    public void deleteArticle(long articleId) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest(BLOG_INDEX, ARTICLE_TYPE, String.valueOf(articleId));
        DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);

        logger.info("删除数据 | {}", JSON.toJSONString(deleteResponse));
    }

    /**
     * 查询文章数据
     */
    public List<Article> queryArticle(String keyword, String category) throws IOException {
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //数据偏移量，下标从0开始，取前5个
        sourceBuilder.from(0);
        sourceBuilder.size(5);
        //指定返回结果的字段，第一个参数为包含的字段，第二个参数为排除的字段
        sourceBuilder.fetchSource(new String[]{"title"}, new String[]{});

        //分词匹配查询

        //且条件组合，必须满足每一项，计算相关性得分
        boolBuilder.must(QueryBuilders.matchQuery("title", keyword));
//        boolBuilder.must(QueryBuilders.matchQuery("content", keyword));

        //且条件组合，必须满足每一项，不计算相关性得分
//        boolBuilder.filter(QueryBuilders.matchQuery("title", keyword));
//        boolBuilder.filter(QueryBuilders.matchQuery("content", keyword));

        //或条件组合，满足任意一项即可
//        boolBuilder.should(QueryBuilders.matchQuery("title", keyword));
//        boolBuilder.should(QueryBuilders.matchQuery("content", keyword));

        //非条件组合，不满足每一项
//        boolBuilder.mustNot(QueryBuilders.matchQuery("title", keyword));
//        boolBuilder.mustNot(QueryBuilders.matchQuery("content", keyword));

        if (StringUtils.isNotBlank(category)) {
            //不分词匹配查询
            boolBuilder.must(QueryBuilders.matchPhraseQuery("category", category));

            //只匹配分词，如字段值为"Quick Foxes"，则只有"Quick"和"Foxes"可以匹配到值，"Quick Foxes"则不行
//            boolBuilder.must(QueryBuilders.termQuery("category", category));
        }

        //范围条件
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("publishTime");
        rangeQueryBuilder.gte("2018-12-31T00:00:00Z");
        rangeQueryBuilder.lte("2018-12-31T23:59:59Z");
        boolBuilder.must(rangeQueryBuilder);

        sourceBuilder.query(boolBuilder);
        SearchRequest searchRequest = new SearchRequest();
        //指定索引名称，如果未指定，则会查询所有
        searchRequest.indices(BLOG_INDEX);
        searchRequest.types(ARTICLE_TYPE);
        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        logger.info("查询数据 | {} | {} | {}", keyword, category, JSON.toJSONString(searchResponse));

        List<Article> articleList = Lists.newArrayList();
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        for (SearchHit searchHit : searchHits) {
            Article article = new Article();
            article.setId(Long.valueOf(searchHit.getId()));
            article.setTitle((String) searchHit.getSourceAsMap().get("title"));

            articleList.add(article);
        }

        return articleList;
    }

}
