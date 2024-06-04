/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 80019
Source Host           : localhost:3306
Source Database       : blog

Target Server Type    : MYSQL
Target Server Version : 80019
File Encoding         : 65001

Date: 2024-05-05 23:52:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `blog_article`
-- ----------------------------
DROP TABLE IF EXISTS `blog_article`;
CREATE TABLE `blog_article` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '发布文章用户id',
  `article_image` text COMMENT '文章封面地址',
  `article_title` tinytext NOT NULL COMMENT '文章标题',
  `article_limiter` bit(1) DEFAULT b'0' COMMENT '0:文章，1:话题',
  `article_content_id` bigint DEFAULT NULL COMMENT '文章内容ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `article_privacy` bit(1) DEFAULT b'0' COMMENT '0:公开，1:私密',
  `deleted` bit(1) DEFAULT b'0' COMMENT '0:未删除，1:已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章';

-- ----------------------------
-- Records of blog_article
-- ----------------------------
INSERT INTO `blog_article` VALUES ('8', '5', '', 'nihao', '', '15', null, '2024-03-05 04:54:39', '', '');
INSERT INTO `blog_article` VALUES ('11', '5', '', 'r', '', '19', null, '2024-03-06 00:04:29', '', '');
INSERT INTO `blog_article` VALUES ('12', '5', '', 'dr', '', '21', null, '2024-03-12 10:28:43', '', '');
INSERT INTO `blog_article` VALUES ('14', '5', '', '管理智慧：领导者的艺术与技巧', '', '25', null, '2024-03-06 02:56:27', '', '');
INSERT INTO `blog_article` VALUES ('32', '6', '', 'JavaScript学习笔记', '', '44', '2024-03-12 09:39:03', '2024-03-12 09:39:03', '', '');
INSERT INTO `blog_article` VALUES ('33', '6', 'https://ts2.cn.mm.bing.net/th?id=OIP-C.aSvNxj1fhYVBoc05NYYWQAHaFj&w=288&h=216&c=8&rs=1&qlt=90&o=6&dpr=1.3&pid=3.1&rm=2', '心情', '', '45', '2024-03-12 11:28:54', '2024-03-12 11:28:54', '', '');
INSERT INTO `blog_article` VALUES ('34', '5', '', '111', '', '46', '2024-03-12 12:23:20', '2024-03-12 12:23:20', '', '');
INSERT INTO `blog_article` VALUES ('35', '5', '', '2131', '', '47', '2024-03-12 12:23:50', '2024-03-12 12:23:50', '', '');
INSERT INTO `blog_article` VALUES ('36', '6', '', '11', '', '48', '2024-04-25 01:21:06', '2024-04-25 01:21:06', '', '');
INSERT INTO `blog_article` VALUES ('37', '6', '', '1', '', '49', '2024-04-25 01:23:59', '2024-04-25 01:23:59', '', '');
INSERT INTO `blog_article` VALUES ('38', '6', '', '1', '', '50', '2024-04-25 01:25:35', '2024-04-25 01:25:35', '', '');
INSERT INTO `blog_article` VALUES ('39', '6', '', '1', '', '51', '2024-04-25 01:26:30', '2024-04-25 01:26:30', '', '');
INSERT INTO `blog_article` VALUES ('40', '6', '', '12', '', '52', '2024-04-25 01:27:44', '2024-04-25 01:27:44', '', '');
INSERT INTO `blog_article` VALUES ('41', '6', '', '212', '', '53', '2024-04-25 01:28:08', '2024-04-25 01:28:08', '', '');
INSERT INTO `blog_article` VALUES ('42', '6', '', '12321', '', '54', '2024-04-25 01:29:10', '2024-04-25 01:29:10', '', '');

-- ----------------------------
-- Table structure for `blog_article_content`
-- ----------------------------
DROP TABLE IF EXISTS `blog_article_content`;
CREATE TABLE `blog_article_content` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `article_md` mediumtext COMMENT '文章内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章内容映射';

