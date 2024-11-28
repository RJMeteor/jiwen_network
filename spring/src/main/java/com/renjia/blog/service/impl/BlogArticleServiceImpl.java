package com.renjia.blog.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.renjia.blog.config.cache.DeleteRedisCacheAnnotation;
import com.renjia.blog.config.cache.RedisCacheAnnotation;
import com.renjia.blog.mapper.BlogArticleContentMapper;
import com.renjia.blog.pojo.*;
import com.renjia.blog.mapper.BlogArticleMapper;
import com.renjia.blog.service.IBlogArticleLableClassService;
import com.renjia.blog.service.IBlogArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.renjia.blog.util.OSSCloudClient;
import com.renjia.blog.util.RedisUtil;
import com.renjia.blog.util.TrieSearcherUtil;
import com.renjia.blog.util.exceptions.EmptyArticleException;
import com.renjia.blog.util.exceptions.OtherException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 文章 服务实现类
 * </p>
 *
 * @author 作者
 * @since 2024-03-02
 */
@Service
public class BlogArticleServiceImpl extends ServiceImpl<BlogArticleMapper, BlogArticle> implements IBlogArticleService {
    @Resource
    private BlogArticleContentMapper blogArticleContentMapper;
    @Resource
    private BlogArticleMapper blogArticleMapper;
    @Resource
    private IBlogArticleLableClassService iBlogArticleLableClassService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private TrieSearcherUtil trieSearcherUtil;

    @Autowired
    @Qualifier("remoteHighLevelClient")
    private RestHighLevelClient remoteHighLevelClient;


    @Override
    public String uploadImg(MultipartFile file) throws Exception {
        OSSCloudClient client = OSSCloudClient.getInstance();
        String filePath = client.uploadFile(file.getOriginalFilename(), file.getInputStream());
        if (!ObjectUtils.isEmpty(filePath)) {
            System.out.println(filePath);
            return filePath;
        }
        return null;
    }

    @Override
    public PageInfo<BlogArticle> articleListByNameAndTitleAndLableId(String userName, String articleTitle, Long lableId, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<BlogArticle> blogArticlea = blogArticleMapper.articleListByNameAndTitleAndLableId(userName, articleTitle, lableId);
        PageInfo<BlogArticle> pageInfo = new PageInfo<>(blogArticlea);
        return pageInfo;
    }

    @Override
    @DeleteRedisCacheAnnotation(cacheName = RedisCacheAnnotation.Type.BlogArticle, key = "#articleId")
    public Integer disabledArticle(Long articleId) {
        LambdaUpdateWrapper<BlogArticle> articleUpdate = new LambdaUpdateWrapper<>();
        articleUpdate.eq(BlogArticle::getId, articleId).set(BlogArticle::getDeleted, 1);
        boolean update = this.update(articleUpdate);
        return update ? 1 : 0;
    }

    @Override
    public Integer disabledArticleIds(List<Long> articleIds) {
        LambdaUpdateWrapper<BlogArticle> articleUpdate = new LambdaUpdateWrapper<>();
        articleUpdate.in(BlogArticle::getId, articleIds).set(BlogArticle::getDeleted, 1);
        boolean update = this.update(articleUpdate);
        return update ? 1 : 0;
    }

    @Override
    public Integer cancelDisabledArticle(Long articleId) {
        LambdaUpdateWrapper<BlogArticle> articleUpdate = new LambdaUpdateWrapper<>();
        articleUpdate.eq(BlogArticle::getId, articleId).set(BlogArticle::getDeleted, 0);
        boolean update = this.update(articleUpdate);
        return update ? 1 : 0;
    }

    @Override
    public Integer cancelDisabledArticleIds(List<Long> articleIds) {
        LambdaUpdateWrapper<BlogArticle> articleUpdate = new LambdaUpdateWrapper<>();
        articleUpdate.in(BlogArticle::getId, articleIds).set(BlogArticle::getDeleted, 0);
        boolean update = this.update(articleUpdate);
        return update ? 1 : 0;
    }

    @Override
    public Integer updateArticle(BlogArticle blogArticle, ArrayList<BlogArticleLableClass> blogArticleLableClass) {
        BlogArticleContent articleContent = blogArticle.getArticleContent();
        blogArticleContentMapper.inserArticleContent(articleContent);
        blogArticle.setArticleContentId(articleContent.getId());
        blogArticle.setId(blogArticle.getId());
        this.updateById(blogArticle);
        int delete = iBlogArticleLableClassService.getBaseMapper().delete(new LambdaQueryWrapper<BlogArticleLableClass>()
                .eq(BlogArticleLableClass::getArticleId, blogArticle.getId()));
        if (delete > 0) {
            return iBlogArticleLableClassService.saveBatch(blogArticleLableClass) ? 1 : 0;
        }
        return null;
    }

