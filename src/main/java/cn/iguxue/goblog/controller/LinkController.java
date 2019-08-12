package cn.iguxue.goblog.controller;

import cn.iguxue.goblog.entity.Link;
import cn.iguxue.goblog.service.impl.LinkServiceImpl;
import cn.iguxue.goblog.utils.ResultVOUtil;
import cn.iguxue.goblog.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.expression.Ids;

import java.util.List;

@RestController
@RequestMapping("/link")
public class LinkController {

    @Autowired
    LinkServiceImpl linkService;

    @GetMapping("/getAll")
    public ResultVO getAll(){
        return ResultVOUtil.success(linkService.getAll());
    }

    @GetMapping("/getAllCount")
    public ResultVO getAllCount() {
        return ResultVOUtil.success(linkService.getAllCount());
    }

    @GetMapping("/getByPage")
    public ResultVO getByPage(@RequestParam(value = "start",defaultValue = "1") Integer start, @RequestParam(value = "size",defaultValue = "5") Integer size) {
        return ResultVOUtil.success(linkService.getPage(start,size));
    }

    @GetMapping("/getById")
    public ResultVO getById(@RequestParam("id") Long id) {
        return ResultVOUtil.success(linkService.getById(id));
    }

    @PostMapping("/save")
    public ResultVO save(@RequestBody Link link) {
        try {
            linkService.save(link);
            return ResultVOUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(101,"心怎那个失败");
        }
    }

    @PostMapping("/update")
    public ResultVO update(@RequestBody Link link) {
        try {
            linkService.update(link);
            return ResultVOUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(101,"更新失败");
        }
    }

    @PostMapping("/delete")
    public ResultVO delete(@RequestBody List<Long> ids) {
        try {
            linkService.delete(ids);
            return ResultVOUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(101,"删除时v哎");
        }
    }
}
