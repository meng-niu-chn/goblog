package cn.iguxue.goblog.service;

import cn.iguxue.goblog.entity.Article;
import cn.iguxue.goblog.entity.Category;
import cn.iguxue.goblog.entity.Link;
import cn.iguxue.goblog.entity.Tag;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface SiteService {

    IPage<Article> getArticlesByPage(Integer start, Integer size, Long categoryId);

    List<Link> getAllLinks();

    List<Tag> getAllTags();

    List<Category> getCategorys();

    Article getArticleById(Long id);
}
