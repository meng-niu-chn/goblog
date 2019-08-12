package cn.iguxue.goblog.controller;

import cn.iguxue.goblog.entity.Tag;
import cn.iguxue.goblog.service.TagService;
import cn.iguxue.goblog.utils.ResultVOUtil;
import cn.iguxue.goblog.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    TagService tagService;

    @GetMapping("/getAll")
    public ResultVO getAll() {
        return ResultVOUtil.success(tagService.getAll());
    }

    @GetMapping("/getAllCount")
    public ResultVO getAllCount() {
        return ResultVOUtil.success(tagService.getAllCount());
    }

    @GetMapping("/getByPage")
    public ResultVO getByPage(@RequestParam(value = "start",defaultValue = "1") Integer start, @RequestParam(value = "size",defaultValue = "5") Integer size) {
        return ResultVOUtil.success(tagService.getByPage(start,size));
    }

    @GetMapping("/getById")
    public ResultVO getById(@RequestParam("id") Long id){
        return ResultVOUtil.success(tagService.getById(id));
    }

    @PostMapping("/save")
    public ResultVO save(@RequestBody Tag tag) {
        try {
            tagService.save(tag);
            return ResultVOUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(101,"新增失败");
        }
    }

    @PostMapping("/update")
    public ResultVO update(@RequestBody Tag tag) {
        try {
            tagService.update(tag);
            return ResultVOUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(101,"更新失败");
        }
    }

    @PostMapping("/delete")
    public ResultVO delete(@RequestBody List<Long> ids) {
        try {
            tagService.delete(ids);
            return ResultVOUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(101,"删除失败");
        }

    }

}
