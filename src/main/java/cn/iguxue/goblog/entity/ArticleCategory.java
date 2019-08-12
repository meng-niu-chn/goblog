package cn.iguxue.goblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author TyCoding
 * @date 2018/10/22
 */
@Data
@TableName("tb_article_category")
public class ArticleCategory implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
    @NotNull
    private Long articleId;
    @NotNull
    private Long categoryId;

    public ArticleCategory() {
    }

    public ArticleCategory(Long articleId, Long categoryId) {
        this.articleId = articleId;
        this.categoryId = categoryId;
    }
}
