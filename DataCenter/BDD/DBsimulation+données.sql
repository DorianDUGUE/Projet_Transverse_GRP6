-- Adminer 4.7.8 MySQL dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

DROP DATABASE IF EXISTS `simulation`;
CREATE DATABASE `simulation` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;
USE `simulation`;

DROP TABLE IF EXISTS `capteur`;
CREATE TABLE `capteur` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `x` float NOT NULL,
  `y` float NOT NULL,
  `valeur` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `capteur` (`id`, `x`, `y`, `valeur`) VALUES
(1,	45.77,	4.82,	10),
(2,	45.77,	4.83,	4),
(3,	45.77,	4.84,	10),
(4,	45.77,	4.85,	0),
(5,	45.77,	4.86,	0),
(6,	45.77,	4.87,	0),
(7,	45.77,	4.88,	1),
(8,	45.77,	4.89,	8),
(9,	45.77,	4.9,	9),
(10,	45.76,	4.82,	1),
(11,	45.76,	4.83,	0),
(12,	45.76,	4.84,	0),
(13,	45.76,	4.85,	0),
(14,	45.76,	4.86,	0),
(15,	45.76,	4.87,	0),
(16,	45.76,	4.88,	0),
(17,	45.76,	4.89,	0),
(18,	45.76,	4.9,	0),
(19,	45.75,	4.82,	3),
(20,	45.75,	4.83,	7),
(21,	45.75,	4.84,	0),
(22,	45.75,	4.85,	0),
(23,	45.75,	4.86,	6),
(24,	45.75,	4.87,	4),
(25,	45.75,	4.88,	0),
(26,	45.75,	4.89,	0),
(27,	45.75,	4.9,	2),
(28,	45.74,	4.82,	9),
(29,	45.74,	4.83,	3),
(30,	45.74,	4.84,	4),
(31,	45.74,	4.85,	0),
(32,	45.74,	4.86,	0),
(33,	45.74,	4.87,	0),
(34,	45.74,	4.88,	0),
(35,	45.74,	4.89,	0),
(36,	45.74,	4.9,	4);

-- 2021-01-06 09:15:05
