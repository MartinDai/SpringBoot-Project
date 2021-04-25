package com.doodl6.springboot.elasticsearch;

import com.doodl6.springboot.common.web.response.BaseResponse;
import com.doodl6.springboot.elasticsearch.repository.ArticleRepository;
import com.doodl6.springboot.elasticsearch.vo.Article;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
     * 修改数据
     */
    @PostMapping("/updateData")
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
     * 删除数据
     */
    @PostMapping("/deleteData")
    public BaseResponse<Void> deleteData(Long id) {
        Preconditions.checkArgument(id != null, "ID不能为空");

        articleRepository.deleteById(id);

        return new BaseResponse<>();
    }

    /**
     * 查询数据
     */
    @GetMapping("/queryData")
    public BaseResponse<List<Article>> queryData(String title) {
        Preconditions.checkArgument(StringUtils.isNotBlank(title), "标题不能为空");

        Iterable<Article> iterable = articleRepository.findByTitle(title);

        return new BaseResponse<>(Lists.newArrayList(iterable));
    }
}
