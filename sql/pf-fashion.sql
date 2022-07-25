CREATE TABLE `sys_log`
(
    `id`           int unsigned NOT NULL AUTO_INCREMENT COMMENT '标识符',
    `ip`           varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT 'ip地址',
    `module_name`  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '模块名',
    `method`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作方法',
    `operation`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作描述',
    `take_up_time` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '耗时ms',
    `params`       varchar(1000) DEFAULT NULL COMMENT '参数',
    `year`         varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '年份',
    `create_by`    int                                                           NOT NULL COMMENT '操作者',
    `create_date`  datetime                                                      NOT NULL COMMENT '操作日期',
    PRIMARY KEY (`id`) USING BTREE,
    KEY            `sys_log_idx0` (`ip`,`module_name`,`method`,`year`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='系统-日志表';

CREATE TABLE `sys_user`
(
    `id`          int unsigned NOT NULL AUTO_INCREMENT COMMENT '标识符',
    `name`        varchar(32)                                                  NOT NULL COMMENT '用户名',
    `password`    varchar(64)                                                  NOT NULL COMMENT '密码',
    `mail`        varchar(200)                                                 NOT NULL COMMENT '邮箱',
    `phone`       varchar(20)                                                           DEFAULT NULL COMMENT '手机号',
    `role_id`     int                                                          NOT NULL DEFAULT '0' COMMENT '角色id',
    `year`        varchar(20)                                                  NOT NULL COMMENT '年份',
    `status`      tinyint(1) NOT NULL DEFAULT '0' COMMENT '用户状态(0审批 1正常 2锁定)',
    `create_by`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '创建人(0代表自己注册的 别的参数代表管理员操作)',
    `create_date` datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '修改人(0代表默认 别的参数代表管理员操作)',
    `update_date` datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `del_flag`    tinyint                                                      NOT NULL DEFAULT '1' COMMENT '删除状态(1正常 0删除)',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `sys_user_idx0` (`mail`) USING BTREE COMMENT '邮箱-唯一索引',
    KEY           `sys_user_idx1` (`name`,`mail`,`phone`,`year`,`status`,`del_flag`) USING BTREE COMMENT '组合索引'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='系统-用户表';