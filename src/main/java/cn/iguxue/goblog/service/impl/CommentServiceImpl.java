package cn.iguxue.goblog.service.impl;

import cn.iguxue.goblog.entity.Article;
import cn.iguxue.goblog.entity.Comment;
import cn.iguxue.goblog.mapper.CommentMapper;
import cn.iguxue.goblog.service.CommentService;
import cn.iguxue.goblog.vo.CommentVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Override
    public List<Comment> getAll() {
        return commentMapper.selectList(null);
    }

    @Override
    public List<Comment> getLatest() {
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Comment::getTime).last("LIMIT 3");
        return commentMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    public Integer getAllCount() {
        return commentMapper.selectCount(null);
    }

    @Override
    public IPage<Comment> getByPage(Integer start, Integer size) {
        Page<Comment> page = new Page<>(start,size);
        return commentMapper.selectPage(page,null);
    }

    @Override
    public Comment getById(Long id) {
        return commentMapper.selectById(id);
    }

    @Override
    public List<CommentVO> getCommentList(Long articleId) {
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Comment::getArticleId, articleId);
        List<Comment> comments = commentMapper.selectList(lambdaQueryWrapper);
        List<CommentVO> commentVOS = new ArrayList<>();

        comments.forEach(comment -> {
                List<Comment> children = new ArrayList<>();
                if (comment.getPId() == 0 ) {
                    comments.forEach(child -> {
                        if (child.getPId() != 0 && child.getPId() == comment.getId()) {
                            children.add(child);
                        }
                    });
                    commentVOS.add(new CommentVO(comment,children));
                }
        });
        return commentVOS;
    }

    @Override
    public void save(Comment comment) {
        commentMapper.insert(comment);
    }

    @Override
    public void update(Comment comment) {
        commentMapper.updateById(comment);
    }

    @Override
    public void delete(List<Long> ids) {
        commentMapper.deleteBatchIds(ids);
    }


}
