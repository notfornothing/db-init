/*
 Navicat Premium Data Transfer

 Source Server         : localhost-3306
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : localhost:3306
 Source Schema         : husoul

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 11/05/2022 14:52:15
*/

SET NAMES utf8mb4;

-- ----------------------------
-- Table structure for clock_history
-- ----------------------------
DROP TABLE IF EXISTS `clock_history`;
CREATE TABLE `clock_history`  (
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  `user_account` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户账号',
  `clock_info` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '打卡内容',
  `clock_date` datetime(0) NULL DEFAULT NULL COMMENT '打卡日期',
  INDEX `user_id_his`(`user_id`) USING BTREE COMMENT '历史任务用户ID索引，提高用户历史任务查询速度'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of clock_history
-- ----------------------------

-- ----------------------------
-- Table structure for clock_today
-- ----------------------------
DROP TABLE IF EXISTS `clock_today`;
CREATE TABLE `clock_today`  (
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `user_account` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户账号',
  `clock_info` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '打卡内容（使用json封装）',
  `clock_date` datetime(0) NULL DEFAULT NULL COMMENT '打卡日期',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of clock_today
-- ----------------------------
INSERT INTO `clock_today` VALUES (1, 'xxhu', '[{\"time\":\"09:00\",\"title\":\"上班\",\"info\":\"\",\"status\":\"done\"},{\"time\":\"18:00\",\"title\":\"下班\",\"info\":\"\",\"status\":\"done\"},{\"time\":\"18:30\",\"title\":\"吃晚饭\",\"info\":\"\",\"status\":\"doing\"}]', '2022-02-22 01:33:13');

-- ----------------------------
-- Table structure for clock_user
-- ----------------------------
DROP TABLE IF EXISTS `clock_user`;
CREATE TABLE `clock_user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID（主键）',
  `user_account` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户账号',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_active_date` datetime(0) NULL DEFAULT NULL COMMENT '最后活动时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `user_account_unique`(`user_account`) USING BTREE COMMENT '用户账号唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of clock_user
-- ----------------------------
INSERT INTO `clock_user` VALUES (12, 'xxhu', '2022-02-21 09:03:04', '2022-02-21 09:03:04');

-- ----------------------------
-- Table structure for day_read_num
-- ----------------------------
DROP TABLE IF EXISTS `day_read_num`;
CREATE TABLE `day_read_num`  (
  `day` datetime(0) NULL DEFAULT NULL COMMENT '日期',
  `addnum` int(11) NULL DEFAULT NULL COMMENT '新增浏览量'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of day_read_num
-- ----------------------------

-- ----------------------------
-- Table structure for soul
-- ----------------------------
DROP TABLE IF EXISTS `soul`;
CREATE TABLE `soul`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1' COMMENT '主键',
  `soul_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文章名称',
  `soul_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文章分类',
  `soul_time` datetime(0) NULL DEFAULT NULL COMMENT '文章时间',
  `soul_author` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作者',
  `sort` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '排序',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '01' COMMENT '状态：01已发布；02草稿；03已删除',
  `read_num` int(11) NULL DEFAULT 0 COMMENT '浏览次数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of soul
-- ----------------------------
INSERT INTO `soul` VALUES ('123', '123', '123', '2021-05-10 15:44:00', 'xxhu', '123', NULL, '01', 1);
INSERT INTO `soul` VALUES ('1234', '123', '123', '2021-05-10 15:44:21', 'xxhu', '123', NULL, '01', 1);
INSERT INTO `soul` VALUES ('1235', '123', '123', '2021-05-10 15:44:21', 'xxhu', '123', NULL, '01', 1);
INSERT INTO `soul` VALUES ('soul1620789457134', '测试', 'ceshi', '2021-05-12 11:17:37', 'xxhu', '1', '2021-05-12 18:45:54', '01', 1);
INSERT INTO `soul` VALUES ('soul1620808494031', '测试', 'ceshi', '2021-05-12 16:34:54', 'xxhu', '', NULL, '01', 1);
INSERT INTO `soul` VALUES ('soul1620808615457', 'windows 7', 'ceshi', '2021-05-12 16:36:55', 'xxhu', '', NULL, '01', 1);

-- ----------------------------
-- Table structure for soul_type
-- ----------------------------
DROP TABLE IF EXISTS `soul_type`;
CREATE TABLE `soul_type`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `type_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类名称',
  `type_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类编码（文件夹名称）',
  `sort` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of soul_type
-- ----------------------------
INSERT INTO `soul_type` VALUES ('123', '123', '123', '123');
INSERT INTO `soul_type` VALUES ('1234', '1234', '1234', '1234');
INSERT INTO `soul_type` VALUES ('12345', '测试', 'ceshi', '1');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '小胡', 'xxhu', 'glWYdMEsRRpfeZRuRitOHA==');

SET FOREIGN_KEY_CHECKS = 1;
