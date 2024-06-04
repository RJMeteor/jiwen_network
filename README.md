## 🍉项目技术点

后端：`Maven`、`Spring Boot` 、`Spring MVC `、 `Mybatis Plus` 、`MySQL`、  `OSS`、 `JWT`、 `Netty`、 `RabbitMQ`、 `Redis`、 `Nginx`、 `Git`、 `Linux`、 `Docker`、 `Gitlab CI/CD`

前端：`Vite`、`Vue3`、`Naive UI`、`Vue Router`、`Axios`、`Pinia`、`IndexDB`、`Vditor`、`Sass`、`JSX`、`TypeScript`、`marked`、`highlight.js`

## 🍒项目描述

在项目中我担任前端和后端开发，**从0到1完成项目**。该项目分为首页、文章创造、消息中心、消息通知设置、后台通知、个人主页、话题广场、私信、关注、评论、个人信息等模块，旨在打造一个为用户提供一个展示个人技术文章、项目经验和分享学习心得的个人博客网站平台，同时通过互动促进读者与博主之间的交流和沟通。

项目的设计模式包括**责任链模式、单例模式、模板方法模式**等设计模式，使用**AOP技术和SpEL解析器**自定义数据缓存逻辑、使用**Netty技术搭建WebSocket服务端**，定时**心跳检测**，使用**模板方法设计模式和函数式接口编程**自定义RabbitMQ消费失败**重试与补偿、降低消息幂等性**等逻辑等。

## 🍆说明

- `fore`目录：**前端前台代码**，`fore\src\config`下的`.env`文件下的配置包括Token请求头名、后端请求接口、Websocket后端请求接口。
- `fore_back`目录：**前端后台代码**，同上`fore`目录介绍。
- `spring`目录：**后端代码**，`application.yml`配置文件关联的配置在`src\main\resources\config`下，`blog.sql`SQL文件在`spring根目录`下。
- 项目中设计nginx配置，如下：

~~~nginx
user  nginx;
worker_processes  auto;
error_log  /var/log/nginx/error.log notice;
pid        /var/run/nginx.pid;
events {
    worker_connections  1024;
}
http {
    include       /etc/nginx/mime.types;
    default_type  application/octet-stream;
    log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
                      '$status $body_bytes_sent "$http_referer" '
                      '"$http_user_agent" "$http_x_forwarded_for"';
    access_log  /var/log/nginx/access.log  main;
    sendfile        on;
    keepalive_timeout  65;
    proxy_buffer_size 128k; 
    proxy_buffers 32 128k; 
    proxy_busy_buffers_size 128k;
    gzip  on;
    server {
       listen       80;
       listen  [::]:80;
       server_name  localhost;
       location / {
           root   /usr/share/nginx/html/blog;
           index  index.html index.htm;	   
       }
       location /request {
            proxy_pass http://host:9002/; # 后端服务器地址    
            proxy_set_header Host $host;
            proxy_set_header X-Real-IP $remote_addr;
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
            proxy_set_header X-Forwarded-Proto $scheme;
        }
       location /request/websocket {
            proxy_pass http://host:9003/websocket; # Websocket后端服务器地址
            proxy_http_version 1.1;
            proxy_set_header Upgrade $http_upgrade;
            proxy_set_header Connection "upgrade";
            proxy_set_header Host $host;
            proxy_set_header X-Real-IP $remote_addr;
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
            proxy_set_header X-Forwarded-Proto $scheme;
        }
    }
}
~~~

## 🍇项目概述

> 🍩前台系统的用户有两种身份为**游客和已登录**的用户

☕**游客：**

- `系统首页`：在该页面上，可以浏览其他用户发表的文章；可以通过输入文章名、选择文章类别搜索想要看的文章，点击某文章跳转下一个页面；可以查看热门文章和热门用户。

- `话题广场`：在该页面上可以浏览用户发布的全部话题内容分享，已登录的用户可以进行话题的评论、点赞，可点击用户的头像跳转下一个页面。

- `文章内容页面`：该页面进行文章内容的阅读，查看博主的个人信息（个人简介、关注数、粉丝数），也可查看文章的浏览数、评论数、收藏数、文章目录，已登录的用户用户可点赞、收藏和评论该文章，可点击用户的头像跳转下一个页面。

- `个人主页`；页面上可是查看用户全部公开发表的文章和话题、用户个人收藏的内容等，已注册用户可删除、修改。

- `注册`：游客点击页面的注册按钮进行系统的注册、其中涉及的字段包括QQ邮箱、QQ邮箱验证码、用户名、密码等就可以完成注册。

