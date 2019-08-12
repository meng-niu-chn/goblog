package cn.iguxue.goblog.service.impl;

import cn.iguxue.goblog.entity.ArticleCategory;
import cn.iguxue.goblog.entity.Category;
import cn.iguxue.goblog.enums.ResultEnum;
import cn.iguxue.goblog.exception.GoblogException;
import cn.iguxue.goblog.mapper.ArticleCategoryMapper;
import cn.iguxue.goblog.mapper.CategoryMapper;
import cn.iguxue.goblog.service.ArticleCategoryService;
import cn.iguxue.goblog.service.ArticleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.util.locale.LanguageTag;
import sun.util.locale.provider.FallbackLocaleProviderAdapter;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleCategoryServiceImpl implements ArticleCategoryService {

    @Autowired
    ArticleCategoryMapper articleCategoryMapper;

    @Autowired
    ArticleServiceImpl articleService;

    @Autowired
    CategoryMapper categoryMapper;

    private Boolean exist(ArticleCategory articleCategory){

        if (articleCategory != null) {
            LambdaQueryWrapper<ArticleCategory> lambdaQueryWrapper = new LambdaQueryWrapper();
            lambdaQueryWrapper.eq(ArticleCategory::getArticleId,articleCategory.getArticleId());
            return articleCategoryMapper.selectCount(lambdaQueryWrapper) > 0 ? true : false;
        } else {
            throw new GoblogException(ResultEnum.PARAM_ERROR);
        }

    }

    @Override
    public void save(ArticleCategory articleCategory) {
        if (!exist(articleCategory)) {
            articleCategoryMapper.insert(articleCategory);
        } else {
            LambdaQueryWrapper<ArticleCategory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(ArticleCategory::getArticleId,articleCategory.getArticleId());
            articleCategoryMapper.update(articleCategory,lambdaQueryWrapper);
        }
    }

    @Override
    public void deleteByArticleIds(List<Long> ids) {
        LambdaQueryWrapper<ArticleCategory> lambdaQueryWrapper = new LambdaQueryWrapper();
//        ids.forEach(id -> {
//            lambdaQueryWrapper.or().eq(ArticleCategory::getArticleId, id);
//        });
        lambdaQueryWrapper.in(ArticleCategory::getArticleId,ids);
//        List<ArticleCategory> articleCategoryList = articleCategoryMapper.selectList(lambdaQueryWrapper);
//        List<Long> idss = new ArrayList<>();
//        articleCategoryList.forEach(articleCategory -> {
//            idss.add(articleCategory.getId());
//        });
        articleCategoryMapper.delete(lambdaQueryWrapper);
//        articleCategoryMapper.deleteBatchIds(idss);
    }

    @Override
    public void deleteByCategoryIds(List<Long> ids) {
        LambdaQueryWrapper<ArticleCategory> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.in(ArticleCategory::getCategoryId,ids);
        List<ArticleCategory> articleCategoryList = articleCategoryMapper.selectList(lambdaQueryWrapper);
        List<Long> idss = new ArrayList<>();
        articleCategoryList.forEach( articleCategory -> {
            idss.add(articleCategory.getArticleId());
        });
        articleCategoryMapper.delete(lambdaQueryWrapper);
        articleService.setCategoryNull(idss);
    }

    @Override
    public Category getCategoryByArticle(Long id) {
        LambdaQueryWrapper<ArticleCategory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ArticleCategory::getArticleId, id);

        return categoryMapper.selectById(articleCategoryMapper.selectOne(lambdaQueryWrapper).getCategoryId());
    }
}
