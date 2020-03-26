-- MySQL dump 10.13  Distrib 8.0.19, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: covid19
-- ------------------------------------------------------
-- Server version	8.0.19-0ubuntu0.19.10.3

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `centro`
--

DROP TABLE IF EXISTS `centro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `centro` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) COLLATE utf8_spanish_ci NOT NULL,
  `codCentro` varchar(25) COLLATE utf8_spanish_ci NOT NULL,
  `telefono` varchar(25) COLLATE utf8_spanish_ci DEFAULT NULL,
  `email` varchar(100) COLLATE utf8_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `centro`
--

LOCK TABLES `centro` WRITE;
/*!40000 ALTER TABLE `centro` DISABLE KEYS */;
INSERT INTO `centro` VALUES (1,'Jardín Botánico','BOT','123454321','botanicPrueba@ucm.es');
/*!40000 ALTER TABLE `centro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `muestra`
--

DROP TABLE IF EXISTS `muestra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `muestra` (
  `id` int NOT NULL AUTO_INCREMENT,
  `etiqueta` varchar(100) COLLATE utf8_spanish_ci NOT NULL,
  `tipoMuestra` varchar(2) COLLATE utf8_spanish_ci NOT NULL,
  `fechaEntrada` datetime DEFAULT NULL,
  `fechaEnvio` datetime DEFAULT NULL,
  `fechaResultado` datetime DEFAULT NULL,
  `refInterna` varchar(100) COLLATE utf8_spanish_ci DEFAULT NULL,
  `resultado` varchar(25) COLLATE utf8_spanish_ci DEFAULT NULL,
  `idEstado` int DEFAULT NULL,
  `idCentro` int NOT NULL,
  `idLote` int NOT NULL,
  `NHC` varchar(100) COLLATE utf8_spanish_ci DEFAULT NULL,
  `recogerDatosNotif` tinyint(1) NOT NULL DEFAULT '0',
  `avisosAuto` tinyint(1) NOT NULL DEFAULT '0',
  `nombreApellidos` varchar(200) COLLATE utf8_spanish_ci NOT NULL,
  `correo` varchar(100) COLLATE utf8_spanish_ci DEFAULT NULL,
  `telefono` varchar(25) COLLATE utf8_spanish_ci DEFAULT NULL,
  `avisoSMS` tinyint(1) NOT NULL,
  `fechaNotificacion` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_muestra_1_idx` (`idCentro`),
  CONSTRAINT `fk_muestra_1` FOREIGN KEY (`idCentro`) REFERENCES `centro` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `muestra`
--

LOCK TABLES `muestra` WRITE;
/*!40000 ALTER TABLE `muestra` DISABLE KEYS */;
/*!40000 ALTER TABLE `muestra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rol`
--

DROP TABLE IF EXISTS `rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rol` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) COLLATE utf8_spanish_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rol`
--

LOCK TABLES `rol` WRITE;
/*!40000 ALTER TABLE `rol` DISABLE KEYS */;
INSERT INTO `rol` VALUES (1,'ADMIN'),(2,'USER'),(3,'MUESTREADOR'),(4,'RECEPTOR'),(5,'TECNICO'),(6,'JEFESERV'),(7,'GESTOR');
/*!40000 ALTER TABLE `rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) COLLATE utf8_spanish_ci NOT NULL,
  `apellido1` varchar(100) COLLATE utf8_spanish_ci NOT NULL,
  `apellido2` varchar(100) COLLATE utf8_spanish_ci DEFAULT NULL,
  `email` varchar(100) COLLATE utf8_spanish_ci NOT NULL,
  `password` varchar(255) COLLATE utf8_spanish_ci NOT NULL,
  `idCentro` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `fk_usuario_1_idx` (`idCentro`),
  CONSTRAINT `fk_usuario_1` FOREIGN KEY (`idCentro`) REFERENCES `centro` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'Pedro','Marrasan','Sánchez','pmarrasant@ucm.es','$2a$10$fIQ5Kj0v6RUHYwvaPpV.bO0QDPrJ601alFv9bJdtig7fTa5wySTIu',1),(2,'Sara','García','','sagarcia@ucm.es','$2a$10$fIQ5Kj0v6RUHYwvaPpV.bO0QDPrJ601alFv9bJdtig7fTa5wySTIu',NULL),(3,'Fernando','de las Heras','','fherasm@ucm.es','$2a$10$fIQ5Kj0v6RUHYwvaPpV.bO0QDPrJ601alFv9bJdtig7fTa5wySTIu',NULL),(4,'Yolanda','Roldan','','yroldan@ucm.es','$2a$10$fIQ5Kj0v6RUHYwvaPpV.bO0QDPrJ601alFv9bJdtig7fTa5wySTIu',NULL),(5,'Javier','García','','jgarciap@ucm,.es','$2a$10$fIQ5Kj0v6RUHYwvaPpV.bO0QDPrJ601alFv9bJdtig7fTa5wySTIu',NULL),(6,'Mercedes','Moreno-Manzanero','','memoreno@ucm.es','$2a$10$fIQ5Kj0v6RUHYwvaPpV.bO0QDPrJ601alFv9bJdtig7fTa5wySTIu',NULL),(7,'Diana','Jiménez','','dijimene@ucm.es','$2a$10$fIQ5Kj0v6RUHYwvaPpV.bO0QDPrJ601alFv9bJdtig7fTa5wySTIu',NULL),(8,'Aaron','San Frutos','','aaronsanfrutos@ucm.es','$2a$10$fIQ5Kj0v6RUHYwvaPpV.bO0QDPrJ601alFv9bJdtig7fTa5wySTIu',NULL),(9,'Teresa','Sanz','','tsanz01@ucm.es','$2a$10$fIQ5Kj0v6RUHYwvaPpV.bO0QDPrJ601alFv9bJdtig7fTa5wySTIu',NULL);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_rol`
--

DROP TABLE IF EXISTS `usuario_rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario_rol` (
  `usuario_id` int NOT NULL,
  `rol_id` int NOT NULL,
  PRIMARY KEY (`usuario_id`,`rol_id`),
  KEY `fk_usuario_has_rol_rol1_idx` (`rol_id`),
  KEY `fk_usuario_has_rol_usuario_idx` (`usuario_id`),
  CONSTRAINT `fk_usuario_has_rol_rol1` FOREIGN KEY (`rol_id`) REFERENCES `rol` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_usuario_has_rol_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_rol`
--

LOCK TABLES `usuario_rol` WRITE;
/*!40000 ALTER TABLE `usuario_rol` DISABLE KEYS */;
INSERT INTO `usuario_rol` VALUES (1,1),(2,1),(3,1),(4,1),(5,1),(6,1),(7,1),(8,1),(9,1),(1,5);
/*!40000 ALTER TABLE `usuario_rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'covid19'
--

--
-- Dumping routines for database 'covid19'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-03-25 21:37:57
