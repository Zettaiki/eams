CREATE DATABASE  IF NOT EXISTS `eams` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `eams`;
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
  `codicePostale` varchar(10) NOT NULL,
  `città` varchar(15) NOT NULL,
  `regione` varchar(20) NOT NULL,
  `mail` varchar(40) NOT NULL,
  `fax` varchar(24) DEFAULT NULL,
  PRIMARY KEY (`partitaIVA`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `azienda`
--

LOCK TABLES `azienda` WRITE;
/*!40000 ALTER TABLE `azienda` DISABLE KEYS */;
INSERT INTO `azienda` VALUES (12345678191,'Rainbow srl','0832-675104','piazza 4 maggio, 45','73120','Campi Salentina','Puglia','rainbowsrl@gmail.com','0832-675104'),(12345678911,'Hera Recycle srl','0543-627897','via G. Garibaldi, 94','47122','Forlì','Emilia-Romagna','herarecycle@gmail.com','0543-627897'),(12346578911,'Trash tossers srl','0836-627897','via Taranto, 12','73100','Lecce','Puglia','trashtossers@gmail.com','0836-627897'),(12435678911,'GreenWash spa','0961-453415','via G. Garibaldi, 36','85544','Catanzaro','Calabria','greenwash@gmail.com','0961-453415'),(13245678911,'Paper Crafts spa','0774-796084','corso Mazzini, 52','00118','Roma','Lazio','papercrafts@gmail.com','0774-796084');
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
INSERT INTO `categoria` VALUES ('Abbigliamento',NULL),('Cancelleria',NULL),('Cucina',NULL),('Cura della persona',NULL),('Giardinaggio',NULL),('Idee regalo','Regali ecosostenibili.'),('Merchandise','Eco-gadget con il logo dell\'associazione.'),('On the go','Usati spesso da portare fuori casa che di solito sono in plastica, usa e getta o non riciclabili.');
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
  KEY `fk_rifiuto_has_azienda_azienda1_idx` (`partitaIVA`),
  KEY `fk_rifiuto_has_azienda_rifiuto1_idx` (`materiale`),
  CONSTRAINT `fk_rifiuto_has_azienda_azienda1` FOREIGN KEY (`partitaIVA`) REFERENCES `azienda` (`partitaIVA`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_rifiuto_has_azienda_rifiuto1` FOREIGN KEY (`materiale`) REFERENCES `rifiuto` (`materiale`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `consegna`
--

LOCK TABLES `consegna` WRITE;
/*!40000 ALTER TABLE `consegna` DISABLE KEYS */;
INSERT INTO `consegna` VALUES ('Carta',12346578911,'2022-04-11',19.20),('Termoplastiche',12345678911,'2022-02-23',13.40),('Termoplastiche',12345678911,'2022-07-07',5.70),('Termoplastiche',12346578911,'2022-08-11',10.50),('Termoplastiche',12346578911,'2022-08-27',8.40);
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
  KEY `fk_dipendente_sede1_idx` (`sedeCittà`),
  CONSTRAINT `fk_dipendente_persona1` FOREIGN KEY (`codiceFiscale`) REFERENCES `persona` (`codiceFiscale`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_dipendente_sede1` FOREIGN KEY (`sedeCittà`) REFERENCES `sede` (`città`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dipendente`
--

LOCK TABLES `dipendente` WRITE;
/*!40000 ALTER TABLE `dipendente` DISABLE KEYS */;
INSERT INTO `dipendente` VALUES ('CLDMTR70A01G479X','Pesaro','2021-01-01',1500.00),('GNMRZN78A01B354R','Catanzaro','2020-04-01',1200.00);
/*!40000 ALTER TABLE `dipendente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `donazione`
--

DROP TABLE IF EXISTS `donazione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donazione` (
  `idDonazione` int NOT NULL AUTO_INCREMENT,
  `importo` decimal(11,2) NOT NULL,
  `codiceFiscale` char(16) NOT NULL,
  `dataDonazione` date NOT NULL,
  `idProgetto` int DEFAULT NULL,
  PRIMARY KEY (`idDonazione`),
  KEY `fk_donazione_progetto1_idx` (`idProgetto`),
  KEY `fk_donazione_persona1_idx` (`codiceFiscale`),
  CONSTRAINT `fk_donazione_persona1` FOREIGN KEY (`codiceFiscale`) REFERENCES `persona` (`codiceFiscale`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_donazione_progetto1` FOREIGN KEY (`idProgetto`) REFERENCES `progetto` (`idProgetto`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donazione`
--

LOCK TABLES `donazione` WRITE;
/*!40000 ALTER TABLE `donazione` DISABLE KEYS */;
INSERT INTO `donazione` VALUES (1,20.00,'CHRGTN88A01C352W','2022-05-24',4),(2,20.00,'CHRGTN88A01C352W','2022-06-24',6),(3,5.00,'PLABNL91S14I608D','2022-06-29',NULL),(4,20.00,'CHRGTN88A01C352W','2022-07-24',6),(5,35.00,'PLABNL91S14I608D','2022-06-29',1);
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
  `descrizione` mediumtext,
  PRIMARY KEY (`idEvento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evento`
--

LOCK TABLES `evento` WRITE;
/*!40000 ALTER TABLE `evento` DISABLE KEYS */;
INSERT INTO `evento` VALUES ('e1','Giornata della terra','2023-04-22','Manifestazione in piazza con raccolte fondi per i progetti, stand di vendita,\n e attività di gioco e istruzione ambientale per bambini.'),('e2','Spiagge pulite','2022-06-15',NULL),('e3','Giornata del leone','2022-08-10',NULL),('e4','Eco-photo run','2023-05-10','Raccolta fondi tramite offerta libera per partecipare a una maratona di foto green.'),('e5','Friday for future','2022-03-25',NULL),('e6','Earth hour','2021-03-21','Spegnamo le luci per l\'inquinamento luminoso.'),('e7','Liberi dai rifiuti','2022-11-21','Raccolte rifiuti urbani, fabbricazione prodotti da materiale riciclabile.');
/*!40000 ALTER TABLE `evento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fornitura`
--

DROP TABLE IF EXISTS `fornitura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fornitura` (
  `idProdotto` char(20) NOT NULL,
  `partitaIVA` decimal(11,0) NOT NULL,
  `data` date NOT NULL,
  `quantitàFornita` int NOT NULL,
  PRIMARY KEY (`idProdotto`,`partitaIVA`,`data`),
  KEY `fk_prodotto_has_azienda_azienda1_idx` (`partitaIVA`),
  KEY `fk_fornitura_prodotto1_idx` (`idProdotto`),
  CONSTRAINT `fk_fornitura_prodotto1` FOREIGN KEY (`idProdotto`) REFERENCES `prodotto` (`idProdotto`),
  CONSTRAINT `fk_prodotto_has_azienda_azienda1` FOREIGN KEY (`partitaIVA`) REFERENCES `azienda` (`partitaIVA`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fornitura`
--

LOCK TABLES `fornitura` WRITE;
/*!40000 ALTER TABLE `fornitura` DISABLE KEYS */;
INSERT INTO `fornitura` VALUES ('DG56',12435678911,'2022-01-19',30),('NT39',12435678911,'2022-04-19',15),('PA73',12345678191,'2022-02-05',10);
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
  KEY `fk_persona_has_newsletter_persona1_idx` (`codiceFiscale`),
  KEY `fk_iscrizione_newsletter1_idx` (`idNewsletter`),
  CONSTRAINT `fk_iscrizione_newsletter1` FOREIGN KEY (`idNewsletter`) REFERENCES `newsletter` (`idNewsletter`),
  CONSTRAINT `fk_persona_has_newsletter_persona1` FOREIGN KEY (`codiceFiscale`) REFERENCES `persona` (`codiceFiscale`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `iscrizione`
--

LOCK TABLES `iscrizione` WRITE;
/*!40000 ALTER TABLE `iscrizione` DISABLE KEYS */;
INSERT INTO `iscrizione` VALUES ('GNMRZN78A01B354R',1),('CHRGTN88A01C352W',2),('GNMRZN78A01B354R',3),('CHRGTN88A01C352W',6),('GNMRZN78A01B354R',6),('CHRGTN88A01C352W',7),('CHRGTN88A01C352W',9);
/*!40000 ALTER TABLE `iscrizione` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `newsletter`
--

DROP TABLE IF EXISTS `newsletter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `newsletter` (
  `idNewsletter` int NOT NULL AUTO_INCREMENT,
  `argomento` varchar(45) NOT NULL,
  `descrizione` mediumtext,
  PRIMARY KEY (`idNewsletter`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `newsletter`
--

LOCK TABLES `newsletter` WRITE;
/*!40000 ALTER TABLE `newsletter` DISABLE KEYS */;
INSERT INTO `newsletter` VALUES (1,'Ambiente',NULL),(2,'Animali in estinzione',NULL),(3,'Inquinamento',NULL),(4,'Junior','Una newsletter dedicata ai più piccoli.'),(5,'Quiz',NULL),(6,'Novità sui progetti',NULL),(7,'Notizie verdi',NULL),(8,'Curiosità ed eco-consigli',NULL),(9,'Crimini di natura',NULL);
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
  KEY `fk_evento_has_dipendente_dipendente1_idx` (`codiceFiscaleDipendente`),
  KEY `fk_evento_has_dipendente_evento1_idx` (`idEvento`),
  CONSTRAINT `fk_evento_has_dipendente_dipendente1` FOREIGN KEY (`codiceFiscaleDipendente`) REFERENCES `dipendente` (`codiceFiscale`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_evento_has_dipendente_evento1` FOREIGN KEY (`idEvento`) REFERENCES `evento` (`idEvento`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organizzazione`
--

LOCK TABLES `organizzazione` WRITE;
/*!40000 ALTER TABLE `organizzazione` DISABLE KEYS */;
INSERT INTO `organizzazione` VALUES ('CLDMTR70A01G479X','e1'),('CLDMTR70A01G479X','e3'),('CLDMTR70A01G479X','e7'),('GNMRZN78A01B354R','e2'),('GNMRZN78A01B354R','e4'),('GNMRZN78A01B354R','e5'),('GNMRZN78A01B354R','e6');
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
  PRIMARY KEY (`codiceFiscaleVolontario`,`idServizio`),
  KEY `fk_partecipazione_volontario1_idx` (`codiceFiscaleVolontario`),
  KEY `fk_partecipazione_servizio2_idx` (`idServizio`),
  CONSTRAINT `fk_partecipazione_servizio2` FOREIGN KEY (`idServizio`) REFERENCES `servizio` (`idServizio`),
  CONSTRAINT `fk_partecipazione_volontario1` FOREIGN KEY (`codiceFiscaleVolontario`) REFERENCES `volontario` (`codiceFiscale`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `partecipazione`
--

LOCK TABLES `partecipazione` WRITE;
/*!40000 ALTER TABLE `partecipazione` DISABLE KEYS */;
INSERT INTO `partecipazione` VALUES ('NDRCSD00A01C573B','S1-1'),('NDRCSD00A01C573B','S4-1'),('NDRCSD00A01C573B','S5-1'),('PLABNL91S14I608D','S1-2'),('PLABNL91S14I608D','S5-1');
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
  `nome` varchar(25) NOT NULL,
  `cognome` varchar(25) NOT NULL,
  `mail` varchar(45) NOT NULL,
  `telefono` varchar(24) DEFAULT NULL,
  `indirizzo` varchar(60) NOT NULL,
  `codicePostale` varchar(10) NOT NULL,
  `città` varchar(15) NOT NULL,
  `regione` varchar(20) NOT NULL,
  `tipo` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`codiceFiscale`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persona`
--

LOCK TABLES `persona` WRITE;
/*!40000 ALTER TABLE `persona` DISABLE KEYS */;
INSERT INTO `persona` VALUES ('CHRGTN88A01C352W','Chiara','Gatani','chiara88@gmail.com','0961-15640','via F. Tozzi, 56','85545','Catanzaro','Calabria',NULL),('CLDMTR70A01G479X','Claudio','Mastrati','claudio70@gmail.com','0721-10434','viale della Costituzione, 178','61121','Pesaro','Marche','dipendente'),('GNMRZN78A01B354R','Gianmarco','Oronzini','gianma78@gmail.com','0732-76078','piazza 20 settembre, 4','09134','Cagliari','Sardegna','dipendente'),('NDRCSD00A01C573B','Andrea','Casadei','andrecas@gmail.com','0543-80765','via A. Avogadro, 13','47122','Forlì','Emilia-Romagna','volontario'),('PLABNL91S14I608D','Paolo','Brunelli','brunellipaolo@gmail.com','0719-84515','corso Mazzini, 29','44207','Senigallia','Marche','volontario');
/*!40000 ALTER TABLE `persona` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prodotto`
--

DROP TABLE IF EXISTS `prodotto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prodotto` (
  `idProdotto` char(20) NOT NULL,
  `categoria` varchar(30) NOT NULL,
  `nome` varchar(30) NOT NULL,
  `prezzo` decimal(6,2) NOT NULL,
  `quantitàImmagazzinata` int NOT NULL,
  `provenienza` varchar(20) NOT NULL,
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
INSERT INTO `prodotto` VALUES ('DG56','On the go','Bottiglia di acciaio blu',20.00,30,'Fornito',NULL),('EC51','Abbigliamento','Cappello da baseball',20.00,30,'Fornito','Logo sulla parte frontale.'),('F001','Merchandise','Braccialetto turtle',7.00,50,'Fabbricato','Plastica riciclata, ciondolo tartaruga.'),('F002','Merchandise','Braccialetto dolphin',7.00,50,'Fabbricato','Plastica riciclata, ciondolo delfino.'),('F003','Merchandise','Collana turtle',10.00,40,'Fabbricato','Plastica riciclata, ciondolo tartaruga.'),('F005','Merchandise','Collana dolphin',10.00,40,'Fabbricato','Plastica riciclata, ciondolo delfino.'),('HG46','Cura della persona','Spazzolino bamboo',20.00,30,'Fornito','Set da 7 spazzolini.'),('JW49','Idee regalo','Sacchetto di tela',5.50,50,'Fornito',NULL),('NT39','On the go','Bottiglia di acciaio grigia',20.00,30,'Fornito',NULL),('PA73','On the go','Set posate in bamboo',15.00,25,'Fornito',NULL),('RB87','On the go','Bottiglia di acciaio nera',20.00,30,'Fornito',NULL),('W79R','On the go','Bottiglia di acciaio rosa',20.00,30,'Fornito',NULL);
/*!40000 ALTER TABLE `prodotto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produzione`
--

DROP TABLE IF EXISTS `produzione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `produzione` (
  `idProdotto` char(20) NOT NULL,
  `idServizio` char(20) NOT NULL,
  `quantitàProdotta` int NOT NULL,
  `materialeUsato` varchar(30) NOT NULL,
  `kgRifiutiUsati` decimal(8,2) NOT NULL,
  PRIMARY KEY (`idProdotto`,`idServizio`),
  KEY `fk_servizio_has_rifiuto_rifiuto2_idx` (`materialeUsato`),
  KEY `fk_produzione_servizio1_idx` (`idServizio`),
  KEY `fk_produzione_prodotto2_idx` (`idProdotto`),
  CONSTRAINT `fk_produzione_prodotto2` FOREIGN KEY (`idProdotto`) REFERENCES `prodotto` (`idProdotto`),
  CONSTRAINT `fk_produzione_servizio1` FOREIGN KEY (`idServizio`) REFERENCES `servizio` (`idServizio`),
  CONSTRAINT `fk_servizio_has_rifiuto_rifiuto2` FOREIGN KEY (`materialeUsato`) REFERENCES `rifiuto` (`materiale`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produzione`
--

LOCK TABLES `produzione` WRITE;
/*!40000 ALTER TABLE `produzione` DISABLE KEYS */;
INSERT INTO `produzione` VALUES ('F001','S4-3',15,'Termoplastiche',13.52),('F002','S4-3',13,'Termoplastiche',10.70);
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
  `descrizione` mediumtext,
  `dataFine` date GENERATED ALWAYS AS (cast(concat_ws(_utf8mb4'-',(floor(((month(`dataInizio`) + `durataMesi`) / 12)) + year(`dataInizio`)),((month(`dataInizio`) + `durataMesi`) % 12),dayofmonth(`dataInizio`)) as date)) VIRTUAL,
  PRIMARY KEY (`idProgetto`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `progetto`
--

LOCK TABLES `progetto` WRITE;
/*!40000 ALTER TABLE `progetto` DISABLE KEYS */;
INSERT INTO `progetto` (`idProgetto`, `obbiettivo`, `dataInizio`, `durataMesi`, `descrizione`) VALUES (1,'Creare un rifugio per animali','2022-10-01',6,NULL),(2,'Ecofishing','2021-04-04',12,'Programma di protezione dell’ambiente marino, attraverso la raccolta, il recupero e il riciclo delle reti da pesca abbandonate \ne l’implementazione di un programma di pesca sostenibile.'),(3,'Rain-forest','2022-08-10',18,'Piano di reinforestazione delle aree più colpite dal disboscamento in Italia.'),(4,'Città verde','2022-01-17',12,'Rendere più verdi le zone urbane.'),(5,'Leopardo delle nevi','2022-03-22',12,'Ridurre il bracconaggio dei leopardi delle nevi, ricostruzione di habitat favorevoli.'),(6,'Tour in bosco','2022-09-01',9,'Progetto di educazione ambientale tramite tour nei luoghi naturali più prossimi per le scuole elementari e medie.'),(7,'Elefanti','2022-08-22',18,'Ridurre il bracconaggio.');
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
  `materiale` varchar(30) NOT NULL,
  `kg` decimal(8,2) NOT NULL,
  PRIMARY KEY (`idServizio`,`materiale`),
  KEY `fk_servizio_has_rifiuto_rifiuto1_idx` (`materiale`),
  KEY `fk_raccolta_servizio1_idx` (`idServizio`),
  CONSTRAINT `fk_raccolta_servizio1` FOREIGN KEY (`idServizio`) REFERENCES `servizio` (`idServizio`),
  CONSTRAINT `fk_servizio_has_rifiuto_rifiuto1` FOREIGN KEY (`materiale`) REFERENCES `rifiuto` (`materiale`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `raccolta`
--

LOCK TABLES `raccolta` WRITE;
/*!40000 ALTER TABLE `raccolta` DISABLE KEYS */;
INSERT INTO `raccolta` VALUES ('S4-1','Carta',25.30),('S4-1','Plastiche termoindurenti',30.40),('S4-1','Termoplastiche',10.02),('S4-2','Carta',25.73),('S4-2','Termoplastiche',18.02),('S4-2','Vetro',20.92);
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
INSERT INTO `rifiuto` VALUES ('Carta','Riciclabile',154.65,NULL),('Plastiche termoindurenti','Non riciclabile',322.54,'7 - Altre plastiche'),('Termoplastiche','Riciclabile',260.87,'1 - PET (polietilene tereftalato), \n2 - HDPE (polietilene ad alta densità), 3 - PVC O V (cloruro di polivinile), 4 - LDPE (polietilene a bassa densità), 5 - PP (polipropilene), \n6 - PS (polistirene o polistirolo)'),('Vetro','Riciclabile',134.78,NULL);
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
  `codicePostale` varchar(10) NOT NULL,
  `regione` varchar(20) NOT NULL,
  `telefono` varchar(24) NOT NULL,
  PRIMARY KEY (`città`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sede`
--

LOCK TABLES `sede` WRITE;
/*!40000 ALTER TABLE `sede` DISABLE KEYS */;
INSERT INTO `sede` VALUES ('Catanzaro','via delle rocce, 273','85545','Calabria','0961-40864'),('Forlì','viale Franco, 45','47121','Emilia-Romagna','0543-86546'),('Pesaro','via ponti, 23','61121','Marche','0721-01898'),('Roma','piazzetta della misura, 76','00118','Lazio','0774-34837');
/*!40000 ALTER TABLE `sede` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servizio`
--

DROP TABLE IF EXISTS `servizio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `servizio` (
  `idServizio` char(20) NOT NULL,
  `idEvento` char(20) NOT NULL,
  `oraInizio` time NOT NULL,
  `oraFine` time NOT NULL,
  `tipo` varchar(45) NOT NULL,
  `idProgetto` int DEFAULT NULL,
  PRIMARY KEY (`idServizio`),
  KEY `fk_servizio_evento1_idx` (`idEvento`),
  KEY `fk_servizio_progetto1_idx` (`idProgetto`),
  CONSTRAINT `fk_servizio_evento1` FOREIGN KEY (`idEvento`) REFERENCES `evento` (`idEvento`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_servizio_progetto1` FOREIGN KEY (`idProgetto`) REFERENCES `progetto` (`idProgetto`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servizio`
--

LOCK TABLES `servizio` WRITE;
/*!40000 ALTER TABLE `servizio` DISABLE KEYS */;
INSERT INTO `servizio` VALUES ('S1-1','e1','15:00:00','18:00:00','Stand di vendita',NULL),('S1-2','e1','17:00:00','19:00:00','Raccolta fondi',NULL),('S2-1','e3','17:30:00','18:00:00','Attività ludico-istruttiva',NULL),('S4-1','e7','10:00:00','13:00:00','Raccolta rifiuti',NULL),('S4-2','e7','15:00:00','17:00:00','Raccolta rifiuti',NULL),('S4-3','e7','15:00:00','17:00:00','Fabbricazione prodotti',NULL),('S5-1','e4','16:30:00','19:00:00','Raccolta fondi',NULL);
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
  KEY `fk_tesserasocio_persona1_idx` (`codiceFiscale`),
  CONSTRAINT `fk_tesserasocio_persona1` FOREIGN KEY (`codiceFiscale`) REFERENCES `persona` (`codiceFiscale`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tesserasocio`
--

LOCK TABLES `tesserasocio` WRITE;
/*!40000 ALTER TABLE `tesserasocio` DISABLE KEYS */;
INSERT INTO `tesserasocio` VALUES ('SOC0000001','CHRGTN88A01C352W','2022-05-24'),('SOC0000002','PLABNL91S14I608D','2021-12-07');
/*!40000 ALTER TABLE `tesserasocio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utente`
--

DROP TABLE IF EXISTS `utente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utente` (
  `codiceFiscaleDipendente` char(16) NOT NULL,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`codiceFiscaleDipendente`),
  CONSTRAINT `fk_User_dipendente1` FOREIGN KEY (`codiceFiscaleDipendente`) REFERENCES `dipendente` (`codiceFiscale`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utente`
--

LOCK TABLES `utente` WRITE;
/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` VALUES ('CLDMTR70A01G479X','claudio','claudio22'),('GNMRZN78A01B354R','gianmarco','gianmarco22');
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vendita`
--

DROP TABLE IF EXISTS `vendita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vendita` (
  `idProdotto` char(20) NOT NULL,
  `idServizio` char(20) NOT NULL,
  `codiceFiscaleCliente` char(16) NOT NULL,
  `quantità` int NOT NULL,
  KEY `fk_persona_has_prodotto_persona1_idx` (`codiceFiscaleCliente`),
  KEY `fk_vendita_servizio1_idx` (`idServizio`),
  KEY `fk_vendita_prodotto1_idx` (`idProdotto`),
  CONSTRAINT `fk_persona_has_prodotto_persona1` FOREIGN KEY (`codiceFiscaleCliente`) REFERENCES `persona` (`codiceFiscale`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_vendita_prodotto1` FOREIGN KEY (`idProdotto`) REFERENCES `prodotto` (`idProdotto`),
  CONSTRAINT `fk_vendita_servizio1` FOREIGN KEY (`idServizio`) REFERENCES `servizio` (`idServizio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vendita`
--

LOCK TABLES `vendita` WRITE;
/*!40000 ALTER TABLE `vendita` DISABLE KEYS */;
INSERT INTO `vendita` VALUES ('DG56','S1-1','GNMRZN78A01B354R',1),('DG56','S1-1','NDRCSD00A01C573B',1),('EC51','S1-1','CLDMTR70A01G479X',2),('F003','S1-1','CHRGTN88A01C352W',4);
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
  KEY `fk_volontario_sede1_idx` (`sedeCittà`),
  KEY `fk_volontario_persona1_idx` (`codiceFiscale`),
  CONSTRAINT `fk_volontario_persona1` FOREIGN KEY (`codiceFiscale`) REFERENCES `persona` (`codiceFiscale`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_volontario_sede1` FOREIGN KEY (`sedeCittà`) REFERENCES `sede` (`città`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `volontario`
--

LOCK TABLES `volontario` WRITE;
/*!40000 ALTER TABLE `volontario` DISABLE KEYS */;
INSERT INTO `volontario` VALUES ('NDRCSD00A01C573B','Forlì','2021-05-17'),('PLABNL91S14I608D','Pesaro','2020-02-21');
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

-- Dump completed on 2022-08-22 15:13:28
