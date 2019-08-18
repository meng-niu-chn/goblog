package cn.iguxue.goblog.controller;

import cn.iguxue.goblog.entity.Article;
import cn.iguxue.goblog.service.SiteService;
import cn.iguxue.goblog.service.impl.ArticleServiceImpl;
import cn.iguxue.goblog.service.impl.CommentServiceImpl;
import cn.iguxue.goblog.service.impl.SiteServiceImpl;
import cn.iguxue.goblog.service.impl.UserServiceImpl;
import cn.iguxue.goblog.utils.ResultVOUtil;
import cn.iguxue.goblog.vo.ResultVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/site")
public class SiteController {

    @Autowired
    SiteServiceImpl siteService;

    @Autowired
    CommentServiceImpl commentService;

    @Autowired
    UserServiceImpl userService;

    @GetMapping(value = "/getArticlesByPage")
    public ResultVO getArticleByPage(@RequestParam(value = "start", defaultValue = "1") Integer start,
                                     @RequestParam(value = "size", defaultValue = "5") Integer size,
                                     @RequestParam(value = "categoryId",defaultValue = "") Long categoryId) {
        IPage<Article> page = siteService.getArticlesByPage(start,size,categoryId);
        return ResultVOUtil.success(page);
    }

    @GetMapping("/getLinks")
    public ResultVO getLinks() {
        return ResultVOUtil.success(siteService.getAllLinks());
    }

    @GetMapping("/getTags")
    public ResultVO getTags() {
        return ResultVOUtil.success(siteService.getAllTags());
    }

    @GetMapping("/getCategorys")
    public ResultVO getCategorys() {
        return ResultVOUtil.success(siteService.getCategorys());
    }

    @GetMapping("/getArticleById")
    public ResultVO getArticleById(@RequestParam("id") Long id) {
        return ResultVOUtil.success(siteService.getArticleById(id));
    }

    @PostMapping("/getCommentList")
    public ResultVO getCommentList(@RequestParam("articleId") Long articleId) {
        return ResultVOUtil.success(commentService.getCommentList(articleId));
    }

    @GetMapping("/getSetting")
    public ResultVO getSetting() {
        return ResultVOUtil.success(userService.getSetting());
    }
}
