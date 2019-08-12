package cn.iguxue.goblog.mapper;

import cn.iguxue.goblog.entity.Article;
import cn.iguxue.goblog.entity.ArticleTag;
import cn.iguxue.goblog.entity.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {
    List<Tag> getByArticleId(long articleId);

    List<Article> getByTagName(String name);
}
