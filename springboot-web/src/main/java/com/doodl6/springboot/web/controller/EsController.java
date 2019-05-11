package com.doodl6.springboot.web.controller;

import com.doodl6.springboot.web.response.base.BaseResponse;
import com.doodl6.springboot.web.service.elasticsearch.ElasticSearchService;
import com.doodl6.springboot.web.service.elasticsearch.vo.Article;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * elasticSearch控制类
 */
@RestController
@RequestMapping("/es")
public class EsController extends BaseController {

    @Resource
    private ElasticSearchService elasticSearchService;

    /**
     * 添加ES数据
     */
    @RequestMapping("/addData")
    public BaseResponse<Void> addData(Long id, String title, String category, String content) throws IOException {
        Preconditions.checkArgument(id != null, "ID不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(title), "标题不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(category), "类目不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(content), "内容不能为空");

        Article article = new Article();
        article.setId(id);
        article.setTitle(title);
        article.setCategory(category);
        article.setContent(content);
        article.setPublishTime(new Date());

        elasticSearchService.addArticle(article);

        return new BaseResponse<>();
    }

    /**
     * 修改ES数据
     */
    @RequestMapping("/updateData")
    public BaseResponse<Void> updateData(Long id, String title, String category, String content) throws IOException {
        Preconditions.checkArgument(id != null, "ID不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(title), "标题不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(category), "类目不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(content), "内容不能为空");

        Article article = new Article();
        article.setId(id);
        article.setTitle(title);
        article.setCategory(category);
        article.setContent(content);
        article.setPublishTime(new Date());

        elasticSearchService.updateArticle(article);

        return new BaseResponse<>();
    }

    /**
     * 删除ES数据
     */
    @RequestMapping("/deleteData")
    public BaseResponse<Void> deleteData(Long id) throws IOException {
        Preconditions.checkArgument(id != null, "ID不能为空");

        elasticSearchService.deleteArticle(id);

        return new BaseResponse<>();
    }

    /**
     * 查询ES数据
     */
    @RequestMapping("/queryData")
    public BaseResponse<List<Article>> queryData(String keyword, String category) throws IOException {
        Preconditions.checkArgument(StringUtils.isNotBlank(keyword), "关键字不能为空");

        List<Article> list = elasticSearchService.queryArticle(keyword, category);

        return new BaseResponse<>(list);
    }
}
