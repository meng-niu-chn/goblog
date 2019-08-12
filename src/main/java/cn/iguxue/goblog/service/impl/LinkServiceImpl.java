package cn.iguxue.goblog.service.impl;


import cn.iguxue.goblog.entity.Link;
import cn.iguxue.goblog.enums.ResultEnum;
import cn.iguxue.goblog.exception.GoblogException;
import cn.iguxue.goblog.mapper.LinkMapper;
import cn.iguxue.goblog.service.LinkService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.corba.se.pept.transport.ReaderThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.cert.TrustAnchor;
import java.util.List;

@Service
public class LinkServiceImpl implements LinkService {

    @Autowired
    LinkMapper linkMapper;

    @Override
    public List<Link> getAll() {
        return linkMapper.selectList(null);
    }

    @Override
    public Integer getAllCount() {
        return linkMapper.selectCount(null);
    }

    @Override
    public IPage<Link> getPage(Integer start, Integer size) {
        Page<Link> page = new Page<>(start,size);
        return linkMapper.selectPage(page,null);
    }

    @Override
    public Link getById(Long id) {
        return linkMapper.selectById(id);
    }

    private Boolean exist(Link link) {
        if (link != null) {
            LambdaQueryWrapper<Link> lambdaQueryWrapper = new LambdaQueryWrapper();
            lambdaQueryWrapper.eq(Link::getName,link.getName());
            lambdaQueryWrapper.eq(Link::getUrl,link.getUrl());
            return linkMapper.selectCount(lambdaQueryWrapper) > 0 ? true : false;
        } else {
            return true;
        }
    }

    @Override
    public void save(Link link) {
        if (!exist(link)) {
            linkMapper.insert(link);
        } else {
            throw new GoblogException(ResultEnum.PARAM_ERROR);
        }
    }

    @Override
    public void update(Link link) {
        if (!exist(link)) {
            linkMapper.updateById(link);
        } else {
            throw new GoblogException(ResultEnum.PARAM_ERROR);
        }
    }

    @Override
    public void delete(List<Long> ids) {
        linkMapper.deleteBatchIds(ids);
    }
}
