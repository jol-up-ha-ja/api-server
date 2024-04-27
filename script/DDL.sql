-- scheme
CREATE DATABASE balancemania CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

-- 유저 정보
CREATE TABLE `user`
(
    `id`                bigint       NOT NULL AUTO_INCREMENT COMMENT 'user id',
    `name`              varchar(256) NOT NULL COMMENT 'user 이름',
    `created_at`        datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    `modified_at`       datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=200000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='유저 정보';
