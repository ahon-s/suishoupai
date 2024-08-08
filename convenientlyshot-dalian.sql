/*
 Navicat Premium Data Transfer

 Source Server         : 大连4.24
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : 172.17.4.24:3306
 Source Schema         : convenientlyshot-dalian

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 23/07/2024 12:43:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for accept_problem
-- ----------------------------
DROP TABLE IF EXISTS `accept_problem`;
CREATE TABLE `accept_problem`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '受理记录id',
  `accepter` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '受理人账号',
  `accepter_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '受理人id',
  `problem_id` int(0) NULL DEFAULT NULL COMMENT '对应问题id',
  `description` varchar(600) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '受理描述',
  `status` int(0) NULL DEFAULT NULL COMMENT '受理状态  1 是 2 否 ',
  `confirm_status` int(0) NULL DEFAULT NULL COMMENT '0不受理 1 待确认 2 工程部确认 3 驳回',
  `build_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '工程部门',
  `build_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '工程部id',
  `section_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标段名称',
  `section_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标段id',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `deleted` int(0) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 98 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for conclude
-- ----------------------------
DROP TABLE IF EXISTS `conclude`;
CREATE TABLE `conclude`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `concluder` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '结办人',
  `concluder_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '结办人id',
  `confirm_verify_id` int(0) NULL DEFAULT NULL COMMENT '确认审核记录id',
  `problem_id` int(0) NULL DEFAULT NULL COMMENT '问题id',
  `rectify_id` int(0) NULL DEFAULT NULL COMMENT '整改记录id',
  `verify_id` int(0) NULL DEFAULT NULL COMMENT '审阅id',
  `description` varchar(600) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '结办描述',
  `status` int(0) NULL DEFAULT NULL COMMENT '结办状态 1 是 2 否',
  `gmt_create` datetime(0) NULL DEFAULT NULL,
  `gmt_modified` datetime(0) NULL DEFAULT NULL,
  `deleted` int(0) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for confirm_problem
-- ----------------------------
DROP TABLE IF EXISTS `confirm_problem`;
CREATE TABLE `confirm_problem`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `confirmer` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '确认人',
  `confirmer_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '确认人id',
  `problem_id` int(0) NULL DEFAULT NULL COMMENT '问题id',
  `accept_id` int(0) NULL DEFAULT NULL COMMENT '受理id',
  `description` varchar(600) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '确认描述',
  `status` int(0) NULL DEFAULT NULL COMMENT '受理状态',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `build_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '工程部id',
  `build_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '工程部名称',
  `supervisor` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分配监理',
  `supervisor_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '监理id',
  `deleted` int(0) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 52 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for confirm_verify
-- ----------------------------
DROP TABLE IF EXISTS `confirm_verify`;
CREATE TABLE `confirm_verify`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `confirmer` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '确认人',
  `confirmer_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '确认人id',
  `verify_id` int(0) NULL DEFAULT NULL COMMENT '审核记录id',
  `rectify_id` int(0) NULL DEFAULT NULL COMMENT '整改记录id',
  `problem_id` int(0) NULL DEFAULT NULL COMMENT '问题id',
  `description` varchar(600) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '审核意见',
  `status` int(0) NULL DEFAULT NULL COMMENT '1 待审核结办 2 审核结办通过 3不通过',
  `gmt_create` datetime(0) NULL DEFAULT NULL,
  `gmt_modified` datetime(0) NULL DEFAULT NULL,
  `deleted` int(0) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for problem
-- ----------------------------
DROP TABLE IF EXISTS `problem`;
CREATE TABLE `problem`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `submitter` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '提交人姓名',
  `submitter_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '提交人id',
  `submitter_section` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '提交人所属标段',
  `description` varchar(600) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '问题描述',
  `lat` double NULL DEFAULT NULL COMMENT '纬度',
  `picture_url` varchar(2550) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '照片地址',
  `lng` double NULL DEFAULT NULL COMMENT '经度',
  `type` int(0) NULL DEFAULT NULL COMMENT '问题类型	1：日常检查，2：随手拍',
  `section_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '问题所属标段id',
  `section_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标段名称',
  `rectify` int(0) NULL DEFAULT NULL COMMENT '是否需要整改 1否 2是',
  `status` int(0) NULL DEFAULT NULL COMMENT '问题状态',
  `deleted` int(0) NULL DEFAULT 0 COMMENT '删除位',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `A` double NULL DEFAULT NULL COMMENT 'A坐标',
  `B` double NULL DEFAULT NULL COMMENT 'B坐标',
  `phone_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `rectify_department` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '整改部门',
  `verify_department` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '审核部门',
  `rectify_department_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '整改部门id',
  `verify_department_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '审核部门id',
  `assign_status` int(0) NULL DEFAULT NULL COMMENT '分配状态',
  `rectify_time` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '需整改时间',
  `supervisor` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '监理',
  `supervisor_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '监理id',
  `build_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '工程部门',
  `build_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '工程部门id',
  `location` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '问题发现 位置',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 205 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for problem_status
-- ----------------------------
DROP TABLE IF EXISTS `problem_status`;
CREATE TABLE `problem_status`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for problem_type
-- ----------------------------
DROP TABLE IF EXISTS `problem_type`;
CREATE TABLE `problem_type`  (
  `id` int(0) NOT NULL COMMENT '问题类型id',
  `type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '问题类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rectify
-- ----------------------------
DROP TABLE IF EXISTS `rectify`;
CREATE TABLE `rectify`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '整改记录id',
  `rectifier` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '整改人账号',
  `rectifier_section` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '整改人所属标段',
  `problem_id` int(0) NULL DEFAULT NULL COMMENT '对应问题id',
  `description` varchar(600) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '整改描述',
  `picture_url` varchar(2550) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '整改图片',
  `status` int(0) NULL DEFAULT NULL COMMENT '整改状态 1 待审阅  2 审阅通过 3 审阅不通过',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `deleted` int(0) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 70 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rectify_supervisor
-- ----------------------------
DROP TABLE IF EXISTS `rectify_supervisor`;
CREATE TABLE `rectify_supervisor`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '监理整改id',
  `rectifer` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '整改人',
  `rectifer_id` int(0) NULL DEFAULT NULL COMMENT '整改人id',
  `problem_id` int(0) NULL DEFAULT NULL COMMENT '问题id',
  `description` varchar(600) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '整改描述',
  `picture_url` varchar(2550) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '照片地址',
  `status` int(0) NULL DEFAULT NULL COMMENT '整改状态 1 待审核 2 待结办 3 完成',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `deleted` int(0) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for verify
-- ----------------------------
DROP TABLE IF EXISTS `verify`;
CREATE TABLE `verify`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '审阅记录id',
  `verifier` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '审阅人',
  `nick_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `verifier_section` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '审阅人标段',
  `rectify_id` int(0) NULL DEFAULT NULL COMMENT '整改记录id',
  `problem_id` int(0) NULL DEFAULT NULL COMMENT '对应问题id',
  `description` varchar(600) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '审核批注',
  `rectify` int(0) NULL DEFAULT NULL COMMENT '是否再次整改1：否 2：是',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `deleted` int(0) NULL DEFAULT 0,
  `status` int(0) NULL DEFAULT NULL COMMENT '1待审核 2 待结办 3 完成',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
