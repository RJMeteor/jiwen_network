package com.renjia.blog.mq;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.rabbitmq.client.Channel;
import com.renjia.blog.pojo.BlogArticleContent;
import com.renjia.blog.pojo.BlogLikeBrowse;
import com.renjia.blog.service.IBlogArticleContentService;
import com.sun.javafx.collections.MappingChange;
import groovyjarjarantlr.collections.List;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Component
public class SearchCannalMq {

    @Resource
    private IBlogArticleContentService iBlogArticleContentService;
    @Autowired
    @Qualifier("remoteHighLevelClient")
    private RestHighLevelClient remoteHighLevelClient;

    @RabbitHandler
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "canal-queue", durable = "true", autoDelete = "false"),
            exchange = @Exchange(value = "canal-exchange"),
            key = "sync"), concurrency = "10")
    public void dataSynchronizationToEs(Message message, Channel channel) throws IOException {
        Map map = JSON.parseObject(new String(message.getBody()), Map.class);
        // 变更的数据
        JSONArray o = (JSONArray) map.get("data");
        String table = (String) map.get("table");
        if (!"blog_article".equals(table)) {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            return;
        }
        // ddl类型
        String type =(String) map.get("type");
        if ("INSERT".equals(type)||"UPDATE".equals(type)){
            for (Object object :  o) {
                Long id = ((JSONObject) object).getLong("id");
                Integer articleLimiter = ((JSONObject) object).getInteger("article_limiter");
                String articleTitle = ((JSONObject) object).getString("article_title");
                Long articleContentId = ((JSONObject) object).getLong("article_content_id");
                BlogArticleContent blogArticleContent = iBlogArticleContentService.getById(articleContentId);
                String articleMd = blogArticleContent.getArticleMd();
                remoteHighLevelClient.index(this.returnIndex(id,articleMd,articleTitle,articleContentId,articleLimiter), RequestOptions.DEFAULT);
            }
        } else if ("DELETE".equals(type)) {
            for (Object object :  o) {
                Long id = ((JSONObject) object).getLong("id");
                DeleteRequest deleteRequest = new DeleteRequest("blog_article_search");
                deleteRequest.id(id.toString());
                remoteHighLevelClient.delete(deleteRequest,RequestOptions.DEFAULT);
            }
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        System.out.println(message);
    }



    public static IndexRequest returnIndex(Long articleId,String articleContent,String articleTitle,Long contentId,Integer limiter){
       return new IndexRequest("blog_article_search").id(articleId.toString())
                .source(XContentType.JSON,"id", articleId, "articleContent", articleContent, "articleTitle", articleTitle, "contentId", contentId, "limiter",limiter);

    }

}