    @Override
    public Integer inserArticle(BlogArticle blogArticle, ArrayList<BlogArticleLableClass> blogArticleLableClass) {
        BlogArticleContent articleContent = blogArticle.getArticleContent();
        String replace = trieSearcherUtil.replace(articleContent.getArticleMd(), "****");
        articleContent.setArticleMd(replace);
        Integer ex = transactionTemplate.execute((transactionStatus) ->
                Optional.ofNullable(blogArticle).flatMap(article -> {
                    blogArticleContentMapper.inserArticleContent(articleContent);
                    return Optional.ofNullable(article).filter(article1 -> !ObjectUtils.isEmpty(article1.getArticleContent().getId()));
                }).flatMap(article -> {
                    article.setArticleContentId(articleContent.getId());
                    Integer integer = blogArticleMapper.inserArticle(article);
                    for (BlogArticleLableClass articleLableClass : blogArticleLableClass) {
                        articleLableClass.setArticleId(blogArticle.getId());
                    }
                    return Optional.ofNullable(integer);
                }).filter(se -> {
                    if (!ObjectUtils.isEmpty(blogArticleLableClass)) {
                        redisTemplate.boundZSetOps(RedisUtil.HOTUSER).incrementScore(blogArticle.getUserId(), 1);
                        return iBlogArticleLableClassService.saveBatch(blogArticleLableClass);
                    }
                    return false;
                }).orElseThrow(() -> {
                    redisTemplate.boundZSetOps(RedisUtil.HOTUSER).incrementScore(blogArticle.getUserId(), -1);
                    return new OtherException("提交失败");
                }));
        return ex;
    }

    @Override
    public Integer deleteArticleByIds(List<Integer> articleIds) {
        List<BlogArticle> blogArticles = this.listByIds(articleIds);
        for (BlogArticle blogArticle : blogArticles) {
            redisTemplate.boundZSetOps(RedisUtil.HOTUSER).incrementScore(blogArticle.getUserId(), -1);
        }
        int i = this.getBaseMapper().deleteBatchIds(articleIds);
        return i;
    }

    @Override
    public PageInfo<BlogArticle> articleByUserIdAndName(Long userId, Integer isPrivate, Integer limiter, String name, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<BlogArticle> blogArticles = blogArticleMapper.articleByUserIdAndName(userId, isPrivate, limiter, name);
        PageInfo<BlogArticle> pageInfo = new PageInfo<>(blogArticles);
        return pageInfo;
    }

