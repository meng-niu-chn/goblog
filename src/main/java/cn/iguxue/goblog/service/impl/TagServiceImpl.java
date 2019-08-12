package cn.iguxue.goblog.service.impl;

import cn.iguxue.goblog.entity.Link;
import cn.iguxue.goblog.entity.Tag;
import cn.iguxue.goblog.enums.ResultEnum;
import cn.iguxue.goblog.exception.GoblogException;
import cn.iguxue.goblog.mapper.LinkMapper;
import cn.iguxue.goblog.mapper.TagMapper;
import cn.iguxue.goblog.service.ArticleTagService;
import cn.iguxue.goblog.service.TagService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagMapper tagMapper;

    @Autowired
    ArticleTagServiceImpl articleTagService;

    private Boolean exist(Tag tag) {
        if (tag != null) {
            LambdaQueryWrapper<Tag> lambdaQueryWrapper = new LambdaQueryWrapper();
            lambdaQueryWrapper.eq(Tag::getName, tag.getName());
            return tagMapper.selectCount(lambdaQueryWrapper) > 0 ? true : false;
        } else {
            throw new GoblogException(ResultEnum.PARAM_ERROR);
        }
    }

    @Override
    public void save(Tag tag) {
        if (!exist(tag)) {
            tagMapper.insert(tag);
        } else {
            throw new GoblogException(ResultEnum.PARAM_ERROR);
        }
    }

    @Override
    public Tag getByName(String name) {
        if (!StringUtils.isEmpty(name)) {
            LambdaQueryWrapper<Tag> lambdaQueryWrapper = new LambdaQueryWrapper();
            lambdaQueryWrapper.eq(Tag::getName, name);
            return tagMapper.selectOne(lambdaQueryWrapper);
        } else {
            throw new GoblogException(ResultEnum.PARAM_ERROR);
        }
    }

    @Override
    public List<Tag> getAll() {
        return tagMapper.selectList(null);
    }

    @Override
    public Integer getAllCount() {
        return tagMapper.selectCount(null);
    }

    @Override
    public IPage<Tag> getByPage(Integer start, Integer size) {
        Page<Tag> page = new Page<>(start,size);
        return tagMapper.selectPage(page,null);
    }

    @Override
    public Tag getById(Long id) {
        return tagMapper.selectById(id);
    }

    @Override
    public void update(Tag tag) {
        if (!exist(tag)) {
            tagMapper.updateById(tag);
        } else {
            throw new GoblogException(ResultEnum.PARAM_ERROR);
        }
    }

    @Override
    public void delete(List<Long> ids) {
        ids.forEach(id -> {
            articleTagService.deleteByTagId(id);
        });

        tagMapper.deleteBatchIds(ids);
    }
}
