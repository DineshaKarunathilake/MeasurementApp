-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: measurement_app
-- ------------------------------------------------------
-- Server version	5.7.14

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
-- Table structure for table `batch`
--

DROP TABLE IF EXISTS `batch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `batch` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `style_id` int(11) NOT NULL,
  `batch_number` int(11) NOT NULL,
  `current_stage_id` int(11) NOT NULL,
  `total_count` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `style_id` (`style_id`),
  KEY `current_stage_id` (`current_stage_id`),
  CONSTRAINT `batch_ibfk_1` FOREIGN KEY (`style_id`) REFERENCES `style` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `batch_ibfk_2` FOREIGN KEY (`current_stage_id`) REFERENCES `stage` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `batch`
--

LOCK TABLES `batch` WRITE;
/*!40000 ALTER TABLE `batch` DISABLE KEYS */;
INSERT INTO `batch` VALUES (1,1,12341,1,12),(2,1,12342,1,13);
/*!40000 ALTER TABLE `batch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `garment_entry`
--

DROP TABLE IF EXISTS `garment_entry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `garment_entry` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `batch_id` int(11) NOT NULL,
  `size_id` int(11) NOT NULL,
  `stage_id` int(11) NOT NULL,
  `chest_width` decimal(10,4) NOT NULL,
  `hem_width` decimal(10,4) NOT NULL,
  `cb_length` decimal(10,4) NOT NULL,
  `cf_length` decimal(10,4) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `batch_id` (`batch_id`),
  KEY `size_id` (`size_id`),
  KEY `stage_id` (`stage_id`),
  CONSTRAINT `garment_entry_ibfk_1` FOREIGN KEY (`batch_id`) REFERENCES `batch` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `garment_entry_ibfk_2` FOREIGN KEY (`stage_id`) REFERENCES `stage` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `garment_entry_ibfk_3` FOREIGN KEY (`size_id`) REFERENCES `size` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `garment_entry`
--

LOCK TABLES `garment_entry` WRITE;
/*!40000 ALTER TABLE `garment_entry` DISABLE KEYS */;
INSERT INTO `garment_entry` VALUES (1,1,1,1,12.5000,14.0000,17.0000,22.0000),(2,1,1,1,12.4000,14.7000,16.9000,22.1000),(3,1,1,1,12.6000,14.0000,17.1000,22.3000),(4,1,1,1,12.7000,14.9000,17.2000,22.4000),(5,1,1,1,12.8000,14.8000,17.1000,22.1000),(6,1,1,2,11.0000,13.4000,16.4000,20.5000),(7,1,1,2,11.2000,13.5000,16.3000,20.1000),(8,1,1,2,11.3000,13.6000,16.1000,20.4000),(9,1,1,2,11.2000,13.0000,16.2000,20.3000),(10,1,1,2,11.7000,13.8000,16.8000,20.4000),(11,1,1,3,10.5000,12.0000,16.0000,19.5000),(12,1,1,3,10.4000,12.4000,16.1000,19.2000),(13,1,1,3,10.5000,12.3000,16.2000,19.6000),(14,1,1,3,10.6000,12.6000,16.3000,19.7000),(15,1,1,3,10.2000,12.7000,15.9000,19.6000),(16,2,1,1,12.0000,14.0000,25.0000,20.0000),(17,2,1,1,14.0000,14.2000,24.0000,20.3000),(18,2,1,1,15.0000,14.5000,24.5000,20.5000),(19,2,1,1,15.1000,14.6000,22.3000,20.7000),(20,2,1,1,15.3000,14.7000,23.6000,20.8000);
/*!40000 ALTER TABLE `garment_entry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `size`
--

DROP TABLE IF EXISTS `size`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `size` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `size`
--

LOCK TABLES `size` WRITE;
/*!40000 ALTER TABLE `size` DISABLE KEYS */;
INSERT INTO `size` VALUES (1,'XS'),(2,'S'),(3,'M'),(4,'L'),(5,'XL'),(6,'XXL');
/*!40000 ALTER TABLE `size` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sleeve_entry`
--

DROP TABLE IF EXISTS `sleeve_entry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sleeve_entry` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `batch_id` int(11) NOT NULL,
  `size_id` int(11) NOT NULL,
  `stage_id` int(11) NOT NULL,
  `opening` decimal(5,4) NOT NULL,
  `length` decimal(5,4) NOT NULL,
  `width` decimal(5,4) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `batch_id` (`batch_id`),
  KEY `size_id` (`size_id`),
  KEY `stage_id` (`stage_id`),
  CONSTRAINT `sleeve_entry_ibfk_1` FOREIGN KEY (`stage_id`) REFERENCES `stage` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `sleeve_entry_ibfk_2` FOREIGN KEY (`size_id`) REFERENCES `size` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `sleeve_entry_ibfk_3` FOREIGN KEY (`batch_id`) REFERENCES `batch` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sleeve_entry`
--

LOCK TABLES `sleeve_entry` WRITE;
/*!40000 ALTER TABLE `sleeve_entry` DISABLE KEYS */;
/*!40000 ALTER TABLE `sleeve_entry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stage`
--

DROP TABLE IF EXISTS `stage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stage`
--

LOCK TABLES `stage` WRITE;
/*!40000 ALTER TABLE `stage` DISABLE KEYS */;
INSERT INTO `stage` VALUES (1,'before presetting'),(2,'after presetting'),(3,'after dyeing');
/*!40000 ALTER TABLE `stage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `style`
--

DROP TABLE IF EXISTS `style`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `style` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `customer` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `style`
--

LOCK TABLES `style` WRITE;
/*!40000 ALTER TABLE `style` DISABLE KEYS */;
INSERT INTO `style` VALUES (1,'ab111','nike'),(2,'ab112','lulu');
/*!40000 ALTER TABLE `style` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-10-17 10:58:21
