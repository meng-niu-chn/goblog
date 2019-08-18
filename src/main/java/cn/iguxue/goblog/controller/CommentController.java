package cn.iguxue.goblog.controller;

import cn.iguxue.goblog.entity.Comment;
import cn.iguxue.goblog.service.impl.ArticleServiceImpl;
import cn.iguxue.goblog.service.impl.CommentServiceImpl;
import cn.iguxue.goblog.utils.AddressUtil;
import cn.iguxue.goblog.utils.IPUtil;
import cn.iguxue.goblog.utils.ResultVOUtil;
import cn.iguxue.goblog.vo.ResultVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import jdk.nashorn.internal.ir.CatchNode;
import jdk.nashorn.internal.ir.ReturnNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.sound.midi.Soundbank;
import java.io.Console;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentServiceImpl commentService;

    @Autowired
    ArticleServiceImpl articleService;

    @GetMapping("/getAll")
    public ResultVO getAll(){
        return ResultVOUtil.success(commentService.getAll());
    }

    @GetMapping("/getLatest")
    public ResultVO getLatest() {
        return ResultVOUtil.success(commentService.getLatest());
    }

    @GetMapping("/getAllCount")
    public ResultVO getAllCount() {
        return ResultVOUtil.success(commentService.getAllCount());
    }

    @PostMapping("/getByPage")
    public ResultVO getByPage(@RequestParam(value = "start",defaultValue = "1") Integer start, @RequestParam(value = "size",defaultValue = "5") Integer size) {
        return ResultVOUtil.success(commentService.getByPage(start,size));
    }

    @PostMapping("/getById")
    public ResultVO getById(@RequestParam("id") Long id) {
        return ResultVOUtil.success(commentService.getById(id));
    }

    @GetMapping("/getCommentList")
    public ResultVO getCommentList(@RequestParam("articleId") Long articleId) {
        return ResultVOUtil.success(commentService.getCommentList(articleId));
    }

    @PostMapping("/save")
    public ResultVO save(@RequestBody Comment comment, HttpServletRequest httpServletRequest) {
        System.out.println("comment:  " + comment);
        try {
            String ip = IPUtil.getIpAddr(httpServletRequest);
            comment.setTime(new Date());
            comment.setIp(ip);
            //comment.setAddress(AddressUtil.getAddress(ip));
            String header = httpServletRequest.getHeader("User-Agent");
            UserAgent userAgent = UserAgent.parseUserAgentString(header);
            Browser browser = userAgent.getBrowser();
            OperatingSystem operatingSystem = userAgent.getOperatingSystem();
            comment.setDevice(browser.getName() + "," + operatingSystem.getName());
            commentService.save(comment);
            return ResultVOUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(101,"新增失败");
        }
    }

    @PostMapping("/update")
    public ResultVO update(Comment comment) {
        try {
            commentService.update(comment);
            return ResultVOUtil.success();
        } catch (Exception e) {
            return ResultVOUtil.error(101,"更细腻失败");
        }
    }

    @PostMapping("/delete")
    public ResultVO delete(List<Long> ids) {
        try {
            commentService.delete(ids);
            return ResultVOUtil.success();
        } catch (Exception e) {
            return ResultVOUtil.error(101,"删除失败");
        }
    }

}
