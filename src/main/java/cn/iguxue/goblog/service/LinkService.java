package cn.iguxue.goblog.service;

import cn.iguxue.goblog.entity.Link;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface LinkService {
    List<Link> getAll();

    Integer getAllCount();

    IPage<Link> getPage(Integer start, Integer size);

    Link getById(Long id);

    void save(Link link);

    void update(Link link);

    void delete(List<Long> ids);
}
