DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `auth`;

CREATE TABLE `auth`
(
    `auth_id`   bigint      NOT NULL,
    `user_id` bigint      NOT NULL,
    `role`      varchar(45) NOT NULL,
    PRIMARY KEY (`auth_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user`
(
    `id`       bigint       NOT NULL AUTO_INCREMENT,
    `email`    varchar(128) NOT NULL,
    `password` varchar(128) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
