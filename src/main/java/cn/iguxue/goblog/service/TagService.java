package cn.iguxue.goblog.service;

import cn.iguxue.goblog.entity.Link;
import cn.iguxue.goblog.entity.Tag;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface TagService {
    void save(Tag tag);

    Tag getByName(String name);

    List<Tag> getAll();

    Integer getAllCount();

    IPage<Tag> getByPage(Integer start, Integer size);

    Tag getById(Long id);

    void update(Tag tag);

    void delete(List<Long> ids);
}
