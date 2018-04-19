-- MySQL dump 10.16  Distrib 10.2.13-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	10.2.13-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `contato`
--

DROP TABLE IF EXISTS `contato`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contato` (
  `codigo_contato` bigint(20) NOT NULL,
  `cpf` bigint(20) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nomecontato` varchar(255) DEFAULT NULL,
  `telefonecelular` int(11) DEFAULT NULL,
  `telefoneresidencial` int(11) DEFAULT NULL,
  `codigoarea` bigint(20) DEFAULT NULL,
  `codigoempresa` bigint(20) DEFAULT NULL,
  `dataregistro` datetime NOT NULL,
  `cpf_formatado` varchar(255) DEFAULT NULL,
  `telefone_celular_formatado` varchar(255) DEFAULT NULL,
  `telefone_residencial_formatado` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`codigo_contato`),
  UNIQUE KEY `UKnmyajsblxiii9ljlkoc9ao2dt` (`cpf`,`nomecontato`,`codigoempresa`),
  UNIQUE KEY `UK_v6ynadwmlgiar3tqrydve73q` (`cpf`),
  UNIQUE KEY `UK_4ds5brlm0e7etaij4k5twocp4` (`email`),
  KEY `FKlybra677lke05k7i2y1p0vl1h` (`codigoarea`),
  KEY `FKcq6g378wvstiwvqjdcrta1bs6` (`codigoempresa`),
  CONSTRAINT `FKcq6g378wvstiwvqjdcrta1bs6` FOREIGN KEY (`codigoempresa`) REFERENCES `empresa` (`codigo_empresa`),
  CONSTRAINT `FKlybra677lke05k7i2y1p0vl1h` FOREIGN KEY (`codigoarea`) REFERENCES `area` (`codigo_area`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contato`
--

LOCK TABLES `contato` WRITE;
/*!40000 ALTER TABLE `contato` DISABLE KEYS */;
INSERT INTO `contato` VALUES (10,6031004577,'diegosantana019@gmail.com','Diego Santana dos Santos',82068116,33333333,1,11,'2018-04-16 00:00:00','060.310.045-77','8206-8116','3333-3333');
/*!40000 ALTER TABLE `contato` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-18 19:17:18
