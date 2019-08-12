package cn.iguxue.goblog.service.impl;

import cn.iguxue.goblog.entity.Article;
import cn.iguxue.goblog.entity.ArticleCategory;
import cn.iguxue.goblog.entity.ArticleTag;
import cn.iguxue.goblog.entity.Tag;
import cn.iguxue.goblog.enums.ResultEnum;
import cn.iguxue.goblog.exception.GoblogException;
import cn.iguxue.goblog.mapper.ArticleTagMapper;
import cn.iguxue.goblog.service.ArticleService;
import cn.iguxue.goblog.service.ArticleTagService;
import cn.iguxue.goblog.service.TagService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.SelectCount;
import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleTagServiceImpl implements ArticleTagService {

    @Autowired
    ArticleTagMapper articleTagMapper;

    @Autowired
    TagService tagService;

    @Override
    public List<Tag> getByArticleId(Long articleId) {
        if (articleId != 0 && !articleId.equals(null)) {
            return articleTagMapper.getByArticleId(articleId);
        } else {
            throw new GoblogException(ResultEnum.PARAM_ERROR);
        }
    }

    @Override
    public List<ArticleTag> getByTagId(Long tagId) {
        LambdaQueryWrapper<ArticleTag> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(ArticleTag::getTagId, tagId);
        return articleTagMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    public void deleteByArticleId(Long id) {
        LambdaQueryWrapper<ArticleTag> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(ArticleTag::getArticleId, id);
        articleTagMapper.delete(lambdaQueryWrapper);
    }

    @Override
    public void deleteByTagId(Long id) {
        LambdaQueryWrapper<ArticleTag> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(ArticleTag::getTagId, id);
        articleTagMapper.delete(lambdaQueryWrapper);
    }

    @Override
    public List<Article> getByTagName(String tag) {
        return articleTagMapper.getByTagName(tag);
    }

    private Boolean exist(ArticleTag articleTag) {
        if (articleTag != null) {
            LambdaQueryWrapper<ArticleTag> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(ArticleTag::getArticleId, articleTag.getArticleId())
                    .eq(ArticleTag::getTagId, articleTag.getTagId());
            return articleTagMapper.selectCount(lambdaQueryWrapper) > 0 ? true : false;
        } else {
            throw new GoblogException(ResultEnum.PARAM_ERROR);
        }

    }

    @Override
    public void save(ArticleTag articleTag) {
        if (!exist(articleTag)) {
            articleTagMapper.insert(articleTag);
        }
    }

    @Override
    public void deleteByArticleIds(List<Long> ids) {
        LambdaQueryWrapper<ArticleTag> lambdaQueryWrapper = new LambdaQueryWrapper();
        ids.forEach(id -> {
            lambdaQueryWrapper.or().eq(ArticleTag::getArticleId, id);
        });
        List<ArticleTag> articleTagList = articleTagMapper.selectList(lambdaQueryWrapper);
        List<Long> idss = new ArrayList<>();
        articleTagList.forEach(articleTag -> {
            idss.add(articleTag.getId());
        });
        if (idss.size() > 0) {
            articleTagMapper.deleteBatchIds(idss);
        }
    }
}