-- ----------------------------
-- Records of blog_article_content
-- ----------------------------
INSERT INTO `blog_article_content` VALUES ('15', '# 我的青春\n\n> 青春是一本最好的书籍，却总是被我们打开的太晚。-- 斯蒂芬·金\n\n在离开青春的时光中，我回首往事，不禁感慨万千。青春如同一条奔腾的江河，湍急而又美丽。它是我们生命中最璀璨的篇章，也是我成长路上最为宝贵的时光。\n\n## 成长的阶段\n\n青春是我们从孩童蜕变为成人的过程，在这个阶段，我经历了无数的探索和发现。每一天都充满了新奇和好奇，充满了不断突破自我的勇气。\n\n我记得那些天真无邪的笑容，和朋友们一起疯跑在操场上的快乐。我们的希冀仿佛没有边界，充满了对未来的期待。每个星期日的下午，我们会背着书包，四处探索神秘的角落，追寻自己的梦想。青春是无畏的，是勇敢面对挑战的勇气。\n\n## 梦想与努力\n\n在青春中，我也学会了为自己的梦想去努力。梦想是我青春的指引，是我追逐的目标。无论是在学业上，还是在兴趣爱好中，我都始终坚持努力不懈。困难和挫折只是暂时的，只要我坚持，梦想就能变为现实。\n\n每一个早晨，在生活的舞台上，我不断挑战自我限制，勇敢追求成长。我学会了承担责任，尊重他人，与人沟通和协作。我不再害怕失败，因为失败只是反复尝试的一部分。正是这种正能量的努力，让我在青春岁月中积累了宝贵的丰富经验。\n\n## 珍爱的人和美好回忆\n\n青春中最美好的事情之一就是与珍爱的人共度时光，与他们一起创造了许多美好的回忆。\n\n我们一起嬉笑打闹，谈心说话。无论是在学校里还是在生活中，我们一起经历了成长的酸甜苦辣。每一次相聚都是一次珍贵的礼物，让我明白友情是青春的光芒。在他们身上，我学到了如何关心他人，如何分担他人的忧愁，如何与他人分享喜悦。这些贵重的经历让我更加明白，珍惜身边的每一个人是多么的重要。\n\n## 对未来的期许\n\n青春是向前看的时光，是点燃梦想的时刻。在我追逐梦想的道路上，我愿意用我年轻的热情与勇气去书写未来。我希望能将我所学到的知识和经验转化为实际行动，为社会做出贡献。\n\n争取成为更好的自己，为自己设定更高的目标，不断学习和成长。我相信，只要我付出努力，就一定能够实现自己的梦想。\n\n## 结语\n\n青春像一束阳光，照亮了我前行的道路。在我的青春岁月中，我学会了坚持，学会了追求梦想，学会了珍惜人与人之间的情感。这段珍贵时光，将永远成为我人生中最宝贵的财富。\n\n让我们怀揣梦想，迈开坚定的步伐，勇敢面对未来。因为青春从未离开我们，它一直与我们同在。\n');
INSERT INTO `blog_article_content` VALUES ('16', '# 我的青春\n\n> 青春是一本最好的书籍，却总是被我们打开的太晚。-- 斯蒂芬·金\n\n在离开青春的时光中，我回首往事，不禁感慨万千。青春如同一条奔腾的江河，湍急而又美丽。它是我们生命中最璀璨的篇章，也是我成长路上最为宝贵的时光。\n\n## 成长的阶段\n\n青春是我们从孩童蜕变为成人的过程，在这个阶段，我经历了无数的探索和发现。每一天都充满了新奇和好奇，充满了不断突破自我的勇气。\n\n我记得那些天真无邪的笑容，和朋友们一起疯跑在操场上的快乐。我们的希冀仿佛没有边界，充满了对未来的期待。每个星期日的下午，我们会背着书包，四处探索神秘的角落，追寻自己的梦想。青春是无畏的，是勇敢面对挑战的勇气。\n\n## 梦想与努力\n\n在青春中，我也学会了为自己的梦想去努力。梦想是我青春的指引，是我追逐的目标。无论是在学业上，还是在兴趣爱好中，我都始终坚持努力不懈。困难和挫折只是暂时的，只要我坚持，梦想就能变为现实。\n\n每一个早晨，在生活的舞台上，我不断挑战自我限制，勇敢追求成长。我学会了承担责任，尊重他人，与人沟通和协作。我不再害怕失败，因为失败只是反复尝试的一部分。正是这种正能量的努力，让我在青春岁月中积累了宝贵的丰富经验。\n\n## 珍爱的人和美好回忆\n\n青春中最美好的事情之一就是与珍爱的人共度时光，与他们一起创造了许多美好的回忆。\n\n我们一起嬉笑打闹，谈心说话。无论是在学校里还是在生活中，我们一起经历了成长的酸甜苦辣。每一次相聚都是一次珍贵的礼物，让我明白友情是青春的光芒。在他们身上，我学到了如何关心他人，如何分担他人的忧愁，如何与他人分享喜悦。这些贵重的经历让我更加明白，珍惜身边的每一个人是多么的重要。\n\n## 对未来的期许\n\n青春是向前看的时光，是点燃梦想的时刻。在我追逐梦想的道路上，我愿意用我年轻的热情与勇气去书写未来。我希望能将我所学到的知识和经验转化为实际行动，为社会做出贡献。\n\n争取成为更好的自己，为自己设定更高的目标，不断学习和成长。我相信，只要我付出努力，就一定能够实现自己的梦想。\n\n## 结语\n\n青春像一束阳光，照亮了我前行的道路。在我的青春岁月中，我学会了坚持，学会了追求梦想，学会了珍惜人与人之间的情感。这段珍贵时光，将永远成为我人生中最宝贵的财富。\n\n让我们怀揣梦想，迈开坚定的步伐，勇敢面对未来。因为青春从未离开我们，它一直与我们同在。\n');
INSERT INTO `blog_article_content` VALUES ('17', '# 我的青春\n\n> 青春是一本最好的书籍，却总是被我们打开的太晚。-- 斯蒂芬·金\n\n在离开青春的时光中，我回首往事，不禁感慨万千。青春如同一条奔腾的江河，湍急而又美丽。它是我们生命中最璀璨的篇章，也是我成长路上最为宝贵的时光。\n\n## 成长的阶段\n\n青春是我们从孩童蜕变为成人的过程，在这个阶段，我经历了无数的探索和发现。每一天都充满了新奇和好奇，充满了不断突破自我的勇气。\n\n我记得那些天真无邪的笑容，和朋友们一起疯跑在操场上的快乐。我们的希冀仿佛没有边界，充满了对未来的期待。每个星期日的下午，我们会背着书包，四处探索神秘的角落，追寻自己的梦想。青春是无畏的，是勇敢面对挑战的勇气。\n\n## 梦想与努力\n\n在青春中，我也学会了为自己的梦想去努力。梦想是我青春的指引，是我追逐的目标。无论是在学业上，还是在兴趣爱好中，我都始终坚持努力不懈。困难和挫折只是暂时的，只要我坚持，梦想就能变为现实。\n\n每一个早晨，在生活的舞台上，我不断挑战自我限制，勇敢追求成长。我学会了承担责任，尊重他人，与人沟通和协作。我不再害怕失败，因为失败只是反复尝试的一部分。正是这种正能量的努力，让我在青春岁月中积累了宝贵的丰富经验。\n\n## 珍爱的人和美好回忆\n\n青春中最美好的事情之一就是与珍爱的人共度时光，与他们一起创造了许多美好的回忆。\n\n我们一起嬉笑打闹，谈心说话。无论是在学校里还是在生活中，我们一起经历了成长的酸甜苦辣。每一次相聚都是一次珍贵的礼物，让我明白友情是青春的光芒。在他们身上，我学到了如何关心他人，如何分担他人的忧愁，如何与他人分享喜悦。这些贵重的经历让我更加明白，珍惜身边的每一个人是多么的重要。\n\n## 对未来的期许\n\n青春是向前看的时光，是点燃梦想的时刻。在我追逐梦想的道路上，我愿意用我年轻的热情与勇气去书写未来。我希望能将我所学到的知识和经验转化为实际行动，为社会做出贡献。\n\n争取成为更好的自己，为自己设定更高的目标，不断学习和成长。我相信，只要我付出努力，就一定能够实现自己的梦想。\n\n## 结语\n\n青春像一束阳光，照亮了我前行的道路。在我的青春岁月中，我学会了坚持，学会了追求梦想，学会了珍惜人与人之间的情感。这段珍贵时光，将永远成为我人生中最宝贵的财富。\n\n让我们怀揣梦想，迈开坚定的步伐，勇敢面对未来。因为青春从未离开我们，它一直与我们同在。\n');
INSERT INTO `blog_article_content` VALUES ('18', null);
INSERT INTO `blog_article_content` VALUES ('19', '2024 年 3 月 10 日\n\n今天是一个特殊的日子，因为我和他相识的第一天。一年前的今天，我第一次见到了他的笑容，那个让我心动不已的笑容。当时的我不知道，这个人会成为我生命中如此重要的存在。\n\n回想起我们相识的那一天，一切仿佛昨天发生。我们在一个朋友的生日派对上相遇。当时，他向我走来，微笑着自我介绍。他的眼神中透露着热情和真诚，令我陷入了他的魅力。我们从那天起就开始了交往，每一天都是那么美好而快乐。\n\n在一年的相处中，我们经历了许多美好的时刻和难忘的经历。我们一起旅行，探索未知的城市和风景。我们一起度过了无数个夜晚，相互依靠和鼓励。我们一起分享了许多笑声和眼泪，共同面对生活中的挑战。我们从彼此身上学到了很多，不断地成长和进步。\n\n他是我生命中最重要的人，他给了我无尽的爱和支持。他总是在我最需要他的时候出现，给我力量和勇气。他从来不计较我的过去，只看重我内心的美和潜力。他与我一起建立了美好的未来规划，我们共同追求着梦想。\n\n爱情不是简单的情感，它是一种奉献和牺牲。我们已经学会了互相包容和理解，一起度过人生的起伏和坎坷。我们相信，爱情是需要付出和经营的。我们不仅追求浪漫和激情，更注重彼此间的真实和持久。\n\n我不知道未来会如何，但我相信我们会一直走下去。无论前方有多少困难和挑战，我们都将携手面对。因为我们的爱情是坚不可摧的，是我们共同的力量源泉。\n\n爱情是一场美丽的旅程，我们将用心去经营和守护，一直走到最后。我期待着未来的每一天，期待着我们共同创造的更多回忆和故事。我们的爱情是永恒的，它将伴随着我一生。\n\n这是我对爱情的思考和感悟，希望它能激励更多的人去珍惜和追求真挚的爱情。让我们一起相信爱情的力量，用心去爱，用力去守护。爱是美好的，它给予我们无尽的幸福和满足。让我们为爱而努力，为爱而生活。\n');
INSERT INTO `blog_article_content` VALUES ('20', null);
INSERT INTO `blog_article_content` VALUES ('21', '# 念余居室述怀\n\n余居室之际，窗外烟雨微微，室中烛火摇曳。忆起往昔，久居此间，复忆及清朝诸公笔下之美文，遂有撰文一篇之意。试觇纸墨，书以文言，则愿其情景如实呈现。\n\n## 草木生姿\n\n室内之花木，虽为园中之微观，然其生机盎然，稚叶吐新，俨然一派生机。花开花落，生长枯荣之观，颇为书中所谓“风雨骤至，生命之脆弱”者所感。\n\n## 心怀乡思\n\n闭目怡然，思及乡音，自天降至刻，乡音之美不绝于耳，过往风物如今犹在眼前。应有斐然之思念国土之情，重山叠嶂，江河纵横，有何等壮美！\n\n## 文心自恣\n\n愧余才短，不能满拟百年前辈之文采。纵览先贤之文章，得神思意驰，若身临其境。欲有多人相倾吐，共议天下大事之意乐，惜余浅薄，奈何致此！\n\n## 寄语\n\n庶几见诸吾言，得悯愚而指教。愿人生得一知已，共从书中得灵感。望修身齐家治国平天下之美愿，愿汝等有缘见此，能共勉。\n\n盖因居室述怀，有感而写，乃知文学之奥妙，实未可知。余心所喜，惟愿以文相告，愿闻闻者，于斯文中得一慰藉也。\n');
INSERT INTO `blog_article_content` VALUES ('22', null);
INSERT INTO `blog_article_content` VALUES ('23', '23243\n');
INSERT INTO `blog_article_content` VALUES ('24', null);
INSERT INTO `blog_article_content` VALUES ('25', '## 引言\n\n管理乃一门艺术，亦系一项技术。领导者应具备管理智慧，善于运用各项管理工具和技巧，以实现组织的愿景和目标。本文将探讨领导者在管理过程中所需的艺术和技巧。\n\n## 1. 愿景与目标设定\n\n* **明确愿景**：领导者应该清晰明确地描述组织的愿景，激励团队成员为之努力奋斗。\n* **设定目标**：制定具体、可衡量的目标，并与团队共同制定实现这些目标的计划。\n\n## 2. 沟通与协调\n\n* **有效沟通**：建立开放、透明的沟通机制，及时分享信息、反馈和指导，确保团队成员理解组织的方向和期望。\n* **协调团队**：协调团队成员的工作，合理分配资源，协同合作，以实现整体目标。\n\n## 3. 激励与激励\n\n* **激励团队**：采用多样化的激励手段，调动团队成员的积极性和创造力，激发团队的潜能。\n* **奖惩分明**：公平公正地对待团队成员，使其认可努力和成绩，同时对违规行为进行适当处理。\n\n## 4. 制度与规范\n\n* **建立制度**：建立健全的管理制度和流程，保障组织的运转效率和秩序。\n* **遵守规范**：领导者应身体力行，带头遵守组织规章制度，树立榜样，引领团队自律。\n\n## 结语\n\n管理乃一门综合性的艺术与技术，领导者应不断学习、提升自我，善于应用各项管理工具和技巧，引领团队走向成功。通过建立明确的愿景与目标、有效沟通与协调、激励与奖惩、建立制度规范等措施，领导者可实现组织的长期可持续发展。愿每位领导者在管理智慧的指引下，成就辉煌，引领团队创造更加美好的明天。\n');
INSERT INTO `blog_article_content` VALUES ('26', null);
INSERT INTO `blog_article_content` VALUES ('27', '121\n');
INSERT INTO `blog_article_content` VALUES ('28', '121\n');
INSERT INTO `blog_article_content` VALUES ('29', '121\n');
INSERT INTO `blog_article_content` VALUES ('30', '121\n');
INSERT INTO `blog_article_content` VALUES ('31', '13312312\n');
INSERT INTO `blog_article_content` VALUES ('32', '313312414141243112\n');
INSERT INTO `blog_article_content` VALUES ('33', '24243242\n');
INSERT INTO `blog_article_content` VALUES ('34', '42423432432\n');
INSERT INTO `blog_article_content` VALUES ('35', '42424\n\n243243243324242422324\n');
INSERT INTO `blog_article_content` VALUES ('36', '4242414\n');
INSERT INTO `blog_article_content` VALUES ('37', '414214321\n');
INSERT INTO `blog_article_content` VALUES ('38', '313114414\n');
INSERT INTO `blog_article_content` VALUES ('39', '4141\n\n14141414 1124\n');
INSERT INTO `blog_article_content` VALUES ('40', '3\n\n3141414\n');
INSERT INTO `blog_article_content` VALUES ('41', 'eqwdszsfwfsa\n');
INSERT INTO `blog_article_content` VALUES ('42', '31\n\n31231232131\n');
INSERT INTO `blog_article_content` VALUES ('43', '31313123211421412\n');
INSERT INTO `blog_article_content` VALUES ('44', '#### 现代模式\n\n> **use strict**\n\n```\n这个指令看上去像一个字符串\n```\n\n`\"use strict\"` 或者 `\'use strict\'`。当它处于脚本文件的顶部时，则整个脚本文件都将以“现代”模式进行工作。\n\n- 请确保 `\"use strict\"` 出现在脚本的最顶部，否则严格模式可能无法启用。\n- 现代 JavaScript 支持 “class” 和 “module” —— 高级语言结构（本教程后续章节会讲到），它们会自动启用 `use strict`。因此，如果我们使用它们，则无需添加 `\"use strict\"` 指令。\n\n#### 交互：alert、prompt 和 confirm\n\n> **alter**\n\n~~~javascript\nalert(\"Hello\");\n~~~\n\n```\n弹出的这个带有信息的小窗口被称为\n```\n\n**模态窗**。“modal” 意味着用户不能与页面的其他部分（例如点击其他按钮等）进行交互，直到他们处理完窗口。在上面示例这种情况下 —— 直到用户点击“确定”按钮。\n\n> **prompt**\n\n~~~javascript\nresult = prompt(title, [default]);\n~~~\n\n`prompt` 函数接收两个参数：\n\n- `title`：显示给用户的文本\n- `[default]`：可选的第二个参数，指定 input 框的初始值。\n\n> **confirm**\n\n~~~javascript\nresult = confirm(question);\n~~~\n\n`confirm` 函数显示一个带有 `question` 以及确定和取消两个按钮的模态窗口。\n\n点击确定返回 `true`，点击取消返回 `false`。\n\n#### 空值合并运算符 \'??\'\n\n~~~javascript\nlet height = 0;\nalert(height ?? 100); // 0\n~~~\n\n- `??` 序列从一系列的值中选择出第一个非 `null/undefined` 的值\n- `??` 返回第一个 **已定义的** 值。\n\n#### 可选链 \"?.\"\n\n~~~javascript\nlet user = {}; // user 没有 address 属性\nalert( user?.address?.street ); // undefined（不报错）\n~~~\n\n- 如果可选链 `?.` 前面的值为 `undefined` 或者 `null`，它会停止运算并返回 `undefined`。\n- `?.` 语法使其前面的值成为可选值，但不会对其后面的起作用。\n- `?.` 前的变量必须已声明\n\n  如果未声明变量 `user`，那么 `user?.anything` 会触发一个错误：\n\n  ```javascript\n  // ReferenceError: user is not defined\n  user?.address;\n  ```\n\n  `?.` 前的变量必须已声明（例如 `let/const/var user` 或作为一个函数参数）。可选链仅适用于已声明的变量。\n- 如果 `?.` 左边部分不存在，就会立即停止运算（“短路效应”）。因此，如果在 `?.` 的右侧有任何进一步的函数调用或操作，它们均不会执行。\n\n#### 函数\n\n- 函数声明：在主代码流中声明为单独的语句的函数。\n\n  **在函数声明被定义之前，它就可以被调用。**\n\n  ~~~javascript\n  function sayHi() {\n    alert( \"Hello\" );\n  }\n  ~~~\n- 函数表达式：在一个表达式中或另一个语法结构中创建的函数。\n\n  **函数表达式是在代码执行到达时被创建，并且仅从那一刻起可用。**\n\n  ~~~javascript\n  let sayHi = function() {\n    alert( \"Hello\" );\n  };\n  ~~~\n- 函数是个值。无论函数是如何创建的，函数都是一个值。无论函数是如何创建的，函数都是一个值。\n\n#### 箭头函数\n\n箭头函数可以像函数表达式一样使用。\n\n~~~javascript\nlet age = prompt(\"What is your age?\", 18);\nlet welcome = (age < 18) ?\n  () => alert(\'Hello\'!) :\n  () => alert(\"Greetings!\");\nwelcome();\n~~~\n\n箭头函数对于简单的操作很方便，特别是对于单行的函数。它具体有两种形式：\n\n- 不带花括号：`(...args) => expression` — 右侧是一个表达式：函数计算表达式并返回其结果。如果只有一个参数，则可以省略括号，例如 `n => n*2`。\n- 带花括号：`(...args) => { body }` — 花括号允许我们在函数中编写多个语句，但是我们需要显式地 `return` 来返回一些内容。\n\n深入理解箭头函数：\n\n- **箭头函数没有“this\"**，箭头函数没有 `this`。如果访问 `this`，则会从外部获取。\n- **不能对箭头函数进行** `new` **操作**\n- **箭头函数没有**`arguments`\n\n#### 对象引用和复制\n\n```\n与原始类型相比，对象的根本区别之一是对象是“通过引用”被存储和复制的，与原始类型值相反：字符串，数字，布尔值等 —— 始终是以“整体值”的形式被复制的。\n```\n\n**赋值了对象的变量存储的不是对象本身，而是该对象“在内存中的地址” —— 换句话说就是对该对象的“引用”。**\n\n克隆与合并：\n\n> **Object.assign()**\n\n~~~javascript\nObject.assign(dest, [src1, src2, src3...])\n~~~\n\n- 第一个参数 `dest` 是指目标对象。\n- 更后面的参数 `src1, ..., srcN`（可按需传递多个参数）是源对象。\n- 该方法将所有源对象的属性拷贝到目标对象 `dest` 中。换句话说，从第二个开始的所有参数的属性都被拷贝到第一个参数的对象中。\n- 调用结果返回 `dest`。\n\n~~~javascript\nlet user = {\n  name: \"John\",\n  sizes: {\n    height: 182,\n    width: 50\n  }\n};\n\nlet clone = Object.assign({}, user);\n\nalert( user.sizes === clone.sizes ); // true，同一个对象\n\n// user 和 clone 分享同一个 sizes\nuser.sizes.width++;       // 通过其中一个改变属性值\nalert(clone.sizes.width); // 51，能从另外一个看到变更的结果\n~~~\n\n现在这样拷贝`clone.sizes = user.sizes` 已经不足够了，因为 `user.sizes` 是个对象，它会以引用形式被拷贝。因此 `clone` 和 `user` 会共用一个 sizes。\n\n#### Symbol 类型\n\n- `Symbol值`表示唯一的标识符。\n- 我们可以给 Symbol 一个描述（也称为 Symbol 名），这在代码调试时非常有用：\n\n  ~~~javascript\n  // id 是 symbol 的一个实例化对象\n  // id 是描述为 \"id\" 的 Symbol\n  let id = Symbol(\"id\");\n  ~~~\n\n  Symbol 保证是唯一的。即使我们创建了许多具有相同描述的 Symbol，它们的值也是不同。描述只是一个标签，不影响任何东西。\n- **Symbol 不会被自动转换为字符串**\n\n  如果我们真的想显示一个 Symbol，我们需要在它上面调用 `.toString()`，如下所示：\n\n  ~~~javascript\n  let id = Symbol(\"id\");\n  alert(id.toString()); // Symbol(id)，现在它有效了\n  ~~~\n\n  或者获取 `symbol.description` 属性，只显示描述（description）：\n\n  ~~~javascript\n  let id = Symbol(\"id\");\n  alert(id.description); // id\n  ~~~\n- **“隐藏”属性**\n\n  Symbol 允许我们创建对象的“隐藏”属性，代码的任何其他部分都不能意外访问或重写这些属性。\n\n  使用 `Symbol(\"id\")` 作为键，比起用字符串 `\"id\"` 来有什么好处呢？\n\n  因为 `user` 对象属于其他的代码，那些代码也会使用这个对象，所以我们不应该在它上面直接添加任何字段，这样很不安全。但是你添加的 Symbol 属性不会被意外访问到，第三方代码根本不会看到它，所以使用 Symbol 基本上不会有问题。\n\n  另外，假设另一个脚本希望在 `user` 中有自己的标识符，以实现自己的目的。这可能是另一个 JavaScript 库，因此脚本之间完全不了解彼此。\n- **对象字面量中的 Symbol**\n\n  ~~~javascript\n  let id = Symbol(\"id\");\n\n  let user = {\n    name: \"John\",\n    [id]: 123 // 而不是 \"id\"：123\n  };\n  ~~~\n\n  这是因为我们需要变量 `id` 的值作为键，而不是字符串 “id”。\n- **Symbol 在 for…in 中会被跳过**\n\n  `Object.keys(user) `也会忽略它们。这是一般“隐藏符号属性”原则的一部分。如果另一个脚本或库遍历我们的对象，它不会意外地访问到符号属性。\n\n  相反，`Object.assign()`会同时复制字符串和 symbol 属性：\n- **全局 symbol**\n\n  正如我们所看到的，通常所有的 Symbol 都是不同的，即使它们有相同的名字。但有时我们想要名字相同的 Symbol 具有相同的实体。例如，应用程序的不同部分想要访问的 Symbol `\"id\"` 指的是完全相同的属性。\n\n  为了实现这一点，这里有一个 **全局 Symbol 注册表**。我们可以在其中创建 Symbol 并在稍后访问它们，它可以确保每次访问相同名字的 Symbol 时，返回的都是相同的 Symbol。\n\n  要从注册表中读取（不存在则创建）Symbol，请使用 `Symbol.for(key)`。\n\n  对于全局 Symbol，不仅有 `Symbol.for(key)` 按名字返回一个 Symbol，还有一个反向调用：`Symbol.keyFor(sym)`，它的作用完全反过来：通过全局 Symbol 返回一个名字。\n\n  例如：\n\n  ~~~\n  // 通过 name 获取 Symbol\n  let sym = Symbol.for(\"name\");\n  let sym2 = Symbol.for(\"id\");\n\n  // 通过 Symbol 获取 name\n  alert( Symbol.keyFor(sym) ); // name\n  alert( Symbol.keyFor(sym2) ); // id\n  ~~~\n- **系统 Symbol**\n\n  JavaScript 内部有很多“系统” Symbol，我们可以使用它们来微调对象的各个方面。\n\n  它们都被列在了 [众所周知的 Symbol](https://tc39.github.io/ecma262/#sec-well-known-symbols) 表的规范中：\n\n  - `Symbol.hasInstance`\n  - `Symbol.isConcatSpreadable`\n  - `Symbol.iterator`\n  - `Symbol.toPrimitive`\n  - ……等等。\n\n  例如，`Symbol.toPrimitive` 允许我们将对象描述为原始值转换。我们很快就会看到它的使用。\n\n#### 数学类型\n\n> **toString(base)**\n\n方法 `num.toString(base)` 返回在给定 `base` 进制数字系统中 `num` 的字符串表示形式。\n\n> **toFixed(n)**\n\n将数字舍入到小数点后 `n` 位，并以字符串形式返回结果。\n\n> **isFinite(value)**\n\n`isFinite(value)` 将其参数转换为数字，如果是常规数字，则返回 `true`，而不是 `NaN/Infinity/-Infinity`：\n\n> **isNaN(value)**\n\n`isNaN(value)` 将其参数转换为数字，然后测试它是否为 `NaN`：\n\n> **parseInt(str, radix)**\n\n`parseInt()` 函数具有可选的第二个参数。它指定了数字系统的基数，因此 `parseInt` 还可以解析十六进制数字、二进制数字等的字符串：\n\n`parseInt` 和 `parseFloat` 的作用。\n\n它们可以从字符串中“读取”数字，直到无法读取为止。如果发生 error，则返回收集到的数字。函数 `parseInt` 返回一个整数，而 `parseFloat` 返回一个浮点数：\n\n```javascript\nalert( parseInt(\'100px\') ); // 100\nalert( parseFloat(\'12.5em\') ); // 12.5\n```\n\n#### 数组方法\n\n- **添加/移除数组元素**\n  - `arr.push(...items)` —— 从尾端添加元素，\n  - `arr.pop()` —— 从尾端提取元素，\n  - `arr.shift()` —— 从首端提取元素，\n  - `arr.unshift(...items)` —— 从首端添加元素。\n\n> **splice**\n\n可以做所有事情：添加，删除和插入元素。\n\n~~~javascript\narr.splice(start[, deleteCount[, elem1, ..., elemN]])\n~~~\n\n它从索引 `start` 开始修改 `arr`：删除 `deleteCount` 个元素并在当前位置插入 `elem1, ..., elemN`。最后返回已被删除元素的数组。\n\n> **slice**\n\n~~~javascript\narr.slice([start], [end])\n~~~\n\n它会返回一个新数组，将所有从索引 `start` 到 `end-1`的数组项复制到一个新的数组。`start` 和 `end` 都可以是负数，在这种情况下，从末尾计算索引。\n\n> **concat**\n\n~~~javascript\narr.concat(arg1, arg2...)\n~~~\n\n创建一个新数组，其中包含来自于其他数组和其他项的值。\n\n它接受任意数量的参数 —— 数组或值都可以。\n\n结果是一个包含来自于 `arr`，然后是 `arg1`，`arg2` 的元素的新数组。\n\n如果参数 `argN` 是一个数组，那么其中的所有元素都会被复制。否则，将复制参数本身。\n\n> **forEach**\n\n允许为数组的每个元素都运行一个函数。\n\n~~~javascript\narr.forEach(function(item, index, array) {\n  // ... do something with item\n});\n~~~\n\n> **indexOf/lastIndexOf 和 includes**\n\n与字符串操作具有相同的语法，并且作用基本上也与字符串的方法相同，只不过这里是对数组元素而不是字符进行操作：\n\n- `arr.indexOf(item, from)` 从索引 `from` 开始搜索 `item`，如果找到则返回索引，否则返回 `-1`。\n- `arr.lastIndexOf(item, from)` —— 和上面相同，只是从右向左搜索。\n- `arr.includes(item, from)` —— 从索引 `from` 开始搜索 `item`，如果找到则返回 `true`（译注：如果没找到，则返回 `false`）。\n\n> **find 和 findIndex**\n\n~~~javascript\nlet result = arr.find(function(item, index, array) {\n  // 如果返回 true，则返回 item 并停止迭代\n  // 对于假值（falsy）的情况，则返回 undefined\n});\n~~~\n\n依次对数组中的每个元素调用该函数：\n\n- `item` 是元素。\n- `index` 是它的索引。\n- `array` 是数组本身。\n\n如果它返回 `true`，则搜索停止，并返回 `item`。如果没有搜索到，则返回 `undefined`。\n\n`arr.findIndex`方法（与 `arr.find` 方法）基本上是一样的，但它返回找到元素的索引，而不是元素本身。并且在未找到任何内容时返回 `-1`。\n\n> **filter**\n\n`find` 方法搜索的是使函数返回 `true` 的第一个（单个）元素。\n\n如果需要匹配的有很多，我们可以使用` arr.filter(fn)`。\n\n语法与 `find` 大致相同，但是 `filter` 返回的是所有匹配元素组成的数组：\n\n~~~javascript\nlet results = arr.filter(function(item, index, array) {\n  // 如果 true item 被 push 到 results，迭代继续\n  // 如果什么都没找到，则返回空数组\n});\n~~~\n\n> **map**\n\n它对数组的每个元素都调用函数，并返回结果数组。\n\n~~~javascript\nlet result = arr.map(function(item, index, array) {\n  // 返回新值而不是当前元素\n})\n~~~\n\n> **split 和 join**\n\n`str.split(delim)` 方法可以做到。它通过给定的分隔符 `delim` 将字符串分割成一个数组。\n\n`arr.join(glue)` 与 `split` 相反。它会在它们之间创建一串由 `glue` 粘合的 `arr` 项。\n\n> **reduce**\n\n用于根据数组计算单个值\n\n~~~javascript\nlet value = arr.reduce(function(accumulator, item, index, array) {\n  // ...\n}, [initial]);\n~~~\n\n该函数一个接一个地应用于所有数组元素，并将其结果“搬运（carry on）”到下一个调用。\n\n参数：\n\n- `accumulator` —— 是上一个函数调用的结果，第一次等于 `initial`（如果提供了 `initial` 的话）。本质上是累加器\n- `item` —— 当前的数组元素。\n- `index` —— 当前索引。\n- `arr` —— 数组本身。\n\n> 如果 `fn` 返回一个真值，`arr.some(fn)` 立即返回 `true` 并停止迭代其余数组项；如果 `fn` 返回一个假值，`arr.every(fn)` 立即返回 `false` 并停止对其余数组项的迭代。\n\n#### Iterable object（可迭代对象）\n\n可以应用 `for..of` 的对象被称为 **可迭代的**。\n\n> **Symbol.iterator**\n\n为了让 `range` 对象可迭代（也就让 `for..of` 可以运行）我们需要为对象添加一个名为 `Symbol.iterator` 的方法（一个专门用于使对象可迭代的内建 symbol）。\n\n1. 当 `for..of` 循环启动时，它会调用这个方法（如果没找到，就会报错）。这个方法必须返回一个 **迭代器（iterator）** —— 一个有 `next` 方法的对象。\n2. 从此开始，`for..of` **仅适用于这个被返回的对象**。\n3. 当 `for..of` 循环希望取得下一个数值，它就调用这个对象的 `next()` 方法。\n4. `next()` 方法返回的结果的格式必须是 `{done: Boolean, value: any}`，当 `done=true` 时，表示循环结束，否则 `value` 是下一个值。\n\n   ~~~javascript\n   let range = {\n     from: 1,\n     to: 5\n   };\n\n   // 1. for..of 调用首先会调用这个：\n   range[Symbol.iterator] = function() {\n\n     // ……它返回迭代器对象（iterator object）：\n     // 2. 接下来，for..of 仅与下面的迭代器对象一起工作，要求它提供下一个值\n     return {\n       current: this.from,\n       last: this.to,\n\n       // 3. next() 在 for..of 的每一轮循环迭代中被调用\n       next() {\n         // 4. 它将会返回 {done:.., value :...} 格式的对象\n         if (this.current <= this.last) {\n           return { done: false, value: this.current++ };\n         } else {\n           return { done: true };\n         }\n       }\n     };\n   };\n\n   // 现在它可以运行了！\n   for (let num of range) {\n     alert(num); // 1, 然后是 2, 3, 4, 5\n   }\n   ~~~\n\n> **Array.from**\n\n有一个全局方法` Array.from`可以接受一个可迭代或类数组的值，并从中获取一个“真正的”数组。然后我们就可以对其调用数组方法了。\n\n~~~javascript\nArray.from(obj[, mapFn, thisArg])\n~~~\n\n可选的第二个参数 `mapFn` 可以是一个函数，该函数会在对象中的元素被添加到数组前，被应用于每个元素，此外 `thisArg` 允许我们为该函数设置 `this`。\n\n#### Map and Set（映射和集合）\n\n- `Object.keys(obj)`—— 返回一个包含该对象所有的键的数组。\n- `Object.values(obj)`—— 返回一个包含该对象所有的值的数组。\n- `Object.entries(obj)` —— 返回一个包含该对象所有 [key, value] 键值对的数组。\n\n> ### **Map**\n\n`Map` 是一个带键的数据项的集合，就像一个 `Object` 一样。 但是它们最大的差别是 `Map` 允许任何类型的键（key）。\n\n它的方法和属性如下：\n\n- `new Map()` —— 创建 map。\n- `map.set(key, value)` —— 根据键存储值。\n- `map.get(key)` —— 根据键来返回值，如果 `map` 中不存在对应的 `key`，则返回 `undefined`。\n- `map.has(key)` —— 如果 `key` 存在则返回 `true`，否则返回 `false`。\n- `map.delete(key)` —— 删除指定键的值。\n- `map.clear()` —— 清空 map。\n- `map.size` —— 返回当前元素个数。\n\n**Map 还可以使用对象作为键。**\n\n**链式调用**：每一次 `map.set` 调用都会返回 map 本身，所以我们可以进行“链式”调用\n\n~~~javascript\nmap.set(\'1\', \'str1\')\n  .set(1, \'num1\')\n  .set(true, \'bool1\');\n~~~\n\n**Map 迭代：**\n\n如果要在 `map` 里使用循环，可以使用以下三个方法：\n\n- `map.keys()` —— 遍历并返回所有的键（returns an iterable for keys），\n- `map.values()` —— 遍历并返回所有的值（returns an iterable for values），\n- `map.entries()` —— 遍历并返回所有的实体（returns an iterable for entries）`[key, value]`，`for..of` 在默认情况下使用的就是这个。\n- `Object.entries`：从对象创建 Map\n\n  如果我们想从一个已有的普通对象（plain object）来创建一个 `Map`\n\n  ~~~javascript\n  let obj = {\n    name: \"John\",\n    age: 30\n  };\n\n  let map = new Map(Object.entries(obj));\n\n  alert( map.get(\'name\') ); // John\n  ~~~\n- `Object.fromEntries`：从 Map 创建对象\n\n  `Object.fromEntries` :给定一个具有 `[key, value]` 键值对的数组，它会根据给定数组创建一个对象：\n\n  ~~~javascript\n  let prices = Object.fromEntries([\n    [\'banana\', 1],\n    [\'orange\', 2],\n    [\'meat\', 4]\n  ]);\n\n  // 现在 prices = { banana: 1, orange: 2, meat: 4 }\n\n  alert(prices.orange); // 2\n  ~~~\n\n> ### **Set**\n\n`Set` 是一个特殊的类型集合 —— “值的集合”（没有键），它的每一个值只能出现一次。\n\n它的主要方法如下：\n\n- `new Set(iterable)` —— 创建一个 `set`，如果提供了一个 `iterable` 对象（通常是数组），将会从数组里面复制值到 `set` 中。\n- `set.add(value)` —— 添加一个值，返回 set 本身\n- `set.delete(value)` —— 删除值，如果 `value` 在这个方法调用的时候存在则返回 `true` ，否则返回 `false`。\n- `set.has(value)` —— 如果 `value` 在 set 中，返回 `true`，否则返回 `false`。\n- `set.clear()` —— 清空 set。\n- `set.size` —— 返回元素个数。\n\n**Set 迭代（iteration）**:\n\n我们可以使用 `for..of` 或 `forEach` 来遍历 Set：\n\n```javascript\nlet set = new Set([\"oranges\", \"apples\", \"bananas\"]);\n\nfor (let value of set) alert(value);\n\n// 与 forEach 相同：\nset.forEach((value, valueAgain, set) => {\n  alert(value);\n});\n```\n\n- `set.keys()` —— 遍历并返回所有的值（returns an iterable object for values），\n- `set.values()` —— 与 `set.keys()` 作用相同，这是为了兼容 `Map`，\n- `set.entries()` —— 遍历并返回所有的实体（returns an iterable object for entries）`[value, value]`，它的存在也是为了兼容 `Map`。\n\n#### 解构赋值\n\n**解构赋值** 是一种特殊的语法，它使我们可以将数组或对象“拆包”至一系列变量中\n\n> **数组解构**\n\n~~~javascript\n// 我们有一个存放了名字和姓氏的数组\nlet arr = [\"John\", \"Smith\"]\n\n// 解构赋值\n// sets firstName = arr[0]\n// and surname = arr[1]\nlet [firstName, surname] = arr;\n\nalert(firstName); // John\nalert(surname);  // Smith\n~~~\n\n- **等号右侧可以是任何可迭代对象**\n\n  …实际上，我们可以将其与任何可迭代对象一起使用，而不仅限于数组：\n\n  ```javascript\n  let [a, b, c] = \"abc\"; // [\"a\", \"b\", \"c\"]\n  let [one, two, three] = new Set([1, 2, 3]);\n  ```\n\n  这行得通，因为在内部，解构赋值是通过迭代右侧的值来完成工作的。这是一种用于对在 `=` 右侧的值上调用 `for..of` 并进行赋值的操作的语法糖。\n- **赋值给等号左侧的任何内容**\n\n  我们可以在等号左侧使用任何“可以被赋值的”东西。\n\n  例如，一个对象的属性：\n\n  ```javascript\n  let user = {};\n  [user.name, user.surname] = \"John Smith\".split(\' \');\n\n  alert(user.name); // John\n  alert(user.surname); // Smith\n  ```\n- **其余的 ‘…’**\n\n  如果我们还想收集其余的数组项 —— 我们可以使用三个点 `\"...\"` 来再加一个参数以获取“其余”数组项：\n\n  ```javascript\n  let [name1, name2, ...rest] = [\"Julius\", \"Caesar\", \"Consul\", \"of the Roman Republic\"];\n\n  // rest 是包含从第三项开始的其余数组项的数组\n  alert(rest[0]); // Consul\n  alert(rest[1]); // of the Roman Republic\n  alert(rest.length); // 2\n  ```\n- **默认值**\n\n  如果我们想要一个“默认”值给未赋值的变量，我们可以使用 `=` 来提供：\n\n  ```javascript\n  // 默认值\n  let [name = \"Guest\", surname = \"Anonymous\"] = [\"Julius\"];\n\n  alert(name);    // Julius（来自数组的值）\n  alert(surname); // Anonymous（默认值被使用了）\n  ```\n\n> **对象解构**\n\n```javascript\nlet {var1, var2} = {var1:…, var2:…}\n```\n\n变量的顺序并不重要，下面这个代码也奏效：\n\n```javascript\n// 改变 let {...} 中元素的顺序\nlet {height, width, title} = { title: \"Menu\", height: 200, width: 100 }\n```\n\n如果我们想把一个属性赋值给另一个名字的变量，比如把 `options.width` 属性赋值给名为 `w` 的变量，那么我们可以使用冒号来设置变量名称：\n\n```javascript\nlet options = {\n  title: \"Menu\",\n  width: 100,\n  height: 200\n};\n\n// { sourceProperty: targetVariable }\nlet {width: w, height: h, title} = options;\n\n// width -> w\n// height -> h\n// title -> title\n\nalert(title);  // Menu\nalert(w);      // 100\nalert(h);      // 200\n```\n\n冒号表示“什么值：赋值给谁”。上面的例子中，属性 `width` 被赋值给了 `w`，属性 `height` 被赋值给了 `h`，属性 `title` 被赋值给了同名变量。\n\n对于可能缺失的属性，我们可以使用 `\"=\"` 设置默认值，如下所示：\n\n```javascript\nlet options = {\n  title: \"Menu\"\n};\n\nlet {width = 100, height = 200, title} = options;\n\nalert(title);  // Menu\nalert(width);  // 100\nalert(height); // 200\n```\n\n就像数组或函数参数一样，默认值可以是任意表达式甚至可以是函数调用。它们只会在未提供对应的值时才会被计算/调用。\n\n**剩余模式（pattern）\"…\"**\n\n如果对象拥有的属性数量比我们提供的变量数量还多，该怎么办？我们可以只取其中的某一些属性，然后把“剩余的”赋值到其他地方吗？\n\n我们可以使用剩余模式（pattern），就像我们对数组那样。一些较旧的浏览器不支持此功能（例如 IE，可以使用 Babel 对其进行 polyfill），但可以在现代浏览器中使用。\n\n看起来就像这样：\n\n```javascript\nlet options = {\n  title: \"Menu\",\n  height: 200,\n  width: 100\n};\n\n// title = 名为 title 的属性\n// rest = 存有剩余属性的对象\nlet {title, ...rest} = options;\n\n// 现在 title=\"Menu\", rest={height: 200, width: 100}\nalert(rest.height);  // 200\nalert(rest.width);   // 100\n```\n\n#### JSON 方法\n\nJavaScript 提供了如下方法：\n\n- `JSON.stringify` 将对象转换为 JSON。\n\n  ~~~javascript\n  let json = JSON.stringify(value[, replacer, space])\n  ~~~\n\n  `value`：要编码的值。\n\n  `replacer`：要编码的属性数组或映射函数 `function(key, value)`。\n\n  `space`：用于格式化的空格数量\n\n  ~~~javascript\n  let room = {\n    number: 23\n  };\n\n  let meetup = {\n    title: \"Conference\",\n    participants: [{name: \"John\"}, {name: \"Alice\"}],\n    place: room // meetup 引用了 room\n  };\n\n  room.occupiedBy = meetup; // room 引用了 meetup\n\n  alert( JSON.stringify(meetup, [\'title\', \'participants\']) );\n  // {\"title\":\"Conference\",\"participants\":[{},{}]}\n  ~~~\n- `JSON.parse` 将 JSON 转换回对象。\n\n  ~~~javascript\n  let value = JSON.parse(str, [reviver]);\n  ~~~\n\n  `str`：要解析的 JSON 字符串。\n\n  `reviver`：可选的函数 function(key,value)，该函数将为每个 `(key, value)` 对调用，并可以对值进行转换。\n\n#### Rest 参数与 Spread 语法\n\n> **Rest 参数 `...`**\n\n在 JavaScript 中，无论函数是如何定义的，你都可以使用任意数量的参数调用函数。\n\n```javascript\nfunction sumAll(...args) { // 数组名为 args\n  let sum = 0;\n\n  for (let arg of args) sum += arg;\n\n  return sum;\n}\n\nalert( sumAll(1) ); // 1\nalert( sumAll(1, 2) ); // 3\nalert( sumAll(1, 2, 3) ); // 6\n```\n\n> **Spread 语法**\n\nSpread 语法只适用于可迭代对象。\n\n**Spread 语法** 来帮助你了！它看起来和 rest 参数很像，也使用 `...`，但是二者的用途完全相反。\n\n当在函数调用中使用 `...arr` 时，它会把可迭代对象 `arr` “展开”到参数列表中。\n\n以 `Math.max` 为例：\n\n```javascript\nlet arr = [3, 5, 1];\nalert( Math.max(...arr) ); // 5（spread 语法把数组转换为参数列表）\n\nlet obj = { a: 1, b: 2, c: 3 };\nlet objCopy = { ...obj }; // 将对象 spread 到参数列表中\n                          // 然后将结果返回到一个新对象\n```\n\n当我们在代码中看到 `\"...\"` 时，它要么是 rest 参数，要么就是 spread 语法。\n\n有一个简单的方法可以区分它们：\n\n- 若 `...` 出现在函数参数列表的最后，那么它就是 rest 参数，它会把参数列表中剩余的参数收集到一个数组中。\n- 若 `...` 出现在函数调用或类似的表达式中，那它就是 spread 语法，它会把一个数组展开为列表。\n\n使用场景：\n\n- Rest 参数用于创建可接受任意数量参数的函数。\n- Spread 语法用于将数组传递给通常需要含有许多参数的列表的函数。\n\n它们俩的出现帮助我们轻松地在列表和参数数组之间来回转换。\n\n“旧式”的 `arguments`（类数组且可迭代的对象）也依然能够帮助我们获取函数调用中的所有参数。\n\n#### 函数对象，NFE\n\n> **属性 “name”**\n\n函数对象包含一些便于使用的属性。\n\n比如，一个函数的名字可以通过属性 “name” 来访问：\n\n```javascript\nfunction sayHi() {\n  alert(\"Hi\");\n}\n\nalert(sayHi.name); // sayHi\n```\n\n> **属性 “length”**\n\n还有另一个内建属性 “length”，它返回函数入参的个数，比如：\n\n```javascript\nfunction f1(a) {}\nfunction f2(a, b) {}\nfunction many(a, b, ...more) {}\n\nalert(f1.length); // 1\nalert(f2.length); // 2\nalert(many.length); // 2\n```\n\n可以看到，rest 参数不参与计数。\n\n> **自定义属性**\n\n我们也可以添加我们自己的属性。\n\n这里我们添加了 `counter` 属性，用来跟踪总的调用次数：\n\n```javascript\nfunction sayHi() {\n  alert(\"Hi\");\n\n  // 计算调用次数\n  sayHi.counter++;\n}\nsayHi.counter = 0; // 初始值\n\nsayHi(); // Hi\nsayHi(); // Hi\n\nalert( `Called ${sayHi.counter} times` ); // Called 2 times\n```\n\n#### 装饰器模式和转发，call/apply\n\n- **使用 “func.call” 设定上下文**\n\n  不适用于对象方法。\n\n  ~~~javascript\n  func.call(context, arg1, arg2, ...)\n  ~~~\n\n  它运行 `func`，提供的第一个参数作为 `this`，后面的作为参数（arguments）\n\n  ~~~javascript\n  function sayHi() {\n    alert(this.name);\n  }\n\n  let user = { name: \"John\" };\n  let admin = { name: \"Admin\" };\n\n  // 使用 call 将不同的对象传递为 \"this\"\n  sayHi.call( user ); // John\n  sayHi.call( admin ); // Admin\n  ~~~\n- **func.apply**\n\n  我们可以使用 `func.apply(this, arguments)` 代替 `func.call(this, ...arguments)`。\n\n  ~~~javascript\n  func.apply(context, args)\n  ~~~\n\n  它运行 `func` 设置 `this=context`，并使用类数组对象 `args` 作为参数列表（arguments）。\n\n  - `call` 和 `apply` 之间唯一的语法区别是，`call` 期望一个参数列表，而 `apply` 期望一个包含这些参数的类数组对象。\n- `apply` 只接受 **类数组**。\n\n#### 函数绑定\n\n函数提供了一个内建方法 `bind`，它可以绑定 `this`。\n\n基本的语法是：\n\n```javascript\n// 稍后将会有更复杂的语法\nlet boundFunc = func.bind(context);\n```\n\n`func.bind(context)` 的结果是一个特殊的类似于函数的“外来对象（exotic object）”，它可以像函数一样被调用，并且透明地（transparently）将调用传递给 `func` 并设定 `this=context`。\n\n我们不仅可以绑定 `this`，还可以绑定参数（arguments）。虽然很少这么做，但有时它可以派上用场。\n\n`bind` 的完整语法如下：\n\n```javascript\nlet bound = func.bind(context, [arg1], [arg2], ...);\n```\n\n```javascript\nfunction mul(a, b) {\n  return a * b;\n}\n\nlet double = mul.bind(null, 2);\n\nalert( double(3) ); // = mul(2, 3) = 6\nalert( double(4) ); // = mul(2, 4) = 8\nalert( double(5) ); // = mul(2, 5) = 10\n```\n\n对 `mul.bind(null, 2)` 的调用创建了一个新函数 `double`，它将调用传递到 `mul`，将 `null` 绑定为上下文，并将 `2` 绑定为第一个参数。并且，参数（arguments）均被“原样”传递。\n\n#### 属性标志和属性描述符\n\n**属性标志**：\n\n对象属性（properties），除 **`value`** 外，还有三个特殊的特性（attributes），也就是所谓的“标志”：\n\n- **`writable`** — 如果为 `true`，则值可以被修改，否则它是只可读的。\n- **`enumerable`** — 如果为 `true`，则会被在循环中列出，否则不会被列出。\n- **`configurable`** — 如果为 `true`，则此属性可以被删除，这些特性也可以被修改，否则不可以。\n\n当我们用“常用的方式”创建一个属性时，它们都为 `true`。但我们也可以随时更改它们。\n\n首先，让我们来看看如何获得这些标志。\n\n`Object.getOwnPropertyDescriptor` 方法允许查询有关属性的 **完整** 信息。\n\n语法是：\n\n```javascript\nlet descriptor = Object.getOwnPropertyDescriptor(obj, propertyName);\n```\n\n- `obj`\n\n  需要从中获取信息的对象。\n- `propertyName`\n\n  属性的名称。\n\n返回值是一个所谓的“属性描述符”对象：它包含值和所有的标志。\n\n例如：\n\n```javascript\nlet user = {\n  name: \"John\"\n};\n\nlet descriptor = Object.getOwnPropertyDescriptor(user, \'name\');\n```\n\n为了修改标志，我们可以使用 `Object.defineProperty`\n\n语法是：\n\n```javascript\nObject.defineProperty(obj, propertyName, descriptor)\n```\n\n- `obj`，`propertyName`\n\n  要应用描述符的对象及其属性。\n- `descriptor`\n\n  要应用的属性描述符对象。\n\n如果该属性存在，`defineProperty` 会更新其标志。否则，它会使用给定的值和标志创建属性；在这种情况下，如果没有提供标志，则会假定它是 `false`。\n\n例如，这里创建了一个属性 `name`，该属性的所有标志都为 `false`：\n\n```javascript\nlet user = {};\n\nObject.defineProperty(user, \"name\", {\n  value: \"John\"\n});\n\nlet descriptor = Object.getOwnPropertyDescriptor(user, \'name\');\n\nalert( JSON.stringify(descriptor, null, 2 ) );\n/*\n{\n  \"value\": \"John\",\n  \"writable\": false,\n  \"enumerable\": false,\n  \"configurable\": false\n}\n */\n```\n\n**设定一个全局的密封对象**：\n\n属性描述符在单个属性的级别上工作。\n\n还有一些限制访问 **整个** 对象的方法：\n\n- [Object.preventExtensions(obj)](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Object/preventExtensions)\n\n  禁止向对象添加新属性。\n- [Object.seal(obj)](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Object/seal)\n\n  禁止添加/删除属性。为所有现有的属性设置 `configurable: false`。\n- [Object.freeze(obj)](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Object/freeze)\n\n  禁止添加/删除/更改属性。为所有现有的属性设置 `configurable: false, writable: false`。\n- [Object.isExtensible(obj)](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Object/isExtensible)\n\n  如果添加属性被禁止，则返回 `false`，否则返回 `true`。\n- [Object.isSealed(obj)](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Object/isSealed)\n\n  如果添加/删除属性被禁止，并且所有现有的属性都具有 `configurable: false`则返回 `true`。\n- [Object.isFrozen(obj)](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Object/isFrozen)\n\n  如果添加/删除/更改属性被禁止，并且所有当前属性都是 `configurable: false, writable: false`，则返回 `true`。\n\n---\n\n#### 属性的 getter 和 setter\n\n**getter 和 setter**：\n\n访问器属性由 “getter” 和 “setter” 方法表示。在对象字面量中，它们用 `get` 和 `set` 表示：\n\n```javascript\nlet obj = {\n  get propName() {\n    // 当读取 obj.propName 时，getter 起作用\n  },\n\n  set propName(value) {\n    // 当执行 obj.propName = value 操作时，setter 起作用\n  }\n};\n```\n\n当读取 `obj.propName` 时，getter 起作用，当 `obj.propName` 被赋值时，setter 起作用。\n\n**访问器描述符**：\n\n访问器属性的描述符与数据属性的不同。\n\n对于访问器属性，没有 `value` 和 `writable`，但是有 `get` 和 `set` 函数。\n\n所以访问器描述符可能有：\n\n- **`get`** —— 一个没有参数的函数，在读取属性时工作，\n- **`set`** —— 带有一个参数的函数，当属性被设置时调用，\n- **`enumerable`** —— 与数据属性的相同，\n- **`configurable`** —— 与数据属性的相同。\n\n例如，要使用 `defineProperty` 创建一个 `fullName` 访问器，我们可以使用 `get` 和 `set` 来传递描述符：\n\n```javascript\nlet user = {\n  name: \"John\",\n  surname: \"Smith\"\n};\n\nObject.defineProperty(user, \'fullName\', {\n  get() {\n    return `${this.name} ${this.surname}`;\n  },\n\n  set(value) {\n    [this.name, this.surname] = value.split(\" \");\n  }\n});\n\nalert(user.fullName); // John Smith\n\nfor(let key in user) alert(key); // name, surname\n```\n\n请注意，一个属性要么是访问器（具有 `get/set` 方法），要么是数据属性（具有 `value`），但不能两者都是。\n\n如果我们试图在同一个描述符中同时提供 `get` 和 `value`，则会出现错误：\n\n```javascript\n// Error: Invalid property descriptor.\nObject.defineProperty({}, \'prop\', {\n  get() {\n    return 1\n  },\n\n  value: 2\n});\n```\n\n---\n\n#### 原型继承\n\n例如，我们有一个 `user` 对象及其属性和方法，并希望将 `admin` 和 `guest` 作为基于 `user` 稍加修改的变体。我们想重用 `user` 中的内容，而不是复制/重新实现它的方法，而只是在其之上构建一个新的对象。\n\n**原型继承（Prototypal inheritance）** 这个语言特性能够帮助我们实现这一需求。\n\n在 JavaScript 中，对象有一个特殊的隐藏属性 `[[Prototype]]`（如规范中所命名的），它要么为 `null`，要么就是对另一个对象的引用。该对象被称为“原型”\n\n属性 `[[Prototype]]` 是内部的而且是隐藏的，但是这儿有很多设置它的方式。\n\n其中之一就是使用特殊的名字 `__proto__`，就像这样：\n\n```javascript\nlet animal = {\n  eats: true\n};\nlet rabbit = {\n  jumps: true\n};\n\nrabbit.__proto__ = animal; // 设置 rabbit.[[Prototype]] = animal\n```\n\n因此，如果 `animal` 有许多有用的属性和方法，那么它们将自动地变为在 `rabbit` 中可用。这种属性被称为“继承”。\n\n如果我们在 `animal` 中有一个方法，它可以在 `rabbit` 中被调用：\n\n```javascript\nlet animal = {\n  eats: true,\n  walk() {\n    alert(\"Animal walk\");\n  }\n};\n\nlet rabbit = {\n  jumps: true,\n  __proto__: animal\n};\n\n// walk 方法是从原型中获得的\nrabbit.walk(); // Animal walk\n```\n\n这里只有两个限制：\n\n1. 引用不能形成闭环。如果我们试图在一个闭环中分配 `__proto__`，JavaScript 会抛出错误。\n2. `__proto__` 的值可以是对象，也可以是 `null`。而其他的类型都会被忽略。\n\n当然，这可能很显而易见，但是仍然要强调：只能有一个 `[[Prototype]]`。一个对象不能从其他两个对象获得继承。\n\n**`__proto__` 是 `[[Prototype]]` 的因历史原因而留下来的 getter/setter**\n\n初学者常犯一个普遍的错误，就是不知道 `__proto__` 和 `[[Prototype]]` 的区别。\n\n请注意，`__proto__` 与内部的 `[[Prototype]]` **不一样**。`__proto__` 是 `[[Prototype]]` 的 getter/setter。`__proto__` 属性有点过时了。它的存在是出于历史的原因，现代编程语言建议我们应该使用函数`Object.getPrototypeOf/Object.setPrototypeOf` 来取代 `__proto__` 去 get/set 原型。稍后我们将介绍这些函数。\n\n根据规范，`__proto__` 必须仅受浏览器环境的支持。但实际上，包括服务端在内的所有环境都支持它，因此我们使用它是非常安全的。\n\n- 原型仅用于读取属性。\n- 对于写入/删除操作可以直接在对象上进行。\n\n访问器（accessor）属性是一个例外，因为分配（assignment）操作是由 setter 函数处理的。因此，写入此类属性实际上与调用函数相同。\n\n**“this” 的值**：\n\n在上面的例子中可能会出现一个有趣的问题：在 `set fullName(value)` 中 `this` 的值是什么？属性 `this.name` 和 `this.surname` 被写在哪里：在 `user` 还是 `admin`？\n\n答案很简单：`this` 根本不受原型的影响。\n\n**无论在哪里找到方法：在一个对象还是在原型中。在一个方法调用中，`this` 始终是点符号 `.` 前面的对象。**\n\n因此，setter 调用 `admin.fullName=` 使用 `admin` 作为 `this`，而不是 `user`。\n\n这是一件非常重要的事儿，因为我们可能有一个带有很多方法的大对象，并且还有从其继承的对象。当继承的对象运行继承的方法时，它们将仅修改自己的状态，而不会修改大对象的状态。\n\n例如，这里的 `animal` 代表“方法存储”，`rabbit` 在使用其中的方法。\n\n调用 `rabbit.sleep()` 会在 `rabbit` 对象上设置 `this.isSleeping`：\n\n```javascript\n// animal 有一些方法\nlet animal = {\n  walk() {\n    if (!this.isSleeping) {\n      alert(`I walk`);\n    }\n  },\n  sleep() {\n    this.isSleeping = true;\n  }\n};\n\nlet rabbit = {\n  name: \"White Rabbit\",\n  __proto__: animal\n};\n\n// 修改 rabbit.isSleeping\nrabbit.sleep();\n\nalert(rabbit.isSleeping); // true\nalert(animal.isSleeping); // undefined（原型中没有此属性）\n```\n\n`for..in` 循环也会迭代继承的属性。\n\n例如：\n\n```javascript\nlet animal = {\n  eats: true\n};\n\nlet rabbit = {\n  jumps: true,\n  __proto__: animal\n};\n\n// Object.keys 只返回自己的 key\nalert(Object.keys(rabbit)); // jumps\n\n// for..in 会遍历自己以及继承的键\nfor(let prop in rabbit) alert(prop); // jumps，然后是 eats\n```\n\n如果这不是我们想要的，并且我们想排除继承的属性，那么这儿有一个内建方法`obj.hasOwnProperty(key)`：如果 `obj` 具有自己的（非继承的）名为 `key` 的属性，则返回 `true`。\n\n---\n\n#### F.prototype\n\n我们还记得，可以使用诸如 `new F()` 这样的构造函数来创建一个新对象。\n\n如果 `F.prototype` 是一个对象，那么 `new` 操作符会使用它为新对象设置 `[[Prototype]]`。\n\n下面是一个例子：\n\n```javascript\nlet animal = {\n  eats: true\n};\n\nfunction Rabbit(name) {\n  this.name = name;\n}\n\nRabbit.prototype = animal;\n\nlet rabbit = new Rabbit(\"White Rabbit\"); //  rabbit.__proto__ == animal\n\nalert( rabbit.eats ); // true\n```\n\n设置 `Rabbit.prototype = animal` 的字面意思是：“当创建了一个 `new Rabbit` 时，把它的 `[[Prototype]]` 赋值为 `animal`”。\n\n**`F.prototype` 仅用在 `new F` 时**\n\n`F.prototype` 属性仅在 `new F` 被调用时使用，它为新对象的 `[[Prototype]]` 赋值。\n\n如果在创建之后，`F.prototype` 属性有了变化（`F.prototype = <another object>`），那么通过 `new F` 创建的新对象也将随之拥有新的对象作为 `[[Prototype]]`，但已经存在的对象将保持旧有的值。\n\n**默认的 F.prototype，构造器属性**\n\n每个函数都有 `\"prototype\"` 属性，即使我们没有提供它。\n\n默认的 `\"prototype\"` 是一个只有属性 `constructor` 的对象，属性 `constructor` 指向函数自身。\n\n**……JavaScript 自身并不能确保正确的 `\"constructor\"` 函数值。**\n\n是的，它存在于函数的默认 `\"prototype\"` 中，但仅此而已。之后会发生什么 —— 完全取决于我们。\n\n特别是，如果我们将整个默认 prototype 替换掉，那么其中就不会有 `\"constructor\"` 了。\n\n例如：\n\n```javascript\nfunction Rabbit() {}\nRabbit.prototype = {\n  jumps: true\n};\n\nlet rabbit = new Rabbit();\nalert(rabbit.constructor === Rabbit); // false\n```\n\n因此，为了确保正确的 `\"constructor\"`，我们可以选择添加/删除属性到默认 `\"prototype\"`，而不是将其整个覆盖：\n\n```javascript\nfunction Rabbit() {}\n\n// 不要将 Rabbit.prototype 整个覆盖\n// 可以向其中添加内容\nRabbit.prototype.jumps = true\n// 默认的 Rabbit.prototype.constructor 被保留了下来\n```\n\n或者，也可以手动重新创建 `constructor` 属性：\n\n```javascript\nRabbit.prototype = {\n  jumps: true,\n  constructor: Rabbit\n};\n\n// 这样的 constructor 也是正确的，因为我们手动添加了它\n```\n\n**总结**：\n\n在本章中，我们简要介绍了为通过构造函数创建的对象设置 `[[Prototype]]` 的方法。稍后我们将看到更多依赖于此的高级编程模式。\n\n一切都很简单，只需要记住几条重点就可以清晰地掌握了：\n\n- `F.prototype` 属性（不要把它与 `[[Prototype]]` 弄混了）在 `new F` 被调用时为新对象的 `[[Prototype]]` 赋值。\n- `F.prototype` 的值要么是一个对象，要么就是 `null`：其他值都不起作用。\n- `\"prototype\"` 属性仅在设置了一个构造函数（constructor function），并通过 `new` 调用时，才具有这种特殊的影响。\n\n---\n\n#### 原型方法，没有 __proto__ 的对象\n\n`__proto__` 被认为是过时且不推荐使用的（deprecated），这里的不推荐使用是指 JavaScript 规范中规定，**proto** 必须仅在浏览器环境下才能得到支持。\n\n现代的方法有：\n\n- [Object.create(proto, descriptors\\])](https://developer.mozilla.org/zh/docs/Web/JavaScript/Reference/Global_Objects/Object/create) —— 利用给定的 `proto` 作为 `[[Prototype]]` 和可选的属性描述来创建一个空对象。\n- [Object.getPrototypeOf(obj)](https://developer.mozilla.org/zh/docs/Web/JavaScript/Reference/Global_Objects/Object/getPrototypeOf) —— 返回对象 `obj` 的 `[[Prototype]]`。\n- [Object.setPrototypeOf(obj, proto)](https://developer.mozilla.org/zh/docs/Web/JavaScript/Reference/Global_Objects/Object/setPrototypeOf) —— 将对象 `obj` 的 `[[Prototype]]` 设置为 `proto`。\n\n应该使用这些方法来代替 `__proto__`。\n\n其他方法：\n\n- [Object.keys(obj)](https://developer.mozilla.org/zh/docs/Web/JavaScript/Reference/Global_Objects/Object/keys) / [Object.values(obj)](https://developer.mozilla.org/zh/docs/Web/JavaScript/Reference/Global_Objects/Object/values) / [Object.entries(obj)](https://developer.mozilla.org/zh/docs/Web/JavaScript/Reference/Global_Objects/Object/entries) —— 返回一个可枚举的由自身的字符串属性名/值/键值对组成的数组。\n- [Object.getOwnPropertySymbols(obj)](https://developer.mozilla.org/zh/docs/Web/JavaScript/Reference/Global_Objects/Object/getOwnPropertySymbols) —— 返回一个由自身所有的 symbol 类型的键组成的数组。\n- [Object.getOwnPropertyNames(obj)](https://developer.mozilla.org/zh/docs/Web/JavaScript/Reference/Global_Objects/Object/getOwnPropertyNames) —— 返回一个由自身所有的字符串键组成的数组。\n- [Reflect.ownKeys(obj)](https://developer.mozilla.org/zh/docs/Web/JavaScript/Reference/Global_Objects/Reflect/ownKeys) —— 返回一个由自身所有键组成的数组。\n- [obj.hasOwnProperty(key)](https://developer.mozilla.org/zh/docs/Web/JavaScript/Reference/Global_Objects/Object/hasOwnProperty)：如果 `obj` 拥有名为 `key` 的自身的属性（非继承而来的），则返回 `true`。\n\n---\n\n## Promise，async/await\n\n#### Promise\n\nPromise 对象的构造器（constructor）语法如下：\n\n```javascript\nlet promise = new Promise(function(resolve, reject) {\n  // executor（生产者代码，“歌手”）\n});\n```\n\n传递给 `new Promise` 的函数被称为 **executor**。当 `new Promise` 被创建，executor 会自动运行。它包含最终应产出结果的生产者代码。按照上面的类比：executor 就是“歌手”。\n\n它的参数 `resolve` 和 `reject` 是由 JavaScript 自身提供的回调。我们的代码仅在 executor 的内部。\n\n当 executor 获得了结果，无论是早还是晚都没关系，它应该调用以下回调之一：\n\n- `resolve(value)` —— 如果任务成功完成并带有结果 `value`。\n- `reject(error)` —— 如果出现了 error，`error` 即为 error 对象。\n\n所以总结一下就是：executor 会自动运行并尝试执行一项工作。尝试结束后，如果成功则调用 `resolve`，如果出现 error 则调用 `reject`。\n\n由 `new Promise` 构造器返回的 `promise` 对象具有以下内部属性：\n\n- `state` —— 最初是 `\"pending\"`，然后在 `resolve` 被调用时变为 `\"fulfilled\"`，或者在 `reject` 被调用时变为 `\"rejected\"`。\n- `result` —— 最初是 `undefined`，然后在 `resolve(value)` 被调用时变为 `value`，或者在 `reject(error)` 被调用时变为 `error`。\n\n**`state` 和 `result` 都是内部的**\n\nPromise 对象的 `state` 和 `result` 属性都是内部的。我们无法直接访问它们。但我们可以对它们使用 `.then`/`.catch`/`.finally` 方法。我们在下面对这些方法进行了描述。\n\n---\n\n#### then，catch，finally\n\n如果 `.then`（或 `catch/finally` 都可以）处理程序（handler）返回一个 promise，那么链的其余部分将会等待，直到它状态变为 settled。当它被 settled 后，其 result（或 error）将被进一步传递下去。\n\nPromise 的处理程序（handlers）`.then`、`.catch` 和 `.finally` 都是异步的。\n\n> **then**\n\n最重要最基础的一个就是 `.then`。\n\n语法如下：\n\n```javascript\npromise.then(\n  function(result) { /* handle a successful result */ },\n  function(error) { /* handle an error */ }\n);\n```\n\n`.then` 的第一个参数是一个函数，该函数将在 promise resolved 后运行并接收结果。\n\n`.then` 的第二个参数也是一个函数，该函数将在 promise rejected 后运行并接收 error。\n\n> **catch**\n\n如果我们只对 error 感兴趣，那么我们可以使用 `null` 作为第一个参数：`.then(null, errorHandlingFunction)`。或者我们也可以使用 `.catch(errorHandlingFunction)`，其实是一样的：\n\n```javascript\nlet promise = new Promise((resolve, reject) => {\n  setTimeout(() => reject(new Error(\"Whoops!\")), 1000);\n});\n\n// .catch(f) 与 promise.then(null, f) 一样\npromise.catch(alert); // 1 秒后显示 \"Error: Whoops!\"\n```\n\n`.catch(f)` 调用是 `.then(null, f)` 的完全的模拟，它只是一个简写形式。\n\n> **finally**\n\n就像常规 `try {...} catch {...}` 中的 `finally` 子句一样，promise 中也有 `finally`。\n\n`.finally(f)` 调用与 `.then(f, f)` 类似，在某种意义上，`f` 总是在 promise 被 settled 时运行：即 promise 被 resolve 或 reject。\n\n`finally` 是执行清理（cleanup）的很好的处理程序（handler），例如无论结果如何，都停止使用不再需要的加载指示符（indicator）。\n\n像这样：\n\n```javascript\nnew Promise((resolve, reject) => {\n  /* 做一些需要时间的事儿，然后调用 resolve/reject */\n})\n  // 在 promise 为 settled 时运行，无论成功与否\n  .finally(() => stop loading indicator)\n  // 所以，加载指示器（loading indicator）始终会在我们处理结果/错误之前停止\n  .then(result => show result, err => show error)\n```\n\n也就是说，`finally(f)` 其实并不是 `then(f,f)` 的别名。它们之间有一些细微的区别：\n\n1. `finally` 处理程序（handler）没有参数。在 `finally` 中，我们不知道 promise 是否成功。没关系，因为我们的任务通常是执行“常规”的定稿程序（finalizing procedures）。\n2. `finally` 处理程序将结果和 error 传递给下一个处理程序。\n\n---\n\n#### Promise API\n\n> **Promise.all**\n\n假设我们希望并行执行多个 promise，并等待所有 promise 都准备就绪。\n\n例如，并行下载几个 URL，并等到所有内容都下载完毕后再对它们进行处理。\n\n这就是 `Promise.all` 的用途。\n\n语法：\n\n```javascript\nlet promise = Promise.all(iterable);\n```\n\n`Promise.all` 接受一个可迭代对象（通常是一个数组项为 promise 的数组），并返回一个新的 promise。\n\n当所有给定的 promise 都 resolve 时，新的 promise 才会 resolve，并且其结果数组将成为新 promise 的结果。\n\n如果promise 的数组其中一个 promise 被 reject，`Promise.all` 就会立即被 reject，完全忽略列表中其他的 promise。它们的结果也被忽略。\n\n> **Promise.allSettled**\n\n`Promise.allSettled` 等待所有的 promise 都被 settle，无论结果如何。结果数组具有：\n\n- `{status:\"fulfilled\", value:result}` 对于成功的响应，\n- `{status:\"rejected\", reason:error}` 对于 error。\n\n> **Promise.race**\n\n与 `Promise.all` 类似，但只等待第一个 settled 的 promise 并获取其结果（或 error）。\n\n例如，这里的结果将是 `1`：\n\n```javascript\nPromise.race([\n  new Promise((resolve, reject) => setTimeout(() => resolve(1), 1000)),\n  new Promise((resolve, reject) => setTimeout(() => reject(new Error(\"Whoops!\")), 2000)),\n  new Promise((resolve, reject) => setTimeout(() => resolve(3), 3000))\n]).then(alert); // 1\n```\n\n这里第一个 promise 最快，所以它变成了结果。第一个 settled 的 promise “赢得了比赛”之后，所有进一步的 result/error 都会被忽略。\n\n> **Promise.any**\n\n与 `Promise.race` 类似，区别在于 `Promise.any` 只等待第一个 fulfilled 的 promise，并将这个 fulfilled 的 promise 返回。如果给出的 promise 都 rejected，那么则返回 rejected 的 promise 和 [`AggregateError`](https://developer.mozilla.org/zh/docs/Web/JavaScript/Reference/Global_Objects/AggregateError) 错误类型的 error 实例。\n\n**总结**：\n\n`Promise` 类有 6 种静态方法：\n\n1. `Promise.all(promises)` —— 等待所有 promise 都 resolve 时，返回存放它们结果的数组。如果给定的任意一个 promise 为 reject，那么它就会变成 `Promise.all` 的 error，所有其他 promise 的结果都会被忽略。\n2. `Promise.allSettled(promises)`（ES2020 新增方法）—— 等待所有 promise 都 settle 时，并以包含以下内容的对象数组的形式返回它们的结果：\n   - `status`: `\"fulfilled\"` 或 `\"rejected\"`\n   - `value`（如果 fulfilled）或 `reason`（如果 rejected）。\n3. `Promise.race(promises)` —— 等待第一个 settle 的 promise，并将其 result/error 作为结果返回。\n4. `Promise.any(promises)`（ES2021 新增方法）—— 等待第一个 fulfilled 的 promise，并将其结果作为结果返回。如果所有 promise 都 rejected，`Promise.any` 则会抛出 [`AggregateError`](https://developer.mozilla.org/zh/docs/Web/JavaScript/Reference/Global_Objects/AggregateError) 错误类型的 error 实例。\n5. `Promise.resolve(value)` —— 使用给定 value 创建一个 resolved 的 promise。\n6. `Promise.reject(error)` —— 使用给定 error 创建一个 rejected 的 promise。\n\n以上所有方法，`Promise.all` 可能是在实战中使用最多的。\n\n---\n\n#### Async/await\n\nAsync/await 是以更舒适的方式使用 promise 的一种特殊语法，同时它也非常易于理解和使用。\n\n**Async function**:\n\n让我们以 `async` 这个关键字开始。它可以被放置在一个函数前面，如下所示：\n\n```javascript\nasync function f() {\n  return 1;\n}\n```\n\n在函数前面的 “async” 这个单词表达了一个简单的事情：即这个函数总是返回一个 promise。其他值将自动被包装在一个 resolved 的 promise 中。\n\n**Await**：\n\n语法如下：\n\n```javascript\n// 只在 async 函数内工作\nlet value = await promise;\n```\n\n关键字 `await` 让 JavaScript 引擎等待直到 promise 完成（settle）并返回结果。\n\n`await` 实际上会暂停函数的执行，直到 promise 状态变为 settled，然后以 promise 的结果继续执行。这个行为不会耗费任何 CPU 资源，因为 JavaScript 引擎可以同时处理其他任务：执行其他脚本，处理事件等。\n\n**不能在普通函数中使用** `await`\n\n**在现代浏览器中，当我们处于一个 module 中时，那么在顶层使用 `await` 也是被允许的。**\n\n---\n\n## Generator，高级 iteration\n\n#### Generator\n\n而 Generator 可以按需一个接一个地返回（“yield”）多个值。它们可与 [iterable](https://zh.javascript.info/iterable) 完美配合使用，从而可以轻松地创建数据流。\n\n**Generator 函数**：\n\n要创建一个 generator，我们需要一个特殊的语法结构：`function*`，即所谓的 “generator function”。\n\n它看起来像这样：\n\n```javascript\nfunction* generateSequence() {\n  yield 1;\n  yield 2;\n  return 3;\n}\n```\n\nGenerator 函数与常规函数的行为不同。在此类函数被调用时，它不会运行其代码。而是返回一个被称为 “generator object” 的特殊对象，来管理执行流程。\n\n一个 generator 的主要方法就是 `next()`\n\n`next()` 的结果始终是一个具有两个属性的对象：\n\n- `value`: 产出的（yielded）的值。\n- `done`: 如果 generator 函数已执行完成则为 `true`，否则为 `false`。\n\ngenerator 是 `可迭代`的\n\n我们可以使用 `for..of` 循环遍历它所有的值：\n\n```javascript\nfunction* generateSequence() {\n  yield 1;\n  yield 2;\n  return 3;\n}\n\nlet generator = generateSequence();\n\nfor(let value of generator) {\n  alert(value); // 1，然后是 2\n}\n```\n\n---\n\n## 模块\n\n模块可以相互加载，并可以使用特殊的指令 `export` 和 `import` 来交换功能，从另一个模块调用一个模块的函数：\n\n- `export` 关键字标记了可以从当前模块外部访问的变量和函数。\n- `import` 关键字允许从其他模块导入功能。\n\n由于模块支持特殊的关键字和功能，因此我们必须通过使用 `<script type=\"module\">` 特性（attribute）来告诉浏览器，此脚本应该被当作模块（module）来对待。\n\n**模块只通过 HTTP(s) 工作，而非本地**\n\n如果你尝试通过 `file://` 协议在本地打开一个网页，你会发现 `import/export` 指令不起作用。你可以使用本地 Web 服务器，例如 [static-server](https://www.npmjs.com/package/static-server#getting-started)，或者使用编辑器的“实时服务器”功能，例如 VS Code 的 [Live Server Extension](https://marketplace.visualstudio.com/items?itemName=ritwickdey.LiveServer) 来测试模块。\n\n如果同一个模块被导入到多个其他位置，那么它的代码只会执行一次，即在第一次被导入时。然后将其导出（export）的内容提供给进一步的导入（importer）。\n\n如果这个模块被导入到多个文件中，模块仅在第一次被导入时被解析，并创建 `admin` 对象，然后将其传入到所有的导入。\n\n所有的导入都只获得了一个唯一的 `admin` 对象：\n\n```javascript\n// ? 1.js\nimport { admin } from \'./admin.js\';\nadmin.name = \"Pete\";\n\n// ? 2.js\nimport { admin } from \'./admin.js\';\nalert(admin.name); // Pete\n\n// 1.js 和 2.js 引用的是同一个 admin 对象\n// 在 1.js 中对对象做的更改，在 2.js 中也是可见的\n```\n\n正如你所看到的，当在 `1.js` 中修改了导入的 `admin` 中的 `name` 属性时，我们在 `2.js` 中可以看到新的 `admin.name`。\n\n这正是因为该模块只执行了一次。生成导出，然后这些导出在导入之间共享，因此如果更改了 `admin` 对象，在其他导入中也会看到。\n\n`import.meta` 对象包含关于当前模块的信息。\n\n它的内容取决于其所在的环境。在浏览器环境中，它包含当前脚本的 URL，或者如果它是在 HTML 中的话，则包含当前页面的 URL。\n\n```markup\n<script type=\"module\">\n  alert(import.meta.url); // 脚本的 URL（对于内联脚本来说，则是当前 HTML 页面的 URL）\n</script>\n```\n\n在一个模块中，顶级 `this` 是 undefined。\n\n将其与非模块脚本进行比较会发现，非模块脚本的顶级 `this` 是全局对象：\n\n```markup\n<script>\n  alert(this); // window\n</script>\n\n<script type=\"module\">\n  alert(this); // undefined\n</script>\n```\n\n`async`对于模块脚本，它也适用于内联脚本。\n\n例如，下面的内联脚本具有 `async` 特性，因此它不会等待任何东西。\n\n```javascript\n<!-- 所有依赖都获取完成（analytics.js）然后脚本开始运行 -->\n<!-- 不会等待 HTML 文档或者其他 <script> 标签 -->\n<script async type=\"module\">\n  import {counter} from \'./analytics.js\';\n\n  counter.count();\n</script>\n```\n\n#### 导出和导入\n\n导出（export）和导入（import）指令有几种语法变体。\n\n请记住，`import` 命名的导出时需要花括号，而 `import` 默认的导出时不需要花括号。\n\n你可以阅读并回忆它们的含义来进行自查：\n\n- 在声明一个 class/function/… 之前：\n  - `export [default] class/function/variable ...`\n- 独立的导出：\n  - `export {x [as y], ...}`.\n- 重新导出：\n  - `export {x [as y], ...} from \"module\"`\n  - `export * from \"module\"`（不会重新导出默认的导出）。\n  - `export {default [as y]} from \"module\"`（重新导出默认的导出）。\n\n导入：\n\n- 导入命名的导出：\n  - `import {x [as y], ...} from \"module\"`\n- 导入默认的导出：\n  - `import x from \"module\"`\n  - `import {default as x} from \"module\"`\n- 导入所有：\n  - `import * as obj from \"module\"`\n- 导入模块（其代码，并运行），但不要将其任何导出赋值给变量：\n  - `import \"module\"`\n\n---\n\n#### 动态导入\n\n**import() 表达式**：\n\n`import(module)` 表达式加载模块并返回一个 promise，该 promise resolve 为一个包含其所有导出的模块对象。我们可以在代码中的任意位置调用这个表达式。\n\n我们可以在代码中的任意位置动态地使用它。例如：\n\n```javascript\nlet modulePath = prompt(\"Which module to load?\");\n\nimport(modulePath)\n  .then(obj => <module object>)\n  .catch(err => <loading error, e.g. if no such module>)\n```\n\n或者，如果在异步函数中，我们可以使用 `let module = await import(modulePath)`。\n\n---\n\n## 杂项\n\n#### Proxy 和 Reflect\n\n**Proxy**：\n\n代理提供了一种独特的方法，可以在最底层更改或调整现有对象的行为。但是，它并不完美。有局限性。\n\n代理和原始对象是不同的对象。\n\n语法：\n\n```javascript\nlet proxy = new Proxy(target, handler)\n```\n\n- `target` —— 是要包装的对象，可以是任何东西，包括函数。\n- `handler` —— 代理配置：带有“捕捉器”（“traps”，即拦截操作的方法）的对象。比如 `get` 捕捉器用于读取 `target` 的属性，`set` 捕捉器用于写入 `target` 的属性，等等。\n\n\n  | 内部方法                | Handler 方法               | 何时触发                                                                                                                                                                                                                                                                                                                    |\n  | :---------------------- | :------------------------- | :-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |\n  | `[[Get]]`               | `get`                      | 读取属性                                                                                                                                                                                                                                                                                                                    |\n  | `[[Set]]`               | `set`                      | 写入属性                                                                                                                                                                                                                                                                                                                    |\n  | `[[HasProperty]]`       | `has`                      | `in` 操作符                                                                                                                                                                                                                                                                                                                 |\n  | `[[Delete]]`            | `deleteProperty`           | `delete` 操作符                                                                                                                                                                                                                                                                                                             |\n  | `[[Call]]`              | `apply`                    | 函数调用                                                                                                                                                                                                                                                                                                                    |\n  | `[[Construct]]`         | `construct`                | `new` 操作符                                                                                                                                                                                                                                                                                                                |\n  | `[[GetPrototypeOf]]`    | `getPrototypeOf`           | [Object.getPrototypeOf](https://developer.mozilla.org/zh/docs/Web/JavaScript/Reference/Global_Objects/Object/getPrototypeOf)                                                                                                                                                                                                |\n  | `[[SetPrototypeOf]]`    | `setPrototypeOf`           | [Object.setPrototypeOf](https://developer.mozilla.org/zh/docs/Web/JavaScript/Reference/Global_Objects/Object/setPrototypeOf)                                                                                                                                                                                                |\n  | `[[IsExtensible]]`      | `isExtensible`             | [Object.isExtensible](https://developer.mozilla.org/zh/docs/Web/JavaScript/Reference/Global_Objects/Object/isExtensible)                                                                                                                                                                                                    |\n  | `[[PreventExtensions]]` | `preventExtensions`        | [Object.preventExtensions](https://developer.mozilla.org/zh/docs/Web/JavaScript/Reference/Global_Objects/Object/preventExtensions)                                                                                                                                                                                          |\n  | `[[DefineOwnProperty]]` | `defineProperty`           | [Object.defineProperty](https://developer.mozilla.org/zh/docs/Web/JavaScript/Reference/Global_Objects/Object/defineProperty), [Object.defineProperties](https://developer.mozilla.org/zh/docs/Web/JavaScript/Reference/Global_Objects/Object/defineProperties)                                                              |\n  | `[[GetOwnProperty]]`    | `getOwnPropertyDescriptor` | [Object.getOwnPropertyDescriptor](https://developer.mozilla.org/zh/docs/Web/JavaScript/Reference/Global_Objects/Object/getOwnPropertyDescriptor), `for..in`, `Object.keys/values/entries`                                                                                                                                   |\n  | `[[OwnPropertyKeys]]`   | `ownKeys`                  | [Object.getOwnPropertyNames](https://developer.mozilla.org/zh/docs/Web/JavaScript/Reference/Global_Objects/Object/getOwnPropertyNames), [Object.getOwnPropertySymbols](https://developer.mozilla.org/zh/docs/Web/JavaScript/Reference/Global_Objects/Object/getOwnPropertySymbols), `for..in`, `Object.keys/values/entries` |\n\n  `get(target, property, receiver)` 方法。\n\n  读取属性时触发该方法，参数如下：\n\n\n  - `target` —— 是目标对象，该对象被作为第一个参数传递给 `new Proxy`，\n  - `property` —— 目标属性名，\n  - `receiver` —— 如果目标属性是一个 getter 访问器属性，则 `receiver` 就是本次读取属性所在的 `this` 对象。通常，这就是 `proxy` 对象本身（或者，如果我们从 proxy 继承，则是从该 proxy 继承的对象）。现在我们不需要此参数，因此稍后我们将对其进行详细介绍。\n\n  `set(target, property, value, receiver)`：\n\n  - `target` —— 是目标对象，该对象被作为第一个参数传递给 `new Proxy`，\n  - `property` —— 目标属性名称，\n  - `value` —— 目标属性的值，\n  - `receiver` —— 与 `get` 捕捉器类似，仅与 setter 访问器属性相关。\n\n  `has(target, property)`\n\n  - `target` —— 是目标对象，被作为第一个参数传递给 `new Proxy`，\n  - `property` —— 属性名称。\n\n  `apply(target, thisArg, args)` 捕捉器能使代理以函数的方式被调用：\n\n  - `target` 是目标对象（在 JavaScript 中，函数就是一个对象），\n  - `thisArg` 是 `this` 的值。\n  - `args` 是参数列表。\n\n  **Reflect**：\n\n  `Reflect` 是一个内建对象，可简化 `Proxy` 的创建。\n\n  前面所讲过的内部方法，例如 `[[Get]]` 和 `[[Set]]` 等，都只是规范性的，不能直接调用。\n\n  `Reflect` 对象使调用这些内部方法成为了可能。它的方法是内部方法的最小包装。\n\n  以下是执行相同操作和 `Reflect` 调用的示例：\n\n\n  | 操作                | `Reflect` 调用                      | 内部方法        |\n  | :------------------ | :---------------------------------- | :-------------- |\n  | `obj[prop]`         | `Reflect.get(obj, prop)`            | `[[Get]]`       |\n  | `obj[prop] = value` | `Reflect.set(obj, prop, value)`     | `[[Set]]`       |\n  | `delete obj[prop]`  | `Reflect.deleteProperty(obj, prop)` | `[[Delete]]`    |\n  | `new F(value)`      | `Reflect.construct(F, value)`       | `[[Construct]]` |\n  | …                  | …                                  | …              |\n\n  例如：\n\n  ```javascript\n  let user = {};\n\n  Reflect.set(user, \'name\', \'John\');\n\n  alert(user.name); // John\n  ```\n\n  尤其是，`Reflect` 允许我们将操作符（`new`，`delete`，……）作为函数（`Reflect.construct`，`Reflect.deleteProperty`，……）执行调用。这是一个有趣的功能，但是这里还有一点很重要。\n\n  **对于每个可被 `Proxy` 捕获的内部方法，在 `Reflect` 中都有一个对应的方法，其名称和参数与 `Proxy` 捕捉器相同。**\n\n  所以，我们可以使用 `Reflect` 来将操作转发给原始对象。\n\n  在下面这个示例中，捕捉器 `get` 和 `set` 均透明地（好像它们都不存在一样）将读取/写入操作转发到对象，并显示一条消息：\n\n  一个 **可撤销** 的代理是可以被禁用的代理。\n\n  假设我们有一个资源，并且想随时关闭对该资源的访问。\n\n  我们可以做的是将它包装成可一个撤销的代理，没有任何捕捉器。这样的代理会将操作转发给对象，并且我们可以随时将其禁用。\n\n  语法为：\n\n  ```javascript\n  let {proxy, revoke} = Proxy.revocable(target, handler)\n  ```\n\n  对 `revoke()` 的调用会从代理中删除对目标对象的所有内部引用，因此它们之间再无连接。\n\n---\n\n#### Eval：执行代码字符串\n\n语法如下：\n\n```javascript\nlet result = eval(code);\n```\n\n调用 `eval(code)` 会运行代码字符串，并返回最后一条语句的结果。\n\n- 在现代 JavaScript 编程中，很少使用它，通常也不需要使用它。\n- 可以访问外部局部变量。这被认为是一个不好的编程习惯。\n- 要在全局作用域中 `eval` 代码，可以使用 `window.eval(code)` 进行替代。\n- 此外，如果你的代码需要从外部作用域获取数据，请使用 `new Function`，并将数据作为参数传递给函数。\n\n#### Reference type 解读:\n\n仔细看的话，我们可能注意到 `obj.method()` 语句中的两个操作：\n\n1. 首先，点 `\'.\'` 取了属性 `obj.method` 的值。\n2. 接着 `()` 执行了它。\n\n那么，`this` 的信息是怎么从第一部分传递到第二部分的呢？\n\n如果我们将这些操作放在不同的行，`this` 必定是会丢失的：\n\nReference Type 是 ECMA 中的一个“规范类型”。我们不能直接使用它，但它被用在 JavaScript 语言内部。\n\nReference Type 的值是一个三个值的组合 `(base, name, strict)`，其中：\n\n- `base` 是对象。\n- `name` 是属性名。\n- `strict` 在 `use strict` 模式下为 true。\n\n---\n\n# 浏览器：文档，事件，接口\n\n## Document\n\n#### DOM 树\n\nHTML/XML 文档在浏览器内均被表示为 DOM 树。\n\n- 标签（tag）成为元素节点，并形成文档结构。\n- 文本（text）成为文本节点。\n- ……等，HTML 中的所有东西在 DOM 中都有它的位置，甚至对注释也是如此。\n\n我们可以使用开发者工具来检查（inspect）DOM 并手动修改它。\n\n---\n\n#### 遍历 DOM\n\n```\n<html>` = `document.documentElement\n```\n\n最顶层的 document 节点是 `document.documentElement`。这是对应 `<html>` 标签的 DOM 节点。\n\n```\n<body>` = `document.body\n```\n\n另一个被广泛使用的 DOM 节点是 `<body>` 元素 — `document.body`。\n\n```\n<head>` = `document.head\n```\n\n`<head>` 标签可以通过 `document.head` 访问。\n\nDOM集合：`childNodes` 看起来就像一个数组。但实际上它并不是一个数组，而是一个 **集合** — 一个类数组的可迭代对象。\n\n给定一个 DOM 节点，我们可以使用导航（navigation）属性访问其直接的邻居。\n\n这些属性主要分为两组：\n\n- 对于所有节点：`parentNode`，`childNodes`，`firstChild`，`lastChild`，`previousSibling`，`nextSibling`。\n- 仅对于元素节点：`parentElement`，`children`，`firstElementChild`，`lastElementChild`，`previousElementSibling`，`nextElementSibling`。\n\n---\n\n#### 搜索：getElement*，querySelector*\n\n有 6 种主要的方法，可以在 DOM 中搜索元素节点：\n\n\n| 方法名                   | 搜索方式     | 可以在元素上调用？ | 实时的？ |\n| ------------------------ | ------------ | ------------------ | -------- |\n| `querySelector`          | CSS-selector | ✔                 | -        |\n| `querySelectorAll`       | CSS-selector | ✔                 | -        |\n| `getElementById`         | `id`         | -                  | -        |\n| `getElementsByName`      | `name`       | -                  | ✔       |\n| `getElementsByTagName`   | tag or`\'*\'`  | ✔                 | ✔       |\n| `getElementsByClassName` | class        | ✔                 | ✔       |\n\n目前为止，最常用的是 `querySelector` 和 `querySelectorAll`，但是 `getElement(s)By*` 可能会偶尔有用，或者可以在旧脚本中找到。\n\n此外：\n\n- `elem.matches(css)` 用于检查 `elem` 与给定的 CSS 选择器是否匹配。\n- `elem.closest(css)` 用于查找与给定 CSS 选择器相匹配的最近的祖先。`elem` 本身也会被检查。\n\n让我们在这里提一下另一种用来检查子级与父级之间关系的方法，因为它有时很有用：\n\n- 如果 `elemB` 在 `elemA` 内（`elemA` 的后代）或者 `elemA==elemB`，`elemA.contains(elemB)` 将返回 true。\n\n---\n\n#### 节点属性：type，tag 和 content\n\n- `nodeType`\n\n  我们可以使用它来查看节点是文本节点还是元素节点。它具有一个数值型值（numeric value）：`1` 表示元素，`3` 表示文本节点，其他一些则代表其他节点类型。只读。\n- `nodeName/tagName`\n\n  用于元素名，标签名（除了 XML 模式，都要大写）。对于非元素节点，`nodeName` 描述了它是什么。只读。\n\n  - `tagName` 属性仅适用于 `Element` 节点。\n  - nodeName是为任意Node定义的：\n    - 对于元素，它的意义与 `tagName` 相同。\n    - 对于其他节点类型（text，comment 等），它拥有一个对应节点类型的字符串。\n- `innerHTML`\n\n  元素的 HTML 内容。可以被修改。仅对元素节点有效。\n- `outerHTML`\n\n  元素的完整 HTML。对 `elem.outerHTML` 的写入操作不会触及 `elem` 本身。而是在外部上下文中将其替换为新的 HTML。\n\n  在 `div.outerHTML=...` 中发生的事情是：\n\n  - `div` 被从文档（document）中移除。\n  - 另一个 HTML 片段 `<p>A new element</p>` 被插入到其位置上。\n  - `div` 仍拥有其旧的值。新的 HTML 没有被赋值给任何变量。\n- `nodeValue/data`\n\n  非元素节点（文本、注释）的内容。两者几乎一样，我们通常使用 `data`。可以被修改。\n- `textContent`\n\n  元素内的文本：HTML 减去所有 `<tags>`。写入文本会将文本放入元素内，所有特殊字符和标签均被视为文本。可以安全地插入用户生成的文本，并防止不必要的 HTML 插入。\n- `hidden`\n\n  当被设置为 `true` 时，执行与 CSS `display:none` 相同的事。\n\nDOM 节点还具有其他属性，具体有哪些属性则取决于它们的类。例如，`<input>` 元素（`HTMLInputElement`）支持 `value`，`type`，而 `<a>` 元素（`HTMLAnchorElement`）则支持 `href` 等。大多数标准 HTML 特性（attribute）都具有相应的 DOM 属性。\n\n---\n\n#### 特性和属性（Attributes and properties）\n\n- 特性（attribute）— 写在 HTML 中的内容。\n\n  HTML 特性有以下几个特征：\n\n  - 它们的名字是大小写不敏感的（`id` 与 `ID` 相同）。\n  - 它们的值总是字符串类型的。\n- 属性（property）— DOM 对象中的内容。\n\n  DOM 属性和方法的行为就像常规的 Javascript 对象一样：\n\n  - 它们可以有很多值。\n  - 它们是大小写敏感的（要写成 `elem.nodeType`，而不是 `elem.NoDeTyPe`）。\n- 改变特性值会更新属性。\n- 但是属性的更改不会影响特性。\n\n  例如：\n\n  ~~~javascript\n  <input>\n\n  <script>\n    let input = document.querySelector(\'input\');\n\n    // 特性 => 属性\n    input.setAttribute(\'value\', \'text\');\n    alert(input.value); // text\n\n    // 这个操作无效，属性 => 特性\n    input.value = \'newValue\';\n    alert(input.getAttribute(\'value\')); // text（没有被更新！）\n  </script>\n  ~~~\n\n简略的对比：\n\n\n|      | 属性                                   | 特性                         |\n| :--- | :------------------------------------- | :--------------------------- |\n| 类型 | 任何值，标准的属性具有规范中描述的类型 | 字符串                       |\n| 名字 | 名字（name）是大小写敏感的             | 名字（name）是大小写不敏感的 |\n\n操作特性的方法：\n\n- `elem.hasAttribute(name)` — 检查是否存在这个特性。\n- `elem.getAttribute(name)` — 获取这个特性值。\n- `elem.setAttribute(name, value)` — 设置这个特性值。\n- `elem.removeAttribute(name)` — 移除这个特性。\n- `elem.attributes` — 所有特性的集合。\n\n在大多数情况下，最好使用 DOM 属性。仅当 DOM 属性无法满足开发需求，并且我们真的需要特性时，才使用特性，例如：\n\n- 我们需要一个非标准的特性。但是如果它以 `data-` 开头，那么我们应该使用 `dataset`。\n\n  **所有以 “data-” 开头的特性均被保留供程序员使用。它们可在 `dataset` 属性中使用。**\n\n  例如，如果一个 `elem` 有一个名为 `\"data-about\"` 的特性，那么可以通过 `elem.dataset.about` 取到它。\n\n  像 `data-order-state` 这样的多词特性可以以驼峰式进行调用：`dataset.orderState`。\n- 对应的 DOM 属性可能不同，例如 `href` 属性一直是一个 **完整的** URL\n\n  如果我们需要 `href` 特性的值，或者其他与 HTML 中所写的完全相同的特性，则可以使用 `getAttribute`。\n\n---\n\n#### 修改文档（document）\n\n- 创建新节点的方法：\n\n  - `document.createElement(tag)` — 用给定的标签创建一个元素节点，\n  - `document.createTextNode(value)` — 创建一个文本节点（很少使用），\n  - `elem.cloneNode(deep)` — 克隆元素，如果 `deep==true` 则与其后代一起克隆。\n- 插入和移除节点的方法：\n\n  - `node.append(...nodes or strings)` — 在 `node` 末尾插入，\n  - `node.prepend(...nodes or strings)` — 在 `node` 开头插入，\n  - `node.before(...nodes or strings)` — 在 `node` 之前插入，\n  - `node.after(...nodes or strings)` — 在 `node` 之后插入，\n  - `node.replaceWith(...nodes or strings)` — 替换 `node`。\n  - `node.remove()` — 移除 `node`。\n\n  文本字符串被“作为文本”插入。\n- 这里还有“旧式”的方法：\n\n  - `parent.appendChild(node)`\n  - `parent.insertBefore(node, nextSibling)`\n  - `parent.removeChild(node)`\n  - `parent.replaceChild(newElem, node)`\n\n  这些方法都返回 `node`。\n- 在 `html` 中给定一些 HTML，`elem.insertAdjacentHTML(where, html)` 会根据 `where` 的值来插入它：\n\n  - `\"beforebegin\"` — 将 `html` 插入到 `elem` 前面，\n  - `\"afterbegin\"` — 将 `html` 插入到 `elem` 的开头，\n  - `\"beforeend\"` — 将 `html` 插入到 `elem` 的末尾，\n  - `\"afterend\"` — 将 `html` 插入到 `elem` 后面。\n\n另外，还有类似的方法，`elem.insertAdjacentText` 和 `elem.insertAdjacentElement`，它们会插入文本字符串和元素，但很少使用。\n\n- 要在页面加载完成之前将 HTML 附加到页面：\n\n  - `document.write(html)`\n\n  页面加载完成后，这样的调用将会擦除文档。多见于旧脚本。\n\n---\n\n#### 样式和类\n\n要管理 class，有两个 DOM 属性：\n\n- `className` — 字符串值，可以很好地管理整个类的集合。\n- `classList` — 具有 `add/remove/toggle/contains` 方法的对象，可以很好地支持单个类。\n  - `elem.classList.add/remove(class)` — 添加/移除类。\n  - `elem.classList.toggle(class)` — 如果类不存在就添加类，存在就移除它。\n  - `elem.classList.contains(class)` — 检查给定类，返回 `true/false`。\n\n要改变样式：\n\n- `style` 属性是具有驼峰（camelCased）样式的对象。对其进行读取和修改与修改 `\"style\"` 特性（attribute）中的各个属性具有相同的效果。要了解如何应用 `important` 和其他特殊内容 — 在 [MDN](https://developer.mozilla.org/zh/docs/Web/API/CSSStyleDeclaration) 中有一个方法列表。\n- `style.cssText` 属性对应于整个 `\"style\"` 特性（attribute），即完整的样式字符串。\n\n要读取已解析的（resolved）样式（对于所有类，在应用所有 CSS 并计算最终值之后）：\n\n- `getComputedStyle(elem, [pseudo])` 返回与 `style` 对象类似的，且包含了所有类的对象。只读。\n\n  **`getComputedStyle` 需要完整的属性名**\n\n  我们应该总是使用我们想要的确切的属性，例如 `paddingLeft`、`marginTop` 或 `borderTopWidth`。否则，就不能保证正确的结果。\n\n  语法如下：\n\n  ```javascript\n  getComputedStyle(element, [pseudo])\n  ```\n\n  - element\n\n    需要被读取样式值的元素。\n  - pseudo\n\n    伪元素（如果需要），例如 `::before`。空字符串或无参数则意味着元素本身。\n\n---\n\n#### 元素大小和滚动\n\n元素具有以下几何属性：\n\n- `offsetParent` — 是最接近的 CSS 定位的祖先，或者是 `td`，`th`，`table`，`body`。\n- `offsetLeft/offsetTop` — 是相对于 `offsetParent` 的左上角边缘的坐标。\n- `offsetWidth/offsetHeight` —自身完整大小，边框（border）尺寸计算在内。\n- `clientLeft/clientTop` — 边框的宽度， `clientLeft` 也包括滚动条的宽度。\n- `clientWidth/clientHeight` — 内容的 width/height，包括 padding，但不包括滚动条（scrollbar）。\n- `scrollWidth/scrollHeight` — 内容的 width/height，就像 `clientWidth/clientHeight` 一样，但还包括元素的滚动出的不可见的部分。\n- `scrollLeft/scrollTop` — 从元素的左上角开始，滚动出元素的上半部分的 width/height。\n\n除了 `scrollLeft/scrollTop` 外，所有属性都是只读的。如果我们修改 `scrollLeft/scrollTop`，浏览器会滚动对应的元素。\n\n---\n\n#### Window 大小和滚动\n\n- 文档可见部分的 width/height（内容区域的 width/height）：`document.documentElement.clientWidth/clientHeight`\n\n  如果这里存在一个滚动条，那么 `clientWidth/clientHeight` 会提供没有滚动条（减去它）的 width/height。换句话说，它们返回的是可用于内容的文档的可见部分的 width/height。\n\n  `window.innerWidth/innerHeight` 包括了滚动条。\n- 整个文档的 width/height，其中包括滚动出去的部分：\n\n  ```javascript\n  let scrollHeight = Math.max(\n    document.body.scrollHeight, document.documentElement.scrollHeight,\n    document.body.offsetHeight, document.documentElement.offsetHeight,\n    document.body.clientHeight, document.documentElement.clientHeight\n  );\n  ```\n\n滚动：\n\n- 读取当前的滚动：`window.pageYOffset/pageXOffset`。\n- 更改当前的滚动：\n  - `window.scrollTo(pageX,pageY)` — 绝对坐标，\n  - `window.scrollBy(x,y)` — 相对当前位置进行滚动，\n  - `elem.scrollIntoView(top)` — 滚动以使 `elem` 可见（`elem` 与窗口的顶部/底部对齐）。\n\n---\n\n#### 坐标\n\n1. 相对于窗口— 类似于`position:fixed``，从窗口的顶部/左侧边缘计算得出。\n\n   - 我们将这些坐标表示为 `clientX/clientY`，当我们研究事件属性时，就会明白为什么使用这种名称来表示坐标。\n2. 相对于文档— 与文档根（document root）中的`position:absolute`类似，从文档的顶部/左侧边缘计算得出。\n\n   - 我们将它们表示为 `pageX/pageY`。\n\n页面上的任何点都有坐标：\n\n1. 相对于窗口的坐标 — `elem.getBoundingClientRect()`。\n2. 相对于文档的坐标 — `elem.getBoundingClientRect()` 加上当前页面滚动。\n\n方法 `elem.getBoundingClientRect()` 返回最小矩形的窗口坐标，该矩形将 `elem` 作为内建 [DOMRect](https://www.w3.org/TR/geometry-1/#domrect) 类的对象。\n\n主要的 `DOMRect` 属性：\n\n- `x/y` — 矩形原点相对于窗口的 X/Y 坐标，\n- `width/height` — 矩形的 width/height（可以为负）。\n\n此外，还有派生（derived）属性：\n\n- `top/bottom` — 顶部/底部矩形边缘的 Y 坐标，\n- `left/right` — 左/右矩形边缘的 X 坐标。\n\n---\n\n## 事件简介\n\n#### 浏览器事件简介\n\n**事件** 是某事发生的信号。所有的 DOM 节点都生成这样的信号（但事件不仅限于 DOM）。\n\n**陌生的事件类型:**\n\n`touchstart`事件：当手指触摸屏幕时候触发，即使已经有一个手指放在屏幕上也会触发。\n\n`touchmove`事件：当手指在屏幕上滑动的时候连续地触发。在这个事件发生期间，调用preventDefault()事件可以阻止滚动。\n\n`touchend`事件：当手指从屏幕上离开的时候触发。\n\n##### **访问元素：this**：\n\n处理程序中的 `this` 的值是对应的元素。就是处理程序所在的那个元素。\n\n下面这行代码中的 `button` 使用 `this.innerHTML` 来显示它的内容：\n\n```markup\n<button onclick=\"alert(this.innerHTML)\">Click me</button>\n```\n\n**因为这里只有一个 `onclick` 属性，所以我们无法分配更多事件处理程序。**\n\n在下面这个示例中，我们使用 JavaScript 添加了一个处理程序，覆盖了现有的处理程序：\n\n```markup\n<input type=\"button\" id=\"elem\" onclick=\"alert(\'Before\')\" value=\"Click me\">\n<script>\n  elem.onclick = function() { // 覆盖了现有的处理程序\n    alert(\'After\'); // 只会显示此内容\n  };\n</script>\n```\n\n##### **addEventListener：**\n\n上述分配处理程序的方式的根本问题是 —— 我们不能为一个事件分配多个处理程序。\n\n假设，在我们点击了一个按钮时，我们代码中的一部分想要高亮显示这个按钮，另一部分则想要显示一条消息。\n\n我们想为此事件分配两个处理程序。但是，新的 DOM 属性将覆盖现有的 DOM 属性：\n\n```javascript\ninput.onclick = function() { alert(1); }\n// ...\ninput.onclick = function() { alert(2); } // 替换了前一个处理程序\n```\n\nWeb 标准的开发者很早就了解到了这一点，并提出了一种使用特殊方法 `addEventListener` 和 `removeEventListener` 来管理处理程序的替代方法。它们没有这样的问题。\n\n添加处理程序的语法：\n\n```javascript\nelement.addEventListener(event, handler[, options]);\n```\n\n- `event`\n\n  事件名，例如：`\"click\"`。\n- `handler`\n\n  处理程序。\n- `options`\n\n  具有以下属性的附加可选对象：`once`：如果为 `true`，那么会在被触发后自动删除监听器。`capture`：事件处理的阶段，我们稍后将在 [冒泡和捕获](https://zh.javascript.info/bubbling-and-capturing) 一章中介绍。由于历史原因，`options` 也可以是 `false/true`，它与 `{capture: false/true}` 相同。`passive`：如果为 `true`，那么处理程序将不会调用 `preventDefault()`，我们稍后将在 [浏览器默认行为](https://zh.javascript.info/default-browser-action) 一章中介绍。\n\n要移除处理程序，可以使用 `removeEventListener`：\n\n```javascript\nelement.removeEventListener(event, handler[, options]);\n```\n\n##### **事件对象：**\n\n当事件发生时，浏览器会创建一个 **`event` 对象**，将详细信息放入其中，并将其作为参数传递给处理程序。\n\n下面是一个从 `event` 对象获取鼠标指针的坐标的示例：\n\n```markup\n<input type=\"button\" value=\"Click me\" id=\"elem\">\n\n<script>\n  elem.onclick = function(event) {\n    // 显示事件类型、元素和点击的坐标\n    alert(event.type + \" at \" + event.currentTarget);\n    alert(\"Coordinates: \" + event.clientX + \":\" + event.clientY);\n  };\n</script>\n```\n\n`event` 对象的一些属性：\n\n- `event.type`\n\n  事件类型，这里是 `\"click\"`。\n- `event.currentTarget`\n\n  处理事件的元素。这与 `this` 相同，除非处理程序是一个箭头函数，或者它的 `this` 被绑定到了其他东西上，之后我们就可以从 `event.currentTarget` 获取元素了。\n- `event.clientX / event.clientY`\n\n  指针事件（pointer event）的指针的窗口相对坐标。\n\n还有很多属性。其中很多都取决于事件类型：键盘事件具有一组属性，指针事件具有另一组属性，稍后我们将详细讨论不同事件，那时我们再对其进行详细研究。\n\n##### 对象处理程序：handleEvent\n\n我们不仅可以分配函数，还可以使用 `addEventListener` 将一个对象分配为事件处理程序。当事件发生时，就会调用该对象的 `handleEvent` 方法。\n\n例如：\n\n```markup\n<button id=\"elem\">Click me</button>\n\n<script>\n  let obj = {\n    handleEvent(event) {\n      alert(event.type + \" at \" + event.currentTarget);\n    }\n  };\n\n  elem.addEventListener(\'click\', obj);\n</script>\n```\n\n正如我们所看到的，当 `addEventListener` 接收一个对象作为处理程序时，在事件发生时，它就会调用 `obj.handleEvent(event)` 来处理事件。\n\n---\n\n#### 冒泡和捕获\n\n##### 冒泡\n\n冒泡（bubbling）原理很简单。\n\n**当一个事件发生在一个元素上，它会首先运行在该元素上的处理程序，然后运行其父元素上的处理程序，然后一直向上到其他祖先上的处理程序。**\n\n##### event.target：\n\n父元素上的处理程序始终可以获取事件实际发生位置的详细信息。\n\n**引发事件的那个嵌套层级最深的元素被称为目标元素,可以通过 `event.target` 访问。**\n\n注意与 `this`（=`event.currentTarget`）之间的区别：\n\n- `event.target` —— 是引发事件的“目标”元素，它在冒泡过程中不会发生变化。\n- `this` —— 是“当前”元素，其中有一个当前正在运行的处理程序。\n\n例如，如果我们有一个处理程序 `form.onclick`，那么它可以“捕获”表单内的所有点击。无论点击发生在哪里，它都会冒泡到 `<form>` 并运行处理程序。\n\n在 `form.onclick` 处理程序中：\n\n- `this`（=`event.currentTarget`）是 `<form>` 元素，因为处理程序在它上面运行。\n- `event.target` 是表单中实际被点击的元素。\n\n##### 停止冒泡:\n\n冒泡事件从目标元素开始向上冒泡。通常，它会一直上升到 `<html>`，然后再到 `document` 对象，有些事件甚至会到达 `window`，它们会调用路径上所有的处理程序。\n\n但是任意处理程序都可以决定事件已经被完全处理，并停止冒泡。\n\n用于停止冒泡的方法是 `event.stopPropagation()`。\n\n例如，如果你点击 `<button>`，这里的 `body.onclick` 不会工作：\n\n```javascript\n<body onclick=\"alert(`the bubbling doesn\'t reach here`)\">\n  <button onclick=\"event.stopPropagation()\">Click me</button>\n</body>\n```\n\n##### 捕获:\n\n事件处理的另一个阶段被称为“捕获（capturing）”。它很少被用在实际开发中，但有时是有用的。\n\n[DOM 事件](http://www.w3.org/TR/DOM-Level-3-Events/)标准描述了事件传播的 3 个阶段：\n\n1. 捕获阶段（Capturing phase）—— 事件（从 Window）向下走近元素。\n2. 目标阶段（Target phase）—— 事件到达目标元素。\n3. 冒泡阶段（Bubbling phase）—— 事件从元素上开始冒泡。\n\n为了在捕获阶段捕获事件，我们需要将处理程序的 `capture` 选项设置为 `true`：\n\n```javascript\nelem.addEventListener(..., {capture: true})\n// 或者，用 {capture: true} 的别名 \"true\"\nelem.addEventListener(..., true)\n```\n\n`capture` 选项有两个可能的值：\n\n- 如果为 `false`（默认值），则在冒泡阶段设置处理程序。\n- 如果为 `true`，则在捕获阶段设置处理程序。\n\n#### 事件委托\n\n捕获和冒泡允许我们实现最强大的事件处理模式之一，即 **事件委托** 模式。\n\n这个想法是，如果我们有许多以类似方式处理的元素，那么就不必为每个元素分配一个处理程序 —— 而是将单个处理程序放在它们的共同祖先上。\n\n在处理程序中，我们获取 `event.target` 以查看事件实际发生的位置并进行处理。\n\n---\n\n#### 浏览器默认行为\n\n许多事件会自动触发浏览器执行某些行为。\n\n例如：\n\n- 点击一个链接 —— 触发导航（navigation）到该 URL。\n- 点击表单的提交按钮 —— 触发提交到服务器的行为。\n- 在文本上按下鼠标按钮并移动 —— 选中文本。\n\n##### 阻止浏览器行为\n\n有两种方式来告诉浏览器我们不希望它执行默认行为：\n\n- 主流的方式是使用 `event` 对象。有一个 `event.preventDefault()` 方法。\n- 如果处理程序是使用 `on<event>`（而不是 `addEventListener`）分配的，那返回 `false` 也同样有效。\n\n##### event.defaultPrevented\n\n如果默认行为被阻止，那么 `event.defaultPrevented` 属性为 `true`，否则为 `false`。\n\n这儿有一个有趣的用例。\n\n你还记得我们在 [冒泡和捕获](https://zh.javascript.info/bubbling-and-capturing) 一章中讨论过的 `event.stopPropagation()`，以及为什么停止冒泡是不好的吗？\n\n有时我们可以使用 `event.defaultPrevented` 来代替，来通知其他事件处理程序，该事件已经被处理。\n\n---\n\n## 加载文档和其他资源\n\n#### 页面生命周期\n\nHTML 页面的生命周期包含三个重要事件：\n\n- `DOMContentLoaded` —— 浏览器已完全加载 HTML，并构建了 DOM 树，但像 `<img>` 和样式表之类的外部资源可能尚未加载完成。\n- `load` —— 浏览器不仅加载完成了 HTML，还加载完成了所有外部资源：图片，样式等。\n- `beforeunload/unload` —— 当用户正在离开页面时。\n\n每个事件都是有用的：\n\n- `DOMContentLoaded` 事件 —— DOM 已经就绪，因此处理程序可以查找 DOM 节点，并初始化接口。\n- `load` 事件 —— 外部资源已加载完成，样式已被应用，图片大小也已知了。\n- `beforeunload` 事件 —— 用户正在离开：我们可以检查用户是否保存了更改，并询问他是否真的要离开。\n- `unload` 事件 —— 用户几乎已经离开了，但是我们仍然可以启动一些操作，例如发送统计数据。\n\n`DOMContentLoaded` 事件发生在 `document` 对象上。我们必须使用 `addEventListener` 来捕获它。\n\n当整个页面，包括样式、图片和其他资源被加载完成时，会触发 `window` 对象上的 `load` 事件。可以通过 `onload` 属性获取此事件。\n\n当访问者离开页面时，`window` 对象上的 `unload` 事件就会被触发。\n\n如果访问者触发了离开页面的导航（navigation）或试图关闭窗口，`beforeunload` 处理程序将要求进行更多确认。\n\n`document.readyState` 属性可以为我们提供当前加载状态的信息。\n\n它有 3 个可能值：\n\n- `loading` —— 文档正在被加载。\n- `interactive` —— 文档被全部读取。\n- `complete` —— 文档被全部读取，并且所有资源（例如图片等）都已加载完成。\n\n`document.readyStat`e是文档的当前状态，可以在`readystatechange`事件中跟踪状态更改：\n\n- `loading` —— 文档正在被加载。\n- `interactive` —— 文档已被解析完成，与 `DOMContentLoaded` 几乎同时发生，但是在 `DOMContentLoaded` 之前发生。\n- `complete` —— 文档和资源均已加载完成，与 `window.onload` 几乎同时发生，但是在 `window.onload` 之前发生。\n\n~~~javascript\n// 当前状态\nconsole.log(document.readyState);\n\n// 状态改变时打印它\ndocument.addEventListener(\'readystatechange\', () => console.log(document.readyState));\n~~~\n\n---\n\n#### 脚本：async，defer\n\n这会导致两个重要的问题：\n\n1. 脚本不能访问到位于它们下面的 DOM 元素，因此，脚本无法给它们添加处理程序等。\n2. 如果页面顶部有一个笨重的脚本，它会“阻塞页面”。在该脚本下载并执行结束前，用户都不能看到页面内容：\n\n##### `defer`\n\n`defer` 特性告诉浏览器不要等待脚本。相反，浏览器将继续处理 HTML，构建 DOM。脚本会“在后台”下载，然后等 DOM 构建完成后，脚本才会执行。\n\n换句话说：\n\n- 具有 `defer` 特性的脚本不会阻塞页面。\n- 具有 `defer` 特性的脚本总是要等到 DOM 解析完毕，但在 `DOMContentLoaded` 事件之前执行。\n\n**具有 `defer` 特性的脚本保持其相对顺序，就像常规脚本一样。**\n\n##### `async`\n\n`async` 特性与 `defer` 有些类似。它也能够让脚本不阻塞页面。但是，在行为上二者有着重要的区别。\n\n`async` 特性意味着脚本是完全独立的：\n\n- 浏览器不会因 `async` 脚本而阻塞（与 `defer` 类似）。\n- 其他脚本不会等待 `async` 脚本加载完成，同样，`async` 脚本也不会等待其他脚本。\n- DOMContentLoaded和异步脚本不会彼此等待：\n  - `DOMContentLoaded` 可能会发生在异步脚本之前（如果异步脚本在页面完成后才加载完成）\n  - `DOMContentLoaded` 也可能发生在异步脚本之后（如果异步脚本很短，或者是从 HTTP 缓存中加载的）\n\n换句话说，`async` 脚本会在后台加载，并在加载就绪时运行。DOM 和其他脚本不会等待它们，它们也不会等待其它的东西。`async` 脚本就是一个会在加载完成时执行的完全独立的脚本。\n\n**`async` 特性仅适用于外部脚本**\n\n就像 `defer` 一样，如果 `<script>` 标签没有 `src` 特性（attribute），那么 `async` 特性会被忽略。\n\n# DOM 变动观察器\n\n`MutationObserver` 是一个内建对象，它观察 DOM 元素，并在检测到更改时触发回调。\n\n语法：\n\n`MutationObserver` 使用简单。\n\n首先，我们创建一个带有回调函数的观察器：\n\n```javascript\nlet observer = new MutationObserver(callback);\n```\n\n然后将其附加到一个 DOM 节点：\n\n```javascript\nobserver.observe(node, config);\n```\n\n`config` 是一个具有布尔选项的对象，该布尔选项表示“将对哪些更改做出反应”：\n\n- `childList` —— `node` 的直接子节点的更改，\n- `subtree` —— `node` 的所有后代的更改，\n- `attributes` —— `node` 的特性（attribute），\n- `attributeFilter` —— 特性名称数组，只观察选定的特性。\n- `characterData` —— 是否观察 `node.data`（文本内容），\n\n其他几个选项：\n\n- `attributeOldValue` —— 如果为 `true`，则将特性的旧值和新值都传递给回调（参见下文），否则只传新值（需要 `attributes` 选项），\n- `characterDataOldValue` —— 如果为 `true`，则将 `node.data` 的旧值和新值都传递给回调（参见下文），否则只传新值（需要 `characterData` 选项）。\n\n然后，在发生任何更改后，将执行“回调”：更改被作为一个 [MutationRecord](https://dom.spec.whatwg.org/#mutationrecord) 对象列表传入第一个参数，而观察器自身作为第二个参数。\n\n[MutationRecord](https://dom.spec.whatwg.org/#mutationrecord) 对象具有以下属性：\n\n- type—— 变动类型，以下类型之一：\n  - `\"attributes\"`：特性被修改了，\n  - `\"characterData\"`：数据被修改了，用于文本节点，\n  - `\"childList\"`：添加/删除了子元素。\n- `target` —— 更改发生在何处：`\"attributes\"` 所在的元素，或 `\"characterData\"` 所在的文本节点，或 `\"childList\"` 变动所在的元素，\n- `addedNodes/removedNodes` —— 添加/删除的节点，\n- `previousSibling/nextSibling` —— 添加/删除的节点的上一个/下一个兄弟节点，\n- `attributeName/attributeNamespace` —— 被更改的特性的名称/命名空间（用于 XML），\n- `oldValue` —— 之前的值，仅适用于特性或文本更改，如果设置了相应选项 `attributeOldValue`/`characterDataOldValue`。\n\n~~~javascript\n<div contentEditable id=\"elem\">Click and <b>edit</b>, please</div>\n\n<script>\nlet observer = new MutationObserver(mutationRecords => {\n  console.log(mutationRecords); // console.log(the changes)\n});\n\n// 观察除了特性之外的所有变动\nobserver.observe(elem, {\n  childList: true, // 观察直接子节点\n  subtree: true, // 及其更低的后代节点\n  characterDataOldValue: true // 将旧的数据传递给回调\n});\n</script>\n~~~\n\n---\n\n# 其他文章\n\n## 二进制数据，文件\n\n#### ArrayBuffer，二进制数组\n\n在 Web 开发中，当我们处理文件时（创建，上传，下载），经常会遇到二进制数据。另一个典型的应用场景是图像处理。\n\n不过，在 JavaScript 中有很多种二进制数据格式，会有点容易混淆。仅举几个例子：\n\n- `ArrayBuffer`，`Uint8Array`，`DataView`，`Blob`，`File` 及其他。\n\n**基本的二进制对象是 `ArrayBuffer` —— 对固定长度的连续内存空间的引用。**\n\n我们这样创建它：\n\n```javascript\nlet buffer = new ArrayBuffer(16); // 创建一个长度为 16 的 buffer\nalert(buffer.byteLength); // 16\n```\n\n它会分配一个 16 字节的连续内存空间，并用 0 进行预填充。\n\n**`ArrayBuffer` 不是某种东西的数组**\n\n让我们先澄清一个可能的误区。`ArrayBuffer` 与 `Array` 没有任何共同之处：\n\n- 它的长度是固定的，我们无法增加或减少它的长度。\n- 它正好占用了内存中的那么多空间。\n- 要访问单个字节，需要另一个“视图”对象，而不是 `buffer[index]`。\n\n`ArrayBuffer` 是一个内存区域。它里面存储了什么？无从判断。只是一个原始的字节序列。\n\n**如要操作 `ArrayBuffer`，我们需要使用“视图”对象。**\n\n视图对象本身并不存储任何东西。它是一副“眼镜”，透过它来解释存储在 `ArrayBuffer` 中的字节。\n\n例如：\n\n- **`Uint8Array`** —— 将 `ArrayBuffer` 中的每个字节视为 0 到 255 之间的单个数字（每个字节是 8 位，因此只能容纳那么多）。这称为 “8 位无符号整数”。\n- **`Uint16Array`** —— 将每 2 个字节视为一个 0 到 65535 之间的整数。这称为 “16 位无符号整数”。\n- **`Uint32Array`** —— 将每 4 个字节视为一个 0 到 4294967295 之间的整数。这称为 “32 位无符号整数”。\n- **`Float64Array`** —— 将每 8 个字节视为一个 `5.0x10-324` 到 `1.8x10308` 之间的浮点数。\n\n~~~javascript\nlet buffer = new ArrayBuffer(16); // 创建一个长度为 16 的 buffer\n\nlet view = new Uint32Array(buffer); // 将 buffer 视为一个 32 位整数的序列\n\nalert(Uint32Array.BYTES_PER_ELEMENT); // 每个整数 4 个字节\n\nalert(view.length); // 4，它存储了 4 个整数\nalert(view.byteLength); // 16，字节中的大小\n\n// 让我们写入一个值\nview[0] = 123456;\n\n// 遍历值\nfor(let num of view) {\n  alert(num); // 123456，然后 0，0，0（一共 4 个值）\n}\n~~~\n\n---\n\n#### TextDecoder 和 TextEncoder\n\n##### TextDecoder\n\n内建的 [TextDecoder](https://encoding.spec.whatwg.org/#interface-textdecoder) 对象在给定缓冲区（buffer）和编码格式（encoding）的情况下，允许将值读取为实际的 JavaScript 字符串。\n\n首先我们需要创建：\n\n```javascript\nlet decoder = new TextDecoder([label], [options]);\n```\n\n- **`label`** —— 编码格式，默认为 `utf-8`，但同时也支持 `big5`，`windows-1251` 等许多其他编码格式。\n- `options` 可选对象：\n  - **`fatal`** —— 布尔值，如果为 `true` 则为无效（不可解码）字符抛出异常，否则（默认）用字符 `\\uFFFD` 替换无效字符。\n  - **`ignoreBOM`** —— 布尔值，如果为 `true` 则 BOM（可选的字节顺序 Unicode 标记），很少需要使用。\n\n……然后解码：\n\n```javascript\nlet str = decoder.decode([input], [options]);\n```\n\n- **`input`** —— 要被解码的 `BufferSource`。\n- `options`可选对象：\n  - **`stream`** —— 对于解码流，为 true，则将传入的数据块（chunk）作为参数重复调用 `decoder`。在这种情况下，多字节的字符可能偶尔会在块与块之间被分割。这个选项告诉 `TextDecoder` 记住“未完成”的字符，并在下一个数据块来的时候进行解码。\n\n例如：\n\n```javascript\nlet uint8Array = new Uint8Array([72, 101, 108, 108, 111]);\n\nalert( new TextDecoder().decode(uint8Array) ); // Hello\n```\n\n##### TextEncoder\n\n[TextEncoder](https://encoding.spec.whatwg.org/#interface-textencoder) 做相反的事情 —— 将字符串转换为字节。\n\n语法为：\n\n```javascript\nlet encoder = new TextEncoder();\n```\n\n只支持 `utf-8` 编码。\n\n它有两种方法：\n\n- **`encode(str)`** —— 从字符串返回 `Uint8Array`。\n- **`encodeInto(str, destination)`** —— 将 `str` 编码到 `destination` 中，该目标必须为 `Uint8Array`。\n\n```javascript\nlet encoder = new TextEncoder();\n\nlet uint8Array = encoder.encode(\"Hello\");\nalert(uint8Array); // 72,101,108,108,111\n```\n\n---\n\n#### Blob\n\n构造函数的语法为：\n\n```javascript\nnew Blob(blobParts, options);\n```\n\n- **`blobParts`** 是 `Blob`/`BufferSource`/`String` 类型的值的数组。\n- `options`可选对象：\n  - **`type`** —— `Blob` 类型，通常是 MIME 类型，例如 `image/png`，\n  - **`endings`** —— 是否转换换行符，使 `Blob` 对应于当前操作系统的换行符（`\\r\\n` 或 `\\n`）。默认为 `\"transparent\"`（啥也不做），不过也可以是 `\"native\"`（转换）。\n\n##### Blob 用作 URL\n\nBlob 可以很容易用作 `<a>`、`<img>` 或其他标签的 URL，来显示它们的内容。\n\n多亏了 `type`，让我们也可以下载/上传 `Blob` 对象，而在网络请求中，`type` 自然地变成了 `Content-Type`。\n\n让我们从一个简单的例子开始。通过点击链接，你可以下载一个具有动态生成的内容为 `hello world` 的 `Blob` 的文件：\n\n```javascript\n<!-- download 特性（attribute）强制浏览器下载而不是导航 -->\n<a download=\"hello.txt\" href=\'#\' id=\"link\">Download</a>\n\n<script>\nlet blob = new Blob([\"Hello, world!\"], {type: \'text/plain\'});\n\nlink.href = URL.createObjectURL(blob);\n```\n\n##### Blob 转换为 base64\n\n这种编码将二进制数据表示为一个由 0 到 64 的 ASCII 码组成的字符串，非常安全且“可读“。更重要的是 —— 我们可以在 “data-url” 中使用此编码。\n\n[“data-url”](https://developer.mozilla.org/zh/docs/Web/http/Data_URIs) 的形式为 `data:[<mediatype>][;base64],<data>`。我们可以在任何地方使用这种 url，和使用“常规” url 一样。\n\n下面是下载 `Blob` 的示例，这次是通过 base-64：\n\n```javascript\nlet link = document.createElement(\'a\');\nlink.download = \'hello.txt\';\n\nlet blob = new Blob([\'Hello, world!\'], {type: \'text/plain\'});\n\nlet reader = new FileReader();\nreader.readAsDataURL(blob); // 将 Blob 转换为 base64 并调用 onload\n\nreader.onload = function() {\n  link.href = reader.result; // data url\n  link.click();\n};\n```\n\n这两种从 `Blob` 创建 URL 的方法都可以用。但通常 `URL.createObjectURL(blob)` 更简单快捷。\n\n##### File 和 FileReader\n\n[File](https://www.w3.org/TR/FileAPI/#dfn-file) 对象继承自 `Blob`，并扩展了与文件系统相关的功能。\n\n有两种方式可以获取它。\n\n第一种，与 `Blob` 类似，有一个构造器：\n\n```javascript\nnew File(fileParts, fileName, [options])\n```\n\n- **`fileParts`** —— Blob/BufferSource/String 类型值的数组。\n- **`fileName`** —— 文件名字符串。\n- `options` 可选对象：\n  - **`lastModified`** —— 最后一次修改的时间戳（整数日期）。\n\n第二种，更常见的是，我们从 `<input type=\"file\">` 或拖放或其他浏览器接口来获取文件。在这种情况下，file 将从操作系统（OS）获得 this 信息。\n\n由于 `File` 是继承自 `Blob` 的，所以 `File` 对象具有相同的属性，附加：\n\n- `name` —— 文件名，\n- `lastModified` —— 最后一次修改的时间戳。\n\n这就是我们从 `<input type=\"file\">` 中获取 `File` 对象的方式：\n\n```markup\n<input type=\"file\" onchange=\"showFile(this)\">\n\n<script>\nfunction showFile(input) {\n  let file = input.files[0];\n\n  alert(`File name: ${file.name}`); // 例如 my.png\n  alert(`Last modified: ${file.lastModified}`); // 例如 1552830408824\n}\n</script>\n```\n\n**请注意：**\n\n输入（input）可以选择多个文件，因此 `input.files` 是一个类数组对象。这里我们只有一个文件，所以我们只取 `input.files[0]`。\n\n###### \n\n[FileReader](https://www.w3.org/TR/FileAPI/#dfn-filereader) 是一个对象，其唯一目的是从 `Blob`（因此也从 `File`）对象中读取数据。\n\n它使用事件来传递数据，因为从磁盘读取数据可能比较费时间。\n\n构造函数：\n\n```javascript\nlet reader = new FileReader(); // 没有参数\n```\n\n主要方法:\n\n- **`readAsArrayBuffer(blob)`** —— 将数据读取为二进制格式的 `ArrayBuffer`。\n- **`readAsText(blob, [encoding])`** —— 将数据读取为给定编码（默认为 `utf-8` 编码）的文本字符串。\n- **`readAsDataURL(blob)`** —— 读取二进制数据，并将其编码为 base64 的 data url。\n- **`abort()`** —— 取消操作。\n\n`read*` 方法的选择，取决于我们喜欢哪种格式，以及如何使用数据。\n\n- `readAsArrayBuffer` —— 用于二进制文件，执行低级别的二进制操作。对于诸如切片（slicing）之类的高级别的操作，`File` 是继承自 `Blob` 的，所以我们可以直接调用它们，而无需读取。\n- `readAsText` —— 用于文本文件，当我们想要获取字符串时。\n- `readAsDataURL` —— 当我们想在 `src` 中使用此数据，并将其用于 `img` 或其他标签时。正如我们在 [Blob](https://zh.javascript.info/blob) 一章中所讲的，还有一种用于此的读取文件的替代方案：`URL.createObjectURL(file)`。\n\n读取过程中，有以下事件：\n\n- `loadstart` —— 开始加载。\n- `progress` —— 在读取过程中出现。\n- `load` —— 读取完成，没有 error。\n- `abort` —— 调用了 `abort()`。\n- `error` —— 出现 error。\n- `loadend` —— 读取完成，无论成功还是失败。\n\n读取完成后，我们可以通过以下方式访问读取结果：\n\n- `reader.result` 是结果（如果成功）\n- `reader.error` 是 error（如果失败）。\n\n使用最广泛的事件无疑是 `load` 和 `error`。\n\n这是一个读取文件的示例：\n\n```javascript\n<input type=\"file\" onchange=\"readFile(this)\">\n\n<script>\nfunction readFile(input) {\n  let file = input.files[0];\n\n  let reader = new FileReader();\n\n  reader.readAsText(file);\n\n  reader.onload = function() {\n    console.log(reader.result);\n  };\n\n  reader.onerror = function() {\n    console.log(reader.error);\n  };\n\n}\n</script>\n```\n\n**`FileReader` 用于 blob**\n\n正如我们在 [Blob](https://zh.javascript.info/blob) 一章中所提到的，`FileReader` 不仅可读取文件，还可读取任何 blob。\n\n我们可以使用它将 blob 转换为其他格式：\n\n- `readAsArrayBuffer(blob)` —— 转换为 `ArrayBuffer`，\n- `readAsText(blob, [encoding])` —— 转换为字符串（`TextDecoder` 的一个替代方案），\n- `readAsDataURL(blob)` —— 转换为 base64 的 data url。\n\n---\n\n# 网络请求\n\n#### Fetch\n\n基本语法：\n\n```javascript\nlet promise = fetch(url, [options])\n```\n\n- **`url`** —— 要访问的 URL。\n- **`options`** —— 可选参数：method，header 等。\n\n典型的 fetch 请求由两个 `await` 调用组成：\n\n```javascript\nlet response = await fetch(url, options); // 解析 response header\nlet result = await response.json(); // 将 body 读取为 json\n```\n\n或者以 promise 形式：\n\n```javascript\nfetch(url, options)\n  .then(response => response.json())\n  .then(result => /* process result */)\n```\n\n响应的属性：\n\n- `response.status` —— response 的 HTTP 状态码，\n- `response.ok` —— HTTP 状态码为 200-299，则为 `true`。\n- `response.headers` —— 类似于 Map 的带有 HTTP header 的对象。\n\n获取 response body 的方法：\n\n- **`response.text()`** —— 读取 response，并以文本形式返回 response，\n- **`response.json()`** —— 将 response 解析为 JSON 对象形式，\n- **`response.formData()`** —— 以 `FormData` 对象（`multipart/form-data` 编码，参见下一章）的形式返回 response，\n- **`response.blob()`** —— 以 [Blob](https://zh.javascript.info/blob)（具有类型的二进制数据）形式返回 response，\n- **`response.arrayBuffer()`** —— 以 [ArrayBuffer](https://zh.javascript.info/arraybuffer-binary-arrays)（低级别的二进制数据）形式返回 response。\n\n到目前为止我们了解到的 fetch 选项：\n\n- `method` —— HTTP 方法，\n- `headers` —— 具有 request header 的对象（不是所有 header 都是被允许的）\n- `body` —— 要以 `string`，`FormData`，`BufferSource`，`Blob` 或 `UrlSearchParams` 对象的形式发送的数据（request body）。\n\n作为一个读取为二进制格式的演示示例，让我们 fetch 并显示一张 [“fetch” 规范](https://fetch.spec.whatwg.org/) 中的图片（`Blob` 操作的有关内容请见 [Blob](https://zh.javascript.info/blob)）：\n\n```javascript\nlet response = await fetch(\'/article/fetch/logo-fetch.svg\');\n\nlet blob = await response.blob(); // 下载为 Blob 对象\n\n// 为其创建一个 <img>\nlet img = document.createElement(\'img\');\nimg.style = \'position:fixed;top:10px;left:10px;width:100px\';\ndocument.body.append(img);\n\n// 显示它\nimg.src = URL.createObjectURL(blob);\n\nsetTimeout(() => { // 3 秒后将其隐藏\n  img.remove();\n  URL.revokeObjectURL(img.src);\n}, 3000);\n```\n\n##### POST 请求\n\n要创建一个 `POST` 请求，或者其他方法的请求，我们需要使用 `fetch` 选项：\n\n- **`method`** —— HTTP 方法，例如 `POST`，\n- `body`\n\n  —— request body，其中之一：\n\n  - 字符串（例如 JSON 编码的），\n  - `FormData` 对象，以 `multipart/form-data` 形式发送数据，\n  - `Blob`/`BufferSource` 发送二进制数据，\n  - [URLSearchParams](https://zh.javascript.info/url)，以 `x-www-form-urlencoded` 编码形式发送数据，很少使用。\n\nJSON 形式是最常用的。\n\n例如，下面这段代码以 JSON 形式发送 `user` 对象：\n\n```javascript\nlet user = {\n  name: \'John\',\n  surname: \'Smith\'\n};\n\nlet response = await fetch(\'/article/fetch/post/user\', {\n  method: \'POST\',\n  headers: {\n    \'Content-Type\': \'application/json;charset=utf-8\'\n  },\n  body: JSON.stringify(user)\n});\n\nlet result = await response.json();\nalert(result.message);\n```\n\n#### FormData\n\n这一章是关于发送 HTML 表单的：带有或不带文件，带有其他字段等。\n\n正如你所看到的，它几乎就是一行代码：\n\n```javascript\n<form id=\"formElem\">\n  <input type=\"text\" name=\"name\" value=\"John\">\n  <input type=\"text\" name=\"surname\" value=\"Smith\">\n  <input type=\"submit\">\n</form>\n\n<script>\n  formElem.onsubmit = async (e) => {\n    e.preventDefault();\n\n    let response = await fetch(\'/article/formdata/post/user\', {\n      method: \'POST\',\n      body: new FormData(formElem)\n    });\n\n    let result = await response.json();\n\n    alert(result.message);\n  };\n</script>\n```\n\n**FormData 方法**:\n\n我们可以使用以下方法修改 `FormData` 中的字段：\n\n- `formData.append(name, value)` —— 添加具有给定 `name` 和 `value` 的表单字段，\n- `formData.append(name, blob, fileName)` —— 添加一个字段，就像它是 `<input type=\"file\">`，第三个参数 `fileName` 设置文件名（而不是表单字段名），因为它是用户文件系统中文件的名称，\n- `formData.delete(name)` —— 移除带有给定 `name` 的字段，\n- `formData.get(name)` —— 获取带有给定 `name` 的字段值，\n- `formData.has(name)` —— 如果存在带有给定 `name` 的字段，则返回 `true`，否则返回 `false`。\n\n从技术上来讲，一个表单可以包含多个具有相同 `name` 的字段，因此，多次调用 `append` 将会添加多个具有相同名称的字段。\n\n还有一个 `set` 方法，语法与 `append` 相同。不同之处在于 `.set` 移除所有具有给定 `name` 的字段，然后附加一个新字段。因此，它确保了只有一个具有这种 `name` 的字段，其他的和 `append` 一样：\n\n- `formData.set(name, value)`，\n- `formData.set(name, blob, fileName)`。\n\n#### XMLHttpRequest\n\n在现代 Web 开发中，出于以下三种原因，我们还在使用 `XMLHttpRequest`：\n\n1. 历史原因：我们需要支持现有的使用了 `XMLHttpRequest` 的脚本。\n2. 我们需要兼容旧浏览器，并且不想用 polyfill（例如为了使脚本更小）。\n3. 我们需要做一些 `fetch` 目前无法做到的事情，例如跟踪上传进度。\n\nXMLHttpRequest 有两种执行模式：同步（synchronous）和异步（asynchronous）。\n\n我们首先来看看最常用的异步模式：\n\n要发送请求，需要 3 个步骤：\n\n1. 创建 `XMLHttpRequest`：\n\n   ```javascript\n   let xhr = new XMLHttpRequest();\n   ```\n\n   此构造器没有参数。\n2. 初始化它，通常就在 `new XMLHttpRequest` 之后：\n\n   ```javascript\n   xhr.open(method, URL, [async, user, password])\n   ```\n\n   此方法指定请求的主要参数：\n\n   - `method` —— HTTP 方法。通常是 `\"GET\"` 或 `\"POST\"`。\n   - `URL` —— 要请求的 URL，通常是一个字符串，也可以是 [URL](https://zh.javascript.info/url) 对象。\n   - `async` —— 如果显式地设置为 `false`，那么请求将会以同步的方式处理，我们稍后会讲到它。\n   - `user`，`password` —— HTTP 基本身份验证（如果需要的话）的登录名和密码。\n\n   请注意，`open` 调用与其名称相反，不会建立连接。它仅配置请求，而网络活动仅以 `send` 调用开启。\n3. 发送请求。\n\n   ```javascript\n   xhr.send([body])\n   ```\n\n   这个方法会建立连接，并将请求发送到服务器。可选参数 `body` 包含了 request body。\n4. 监听 `xhr` 事件以获取响应。\n\n   这三个事件是最常用的：\n\n   - `load` —— 当请求完成（即使 HTTP 状态为 400 或 500 等），并且响应已完全下载。\n   - `error` —— 当无法发出请求，例如网络中断或者无效的 URL。\n   - `progress` —— 在下载响应期间定期触发，报告已经下载了多少。\n\n     ~~~javascript\n     xhr.onprogress = function(event) {\n       if (event.lengthComputable) {\n         alert(`Received ${event.loaded} of ${event.total} bytes`);\n       } else {\n         alert(`Received ${event.loaded} bytes`); // 没有 Content-Length\n       }\n\n     };\n     ~~~\n\n一旦服务器有了响应，我们可以在以下 `xhr` 属性中接收结果：\n\n- `status`\n\n  HTTP 状态码（一个数字）：`200`，`404`，`403` 等，如果出现非 HTTP 错误，则为 `0`。\n- `statusText`\n\n  HTTP 状态消息（一个字符串）：状态码为 `200` 对应于 `OK`，`404` 对应于 `Not Found`，`403` 对应于 `Forbidden`。\n\n**URL 搜索参数（URL search parameters）**\n\n为了向 URL 添加像 `?name=value` 这样的参数，并确保正确的编码，我们可以使用 [URL](https://zh.javascript.info/url) 对象：\n\n```javascript\nlet url = new URL(\'https://google.com/search\');\nurl.searchParams.set(\'q\', \'test me!\');\n\n// 参数 \'q\' 被编码\nxhr.open(\'GET\', url); // https://google.com/search?q=test+me%21\n```\n\n**响应类型：**\n\n我们可以使用 `xhr.responseType` 属性来设置响应格式：\n\n- `\"\"`（默认）—— 响应格式为字符串，\n- `\"text\"` —— 响应格式为字符串，\n- `\"arraybuffer\"` —— 响应格式为 `ArrayBuffer`（对于二进制数据，请参见 [ArrayBuffer，二进制数组](https://zh.javascript.info/arraybuffer-binary-arrays)），\n- `\"blob\"` —— 响应格式为 `Blob`（对于二进制数据，请参见 [Blob](https://zh.javascript.info/blob)），\n- `\"document\"` —— 响应格式为 XML document（可以使用 XPath 和其他 XML 方法）或 HTML document（基于接收数据的 MIME 类型）\n- `\"json\"` —— 响应格式为 JSON（自动解析）。\n\n**HTTP-header**\n\nHTTP-header 有三种方法：\n\n- `setRequestHeader(name, value)`\n\n  使用给定的 `name` 和 `value` 设置 request header。例如：\n\n  ~~~javascript\n  xhr.setRequestHeader(\'Content-Type\', \'application/json\');\n  ~~~\n- `getResponseHeader(name)`\n\n  获取具有给定 `name` 的 header（`Set-Cookie` 和 `Set-Cookie2` 除外）。\n- `getAllResponseHeaders()`\n\n  返回除 `Set-Cookie` 和 `Set-Cookie2` 外的所有 response header。\n\n  header 以单行形式返回\n\n**上传进度**\n\n`progress` 事件仅在下载阶段触发。\n\n也就是说：如果我们 `POST` 一些内容，`XMLHttpRequest` 首先上传我们的数据（request body），然后下载响应。\n\n如果我们要上传的东西很大，那么我们肯定会对跟踪上传进度感兴趣。但是 `xhr.onprogress` 在这里并不起作用。\n\n这里有另一个对象，它没有方法，它专门用于跟踪上传事件：`xhr.upload`。\n\n它会生成事件，类似于 `xhr`，但是 `xhr.upload` 仅在上传时触发它们：\n\n- `loadstart` —— 上传开始。\n- `progress` —— 上传期间定期触发。\n- `abort` —— 上传中止。\n- `error` —— 非 HTTP 错误。\n- `load` —— 上传成功完成。\n- `timeout` —— 上传超时（如果设置了 `timeout` 属性）。\n- `loadend` —— 上传完成，无论成功还是 error。\n\n**跨域请求**\n\n`XMLHttpRequest` 可以使用和 [fetch](https://zh.javascript.info/fetch-crossorigin) 相同的 CORS 策略进行跨源请求。\n\n就像 `fetch` 一样，默认情况下不会将 cookie 和 HTTP 授权发送到其他域。要启用它们，可以将 `xhr.withCredentials` 设置为 `true`：\n\n```javascript\nlet xhr = new XMLHttpRequest();\nxhr.withCredentials = true;\n\nxhr.open(\'POST\', \'http://anywhere.com/request\');\n...\n```\n\n# 在浏览器中存储数据\n\n## Cookie，document.cookie\n\nCookie 通常是由 Web 服务器使用响应 `Set-Cookie` HTTP-header 设置的。然后浏览器使用 `Cookie` HTTP-header 将它们自动添加到（几乎）每个对相同域的请求中。\n\n选项被列在 `key=value` 之后，以 `;` 分隔，像这样：\n\n```javascript\ndocument.cookie = \"user=John; path=/; expires=Tue, 19 Jan 2038 03:14:07 GMT\"\n```\n\n`document.cookie` 提供了对 cookie 的访问\n\n- 写入操作只会修改其中提到的 cookie。\n- name/value 必须被编码。\n- 一个 cookie 最大不能超过 4KB。每个域下最多允许有 20+ 个左右的 cookie（具体取决于浏览器）。\n\nCookie 选项：\n\n- `path=/`，默认为当前路径，使 cookie 仅在该路径下可见。\n\n  url 路径前缀必须是绝对路径。它使得该路径下的页面可以访问该 cookie。默认为当前路径。\n\n  如果一个 cookie 带有 `path=/admin` 设置，那么该 cookie 在 `/admin` 和 `/admin/something` 下都是可见的，但是在 `/home` 或 `/adminpage` 下不可见。\n\n  通常，我们应该将 `path` 设置为根目录：`path=/`，以使 cookie 对此网站的所有页面可见。\n- `domain=site.com`，默认 cookie 仅在当前域下可见。如果显式地设置了域，可以使 cookie 在子域下也可见。\n- `expires` 或 `max-age` 设定了 cookie 过期时间。如果没有设置，则当浏览器关闭时 cookie 就会失效。\n\n  它是 `expires` 的替代选项，指明了 cookie 的过期时间距离当前时间的秒数。\n\n  如果将其设置为 0 或负数，则 cookie 会被删除：\n\n  ```javascript\n  // cookie 会在一小时后失效\n  document.cookie = \"user=John; max-age=3600\";\n\n  // 删除 cookie（让它立即过期）\n  document.cookie = \"user=John; max-age=0\";\n  ```\n- `secure` 使 cookie 仅在 HTTPS 下有效。\n- `samesite`，如果请求来自外部网站，禁止浏览器发送 cookie。这有助于防止 XSRF 攻击。`samesite=strict` 的 cookie 永远不会被发送。\n\n---\n\n## LocalStorage，sessionStorage\n\nWeb 存储对象 `localStorage` 和 `sessionStorage` 允许我们在浏览器上保存键/值对。\n\n我们已经有了 cookie。为什么还要其他存储对象呢？\n\n- 与 cookie 不同，Web 存储对象不会随每个请求被发送到服务器。因此，我们可以保存更多数据。大多数浏览器都允许保存至少 2MB 的数据（或更多），并且具有用于配置数据的设置。\n- 还有一点和 cookie 不同，服务器无法通过 HTTP header 操纵存储对象。一切都是在 JavaScript 中完成的。\n- 存储绑定到源（域/协议/端口三者）。也就是说，不同协议或子域对应不同的存储对象，它们之间无法访问彼此数据。\n\n两个存储对象都提供相同的方法和属性：\n\n- `setItem(key, value)` —— 存储键/值对。\n- `getItem(key)` —— 按照键获取值。\n- `removeItem(key)` —— 删除键及其对应的值。\n- `clear()` —— 删除所有数据。\n- `key(index)` —— 获取该索引下的键名。\n- `length` —— 存储的内容的长度。\n\n正如你所看到的，它就像一个 `Map` 集合（`setItem/getItem/removeItem`），但也允许通过 `key(index)` 来按索引访问。\n\n`localStorage` 最主要的特点是：\n\n- 在同源的所有标签页和窗口之间共享数据。\n- 数据不会过期。它在浏览器重启甚至系统重启后仍然存在。\n\n我们只需要在同一个源（域/端口/协议），URL 路径可以不同。\n\n在所有同源的窗口之间，`localStorage` 数据可以共享。因此，如果我们在一个窗口中设置了数据，则在另一个窗口中也可以看到数据变化。\n\n`sessionStorage` 对象的使用频率比 `localStorage` 对象低得多。\n\n属性和方法是相同的，但是它有更多的限制：\n\n- `sessionStorage`的数据只存在于当前浏览器标签页。\n  - 具有相同页面的另一个标签页中将会有不同的存储。\n  - 但是，它在同一标签页下的 iframe 之间是共享的（假如它们来自相同的源）。\n- 数据在页面刷新后仍然保留，但在关闭/重新打开浏览器标签页后不会被保留。\n\n当 `localStorage` 或 `sessionStorage` 中的数据更新后，[storage](https://html.spec.whatwg.org/multipage/webstorage.html#the-storageevent-interface) 事件就会触发，它具有以下属性：\n\n- `key` —— 发生更改的数据的 `key`（如果调用的是 `.clear()` 方法，则为 `null`）。\n- `oldValue` —— 旧值（如果是新增数据，则为 `null`）。\n- `newValue` —— 新值（如果是删除数据，则为 `null`）。\n- `url` —— 发生数据更新的文档的 url。\n- `storageArea` —— 发生数据更新的 `localStorage` 或 `sessionStorage` 对象。\n');
INSERT INTO `blog_article_content` VALUES ('45', '![th (374×280) (bing.net)](https://ts2.cn.mm.bing.net/th?id=OIP-C.aSvNxj1fhYVBoc05NYYWQAHaFj&w=288&h=216&c=8&rs=1&qlt=90&o=6&dpr=1.3&pid=3.1&rm=2)\n\n你今天还好吗？\n');
INSERT INTO `blog_article_content` VALUES ('46', '1111111111\n');
INSERT INTO `blog_article_content` VALUES ('47', '113131\n');
INSERT INTO `blog_article_content` VALUES ('48', '1111\n');
INSERT INTO `blog_article_content` VALUES ('49', '121\n');
INSERT INTO `blog_article_content` VALUES ('50', '1211212\n');
INSERT INTO `blog_article_content` VALUES ('51', '121\n');
INSERT INTO `blog_article_content` VALUES ('52', '1211\n');
INSERT INTO `blog_article_content` VALUES ('53', '21111111111111\n');
INSERT INTO `blog_article_content` VALUES ('54', '12121\n');

