CREATE DATABASE  IF NOT EXISTS `RRS` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `RRS`;
-- MySQL dump 10.13  Distrib 5.6.24, for osx10.8 (x86_64)
--
-- Host: 127.0.0.1    Database: RRS
-- ------------------------------------------------------
-- Server version	5.6.26

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
-- Table structure for table `Login`
--

DROP TABLE IF EXISTS `Login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Login` (
  `Email` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  `FirstName` varchar(45) NOT NULL,
  `LastName` varchar(45) NOT NULL,
  `ContactNo` bigint(20) NOT NULL,
  `RestaurantId` int(11) NOT NULL,
  PRIMARY KEY (`Email`),
  KEY `FK_restaurantId_Login_idx` (`RestaurantId`),
  CONSTRAINT `FK_restaurantId_Login` FOREIGN KEY (`RestaurantId`) REFERENCES `RestaurantDetails` (`RestaurantId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Login`
--

LOCK TABLES `Login` WRITE;
/*!40000 ALTER TABLE `Login` DISABLE KEYS */;
INSERT INTO `Login` VALUES ('gunter@gmail.com','rachel','Gunter','Galler',4442225656,100);
/*!40000 ALTER TABLE `Login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ReservationDetails`
--

DROP TABLE IF EXISTS `ReservationDetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ReservationDetails` (
  `ConfirmationNo` int(11) NOT NULL AUTO_INCREMENT,
  `FirstName` varchar(45) NOT NULL,
  `LastName` varchar(45) NOT NULL,
  `BookingDate` date NOT NULL,
  `BookingTime` time NOT NULL,
  `PartySize` int(11) NOT NULL,
  `ContactNo` varchar(45) NOT NULL,
  `Status` varchar(10) NOT NULL,
  `Visited` char(1) NOT NULL,
  `AssignedTableNo` int(11) DEFAULT NULL,
  `RestaurantId` int(11) NOT NULL,
  PRIMARY KEY (`ConfirmationNo`),
  KEY `FK_RestaurantId_idx` (`RestaurantId`),
  CONSTRAINT `FK_RestaurantId` FOREIGN KEY (`RestaurantId`) REFERENCES `RestaurantDetails` (`RestaurantId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1005 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ReservationDetails`
--

LOCK TABLES `ReservationDetails` WRITE;
/*!40000 ALTER TABLE `ReservationDetails` DISABLE KEYS */;
INSERT INTO `ReservationDetails` VALUES (1000,'Pawan','Lawale','2015-07-04','17:00:00',4,'4057623052','Confirm','Y',4,100),(1001,'Joe','Tribiyani','2015-05-01','19:00:00',6,'4445556666','Confirm','Y',2,100),(1002,'Pawan','Lawale','2015-09-10','19:00:00',6,'4057623052','Confirm','N',3,100),(1003,'Rachel','Galler','2015-11-06','20:00:00',4,'4445556666','Confirm','N',1,100),(1004,'Ross','Galler','2015-09-07','18:00:00',6,'4057624444','Cancel','N',3,100);
/*!40000 ALTER TABLE `ReservationDetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RestaurantDetails`
--

DROP TABLE IF EXISTS `RestaurantDetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RestaurantDetails` (
  `RestaurantId` int(11) NOT NULL AUTO_INCREMENT,
  `RestaurantName` varchar(100) NOT NULL,
  `ContactNo` bigint(20) NOT NULL,
  `Email` varchar(100) DEFAULT NULL,
  `Address1` varchar(100) NOT NULL,
  `Address2` varchar(100) DEFAULT NULL,
  `OperationalDays` varchar(100) DEFAULT NULL,
  `OpeningTime` time NOT NULL,
  `ClosingTime` time NOT NULL,
  `AutoAssign` char(1) NOT NULL,
  PRIMARY KEY (`RestaurantId`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RestaurantDetails`
--

LOCK TABLES `RestaurantDetails` WRITE;
/*!40000 ALTER TABLE `RestaurantDetails` DISABLE KEYS */;
INSERT INTO `RestaurantDetails` VALUES (100,'Olive Garden New',4057604444,'olive.garden@gmail.com','123rd N Perkins','Stillwater, OK-74075','MON,TUE,WED,THU,FRI,SAT','11:00:00','22:00:00','Y'),(101,'Buffalo Wild Wings',4057604545,'b.wildwings@gmail.com','100rd N Perkins','Stillwater, OK-74075','MON,TUE,WED,THU,FRI,SAT,SUN','11:00:00','24:00:00','Y');
/*!40000 ALTER TABLE `RestaurantDetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RestaurantTables`
--

DROP TABLE IF EXISTS `RestaurantTables`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RestaurantTables` (
  `RestaurantId` int(11) NOT NULL,
  `TableNo` int(11) NOT NULL,
  `NoOfSeats` int(11) NOT NULL,
  PRIMARY KEY (`RestaurantId`,`TableNo`),
  CONSTRAINT `RestaurantId` FOREIGN KEY (`RestaurantId`) REFERENCES `RestaurantDetails` (`RestaurantId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RestaurantTables`
--

LOCK TABLES `RestaurantTables` WRITE;
/*!40000 ALTER TABLE `RestaurantTables` DISABLE KEYS */;
INSERT INTO `RestaurantTables` VALUES (100,1,4),(100,2,2),(100,3,6),(100,4,5),(100,5,4),(100,6,2),(100,7,8),(100,8,10),(100,9,4),(100,10,3);
/*!40000 ALTER TABLE `RestaurantTables` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-09-08 10:18:21
