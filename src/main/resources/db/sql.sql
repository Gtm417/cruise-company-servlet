
CREATE DATABASE  IF NOT EXISTS `cruise_company_servlet` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `cruise_company_servlet`;

-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: localhost    Database: cruise_company_servlet
-- ------------------------------------------------------
-- Server version	8.0.18

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
-- Table structure for table `cruises`
--

DROP TABLE IF EXISTS `cruises`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cruises` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cruise_name` varchar(50) NOT NULL,
  `arrival_date` date NOT NULL,
  `departure_date` date NOT NULL,
  `ship_id` bigint(20) NOT NULL,
  `description_eng` tinytext NOT NULL,
  `description_ru` tinytext NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `cruise_name_UNIQUE` (`cruise_name`),
  KEY `cruise_ship_FK_idx` (`ship_id`),
  CONSTRAINT `cruise_ship_FK` FOREIGN KEY (`ship_id`) REFERENCES `ships` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cruises`
--

LOCK TABLES `cruises` WRITE;
/*!40000 ALTER TABLE `cruises` DISABLE KEYS */;
INSERT INTO `cruises` VALUES (1,'Costa Cruise','2019-12-16','2019-12-16',1,'First','Первый'),(2,'name1','2020-01-17','2020-01-09',2,'Normal cruise','Нормальный круиз'),(3,'Titanic','2020-01-24','2020-01-18',3,'Not good enough cruise','Не очень хороший круиз');
/*!40000 ALTER TABLE `cruises` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `excursions`
--

DROP TABLE IF EXISTS `excursions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `excursions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `duration` int(11) NOT NULL,
  `excursion_name` varchar(55) NOT NULL,
  `port_id` bigint(20) NOT NULL,
  `price` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `excursion_name_UNIQUE` (`excursion_name`),
  KEY `port_excursion_fk_idx` (`port_id`),
  CONSTRAINT `port_excursion_fk` FOREIGN KEY (`port_id`) REFERENCES `ports` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `excursions`
--

LOCK TABLES `excursions` WRITE;
/*!40000 ALTER TABLE `excursions` DISABLE KEYS */;
INSERT INTO `excursions` VALUES (1,1,'name1',1,100),(2,2,'name2',1,100),(3,3,'name3',1,100),(4,4,'name4',1,100),(5,3,'name5',2,100),(6,2,'name16',3,100),(7,1,'name7',2,100),(8,1,'name8',2,100),(9,3,'name9',2,100),(10,2,'name20',1,100),(11,6,'name12',3,100);
/*!40000 ALTER TABLE `excursions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_excursions`
--

DROP TABLE IF EXISTS `order_excursions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_excursions` (
  `order_id` bigint(20) NOT NULL,
  `excursion_id` bigint(20) NOT NULL,
  KEY `excursion_order_fk_idx` (`excursion_id`,`order_id`),
  KEY `order_excursion_fk_idx` (`order_id`),
  CONSTRAINT `excursion_order_fk` FOREIGN KEY (`excursion_id`) REFERENCES `excursions` (`id`),
  CONSTRAINT `order_excursion_fk` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_excursions`
--