-- ----------------------------
-- Table structure for `blog_article_lables`
-- ----------------------------
DROP TABLE IF EXISTS `blog_article_lables`;
CREATE TABLE `blog_article_lables` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `art_name` varchar(20) DEFAULT NULL COMMENT '文章标签名',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` bit(1) DEFAULT b'0' COMMENT '0:未删除，1:已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章标签';

-- ----------------------------
-- Records of blog_article_lables
-- ----------------------------
INSERT INTO `blog_article_lables` VALUES ('1', 'java', '2024-04-17 02:38:45', '2024-04-17 02:38:50', '');
INSERT INTO `blog_article_lables` VALUES ('2', 'python', '2024-04-17 02:38:53', '2024-04-17 02:38:56', '');
INSERT INTO `blog_article_lables` VALUES ('3', 'react', '2024-04-17 02:38:58', '2024-04-17 02:39:01', '');
INSERT INTO `blog_article_lables` VALUES ('4', 'go', null, null, '');
INSERT INTO `blog_article_lables` VALUES ('5', '区块链', null, null, '');
INSERT INTO `blog_article_lables` VALUES ('6', 'vue', null, null, '');
INSERT INTO `blog_article_lables` VALUES ('7', 'spring boot', null, null, '');
INSERT INTO `blog_article_lables` VALUES ('8', '密码学', null, null, '');
INSERT INTO `blog_article_lables` VALUES ('9', 'spring cloud', null, null, '');
INSERT INTO `blog_article_lables` VALUES ('10', 'reds', null, null, '');
INSERT INTO `blog_article_lables` VALUES ('11', '缓存', null, null, '');
INSERT INTO `blog_article_lables` VALUES ('12', '前端', null, null, '');
INSERT INTO `blog_article_lables` VALUES ('13', '后端', null, null, '');
INSERT INTO `blog_article_lables` VALUES ('14', '计算机基础', null, null, '');
INSERT INTO `blog_article_lables` VALUES ('15', '数据结构', null, null, '');
INSERT INTO `blog_article_lables` VALUES ('16', '算法', null, null, '');
INSERT INTO `blog_article_lables` VALUES ('17', '面试', null, null, '');
INSERT INTO `blog_article_lables` VALUES ('18', '简历', null, null, '');
INSERT INTO `blog_article_lables` VALUES ('19', '人工智能', null, null, '');
INSERT INTO `blog_article_lables` VALUES ('20', '软件架构', null, null, '');
INSERT INTO `blog_article_lables` VALUES ('21', 'JavaScript', null, null, '');
INSERT INTO `blog_article_lables` VALUES ('22', 'js', null, null, '');
INSERT INTO `blog_article_lables` VALUES ('23', 'html', null, null, '');
INSERT INTO `blog_article_lables` VALUES ('24', 'css', null, null, '');
INSERT INTO `blog_article_lables` VALUES ('25', '前端打包工具', null, null, '');
INSERT INTO `blog_article_lables` VALUES ('26', '前端优化', null, null, '');
INSERT INTO `blog_article_lables` VALUES ('27', '其他', null, null, '');
INSERT INTO `blog_article_lables` VALUES ('28', 'hello', '2024-04-17 02:42:30', '2024-04-17 02:42:30', '');
INSERT INTO `blog_article_lables` VALUES ('29', 'nihao', '2024-04-17 02:42:30', '2024-04-17 02:42:30', '');
INSERT INTO `blog_article_lables` VALUES ('30', '111', '2024-04-17 02:45:59', '2024-04-17 02:45:59', '');
INSERT INTO `blog_article_lables` VALUES ('31', 'biaho', '2024-04-17 02:45:59', '2024-04-17 02:45:59', '');
INSERT INTO `blog_article_lables` VALUES ('32', '222', '2024-04-17 02:47:48', '2024-04-17 02:47:48', '');

-- ----------------------------
-- Table structure for `blog_article_lable_class`
-- ----------------------------
DROP TABLE IF EXISTS `blog_article_lable_class`;
CREATE TABLE `blog_article_lable_class` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `article_id` bigint DEFAULT NULL COMMENT '文章ID',
  `article_lables_id` bigint DEFAULT NULL COMMENT '文章标签ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` bit(1) DEFAULT b'0' COMMENT '0:未删除，1:已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章标签类别';

