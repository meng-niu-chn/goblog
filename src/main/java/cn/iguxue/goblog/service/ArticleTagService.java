package cn.iguxue.goblog.service;

import cn.iguxue.goblog.entity.Article;
import cn.iguxue.goblog.entity.ArticleTag;
import cn.iguxue.goblog.entity.Tag;

import java.util.List;

public interface ArticleTagService {
    /**
     * 根据文章ID查询文章+标签数据
     */
    List<Tag> getByArticleId(Long articleId);

    /**
     * 根据标签ID查询文章+标签数据
     */
    List<ArticleTag> getByTagId(Long tagId);

    /**
     * 根据文章ID删除
     */
    void deleteByArticleId(Long id);

    /**
     * 根据标签ID删除
     */
    void deleteByTagId(Long id);

    /**
     * 根据标签名称查询关联的文章
     */
    List<Article> getByTagName(String tag);

    void save(ArticleTag articleTag);

    void deleteByArticleIds(List<Long> ids);
}
