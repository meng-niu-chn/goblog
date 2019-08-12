package cn.iguxue.goblog.vo;

import cn.iguxue.goblog.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVO implements Serializable {

    private Comment parent;

    private List<Comment> children;
}
