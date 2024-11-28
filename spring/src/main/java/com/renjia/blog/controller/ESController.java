package com.renjia.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.renjia.blog.pojo.BlogUser;
import com.renjia.blog.service.IBlogUserService;
import com.renjia.blog.util.JWTUtils;
import com.renjia.blog.util.MailUtil;
import com.renjia.blog.util.RedisUtil;
import com.renjia.blog.util.ResultUtils;
import com.renjia.blog.util.exceptions.OtherException;
import com.renjia.blog.util.other.BaseResponse;
import com.renjia.blog.util.other.ErrorCode;
import com.renjia.blog.util.other.MailMessage;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/es")
public class ESController {
    @Autowired
    @Qualifier("remoteHighLevelClient")
    private RestHighLevelClient remoteHighLevelClient;

    @GetMapping("/list")
    public BaseResponse admin() throws IOException {
        System.out.println("renjia");
        SearchRequest searchRequest = new SearchRequest("blog_article_search");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        MatchQueryBuilder articleTitleQuery = new MatchQueryBuilder("articleTitle", "renjia");
        MatchQueryBuilder articleContentQuery = new MatchQueryBuilder("articleContent", "噼里啪啦一大堆东西落下");
        boolQueryBuilder.should(articleContentQuery).should(articleTitleQuery);
        searchSourceBuilder.query(boolQueryBuilder);
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.numOfFragments(0);
        highlightBuilder.field("articleTitle").preTags("<font color=\"red\"").postTags("</font>");
        highlightBuilder.field("articleContent").preTags("<font color=\"red\"").postTags("</font>");
        searchSourceBuilder.highlighter(highlightBuilder);
        searchRequest.source(searchSourceBuilder);

        SearchResponse search = remoteHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        return  ResultUtils.success(search);
    }


}
