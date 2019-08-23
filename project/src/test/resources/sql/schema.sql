
DROP TABLE IF EXISTS `t_withdraw`;
CREATE TABLE `t_withdraw` (
  `f_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT ,
  `f_user_id` bigint(20) unsigned NOT NULL ,
  `f_address` varchar(50) NOT NULL ,
  `f_currency` varchar(20) NOT NULL ,
  `f_txhash` varchar(50) NOT NULL DEFAULT '' ,
  `f_amount` decimal(36,18) unsigned NOT NULL ,
  `f_fee` decimal(36,18) unsigned NOT NULL DEFAULT '0.000000000000000000' ,
  `f_real_fee` decimal(36,18) unsigned NOT NULL DEFAULT '0.000000000000000000' ,
  `f_confirm_times` tinyint(3) unsigned NOT NULL DEFAULT '0' ,
  `f_height` bigint(20) NOT NULL DEFAULT '0' ,
  `f_extra` varchar(255) NOT NULL DEFAULT '' ,
  `f_created_at` bigint(20) unsigned NOT NULL ,
  `f_updated_at` bigint(20) unsigned NOT NULL ,
  `f_version` bigint(20) unsigned NOT NULL DEFAULT '0' ,
  `f_state` tinyint(4) unsigned NOT NULL DEFAULT '0' ,
  PRIMARY KEY (`f_id`),
  UNIQUE KEY `uniq_id` (`f_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_user_withdraw_address`;
CREATE TABLE `t_user_withdraw_address` (
  `f_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT ,
  `f_user_id` bigint(20) unsigned NOT NULL ,
  `f_address` varchar(50) NOT NULL ,
  `f_version` bigint(20) unsigned NOT NULL DEFAULT '0',
  `f_state` tinyint(4) unsigned NOT NULL DEFAULT '0' ,
  PRIMARY KEY (`f_id`),
  UNIQUE KEY `uniq_user_address` (`f_user_id`,`f_address`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_user_info`;
CREATE TABLE `t_user_info` (
  `f_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT ,
  `f_user_name` varchar(30) NOT NULL ,
  `f_phone` varchar(15) NOT NULL ,
  `f_extra` varchar(255) NOT NULL DEFAULT '' ,
  `f_created_at` bigint(20) unsigned NOT NULL ,
  `f_updated_at` bigint(20) unsigned NOT NULL ,
  `f_version` bigint(20) unsigned NOT NULL DEFAULT '0' ,
  `f_state` tinyint(3) unsigned NOT NULL DEFAULT '0' ,
  PRIMARY KEY (`f_id`),
  UNIQUE KEY `uniq_user_name_phone` (`f_user_name`,`f_phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_user_account`;
CREATE TABLE `t_user_account` (
  `f_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT ,
  `f_user_id` varchar(30) NOT NULL ,
  `f_currency` varchar(20) NOT NULL ,
  `f_balance` decimal(36,18) unsigned NOT NULL ,
  `f_extra` varchar(255) NOT NULL DEFAULT '' ,
  `f_created_at` bigint(20) unsigned NOT NULL ,
  `f_updated_at` bigint(20) unsigned NOT NULL ,
  `f_version` bigint(20) unsigned NOT NULL DEFAULT '0' ,
  `f_state` tinyint(3) unsigned NOT NULL DEFAULT '0' ,
  PRIMARY KEY (`f_id`),
  UNIQUE KEY `uniq_user_currency` (`f_user_id`,`f_currency`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_currency_info`;
CREATE TABLE `t_currency_info` (
  `f_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT ,
  `f_currency_no` varchar(20) NOT NULL ,
  `f_version` bigint(20) unsigned NOT NULL DEFAULT '0' ,
  `f_state` tinyint(3) unsigned NOT NULL DEFAULT '0' ,
  PRIMARY KEY (`f_id`),
  UNIQUE KEY `uniq_currency_no` (`f_currency_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;