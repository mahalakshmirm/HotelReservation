-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: hotel_db
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Table structure for table `reservations`
--

DROP TABLE IF EXISTS `reservations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservations` (
  `r_id` int NOT NULL AUTO_INCREMENT,
  `guest_name` varchar(45) NOT NULL,
  `guest_contact` varchar(45) NOT NULL,
  `room_number` int NOT NULL,
  `r_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`r_id`),
  UNIQUE KEY `r_id_UNIQUE` (`r_id`),
  KEY `fk_room_number_idx` (`room_number`),
  CONSTRAINT `fk_room_number` FOREIGN KEY (`room_number`) REFERENCES `rooms` (`room_number`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservations`
--

LOCK TABLES `reservations` WRITE;
/*!40000 ALTER TABLE `reservations` DISABLE KEYS */;
INSERT INTO `reservations` VALUES (1,'Ram N','9789345612',101,'2025-03-10 08:05:02'),(2,'Pugal A','9686453742',106,'2025-03-10 08:07:04'),(3,'Deeksha D','6578435621',111,'2025-03-10 08:07:39'),(4,'Moni S','9087654534',102,'2025-03-10 08:08:13'),(5,'Aaradhya','9875647890',112,'2025-03-10 08:09:00'),(6,'Zara T','8976564532',107,'2025-03-10 08:09:33');
/*!40000 ALTER TABLE `reservations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rooms`
--

DROP TABLE IF EXISTS `rooms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rooms` (
  `room_number` int NOT NULL,
  `room_type` varchar(45) NOT NULL,
  `room_status` varchar(45) DEFAULT 'Available',
  PRIMARY KEY (`room_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rooms`
--

LOCK TABLES `rooms` WRITE;
/*!40000 ALTER TABLE `rooms` DISABLE KEYS */;
INSERT INTO `rooms` VALUES (101,'Single','Reserved'),(102,'Single','Reserved'),(103,'Single','Available'),(104,'Single','Available'),(105,'Single','Available'),(106,'Double','Reserved'),(107,'Double','Reserved'),(108,'Double','Available'),(109,'Double','Available'),(110,'Double','Available'),(111,'Deluxe','Reserved'),(112,'Deluxe','Reserved'),(113,'Deluxe','Available'),(114,'Deluxe','Available'),(115,'Deluxe','Available'),(116,'Deluxe','Available'),(117,'Deluxe','Available'),(118,'Deluxe','Available'),(119,'Deluxe','Available'),(120,'Deluxe','Available');
/*!40000 ALTER TABLE `rooms` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-10 15:04:29
