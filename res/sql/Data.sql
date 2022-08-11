-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: eams
-- ------------------------------------------------------
-- Server version	8.0.30

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
-- Table structure for table `azienda`
--

DROP TABLE IF EXISTS `azienda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `azienda` (
  `partitaIVA` decimal(11,0) NOT NULL,
  `denominazioneSociale` varchar(45) NOT NULL,
  `telefono` varchar(24) NOT NULL,
  `indirizzo` varchar(60) NOT NULL,
  `città` varchar(15) NOT NULL,
  `regione` varchar(20) NOT NULL,
  `codicePostale` varchar(10) NOT NULL,
  `mail` varchar(20) NOT NULL,
  `fax` varchar(24) DEFAULT NULL,
  PRIMARY KEY (`partitaIVA`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `azienda`
--

LOCK TABLES `azienda` WRITE;
/*!40000 ALTER TABLE `azienda` DISABLE KEYS */;
INSERT INTO `azienda` VALUES (12345678911,'az1','415452','via Giuseppe','Ancona','Marche','320256','mail1@nicemail.it','fax1'),(25369874102,'az2','743873','via Garibaldi','Ferrara','Emilia-Romagna','45678','mail2@nicemail.it',NULL);
/*!40000 ALTER TABLE `azienda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoria` (
  `nome` varchar(30) NOT NULL,
  `descrizione` mediumtext,
  PRIMARY KEY (`nome`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` VALUES ('a','mail1@nicemail.it'),('b',NULL);
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `consegna`
--

