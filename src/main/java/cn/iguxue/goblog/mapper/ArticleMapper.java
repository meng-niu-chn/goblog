package cn.iguxue.goblog.mapper;

import cn.iguxue.goblog.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ArticleMapper extends BaseMapper<Article> {

    @Select("SELECT DISTINCT DATE_FORMAT(publish_time, '%Y-%m') FROM tb_article ORDER BY DATE_FORMAT(publish_time, '%Y-%m') DESC")
    List<String> getArchivesDates();

    @Select("SELECT id, title, publish_time FROM tb_article WHERE publish_time LIKE CONCAT ('%', #{date} ,'%')")
    List<Article> getArchivesByDate(String date);

}