-- ----------------------------
-- Records of blog_article_lable_class
-- ----------------------------
INSERT INTO `blog_article_lable_class` VALUES ('1', '11', '11', '2024-03-06 00:04:30', '2024-03-06 00:04:30', '');
INSERT INTO `blog_article_lable_class` VALUES ('3', '13', '14', '2024-03-06 00:28:40', '2024-03-06 00:28:40', '');
INSERT INTO `blog_article_lable_class` VALUES ('4', '14', '18', '2024-03-06 02:56:28', '2024-03-06 02:56:28', '');
INSERT INTO `blog_article_lable_class` VALUES ('5', '14', '17', '2024-03-06 02:56:28', '2024-03-06 02:56:28', '');
INSERT INTO `blog_article_lable_class` VALUES ('6', '15', '18', '2024-03-06 16:20:05', '2024-03-06 16:20:05', '');
INSERT INTO `blog_article_lable_class` VALUES ('7', '16', '18', '2024-03-06 17:09:43', '2024-03-06 17:09:43', '');
INSERT INTO `blog_article_lable_class` VALUES ('9', '18', '18', '2024-03-06 17:33:16', '2024-03-06 17:33:16', '');
INSERT INTO `blog_article_lable_class` VALUES ('11', '17', '18', '2024-03-06 18:54:09', '2024-03-06 18:54:09', '');
INSERT INTO `blog_article_lable_class` VALUES ('12', '17', '8', '2024-03-06 18:54:09', '2024-03-06 18:54:09', '');
INSERT INTO `blog_article_lable_class` VALUES ('13', '19', '18', '2024-03-06 18:55:04', '2024-03-06 18:55:04', '');
INSERT INTO `blog_article_lable_class` VALUES ('14', '19', '17', '2024-03-06 18:55:04', '2024-03-06 18:55:04', '');
INSERT INTO `blog_article_lable_class` VALUES ('15', '19', '12', '2024-03-06 18:55:04', '2024-03-06 18:55:04', '');
INSERT INTO `blog_article_lable_class` VALUES ('16', '20', '18', '2024-03-06 18:55:59', '2024-03-06 18:55:59', '');
INSERT INTO `blog_article_lable_class` VALUES ('17', '20', '17', '2024-03-06 18:55:59', '2024-03-06 18:55:59', '');
INSERT INTO `blog_article_lable_class` VALUES ('18', '21', '8', '2024-03-06 19:12:11', '2024-03-06 19:12:11', '');
INSERT INTO `blog_article_lable_class` VALUES ('19', '22', '18', '2024-03-06 19:12:21', '2024-03-06 19:12:21', '');
INSERT INTO `blog_article_lable_class` VALUES ('20', '22', '12', '2024-03-06 19:12:21', '2024-03-06 19:12:21', '');
INSERT INTO `blog_article_lable_class` VALUES ('21', '22', '8', '2024-03-06 19:12:21', '2024-03-06 19:12:21', '');
INSERT INTO `blog_article_lable_class` VALUES ('22', '23', '18', '2024-03-06 19:12:34', '2024-03-06 19:12:34', '');
INSERT INTO `blog_article_lable_class` VALUES ('23', '24', '8', '2024-03-06 19:13:58', '2024-03-06 19:13:58', '');
INSERT INTO `blog_article_lable_class` VALUES ('24', '25', '8', '2024-03-06 19:14:08', '2024-03-06 19:14:08', '');
INSERT INTO `blog_article_lable_class` VALUES ('25', '25', '17', '2024-03-06 19:14:08', '2024-03-06 19:14:08', '');
INSERT INTO `blog_article_lable_class` VALUES ('26', '25', '18', '2024-03-06 19:14:08', '2024-03-06 19:14:08', '');
INSERT INTO `blog_article_lable_class` VALUES ('27', '25', '14', '2024-03-06 19:14:08', '2024-03-06 19:14:08', '');
INSERT INTO `blog_article_lable_class` VALUES ('28', '26', '8', '2024-03-06 19:16:32', '2024-03-06 19:16:32', '');
INSERT INTO `blog_article_lable_class` VALUES ('29', '26', '17', '2024-03-06 19:16:32', '2024-03-06 19:16:32', '');
INSERT INTO `blog_article_lable_class` VALUES ('30', '26', '12', '2024-03-06 19:16:32', '2024-03-06 19:16:32', '');
INSERT INTO `blog_article_lable_class` VALUES ('31', '27', '18', '2024-03-06 19:17:00', '2024-03-06 19:17:00', '');
INSERT INTO `blog_article_lable_class` VALUES ('32', '27', '17', '2024-03-06 19:17:00', '2024-03-06 19:17:00', '');
INSERT INTO `blog_article_lable_class` VALUES ('33', '28', '18', '2024-03-06 19:19:10', '2024-03-06 19:19:10', '');
INSERT INTO `blog_article_lable_class` VALUES ('34', '29', '18', '2024-03-06 19:19:22', '2024-03-06 19:19:22', '');
INSERT INTO `blog_article_lable_class` VALUES ('35', '29', '17', '2024-03-06 19:19:22', '2024-03-06 19:19:22', '');
INSERT INTO `blog_article_lable_class` VALUES ('36', '29', '12', '2024-03-06 19:19:22', '2024-03-06 19:19:22', '');
INSERT INTO `blog_article_lable_class` VALUES ('37', '30', '14', '2024-03-06 19:22:32', '2024-03-06 19:22:32', '');
INSERT INTO `blog_article_lable_class` VALUES ('38', '30', '8', '2024-03-06 19:22:32', '2024-03-06 19:22:32', '');
INSERT INTO `blog_article_lable_class` VALUES ('39', '30', '12', '2024-03-06 19:22:32', '2024-03-06 19:22:32', '');
INSERT INTO `blog_article_lable_class` VALUES ('40', '31', '14', '2024-03-06 19:22:39', '2024-03-06 19:22:39', '');
INSERT INTO `blog_article_lable_class` VALUES ('41', '32', '12', '2024-03-12 09:39:03', '2024-03-12 09:39:03', '');
INSERT INTO `blog_article_lable_class` VALUES ('42', '32', '21', '2024-03-12 09:39:03', '2024-03-12 09:39:03', '');
INSERT INTO `blog_article_lable_class` VALUES ('43', '32', '22', '2024-03-12 09:39:03', '2024-03-12 09:39:03', '');
INSERT INTO `blog_article_lable_class` VALUES ('44', '12', '14', '2024-03-12 10:28:44', '2024-03-12 10:28:44', '');
INSERT INTO `blog_article_lable_class` VALUES ('45', '33', '27', '2024-03-12 11:28:55', '2024-03-12 11:28:55', '');
INSERT INTO `blog_article_lable_class` VALUES ('46', '34', '27', '2024-03-12 12:23:21', '2024-03-12 12:23:21', '');
INSERT INTO `blog_article_lable_class` VALUES ('47', '35', '27', '2024-03-12 12:23:50', '2024-03-12 12:23:50', '');

