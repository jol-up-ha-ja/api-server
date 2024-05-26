-- scheme
CREATE
DATABASE balancemania CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

-- 유저 정보
CREATE TABLE `user`
(
    `id`             bigint       NOT NULL AUTO_INCREMENT COMMENT 'user id',
    `oauth_provider` int          NOT NULL COMMENT 'oauth 제공자, KAKAO: 0',
    `oauth_id`       varchar(256) NOT NULL COMMENT 'oauth id',
    `name`           varchar(256) NOT NULL COMMENT 'user 이름',
    `gender`         int      DEFAULT NULL COMMENT 'user 성별, 남성: 0, 여성: 1',
    `birth`          date     DEFAULT NULL COMMENT 'user 출생년도',
    `status_type`    int          NOT NULL COMMENT '상태 정보 타입 정보 / 활동 : 1, 탈퇴 : 2,  일시 정지 7일 : 3, 영구 정지 : 4',
    `created_at`     datetime DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    `modified_at`    datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=200000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='유저 정보';
