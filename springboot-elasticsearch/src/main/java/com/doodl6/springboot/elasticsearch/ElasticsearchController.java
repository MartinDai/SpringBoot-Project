package com.doodl6.springboot.elasticsearch;

import cn.hutool.core.lang.Assert;
import com.doodl6.springboot.common.web.response.BaseResponse;
import com.doodl6.springboot.elasticsearch.repository.ArticleRepository;
import com.doodl6.springboot.elasticsearch.vo.Article;
import com.google.common.collect.Lists;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * elasticsearch控制类
 */
@RestController
@RequestMapping("/elasticsearch")
public class ElasticsearchController {

    @Resource
    private ArticleRepository articleRepository;

    /**
     * 添加数据
     */
    @PostMapping("/addData")
    public BaseResponse<Void> addData(Long id, String title, String category, String content) {
        Assert.notNull(id, "ID不能为空");
        Assert.isTrue(StringUtils.isNotBlank(title), "标题不能为空");
        Assert.isTrue(StringUtils.isNotBlank(category), "类目不能为空");
        Assert.isTrue(StringUtils.isNotBlank(content), "内容不能为空");

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
     * 修改数据
     */
    @PostMapping("/updateData")
    public BaseResponse<Void> updateData(Long id, String title, String category, String content) {
        Assert.notNull(id, "ID不能为空");
        Assert.isTrue(StringUtils.isNotBlank(title), "标题不能为空");
        Assert.isTrue(StringUtils.isNotBlank(category), "类目不能为空");
        Assert.isTrue(StringUtils.isNotBlank(content), "内容不能为空");

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
     * 删除数据
     */
    @PostMapping("/deleteData")
    public BaseResponse<Void> deleteData(Long id) {
        Assert.notNull(id, "ID不能为空");

        articleRepository.deleteById(id);

        return new BaseResponse<>();
    }

    /**
     * 查询数据
     */
    @GetMapping("/queryData")
    public BaseResponse<List<Article>> queryData(String title) {
        Assert.isTrue(StringUtils.isNotBlank(title), "标题不能为空");

        Iterable<Article> iterable = articleRepository.findByTitle(title);

        return new BaseResponse<>(Lists.newArrayList(iterable));
    }
}
