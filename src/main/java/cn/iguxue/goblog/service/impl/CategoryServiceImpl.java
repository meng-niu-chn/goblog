package cn.iguxue.goblog.service.impl;

import cn.iguxue.goblog.entity.ArticleCategory;
import cn.iguxue.goblog.entity.Category;
import cn.iguxue.goblog.enums.ResultEnum;
import cn.iguxue.goblog.exception.GoblogException;
import cn.iguxue.goblog.mapper.ArticleCategoryMapper;
import cn.iguxue.goblog.mapper.CategoryMapper;
import cn.iguxue.goblog.service.ArticleCategoryService;
import cn.iguxue.goblog.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    ArticleCategoryService articleCategoryService;

    private Boolean exist(Category category) {
        if (category != null) {
            LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Category::getName,category.getName());
            return categoryMapper.selectCount(lambdaQueryWrapper) > 0 ? true : false;
        } else {
            return true;
        }
    }

    @Override
    public void save(Category category) {
        if (!exist(category)) {
            categoryMapper.insert(category);
        } else {
            throw new GoblogException(ResultEnum.PARAM_ERROR);
        }
    }

    @Override
    public Category getByName(String category) {
        if (!StringUtils.isEmpty(category)) {
            LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Category::getName,category);
            return categoryMapper.selectOne(lambdaQueryWrapper);
        } else {
            throw new GoblogException(ResultEnum.PARAM_ERROR);
        }
    }

    @Override
    public List<Category> getAll() {
        return categoryMapper.selectList(null);
    }

    @Override
    public IPage<Category> getByPage(Integer start, Integer size) {
        Page<Category> page = new Page<>(start,size);
        return categoryMapper.selectPage(page,null);
    }

    @Override
    public Category getById(Long id) {
        return categoryMapper.selectById(id);
    }

    @Override
    public void update(Category category) {
        if (category.getId() != null && !exist(category)) {
            categoryMapper.updateById(category);
        } else {
            throw new GoblogException(ResultEnum.PARAM_ERROR);
        }
    }

    @Override
    public void delete(List<Long> ids) {
        categoryMapper.deleteBatchIds(ids);
        articleCategoryService.deleteByCategoryIds(ids);
    }

    @Override
    public boolean hasCategory(String category) {
        return exist(new Category(category));
    }
}