- `登录`：该页面输入用户名或QQ邮箱、密码进行登录。登录成功就可以使用和浏览系统提供全部的功和内容。

🧃**已登录用户：**处理游客能使用的功能和内容外，还有其他功能：

- `个人信息`：展示用户的的个人信息，对于刚注册的用户展示默认头像、也可对头像、电话号码、QQ邮箱、QQ邮箱验证码、用户名、性别、个人简介等信息进行修改。

- `文章创作`：该界面用户撰写文章内容的，也可查看保存在本地草稿箱的文章进行再次编辑保存发布，点击下一步跳转下一个界面。

- `文章创作`：该页面，上传文章的需填写文章名、文章标签、文章私密等字段进行上传。

- `消息通知设置`：展示用户的通知权限设置，用户可关闭打开消息通知权限，通知针对聊天、评论、收藏、关注、点赞等消息进行第一时间进行通知。通知的方式选择服务器WebSocket通信给前台页面进行全局弹出框提示消息。

- `消息中心`：该包括收藏、评论、聊天、点赞、关注消息的展示。可进行删除、对于评论、聊天可进行回复。

- `关注/粉丝`：展示关注的用户和粉丝用户，可进行取关、关注、私信用户。

- `反馈消息`；展示用户反馈内容受理清单、用户也可向系统管理反馈信息、系统管理接受反馈内容进行受理其意见。

- `后台通知`：展示系统管理员下发的通知。

  ### 🥑项目结构图如下：

  ![](https://renjia-oss.oss-cn-chengdu.aliyuncs.com/github/jiwen_network/%E6%96%B0%E5%BB%BA%E6%96%87%E4%BB%B6%E5%A4%B9/image-20240604145808771.png)

![image-20240604150857716](https://renjia-oss.oss-cn-chengdu.aliyuncs.com/github/jiwen_network/%E6%96%B0%E5%BB%BA%E6%96%87%E4%BB%B6%E5%A4%B9/image-20240604150857716.png)

## 🥦项目最终实现图

![image-20240604150223203](https://renjia-oss.oss-cn-chengdu.aliyuncs.com/github/jiwen_network/%E6%96%B0%E5%BB%BA%E6%96%87%E4%BB%B6%E5%A4%B9/image-20240604150223203.png)

![image-20240604150240529](https://renjia-oss.oss-cn-chengdu.aliyuncs.com/github/jiwen_network/%E6%96%B0%E5%BB%BA%E6%96%87%E4%BB%B6%E5%A4%B9/image-20240604150240529.png)

![image-20240604150310056](https://renjia-oss.oss-cn-chengdu.aliyuncs.com/github/jiwen_network/%E6%96%B0%E5%BB%BA%E6%96%87%E4%BB%B6%E5%A4%B9/image-20240604150310056.png)

![image-20240604150334963](https://renjia-oss.oss-cn-chengdu.aliyuncs.com/github/jiwen_network/%E6%96%B0%E5%BB%BA%E6%96%87%E4%BB%B6%E5%A4%B9/image-20240604150334963.png)

![image-20240604150418514](https://renjia-oss.oss-cn-chengdu.aliyuncs.com/github/jiwen_network/%E6%96%B0%E5%BB%BA%E6%96%87%E4%BB%B6%E5%A4%B9/image-20240604150403372.png)

![image-20240604150519355](https://renjia-oss.oss-cn-chengdu.aliyuncs.com/github/jiwen_network/%E6%96%B0%E5%BB%BA%E6%96%87%E4%BB%B6%E5%A4%B9/image-20240604150519355.png)

![image-20240604150528366](https://renjia-oss.oss-cn-chengdu.aliyuncs.com/github/jiwen_network/%E6%96%B0%E5%BB%BA%E6%96%87%E4%BB%B6%E5%A4%B9/image-20240604150528366.png)

![image-20240604150700687](https://renjia-oss.oss-cn-chengdu.aliyuncs.com/github/jiwen_network/%E6%96%B0%E5%BB%BA%E6%96%87%E4%BB%B6%E5%A4%B9/image-20240604150700687.png)

![image-20240604150714166](https://renjia-oss.oss-cn-chengdu.aliyuncs.com/github/jiwen_network/%E6%96%B0%E5%BB%BA%E6%96%87%E4%BB%B6%E5%A4%B9/image-20240604150714166.png)

![image-20240604150726936](https://renjia-oss.oss-cn-chengdu.aliyuncs.com/github/jiwen_network/%E6%96%B0%E5%BB%BA%E6%96%87%E4%BB%B6%E5%A4%B9/image-20240604150726936.png)



