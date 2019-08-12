package cn.iguxue.goblog.service.impl;

import cn.iguxue.goblog.dto.ArticleArchive;
import cn.iguxue.goblog.entity.*;
import cn.iguxue.goblog.enums.ResultEnum;
import cn.iguxue.goblog.exception.GoblogException;
import cn.iguxue.goblog.mapper.ArticleMapper;
import cn.iguxue.goblog.service.ArticleCategoryService;
import cn.iguxue.goblog.service.ArticleService;
import cn.iguxue.goblog.service.ArticleTagService;
import cn.iguxue.goblog.service.TagService;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    CategoryServiceImpl categoryService;

    @Autowired
    ArticleCategoryService articleCategoryService;

    @Autowired
    TagService tagService;

    @Autowired
    ArticleTagService articleTagService;



    @Override
    public List<Article> getAll() {
        return articleMapper.selectList(null);
    }

    @Override
    public List<Article> getLatest() {
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Article::getPublishTime).last("LIMIT 3");
        return articleMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    public Integer getAllCount() {
        return articleMapper.selectCount(null);
    }

    @Override
    public Article getById(Long id) {
        return articleMapper.selectById(id);
    }

    @Override
    public List<ArticleArchive> getArchives() {
        List<ArticleArchive> articleArchives = new ArrayList<>();
        try {
            List<String> dates = articleMapper.getArchivesDates();
            dates.forEach( date -> {
                List<Article> articles = articleMapper.getArchivesByDate(date);
                ArticleArchive articleArchive = new ArticleArchive(date, articles);
                articleArchives.add(articleArchive);
            });
        } catch (Exception e) {
            throw new GoblogException(101);
        }
        return articleArchives;
    }

    /*
    * 更新 文章-分类-标签，三表间的关联
    * */
    private void updateArticleCategoryTag(Article article) {

        if (article.getId() > 0) {
            if (article.getCategory() != null) {
                //证明新插入的文章有分类信息，将这个文章分类保存到分类表中

                if (!categoryService.hasCategory(article.getCategory())) {
                    categoryService.save(new Category(article.getCategory()));
                }

                //保存了分类信息再保存分类-文章的关联信息
                Category category = categoryService.getByName(article.getCategory());
                articleCategoryService.save(new ArticleCategory(article.getId(), category.getId()));
            }
            if (article.getTags() != null) {
                //证明新插入的文章有标签数据，将标签数据保存到标签表中
                List<String> list = (List) JSONArray.parse(article.getTags()); //前端传来的标签是JSON字符串格式的标签名称
                if (list.size() > 0) {
                    list.forEach(name -> {
                        Tag tag = tagService.getByName(name); //因为标签是多个的，需要依次将标签信息保存到标签表中

                        if (tag == null) {
                            tagService.save(new Tag(name));
                            tag = tagService.getByName(name);
                        }

                        if (tag != null) {
                            //说明该标签插入成功或已存在，建立标签-文章关联信息
                            articleTagService.save(new ArticleTag(article.getId(), tag.getId()));
                        }
                    });
                }
            }
        }
    }

    @Override
    public void save(Article article) {
//        articleMapper.insert(article);
        try {
            if (article.getState().equals("1")) {
                article.setPublishTime(new Date());
            }
            //article.setAuthor(((User) SecurityUtils.getSubject().getPrincipal()).getUsername());
            article.setAuthor("test");
            article.setEditTime(new Date());
            article.setCreateTime(new Date());
            articleMapper.insert(article);
            updateArticleCategoryTag(article);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Article article) {
        article.setEditTime(new Date());
        articleMapper.updateById(article);
        updateArticleCategoryTag(article);
    }

    @Override
    public void delete(List<Long> ids) {

        articleMapper.deleteBatchIds(ids);
        articleCategoryService.deleteByArticleIds(ids);
        articleTagService.deleteByArticleIds(ids);
    }

    @Override
    public IPage<Article> getByPage(Integer start, Integer size, String titles) {
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(titles)) {
            lambdaQueryWrapper.like(Article::getTitle, titles);
        }
        Page<Article> page = new Page<>(start,size);
        IPage<Article> selecePage = articleMapper.selectPage(page,lambdaQueryWrapper);
        return selecePage;
    }

    @Override
    public void setCategoryNull(List<Long> idss) {
        if (idss.size() > 0) {
            List<Article> articles = articleMapper.selectBatchIds(idss);
            if (articles.size() > 0) {
                articles.forEach(article -> {
                    article.setCategory(null);
                });
            }
        }
    }


}
