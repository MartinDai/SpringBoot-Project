package com.doodl6.springboot.web.controller;

import com.doodl6.springboot.web.response.base.BaseResponse;
import com.doodl6.springboot.web.service.elasticsearch.repository.ArticleRepository;
import com.doodl6.springboot.web.service.elasticsearch.vo.Article;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * elasticSearch控制类
 */
@RestController
@RequestMapping("/es")
public class EsController extends BaseController {

    @Resource
    private ArticleRepository articleRepository;

    /**
     * 添加ES数据
     */
    @RequestMapping("/addData")
    public BaseResponse<Void> addData(Long id, String title, String category, String content) {
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

        articleRepository.index(article);

        return new BaseResponse<>();
    }

    /**
     * 修改ES数据
     */
    @RequestMapping("/updateData")
    public BaseResponse<Void> updateData(Long id, String title, String category, String content) {
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

        articleRepository.save(article);

        return new BaseResponse<>();
    }

    /**
     * 删除ES数据
     */
    @RequestMapping("/deleteData")
    public BaseResponse<Void> deleteData(Long id) {
        Preconditions.checkArgument(id != null, "ID不能为空");

        articleRepository.deleteById(id);

        return new BaseResponse<>();
    }

    /**
     * 查询ES数据
     */
    @RequestMapping("/queryData")
    public BaseResponse<List<Article>> queryData(String title) {
        Preconditions.checkArgument(StringUtils.isNotBlank(title), "标题不能为空");

        Iterable<Article> iterable = articleRepository.findByTitle(title);

        return new BaseResponse<>(Lists.newArrayList(iterable));
    }
}
