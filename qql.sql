/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.5.40 : Database - qql
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`qql` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `qql`;

/*Table structure for table `course` */

DROP TABLE IF EXISTS `course`;

CREATE TABLE `course` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '课程名称',
  `score` int(11) DEFAULT NULL COMMENT '学分',
  `times` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '上课时间',
  `state` tinyint(1) DEFAULT NULL COMMENT '是否开课',
  `teacher_id` int(11) DEFAULT NULL COMMENT '授课老师id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

/*Data for the table `course` */

insert  into `course`(`id`,`name`,`score`,`times`,`state`,`teacher_id`) values (1,'大学物理',10,'40',0,17),(2,'高等数学',10,'45',NULL,16),(3,'大学英语',10,'30',NULL,16);

/*Table structure for table `student_course` */

DROP TABLE IF EXISTS `student_course`;

CREATE TABLE `student_course` (
  `student_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  PRIMARY KEY (`student_id`,`course_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

/*Data for the table `student_course` */

insert  into `student_course`(`student_id`,`course_id`) values (28,1),(28,2);

/*Table structure for table `sys_anlian` */

DROP TABLE IF EXISTS `sys_anlian`;

CREATE TABLE `sys_anlian` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `src` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `author` varchar(50) DEFAULT NULL,
  `release_time` varchar(50) DEFAULT NULL,
  `introduce` varchar(255) DEFAULT NULL,
  `book_type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `sys_anlian` */

insert  into `sys_anlian`(`id`,`src`,`title`,`author`,`release_time`,`introduce`,`book_type`) values (1,'https://img1.baidu.com/it/u=3690405979,1658731319&fm=253&fmt=auto&app=138&f=JPEG?w=819&h=423','PHP','张三','2022-5-7','PHP是?种易于学习和使?的服务器端脚本语?。只需要很少的编程知识你就能使?PHP建??个真正交互的WEB站点。欢迎?家阅读！','编程'),(2,'https://img2.baidu.com/it/u=3995721299,3945742051&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=281','PYTHON','李总','2022-5-7','Python解释器易于扩展，可以使用C语言或C++（或者其他可以通过C调用的语言）扩展新的功能和数据类型。 [4]  Python 也可用于可定制化软件中的扩展程序语言。Python丰富的标准库，提供了适用于各个主要系统平台的源码或机器码','编程'),(3,'https://img0.baidu.com/it/u=642388306,1845997403&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=368','Vue','法人','2022-5-7','Vue.js是用于构建交互式的Web界面的库,它提供了 MVVM数据绑定和一个可组合的组件系统,具有简单、灵活的API。从技术上讲，Vue.js集中在MVVM模式上的视图模型层,并通过双向数据绑定连接视图和模型','编程'),(4,'https://img0.baidu.com/it/u=3654026550,3264153565&fm=253&fmt=auto&app=138&f=JPEG?w=560&h=310','React','凤凰','2022-5-7','React是用于构建用户界面的JavaScript库','编程'),(5,'https://img1.baidu.com/it/u=990038585,3984304090&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=501','Spring','格式','2022-5-7','Spring属于开源框架，Spring是于2003年流行起来的一个轻量级的Java开发基础框架，它是为了解决企业应用开发的复杂性而提供的解决方案','编程'),(6,'https://img0.baidu.com/it/u=536714826,1395654827&fm=253&fmt=auto&app=138&f=PNG?w=667&h=500','SpringMvc','突然','2022-5-7','我们可以看到，在 Spring 的基本架构中，红色圈起来的 Spring Web MVC ，也就是本系列的主角 SpringMVC，它是属于Spring基本架构里面的一个组成部分，属于SpringFrameWork的后续产品，已经融合在Spring Web Flow里面，所以我们在后期和 Spring 进行整合的时候，几乎不需要别的什么配置','编程'),(7,'https://img0.baidu.com/it/u=2698410332,2050932400&fm=253&fmt=auto&app=138&f=PNG?w=774&h=364','SpringBoot','感到','2022-5-7','SpringBoot是一个全新的框架，其设计目的是用来简化spring应用初始搭建以及开发过程。','编程'),(8,'https://img1.baidu.com/it/u=1412260604,1880570622&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500','JAVA','小覃','2022-5-7','简单地说，Java 是由 Sun Microsystems 公司于 1995 年推出的一门面向对象程序设计语言。','编程');

/*Table structure for table `sys_dict` */

DROP TABLE IF EXISTS `sys_dict`;

CREATE TABLE `sys_dict` (
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名称',
  `value` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '内容',
  `type` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '类型'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

/*Data for the table `sys_dict` */

insert  into `sys_dict`(`name`,`value`,`type`) values ('user','el-icon-user','icon'),('house','el-icon-house','icon'),('menu','el-icon-menu','icon'),('s-custom','el-icon-s-custom','icon'),('s-grid','el-icon-s-grid','icon'),('document','el-icon-document','icon'),('coffee','el-icon-coffee\r\n','icon'),('s-marketing','el-icon-s-marketing','icon');

/*Table structure for table `sys_file` */

DROP TABLE IF EXISTS `sys_file`;

CREATE TABLE `sys_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '文件名称',
  `type` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '文件类型',
  `size` bigint(20) DEFAULT NULL COMMENT '文件大小(kb)',
  `url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '下载链接',
  `md5` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '文件md5',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '是否删除',
  `enable` tinyint(1) DEFAULT '1' COMMENT '是否禁用链接',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

/*Data for the table `sys_file` */

insert  into `sys_file`(`id`,`name`,`type`,`size`,`url`,`md5`,`is_delete`,`enable`) values (35,'小聪明.jpg','jpg',32,'http://localhost:9090/file/1a6fd86d509b48638fb2a2d56722482e.jpg','cb887a9d64563352edce80cf50296cc5',0,1),(36,'笨蛋.png','png',445,'http://localhost:9090/file/0d0e9f0472f34a4bb211029b8f589b34.png','793fd534fa705475eb3358f68c87ec68',0,1),(41,'php.png','png',56,'http://localhost:9090/file/8ab7b9f9106e4d1cabf01559d8cc3297.png','050df6119399582fda666834870608d7',0,1),(42,'小可爱','png',100,'http://localhost:9090/file/8dd6162f1ff44a82b6fe5d165b3e5bf6.png','0f1337b5c0ebf68f67718fcf42d1322f',0,1);

/*Table structure for table `sys_gong` */

DROP TABLE IF EXISTS `sys_gong`;

CREATE TABLE `sys_gong` (
  `gid` int(11) NOT NULL AUTO_INCREMENT,
  `gname` varchar(50) NOT NULL,
  PRIMARY KEY (`gid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `sys_gong` */

insert  into `sys_gong`(`gid`,`gname`) values (1,'字节跳动'),(2,'阿里巴巴'),(3,'腾讯'),(4,'华为');

/*Table structure for table `sys_menu` */

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名称',
  `path` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '路径',
  `icon` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图标',
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述',
  `pid` int(11) DEFAULT NULL COMMENT '父级id',
  `page_path` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '页面路径',
  `sort_num` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

/*Data for the table `sys_menu` */

insert  into `sys_menu`(`id`,`name`,`path`,`icon`,`description`,`pid`,`page_path`,`sort_num`) values (2,'激励','/dashbord','el-icon-s-marketing','11',NULL,'Dashbord',100),(4,'系统管理',NULL,'el-icon-s-grid',NULL,NULL,NULL,300),(5,'用户管理','/user','el-icon-user',NULL,4,'User',301),(6,'角色管理','/role','el-icon-s-custom',NULL,4,'Role',302),(7,'菜单管理','/menu','el-icon-menu',NULL,4,'Menu',303),(8,'文件管理','/file','el-icon-document',NULL,4,'File',304),(9,'首页管理','/donate','el-icon-coffee\r\n',NULL,NULL,'Donate',200),(10,'主页','/home','el-icon-house',NULL,NULL,'Home',0),(39,'课程管理','/course','el-icon-menu',NULL,NULL,'Course',201),(40,'高德地图','/map','el-icon-house',NULL,NULL,'Map',999),(41,'面试管理','/main','el-icon-picture-outline-round',NULL,NULL,'Main',1001);

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名称',
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述',
  `flag` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '唯一标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`name`,`description`,`flag`) values (1,'管理员','管理员','ROLE_ADMIN'),(2,'学生','学生','ROLE_STUDENT'),(3,'老师','老师','ROLE_TEACHER');

/*Table structure for table `sys_role_menu` */

DROP TABLE IF EXISTS `sys_role_menu`;

CREATE TABLE `sys_role_menu` (
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `menu_id` int(11) NOT NULL COMMENT '菜单id',
  PRIMARY KEY (`role_id`,`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='角色菜单关系表';

/*Data for the table `sys_role_menu` */

insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,2),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),(1,39),(1,40),(1,41),(3,2),(3,10),(3,39);

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '密码',
  `nickname` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '昵称',
  `email` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '电话',
  `address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '地址',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `avatar_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像',
  `role` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`username`,`password`,`nickname`,`email`,`phone`,`address`,`create_time`,`avatar_url`,`role`) values (1,'admin','admin','张三','admin@qq.com','13988997788','湖南梦游地区','2022-01-22 21:10:27','http://localhost:9090/file/8686de56475b432284aec965fc64f853.png','ROLE_ADMIN'),(16,'222','123','李四','1389@qq.com','12312312','湖南地狱梦想','2022-02-26 22:10:14','https://img2.baidu.com/it/u=2354211131,1926992901&fm=253&fmt=auto&app=138&f=JPEG?w=640&h=458','ROLE_TEACHER'),(17,'333','123','王五','333@qq.com','123147987','北京一环','2022-02-26 22:10:18','https://img2.baidu.com/it/u=2405596874,2530509328&fm=253&fmt=auto&app=138&f=JPEG?w=400&h=300','ROLE_TEACHER'),(18,'nzz','123','哪吒','123@qq.com','12312333','火焰山','2022-03-29 16:59:44','https://img1.baidu.com/it/u=3085166806,3823761585&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=452','ROLE_STUDENT'),(19,'yss','123','亚瑟','3','3','3','2022-04-29 16:59:44','','ROLE_STUDENT'),(20,'lxx','123','李信','2','2','2','2022-05-29 17:12:04','2','ROLE_STUDENT'),(25,'sir','123','安琪拉',NULL,NULL,NULL,'2022-06-08 17:00:47',NULL,'ROLE_STUDENT'),(26,'err',NULL,'妲己','11','1','1','2022-07-08 17:20:01',NULL,'ROLE_STUDENT'),(29,'ffff','123',NULL,NULL,NULL,NULL,'2022-12-10 11:53:31',NULL,'ROLE_STUDENT');

/*Table structure for table `sys_xue` */

DROP TABLE IF EXISTS `sys_xue`;

CREATE TABLE `sys_xue` (
  `xid` int(11) NOT NULL AUTO_INCREMENT,
  `li` varchar(50) NOT NULL,
  PRIMARY KEY (`xid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `sys_xue` */

insert  into `sys_xue`(`xid`,`li`) values (1,'大专'),(2,'本科'),(3,'研究生'),(4,'硕士');

/*Table structure for table `sys_yuan` */

DROP TABLE IF EXISTS `sys_yuan`;

CREATE TABLE `sys_yuan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `yname` varchar(50) NOT NULL,
  `age` int(11) DEFAULT NULL,
  `gender` char(1) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `state` tinyint(1) DEFAULT NULL COMMENT '是否开课',
  `xid` int(11) DEFAULT NULL,
  `gid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_sys_yuan_sys_gong` (`gid`),
  KEY `fk_sys_yuan_sys_xue` (`xid`),
  CONSTRAINT `fk_sys_yuan_sys_gong` FOREIGN KEY (`gid`) REFERENCES `sys_gong` (`gid`),
  CONSTRAINT `fk_sys_yuan_sys_xue` FOREIGN KEY (`xid`) REFERENCES `sys_xue` (`xid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `sys_yuan` */

insert  into `sys_yuan`(`id`,`yname`,`age`,`gender`,`address`,`state`,`xid`,`gid`) values (1,'张三',18,'1','湖南省长沙市',1,1,1),(2,'萌萌',38,'2','湖南省常德市',0,2,2),(3,'李四',28,'1','湖南省永州市',1,3,3),(4,'小花',21,'2','湖南省张家界市',0,4,4),(5,'xx',12,'1','112',1,1,1);

/*Table structure for table `t_comment` */

DROP TABLE IF EXISTS `t_comment`;

CREATE TABLE `t_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '内容',
  `user_id` int(11) DEFAULT NULL COMMENT '评论人id',
  `time` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '评论时间',
  `pid` int(11) DEFAULT NULL COMMENT '父id',
  `origin_id` int(11) DEFAULT NULL COMMENT '最上级评论id',
  `article_id` int(11) DEFAULT NULL COMMENT '关联文章的id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

/*Data for the table `t_comment` */

insert  into `t_comment`(`id`,`content`,`user_id`,`time`,`pid`,`origin_id`,`article_id`) values (1,'测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试',1,'2022-03-22 20:00:00',NULL,NULL,1),(2,'123',NULL,NULL,NULL,NULL,NULL),(5,'回复内容',1,'2022-03-22 21:01:00',NULL,NULL,1),(6,'4444',1,'2022-03-22 21:03:15',4,4,1),(7,'5555',1,'2022-03-22 21:04:11',4,4,1),(8,'444444',1,'2022-03-22 21:29:55',7,7,1),(9,'5555',1,'2022-03-22 21:30:04',7,7,1),(10,'666',1,'2022-03-22 21:34:05',7,4,1),(11,'甄姬真的好大好大！！',16,'2022-03-22 21:38:26',10,4,1),(13,'哈哈哈哈，我是ddd',28,'2022-03-22 21:46:01',12,12,1),(14,'我是李信，我很萌',20,'2022-03-22 21:46:48',13,12,1),(15,'我在回复ddd',20,'2022-03-22 21:47:03',13,12,1),(16,'我是李信',20,'2022-03-22 21:48:19',4,4,1),(17,'33333',20,'2022-03-22 21:48:42',5,5,1),(19,'我是李信嗯嗯嗯',20,'2022-03-22 21:49:21',1,1,1),(21,'哈哈哈 我是ddd',28,'2022-03-22 21:50:04',20,1,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
