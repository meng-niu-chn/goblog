package cn.iguxue.goblog.controller;

import cn.iguxue.goblog.entity.Category;
import cn.iguxue.goblog.service.impl.ArticleServiceImpl;
import cn.iguxue.goblog.service.impl.CategoryServiceImpl;
import cn.iguxue.goblog.utils.ResultVOUtil;
import cn.iguxue.goblog.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryServiceImpl categoryService;

    @Autowired
    ArticleServiceImpl articleService;

    @GetMapping("/getAll")
    public ResultVO getAll() {
        return ResultVOUtil.success(categoryService.getAll());
    }

    @GetMapping("/getByPage")
    public ResultVO getByPage(@RequestParam(value = "start",defaultValue = "1") Integer start, @RequestParam(value = "size",defaultValue = "5") Integer size) {
        return ResultVOUtil.success(categoryService.getByPage(start,size));
    }

    @GetMapping("/getById")
    public ResultVO getById(@RequestParam("id") Long id) {
        return ResultVOUtil.success(categoryService.getById(id));
    }

    @PostMapping("/save")
    public ResultVO save(@RequestBody Category category) {
        System.out.println(category);
        try {
            categoryService.save(category);
            return ResultVOUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(101,"添加失败");
        }
    }

    @PostMapping("/update")
    public ResultVO update(@RequestBody Category category) {
        try {
            categoryService.update(category);
            return ResultVOUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(101,"更新失败");
        }
    }

    @PostMapping("/delete")
    public ResultVO delete(@RequestBody List<Long> ids) {
        try {
            categoryService.delete(ids);
            return ResultVOUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(101,"删除失败");
        }
    }

}
