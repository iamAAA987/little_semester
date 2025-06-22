-- 创建数据库
CREATE DATABASE IF NOT EXISTS `system` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `system`;

-- 创建用户账户表
CREATE TABLE `sys_account` (
  `account_id` varchar(20) NOT NULL COMMENT '账户ID（手机号）',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `account_name` varchar(50) DEFAULT NULL COMMENT '账户名称',
  `account_sex` int DEFAULT NULL COMMENT '性别（0-女，1-男）',
  `account_img` varchar(500) DEFAULT NULL COMMENT '头像图片',
  `del_tag` int DEFAULT '0' COMMENT '删除标记（0-未删除，1-已删除）',
  `created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `statu` int DEFAULT '1' COMMENT '状态（0-禁用，1-正常）',
  PRIMARY KEY (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户账户表';

-- 创建商家表
CREATE TABLE `sys_business` (
  `business_id` bigint NOT NULL AUTO_INCREMENT COMMENT '商家编号',
  `business_name` varchar(100) NOT NULL COMMENT '商家名称',
  `business_address` varchar(200) DEFAULT NULL COMMENT '商家地址',
  `business_explain` text COMMENT '商家介绍',
  `business_img` varchar(500) DEFAULT NULL COMMENT '商家图片',
  `star_price` decimal(10,2) DEFAULT '0.00' COMMENT '起送费',
  `delivery_price` decimal(10,2) DEFAULT '0.00' COMMENT '配送费',
  `del_tag` int DEFAULT '0' COMMENT '删除标记（0-未删除，1-已删除）',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注',
  `created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `statu` int DEFAULT '1' COMMENT '状态（0-禁用，1-正常）',
  PRIMARY KEY (`business_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商家表';

-- 创建商品分类表
CREATE TABLE `sys_category` (
  `category_id` int NOT NULL AUTO_INCREMENT COMMENT '分类编号',
  `category_name` varchar(50) NOT NULL COMMENT '分类名称',
  `category_explain` varchar(200) DEFAULT NULL COMMENT '分类说明',
  `order_num` int DEFAULT '0' COMMENT '显示顺序',
  `created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `statu` int DEFAULT '1' COMMENT '状态（0-禁用，1-正常）',
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品分类表';

-- 创建商家分类关联表
CREATE TABLE `sys_business_category` (
  `business_category_id` int NOT NULL AUTO_INCREMENT COMMENT '商家分类关联编号',
  `business_id` bigint NOT NULL COMMENT '商家编号',
  `category_id` int NOT NULL COMMENT '分类编号',
  `created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `statu` int DEFAULT '1' COMMENT '状态（0-禁用，1-正常）',
  PRIMARY KEY (`business_category_id`),
  KEY `idx_business_id` (`business_id`),
  KEY `idx_category_id` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商家分类关联表';

-- 创建商品表
CREATE TABLE `sys_goods` (
  `goods_id` int NOT NULL AUTO_INCREMENT COMMENT '商品编号',
  `goods_name` varchar(100) NOT NULL COMMENT '商品名称',
  `goods_explain` text COMMENT '商品介绍',
  `goods_img` varchar(500) DEFAULT NULL COMMENT '商品图片',
  `goods_price` decimal(10,2) NOT NULL COMMENT '商品价格',
  `business_id` bigint NOT NULL COMMENT '所属商家编号',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注',
  `sold_num` int DEFAULT '0' COMMENT '销售数量',
  `created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `statu` int DEFAULT '1' COMMENT '状态（0-禁用，1-正常）',
  PRIMARY KEY (`goods_id`),
  KEY `idx_business_id` (`business_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品表';

-- 创建购物车表
CREATE TABLE `sys_cart` (
  `cart_id` int NOT NULL AUTO_INCREMENT COMMENT '购物车编号',
  `account_id` varchar(20) NOT NULL COMMENT '账户ID',
  `goods_id` int NOT NULL COMMENT '商品编号',
  `quantity` int NOT NULL DEFAULT '1' COMMENT '数量',
  `created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `statu` int DEFAULT '1' COMMENT '状态（0-禁用，1-正常）',
  PRIMARY KEY (`cart_id`),
  KEY `idx_account_id` (`account_id`),
  KEY `idx_goods_id` (`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='购物车表';

-- 插入一些测试数据

-- 插入商品分类
INSERT INTO `sys_category` (`category_name`, `category_explain`, `order_num`) VALUES
('快餐便当', '各种快餐和便当', 1),
('特色菜系', '地方特色菜系', 2),
('甜点饮品', '甜点和各种饮品', 3),
('小吃夜宵', '各种小吃和夜宵', 4);

-- 插入商家数据
INSERT INTO `sys_business` (`business_name`, `business_address`, `business_explain`, `star_price`, `delivery_price`) VALUES
('老王炒饭', '北京市朝阳区某某街道123号', '专业炒饭20年，口味独特', 20.00, 5.00),
('小李烧烤', '北京市海淀区某某路456号', '新鲜食材，现烤现卖', 30.00, 8.00),
('小张奶茶', '北京市西城区某某街789号', '手工制作，口感醇厚', 15.00, 3.00);

-- 插入商家分类关联
INSERT INTO `sys_business_category` (`business_id`, `category_id`) VALUES
(1, 1), -- 老王炒饭 - 快餐便当
(2, 4), -- 小李烧烤 - 小吃夜宵
(3, 3); -- 小张奶茶 - 甜点饮品

-- 插入商品数据
INSERT INTO `sys_goods` (`goods_name`, `goods_explain`, `goods_price`, `business_id`, `sold_num`) VALUES
('蛋炒饭', '精选大米，新鲜鸡蛋，口感香滑', 12.00, 1, 100),
('扬州炒饭', '传统工艺，配料丰富', 15.00, 1, 80),
('羊肉串', '新鲜羊肉，现烤现卖', 5.00, 2, 200),
('鸡翅', '秘制腌料，外酥里嫩', 8.00, 2, 150),
('珍珠奶茶', '手工珍珠，香浓奶茶', 12.00, 3, 120),
('柠檬茶', '新鲜柠檬，清爽解腻', 10.00, 3, 90); 