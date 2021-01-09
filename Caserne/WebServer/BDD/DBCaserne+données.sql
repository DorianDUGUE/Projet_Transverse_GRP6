SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

DROP DATABASE IF EXISTS `DBCaserne`;
CREATE DATABASE `DBCaserne` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `DBCaserne`;

DROP TABLE IF EXISTS `Camion`;
CREATE TABLE `Camion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `x` float NOT NULL,
  `y` float NOT NULL,
  `capacite` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Camion` (`id`, `x`, `y`, `capacite`) VALUES
(1,	45.762,	4.86408,	4),
(2,	45.7508,	4.86408,	5),
(3,	45.762,	4.87389,	3);

DROP TABLE IF EXISTS `Capteur`;
CREATE TABLE `Capteur` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `x` float NOT NULL,
  `y` float NOT NULL,
  `valeur` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Capteur` (`id`, `x`, `y`, `valeur`) VALUES
(3,	45.77,	4.84,	10),
(4,	45.77,	4.85,	4),
(5,	45.77,	4.83,	8);

DROP TABLE IF EXISTS `Incendie`;
CREATE TABLE `Incendie` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Etat` text NOT NULL,
  `idIntervention` int(11) NOT NULL,
  `NumCapteur` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idIntervention` (`idIntervention`),
  KEY `NumCapteur` (`NumCapteur`),
  CONSTRAINT `Incendie_ibfk_1` FOREIGN KEY (`idIntervention`) REFERENCES `Intervention` (`id`),
  CONSTRAINT `Incendie_ibfk_2` FOREIGN KEY (`NumCapteur`) REFERENCES `Capteur` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Incendie` (`id`, `Etat`, `idIntervention`, `NumCapteur`) VALUES
(1,	'En feu !',	1,	4),
(2,	'En feu !',	2,	5);

DROP TABLE IF EXISTS `Intervention`;
CREATE TABLE `Intervention` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `HeureDebut` datetime NOT NULL,
  `numCamion` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `numCamion` (`numCamion`),
  CONSTRAINT `Intervention_ibfk_1` FOREIGN KEY (`numCamion`) REFERENCES `Camion` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Intervention` (`id`, `HeureDebut`, `numCamion`) VALUES
(1,	'2021-01-06 10:11:28',	1),
(2,	'2021-01-06 10:11:40',	2);