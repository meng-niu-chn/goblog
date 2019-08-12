package cn.iguxue.goblog.service;

import cn.iguxue.goblog.entity.Category;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface CategoryService {
    void save(Category category);

    Category getByName(String category);

    List<Category> getAll();

    IPage<Category> getByPage(Integer start, Integer size);

    Category getById(Long id);

    void update(Category category);

    void delete(List<Long> ids);

    boolean hasCategory(String category);
}