-- ----------------------------
-- Table structure for `blog_attention`
-- ----------------------------
DROP TABLE IF EXISTS `blog_attention`;
CREATE TABLE `blog_attention` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '用户id',
  `attention_user_id` bigint NOT NULL COMMENT '关注的用户id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` bit(1) DEFAULT b'0' COMMENT '0:未删除，1:已删除',
  `active` bit(1) DEFAULT b'0' COMMENT '0:在推送列表里，1：不在',
  `stated` bit(1) DEFAULT b'0' COMMENT '0:未读，1:已读',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='关注';

-- ----------------------------
-- Records of blog_attention
-- ----------------------------
INSERT INTO `blog_attention` VALUES ('8', '5', '6', '2024-04-22 01:35:55', '2024-04-22 01:35:55', '', '', '');
INSERT INTO `blog_attention` VALUES ('9', '6', '5', '2024-04-24 20:45:38', '2024-04-24 20:45:38', '', '', '');
INSERT INTO `blog_attention` VALUES ('10', '6', '5', '2024-04-24 20:45:53', '2024-04-24 20:45:53', '', '', '');
INSERT INTO `blog_attention` VALUES ('11', '6', '5', '2024-04-24 20:48:26', '2024-04-24 20:48:26', '', '', '');
INSERT INTO `blog_attention` VALUES ('12', '6', '5', '2024-04-24 20:52:55', '2024-04-24 20:52:55', '', '', '');
INSERT INTO `blog_attention` VALUES ('13', '6', '5', '2024-04-24 20:53:08', '2024-04-24 20:53:08', '', '', '');
INSERT INTO `blog_attention` VALUES ('14', '6', '5', '2024-04-24 22:58:58', '2024-04-24 22:58:58', '', '', '');
INSERT INTO `blog_attention` VALUES ('15', '6', '5', '2024-04-24 23:09:25', '2024-04-24 23:09:25', '', '', '');
INSERT INTO `blog_attention` VALUES ('16', '6', '5', '2024-04-24 23:14:27', '2024-04-24 23:14:27', '', '', '');
INSERT INTO `blog_attention` VALUES ('17', '6', '5', '2024-04-24 23:14:44', '2024-04-24 23:14:44', '', '', '');

