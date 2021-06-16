/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : myzstu-user

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 22/05/2021 22:11:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for myzstu_role
-- ----------------------------
DROP TABLE IF EXISTS `myzstu_role`;
CREATE TABLE `myzstu_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `creator` int(11) NULL DEFAULT NULL COMMENT '创建者',
  `updater` int(11) NULL DEFAULT NULL COMMENT '更新者',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否删除',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for myzstu_user
-- ----------------------------
DROP TABLE IF EXISTS `myzstu_user`;
CREATE TABLE `myzstu_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `nickname` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `signature` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '签名',
  `sex` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `enable` tinyint(1) NULL DEFAULT NULL COMMENT '是否可用',
  `locked` tinyint(1) NULL DEFAULT NULL COMMENT '是否锁定',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `telephone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '移动电话',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `avatar` varchar(511) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否删除',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 55 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for myzstu_user_role
-- ----------------------------
DROP TABLE IF EXISTS `myzstu_user_role`;
CREATE TABLE `myzstu_user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色映射表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
