/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3306
 Source Schema         : study

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 16/06/2022 15:06:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for menu1
-- ----------------------------
DROP TABLE IF EXISTS `menu1`;
CREATE TABLE `menu1`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pattern` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu1
-- ----------------------------
INSERT INTO `menu1` VALUES (1, '/admin/**');
INSERT INTO `menu1` VALUES (2, '/user/**');
INSERT INTO `menu1` VALUES (3, '/guest/**');

-- ----------------------------
-- Table structure for menu1_role1
-- ----------------------------
DROP TABLE IF EXISTS `menu1_role1`;
CREATE TABLE `menu1_role1`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mid` int(11) DEFAULT NULL,
  `rid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu1_role1
-- ----------------------------
INSERT INTO `menu1_role1` VALUES (1, 1, 1);
INSERT INTO `menu1_role1` VALUES (2, 2, 1);
INSERT INTO `menu1_role1` VALUES (3, 2, 2);
INSERT INTO `menu1_role1` VALUES (4, 3, 1);
INSERT INTO `menu1_role1` VALUES (5, 3, 2);
INSERT INTO `menu1_role1` VALUES (6, 3, 3);

-- ----------------------------
-- Table structure for role1
-- ----------------------------
DROP TABLE IF EXISTS `role1`;
CREATE TABLE `role1`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `nameZh` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role1
-- ----------------------------
INSERT INTO `role1` VALUES (1, 'ROLE_ADMIN', '系统管理员');
INSERT INTO `role1` VALUES (2, 'ROLE_USER', '普通用户');
INSERT INTO `role1` VALUES (3, 'ROLE_GUEST', '游客');

-- ----------------------------
-- Table structure for user1
-- ----------------------------
DROP TABLE IF EXISTS `user1`;
CREATE TABLE `user1`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  `locked` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user1
-- ----------------------------
INSERT INTO `user1` VALUES (1, 'admin', '{noop}123', 1, 0);
INSERT INTO `user1` VALUES (2, 'user', '{noop}123', 1, 0);
INSERT INTO `user1` VALUES (3, 'xmxe', '{noop}123', 1, 0);

-- ----------------------------
-- Table structure for user1_role1
-- ----------------------------
DROP TABLE IF EXISTS `user1_role1`;
CREATE TABLE `user1_role1`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `rid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user1_role1
-- ----------------------------
INSERT INTO `user1_role1` VALUES (1, 1, 1);
INSERT INTO `user1_role1` VALUES (2, 2, 2);
INSERT INTO `user1_role1` VALUES (3, 3, 3);

SET FOREIGN_KEY_CHECKS = 1;