-- ----------------------------
-- Table structure for `blog_back_messages`
-- ----------------------------
DROP TABLE IF EXISTS `blog_back_messages`;
CREATE TABLE `blog_back_messages` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `back_content` text NOT NULL COMMENT '内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` bit(1) DEFAULT b'0' COMMENT '0:未删除，1:已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='后台消息通知';

-- ----------------------------
-- Records of blog_back_messages
-- ----------------------------
INSERT INTO `blog_back_messages` VALUES ('5', '尊敬的用户：\n\n感谢您一直以来对我们博客系统的支持与信任！为了提供更好的使用体验和更丰富的功能，我们将于近期进行博客系统的更新和优化。更新内容包括但不限于以下几个方面：\n\n界面优化：我们对博客系统的界面进行了重新设计和优化，使其更加清新简洁，操作更加流畅。\n\n功能增强：新增了一些实用的功能模块，如标签管理、订阅通知等，以满足用户的不同需求。\n\n性能提升：优化了系统的性能和稳定性，加强了系统的安全防护，确保用户数据的安全和稳定。\n\n移动端适配：对移动端的浏览体验进行了优化，使用户在手机或平板上也能够轻松访问和使用博客系统。\n\n为了让更新过程顺利进行，我们可能会在更新期间暂时关闭部分功能或页面，给您带来的不便，敬请谅解！更新完成后，我们将第一时间通知您，您可以通过登录系统或关注我们的官方公众号获取最新的更新信息。\n\n再次感谢您对我们的支持和信任，如果您在使用过程中遇到任何问题或有任何建议，欢迎随时联系我们的客服团队，我们将竭诚为您服务！\n\n祝您使用愉快！', '2024-04-17 17:26:41', '2024-04-17 17:26:41', '');

