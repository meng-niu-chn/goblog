package cn.iguxue.goblog.service.impl;

import cn.iguxue.goblog.entity.*;
import cn.iguxue.goblog.mapper.*;
import cn.iguxue.goblog.service.LinkService;
import cn.iguxue.goblog.service.SiteService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class SiteServiceImpl implements SiteService {

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    LinkMapper linkMapper;

    @Autowired
    TagMapper tagMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    ArticleCategoryMapper articleCategoryMapper;

    @Override
    public IPage<Article> getArticlesByPage(Integer start, Integer size, Long categoryId) {
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        if (!StringUtils.isEmpty(categoryId)){
            LambdaQueryWrapper<ArticleCategory> lambdaQueryWrapperAC = new LambdaQueryWrapper<>();
            lambdaQueryWrapperAC.eq(ArticleCategory::getCategoryId,categoryId);
            List<ArticleCategory> articleCategoryList = articleCategoryMapper.selectList(lambdaQueryWrapperAC);
            List<Long> articleIds = new ArrayList<>();
            if (articleCategoryList != null) {
                articleCategoryList.forEach(articleCategory -> {
                    articleIds.add(articleCategory.getId());
                });
            }
            if (articleIds.size() == 0) {
                return null;
            }
            lambdaQueryWrapper.in(Article::getId,articleIds);
        }

        lambdaQueryWrapper.eq(Article::getType,"0");
        Page<Article> page = new Page<>(start,size);
        IPage<Article> selecePage = articleMapper.selectPage(page,lambdaQueryWrapper);
        return selecePage;
    }

    @Override
    public List<Link> getAllLinks() {
        return linkMapper.selectList(null);
    }

    @Override
    public List<Tag> getAllTags() {
        return tagMapper.selectList(null);
    }

    @Override
    public List<Category> getCategorys() {
        List<Category> categories = categoryMapper.selectList(null);
        if (categories != null) {
            categories.forEach(category -> {
                LambdaQueryWrapper<ArticleCategory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq(ArticleCategory::getCategoryId,category.getId());
                Integer count = articleCategoryMapper.selectCount(lambdaQueryWrapper);
                category.setCount(count);
            });
        }
        return categories;
    }

    @Override
    public Article getArticleById(Long id) {
        return articleMapper.selectById(id);
    }
}
