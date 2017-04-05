-- MySQL dump 10.13  Distrib 5.7.17, for Linux (x86_64)
--
-- Host: localhost    Database: smarterTransferDB
-- ------------------------------------------------------
-- Server version	5.7.17

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
-- Table structure for table `ADDRESS`
--

DROP TABLE IF EXISTS `ADDRESS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ADDRESS` (
  `addressId` bigint(20) NOT NULL AUTO_INCREMENT,
  `country` varchar(45) DEFAULT NULL,
  `state` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `zip` varchar(45) DEFAULT NULL,
  `addressLine1` varchar(45) DEFAULT NULL,
  `addressLine2` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`addressId`),
  UNIQUE KEY `addressId_UNIQUE` (`addressId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ADDRESS`
--

LOCK TABLES `ADDRESS` WRITE;
/*!40000 ALTER TABLE `ADDRESS` DISABLE KEYS */;
/*!40000 ALTER TABLE `ADDRESS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CONTACT`
--

DROP TABLE IF EXISTS `CONTACT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CONTACT` (
  `merchantId` bigint(20) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `forename` varchar(45) DEFAULT NULL,
  `surname` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`merchantId`),
  UNIQUE KEY `merchantId_UNIQUE` (`merchantId`),
  KEY `fk_Contact_Merchant_idx` (`merchantId`),
  CONSTRAINT `fk_Contact_Merchant` FOREIGN KEY (`merchantId`) REFERENCES `MERCHANT` (`merchantId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CONTACT`
--

LOCK TABLES `CONTACT` WRITE;
/*!40000 ALTER TABLE `CONTACT` DISABLE KEYS */;
/*!40000 ALTER TABLE `CONTACT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ITEM`
--

DROP TABLE IF EXISTS `ITEM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ITEM` (
  `itemId` bigint(20) NOT NULL AUTO_INCREMENT,
  `merchantId` bigint(20) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `created_at` timestamp(6) NULL DEFAULT NULL,
  `updated_at` timestamp(6) NULL DEFAULT NULL,
  PRIMARY KEY (`itemId`,`merchantId`),
  KEY `fk_Item_1_idx` (`merchantId`),
  CONSTRAINT `fk_Item_1` FOREIGN KEY (`merchantId`) REFERENCES `MERCHANT` (`merchantId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ITEM`
--

LOCK TABLES `ITEM` WRITE;
/*!40000 ALTER TABLE `ITEM` DISABLE KEYS */;
/*!40000 ALTER TABLE `ITEM` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ITEM_HISTORY`
--

DROP TABLE IF EXISTS `ITEM_HISTORY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ITEM_HISTORY` (
  `revisionNumber` bigint(20) NOT NULL,
  `itemId` bigint(20) NOT NULL,
  `merchantId` bigint(20) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `created_at` timestamp(6) NULL DEFAULT NULL,
  `updated_at` timestamp(6) NULL DEFAULT NULL,
  PRIMARY KEY (`revisionNumber`,`itemId`,`merchantId`),
  KEY `fk_Item_History_1_idx` (`merchantId`),
  CONSTRAINT `fk_Item_History_1` FOREIGN KEY (`merchantId`) REFERENCES `MERCHANT` (`merchantId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ITEM_HISTORY`
--

LOCK TABLES `ITEM_HISTORY` WRITE;
/*!40000 ALTER TABLE `ITEM_HISTORY` DISABLE KEYS */;
/*!40000 ALTER TABLE `ITEM_HISTORY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MENU`
--

DROP TABLE IF EXISTS `MENU`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MENU` (
  `menuId` bigint(20) NOT NULL AUTO_INCREMENT,
  `merchantId` bigint(20) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`menuId`,`merchantId`),
  KEY `fk_MENU_1_idx` (`merchantId`),
  CONSTRAINT `fk_MENU_1` FOREIGN KEY (`merchantId`) REFERENCES `MERCHANT` (`merchantId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MENU`
--

LOCK TABLES `MENU` WRITE;
/*!40000 ALTER TABLE `MENU` DISABLE KEYS */;
/*!40000 ALTER TABLE `MENU` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MENU_ITEM`
--

DROP TABLE IF EXISTS `MENU_ITEM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MENU_ITEM` (
  `menuId` bigint(20) NOT NULL,
  `itemId` bigint(20) NOT NULL,
  PRIMARY KEY (`menuId`),
  KEY `fk_MENU_ITEM_3_idx` (`itemId`),
  CONSTRAINT `fk_MENU_ITEM_1` FOREIGN KEY (`menuId`) REFERENCES `MENU` (`menuId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_MENU_ITEM_2` FOREIGN KEY (`itemId`) REFERENCES `ITEM` (`itemId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MENU_ITEM`
--

LOCK TABLES `MENU_ITEM` WRITE;
/*!40000 ALTER TABLE `MENU_ITEM` DISABLE KEYS */;
/*!40000 ALTER TABLE `MENU_ITEM` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MERCHANT`
--

DROP TABLE IF EXISTS `MERCHANT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MERCHANT` (
  `merchantId` bigint(20) NOT NULL AUTO_INCREMENT,
  `keshID` bigint(20) NOT NULL,
  `companyName` varchar(128) DEFAULT NULL,
  `phoneNumber` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `addressId` bigint(20) DEFAULT NULL,
  `ustId` varchar(45) DEFAULT NULL,
  `logoId` bigint(20) DEFAULT NULL,
  `websiteURL` varchar(45) DEFAULT NULL,
  `shopURL` varchar(45) DEFAULT NULL,
  `ticketURL` varchar(45) DEFAULT NULL,
  `isDeleted` bit(1) NOT NULL DEFAULT b'0',
  `created_at` timestamp(6) NULL DEFAULT NULL,
  `updated_at` timestamp(6) NULL DEFAULT NULL,
  PRIMARY KEY (`merchantId`),
  UNIQUE KEY `merchantId_UNIQUE` (`merchantId`),
  UNIQUE KEY `keshID_UNIQUE` (`keshID`),
  KEY `fk_Merchant_Address_idx` (`addressId`),
  CONSTRAINT `fk_Merchant_Address` FOREIGN KEY (`addressId`) REFERENCES `ADDRESS` (`addressId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MERCHANT`
--

LOCK TABLES `MERCHANT` WRITE;
/*!40000 ALTER TABLE `MERCHANT` DISABLE KEYS */;
/*!40000 ALTER TABLE `MERCHANT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `POS`
--

DROP TABLE IF EXISTS `POS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `POS` (
  `posId` bigint(20) NOT NULL AUTO_INCREMENT,
  `merchantId` bigint(20) NOT NULL,
  `menuId` bigint(20) DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `isDeleted` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`posId`,`merchantId`),
  KEY `fk_POS_1_idx` (`merchantId`,`menuId`),
  CONSTRAINT `fk_POS_1` FOREIGN KEY (`merchantId`, `menuId`) REFERENCES `MENU` (`merchantId`, `menuId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_POS_2` FOREIGN KEY (`merchantId`) REFERENCES `MERCHANT` (`merchantId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `POS`
--

LOCK TABLES `POS` WRITE;
/*!40000 ALTER TABLE `POS` DISABLE KEYS */;
/*!40000 ALTER TABLE `POS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `THEME`
--

DROP TABLE IF EXISTS `THEME`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `THEME` (
  `themeId` bigint(20) NOT NULL AUTO_INCREMENT,
  `merchantId` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`themeId`),
  UNIQUE KEY `themeId_UNIQUE` (`themeId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `THEME`
--

LOCK TABLES `THEME` WRITE;
/*!40000 ALTER TABLE `THEME` DISABLE KEYS */;
INSERT INTO `THEME` VALUES (1,NULL);
/*!40000 ALTER TABLE `THEME` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER`
--

DROP TABLE IF EXISTS `USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER` (
  `userId` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `deviceId` varchar(45) DEFAULT NULL,
  `themeId` bigint(20) NOT NULL DEFAULT '1',
  `longitude` double DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `password` varchar(45) NOT NULL,
  `keshId` varchar(200) NOT NULL,
  `isDeleted` bit(1) NOT NULL DEFAULT b'0',
  `created_at` timestamp(6) NULL DEFAULT NULL,
  `updated_at` timestamp(6) NULL DEFAULT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `keshId_UNIQUE` (`userId`),
  UNIQUE KEY `userId_UNIQUE` (`keshId`),
  KEY `FK285FEB3E23B8F8` (`themeId`),
  CONSTRAINT `user_themeId` FOREIGN KEY (`themeId`) REFERENCES `THEME` (`themeId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER`
--

LOCK TABLES `USER` WRITE;
/*!40000 ALTER TABLE `USER` DISABLE KEYS */;
INSERT INTO `USER` VALUES (1,'',NULL,1,NULL,NULL,'pw','test','',NULL,NULL);
/*!40000 ALTER TABLE `USER` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-04-02 18:47:45
