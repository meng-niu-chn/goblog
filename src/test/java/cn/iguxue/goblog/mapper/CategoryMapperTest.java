package cn.iguxue.goblog.mapper;

import cn.iguxue.goblog.entity.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryMapperTest {

    @Autowired
    CategoryMapper categoryMapper;

    @Test
    public void save() {
        Category category = new Category("lala");
        categoryMapper.insert(category);
    }

}