-- ----------------------------
-- Table structure for `blog_chatting_records`
-- ----------------------------
DROP TABLE IF EXISTS `blog_chatting_records`;
CREATE TABLE `blog_chatting_records` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `sender_user_id` bigint NOT NULL COMMENT '发送方用户id',
  `acceptor_user_id` bigint NOT NULL COMMENT '接收方用户id',
  `chatting_records_content_id` bigint NOT NULL COMMENT '聊天内容id',
  `read_status` bit(1) DEFAULT b'0' COMMENT '0:未读，1:已读',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `active` bit(1) DEFAULT b'0' COMMENT '0:在推送列表里，1：不在',
  `deleted` bit(1) DEFAULT b'0' COMMENT '0:未删除，1:已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='聊天记录';

-- ----------------------------
-- Records of blog_chatting_records
-- ----------------------------
INSERT INTO `blog_chatting_records` VALUES ('15', '6', '5', '15', '', null, null, '', '');
INSERT INTO `blog_chatting_records` VALUES ('16', '6', '5', '16', '', null, null, '', '');
INSERT INTO `blog_chatting_records` VALUES ('17', '5', '6', '17', '', '2024-03-17 03:35:02', '2024-03-17 03:35:02', '', '');
INSERT INTO `blog_chatting_records` VALUES ('18', '5', '6', '18', '', '2024-03-17 03:35:06', '2024-03-17 03:35:06', '', '');
INSERT INTO `blog_chatting_records` VALUES ('19', '5', '6', '19', '', '2024-03-17 03:35:07', '2024-03-17 03:35:07', '', '');
INSERT INTO `blog_chatting_records` VALUES ('20', '5', '6', '20', '', '2024-03-17 03:35:08', '2024-03-17 03:35:08', '', '');
INSERT INTO `blog_chatting_records` VALUES ('21', '5', '6', '21', '', '2024-03-17 03:35:09', '2024-03-17 03:35:09', '', '');
INSERT INTO `blog_chatting_records` VALUES ('22', '5', '6', '22', '', '2024-03-17 03:35:10', '2024-03-17 03:35:10', '', '');
INSERT INTO `blog_chatting_records` VALUES ('23', '5', '6', '23', '', '2024-03-17 03:35:11', '2024-03-17 03:35:11', '', '');
INSERT INTO `blog_chatting_records` VALUES ('24', '5', '6', '24', '', '2024-03-17 03:35:14', '2024-03-17 03:35:14', '', '');
INSERT INTO `blog_chatting_records` VALUES ('25', '5', '6', '25', '', '2024-03-17 03:35:51', '2024-03-17 03:35:51', '', '');
INSERT INTO `blog_chatting_records` VALUES ('26', '5', '6', '26', '', '2024-03-17 03:36:17', '2024-03-17 03:36:17', '', '');
INSERT INTO `blog_chatting_records` VALUES ('27', '5', '6', '27', '', '2024-03-17 03:36:19', '2024-03-17 03:36:19', '', '');
INSERT INTO `blog_chatting_records` VALUES ('28', '5', '6', '28', '', '2024-03-17 03:36:20', '2024-03-17 03:36:20', '', '');
INSERT INTO `blog_chatting_records` VALUES ('29', '5', '6', '29', '', '2024-03-17 03:36:21', '2024-03-17 03:36:21', '', '');
INSERT INTO `blog_chatting_records` VALUES ('30', '5', '6', '30', '', '2024-03-17 03:36:21', '2024-03-17 03:36:21', '', '');
INSERT INTO `blog_chatting_records` VALUES ('31', '5', '6', '31', '', '2024-03-17 03:36:22', '2024-03-17 03:36:22', '', '');
INSERT INTO `blog_chatting_records` VALUES ('32', '5', '6', '32', '', '2024-03-17 03:36:23', '2024-03-17 03:36:23', '', '');
INSERT INTO `blog_chatting_records` VALUES ('33', '5', '6', '33', '', '2024-03-17 03:36:25', '2024-03-17 03:36:25', '', '');
INSERT INTO `blog_chatting_records` VALUES ('34', '5', '6', '34', '', '2024-03-17 03:36:26', '2024-03-17 03:36:26', '', '');
INSERT INTO `blog_chatting_records` VALUES ('35', '5', '6', '35', '', '2024-03-17 03:37:50', '2024-03-17 03:37:50', '', '');
INSERT INTO `blog_chatting_records` VALUES ('36', '5', '6', '36', '', '2024-03-17 03:37:54', '2024-03-17 03:37:54', '', '');
INSERT INTO `blog_chatting_records` VALUES ('37', '6', '5', '37', '', '2024-03-17 03:49:11', '2024-03-17 03:49:11', '', '');
INSERT INTO `blog_chatting_records` VALUES ('38', '6', '5', '38', '', '2024-03-17 03:54:31', '2024-03-17 03:54:31', '', '');
INSERT INTO `blog_chatting_records` VALUES ('39', '5', '6', '39', '', '2024-03-17 03:58:50', '2024-03-17 03:58:50', '', '');
INSERT INTO `blog_chatting_records` VALUES ('40', '5', '6', '40', '', '2024-03-17 03:59:01', '2024-03-17 03:59:01', '', '');
INSERT INTO `blog_chatting_records` VALUES ('41', '5', '6', '41', '', '2024-03-17 03:59:14', '2024-03-17 03:59:14', '', '');
INSERT INTO `blog_chatting_records` VALUES ('42', '5', '6', '42', '', '2024-03-17 04:42:01', '2024-03-17 04:42:01', '', '');
INSERT INTO `blog_chatting_records` VALUES ('43', '5', '6', '43', '', '2024-04-24 23:15:36', '2024-04-24 23:15:36', '', '');

-- ----------------------------
-- Table structure for `blog_chatting_records_content`
-- ----------------------------
DROP TABLE IF EXISTS `blog_chatting_records_content`;
CREATE TABLE `blog_chatting_records_content` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `chatting_records_content` text COMMENT '聊天内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='聊天记录关系映射';

-- ----------------------------
-- Records of blog_chatting_records_content
-- ----------------------------
INSERT INTO `blog_chatting_records_content` VALUES ('15', '弄好');
INSERT INTO `blog_chatting_records_content` VALUES ('16', 'jj');
INSERT INTO `blog_chatting_records_content` VALUES ('17', 'dd');
INSERT INTO `blog_chatting_records_content` VALUES ('18', 'dd');
INSERT INTO `blog_chatting_records_content` VALUES ('19', 'dd');
INSERT INTO `blog_chatting_records_content` VALUES ('20', 'dd');
INSERT INTO `blog_chatting_records_content` VALUES ('21', 'dd');
INSERT INTO `blog_chatting_records_content` VALUES ('22', 'dd');
INSERT INTO `blog_chatting_records_content` VALUES ('23', 'dd');
INSERT INTO `blog_chatting_records_content` VALUES ('24', 'dd');
INSERT INTO `blog_chatting_records_content` VALUES ('25', '222');
INSERT INTO `blog_chatting_records_content` VALUES ('26', '111');
INSERT INTO `blog_chatting_records_content` VALUES ('27', '111');
INSERT INTO `blog_chatting_records_content` VALUES ('28', '111');
INSERT INTO `blog_chatting_records_content` VALUES ('29', '111');
INSERT INTO `blog_chatting_records_content` VALUES ('30', '111');
INSERT INTO `blog_chatting_records_content` VALUES ('31', '111');
INSERT INTO `blog_chatting_records_content` VALUES ('32', '111');
INSERT INTO `blog_chatting_records_content` VALUES ('33', '111');
INSERT INTO `blog_chatting_records_content` VALUES ('34', '111');
INSERT INTO `blog_chatting_records_content` VALUES ('35', '12sfsafsaf');
INSERT INTO `blog_chatting_records_content` VALUES ('36', '12sfsafsaffsafsaf');
INSERT INTO `blog_chatting_records_content` VALUES ('37', '12');
INSERT INTO `blog_chatting_records_content` VALUES ('38', '12');
INSERT INTO `blog_chatting_records_content` VALUES ('39', '12');
INSERT INTO `blog_chatting_records_content` VALUES ('40', '12');
INSERT INTO `blog_chatting_records_content` VALUES ('41', 'renjia');
INSERT INTO `blog_chatting_records_content` VALUES ('42', '333');
INSERT INTO `blog_chatting_records_content` VALUES ('43', '121');

