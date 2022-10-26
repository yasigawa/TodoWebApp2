DROP TABLE IF EXISTS user;

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userid` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `authority` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_index` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS todo;

CREATE TABLE `todo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `userid` varchar(100) NOT NULL,
  `title` varchar(100) NOT NULL,
  `details` varchar(255) DEFAULT NULL,
  `tododate` date DEFAULT NULL,
  `todotime` time DEFAULT NULL,
  `done` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
