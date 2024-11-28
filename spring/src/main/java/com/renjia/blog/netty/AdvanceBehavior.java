package com.renjia.blog.netty;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.renjia.blog.mq.SearchCannalMq;
import com.renjia.blog.mq.producer.MessageProcessingChain;
import com.renjia.blog.pojo.BlogArticle;
import com.renjia.blog.pojo.BlogArticleContent;
import com.renjia.blog.service.IBlogArticleContentService;
import com.renjia.blog.service.IBlogArticleService;
import com.renjia.blog.util.BeanUtil;
import com.renjia.blog.util.TrieSearcherUtil;
import lombok.Data;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Stream;


@Data
public class AdvanceBehavior implements CommandLineRunner, Serializable {

    @Autowired
    @Qualifier("remoteHighLevelClient")
    private RestHighLevelClient remoteHighLevelClient;
    @Resource
    private IBlogArticleService blogArticleService;
    @Resource
    private IBlogArticleContentService iBlogArticleContentService;

    private ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(3,6,60, TimeUnit.SECONDS,new ArrayBlockingQueue<>(10),new ThreadPoolExecutor.DiscardOldestPolicy());



    @Value("${netty.port}")
    Integer nettyPort;
    @Value("${netty.url}")
    String nettyUrl;
    @Value("${netty.host}")
    private String host = "localhost";

    @Override
    public void run(String... args) throws Exception {
        try {
            new MessageProcessingChain().initChai();
            TrieSearcherUtil trieSearcherUtil = BeanUtil.getBean(TrieSearcherUtil.class);
            Stream.of("猪狗", "小狗", "小猫", "小猪", "垃圾", "狗东西").forEach(trieSearcherUtil::addWord);
            LambdaQueryWrapper<BlogArticle> blogArticleLambdaQueryWrapper = new LambdaQueryWrapper<>();
            blogArticleLambdaQueryWrapper.eq(BlogArticle::getDeleted,0);

            poolExecutor.execute(()->{
                List<BlogArticle> list = blogArticleService.list(blogArticleLambdaQueryWrapper);
                for (BlogArticle blogArticle : list) {
                    System.out.println("数据库数据全量同步更新到es中。。。。");
                    BlogArticleContent blogArticleContent = iBlogArticleContentService.getById(blogArticle.getArticleContentId());
                    try {
                        remoteHighLevelClient.index(SearchCannalMq.returnIndex(blogArticle.getId(),blogArticleContent.getArticleMd(),blogArticle.getArticleTitle(),blogArticle.getArticleContentId(),blogArticle.getArticleLimiter()), RequestOptions.DEFAULT);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            System.out.println("nettyServer starting ...");
            new NettyServer(nettyPort).start(nettyUrl);
        } catch (Exception e) {
            System.out.println("NettyServerError:" + e.getMessage());
        }
    }

}
