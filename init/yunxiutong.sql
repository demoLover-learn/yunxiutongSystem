/*
 Navicat Premium Data Transfer

 Source Server         : 学习专用
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3306
 Source Schema         : yunxiutong

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 12/09/2026 14:09:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
CREATE DATABASE IF NOT EXISTS yunxiutong DEFAULT CHARACTER SET utf8mb4;
USE yunxiutong;

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '管理员ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账号',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '姓名',
  `role` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态:1正常 0禁用',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '管理员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES (1, 'admin', '$2a$10$L0zc5zHeAEr/SftqkeYlLODp/oH9eYRJ806CvIwaqEXO.urrpTx22', '系统管理员', 'ADMIN', 1, '2026-01-01 00:00:00', '2026-09-11 13:41:19');
INSERT INTO `employee` VALUES (2, 'admin2', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '运维小二', 'ADMIN', 1, '2026-03-01 00:00:00', '2026-06-01 20:22:13');

-- ----------------------------
-- Table structure for grab_order_record
-- ----------------------------
DROP TABLE IF EXISTS `grab_order_record`;
CREATE TABLE `grab_order_record`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `order_id` bigint NOT NULL COMMENT '工单ID',
  `worker_id` bigint NOT NULL COMMENT '服务人员ID',
  `grab_result` tinyint NOT NULL COMMENT '抢单结果:1成功 0失败',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_id`(`order_id` ASC) USING BTREE,
  INDEX `idx_worker_id`(`worker_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '抢单记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of grab_order_record
-- ----------------------------
INSERT INTO `grab_order_record` VALUES (1, 21, 3, 0, '2026-04-26 17:05:00');
INSERT INTO `grab_order_record` VALUES (2, 27, 3, 0, '2026-05-28 16:05:00');
INSERT INTO `grab_order_record` VALUES (3, 27, 5, 0, '2026-05-28 16:10:00');
INSERT INTO `grab_order_record` VALUES (4, 29, 3, 0, '2026-06-01 07:05:00');
INSERT INTO `grab_order_record` VALUES (5, 30, 2, 0, '2026-06-01 11:35:00');

-- ----------------------------
-- Table structure for service_category
-- ----------------------------
DROP TABLE IF EXISTS `service_category`;
CREATE TABLE `service_category`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名称',
  `sort` int NULL DEFAULT 0 COMMENT '排序',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态:1启用 0禁用',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '服务分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of service_category
-- ----------------------------
INSERT INTO `service_category` VALUES (1, '家电维修', 1, 1, '2026-01-01 00:00:00', '2026-06-01 20:22:13');
INSERT INTO `service_category` VALUES (2, '管道疏通', 2, 1, '2026-01-01 00:00:00', '2026-06-01 20:22:13');
INSERT INTO `service_category` VALUES (3, '保洁清洗', 3, 1, '2026-01-01 00:00:00', '2026-06-01 20:22:13');
INSERT INTO `service_category` VALUES (4, '水电维修', 4, 1, '2026-01-01 00:00:00', '2026-06-01 20:22:13');

-- ----------------------------
-- Table structure for service_item
-- ----------------------------
DROP TABLE IF EXISTS `service_item`;
CREATE TABLE `service_item`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '服务项ID',
  `category_id` bigint NOT NULL COMMENT '分类ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '服务名称',
  `price` decimal(10, 2) NOT NULL COMMENT '基础价格',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '服务说明',
  `duration_minutes` int NULL DEFAULT NULL COMMENT '预计服务时长(分钟)',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态:1上架 0下架',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_category_id`(`category_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '服务项目表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of service_item
-- ----------------------------
INSERT INTO `service_item` VALUES (1, 1, '空调维修', 150.00, '挂机/柜机不制冷、漏水、异响维修', 60, 1, '2026-01-01 00:00:00', '2026-06-01 20:22:13');
INSERT INTO `service_item` VALUES (2, 1, '冰箱维修', 200.00, '不制冷、结冰、漏水、噪音大', 90, 1, '2026-01-01 00:00:00', '2026-06-01 20:22:13');
INSERT INTO `service_item` VALUES (3, 2, '马桶疏通', 100.00, '机械疏通+高压清洗', 30, 1, '2026-01-01 00:00:00', '2026-06-01 20:22:13');
INSERT INTO `service_item` VALUES (4, 2, '地漏疏通', 80.00, '厨房/浴室/阳台地漏', 30, 1, '2026-01-01 00:00:00', '2026-06-01 20:22:13');
INSERT INTO `service_item` VALUES (5, 3, '全屋保洁', 300.00, '三室一厅标准，含擦窗/厨卫深度', 180, 1, '2026-01-01 00:00:00', '2026-06-01 20:22:13');
INSERT INTO `service_item` VALUES (6, 3, '开荒保洁', 500.00, '新装修/搬家后首次保洁', 240, 1, '2026-01-01 00:00:00', '2026-06-01 20:22:13');
INSERT INTO `service_item` VALUES (7, 4, '灯具安装', 120.00, '吸顶灯/吊灯/筒灯安装', 45, 1, '2026-01-01 00:00:00', '2026-06-01 20:22:13');
INSERT INTO `service_item` VALUES (8, 4, '电路排查', 180.00, '跳闸/短路排查+维修', 60, 0, '2026-01-01 00:00:00', '2026-06-01 20:22:13');

-- ----------------------------
-- Table structure for service_order
-- ----------------------------
DROP TABLE IF EXISTS `service_order`;
CREATE TABLE `service_order`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '工单ID',
  `order_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单编号',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `service_item_id` bigint NOT NULL COMMENT '服务项ID',
  `worker_id` bigint NULL DEFAULT NULL COMMENT '服务人员ID',
  `address_id` bigint NOT NULL COMMENT '服务地址ID',
  `appointment_time` datetime NOT NULL COMMENT '预约上门时间',
  `order_status` tinyint NOT NULL COMMENT '工单状态',
  `pay_status` tinyint NOT NULL DEFAULT 0 COMMENT '支付状态',
  `total_amount` decimal(10, 2) NOT NULL COMMENT '订单金额',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户备注',
  `cancel_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '取消原因',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
  `pay_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
  `receive_time` datetime NULL DEFAULT NULL COMMENT '接单时间',
  `start_service_time` datetime NULL DEFAULT NULL COMMENT '开始服务时间',
  `finish_service_time` datetime NULL DEFAULT NULL COMMENT '完成服务时间',
  `cancel_time` datetime NULL DEFAULT NULL COMMENT '取消时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `pay_expire_time` datetime NULL DEFAULT NULL COMMENT '支付超时时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `order_no`(`order_no` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_worker_id`(`worker_id` ASC) USING BTREE,
  INDEX `idx_service_item_id`(`service_item_id` ASC) USING BTREE,
  INDEX `idx_order_status`(`order_status` ASC) USING BTREE,
  INDEX `idx_appointment_time`(`appointment_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 58 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '工单主表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of service_order
-- ----------------------------
INSERT INTO `service_order` VALUES (1, 'SO20260108001', 1, 1, 1, 1, '2026-01-10 09:00:00', 4, 1, 150.00, '空调不制冷了', NULL, '2026-01-08 14:00:00', '2026-01-10 10:05:00', '2026-01-08 14:30:00', '2026-01-10 09:00:00', '2026-01-10 10:00:00', NULL, '2026-06-09 13:54:01', NULL);
INSERT INTO `service_order` VALUES (2, 'SO20260112001', 2, 3, 2, 2, '2026-01-15 10:00:00', 3, 1, 100.00, '马桶堵了', NULL, '2026-01-12 11:00:00', '2026-01-15 10:35:00', '2026-01-12 11:20:00', '2026-01-15 10:00:00', '2026-01-15 10:30:00', NULL, '2026-06-01 20:22:13', NULL);
INSERT INTO `service_order` VALUES (3, 'SO20260118001', 1, 2, 1, 1, '2026-01-20 14:00:00', 3, 1, 200.00, '', NULL, '2026-01-18 09:00:00', '2026-01-20 15:35:00', '2026-01-18 09:15:00', '2026-01-20 14:00:00', '2026-01-20 15:30:00', NULL, '2026-06-01 20:22:13', NULL);
INSERT INTO `service_order` VALUES (4, 'SO20260125001', 2, 5, 3, 2, '2026-01-28 08:00:00', 3, 1, 300.00, '全屋大扫除', NULL, '2026-01-25 16:00:00', '2026-01-28 11:05:00', '2026-01-25 16:10:00', '2026-01-28 08:00:00', '2026-01-28 11:00:00', NULL, '2026-06-01 20:22:13', NULL);
INSERT INTO `service_order` VALUES (5, 'SO20260202001', 1, 1, 1, 1, '2026-02-05 09:00:00', 3, 1, 150.00, '', NULL, '2026-02-02 10:00:00', '2026-02-05 10:05:00', '2026-02-02 10:30:00', '2026-02-05 09:00:00', '2026-02-05 10:00:00', NULL, '2026-06-01 20:22:13', NULL);
INSERT INTO `service_order` VALUES (6, 'SO20260210001', 2, 3, 2, 2, '2026-02-14 11:00:00', 3, 1, 100.00, '洗脸盆下水慢', NULL, '2026-02-10 08:00:00', '2026-02-14 11:35:00', '2026-02-10 08:15:00', '2026-02-14 11:00:00', '2026-02-14 11:30:00', NULL, '2026-06-01 20:22:13', NULL);
INSERT INTO `service_order` VALUES (7, 'SO20260219001', 3, 5, 3, 3, '2026-02-22 14:00:00', 3, 1, 300.00, '深度保洁', NULL, '2026-02-19 13:00:00', '2026-02-22 17:05:00', '2026-02-19 13:20:00', '2026-02-22 14:00:00', '2026-02-22 17:00:00', NULL, '2026-06-01 20:22:13', NULL);
INSERT INTO `service_order` VALUES (8, 'SO20260225001', 2, 2, NULL, 2, '2026-02-28 10:00:00', 4, 0, 200.00, '不想修了', '用户主动取消', '2026-02-25 15:00:00', NULL, NULL, NULL, NULL, '2026-02-26 10:00:00', '2026-06-01 20:22:13', NULL);
INSERT INTO `service_order` VALUES (9, 'SO20260227001', 1, 7, 4, 4, '2026-03-02 09:00:00', 2, 0, 120.00, '', NULL, '2026-02-27 10:00:00', NULL, '2026-02-27 10:20:00', '2026-03-02 09:00:00', NULL, NULL, '2026-06-01 20:22:13', NULL);
INSERT INTO `service_order` VALUES (10, 'SO20260305001', 1, 1, 1, 1, '2026-03-08 09:00:00', 3, 1, 150.00, '', NULL, '2026-03-05 09:00:00', '2026-03-08 10:05:00', '2026-03-05 09:15:00', '2026-03-08 09:00:00', '2026-03-08 10:00:00', NULL, '2026-06-01 20:22:13', NULL);
INSERT INTO `service_order` VALUES (11, 'SO20260309001', 2, 5, 3, 2, '2026-03-12 08:00:00', 3, 1, 300.00, '', NULL, '2026-03-09 14:00:00', '2026-03-12 11:05:00', '2026-03-09 14:10:00', '2026-03-12 08:00:00', '2026-03-12 11:00:00', NULL, '2026-06-01 20:22:13', NULL);
INSERT INTO `service_order` VALUES (12, 'SO20260315001', 3, 3, 2, 3, '2026-03-18 15:00:00', 3, 1, 100.00, '厨房下水堵了', NULL, '2026-03-15 10:00:00', '2026-03-18 15:35:00', '2026-03-15 10:20:00', '2026-03-18 15:00:00', '2026-03-18 15:30:00', NULL, '2026-06-01 20:22:13', NULL);
INSERT INTO `service_order` VALUES (13, 'SO20260322001', 2, 1, 1, 5, '2026-03-25 10:00:00', 3, 1, 150.00, '空调漏水', NULL, '2026-03-22 16:00:00', '2026-03-25 11:05:00', '2026-03-22 16:30:00', '2026-03-25 10:00:00', '2026-03-25 11:00:00', NULL, '2026-06-01 20:22:13', NULL);
INSERT INTO `service_order` VALUES (14, 'SO20260328001', 1, 2, NULL, 1, '2026-03-30 10:00:00', 4, 0, 200.00, '时间安排不了', '用户主动取消', '2026-03-28 08:00:00', NULL, NULL, NULL, NULL, '2026-03-29 10:00:00', '2026-06-01 20:22:13', NULL);
INSERT INTO `service_order` VALUES (15, 'SO20260330001', 4, 4, 6, 5, '2026-04-03 15:00:00', 1, 0, 80.00, '', NULL, '2026-03-30 11:00:00', NULL, '2026-09-11 16:21:48', NULL, NULL, NULL, '2026-09-11 16:21:48', NULL);
INSERT INTO `service_order` VALUES (16, 'SO20260401001', 1, 5, 3, 1, '2026-04-03 08:00:00', 3, 1, 300.00, '春季大扫除', NULL, '2026-04-01 15:00:00', '2026-04-03 11:05:00', '2026-04-01 15:10:00', '2026-04-03 08:00:00', '2026-04-03 11:00:00', NULL, '2026-06-01 20:22:13', NULL);
INSERT INTO `service_order` VALUES (17, 'SO20260407001', 2, 1, 1, 2, '2026-04-10 09:00:00', 3, 1, 150.00, '', NULL, '2026-04-07 10:00:00', '2026-04-10 10:05:00', '2026-04-07 10:15:00', '2026-04-10 09:00:00', '2026-04-10 10:00:00', NULL, '2026-06-01 20:22:13', NULL);
INSERT INTO `service_order` VALUES (18, 'SO20260412001', 3, 3, 2, 3, '2026-04-14 16:00:00', 3, 1, 100.00, '浴室地漏异味', NULL, '2026-04-12 07:00:00', '2026-04-14 16:35:00', '2026-04-12 07:20:00', '2026-04-14 16:00:00', '2026-04-14 16:30:00', NULL, '2026-06-01 20:22:13', NULL);
INSERT INTO `service_order` VALUES (19, 'SO20260417001', 2, 2, 1, 2, '2026-04-20 10:00:00', 3, 1, 200.00, '冰箱结冰严重', NULL, '2026-04-17 11:00:00', '2026-04-20 11:35:00', '2026-04-17 11:30:00', '2026-04-20 10:00:00', '2026-04-20 11:30:00', NULL, '2026-06-01 20:22:13', NULL);
INSERT INTO `service_order` VALUES (20, 'SO20260422001', 1, 1, 1, 1, '2026-04-25 14:00:00', 3, 0, 150.00, '', NULL, '2026-04-22 09:00:00', NULL, '2026-04-22 09:15:00', '2026-04-25 14:00:00', '2026-06-09 17:07:23', NULL, '2026-06-09 17:07:23', NULL);
INSERT INTO `service_order` VALUES (21, 'SO20260426001', 5, 5, 1, 6, '2026-04-28 08:00:00', 1, 0, 300.00, '第一次用，试试看', NULL, '2026-04-26 17:00:00', NULL, '2026-06-09 22:13:57', NULL, NULL, NULL, '2026-06-09 22:13:57', NULL);
INSERT INTO `service_order` VALUES (22, 'SO20260502001', 1, 1, 1, 1, '2026-05-05 09:00:00', 3, 1, 150.00, '', NULL, '2026-05-02 14:00:00', '2026-05-05 10:05:00', '2026-05-02 14:30:00', '2026-05-05 09:00:00', '2026-05-05 10:00:00', NULL, '2026-06-01 20:22:13', NULL);
INSERT INTO `service_order` VALUES (23, 'SO20260507001', 2, 3, 2, 2, '2026-05-10 11:00:00', 3, 1, 100.00, '厨房下水反复堵', NULL, '2026-05-07 08:00:00', '2026-05-10 11:35:00', '2026-05-07 08:15:00', '2026-05-10 11:00:00', '2026-05-10 11:30:00', NULL, '2026-06-01 20:22:13', NULL);
INSERT INTO `service_order` VALUES (24, 'SO20260511001', 3, 5, 3, 3, '2026-05-14 09:00:00', 3, 1, 300.00, '', NULL, '2026-05-11 15:00:00', '2026-05-14 12:05:00', '2026-05-11 15:10:00', '2026-05-14 09:00:00', '2026-05-14 12:00:00', NULL, '2026-06-01 20:22:13', NULL);
INSERT INTO `service_order` VALUES (25, 'SO20260520001', 1, 4, 2, 1, '2026-05-22 09:00:00', 4, 0, 80.00, '自己用疏通剂搞定了', '用户主动取消', '2026-05-20 12:00:00', NULL, '2026-05-20 12:10:00', NULL, NULL, '2026-05-21 10:00:00', '2026-06-01 20:22:13', NULL);
INSERT INTO `service_order` VALUES (26, 'SO20260525001', 2, 1, 1, 2, '2026-05-26 10:00:00', 3, 0, 150.00, '', NULL, '2026-05-25 08:00:00', NULL, '2026-05-25 08:20:00', '2026-06-09 22:14:06', '2026-06-09 22:14:09', NULL, '2026-06-09 22:14:09', NULL);
INSERT INTO `service_order` VALUES (27, 'SO20260528001', 5, 6, 7, 6, '2026-05-30 08:00:00', 3, 0, 500.00, '端午前大扫除', NULL, '2026-05-28 16:00:00', NULL, '2026-06-24 21:03:40', '2026-06-24 21:04:05', '2026-06-24 21:04:08', NULL, '2026-06-24 21:04:08', NULL);
INSERT INTO `service_order` VALUES (28, 'SO20260601001', 2, 1, 1, 2, '2026-06-01 08:00:00', 3, 0, 150.00, '急！', NULL, '2026-05-31 09:00:00', NULL, '2026-05-31 09:30:00', '2026-06-09 17:07:30', '2026-06-09 18:29:45', NULL, '2026-06-09 18:29:45', NULL);
INSERT INTO `service_order` VALUES (29, 'SO20260601002', 1, 5, NULL, 1, '2026-06-02 10:00:00', 0, 0, 300.00, '', NULL, '2026-06-01 07:00:00', NULL, NULL, NULL, NULL, NULL, '2026-06-01 20:22:13', NULL);
INSERT INTO `service_order` VALUES (30, 'SO20260601003', 3, 3, NULL, 3, '2026-06-03 14:00:00', 0, 0, 100.00, '', NULL, '2026-06-01 11:30:00', NULL, NULL, NULL, NULL, NULL, '2026-06-01 20:22:13', NULL);
INSERT INTO `service_order` VALUES (31, 'YXT1780565577121', 7, 1, NULL, 8, '2026-06-05 13:06:00', 4, 0, 150.00, '带个冰糕', '用户主动取消', '2026-06-04 17:32:57', NULL, NULL, NULL, NULL, '2026-06-04 22:19:24', '2026-06-04 22:19:24', '2026-06-04 17:47:57');
INSERT INTO `service_order` VALUES (32, 'YXT1780567547836', 7, 1, 1, 8, '2026-06-05 16:00:00', 1, 0, 150.00, '好的', NULL, '2026-06-04 18:05:48', NULL, '2026-06-09 22:16:29', NULL, NULL, NULL, '2026-06-09 22:16:29', '2026-06-04 18:20:48');
INSERT INTO `service_order` VALUES (33, 'YXT1780568842313', 7, 1, 3, 8, '2026-06-05 16:00:00', 4, 1, 150.00, '123', '用户主动取消', '2026-06-04 18:27:22', '2026-06-04 18:35:11', '2026-06-04 23:23:40', NULL, NULL, '2026-06-06 14:18:18', '2026-06-06 14:18:18', '2026-06-04 18:42:22');
INSERT INTO `service_order` VALUES (34, 'YXT1780580053376', 7, 1, 1, 8, '2026-06-05 07:00:00', 3, 1, 150.00, '', NULL, '2026-06-04 21:34:13', '2026-06-04 21:34:16', '2026-06-04 23:13:25', NULL, NULL, NULL, '2026-06-05 10:56:31', '2026-06-04 21:49:13');
INSERT INTO `service_order` VALUES (35, 'YXT1780725602906', 7, 1, NULL, 8, '2026-06-06 15:59:47', 4, 0, 150.00, '快来', '用户主动取消', '2026-06-06 14:00:03', NULL, NULL, NULL, NULL, '2026-06-06 14:00:20', '2026-06-06 14:00:20', '2026-06-06 14:15:03');
INSERT INTO `service_order` VALUES (36, 'YXT1780725694918', 7, 1, 5, 8, '2026-06-10 00:00:00', 4, 1, 150.00, '123', '管理员取消', '2026-06-06 14:01:35', '2026-06-06 14:01:39', '2026-06-06 14:04:04', NULL, NULL, '2026-06-06 14:04:53', '2026-06-06 14:04:53', '2026-06-06 14:16:35');
INSERT INTO `service_order` VALUES (37, 'YXT1780725950617', 7, 1, 5, 8, '2026-06-11 00:00:00', 4, 1, 150.00, '', '用户主动取消', '2026-06-06 14:05:51', '2026-06-06 14:05:53', '2026-06-06 14:06:28', NULL, NULL, '2026-06-06 14:09:34', '2026-06-06 14:09:34', '2026-06-06 14:20:51');
INSERT INTO `service_order` VALUES (38, 'YXT1780877781745', 7, 1, 1, 8, '2026-06-12 00:00:00', 3, 1, 150.00, '', NULL, '2026-06-08 08:16:22', '2026-06-08 08:16:24', '2026-06-09 22:15:44', '2026-06-09 22:16:56', '2026-07-15 18:00:40', NULL, '2026-07-15 18:00:40', '2026-06-08 08:31:22');
INSERT INTO `service_order` VALUES (39, 'YXT1781000922579', 7, 2, 1, 8, '2026-06-13 00:00:00', 4, 1, 200.00, '123456789', '用户主动取消', '2026-06-09 18:28:43', '2026-06-09 18:28:45', '2026-06-09 18:32:27', NULL, NULL, '2026-06-09 18:32:44', '2026-06-09 18:32:44', '2026-06-09 18:43:43');
INSERT INTO `service_order` VALUES (40, 'YXT1782305604245', 7, 1, NULL, 8, '2026-06-25 00:00:00', 0, 1, 150.00, '123', NULL, '2026-06-24 20:53:24', '2026-06-24 20:53:27', NULL, NULL, NULL, NULL, '2026-06-24 20:53:26', '2026-06-24 21:08:24');
INSERT INTO `service_order` VALUES (41, 'YXT1782305622786', 7, 1, 5, 8, '2026-06-25 00:00:00', 1, 1, 150.00, '123', NULL, '2026-06-24 20:53:43', '2026-06-24 20:53:47', '2026-07-15 16:16:50', NULL, NULL, NULL, '2026-07-15 16:16:50', '2026-06-24 21:08:43');
INSERT INTO `service_order` VALUES (42, 'SO202506001', 1, 1, 1, 1, '2025-06-15 10:00:00', 3, 1, 299.00, '空调不制冷，需要加雪种', NULL, '2025-06-14 15:30:00', '2025-06-14 15:35:00', '2025-06-14 16:00:00', '2025-06-15 10:10:00', '2025-06-15 11:30:00', NULL, '2025-06-15 11:30:00', NULL);
INSERT INTO `service_order` VALUES (43, 'SO202507001', 2, 3, 2, 2, '2025-07-20 14:00:00', 3, 1, 150.00, '马桶堵塞严重，需要尽快处理', NULL, '2025-07-19 09:00:00', '2025-07-19 09:02:00', '2025-07-19 09:30:00', '2025-07-20 14:05:00', '2025-07-20 14:40:00', NULL, '2025-07-20 14:40:00', NULL);
INSERT INTO `service_order` VALUES (44, 'SO202508001', 3, 5, 3, 3, '2025-08-05 08:30:00', 3, 1, 499.00, '三室两厅全屋保洁', NULL, '2025-08-04 20:00:00', '2025-08-04 20:10:00', '2025-08-05 06:00:00', '2025-08-05 08:35:00', '2025-08-05 12:00:00', NULL, '2025-08-05 12:00:00', NULL);
INSERT INTO `service_order` VALUES (45, 'SO202509001', 4, 2, 4, 4, '2025-09-12 16:00:00', 4, 0, 200.00, '冰箱冷藏室不制冷', '用户主动取消', '2025-09-11 11:00:00', NULL, NULL, NULL, NULL, '2025-09-12 10:00:00', '2025-09-12 10:00:00', NULL);
INSERT INTO `service_order` VALUES (46, 'SO202510001', 5, 7, 5, 5, '2025-10-08 09:00:00', 3, 1, 180.00, '客厅吊灯和卧室吸顶灯安装', NULL, '2025-10-07 13:00:00', '2025-10-07 13:05:00', '2025-10-07 13:30:00', '2025-10-08 09:10:00', '2025-10-08 11:00:00', NULL, '2025-10-08 11:00:00', NULL);
INSERT INTO `service_order` VALUES (47, 'SO202511001', 1, 8, 6, 1, '2025-11-18 10:30:00', 3, 1, 350.00, '老房子电路跳闸频繁，需全屋排查', NULL, '2025-11-17 16:00:00', '2025-11-17 16:08:00', '2025-11-17 16:40:00', '2025-11-18 10:35:00', '2025-11-18 12:30:00', NULL, '2025-11-18 12:30:00', NULL);
INSERT INTO `service_order` VALUES (48, 'SO202512001', 2, 4, 7, 6, '2025-12-22 13:30:00', 3, 1, 120.00, '卫生间地漏反味严重，需疏通清理', NULL, '2025-12-21 10:00:00', '2025-12-21 10:06:00', '2025-12-21 10:30:00', '2025-12-22 13:35:00', '2025-12-22 14:20:00', NULL, '2025-12-22 14:20:00', NULL);
INSERT INTO `service_order` VALUES (49, 'YXT1784106554596', 7, 1, 1, 8, '2026-07-16 10:00:00', 1, 1, 150.00, '带包烟', NULL, '2026-07-15 17:09:15', '2026-07-15 17:09:17', '2026-07-15 18:02:44', NULL, NULL, NULL, '2026-07-15 18:02:44', '2026-07-15 17:24:15');
INSERT INTO `service_order` VALUES (50, 'YXT1784110894593', 7, 1, 7, 8, '2026-07-16 00:00:00', 3, 1, 150.00, '123', NULL, '2026-07-15 18:21:35', '2026-07-15 18:21:37', '2026-07-15 18:22:44', '2026-07-15 18:23:43', '2026-07-15 18:23:44', NULL, '2026-07-15 18:23:44', '2026-07-15 18:36:35');
INSERT INTO `service_order` VALUES (51, 'YXT1784111059118', 7, 2, 7, 8, '2026-07-15 18:24:01', 1, 1, 200.00, '测试数据', NULL, '2026-07-15 18:24:19', '2026-07-15 18:24:21', '2026-07-15 18:32:25', NULL, NULL, NULL, '2026-07-15 18:32:25', '2026-07-15 18:39:19');
INSERT INTO `service_order` VALUES (52, 'YXT1789102558906', 7, 9, NULL, 8, '2026-09-12 00:00:00', 0, 1, 10000.00, 'asd', NULL, '2026-09-11 12:55:59', '2026-09-11 12:56:01', NULL, NULL, NULL, NULL, '2026-09-11 12:56:01', '2026-09-11 13:10:59');
INSERT INTO `service_order` VALUES (53, 'YXT1789102739054', 7, 1, 7, 10, '2026-09-12 00:00:00', 3, 1, 150.00, '123', NULL, '2026-09-11 12:58:59', '2026-09-11 12:59:01', '2026-09-11 13:00:11', '2026-09-11 13:00:29', '2026-09-11 13:00:31', NULL, '2026-09-11 13:00:31', '2026-09-11 13:13:59');
INSERT INTO `service_order` VALUES (54, 'YXT1789114953853', 7, 1, 6, 10, '2026-09-12 00:00:00', 1, 1, 150.00, 'as', NULL, '2026-09-11 16:22:34', '2026-09-11 16:22:36', '2026-09-11 16:23:00', NULL, NULL, NULL, '2026-09-11 16:23:00', '2026-09-11 16:37:34');
INSERT INTO `service_order` VALUES (55, 'YXT1789115808939', 7, 2, 6, 10, '2026-09-12 00:00:00', 1, 1, 200.00, 'ad', NULL, '2026-09-11 16:36:49', '2026-09-11 16:36:51', '2026-09-11 16:37:12', NULL, NULL, NULL, '2026-09-11 16:37:12', '2026-09-11 16:51:49');
INSERT INTO `service_order` VALUES (56, 'YXT1789116166529', 7, 2, 6, 10, '2026-09-12 00:00:00', 1, 1, 200.00, 'qwe', NULL, '2026-09-11 16:42:47', '2026-09-11 16:42:50', '2026-09-11 16:44:56', NULL, NULL, NULL, '2026-09-11 16:44:56', '2026-09-11 16:57:47');
INSERT INTO `service_order` VALUES (57, 'YXT1789134995522', 7, 1, 7, 10, '2026-09-18 00:00:00', 1, 1, 150.00, 'qweweq', NULL, '2026-09-11 21:56:36', '2026-09-11 21:56:39', '2026-09-11 21:57:29', NULL, NULL, NULL, '2026-09-11 21:57:29', '2026-09-11 22:11:36');
INSERT INTO `service_order` VALUES (58, 'YXT1789135332409', 7, 1, 7, 10, '2026-09-26 00:00:00', 1, 1, 150.00, '666666666', NULL, '2026-09-11 22:02:12', '2026-09-11 22:02:15', '2026-09-11 22:02:33', NULL, NULL, NULL, '2026-09-11 22:02:33', '2026-09-11 22:17:12');
INSERT INTO `service_order` VALUES (59, 'YXT1789135389652', 7, 1, 7, 10, '2026-09-12 00:00:00', 1, 1, 150.00, '789456', NULL, '2026-09-11 22:03:10', '2026-09-11 22:03:11', '2026-09-11 22:03:25', NULL, NULL, NULL, '2026-09-11 22:03:25', '2026-09-11 22:18:10');
INSERT INTO `service_order` VALUES (60, 'YXT1789135816905', 7, 1, 7, 10, '2026-09-17 00:00:00', 4, 1, 150.00, 'qqqqqqq', '用户主动取消', '2026-09-11 22:10:17', '2026-09-11 22:10:18', '2026-09-11 22:10:52', NULL, NULL, '2026-09-11 22:10:54', '2026-09-11 22:10:54', '2026-09-11 22:25:17');

-- ----------------------------
-- Table structure for service_order_comment
-- ----------------------------
DROP TABLE IF EXISTS `service_order_comment`;
CREATE TABLE `service_order_comment`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '评价ID',
  `order_id` bigint NOT NULL COMMENT '工单ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `worker_id` bigint NOT NULL COMMENT '服务人员ID',
  `score` int NOT NULL COMMENT '评分',
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评价内容',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `order_id`(`order_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_worker_id`(`worker_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '工单评价表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of service_order_comment
-- ----------------------------
INSERT INTO `service_order_comment` VALUES (1, 1, 1, 1, 5, '王师傅技术好，很快就修好了！', '2026-01-10 11:00:00');
INSERT INTO `service_order_comment` VALUES (2, 2, 2, 2, 4, '疏通了但声音有点大', '2026-01-15 11:00:00');
INSERT INTO `service_order_comment` VALUES (3, 3, 1, 1, 5, '第二次找王师傅了，靠谱', '2026-01-20 16:00:00');
INSERT INTO `service_order_comment` VALUES (4, 4, 2, 3, 5, '刘阿姨打扫特别干净，赞！', '2026-01-28 12:00:00');
INSERT INTO `service_order_comment` VALUES (5, 5, 1, 1, 4, '', '2026-02-05 11:00:00');
INSERT INTO `service_order_comment` VALUES (6, 7, 3, 3, 5, '保洁非常细致，以后就找刘阿姨了', '2026-02-22 18:00:00');
INSERT INTO `service_order_comment` VALUES (7, 10, 1, 1, 5, '第四次了，没话说', '2026-03-08 11:00:00');
INSERT INTO `service_order_comment` VALUES (8, 11, 2, 3, 5, '', '2026-03-12 12:00:00');
INSERT INTO `service_order_comment` VALUES (9, 12, 3, 2, 3, '还行吧，速度一般', '2026-03-18 16:00:00');
INSERT INTO `service_order_comment` VALUES (10, 13, 2, 1, 5, '王师傅跨城也来了，感动', '2026-03-25 12:00:00');
INSERT INTO `service_order_comment` VALUES (11, 16, 1, 3, 5, '', '2026-04-03 12:00:00');
INSERT INTO `service_order_comment` VALUES (12, 17, 2, 1, 4, '挺好的', '2026-04-10 11:00:00');
INSERT INTO `service_order_comment` VALUES (13, 19, 2, 1, 5, '冰箱跟新的一样', '2026-04-20 12:00:00');
INSERT INTO `service_order_comment` VALUES (14, 22, 1, 1, 5, '', '2026-05-05 11:00:00');
INSERT INTO `service_order_comment` VALUES (15, 23, 2, 2, 4, '这次通得比较干净', '2026-05-10 12:00:00');
INSERT INTO `service_order_comment` VALUES (16, 34, 7, 1, 5, '师傅人很好', '2026-06-05 11:31:17');
INSERT INTO `service_order_comment` VALUES (17, 53, 7, 7, 5, '123', '2026-09-11 13:00:45');

-- ----------------------------
-- Table structure for service_order_log
-- ----------------------------
DROP TABLE IF EXISTS `service_order_log`;
CREATE TABLE `service_order_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `order_id` bigint NOT NULL COMMENT '工单ID',
  `operator_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作人类型:USER/WORKER/ADMIN/SYSTEM',
  `operator_id` bigint NULL DEFAULT NULL COMMENT '操作人ID',
  `from_status` tinyint NULL DEFAULT NULL COMMENT '原状态',
  `to_status` tinyint NULL DEFAULT NULL COMMENT '新状态',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作说明',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_id`(`order_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 67 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '工单日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of service_order_log
-- ----------------------------
INSERT INTO `service_order_log` VALUES (1, 1, 'USER', 1, NULL, 0, '用户提交工单', '2026-01-08 14:00:00');
INSERT INTO `service_order_log` VALUES (2, 1, 'ADMIN', 1, 0, 1, '管理员派单给王师傅', '2026-01-08 14:30:00');
INSERT INTO `service_order_log` VALUES (3, 1, 'WORKER', 1, 1, 2, '王师傅开始服务', '2026-01-10 09:00:00');
INSERT INTO `service_order_log` VALUES (4, 1, 'WORKER', 1, 2, 3, '王师傅完成服务', '2026-01-10 10:00:00');
INSERT INTO `service_order_log` VALUES (5, 2, 'USER', 2, NULL, 0, '用户提交工单', '2026-01-12 11:00:00');
INSERT INTO `service_order_log` VALUES (6, 2, 'ADMIN', 1, 0, 1, '管理员派单给赵师傅', '2026-01-12 11:20:00');
INSERT INTO `service_order_log` VALUES (7, 2, 'WORKER', 2, 1, 2, '赵师傅开始服务', '2026-01-15 10:00:00');
INSERT INTO `service_order_log` VALUES (8, 2, 'WORKER', 2, 2, 3, '赵师傅完成服务', '2026-01-15 10:30:00');
INSERT INTO `service_order_log` VALUES (9, 5, 'USER', 1, NULL, 0, '用户提交工单', '2026-02-02 10:00:00');
INSERT INTO `service_order_log` VALUES (10, 5, 'ADMIN', 1, 0, 1, '管理员派单给王师傅', '2026-02-02 10:30:00');
INSERT INTO `service_order_log` VALUES (11, 5, 'WORKER', 1, 1, 2, '王师傅开始服务', '2026-02-05 09:00:00');
INSERT INTO `service_order_log` VALUES (12, 5, 'WORKER', 1, 2, 3, '王师傅完成服务', '2026-02-05 10:00:00');
INSERT INTO `service_order_log` VALUES (13, 8, 'ADMIN', 1, 0, 4, '管理员取消工单（用户主动取消）', '2026-02-26 10:00:00');
INSERT INTO `service_order_log` VALUES (14, 10, 'USER', 1, NULL, 0, '用户提交工单', '2026-03-05 09:00:00');
INSERT INTO `service_order_log` VALUES (15, 10, 'ADMIN', 1, 0, 1, '管理员派单给王师傅', '2026-03-05 09:15:00');
INSERT INTO `service_order_log` VALUES (16, 10, 'WORKER', 1, 1, 2, '王师傅开始服务', '2026-03-08 09:00:00');
INSERT INTO `service_order_log` VALUES (17, 10, 'WORKER', 1, 2, 3, '王师傅完成服务', '2026-03-08 10:00:00');
INSERT INTO `service_order_log` VALUES (18, 16, 'USER', 1, NULL, 0, '用户提交工单', '2026-04-01 15:00:00');
INSERT INTO `service_order_log` VALUES (19, 16, 'ADMIN', 1, 0, 1, '管理员派单给刘阿姨', '2026-04-01 15:10:00');
INSERT INTO `service_order_log` VALUES (20, 16, 'WORKER', 3, 1, 2, '刘阿姨开始服务', '2026-04-03 08:00:00');
INSERT INTO `service_order_log` VALUES (21, 16, 'WORKER', 3, 2, 3, '刘阿姨完成服务', '2026-04-03 11:00:00');
INSERT INTO `service_order_log` VALUES (22, 22, 'USER', 1, NULL, 0, '用户提交工单', '2026-05-02 14:00:00');
INSERT INTO `service_order_log` VALUES (23, 22, 'ADMIN', 1, 0, 1, '管理员派单给王师傅', '2026-05-02 14:30:00');
INSERT INTO `service_order_log` VALUES (24, 22, 'WORKER', 1, 1, 2, '王师傅开始服务', '2026-05-05 09:00:00');
INSERT INTO `service_order_log` VALUES (25, 22, 'WORKER', 1, 2, 3, '王师傅完成服务', '2026-05-05 10:00:00');
INSERT INTO `service_order_log` VALUES (26, 25, 'USER', 1, 0, 4, '用户取消工单', '2026-05-21 10:00:00');
INSERT INTO `service_order_log` VALUES (27, 28, 'USER', 2, NULL, 0, '用户提交工单', '2026-05-31 09:00:00');
INSERT INTO `service_order_log` VALUES (28, 28, 'ADMIN', 1, 0, 1, '管理员派单给王师傅', '2026-05-31 09:30:00');
INSERT INTO `service_order_log` VALUES (29, 29, 'USER', 1, NULL, 0, '用户提交工单', '2026-06-01 07:00:00');
INSERT INTO `service_order_log` VALUES (30, 30, 'USER', 3, NULL, 0, '用户提交工单', '2026-06-01 11:30:00');
INSERT INTO `service_order_log` VALUES (31, 35, 'user', 7, 0, 4, '用户取消', '2026-06-06 14:00:20');
INSERT INTO `service_order_log` VALUES (32, 36, 'admin', 1, 0, 1, '管理员派单给:周师傅', '2026-06-06 14:04:04');
INSERT INTO `service_order_log` VALUES (33, 36, 'admin', 1, 1, 4, '管理员取消', '2026-06-06 14:04:53');
INSERT INTO `service_order_log` VALUES (34, 37, 'admin', 1, 0, 1, '管理员派单给:周师傅', '2026-06-06 14:06:28');
INSERT INTO `service_order_log` VALUES (35, 37, 'user', 7, 1, 4, '用户取消', '2026-06-06 14:09:34');
INSERT INTO `service_order_log` VALUES (36, 33, 'user', 7, 1, 4, '用户取消', '2026-06-06 14:18:18');
INSERT INTO `service_order_log` VALUES (37, 20, 'worker', 1, 2, 3, '工单被完成', '2026-06-09 17:07:23');
INSERT INTO `service_order_log` VALUES (38, 28, 'worker', 1, 1, 2, '工人开始服务', '2026-06-09 17:07:30');
INSERT INTO `service_order_log` VALUES (39, 28, 'worker', 1, 2, 3, '工单被完成', '2026-06-09 18:29:45');
INSERT INTO `service_order_log` VALUES (40, 39, 'admin', 1, 0, 1, '管理员派单给:王师傅', '2026-06-09 18:32:27');
INSERT INTO `service_order_log` VALUES (41, 39, 'user', 7, 1, 4, '用户取消', '2026-06-09 18:32:44');
INSERT INTO `service_order_log` VALUES (42, 21, 'worker', 1, 0, 1, '工人:王师傅抢到了订单', '2026-06-09 22:13:57');
INSERT INTO `service_order_log` VALUES (43, 26, 'worker', 1, 1, 2, '工人开始服务', '2026-06-09 22:14:06');
INSERT INTO `service_order_log` VALUES (44, 26, 'worker', 1, 2, 3, '工单被完成', '2026-06-09 22:14:09');
INSERT INTO `service_order_log` VALUES (45, 38, 'admin', 1, 0, 1, '管理员派单给:王师傅', '2026-06-09 22:15:44');
INSERT INTO `service_order_log` VALUES (46, 32, 'worker', 1, 0, 1, '工人:王师傅抢到了订单', '2026-06-09 22:16:29');
INSERT INTO `service_order_log` VALUES (47, 38, 'worker', 1, 1, 2, '工人开始服务', '2026-06-09 22:16:56');
INSERT INTO `service_order_log` VALUES (48, 27, 'worker', 7, 0, 1, '工人:王小二抢到了订单', '2026-06-24 21:03:40');
INSERT INTO `service_order_log` VALUES (49, 27, 'worker', 7, 1, 2, '工人开始服务', '2026-06-24 21:04:05');
INSERT INTO `service_order_log` VALUES (50, 27, 'worker', 7, 2, 3, '工单被完成', '2026-06-24 21:04:08');
INSERT INTO `service_order_log` VALUES (51, 41, 'admin', 1, 0, 1, '管理员派单给:周师傅', '2026-07-15 16:16:50');
INSERT INTO `service_order_log` VALUES (52, 38, 'worker', 1, 2, 3, '工单被完成', '2026-07-15 18:00:40');
INSERT INTO `service_order_log` VALUES (53, 49, 'worker', 1, 0, 1, '工人:王师傅抢到了订单', '2026-07-15 18:02:44');
INSERT INTO `service_order_log` VALUES (54, 50, 'worker', 7, 0, 1, '工人:王小二抢到了订单', '2026-07-15 18:22:44');
INSERT INTO `service_order_log` VALUES (55, 50, 'worker', 7, 1, 2, '工人开始服务', '2026-07-15 18:23:43');
INSERT INTO `service_order_log` VALUES (56, 50, 'worker', 7, 2, 3, '工单被完成', '2026-07-15 18:23:44');
INSERT INTO `service_order_log` VALUES (57, 51, 'worker', 7, 0, 1, '工人:王师傅抢到了订单', '2026-07-15 18:32:25');
INSERT INTO `service_order_log` VALUES (58, 53, 'worker', 7, 0, 1, '工人:王师傅抢到了订单', '2026-09-11 13:00:11');
INSERT INTO `service_order_log` VALUES (59, 53, 'worker', 7, 1, 2, '工人开始服务', '2026-09-11 13:00:29');
INSERT INTO `service_order_log` VALUES (60, 53, 'worker', 7, 2, 3, '工单被完成', '2026-09-11 13:00:31');
INSERT INTO `service_order_log` VALUES (61, 15, 'admin', 1, 0, 1, '管理员派单给:谢师傅', '2026-09-11 16:21:48');
INSERT INTO `service_order_log` VALUES (62, 54, 'admin', 1, 0, 1, '管理员派单给:谢师傅', '2026-09-11 16:23:00');
INSERT INTO `service_order_log` VALUES (63, 55, 'worker', 6, 0, 1, '工人:谢师傅抢到了订单', '2026-09-11 16:37:12');
INSERT INTO `service_order_log` VALUES (64, 55, 'worker', 6, 0, 1, '工人:谢师傅抢到了订单', '2026-09-11 16:37:12');
INSERT INTO `service_order_log` VALUES (65, 56, 'admin', 1, 0, 1, '管理员派单给:谢师傅', '2026-09-11 16:44:56');
INSERT INTO `service_order_log` VALUES (66, 57, 'worker', 7, 0, 1, '工人:王师傅抢到了订单', '2026-09-11 21:57:29');
INSERT INTO `service_order_log` VALUES (67, 58, 'worker', 7, 0, 1, '工人:王师傅抢到了订单', '2026-09-11 22:02:33');
INSERT INTO `service_order_log` VALUES (68, 59, 'worker', 7, 0, 1, '工人:王师傅抢到了订单', '2026-09-11 22:03:25');
INSERT INTO `service_order_log` VALUES (69, 59, 'user', 7, 0, 4, '用户取消', '2026-09-11 22:03:27');
INSERT INTO `service_order_log` VALUES (70, 60, 'worker', 7, 0, 1, '工人:王师傅抢到了订单', '2026-09-11 22:10:52');
INSERT INTO `service_order_log` VALUES (71, 60, 'user', 7, 0, 4, '用户取消', '2026-09-11 22:10:54');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '手机号',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态:1正常 0禁用',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `phone`(`phone` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '13800001111', '$2a$10$JuGlV0RPZK2lW2RMBVSZzePfEQmeC42XWNAdtBuMYv/2XHdiQVWH6', '张三', '', 1, '2026-01-05 10:00:00', '2026-06-04 08:26:53');
INSERT INTO `user` VALUES (2, '13800002222', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '李四', '', 1, '2026-01-10 10:00:00', '2026-06-01 20:22:13');
INSERT INTO `user` VALUES (3, '13800003333', '$2a$10$WrxK4Rbw6T0L7zA4.kV2OODjx8mCIJ1XimiWaVPUltVfKPkF1349q', '王五', '', 1, '2026-02-15 14:00:00', '2026-06-03 10:49:42');
INSERT INTO `user` VALUES (4, '13800004444', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '赵六', '', 0, '2026-03-20 09:00:00', '2026-06-01 20:22:13');
INSERT INTO `user` VALUES (5, '13800005555', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '孙七', '', 1, '2026-04-01 16:00:00', '2026-06-01 20:22:13');
INSERT INTO `user` VALUES (6, '0', NULL, NULL, NULL, 1, NULL, NULL);
INSERT INTO `user` VALUES (7, '13456789001', '$2a$10$ZJ3YFYqdcu8XbSuE4mitAeKz.VReM8TwSxzJKgiD.Rb9TJ4lRdOpy', '测试', '/uploads/60275ecb-031c-4529-a178-f1b148a55ccb.jpg', 1, '2026-06-03 15:44:40', '2026-09-11 14:14:20');
INSERT INTO `user` VALUES (9, '13456789006', '$2a$10$dGXX.T3ebfeq/lPAx3F8iej1whIOkKy/PvUcAUnByzJ64tzJroGee', 'ceshi666', NULL, 1, '2026-07-15 16:50:53', '2026-09-12 14:08:22');
INSERT INTO `user` VALUES (10, '13456789002', '$2a$10$2kSuVtjIwH.Q5GgKJteKeuP4dqfKH88XNjnwBdnLO/2ZQ4KRhsgOe', '小美', NULL, 1, '2026-08-28 10:51:04', '2026-09-12 14:08:39');
INSERT INTO `user` VALUES (11, '13456789005', '$2a$10$YctRqhpGcajE4USmrDkOMuyvHhEcc4MYtp0WfQ64RceJIXOtBe8A2', 'subai', NULL, 1, '2026-09-11 13:38:39', '2026-09-12 14:08:52');
INSERT INTO `user` VALUES (12, '13456789008', '$2a$10$W0nPTkWQKYs9xADt4MvCJ.0qEBLJZWg7o5xu7OkSw.7L1N9zj1V3m', 'ceshi1', NULL, 1, '2026-09-11 13:39:45', NULL);

-- ----------------------------
-- Table structure for user_address
-- ----------------------------
DROP TABLE IF EXISTS `user_address`;
CREATE TABLE `user_address`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '地址ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `contact_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '联系人',
  `contact_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '联系电话',
  `province` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '省',
  `city` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '市',
  `district` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '区',
  `detail_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '详细地址',
  `latitude` decimal(10, 6) NULL DEFAULT NULL COMMENT '纬度',
  `longitude` decimal(10, 6) NULL DEFAULT NULL COMMENT '经度',
  `is_default` tinyint NOT NULL DEFAULT 0 COMMENT '是否默认地址:1是 0否',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户地址表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_address
-- ----------------------------
INSERT INTO `user_address` VALUES (1, 1, '张三', '13800001111', '广东省', '广州市', '天河区', '体育西路100号天汇大厦1203', NULL, NULL, 1, '2026-01-05 10:00:00', '2026-06-01 20:22:13');
INSERT INTO `user_address` VALUES (2, 2, '李四', '13800002222', '广东省', '深圳市', '南山区', '科技园路50号创新大厦B座501', NULL, NULL, 1, '2026-01-10 10:00:00', '2026-06-01 20:22:13');
INSERT INTO `user_address` VALUES (3, 3, '王五', '13800003333', '广东省', '广州市', '番禺区', '大学城外环东路100号', NULL, NULL, 1, '2026-02-15 14:00:00', '2026-06-01 20:22:13');
INSERT INTO `user_address` VALUES (4, 1, '张三', '13800001111', '广东省', '广州市', '番禺区', '暨南大学东校区北门', NULL, NULL, 0, '2026-03-01 09:00:00', '2026-06-04 08:57:43');
INSERT INTO `user_address` VALUES (5, 2, '李四', '13800002222', '广东省', '深圳市', '福田区', '华强北路1号赛格广场', NULL, NULL, 0, '2026-03-10 11:00:00', '2026-06-01 20:22:13');
INSERT INTO `user_address` VALUES (6, 5, '孙七', '13800005555', '广东省', '东莞市', '南城区', '鸿福路99号', NULL, NULL, 1, '2026-04-01 16:00:00', '2026-06-01 20:22:13');
INSERT INTO `user_address` VALUES (9, 10, '小美', '13456789002', '很那声', '商丘书', '睢阳区', '456', NULL, NULL, 0, '2026-09-10 21:21:08', '2026-09-10 21:22:13');
INSERT INTO `user_address` VALUES (10, 7, '素白', '13456789001', '北京市', '市辖区', '东城区', '122', NULL, NULL, 0, '2026-09-11 12:58:48', '2026-09-11 12:58:47');

-- ----------------------------
-- Table structure for worker
-- ----------------------------
DROP TABLE IF EXISTS `worker`;
CREATE TABLE `worker`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '服务人员ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '姓名',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '手机号',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `avatar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像',
  `gender` tinyint NULL DEFAULT NULL COMMENT '性别:1男 2女',
  `skill_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '技能描述',
  `service_status` tinyint NOT NULL DEFAULT 1 COMMENT '接单状态:0休息中 1可接单 2服务中',
  `latitude` decimal(10, 6) NULL DEFAULT NULL COMMENT '当前纬度',
  `longitude` decimal(10, 6) NULL DEFAULT NULL COMMENT '当前经度',
  `rating` decimal(3, 2) NULL DEFAULT 5.00 COMMENT '评分',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '账号状态:1正常 0禁用',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `phone`(`phone` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '服务人员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of worker
-- ----------------------------
INSERT INTO `worker` VALUES (1, '王师傅', '13456789003', '$2a$10$1mk4UuFDYmp9xmnVTBkfd.g9.8mYkMiTSKjdL6xN1JlW8DSP4oyze', '/uploads/e1ada343-eb2b-4cbc-b402-22d589180fa2.jpg', 1, '10年空调/冰箱维修经验，格力美的认证', 1, 23.129100, 113.264400, 4.80, 1, '2026-01-01 00:00:00', '2026-07-15 18:02:44');
INSERT INTO `worker` VALUES (2, '赵师傅', '13900002222', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', NULL, 1, '管道疏通/水电维修/热水器安装', 2, 23.050000, 113.750000, 4.50, 1, '2026-01-01 00:00:00', '2026-06-01 20:22:13');
INSERT INTO `worker` VALUES (3, '刘阿姨', '13900003333', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', NULL, 2, '专业保洁6年，开荒/日常/深度保洁', 1, 22.540000, 113.950000, 4.90, 1, '2026-01-15 00:00:00', '2026-06-06 14:18:18');
INSERT INTO `worker` VALUES (4, '陈师傅', '13900004444', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', NULL, 1, '家电维修/电路排查/智能家居安装', 0, 23.080000, 113.460000, 4.20, 1, '2026-02-01 00:00:00', '2026-06-01 20:22:13');
INSERT INTO `worker` VALUES (5, '周师傅', '13900005555', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', NULL, 1, '水管维修/防水补漏/暖气地暖', 1, 23.000000, 113.300000, 4.60, 1, '2026-03-01 00:00:00', '2026-07-15 16:16:50');
INSERT INTO `worker` VALUES (6, '谢师傅', '13456789001', '$2a$10$EvwOh73YwLYR.TZjU/zolOfEtVrGgKaEJfZl9ZCrFT30SqO70543e', '/uploads/7be9507b-a5e8-4014-9a7d-c2383724fef7.jpg', 1, '水管维修/防水补漏/暖气地暖', 1, 23.000000, 113.300000, 5.00, 1, '2026-06-06 14:41:59', '2026-09-11 16:44:56');
INSERT INTO `worker` VALUES (7, '王师傅', '13456789002', '$2a$10$3lke7A9dDYoFy9fTpWvAged51PwJrWsPd2N3x3yrAgV3IR6lon9pK', NULL, 1, '10年经验', 1, NULL, NULL, NULL, 1, '2026-06-06 17:10:29', '2026-09-11 22:10:52');

SET FOREIGN_KEY_CHECKS = 1;
