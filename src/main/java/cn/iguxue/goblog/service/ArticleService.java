package cn.iguxue.goblog.service;

import cn.iguxue.goblog.dto.ArticleArchive;
import cn.iguxue.goblog.entity.Article;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public interface ArticleService {
    List<Article> getAll();

    Integer getAllCount();

    Article getById(Long id);

    List<ArticleArchive> getArchives();

    void save(Article article);

    void update(Article article);

    void delete(List<Long> ids);

    IPage<Article> getByPage(Integer start, Integer size, String titles);

    void setCategoryNull(List<Long> idss);

    List<Article> getLatest();
}
