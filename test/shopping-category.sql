DROP TABLE IF EXISTS `neuedu_category`;
CREATE TABLE `neuedu_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '类别id',
  `parent_id` int(11) DEFAULT NULL COMMENT '父类Id,当pareng_id=0,说明是根节点，一级类别',
  `name` varchar(50) DEFAULT NULL COMMENT '类别名称',
  `status` tinyint(1) DEFAULT '1' COMMENT '类别状态1-正常，2-已废弃',
  `sort_order` int(4) DEFAULT NULL COMMENT '排序编号，同类展示顺序，数值相等则自然排序',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of neuedu_category
-- ----------------------------
INSERT INTO `neuedu_category` VALUES ('1', '0', '手机数码', '1', '1', '2018-11-29 05:13:37', '2018-11-29 05:13:37');
INSERT INTO `neuedu_category` VALUES ('2', '1', '华为（HUAWEI）', '1', '2', '2018-11-29 05:13:37', '2018-11-29 05:13:37');
INSERT INTO `neuedu_category` VALUES ('3', '1', '小米（MI）', '1', '3', '2018-11-29 05:13:37', '2018-11-29 05:13:37');
INSERT INTO `neuedu_category` VALUES ('4', '1', 'Apple', '1', '4', '2018-11-29 05:13:37', '2018-11-29 05:13:37');
INSERT INTO `neuedu_category` VALUES ('5', '1', 'OPPO', '1', '5', '2018-11-29 05:13:37', '2018-11-29 05:13:37');
INSERT INTO `neuedu_category` VALUES ('6', '1', 'vivo', '1', '6', '2018-11-29 05:13:37', '2018-11-29 05:13:37');
INSERT INTO `neuedu_category` VALUES ('7', '1', '魅族（MEIZU）', '1', '7', '2018-11-29 05:13:37', '2018-11-29 05:13:37');
INSERT INTO `neuedu_category` VALUES ('8', '1', '三星（SAMSUNG）', '1', '8', '2018-11-29 05:13:37', '2018-11-29 05:13:37');
INSERT INTO `neuedu_category` VALUES ('9', '1', '锤子（smartisan）', '1', '9', '2018-11-29 05:13:37', '2018-11-29 05:13:37');
INSERT INTO `neuedu_category` VALUES ('10', '1', '诺基亚（NOKIA）', '1', '10', '2018-11-29 05:13:37', '2018-11-29 05:13:37');
INSERT INTO `neuedu_category` VALUES ('11', '1', '360', '1', '11', '2018-11-29 05:13:37', '2018-11-29 05:13:37');
INSERT INTO `neuedu_category` VALUES ('12', '1', '一加', '1', '12', '2018-11-29 05:13:37', '2018-11-29 05:13:37');
INSERT INTO `neuedu_category` VALUES ('13', '1', '联想（Lenovo）', '1', '13', '2018-11-29 05:13:37', '2018-11-29 05:13:37');
INSERT INTO `neuedu_category` VALUES ('14', '1', '努比亚（nubia）', '1', '14', '2018-11-29 05:13:37', '2018-11-29 05:13:37');
INSERT INTO `neuedu_category` VALUES ('15', '1', '飞利浦（PHILIPS）', '1', '15', '2018-11-29 05:13:37', '2018-11-29 05:13:37');
INSERT INTO `neuedu_category` VALUES ('16', '1', '荣耀', '1', '16', '2018-11-29 05:13:37', '2018-11-29 05:13:37');
INSERT INTO `neuedu_category` VALUES ('17', '1', '美图（meitu）', '1', '17', '2018-11-29 05:13:37', '2018-11-29 05:13:37');
INSERT INTO `neuedu_category` VALUES ('18', '1', '黑鲨', '1', '18', '2018-11-29 05:13:37', '2018-11-29 05:13:37');
INSERT INTO `neuedu_category` VALUES ('19', '1', '摩托罗拉（Motorola）', '1', '19', '2018-11-29 05:13:37', '2018-11-29 05:13:37');
INSERT INTO `neuedu_category` VALUES ('20', '0', '笔记本电脑', '1', null, '2018-12-07 14:34:31', '2018-12-07 14:34:31');
INSERT INTO `neuedu_category` VALUES ('21', '20', '联想（Lenovo）', '1', null, '2018-12-07 14:34:31', '2018-12-07 14:34:31');
INSERT INTO `neuedu_category` VALUES ('22', '20', '戴尔（DELL）', '1', null, '2018-12-07 14:34:31', '2018-12-07 14:34:31');
INSERT INTO `neuedu_category` VALUES ('23', '20', 'ThinkPad', '1', null, '2018-12-07 14:34:31', '2018-12-07 14:34:31');
INSERT INTO `neuedu_category` VALUES ('24', '20', '华硕（ASUS）', '1', null, '2018-12-07 14:34:31', '2018-12-07 14:34:31');
INSERT INTO `neuedu_category` VALUES ('25', '20', '惠普（HP）', '1', null, '2018-12-07 14:34:31', '2018-12-07 14:34:31');
INSERT INTO `neuedu_category` VALUES ('26', '20', '小米（MI）', '1', null, '2018-12-07 14:34:31', '2018-12-07 14:34:31');
INSERT INTO `neuedu_category` VALUES ('27', '20', '华为（HUAWEI）', '1', null, '2018-12-07 14:34:31', '2018-12-07 14:34:31');
INSERT INTO `neuedu_category` VALUES ('28', '20', 'Apple', '1', null, '2018-12-07 14:34:31', '2018-12-07 14:34:31');
INSERT INTO `neuedu_category` VALUES ('29', '20', '宏碁（acer）', '1', null, '2018-12-07 14:34:31', '2018-12-07 14:34:31');
INSERT INTO `neuedu_category` VALUES ('30', '20', '微软（Microsoft）', '1', null, '2018-12-07 14:34:31', '2018-12-07 14:34:31');
INSERT INTO `neuedu_category` VALUES ('31', '20', '三星（SAMSUNG）', '1', null, '2018-12-07 14:34:31', '2018-12-07 14:34:31');
INSERT INTO `neuedu_category` VALUES ('32', '20', '神舟（HASEE）', '1', null, '2018-12-07 14:34:31', '2018-12-07 14:34:31');
INSERT INTO `neuedu_category` VALUES ('33', '20', '外星人（Alienware）', '1', null, '2018-12-07 14:34:31', '2018-12-07 14:34:31');
INSERT INTO `neuedu_category` VALUES ('34', '20', '微星（MSI）', '1', null, '2018-12-07 14:34:31', '2018-12-07 14:34:31');
INSERT INTO `neuedu_category` VALUES ('35', '20', '雷蛇（Razer）', '1', null, '2018-12-07 14:34:31', '2018-12-07 14:34:31');
INSERT INTO `neuedu_category` VALUES ('36', '20', '机械革命（MECHREVO）', '1', null, '2018-12-07 14:34:31', '2018-12-07 14:34:31');
INSERT INTO `neuedu_category` VALUES ('37', '20', '荣耀', '1', null, '2018-12-07 14:34:31', '2018-12-07 14:34:31');
INSERT INTO `neuedu_category` VALUES ('38', '20', '清华同方（THTF）', '1', null, '2018-12-07 14:34:31', '2018-12-07 14:34:31');
INSERT INTO `neuedu_category` VALUES ('39', '0', '平板电脑', '1', null, '2018-12-07 16:37:53', '2018-12-07 16:37:53');
INSERT INTO `neuedu_category` VALUES ('40', '39', '华为（HUAWEI）', '1', null, '2018-12-07 16:41:39', '2018-12-07 16:41:39');
INSERT INTO `neuedu_category` VALUES ('41', '39', 'Apple', '1', null, '2018-12-07 16:41:39', '2018-12-07 16:41:39');
INSERT INTO `neuedu_category` VALUES ('42', '39', '微软（Microsoft）', '1', null, '2018-12-07 16:41:39', '2018-12-07 16:41:39');
INSERT INTO `neuedu_category` VALUES ('43', '39', '小米（MI）', '1', null, '2018-12-07 16:41:39', '2018-12-07 16:41:39');
INSERT INTO `neuedu_category` VALUES ('44', '39', '联想（Lenovo）', '1', null, '2018-12-07 16:41:39', '2018-12-07 16:41:39');
INSERT INTO `neuedu_category` VALUES ('45', '39', '台电（TECLAST）', '1', null, '2018-12-07 16:41:39', '2018-12-07 16:41:39');
INSERT INTO `neuedu_category` VALUES ('46', '39', '中柏（Jumper）', '1', null, '2018-12-07 16:41:39', '2018-12-07 16:41:39');
INSERT INTO `neuedu_category` VALUES ('47', '39', '三星（SAMSUNG）', '1', null, '2018-12-07 16:41:39', '2018-12-07 16:41:39');
INSERT INTO `neuedu_category` VALUES ('48', '39', '酷比魔方', '1', null, '2018-12-07 16:41:39', '2018-12-07 16:41:39');
INSERT INTO `neuedu_category` VALUES ('49', '39', '紫光电子（Uniscom）', '1', null, '2018-12-07 16:41:39', '2018-12-07 16:41:39');
INSERT INTO `neuedu_category` VALUES ('50', '39', '戴尔（DELL）', '1', null, '2018-12-07 16:41:39', '2018-12-07 16:41:39');
INSERT INTO `neuedu_category` VALUES ('51', '39', 'ThinkPad', '1', null, '2018-12-07 16:41:39', '2018-12-07 16:41:39');
INSERT INTO `neuedu_category` VALUES ('52', '39', '华硕（ASUS）', '1', null, '2018-12-07 16:41:39', '2018-12-07 16:41:39');
INSERT INTO `neuedu_category` VALUES ('53', '39', '惠普（HP）', '1', null, '2018-12-07 16:41:39', '2018-12-07 16:41:39');
INSERT INTO `neuedu_category` VALUES ('54', '39', '荣耀', '1', null, '2018-12-07 16:41:39', '2018-12-07 16:41:39');
INSERT INTO `neuedu_category` VALUES ('55', '39', 'E人E本', '1', null, '2018-12-07 16:41:39', '2018-12-07 16:41:39');
INSERT INTO `neuedu_category` VALUES ('56', '39', '昂达（ONDA）', '1', null, '2018-12-07 16:41:39', '2018-12-07 16:41:39');
INSERT INTO `neuedu_category` VALUES ('57', '39', '博智星', '1', null, '2018-12-07 16:41:39', '2018-12-07 16:41:39');
INSERT INTO `neuedu_category` VALUES ('71', '0', '超市', '1', '0', '2018-12-19 10:38:00', '2018-12-19 10:38:00');
INSERT INTO `neuedu_category` VALUES ('72', '0', '生鲜', '1', '0', '2018-12-19 10:39:00', '2018-12-19 10:39:00');
INSERT INTO `neuedu_category` VALUES ('73', '0', '男装', '1', '0', '2018-12-19 10:39:00', '2018-12-19 10:39:00');
INSERT INTO `neuedu_category` VALUES ('74', '0', '家电', '1', '0', '2018-12-19 10:40:00', '2018-12-19 10:40:00');
INSERT INTO `neuedu_category` VALUES ('75', '0', '女装', '1', '0', '2018-12-19 10:40:00', '2018-12-19 10:40:00');
INSERT INTO `neuedu_category` VALUES ('76', '0', '数码相机', '1', '0', '2019-01-04 14:12:00', '2019-01-04 14:12:00');
INSERT INTO `neuedu_category` VALUES ('77', '0', '家电2', '1', '0', '2019-01-07 13:13:00', '2019-01-07 13:13:00');