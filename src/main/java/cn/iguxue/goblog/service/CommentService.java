package cn.iguxue.goblog.service;

import cn.iguxue.goblog.entity.Comment;
import cn.iguxue.goblog.vo.CommentVO;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;

public interface CommentService {
    List<Comment> getAll();

    Integer getAllCount();

    IPage<Comment> getByPage(Integer start, Integer size);

    Comment getById(Long id);

    List<CommentVO> getCommentList(Long articleId);

    void save(Comment comment);

    void update(Comment comment);

    void delete(List<Long> ids);

    List<Comment> getLatest();
}
