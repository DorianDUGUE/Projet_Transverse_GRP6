-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3308
-- Généré le :  mar. 08 déc. 2020 à 23:21
-- Version du serveur :  8.0.18
-- Version de PHP :  7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `emergency`
--
CREATE DATABASE IF NOT EXISTS `emergency` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
USE `emergency`;

-- --------------------------------------------------------

--
-- Structure de la table `camion`
--

DROP TABLE IF EXISTS `camion`;
CREATE TABLE IF NOT EXISTS `camion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `x` int(20) NOT NULL,
  `y` int(20) NOT NULL,
  `capacite` int(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Structure de la table `capteur`
--

DROP TABLE IF EXISTS `capteur`;
CREATE TABLE IF NOT EXISTS `capteur` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `x` int(20) NOT NULL,
  `y` int(20) NOT NULL,
  `Valeur` int(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Structure de la table `incendie`
--

DROP TABLE IF EXISTS `incendie`;
CREATE TABLE IF NOT EXISTS `incendie` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `etat` varchar(200) COLLATE utf8_bin NOT NULL,
  `heure` datetime NOT NULL,
  `idIntervention` int(20) NOT NULL,
  `numCapteur` int(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idIntervention` (`idIntervention`),
  KEY `numCapteur` (`numCapteur`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Structure de la table `intervention`
--

DROP TABLE IF EXISTS `intervention`;
CREATE TABLE IF NOT EXISTS `intervention` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `heureDebut` datetime NOT NULL,
  `numCamion` int(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `numCamion` (`numCamion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `incendie`
--
ALTER TABLE `incendie`
  ADD CONSTRAINT `incendie_ibfk_1` FOREIGN KEY (`numCapteur`) REFERENCES `capteur` (`id`),
  ADD CONSTRAINT `incendie_ibfk_2` FOREIGN KEY (`idIntervention`) REFERENCES `intervention` (`id`);

--
-- Contraintes pour la table `intervention`
--
ALTER TABLE `intervention`
  ADD CONSTRAINT `intervention_ibfk_1` FOREIGN KEY (`numCamion`) REFERENCES `camion` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