    private SearchResponse search(String name, Integer page, Integer size) throws IOException {
        SearchRequest searchRequest = new SearchRequest("blog_article_search");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        MatchQueryBuilder articleTitleQuery = new MatchQueryBuilder("articleTitle", name);
        MatchQueryBuilder articleContentQuery = new MatchQueryBuilder("articleContent", name);
        boolQueryBuilder.should(articleContentQuery);
        boolQueryBuilder.should(articleTitleQuery);
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.from(page - 1).size(size);
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.numOfFragments(0);
        highlightBuilder.preTags("<span style='color:red !important;'>");
        highlightBuilder.postTags("</span>");
        highlightBuilder.field("articleTitle");
        highlightBuilder.field("articleContent");
        searchSourceBuilder.highlighter(highlightBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = remoteHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        return search;
    }

    @Override
    public PageInfo<BlogArticle> articlesByNameAndLabelAndUserId(String name, Integer limiter, Long userId, Integer label, Integer privacy, Integer page, Integer size) throws IOException {
        PageHelper.startPage(page, size);
        if (StringUtils.isEmpty(name)) {
            List<BlogArticle> blogArticles = blogArticleMapper.articlesByNameAndLabelAndUserId(name, label, userId, limiter, privacy);
            PageInfo<BlogArticle> pageInfo = new PageInfo<>(blogArticles);
            return pageInfo;
        }
        SearchResponse search = search(name, page, size);
        SearchHits hits = search.getHits();
        long value = hits.getTotalHits().value;
        SearchHit[] hits1 = hits.getHits();
        ArrayList<Long> longs = new ArrayList<>();
        Map<Long, ArrayList<String>> map = new HashMap<>();
        for (SearchHit documentFields : hits1) {
            ArrayList<String> contents = new ArrayList<>();
            HighlightField articleTitle = documentFields.getHighlightFields().get("articleTitle");
            HighlightField articleContent = documentFields.getHighlightFields().get("articleContent");
            if (articleTitle != null) contents.add(articleTitle.getFragments()[0].string());
            else contents.add(null);
            if (articleContent != null) contents.add(articleContent.getFragments()[0].string());
            else contents.add(null);
            long id = Long.parseLong(documentFields.getId());
            map.put(id, contents);
        }
        List<Long> ids = map.keySet().stream().collect(Collectors.toList());
        List<BlogArticle> blogArticles = blogArticleMapper.articlesByIdsAndLabelAndUserId(ids, label, userId, limiter, privacy);
        for (BlogArticle blogArticle : blogArticles) {
            String title = map.get(blogArticle.getArticleContent().getArticleId()).get(0);
            String content = map.get(blogArticle.getArticleContent().getArticleId()).get(1);
            if (content !=null) {
                blogArticle.getArticleContent().setArticleMd(content);
            }
            if (title!=null){
                blogArticle.setArticleTitle(title);
            }
        }

        PageInfo<BlogArticle> pageInfo = new PageInfo<>(blogArticles);
        return pageInfo;
    }

    @RedisCacheAnnotation(key = "'articles-'+#limiter+'-'+#page+'-'+#size", conditions = "(#name=='')&&(#limiter==0)&&(#label.equals(0))&&(#privacy==0)")
    @Override
    public PageInfo<BlogArticle> articlesByNameAndLabel(String name, Integer limiter, Integer label, Integer privacy, Integer page, Integer size) throws IOException {
        PageHelper.startPage(page, size);
        if (StringUtils.isEmpty(name)) {
            List<BlogArticle> blogArticles = blogArticleMapper.articlesByNameAndLabel(name, label, limiter, privacy);
            PageInfo<BlogArticle> pageInfo = new PageInfo<>(blogArticles);
            return pageInfo;
        }
        SearchResponse search = search(name, page, size);
        SearchHits hits = search.getHits();
        long value = hits.getTotalHits().value;
        SearchHit[] hits1 = hits.getHits();
        ArrayList<Long> longs = new ArrayList<>();
        Map<Long, ArrayList<String>> map = new HashMap<>();
        for (SearchHit documentFields : hits1) {
            ArrayList<String> contents = new ArrayList<>();
            HighlightField articleTitle = documentFields.getHighlightFields().get("articleTitle");
            HighlightField articleContent = documentFields.getHighlightFields().get("articleContent");
            if (articleTitle != null) contents.add(articleTitle.getFragments()[0].string());
            else contents.add(null);
            if (articleContent != null) contents.add(articleContent.getFragments()[0].string());
            else contents.add(null);
            long id = Long.parseLong(documentFields.getId());
            map.put(id, contents);
        }
        List<Long> ids = map.keySet().stream().collect(Collectors.toList());
        List<BlogArticle> blogArticles = blogArticleMapper.articlesByIdAndLabel(ids, label, limiter, privacy);
        for (BlogArticle blogArticle : blogArticles) {
            String title = map.get(blogArticle.getArticleContent().getArticleId()).get(0);
            String content = map.get(blogArticle.getArticleContent().getArticleId()).get(1);
            if (content !=null) {
                blogArticle.getArticleContent().setArticleMd(content);
            }
            if (title!=null){
                blogArticle.setArticleTitle(title);
            }
        }

        PageInfo<BlogArticle> pageInfo = new PageInfo<>(blogArticles);
        return pageInfo;
    }

    @Override
    public BlogArticle getArticlesByUserIdAndArticlesId(Long userId, Long articlesId) throws EmptyArticleException {
        BlogArticle article = blogArticleMapper.getArticlesByUserIdAndArticlesId(userId, articlesId);
        if (article == null) {
            HashMap<String, List<Long>> o = (HashMap<String, List<Long>>) redisTemplate.opsForValue().get(RedisCacheAnnotation.Type.BlogArticle.getType());
            q:
            for (String s : o.keySet()) {
                for (Long aLong : o.get(s)) {
                    if (aLong.equals(articlesId)) {
                        redisTemplate.delete(s);
                        break q;
                    }
                }
            }
            throw new EmptyArticleException();
        }
        return article;
    }
}
