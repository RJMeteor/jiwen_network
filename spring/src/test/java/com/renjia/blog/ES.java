package com.renjia.blog;

import com.renjia.blog.util.RedisUtil;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryShardContext;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.invocation.MatchersBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ES {

    @Autowired
    @Qualifier("remoteHighLevelClient")
    private RestHighLevelClient remoteHighLevelClient;


    @Test
    public void search1() throws IOException {
        System.out.println("renjia");
        remoteHighLevelClient.search(new SearchRequest(),RequestOptions.DEFAULT);
    }

    @Test
    public void search() throws IOException {
        System.out.println("renjia");
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        List<QueryBuilder> queryBuilders = boolQueryBuilder.mustNot();
        MatchQueryBuilder articleTitleQuery = new MatchQueryBuilder("articleTitle", "re");
        MatchQueryBuilder articleContentQuery = new MatchQueryBuilder("articleContent", "今天脑袋好疼！");
        queryBuilders.add(articleTitleQuery);
        queryBuilders.add(articleContentQuery);
        searchSourceBuilder.query(boolQueryBuilder);
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font color=‘red’");
        highlightBuilder.postTags("</font>");
        highlightBuilder.field("articleTitle");
        highlightBuilder.field("articleContent");
        searchSourceBuilder.highlighter(highlightBuilder);
        searchRequest.source(searchSourceBuilder);

        SearchResponse search = remoteHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        System.out.println(search);

    }
}