DROP TABLE IF EXISTS `consegna`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `consegna` (
  `materiale` varchar(30) NOT NULL,
  `partitaIVA` decimal(11,0) NOT NULL,
  `data` date NOT NULL,
  `kgConsegnati` decimal(11,2) NOT NULL,
  PRIMARY KEY (`materiale`,`partitaIVA`,`data`),
  KEY `partitaIVA` (`partitaIVA`),
  CONSTRAINT `consegna_ibfk_1` FOREIGN KEY (`partitaIVA`) REFERENCES `azienda` (`partitaIVA`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `consegna_ibfk_2` FOREIGN KEY (`materiale`) REFERENCES `rifiuto` (`materiale`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `consegna`
--

LOCK TABLES `consegna` WRITE;
/*!40000 ALTER TABLE `consegna` DISABLE KEYS */;
INSERT INTO `consegna` VALUES ('carta',12345678911,'2021-12-11',420.50),('plastica',25369874102,'2022-12-11',200.00);
/*!40000 ALTER TABLE `consegna` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dipendente`
--

DROP TABLE IF EXISTS `dipendente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dipendente` (
  `codiceFiscale` char(16) NOT NULL,
  `sedeCittà` varchar(25) NOT NULL,
  `dataAssunzione` date NOT NULL,
  `salario` decimal(8,2) NOT NULL,
  PRIMARY KEY (`codiceFiscale`),
  KEY `sedeCittà` (`sedeCittà`),
  CONSTRAINT `dipendente_ibfk_1` FOREIGN KEY (`codiceFiscale`) REFERENCES `persona` (`codiceFiscale`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `dipendente_ibfk_2` FOREIGN KEY (`sedeCittà`) REFERENCES `sede` (`città`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dipendente`
--

LOCK TABLES `dipendente` WRITE;
/*!40000 ALTER TABLE `dipendente` DISABLE KEYS */;
INSERT INTO `dipendente` VALUES ('AAAAAAAAAAAAAAAA','Roma','2022-12-11',1000.00),('BBBBBBBBBBBBBBBB','Bari','2021-12-11',2000.00);
/*!40000 ALTER TABLE `dipendente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `donazione`
--

DROP TABLE IF EXISTS `donazione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donazione` (
  `idProgetto` int NOT NULL AUTO_INCREMENT,
  `obbiettivo` varchar(45) NOT NULL,
  `dataInizio` date NOT NULL,
  `durataMesi` int NOT NULL,
  PRIMARY KEY (`idProgetto`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donazione`
--

LOCK TABLES `donazione` WRITE;
/*!40000 ALTER TABLE `donazione` DISABLE KEYS */;
INSERT INTO `donazione` VALUES (1,'m','2022-10-11',4),(2,'a','2021-12-11',5);
/*!40000 ALTER TABLE `donazione` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evento`
--

DROP TABLE IF EXISTS `evento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `evento` (
  `idEvento` char(20) NOT NULL,
  `nome` varchar(30) NOT NULL,
  `data` date NOT NULL,
  `descrizione` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`idEvento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evento`
--

LOCK TABLES `evento` WRITE;
/*!40000 ALTER TABLE `evento` DISABLE KEYS */;
INSERT INTO `evento` VALUES ('1','a','2022-10-11','molto bello'),('2','bo','2021-12-11',NULL);
/*!40000 ALTER TABLE `evento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fornitura`
--

DROP TABLE IF EXISTS `fornitura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fornitura` (
  `idProdotto` int NOT NULL,
  `partitaIVA` decimal(11,0) NOT NULL,
  `data` date NOT NULL,
  `quantitàFornita` int NOT NULL,
  PRIMARY KEY (`idProdotto`,`partitaIVA`,`data`),
  KEY `fk_prodotto_has_azienda_azienda1_idx` (`partitaIVA`),
  KEY `fk_prodotto_has_azienda_prodotto1_idx` (`idProdotto`),
  CONSTRAINT `fk_prodotto_has_azienda_azienda1` FOREIGN KEY (`partitaIVA`) REFERENCES `azienda` (`partitaIVA`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_prodotto_has_azienda_prodotto1` FOREIGN KEY (`idProdotto`) REFERENCES `prodotto` (`idProdotto`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fornitura`
--

LOCK TABLES `fornitura` WRITE;
/*!40000 ALTER TABLE `fornitura` DISABLE KEYS */;
/*!40000 ALTER TABLE `fornitura` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `iscrizione`
--

DROP TABLE IF EXISTS `iscrizione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `iscrizione` (
  `codiceFiscale` char(16) NOT NULL,
  `idNewsletter` int NOT NULL,
  PRIMARY KEY (`codiceFiscale`,`idNewsletter`),
  KEY `fk_persona_has_newsletter_newsletter1_idx` (`idNewsletter`),
  KEY `fk_persona_has_newsletter_persona1_idx` (`codiceFiscale`),
  CONSTRAINT `fk_persona_has_newsletter_newsletter1` FOREIGN KEY (`idNewsletter`) REFERENCES `newsletter` (`idNewsletter`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_persona_has_newsletter_persona1` FOREIGN KEY (`codiceFiscale`) REFERENCES `persona` (`codiceFiscale`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `iscrizione`
--

LOCK TABLES `iscrizione` WRITE;
/*!40000 ALTER TABLE `iscrizione` DISABLE KEYS */;
/*!40000 ALTER TABLE `iscrizione` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `newsletter`
--

DROP TABLE IF EXISTS `newsletter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `newsletter` (
  `idNewsletter` int NOT NULL,
  `argomento` varchar(45) NOT NULL,
  `descrizione` mediumtext,
  PRIMARY KEY (`idNewsletter`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `newsletter`
--

LOCK TABLES `newsletter` WRITE;
/*!40000 ALTER TABLE `newsletter` DISABLE KEYS */;
/*!40000 ALTER TABLE `newsletter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organizzazione`
--

DROP TABLE IF EXISTS `organizzazione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `organizzazione` (
  `codiceFiscaleDipendente` char(16) NOT NULL,
  `idEvento` char(20) NOT NULL,
  PRIMARY KEY (`codiceFiscaleDipendente`,`idEvento`),
  KEY `idEvento` (`idEvento`),
  CONSTRAINT `organizzazione_ibfk_1` FOREIGN KEY (`codiceFiscaleDipendente`) REFERENCES `dipendente` (`codiceFiscale`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `organizzazione_ibfk_2` FOREIGN KEY (`idEvento`) REFERENCES `evento` (`idEvento`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organizzazione`
--

LOCK TABLES `organizzazione` WRITE;
/*!40000 ALTER TABLE `organizzazione` DISABLE KEYS */;
INSERT INTO `organizzazione` VALUES ('AAAAAAAAAAAAAAAA','1'),('BBBBBBBBBBBBBBBB','2');
/*!40000 ALTER TABLE `organizzazione` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `partecipazione`
--

DROP TABLE IF EXISTS `partecipazione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `partecipazione` (
  `codiceFiscaleVolontario` char(16) NOT NULL,
  `idServizio` char(20) NOT NULL,
  `idEvento` char(20) NOT NULL,
  PRIMARY KEY (`codiceFiscaleVolontario`,`idServizio`,`idEvento`),
  KEY `fk_partecipazione_volontario1_idx` (`codiceFiscaleVolontario`),
  KEY `fk_partecipazione_servizio1_idx` (`idEvento`,`idServizio`),
  CONSTRAINT `fk_partecipazione_servizio1` FOREIGN KEY (`idEvento`, `idServizio`) REFERENCES `servizio` (`idEvento`, `idServizio`),
  CONSTRAINT `fk_partecipazione_volontario1` FOREIGN KEY (`codiceFiscaleVolontario`) REFERENCES `volontario` (`codiceFiscale`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `partecipazione`
--

LOCK TABLES `partecipazione` WRITE;
/*!40000 ALTER TABLE `partecipazione` DISABLE KEYS */;
/*!40000 ALTER TABLE `partecipazione` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persona`
--

DROP TABLE IF EXISTS `persona`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `persona` (
  `codiceFiscale` char(16) NOT NULL,
  `nome` varchar(25) DEFAULT NULL,
  `cognome` varchar(25) DEFAULT NULL,
  `mail` varchar(45) DEFAULT NULL,
  `telefono` varchar(24) DEFAULT NULL,
  `indirizzo` varchar(60) DEFAULT NULL,
  `città` varchar(15) DEFAULT NULL,
  `regione` varchar(20) DEFAULT NULL,
  `codicePostale` varchar(10) DEFAULT NULL,
  `tipo` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`codiceFiscale`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persona`
--

LOCK TABLES `persona` WRITE;
/*!40000 ALTER TABLE `persona` DISABLE KEYS */;
INSERT INTO `persona` VALUES ('AAAAAAAAAAAAAAAA','Giacomo','Cavalieri','mail1@nicemail.it','33123446756678','via Giuseppe','Ancona','Marche','320256','Interna'),('BBBBBBBBBBBBBBBB','Tommaso','Cavalieri','mail2@nicemail.it','33563674789056','via Garibaldi','Ferrara','Emilia-Romagna','45678',NULL);
/*!40000 ALTER TABLE `persona` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prodotto`
--

DROP TABLE IF EXISTS `prodotto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prodotto` (
  `idProdotto` int NOT NULL AUTO_INCREMENT,
  `categoria` varchar(30) NOT NULL,
  `nome` varchar(30) NOT NULL,
  `prezzo` decimal(6,2) NOT NULL,
  `quantitàImmagazzinata` int NOT NULL,
  `provenienza` varchar(15) NOT NULL,
  `descrizione` mediumtext,
  PRIMARY KEY (`idProdotto`),
  KEY `fk_prodotto_categoria1_idx` (`categoria`),
  CONSTRAINT `fk_prodotto_categoria1` FOREIGN KEY (`categoria`) REFERENCES `categoria` (`nome`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prodotto`
--

LOCK TABLES `prodotto` WRITE;
/*!40000 ALTER TABLE `prodotto` DISABLE KEYS */;
/*!40000 ALTER TABLE `prodotto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produzione`
--

DROP TABLE IF EXISTS `produzione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `produzione` (
  `idProdotto` int NOT NULL,
  `idServizio` char(20) NOT NULL,
  `idEvento` char(20) NOT NULL,
  `quantitàProdotta` int NOT NULL,
  `materialeUsato` varchar(30) NOT NULL,
  `kgRifiutiUsati` decimal(8,2) NOT NULL,
  PRIMARY KEY (`idProdotto`,`idServizio`,`idEvento`),
  KEY `fk_servizio_has_rifiuto_rifiuto2_idx` (`materialeUsato`),
  KEY `fk_produzione_prodotto1_idx` (`idProdotto`),
  KEY `fk_produzione_servizio1_idx` (`idEvento`,`idServizio`),
  CONSTRAINT `fk_produzione_prodotto1` FOREIGN KEY (`idProdotto`) REFERENCES `prodotto` (`idProdotto`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_produzione_servizio1` FOREIGN KEY (`idEvento`, `idServizio`) REFERENCES `servizio` (`idEvento`, `idServizio`),
  CONSTRAINT `fk_servizio_has_rifiuto_rifiuto2` FOREIGN KEY (`materialeUsato`) REFERENCES `rifiuto` (`materiale`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produzione`
--

LOCK TABLES `produzione` WRITE;
/*!40000 ALTER TABLE `produzione` DISABLE KEYS */;
/*!40000 ALTER TABLE `produzione` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `progetto`
--

DROP TABLE IF EXISTS `progetto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `progetto` (
  `idProgetto` int NOT NULL AUTO_INCREMENT,
  `obbiettivo` varchar(45) NOT NULL,
  `dataInizio` date NOT NULL,
  `durataMesi` int NOT NULL,
  PRIMARY KEY (`idProgetto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `progetto`
--

LOCK TABLES `progetto` WRITE;
/*!40000 ALTER TABLE `progetto` DISABLE KEYS */;
/*!40000 ALTER TABLE `progetto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `raccolta`
--

DROP TABLE IF EXISTS `raccolta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `raccolta` (
  `idServizio` char(20) NOT NULL,
  `idEvento` char(20) NOT NULL,
  `materiale` varchar(30) NOT NULL,
  `kg` decimal(8,2) NOT NULL,
  PRIMARY KEY (`idServizio`,`idEvento`,`materiale`),
  KEY `fk_servizio_has_rifiuto_rifiuto1_idx` (`materiale`),
  KEY `fk_raccolta_servizio1_idx` (`idEvento`,`idServizio`),
  CONSTRAINT `fk_raccolta_servizio1` FOREIGN KEY (`idEvento`, `idServizio`) REFERENCES `servizio` (`idEvento`, `idServizio`),
  CONSTRAINT `fk_servizio_has_rifiuto_rifiuto1` FOREIGN KEY (`materiale`) REFERENCES `rifiuto` (`materiale`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `raccolta`
--

LOCK TABLES `raccolta` WRITE;
/*!40000 ALTER TABLE `raccolta` DISABLE KEYS */;
/*!40000 ALTER TABLE `raccolta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rifiuto`
--

DROP TABLE IF EXISTS `rifiuto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rifiuto` (
  `materiale` varchar(30) NOT NULL,
  `tipo` varchar(45) NOT NULL,
  `kgImagazzinati` decimal(17,2) NOT NULL,
  `note` mediumtext,
  PRIMARY KEY (`materiale`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rifiuto`
--

LOCK TABLES `rifiuto` WRITE;
/*!40000 ALTER TABLE `rifiuto` DISABLE KEYS */;
INSERT INTO `rifiuto` VALUES ('carta','riciclabile',123.51,'note note'),('plastica','riciclabile',56.02,NULL);
/*!40000 ALTER TABLE `rifiuto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sede`
--

DROP TABLE IF EXISTS `sede`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sede` (
  `città` varchar(25) NOT NULL,
  `indirizzo` varchar(60) NOT NULL,
  `regione` varchar(20) NOT NULL,
  `codicePostale` varchar(10) NOT NULL,
  `telefono` varchar(24) NOT NULL,
  PRIMARY KEY (`città`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sede`
--

LOCK TABLES `sede` WRITE;
/*!40000 ALTER TABLE `sede` DISABLE KEYS */;
INSERT INTO `sede` VALUES ('Bari','via Garibaldi','Puglia','743873','0142415245'),('Roma','via Giuseppe','Lazio','320256','054542020');
/*!40000 ALTER TABLE `sede` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servizio`
--

DROP TABLE IF EXISTS `servizio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `servizio` (
  `idEvento` char(20) NOT NULL,
  `idServizio` char(20) NOT NULL,
  `oraInizio` time NOT NULL,
  `oraFine` time NOT NULL,
  `tipo` varchar(45) NOT NULL,
  `idProgetto` int DEFAULT NULL,
  PRIMARY KEY (`idEvento`,`idServizio`),
  KEY `idProgetto` (`idProgetto`),
  CONSTRAINT `servizio_ibfk_1` FOREIGN KEY (`idEvento`) REFERENCES `evento` (`idEvento`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `servizio_ibfk_2` FOREIGN KEY (`idProgetto`) REFERENCES `progetto` (`idProgetto`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servizio`
--

LOCK TABLES `servizio` WRITE;
/*!40000 ALTER TABLE `servizio` DISABLE KEYS */;
/*!40000 ALTER TABLE `servizio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tesserasocio`
--

DROP TABLE IF EXISTS `tesserasocio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tesserasocio` (
  `idSocio` varchar(15) NOT NULL,
  `codiceFiscale` char(16) NOT NULL,
  `dataAssociazione` date NOT NULL,
  PRIMARY KEY (`idSocio`),
  KEY `codiceFiscale` (`codiceFiscale`),
  CONSTRAINT `tesserasocio_ibfk_1` FOREIGN KEY (`codiceFiscale`) REFERENCES `persona` (`codiceFiscale`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tesserasocio`
--

LOCK TABLES `tesserasocio` WRITE;
/*!40000 ALTER TABLE `tesserasocio` DISABLE KEYS */;
INSERT INTO `tesserasocio` VALUES ('1','AAAAAAAAAAAAAAAA','2022-10-11'),('2','BBBBBBBBBBBBBBBB','2021-12-11');
/*!40000 ALTER TABLE `tesserasocio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vendita`
--

DROP TABLE IF EXISTS `vendita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vendita` (
  `codiceFiscaleCliente` char(16) NOT NULL,
  `idProdotto` int NOT NULL,
  `data` date NOT NULL,
  `quantità` int NOT NULL,
  `idEvento` char(20) NOT NULL,
  `idServizio` char(20) NOT NULL,
  PRIMARY KEY (`codiceFiscaleCliente`,`idProdotto`,`data`),
  KEY `fk_persona_has_prodotto_prodotto1_idx` (`idProdotto`),
  KEY `fk_persona_has_prodotto_persona1_idx` (`codiceFiscaleCliente`),
  KEY `fk_vendita_servizio1_idx` (`idEvento`,`idServizio`),
  CONSTRAINT `fk_persona_has_prodotto_persona1` FOREIGN KEY (`codiceFiscaleCliente`) REFERENCES `persona` (`codiceFiscale`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_persona_has_prodotto_prodotto1` FOREIGN KEY (`idProdotto`) REFERENCES `prodotto` (`idProdotto`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_vendita_servizio1` FOREIGN KEY (`idEvento`, `idServizio`) REFERENCES `servizio` (`idEvento`, `idServizio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vendita`
--

LOCK TABLES `vendita` WRITE;
/*!40000 ALTER TABLE `vendita` DISABLE KEYS */;
/*!40000 ALTER TABLE `vendita` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `volontario`
--

DROP TABLE IF EXISTS `volontario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `volontario` (
  `codiceFiscale` char(16) NOT NULL,
  `sedeCittà` varchar(25) NOT NULL,
  `dataIscrizione` date NOT NULL,
  PRIMARY KEY (`codiceFiscale`),
  KEY `sedeCittà` (`sedeCittà`),
  CONSTRAINT `volontario_ibfk_1` FOREIGN KEY (`codiceFiscale`) REFERENCES `persona` (`codiceFiscale`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `volontario_ibfk_2` FOREIGN KEY (`sedeCittà`) REFERENCES `sede` (`città`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `volontario`
--

LOCK TABLES `volontario` WRITE;
/*!40000 ALTER TABLE `volontario` DISABLE KEYS */;
INSERT INTO `volontario` VALUES ('AAAAAAAAAAAAAAAA','Roma','2022-12-11'),('BBBBBBBBBBBBBBBB','Bari','2021-12-11');
/*!40000 ALTER TABLE `volontario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-08-11 16:25:47
