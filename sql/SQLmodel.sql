-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema eams
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema eams
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `eams` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `eams` ;

-- -----------------------------------------------------
-- Table `eams`.`persona`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eams`.`persona` (
  `CodiceFiscale` CHAR(16) NOT NULL,
  `Nome` VARCHAR(25) NULL DEFAULT NULL,
  `Cognome` VARCHAR(25) NULL DEFAULT NULL,
  `Telefono` VARCHAR(24) NULL DEFAULT NULL,
  `Indirizzo` VARCHAR(60) NULL DEFAULT NULL,
  `Città` VARCHAR(15) NULL DEFAULT NULL,
  `Regione` VARCHAR(20) NULL DEFAULT NULL,
  `CodicePostale` VARCHAR(10) NULL DEFAULT NULL,
  `Tipo` VARCHAR(45) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_bin' NULL DEFAULT NULL COMMENT '1: Interna\\\\n0: Esterna',
  PRIMARY KEY (`CodiceFiscale`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `eams`.`categoria`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eams`.`categoria` (
  `Nome` VARCHAR(30) NOT NULL,
  `Descrizione` MEDIUMTEXT NULL DEFAULT NULL,
  PRIMARY KEY (`Nome`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `eams`.`prodotto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eams`.`prodotto` (
  `ProdottoID` INT NOT NULL,
  `Nome` VARCHAR(30) NULL DEFAULT NULL,
  `Descrizione` MEDIUMTEXT NULL DEFAULT NULL,
  `Prezzo` DECIMAL(6,2) NULL DEFAULT NULL,
  `Categoria` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`ProdottoID`, `Categoria`),
  INDEX `fk_prodotto_categoria1_idx` (`Categoria` ASC) VISIBLE,
  CONSTRAINT `fk_prodotto_categoria1`
    FOREIGN KEY (`Categoria`)
    REFERENCES `eams`.`categoria` (`Nome`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `eams`.`evento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eams`.`evento` (
  `EventoID` CHAR(20) NOT NULL,
  `Nome` VARCHAR(30) NULL DEFAULT NULL,
  `Data` DATETIME NULL DEFAULT NULL,
  `Descrizione` VARCHAR(60) NULL DEFAULT NULL,
  PRIMARY KEY (`EventoID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `eams`.`progetto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eams`.`progetto` (
  `ProgettoID` INT NOT NULL,
  `Obbiettivo` VARCHAR(45) NULL DEFAULT NULL,
  `DataInizio` DATETIME NULL DEFAULT NULL,
  `DurataMesi` INT NULL DEFAULT NULL,
  PRIMARY KEY (`ProgettoID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `eams`.`servizio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eams`.`servizio` (
  `OraInizio` TIME NOT NULL,
  `OraFine` TIME NULL DEFAULT NULL,
  `Tipo` VARCHAR(45) NULL DEFAULT NULL,
  `EventoID` CHAR(20) NOT NULL,
  `progetto_ProgettoID` INT NOT NULL,
  PRIMARY KEY (`OraInizio`, `EventoID`),
  INDEX `fk_servizio_evento1_idx` (`EventoID` ASC) VISIBLE,
  INDEX `fk_servizio_progetto1_idx` (`progetto_ProgettoID` ASC) VISIBLE,
  CONSTRAINT `fk_servizio_evento1`
    FOREIGN KEY (`EventoID`)
    REFERENCES `eams`.`evento` (`EventoID`),
  CONSTRAINT `fk_servizio_progetto1`
    FOREIGN KEY (`progetto_ProgettoID`)
    REFERENCES `eams`.`progetto` (`ProgettoID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `eams`.`acquisto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eams`.`acquisto` (
  `CodiceFiscaleCliente` CHAR(16) NOT NULL,
  `ProdottoID` INT NOT NULL,
  `CategoriaProdotto` VARCHAR(30) NOT NULL,
  `Data` DATETIME NULL DEFAULT NULL,
  `Quantità` INT NULL DEFAULT NULL,
  `servizio_OraInizio` TIME NOT NULL,
  `servizio_EventoID` CHAR(20) NOT NULL,
  PRIMARY KEY (`CodiceFiscaleCliente`, `ProdottoID`, `CategoriaProdotto`, `servizio_OraInizio`, `servizio_EventoID`),
  INDEX `fk_persona_has_prodotto_prodotto1_idx` (`ProdottoID` ASC, `CategoriaProdotto` ASC) VISIBLE,
  INDEX `fk_persona_has_prodotto_persona1_idx` (`CodiceFiscaleCliente` ASC) VISIBLE,
  INDEX `fk_acquisto_servizio1_idx` (`servizio_OraInizio` ASC, `servizio_EventoID` ASC) VISIBLE,
  CONSTRAINT `fk_persona_has_prodotto_persona1`
    FOREIGN KEY (`CodiceFiscaleCliente`)
    REFERENCES `eams`.`persona` (`CodiceFiscale`),
  CONSTRAINT `fk_persona_has_prodotto_prodotto1`
    FOREIGN KEY (`ProdottoID` , `CategoriaProdotto`)
    REFERENCES `eams`.`prodotto` (`ProdottoID` , `Categoria`),
  CONSTRAINT `fk_acquisto_servizio1`
    FOREIGN KEY (`servizio_OraInizio` , `servizio_EventoID`)
    REFERENCES `eams`.`servizio` (`OraInizio` , `EventoID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `eams`.`azienda`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eams`.`azienda` (
  `PartitaIVA` INT NOT NULL,
  `DenominazioneSociale` VARCHAR(45) NULL DEFAULT NULL,
  `Telefono` VARCHAR(24) NULL DEFAULT NULL,
  `Indirizzo` VARCHAR(60) NULL DEFAULT NULL,
  `Città` VARCHAR(15) NULL DEFAULT NULL,
  `Regione` VARCHAR(20) NULL DEFAULT NULL,
  `CodicePostale` VARCHAR(10) NULL DEFAULT NULL,
  `Mail` VARCHAR(20) NULL DEFAULT NULL,
  `Fax` VARCHAR(24) NULL DEFAULT NULL,
  PRIMARY KEY (`PartitaIVA`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `eams`.`rifiuto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eams`.`rifiuto` (
  `Materiale` VARCHAR(30) NOT NULL,
  `Note` MEDIUMTEXT NULL DEFAULT NULL,
  `Tipo` VARCHAR(45) NULL,
  PRIMARY KEY (`Materiale`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `eams`.`consegna`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eams`.`consegna` (
  `Materiale` VARCHAR(30) NOT NULL,
  `PartitaIVA` INT NOT NULL,
  `Data` DATETIME NULL DEFAULT NULL,
  `Quantità(kg)` FLOAT NULL DEFAULT NULL,
  PRIMARY KEY (`Materiale`, `PartitaIVA`),
  INDEX `fk_rifiuto_has_azienda_azienda1_idx` (`PartitaIVA` ASC) VISIBLE,
  INDEX `fk_rifiuto_has_azienda_rifiuto1_idx` (`Materiale` ASC) VISIBLE,
  CONSTRAINT `fk_rifiuto_has_azienda_azienda1`
    FOREIGN KEY (`PartitaIVA`)
    REFERENCES `eams`.`azienda` (`PartitaIVA`),
  CONSTRAINT `fk_rifiuto_has_azienda_rifiuto1`
    FOREIGN KEY (`Materiale`)
    REFERENCES `eams`.`rifiuto` (`Materiale`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `eams`.`sede`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eams`.`sede` (
  `Città` VARCHAR(25) NOT NULL,
  `Indirizzo` VARCHAR(60) NULL DEFAULT NULL,
  `Regione` VARCHAR(20) NULL DEFAULT NULL,
  `CodicePostale` VARCHAR(10) NULL DEFAULT NULL,
  `Telefono` VARCHAR(24) NULL DEFAULT NULL,
  PRIMARY KEY (`Città`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `eams`.`dipendente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eams`.`dipendente` (
  `CodiceFiscale` CHAR(16) NOT NULL,
  `SedeCittà` VARCHAR(25) NOT NULL,
  `DataAssunzione` DATETIME NULL DEFAULT NULL,
  `Salario` FLOAT NULL DEFAULT NULL,
  PRIMARY KEY (`CodiceFiscale`, `SedeCittà`),
  INDEX `fk_dipendente_sede1_idx` (`SedeCittà` ASC) VISIBLE,
  CONSTRAINT `fk_dipendente_persona1`
    FOREIGN KEY (`CodiceFiscale`)
    REFERENCES `eams`.`persona` (`CodiceFiscale`),
  CONSTRAINT `fk_dipendente_sede1`
    FOREIGN KEY (`SedeCittà`)
    REFERENCES `eams`.`sede` (`Città`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `eams`.`donazione`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eams`.`donazione` (
  `DonazioneID` INT NOT NULL AUTO_INCREMENT,
  `Importo` DECIMAL(11,2) NULL DEFAULT NULL,
  `Data` DATETIME NULL DEFAULT NULL,
  `ProgettoID` INT NOT NULL,
  `CodiceFiscale` CHAR(16) NOT NULL,
  PRIMARY KEY (`DonazioneID`, `ProgettoID`, `CodiceFiscale`),
  INDEX `fk_donazione_progetto1_idx` (`ProgettoID` ASC) VISIBLE,
  INDEX `fk_donazione_persona1_idx` (`CodiceFiscale` ASC) VISIBLE,
  CONSTRAINT `fk_donazione_persona1`
    FOREIGN KEY (`CodiceFiscale`)
    REFERENCES `eams`.`persona` (`CodiceFiscale`),
  CONSTRAINT `fk_donazione_progetto1`
    FOREIGN KEY (`ProgettoID`)
    REFERENCES `eams`.`progetto` (`ProgettoID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `eams`.`fornitura`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eams`.`fornitura` (
  `ProdottoID` INT NOT NULL,
  `Categoria` VARCHAR(30) NOT NULL,
  `PartitaIVA` INT NOT NULL,
  `Data` DATETIME NULL DEFAULT NULL,
  `Quantità` INT NULL DEFAULT NULL,
  PRIMARY KEY (`ProdottoID`, `Categoria`, `PartitaIVA`),
  INDEX `fk_prodotto_has_azienda_azienda1_idx` (`PartitaIVA` ASC) VISIBLE,
  INDEX `fk_prodotto_has_azienda_prodotto1_idx` (`ProdottoID` ASC, `Categoria` ASC) VISIBLE,
  CONSTRAINT `fk_prodotto_has_azienda_azienda1`
    FOREIGN KEY (`PartitaIVA`)
    REFERENCES `eams`.`azienda` (`PartitaIVA`),
  CONSTRAINT `fk_prodotto_has_azienda_prodotto1`
    FOREIGN KEY (`ProdottoID` , `Categoria`)
    REFERENCES `eams`.`prodotto` (`ProdottoID` , `Categoria`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `eams`.`newsletter`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eams`.`newsletter` (
  `NewsletterID` INT NOT NULL,
  `Argomento` VARCHAR(45) NULL DEFAULT NULL,
  `Descrizione` MEDIUMTEXT NULL DEFAULT NULL,
  PRIMARY KEY (`NewsletterID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `eams`.`iscrizione`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eams`.`iscrizione` (
  `CodiceFiscale` CHAR(16) NOT NULL,
  `NewsletterID` INT NOT NULL,
  PRIMARY KEY (`CodiceFiscale`, `NewsletterID`),
  INDEX `fk_persona_has_newsletter_newsletter1_idx` (`NewsletterID` ASC) VISIBLE,
  INDEX `fk_persona_has_newsletter_persona1_idx` (`CodiceFiscale` ASC) VISIBLE,
  CONSTRAINT `fk_persona_has_newsletter_newsletter1`
    FOREIGN KEY (`NewsletterID`)
    REFERENCES `eams`.`newsletter` (`NewsletterID`),
  CONSTRAINT `fk_persona_has_newsletter_persona1`
    FOREIGN KEY (`CodiceFiscale`)
    REFERENCES `eams`.`persona` (`CodiceFiscale`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `eams`.`organizzazione`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eams`.`organizzazione` (
  `EventoID` CHAR(20) NOT NULL,
  `CodiceFiscaleDipendente` CHAR(16) NOT NULL,
  `SedeCittàDipendente` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`EventoID`, `CodiceFiscaleDipendente`, `SedeCittàDipendente`),
  INDEX `fk_evento_has_dipendente_dipendente1_idx` (`CodiceFiscaleDipendente` ASC, `SedeCittàDipendente` ASC) VISIBLE,
  INDEX `fk_evento_has_dipendente_evento1_idx` (`EventoID` ASC) VISIBLE,
  CONSTRAINT `fk_evento_has_dipendente_dipendente1`
    FOREIGN KEY (`CodiceFiscaleDipendente` , `SedeCittàDipendente`)
    REFERENCES `eams`.`dipendente` (`CodiceFiscale` , `SedeCittà`),
  CONSTRAINT `fk_evento_has_dipendente_evento1`
    FOREIGN KEY (`EventoID`)
    REFERENCES `eams`.`evento` (`EventoID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `eams`.`volontario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eams`.`volontario` (
  `CodiceFiscale` CHAR(16) NOT NULL,
  `SedeCittà` VARCHAR(25) NOT NULL,
  `DataIscrizione` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`CodiceFiscale`, `SedeCittà`),
  INDEX `fk_volontario_sede1_idx` (`SedeCittà` ASC) VISIBLE,
  INDEX `fk_volontario_persona1_idx` (`CodiceFiscale` ASC) VISIBLE,
  CONSTRAINT `fk_volontario_sede1`
    FOREIGN KEY (`SedeCittà`)
    REFERENCES `eams`.`sede` (`Città`),
  CONSTRAINT `fk_volontario_persona1`
    FOREIGN KEY (`CodiceFiscale`)
    REFERENCES `eams`.`persona` (`CodiceFiscale`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `eams`.`partecipazione`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eams`.`partecipazione` (
  `CodiceFiscaleVolontario` CHAR(16) NOT NULL,
  `SedeCittàVolontario` VARCHAR(25) NOT NULL,
  `OraInizioServizio` TIME NOT NULL,
  `EventoID` CHAR(20) NOT NULL,
  PRIMARY KEY (`CodiceFiscaleVolontario`, `SedeCittàVolontario`, `OraInizioServizio`, `EventoID`),
  INDEX `fk_volontario_has_servizio_servizio1_idx` (`OraInizioServizio` ASC, `EventoID` ASC) VISIBLE,
  INDEX `fk_volontario_has_servizio_volontario1_idx` (`CodiceFiscaleVolontario` ASC, `SedeCittàVolontario` ASC) VISIBLE,
  CONSTRAINT `fk_volontario_has_servizio_servizio1`
    FOREIGN KEY (`OraInizioServizio` , `EventoID`)
    REFERENCES `eams`.`servizio` (`OraInizio` , `EventoID`),
  CONSTRAINT `fk_volontario_has_servizio_volontario1`
    FOREIGN KEY (`SedeCittàVolontario`)
    REFERENCES `eams`.`volontario` (`SedeCittà`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `eams`.`produzione`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eams`.`produzione` (
  `OraInizioServizio` TIME NOT NULL,
  `EventoID` CHAR(20) NOT NULL,
  `Materiale` VARCHAR(30) NOT NULL,
  `Quantità(kg)` FLOAT NULL DEFAULT NULL,
  PRIMARY KEY (`OraInizioServizio`, `EventoID`, `Materiale`),
  INDEX `fk_servizio_has_rifiuto_rifiuto2_idx` (`Materiale` ASC) VISIBLE,
  INDEX `fk_servizio_has_rifiuto_servizio2_idx` (`OraInizioServizio` ASC, `EventoID` ASC) VISIBLE,
  CONSTRAINT `fk_servizio_has_rifiuto_rifiuto2`
    FOREIGN KEY (`Materiale`)
    REFERENCES `eams`.`rifiuto` (`Materiale`),
  CONSTRAINT `fk_servizio_has_rifiuto_servizio2`
    FOREIGN KEY (`OraInizioServizio` , `EventoID`)
    REFERENCES `eams`.`servizio` (`OraInizio` , `EventoID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `eams`.`raccolta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eams`.`raccolta` (
  `OraInizioServizio` TIME NOT NULL,
  `EventoID` CHAR(20) NOT NULL,
  `Materiale` VARCHAR(30) NOT NULL,
  `Quantità(kg)` FLOAT NULL DEFAULT NULL,
  PRIMARY KEY (`OraInizioServizio`, `EventoID`, `Materiale`),
  INDEX `fk_servizio_has_rifiuto_rifiuto1_idx` (`Materiale` ASC) VISIBLE,
  INDEX `fk_servizio_has_rifiuto_servizio1_idx` (`OraInizioServizio` ASC, `EventoID` ASC) VISIBLE,
  CONSTRAINT `fk_servizio_has_rifiuto_rifiuto1`
    FOREIGN KEY (`Materiale`)
    REFERENCES `eams`.`rifiuto` (`Materiale`),
  CONSTRAINT `fk_servizio_has_rifiuto_servizio1`
    FOREIGN KEY (`OraInizioServizio` , `EventoID`)
    REFERENCES `eams`.`servizio` (`OraInizio` , `EventoID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `eams`.`tesserasocio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eams`.`tesserasocio` (
  `IDsocio` VARCHAR(15) NOT NULL,
  `CodiceFiscale` CHAR(16) NOT NULL,
  `DataAssociazione` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`IDsocio`),
  INDEX `fk_tesserasocio_persona1_idx` (`CodiceFiscale` ASC) VISIBLE,
  CONSTRAINT `fk_tesserasocio_persona1`
    FOREIGN KEY (`CodiceFiscale`)
    REFERENCES `eams`.`persona` (`CodiceFiscale`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