LOCK TABLES `order_excursions` WRITE;
/*!40000 ALTER TABLE `order_excursions` DISABLE KEYS */;
INSERT INTO `order_excursions` VALUES (214,5),(216,5),(216,5),(216,5),(216,5),(215,7),(215,8),(215,8),(216,8),(216,8),(214,9),(215,9),(214,11),(216,11);
/*!40000 ALTER TABLE `order_excursions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cruise_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `ticket_id` bigint(20) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `second_name` varchar(50) NOT NULL,
  `price` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_order_FK_idx` (`user_id`),
  KEY `cruise_order_fk_idx` (`cruise_id`),
  KEY `ticekts_order_fk_idx` (`ticket_id`),
  CONSTRAINT `cruise_order_fk` FOREIGN KEY (`cruise_id`) REFERENCES `cruises` (`id`),
  CONSTRAINT `ticekts_order_fk` FOREIGN KEY (`ticket_id`) REFERENCES `tickets` (`id`),
  CONSTRAINT `user_order_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=217 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (194,1,2,1,'Tymofii','dadada',0),(195,1,2,1,'dasdad','dsadasda',0),(196,1,2,1,'tw','ete',0),(198,1,2,1,'Test55','Test55',0),(199,1,1,1,'Test55','Test55',0),(200,1,2,1,'dasda','dada',0),(203,1,2,1,'alex','Alex',0),(204,1,2,1,'Tima','Tima',1200),(205,1,12,1,'Tymofii','Hodik',1200),(206,1,12,1,'dasda','dasdasd',1300),(214,1,12,1,'Tymofii','Hodik',1200),(215,1,12,1,'Tymofii','Hodik',1300),(216,1,12,1,'Tymofii','Hodik',1600);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ports`
--

DROP TABLE IF EXISTS `ports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ports` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `port_name` varchar(55) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `port_name_UNIQUE` (`port_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ports`
--

LOCK TABLES `ports` WRITE;
/*!40000 ALTER TABLE `ports` DISABLE KEYS */;
INSERT INTO `ports` VALUES (1,'America'),(3,'India'),(2,'Odessa');
/*!40000 ALTER TABLE `ports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ports_cruises`
--

DROP TABLE IF EXISTS `ports_cruises`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ports_cruises` (
  `cruise_id` bigint(20) NOT NULL,
  `port_id` bigint(20) NOT NULL,
  KEY `port_cruise_fk_idx` (`cruise_id`),
  KEY `cruise_port_fk_idx` (`port_id`),
  CONSTRAINT `cruise_port_fk` FOREIGN KEY (`port_id`) REFERENCES `ports` (`id`),
  CONSTRAINT `port_cruise_fk` FOREIGN KEY (`cruise_id`) REFERENCES `cruises` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ports_cruises`
--

LOCK TABLES `ports_cruises` WRITE;
/*!40000 ALTER TABLE `ports_cruises` DISABLE KEYS */;
INSERT INTO `ports_cruises` VALUES (1,2),(1,3);
/*!40000 ALTER TABLE `ports_cruises` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ships`
--

DROP TABLE IF EXISTS `ships`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ships` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `current_amount_of_passenger` int(11) NOT NULL,
  `max_amount_of_passenger` int(11) NOT NULL,
  `ship_name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ship_name_UNIQUE` (`ship_name`),
  CONSTRAINT `check_passengers` CHECK ((`current_amount_of_passenger` <= `max_amount_of_passenger`))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ships`
--

LOCK TABLES `ships` WRITE;
/*!40000 ALTER TABLE `ships` DISABLE KEYS */;
INSERT INTO `ships` VALUES (1,83,100,'Costa'),(2,0,5000,'Titanic'),(3,0,100000,'test');
/*!40000 ALTER TABLE `ships` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tickets`
--

DROP TABLE IF EXISTS `tickets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tickets` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ticket_name` varchar(45) NOT NULL,
  `discount` int(11) NOT NULL,
  `price` bigint(20) NOT NULL,
  `discount_price` bigint(20) NOT NULL,
  `cruise_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_cruise` (`ticket_name`,`cruise_id`),
  KEY `cruise_ticket_fk_idx` (`cruise_id`),
  CONSTRAINT `cruise_tickets_fk` FOREIGN KEY (`cruise_id`) REFERENCES `cruises` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tickets`
--

LOCK TABLES `tickets` WRITE;
/*!40000 ALTER TABLE `tickets` DISABLE KEYS */;
INSERT INTO `tickets` VALUES (1,'VIP',10,1000,900,1),(2,'Casual',10,500,450,1),(3,'Low ',10,150,135,1),(18,'TEST',10,100,90,1),(20,'TEST',10,100,90,2);
/*!40000 ALTER TABLE `tickets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `balance` bigint(20) NOT NULL DEFAULT '0',
  `role` enum('USER','ADMIN') NOT NULL DEFAULT 'USER',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idusers_UNIQUE` (`id`),
  UNIQUE KEY `login_UNIQUE` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Tima','Tima',50,'USER'),(2,'t','t',43410,'ADMIN'),(12,'Admin','e3afed0047b08059d0fada10f400c1e5',43500,'ADMIN'),(14,'User','8f9bfe9d1345237cb3b2b205864da075',0,'USER'),(15,'Admin1','e3afed0047b08059d0fada10f400c1e5',0,'ADMIN'),(16,'Test27','a969756bfdec9ed5479fc41e6fbf4e53',0,'USER'),(17,'Tima123','3f474a79328cfb1a62f89abb88806fe2',0,'USER');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-02-11 11:26:44
