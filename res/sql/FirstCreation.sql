-- -----------------------------------------------------
-- AZIENDA
-- -----------------------------------------------------
/*INSERT INTO azienda (partitaIVA, denominazioneSociale, telefono, indirizzo, città, regione, codicePostale, mail, fax) 
VALUES (12345678191,"Rainbow srl","0832-675104","piazza 4 maggio","Campi Salentina","Puglia","73120","rainbowsrl@gmail.com","0832-675104"),
(12345678911,"Hera Recycle srl","0543-627897","via G. Garibaldi","Forlì","Emilia-Romagna","47122","herarecycle@gmail.com","0543-627897"), 
(12346578911,"Trash tossers srl","0836-627897","via Taranto","Lecce","Puglia","73100","trashtossers@gmail.com","0836-627897"),  
(12435678911,"GreenWash spa","0961-453415","via G. Garibaldi","Catanzaro","Calabria","85544","greenwash@gmail.com","0961-453415"), 
(13245678911,"Paper Crafts spa","0774-796084","corso Mazzini","Roma","Lazio","00118","papercrafts@gmail.com","0774-796084");
SELECT * FROM eams.azienda;*/

-- -----------------------------------------------------
-- CATEGORIA
-- -----------------------------------------------------
/*INSERT INTO categoria (nome, descrizione) 
VALUES ("Idee regalo","Regali ecosostenibili."),
("Abbigliamento",null),
("Cancelleria",null),
("Cura della persona",null),
("Cucina",null),
("Giardinaggio",null),
("Merchandise","Eco-gadget con il logo dell'associazione."),
("On the go","Usati spesso da portare fuori casa che di solito sono in plastica, usa e getta o non riciclabili.");
SELECT * FROM eams.categoria;*/

-- -----------------------------------------------------
-- PROGETTO
-- -----------------------------------------------------
/**/
INSERT INTO progetto (obbiettivo, dataInizio, durataMesi, descrizione) 
VALUES ("Creare un rifugio per animali",01/10/2022,6,null), 
("Ecofishing",04/04/2021,12,"Programma di protezione dell’ambiente marino, attraverso la raccolta, il recupero e il riciclo delle reti da pesca abbandonate 
e l’implementazione di un programma di pesca sostenibile."), 
("Rain-forest",10/08/2022,18,"Piano di reinforestazione delle aree più colpite dal disboscamento in Italia."), 
("Città verde",17/01/2022,12,"Rendere più verdi le zone urbane."), 
("Leopardo delle nevi",22/03/2022,12,"Ridurre il bricconaggio dei leopardi delle nevi."), 
("Tour in bosco","01/09/2022",9,"Progetto di educazione ambientale tramite tour nei luoghi naturali più prossimi per le scuole elementari e medie.");


SELECT * FROM eams.progetto;




-- -----------------------------------------------------
-- PERSONA
-- -----------------------------------------------------
/*INSERT INTO persona (codiceFiscale, nome, cognome, mail, telefono, indirizzo, città, regione, codicePostale, tipo) 
VALUES ("NDRCSD00A01C573B","Andrea","Casadei","andrecas@gmail.com","0543-80765","via A. Avogadro","Forlì","Emilia-Romagna","47122","volontario"),
("CHRGTN88A01C352W","Chiara","Gatani","chiara88@gmail.com","0961-15640","via F. Tozzi","Catanzaro","Calabria","85545",null), 
("CLDMTR70A01G479X","Claudio","Mastrati","claudio70@gmail.com","0721-10434","viale della Costituzione","Pesaro","Marche","61121","dipendente"),  
("GNMRZN78A01B354R","Gianmarco","Oronzini","gianma78@gmail.com","0732-76078","piazza 20 settembre","Cagliari","Sardegna","09134","dipendente"), 
("PLABNL91S14I608D","Paolo","Brunelli","brunellipaolo@gmail.com","0719-84515","corso Mazzini","Senigallia","Marche","44207","volontario");
SELECT * FROM eams.persona;*/



-- -----------------------------------------------------
-- NEWSLETTER
-- -----------------------------------------------------
/*INSERT INTO newsletter (argomento, descrizione) VALUES ("Ambiente", null),
("Animali in estinzione", null),
("Inquinamento", null),
("Junior","Una newsletter dedicata ai più piccoli."),
("Quiz", null),
("Novità sui progetti", null),
("Notizie verdi", null),
("Curiosità ed eco-consigli", null),
("Crimini di natura", null);
SELECT * FROM eams.newsletter;*/





























-- -----------------------------------------------------
-- CONSEGNA
-- -----------------------------------------------------




-- SELECT * FROM eams.consegna;
