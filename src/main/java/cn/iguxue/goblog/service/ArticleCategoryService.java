package cn.iguxue.goblog.service;

import cn.iguxue.goblog.entity.ArticleCategory;
import cn.iguxue.goblog.entity.Category;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

public interface ArticleCategoryService {
    void save(ArticleCategory articleCategory);

    void deleteByArticleIds(List<Long> ids);

    void deleteByCategoryIds(List<Long> ids);

    Category getCategoryByArticle(Long id);
}
