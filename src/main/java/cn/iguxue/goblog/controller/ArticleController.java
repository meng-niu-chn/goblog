package cn.iguxue.goblog.controller;

import cn.iguxue.goblog.entity.Article;
import cn.iguxue.goblog.entity.Tag;
import cn.iguxue.goblog.enums.ResultEnum;
import cn.iguxue.goblog.exception.GoblogException;
import cn.iguxue.goblog.service.ArticleCategoryService;
import cn.iguxue.goblog.service.ArticleService;
import cn.iguxue.goblog.service.ArticleTagService;
import cn.iguxue.goblog.service.CategoryService;
import cn.iguxue.goblog.service.impl.ArticleCategoryServiceImpl;
import cn.iguxue.goblog.service.impl.ArticleServiceImpl;
import cn.iguxue.goblog.utils.ResultVOUtil;
import cn.iguxue.goblog.vo.ResultVO;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleServiceImpl articleService;

    @Autowired
    private ArticleCategoryServiceImpl articleCategoryService;

    @Autowired
    private ArticleTagService articleTagService;

    @GetMapping("/getAll")
    public ResultVO getAll() {
        return ResultVOUtil.success(articleService.getAll());
    }

    @GetMapping("/getLatest")
    public ResultVO getLatest() {
        return ResultVOUtil.success(articleService.getLatest());
    }

    @GetMapping("/getAllCount")
    public ResultVO getAllCount() {
        return ResultVOUtil.success(articleService.getAllCount());
    }

    @GetMapping("/getById")
    public ResultVO getById(@RequestParam("id") Long id) {
        Article article = articleService.getById(id);
        if (article != null && article.getId() != 0 ) {
            List<String> tags = new ArrayList<>();
            List<Tag> tagList = articleTagService.getByArticleId(article.getId());
            tagList.forEach(t -> {
                tags.add(t.getName());
            });
            article.setTags(JSON.toJSONString(tags));
            article.setCategory(articleCategoryService.getCategoryByArticle(id).getName());
            return ResultVOUtil.success(article);
        } else {
            return ResultVOUtil.error(101,"errot");
        }
    }

    @GetMapping(value = "/getByPage")
    public ResultVO getByPage(@RequestParam(value = "start", defaultValue = "1") Integer start, @RequestParam(value = "size", defaultValue = "5") Integer size,@RequestParam(value = "titles",defaultValue = "") String titles) {
        IPage<Article> page = articleService.getByPage(start,size,titles);
        return ResultVOUtil.success(page);
    }

    @GetMapping(value = "/getTags")
    public ResultVO getTags(@RequestParam("id") Long id) {
        List<String> list = new ArrayList<>();
        List<Tag> tagList = articleTagService.getByArticleId(id);
        for (Tag t : tagList) {
            list.add(t.getName());
        }
        return ResultVOUtil.success(list);
    }

    @GetMapping(value = "/getArchives")
    public ResultVO getArchives() {
        return ResultVOUtil.success(articleService.getArchives());
    }

    @PostMapping(value = "/save")
    public ResultVO save(@RequestBody Article article) {
        System.out.println("article:" + article);
            articleService.save(article);
            return ResultVOUtil.success();
    }

    @PostMapping(value = "/update")
    public ResultVO update(@RequestBody Article article) {
            articleService.update(article);
            return ResultVOUtil.success();
    }

    @PostMapping(value = "/delete")
    public ResultVO delete(@RequestBody List<Long> ids) {
        System.out.println(ids);
        System.out.println("delete");
            articleService.delete(ids);
            return ResultVOUtil.success();
    }

    @PostMapping("uploadAvatar")
    public ResultVO uploadAvatar(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResultVOUtil.error(101,"上传失败");
        }

        try {
            //获取文件在服务器的储存位置
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            File filePath = new File(path.getAbsolutePath(), "static/imgs/article/");
            if (!filePath.exists() && !filePath.isDirectory()) {
                filePath.mkdir();
            }

            String fileName = RandomUtils.nextInt(1,100) + file.getOriginalFilename();
            System.out.println("fileName: " + fileName);

            File dest = new File(filePath, fileName);
            file.transferTo(dest);

            Map map = new HashMap<>();
            map.put("name", fileName);
            map.put("url", "http://localhost:8899/imgs/article/" + fileName);

            return ResultVOUtil.success(map);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(101,"SHA");
        }
    }

    @PostMapping("deleteAvatar")
    public ResultVO deleteAvatar(@RequestParam("filename") String filename) {
        if (filename.isEmpty()) {
            return ResultVOUtil.error(101,"删除失败");
        }
        try {
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            File filePath = new File(path.getAbsolutePath(), "static/imgs/article/"+filename);
            if (!filePath.exists()) {
                return ResultVOUtil.error(101,"删除失败，文件不存在");
            } else {
                if (filePath.isFile()&&filePath.delete()) {
                    return ResultVOUtil.success();
                }else {
                    return ResultVOUtil.error(101,"删除失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(101,"删除失败");
        }
    }


}