-- ----------------------------
-- Table structure for `blog_comment`
-- ----------------------------
DROP TABLE IF EXISTS `blog_comment`;
CREATE TABLE `blog_comment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '评论用户id',
  `comment_key_id` bigint NOT NULL DEFAULT '0' COMMENT '评论的id',
  `person_id` bigint NOT NULL COMMENT '父级评论用户id',
  `article_id` bigint NOT NULL COMMENT '文章id',
  `comment_content_id` bigint NOT NULL COMMENT '评论内容id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `active` bit(1) DEFAULT b'0' COMMENT '0:在推送列表里，1：不在',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` bit(1) DEFAULT b'0' COMMENT '0:未删除，1:已删除',
  `stated` bit(1) DEFAULT b'0' COMMENT '0:未读，1:已读',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='评论';

-- ----------------------------
-- Records of blog_comment
-- ----------------------------
INSERT INTO `blog_comment` VALUES ('77', '6', '0', '0', '32', '82', '2024-03-14 22:36:14', '', '2024-03-14 22:36:14', '', '');
INSERT INTO `blog_comment` VALUES ('80', '6', '0', '5', '11', '85', '2024-03-14 22:37:16', '', '2024-03-14 22:37:16', '', '');
INSERT INTO `blog_comment` VALUES ('99', '5', '77', '6', '32', '104', '2024-04-22 01:36:11', '', '2024-04-22 01:36:11', '', '');
INSERT INTO `blog_comment` VALUES ('103', '6', '0', '5', '34', '108', '2024-04-22 02:50:25', '', '2024-04-22 02:50:25', '', '');
INSERT INTO `blog_comment` VALUES ('104', '5', '0', '6', '34', '109', '2024-04-25 01:35:43', '', '2024-04-25 01:35:43', '', '');

-- ----------------------------
-- Table structure for `blog_comment_content`
-- ----------------------------
DROP TABLE IF EXISTS `blog_comment_content`;
CREATE TABLE `blog_comment_content` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `comment_content` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='评论内容映射';

-- ----------------------------
-- Records of blog_comment_content
-- ----------------------------
INSERT INTO `blog_comment_content` VALUES ('1', null);
INSERT INTO `blog_comment_content` VALUES ('2', null);
INSERT INTO `blog_comment_content` VALUES ('3', '1231321');
INSERT INTO `blog_comment_content` VALUES ('4', '131321');
INSERT INTO `blog_comment_content` VALUES ('5', '31321');
INSERT INTO `blog_comment_content` VALUES ('6', '13131');
INSERT INTO `blog_comment_content` VALUES ('7', '3131312');
INSERT INTO `blog_comment_content` VALUES ('8', 'afaffafds');
INSERT INTO `blog_comment_content` VALUES ('9', '123213');
INSERT INTO `blog_comment_content` VALUES ('10', '1132132');
INSERT INTO `blog_comment_content` VALUES ('11', '313213');
INSERT INTO `blog_comment_content` VALUES ('12', '你好我');
INSERT INTO `blog_comment_content` VALUES ('13', '你好');
INSERT INTO `blog_comment_content` VALUES ('14', '你是');
INSERT INTO `blog_comment_content` VALUES ('15', '人间爱');
INSERT INTO `blog_comment_content` VALUES ('16', '好的呢');
INSERT INTO `blog_comment_content` VALUES ('17', '你好');
INSERT INTO `blog_comment_content` VALUES ('18', '你怎么了');
INSERT INTO `blog_comment_content` VALUES ('19', 'hi');
INSERT INTO `blog_comment_content` VALUES ('20', 'hi');
INSERT INTO `blog_comment_content` VALUES ('21', 'nihao1');
INSERT INTO `blog_comment_content` VALUES ('22', 'hoa1');
INSERT INTO `blog_comment_content` VALUES ('23', '12321');
INSERT INTO `blog_comment_content` VALUES ('24', '311');
INSERT INTO `blog_comment_content` VALUES ('25', '12313');
INSERT INTO `blog_comment_content` VALUES ('26', '12');
INSERT INTO `blog_comment_content` VALUES ('27', '12');
INSERT INTO `blog_comment_content` VALUES ('28', '121');
INSERT INTO `blog_comment_content` VALUES ('29', '1321');
INSERT INTO `blog_comment_content` VALUES ('30', 'niaho1');
INSERT INTO `blog_comment_content` VALUES ('31', '123');
INSERT INTO `blog_comment_content` VALUES ('32', '23');
INSERT INTO `blog_comment_content` VALUES ('33', '121131');
INSERT INTO `blog_comment_content` VALUES ('34', '121');
INSERT INTO `blog_comment_content` VALUES ('35', '121');
INSERT INTO `blog_comment_content` VALUES ('36', '34');
INSERT INTO `blog_comment_content` VALUES ('37', '12231');
INSERT INTO `blog_comment_content` VALUES ('38', 'yes');
INSERT INTO `blog_comment_content` VALUES ('39', 'noaho1');
INSERT INTO `blog_comment_content` VALUES ('40', '12121');
INSERT INTO `blog_comment_content` VALUES ('41', '1312321');
INSERT INTO `blog_comment_content` VALUES ('42', '12121');
INSERT INTO `blog_comment_content` VALUES ('43', '12121');
INSERT INTO `blog_comment_content` VALUES ('44', '121321');
INSERT INTO `blog_comment_content` VALUES ('45', '1212121');
INSERT INTO `blog_comment_content` VALUES ('46', '121211');
INSERT INTO `blog_comment_content` VALUES ('47', '121321312');
INSERT INTO `blog_comment_content` VALUES ('48', '你好我');
INSERT INTO `blog_comment_content` VALUES ('49', '12');
INSERT INTO `blog_comment_content` VALUES ('50', '你收');
INSERT INTO `blog_comment_content` VALUES ('51', '121');
INSERT INTO `blog_comment_content` VALUES ('52', '122');
INSERT INTO `blog_comment_content` VALUES ('53', '121');
INSERT INTO `blog_comment_content` VALUES ('54', '12');
INSERT INTO `blog_comment_content` VALUES ('55', '12');
INSERT INTO `blog_comment_content` VALUES ('56', '12');
INSERT INTO `blog_comment_content` VALUES ('57', '132');
INSERT INTO `blog_comment_content` VALUES ('58', '121');
INSERT INTO `blog_comment_content` VALUES ('59', '2121');
INSERT INTO `blog_comment_content` VALUES ('60', '21211212');
INSERT INTO `blog_comment_content` VALUES ('61', '21211212');
INSERT INTO `blog_comment_content` VALUES ('62', '21211212');
INSERT INTO `blog_comment_content` VALUES ('63', '21211212');
INSERT INTO `blog_comment_content` VALUES ('64', '121');
INSERT INTO `blog_comment_content` VALUES ('65', '12121212');
INSERT INTO `blog_comment_content` VALUES ('66', '你好我');
INSERT INTO `blog_comment_content` VALUES ('67', 'yes');
INSERT INTO `blog_comment_content` VALUES ('68', '你怎么了');
INSERT INTO `blog_comment_content` VALUES ('69', '也是');
INSERT INTO `blog_comment_content` VALUES ('70', '313123');
INSERT INTO `blog_comment_content` VALUES ('71', '1');
INSERT INTO `blog_comment_content` VALUES ('72', '你好我');
INSERT INTO `blog_comment_content` VALUES ('73', '1212');
INSERT INTO `blog_comment_content` VALUES ('74', '你好我');
INSERT INTO `blog_comment_content` VALUES ('75', '你好我');
INSERT INTO `blog_comment_content` VALUES ('76', '你好');
INSERT INTO `blog_comment_content` VALUES ('77', 'haul');
INSERT INTO `blog_comment_content` VALUES ('78', 'yes');
INSERT INTO `blog_comment_content` VALUES ('79', 'yes');
INSERT INTO `blog_comment_content` VALUES ('80', 'yes');
INSERT INTO `blog_comment_content` VALUES ('81', '122231');
INSERT INTO `blog_comment_content` VALUES ('82', 'nihao1');
INSERT INTO `blog_comment_content` VALUES ('83', 'nnoa');
INSERT INTO `blog_comment_content` VALUES ('84', 'dd');
INSERT INTO `blog_comment_content` VALUES ('85', 'yes');
INSERT INTO `blog_comment_content` VALUES ('86', '你好');
INSERT INTO `blog_comment_content` VALUES ('87', '111');
INSERT INTO `blog_comment_content` VALUES ('88', '12');
INSERT INTO `blog_comment_content` VALUES ('89', '12');
INSERT INTO `blog_comment_content` VALUES ('90', '12');
INSERT INTO `blog_comment_content` VALUES ('91', '12');
INSERT INTO `blog_comment_content` VALUES ('92', '12');
INSERT INTO `blog_comment_content` VALUES ('93', '34');
INSERT INTO `blog_comment_content` VALUES ('94', '12');
INSERT INTO `blog_comment_content` VALUES ('95', '222');
INSERT INTO `blog_comment_content` VALUES ('96', '12');
INSERT INTO `blog_comment_content` VALUES ('97', '12');
INSERT INTO `blog_comment_content` VALUES ('98', '211');
INSERT INTO `blog_comment_content` VALUES ('99', '12');
INSERT INTO `blog_comment_content` VALUES ('100', '12');
INSERT INTO `blog_comment_content` VALUES ('101', '122');
INSERT INTO `blog_comment_content` VALUES ('102', '12');
INSERT INTO `blog_comment_content` VALUES ('103', '13');
INSERT INTO `blog_comment_content` VALUES ('104', '12');
INSERT INTO `blog_comment_content` VALUES ('105', '13');
INSERT INTO `blog_comment_content` VALUES ('106', '12');
INSERT INTO `blog_comment_content` VALUES ('107', '12');
INSERT INTO `blog_comment_content` VALUES ('108', '12');
INSERT INTO `blog_comment_content` VALUES ('109', '13');

-- ----------------------------
-- Table structure for `blog_favorite`
-- ----------------------------
DROP TABLE IF EXISTS `blog_favorite`;
CREATE TABLE `blog_favorite` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '用户id',
  `person_id` bigint NOT NULL COMMENT '收藏的用户id',
  `favorite_class_id` bigint NOT NULL COMMENT '收藏夹类别管理id',
  `article_id` bigint NOT NULL COMMENT '文章id',
  `active` bit(1) DEFAULT b'0' COMMENT '0:在推送列表里，1：不在',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` bit(1) DEFAULT b'0' COMMENT '0:未删除，1:已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='收藏夹';

-- ----------------------------
-- Records of blog_favorite
-- ----------------------------
INSERT INTO `blog_favorite` VALUES ('12', '6', '5', '7', '34', '', '2024-04-06 01:27:34', '2024-04-06 01:27:34', '');
INSERT INTO `blog_favorite` VALUES ('17', '5', '6', '6', '32', '', '2024-04-25 00:29:14', '2024-04-25 00:29:14', '');

-- ----------------------------
-- Table structure for `blog_favorite_class`
-- ----------------------------
DROP TABLE IF EXISTS `blog_favorite_class`;
CREATE TABLE `blog_favorite_class` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '用户id',
  `favorite_class_name` varchar(30) DEFAULT NULL COMMENT '收藏夹名字',
  `favorite_class_privacy` bit(1) DEFAULT b'0' COMMENT '0:公开，1:私密',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` bit(1) DEFAULT b'0' COMMENT '0:未删除，1:已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='收藏夹类别管理';

-- ----------------------------
-- Records of blog_favorite_class
-- ----------------------------
INSERT INTO `blog_favorite_class` VALUES ('6', '5', '前端', '', '2024-03-12 16:18:25', '2024-03-12 16:18:25', '');
INSERT INTO `blog_favorite_class` VALUES ('7', '6', 'nimz', '', '2024-03-12 18:17:15', '2024-03-12 18:17:15', '');
INSERT INTO `blog_favorite_class` VALUES ('8', '5', 'java', '', '2024-04-22 01:29:19', '2024-04-22 01:29:19', '');

-- ----------------------------
-- Table structure for `blog_feedback`
-- ----------------------------
DROP TABLE IF EXISTS `blog_feedback`;
CREATE TABLE `blog_feedback` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '用户id',
  `feedback_content_id` bigint NOT NULL COMMENT '反馈内容ID',
  `active` bit(1) DEFAULT b'0' COMMENT '0:未受理，1:已受理',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` bit(1) DEFAULT b'0' COMMENT '0:未删除，1:已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='反馈';

-- ----------------------------
-- Records of blog_feedback
-- ----------------------------
INSERT INTO `blog_feedback` VALUES ('1', '5', '1', '', '2024-04-18 23:16:18', '2024-04-18 23:16:18', '');
INSERT INTO `blog_feedback` VALUES ('2', '5', '2', '', '2024-04-18 23:53:13', '2024-04-18 23:53:13', '');
INSERT INTO `blog_feedback` VALUES ('3', '5', '3', '', '2024-04-18 23:53:16', '2024-04-18 23:53:16', '');
INSERT INTO `blog_feedback` VALUES ('4', '5', '4', '', '2024-04-18 23:53:18', '2024-04-18 23:53:18', '');
INSERT INTO `blog_feedback` VALUES ('5', '5', '5', '', '2024-04-18 23:53:20', '2024-04-18 23:53:20', '');
INSERT INTO `blog_feedback` VALUES ('6', '5', '6', '', '2024-04-18 23:53:22', '2024-04-18 23:53:22', '');
INSERT INTO `blog_feedback` VALUES ('10', '5', '10', '', '2024-04-19 02:18:20', '2024-04-19 02:18:20', '');
INSERT INTO `blog_feedback` VALUES ('11', '5', '11', '', '2024-04-19 02:18:23', '2024-04-19 02:18:23', '');
INSERT INTO `blog_feedback` VALUES ('12', '5', '12', '', '2024-04-19 02:22:20', '2024-04-19 02:22:20', '');
INSERT INTO `blog_feedback` VALUES ('13', '5', '13', '', '2024-04-19 02:22:24', '2024-04-19 02:22:24', '');
INSERT INTO `blog_feedback` VALUES ('14', '5', '14', '', '2024-04-19 02:33:57', '2024-04-19 02:33:57', '');
INSERT INTO `blog_feedback` VALUES ('15', '5', '15', '', '2024-04-19 02:34:06', '2024-04-19 02:34:06', '');

-- ----------------------------
-- Table structure for `blog_feedback_content`
-- ----------------------------
DROP TABLE IF EXISTS `blog_feedback_content`;
CREATE TABLE `blog_feedback_content` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `feedback_content` text NOT NULL COMMENT '反馈内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` bit(1) DEFAULT b'0' COMMENT '0:未删除，1:已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='反馈内容回复';

-- ----------------------------
-- Records of blog_feedback_content
-- ----------------------------
INSERT INTO `blog_feedback_content` VALUES ('1', '你好', '2024-04-18 23:16:17', '2024-04-18 23:16:17', '');
INSERT INTO `blog_feedback_content` VALUES ('2', '1111', '2024-04-18 23:53:12', '2024-04-18 23:53:12', '');
INSERT INTO `blog_feedback_content` VALUES ('3', '1111', '2024-04-18 23:53:15', '2024-04-18 23:53:15', '');
INSERT INTO `blog_feedback_content` VALUES ('4', '1111', '2024-04-18 23:53:17', '2024-04-18 23:53:17', '');
INSERT INTO `blog_feedback_content` VALUES ('5', '111', '2024-04-18 23:53:19', '2024-04-18 23:53:19', '');
INSERT INTO `blog_feedback_content` VALUES ('6', '111', '2024-04-18 23:53:22', '2024-04-18 23:53:22', '');
INSERT INTO `blog_feedback_content` VALUES ('7', '111', '2024-04-18 23:53:25', '2024-04-18 23:53:25', '');
INSERT INTO `blog_feedback_content` VALUES ('8', '1111212', '2024-04-18 23:53:27', '2024-04-18 23:53:27', '');
INSERT INTO `blog_feedback_content` VALUES ('9', '', '2024-04-19 00:34:56', '2024-04-19 00:34:56', '');
INSERT INTO `blog_feedback_content` VALUES ('10', '123132', '2024-04-19 02:18:19', '2024-04-19 02:18:19', '');
INSERT INTO `blog_feedback_content` VALUES ('11', '31321', '2024-04-19 02:18:22', '2024-04-19 02:18:22', '');
INSERT INTO `blog_feedback_content` VALUES ('12', '111111', '2024-04-19 02:22:19', '2024-04-19 02:22:19', '');
INSERT INTO `blog_feedback_content` VALUES ('13', '12', '2024-04-19 02:22:23', '2024-04-19 02:22:23', '');
INSERT INTO `blog_feedback_content` VALUES ('14', '1212', '2024-04-19 02:33:56', '2024-04-19 02:33:56', '');
INSERT INTO `blog_feedback_content` VALUES ('15', '12', '2024-04-19 02:34:06', '2024-04-19 02:34:06', '');

-- ----------------------------
-- Table structure for `blog_like_browse`
-- ----------------------------
DROP TABLE IF EXISTS `blog_like_browse`;
CREATE TABLE `blog_like_browse` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '点赞/浏览的用户id',
  `person_id` bigint NOT NULL COMMENT '被点赞/浏览的用户id',
  `article_id` bigint NOT NULL COMMENT '文章id',
  `like_browse_limiter` bit(1) DEFAULT b'0' COMMENT '0:点赞，1:浏览',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `active` bit(1) DEFAULT b'0' COMMENT '0:在推送列表里，1：不在',
  `deleted` bit(1) DEFAULT b'0' COMMENT '0:未删除，1:已删除',
  `stated` bit(1) DEFAULT b'0' COMMENT '0:未读，1:已读',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=248 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='点赞/浏览';

-- ----------------------------
-- Records of blog_like_browse
-- ----------------------------
INSERT INTO `blog_like_browse` VALUES ('195', '5', '6', '32', '', '2024-04-20 03:39:13', '2024-04-20 03:39:13', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('196', '5', '6', '32', '', '2024-04-20 03:40:17', '2024-04-20 03:40:17', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('197', '5', '6', '32', '', '2024-04-20 03:45:37', '2024-04-20 03:45:37', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('198', '6', '5', '34', '', '2024-04-20 03:50:38', '2024-04-20 03:50:38', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('199', '6', '5', '34', '', '2024-04-20 04:13:18', '2024-04-20 04:13:18', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('200', '6', '5', '34', '', '2024-04-20 04:13:18', '2024-04-20 04:13:18', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('201', '6', '5', '34', '', '2024-04-20 04:13:42', '2024-04-20 04:13:42', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('202', '6', '5', '34', '', '2024-04-20 04:13:50', '2024-04-20 04:13:50', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('203', '6', '5', '34', '', '2024-04-20 04:14:27', '2024-04-20 04:14:27', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('204', '6', '5', '34', '', '2024-04-20 04:15:42', '2024-04-20 04:15:42', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('205', '5', '6', '32', '', '2024-04-20 04:49:05', '2024-04-20 04:49:05', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('206', '5', '6', '33', '', '2024-04-20 05:14:42', '2024-04-20 05:14:42', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('207', '5', '6', '33', '', '2024-04-20 05:17:58', '2024-04-20 05:17:58', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('208', '5', '6', '32', '', '2024-04-20 05:51:32', '2024-04-20 05:51:32', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('209', '5', '6', '32', '', '2024-04-20 05:52:26', '2024-04-20 05:52:26', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('210', '5', '6', '32', '', '2024-04-20 05:52:36', '2024-04-20 05:52:36', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('211', '5', '6', '32', '', '2024-04-20 05:55:23', '2024-04-20 05:55:23', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('213', '5', '6', '32', '', '2024-04-22 01:35:45', '2024-04-22 01:35:45', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('214', '5', '6', '33', '', '2024-04-22 01:36:51', '2024-04-22 01:36:51', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('215', '5', '6', '32', '', '2024-04-22 02:05:12', '2024-04-22 02:05:12', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('216', '6', '5', '34', '', '2024-04-22 02:39:14', '2024-04-22 02:39:14', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('217', '6', '5', '34', '', '2024-04-22 03:14:00', '2024-04-22 03:14:00', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('218', '6', '5', '34', '', '2024-04-22 03:29:14', '2024-04-22 03:29:14', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('219', '6', '5', '34', '', '2024-04-22 03:33:43', '2024-04-22 03:33:43', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('220', '6', '5', '34', '', '2024-04-22 03:34:56', '2024-04-22 03:34:56', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('221', '0', '5', '34', '', '2024-04-22 03:35:08', '2024-04-22 03:35:08', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('222', '6', '5', '34', '', '2024-04-22 03:36:22', '2024-04-22 03:36:22', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('223', '6', '5', '34', '', '2024-04-22 03:36:22', '2024-04-22 03:36:22', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('224', '0', '5', '34', '', '2024-04-22 14:27:05', '2024-04-22 14:27:05', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('225', '0', '6', '32', '', '2024-04-24 00:25:06', '2024-04-24 00:25:06', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('226', '5', '6', '32', '', '2024-04-24 00:25:42', '2024-04-24 00:25:42', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('227', '5', '6', '32', '', '2024-04-24 00:29:26', '2024-04-24 00:29:26', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('228', '5', '6', '32', '', '2024-04-24 00:36:26', '2024-04-24 00:36:26', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('229', '5', '6', '32', '', '2024-04-24 00:42:26', '2024-04-24 00:42:26', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('230', '5', '6', '32', '', '2024-04-24 00:49:55', '2024-04-24 00:49:55', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('231', '5', '6', '32', '', '2024-04-24 00:50:29', '2024-04-24 00:50:29', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('232', '5', '6', '32', '', '2024-04-24 00:51:57', '2024-04-24 00:51:57', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('233', '5', '6', '33', '', '2024-04-24 19:50:26', '2024-04-24 19:50:26', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('234', '5', '6', '33', '', '2024-04-24 19:52:40', '2024-04-24 19:52:40', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('235', '6', '5', '14', '', '2024-04-24 20:13:39', '2024-04-24 20:13:39', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('236', '6', '5', '34', '', '2024-04-24 23:39:33', '2024-04-24 23:39:33', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('237', '5', '6', '33', '', '2024-04-24 23:58:13', '2024-04-24 23:58:13', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('238', '5', '6', '33', '', '2024-04-25 00:07:18', '2024-04-25 00:07:18', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('239', '5', '6', '33', '', '2024-04-25 00:07:35', '2024-04-25 00:07:35', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('240', '5', '6', '33', '', '2024-04-25 00:08:11', '2024-04-25 00:08:11', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('241', '5', '6', '33', '', '2024-04-25 00:08:27', '2024-04-25 00:08:27', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('242', '5', '6', '32', '', '2024-04-25 00:29:08', '2024-04-25 00:29:08', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('243', '6', '5', '34', '', '2024-04-25 01:20:28', '2024-04-25 01:20:28', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('244', '5', '6', '40', '', '2024-04-25 08:30:45', '2024-04-25 08:30:45', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('245', '5', '6', '40', '', '2024-04-25 08:31:00', '2024-04-25 08:31:00', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('246', '5', '6', '32', '', '2024-04-25 08:32:32', '2024-04-25 08:32:32', '', '', '');
INSERT INTO `blog_like_browse` VALUES ('247', '5', '6', '32', '', '2024-04-25 08:34:50', '2024-04-25 08:34:50', '', '', '');

-- ----------------------------
-- Table structure for `blog_user`
-- ----------------------------
DROP TABLE IF EXISTS `blog_user`;
CREATE TABLE `blog_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_name` varchar(12) DEFAULT NULL COMMENT '用户名',
  `user_pass` varchar(16) DEFAULT NULL COMMENT '密码',
  `user_email` varchar(20) DEFAULT NULL COMMENT 'QQ邮箱',
  `user_phone` varchar(20) DEFAULT NULL COMMENT '电话号码',
  `user_sex` bit(1) DEFAULT b'0' COMMENT '0:女，1:男',
  `user_image` text COMMENT '头像地址',
  `user_intro` text COMMENT '个人简介',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` bit(1) DEFAULT b'0' COMMENT '0:未删除，1:已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_email` (`user_email`,`user_phone`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户';

-- ----------------------------
-- Records of blog_user
-- ----------------------------
INSERT INTO `blog_user` VALUES ('5', '任家', '123456', '3029364473@qq.com', '13088196807', '', 'https://blog-renjia.oss-cn-chengdu.aliyuncs.com/static/images/5/fd5af464d4cc4f54aa61c0af11770076.webp', '', null, '2024-04-25 01:54:15', '');
INSERT INTO `blog_user` VALUES ('6', '繁星积', '123456', '241786471@qq.com', '', '', 'https://blog-renjia.oss-cn-chengdu.aliyuncs.com/static/images/6/2092b29bd43749f3bb05b6716a50b99b.webp', '', '2024-03-12 09:31:38', '2024-03-12 09:31:38', '');

-- ----------------------------
-- Table structure for `blog_user_noti_set`
-- ----------------------------
DROP TABLE IF EXISTS `blog_user_noti_set`;
CREATE TABLE `blog_user_noti_set` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT '0' COMMENT '用户id',
  `private_letter_active` bit(1) DEFAULT b'0' COMMENT '私信,0:不推送，1:推送',
  `comment_active` bit(1) DEFAULT b'0' COMMENT '评论，0:不推送，1:推送',
  `follow_active` bit(1) DEFAULT b'0' COMMENT '关注，0:不推送，1:推送',
  `collect_active` bit(1) DEFAULT b'0' COMMENT '收藏，0:不推送，1:推送',
  `like_active` bit(1) DEFAULT b'0' COMMENT '点赞，0:不推送，1:推送',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` bit(1) DEFAULT b'0' COMMENT '0:未删除，1:已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户消息推送状态';

-- ----------------------------
-- Records of blog_user_noti_set
-- ----------------------------
INSERT INTO `blog_user_noti_set` VALUES ('9', '5', '', '', '', '', '', '2024-04-24 22:38:20', '2024-04-25 08:37:21', '');
INSERT INTO `blog_user_noti_set` VALUES ('10', '6', '', '', '', '', '', '2024-04-24 23:15:31', '2024-04-24 23:15:31', '');
