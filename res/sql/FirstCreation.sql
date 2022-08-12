-- -----------------------------------------------------
-- AZIENDA
-- -----------------------------------------------------
INSERT INTO azienda (partitaIVA, denominazioneSociale, telefono, indirizzo, codicePostale, città, regione, mail, fax) 
VALUES (12345678191,"Rainbow srl","0832-675104","piazza 4 maggio, 45","73120","Campi Salentina","Puglia","rainbowsrl@gmail.com","0832-675104"),
(12345678911,"Hera Recycle srl","0543-627897","via G. Garibaldi, 94","47122","Forlì","Emilia-Romagna","herarecycle@gmail.com","0543-627897"), 
(12346578911,"Trash tossers srl","0836-627897","via Taranto, 12","73100","Lecce","Puglia","trashtossers@gmail.com","0836-627897"),  
(12435678911,"GreenWash spa","0961-453415","via G. Garibaldi, 36","85544","Catanzaro","Calabria","greenwash@gmail.com","0961-453415"), 
(13245678911,"Paper Crafts spa","0774-796084","corso Mazzini, 52","00118","Roma","Lazio","papercrafts@gmail.com","0774-796084");
-- SELECT * FROM eams.azienda;

-- -----------------------------------------------------
-- CATEGORIA
-- -----------------------------------------------------
INSERT INTO categoria (nome, descrizione) 
VALUES ("Idee regalo","Regali ecosostenibili."),
("Abbigliamento",null),
("Cancelleria",null),
("Cura della persona",null),
("Cucina",null),
("Giardinaggio",null),
("Merchandise","Eco-gadget con il logo dell'associazione."),
("On the go","Usati spesso da portare fuori casa che di solito sono in plastica, usa e getta o non riciclabili.");
-- SELECT * FROM eams.categoria;

-- -----------------------------------------------------
-- EVENTO
-- -----------------------------------------------------
INSERT INTO evento (idEvento, nome, data, descrizione) 
VALUES ("e1","Giornata della terra","2023-04-22","Manifestazione in piazza con raccolte fondi per i progetti, stand di vendita,
 e attività di gioco e istruzione ambientale per bambini."), 
("e2","Spiagge pulite","2022-06-15",null), 
("e3","Giornata del leone","2022-08-10",null), 
("e4","Eco-photo run","2023-05-10","Raccolta fondi tramite offerta libera per partecipare a una maratona di foto green."), 
("e5","Friday for future","2022-03-25",null), 
("e6","Earth hour","2021-03-21","Spegnamo le luci per l'inquinamento luminoso."), 
("e7","Liberi dai rifiuti","2022-11-21","Raccolte rifiuti urbani, fabbricazione prodotti da materiale riciclabile.");
-- SELECT * FROM eams.evento;

-- -----------------------------------------------------
-- PERSONA
-- -----------------------------------------------------
INSERT INTO persona (codiceFiscale, nome, cognome, mail, telefono, indirizzo, codicePostale, città, regione, tipo) 
VALUES ("NDRCSD00A01C573B","Andrea","Casadei","andrecas@gmail.com","0543-80765","via A. Avogadro, 13","47122","Forlì","Emilia-Romagna","volontario"),
("CHRGTN88A01C352W","Chiara","Gatani","chiara88@gmail.com","0961-15640","via F. Tozzi, 56","85545","Catanzaro","Calabria",null), 
("CLDMTR70A01G479X","Claudio","Mastrati","claudio70@gmail.com","0721-10434","viale della Costituzione, 178","61121","Pesaro","Marche","dipendente"),  
("GNMRZN78A01B354R","Gianmarco","Oronzini","gianma78@gmail.com","0732-76078","piazza 20 settembre, 4","09134","Cagliari","Sardegna","dipendente"), 
("PLABNL91S14I608D","Paolo","Brunelli","brunellipaolo@gmail.com","0719-84515","corso Mazzini, 29","44207","Senigallia","Marche","volontario");
-- SELECT * FROM eams.persona;

-- -----------------------------------------------------
-- SEDE
-- -----------------------------------------------------
INSERT INTO sede (città, indirizzo, codicePostale, regione, telefono) 
VALUES ("Forlì","viale Franco, 45","47121","Emilia-Romagna","0543-86546"), 
("Catanzaro","via delle rocce, 273","85545","Calabria","0961-40864"), 
("Roma","piazzetta della misura, 76","00118","Lazio","0774-34837"), 
("Pesaro","via ponti, 23","61121","Marche","0721-01898");
-- SELECT * FROM eams.sede;

-- -----------------------------------------------------
-- DIPENDENTE
-- -----------------------------------------------------
INSERT INTO dipendente (codiceFiscale, sedeCittà, dataAssunzione, salario) 
VALUES ("CLDMTR70A01G479X","Pesaro","2021-01-01",1500.00), 
("GNMRZN78A01B354R","Catanzaro","2020-04-01",1200.00);
-- SELECT * FROM eams.dipendente;

-- -----------------------------------------------------
-- VOLONTARIO
-- -----------------------------------------------------
INSERT INTO volontario (codiceFiscale, sedeCittà, dataIscrizione) 
VALUES ("NDRCSD00A01C573B","Forlì","2021-05-17"), 
("PLABNL91S14I608D","Pesaro","2020-02-21");
-- SELECT * FROM eams.volontario;

-- -----------------------------------------------------
-- ORGANIZZAZIONE
-- -----------------------------------------------------
INSERT INTO organizzazione (codiceFiscaleDipendente, idEvento) 
VALUES ("CLDMTR70A01G479X","e1"), 
("GNMRZN78A01B354R","e2"), 
("CLDMTR70A01G479X","e3"), 
("GNMRZN78A01B354R","e4"), 
("GNMRZN78A01B354R","e5"), 
("GNMRZN78A01B354R","e6"), 
("CLDMTR70A01G479X","e7");
-- SELECT * FROM eams.organizzazione;

-- -----------------------------------------------------
-- TESSERASOCIO
-- -----------------------------------------------------
INSERT INTO tesserasocio (idSocio, codiceFiscale, dataAssociazione) 
VALUES ("SOC0000001","CHRGTN88A01C352W","2022-05-24"), 
("SOC0000002","PLABNL91S14I608D","2021-12-07");
-- SELECT * FROM eams.tesserasocio;

-- -----------------------------------------------------
-- PRODOTTO
-- -----------------------------------------------------
INSERT INTO prodotto (idProdotto, categoria, nome, prezzo, quantitàImmagazzinata, provenienza, descrizione)
VALUES ("DG56","On the go","Bottiglia di acciaio blu",20.00,30,"Fornito",null), 
("W79R","On the go","Bottiglia di acciaio rosa",20.00,30,"Fornito",null), 
("NT39","On the go","Bottiglia di acciaio grigia",20.00,30,"Fornito",null), 
("RB87","On the go","Bottiglia di acciaio nera",20.00,30,"Fornito",null), 
("EC51","Abbigliamento","Cappello da baseball",20.00,30,"Fornito","Logo sulla parte frontale."), 
("F001","Merchandise","Braccialetto turtle",7.00,50,"Fabbricato","Plastica riciclata, ciondolo tartaruga."), 
("F002","Merchandise","Braccialetto dolphin",7.00,50,"Fabbricato","Plastica riciclata, ciondolo delfino."), 
("F003","Merchandise","Collana turtle",10.00,40,"Fabbricato","Plastica riciclata, ciondolo tartaruga."), 
("F005","Merchandise","Collana dolphin",10.00,40,"Fabbricato","Plastica riciclata, ciondolo delfino."), 
("JW49","Idee regalo","Sacchetto di tela",5.50,50,"Fornito",null), 
("PA73","On the go","Set posate in bamboo",15.00,25,"Fornito",null), 
("HG46","Cura della persona","Spazzolino bamboo",20.00,30,"Fornito","Set da 7 spazzolini.");
-- SELECT * FROM eams.prodotto;

-- -----------------------------------------------------
-- PROGETTO
-- -----------------------------------------------------
INSERT INTO progetto (obbiettivo, dataInizio, durataMesi, descrizione) 
VALUES ("Creare un rifugio per animali",01/10/2022,6,null), 
("Ecofishing",04/04/2021,12,"Programma di protezione dell’ambiente marino, attraverso la raccolta, il recupero e il riciclo delle reti da pesca abbandonate 
e l’implementazione di un programma di pesca sostenibile."), 
("Rain-forest",10/08/2022,18,"Piano di reinforestazione delle aree più colpite dal disboscamento in Italia."), 
("Città verde",17/01/2022,12,"Rendere più verdi le zone urbane."), 
("Leopardo delle nevi",22/03/2022,12,"Ridurre il bracconaggio dei leopardi delle nevi, ricostruzione di habitat favorevoli."), 
("Tour in bosco",01/09/2022,9,"Progetto di educazione ambientale tramite tour nei luoghi naturali più prossimi per le scuole elementari e medie."),
("Elefanti",22/08/2022,12,"Ridurre il bracconaggio.");
-- SELECT * FROM eams.progetto;*/

-- -----------------------------------------------------
-- DONAZIONE
-- -----------------------------------------------------
INSERT INTO donazione (importo, codiceFiscale, dataDonazione, idProgetto) 
VALUES (20.00,"CHRGTN88A01C352W","2022-05-24",4), 
(20.00,"CHRGTN88A01C352W","2022-06-24",4), 
(5.00,"PLABNL91S14I608D","2022-06-29",null), 
(20.00,"CHRGTN88A01C352W","2022-07-24",4);
-- SELECT * FROM eams.donazione;

-- -----------------------------------------------------
-- SERVIZIO
-- -----------------------------------------------------
INSERT INTO servizio (idServizio, idEvento, oraInizio, oraFine, tipo, idProgetto) 
VALUES ("S1-1","e1",150000,180000,"Stand di vendita",null), 
("S2-1","e3",173000,180000,"Attività ludico-istruttiva",null), 
("S1-2","e1",170000,190000,"Raccolta fondi",null), 
("S4-1","e7",100000,130000,"Raccolta rifiuti",null), 
("S5-1","e4",163000,190000,"Raccolta fondi",null), 
("S4-3","e7",150000,170000,"Fabbricazione prodotti",null), 
("S4-2","e7",150000,170000,"Raccolta rifiuti",null);
-- SELECT * FROM eams.servizio;

-- -----------------------------------------------------
-- PARTECIPAZIONE
-- -----------------------------------------------------
INSERT INTO partecipazione (codiceFiscaleVolontario, idServizio) 
VALUES ("NDRCSD00A01C573B", "S1-1"), 
("PLABNL91S14I608D", "S1-2"), 
("NDRCSD00A01C573B", "S4-1"), 
("NDRCSD00A01C573B", "S5-1"), 
("PLABNL91S14I608D", "S5-1");
-- SELECT * FROM eams.partecipazione;

-- -----------------------------------------------------
-- NEWSLETTER
-- -----------------------------------------------------
INSERT INTO newsletter (argomento, descrizione) 
VALUES ("Ambiente", null),
("Animali in estinzione", null),
("Inquinamento", null),
("Junior","Una newsletter dedicata ai più piccoli."),
("Quiz", null),
("Novità sui progetti", null),
("Notizie verdi", null),
("Curiosità ed eco-consigli", null),
("Crimini di natura", null);
-- SELECT * FROM eams.newsletter;

-- -----------------------------------------------------
-- ISCRIZIONE
-- -----------------------------------------------------
INSERT INTO iscrizione (codiceFiscale, idNewsletter) 
VALUES ("CHRGTN88A01C352W",7), 
("CHRGTN88A01C352W",6), 
("CHRGTN88A01C352W",9), 
("CHRGTN88A01C352W",2), 
("GNMRZN78A01B354R",3), 
("GNMRZN78A01B354R",1), 
("GNMRZN78A01B354R",6);
-- SELECT * FROM eams.iscrizione;

-- -----------------------------------------------------
-- RIFIUTO
-- -----------------------------------------------------
INSERT INTO rifiuto (materiale, tipo, kgImagazzinati, note) VALUES ("Termoplastiche","Riciclabile",260.87,"1 - PET (polietilene tereftalato), 
2 - HDPE (polietilene ad alta densità), 3 - PVC O V (cloruro di polivinile), 4 - LDPE (polietilene a bassa densità), 5 - PP (polipropilene), 
6 - PS (polistirene o polistirolo)"), 
("Plastiche termoindurenti","Non riciclabile",322.54,"7 - Altre plastiche"), 
("Carta","Riciclabile",154.65,null), 
("Vetro","Riciclabile",134.78,null);
-- SELECT * FROM eams.rifiuto;

-- -----------------------------------------------------
-- RACCOLTA
-- -----------------------------------------------------
INSERT INTO raccolta (idServizio, materiale, kg) 
VALUES ("S4-1","Carta",25.30), 
("S4-1","Termoplastiche",10.02), 
("S4-1","Plastiche termoindurenti",30.40), 
("S4-2","Termoplastiche",18.02), 
("S4-2","Vetro",20.92), 
("S4-2","Carta",25.73);
-- SELECT * FROM eams.raccolta;

-- -----------------------------------------------------
-- CONSEGNA
-- -----------------------------------------------------
INSERT INTO consegna (materiale, partitaIVA, data, kgConsegnati) 
VALUES ("Termoplastiche", 12346578911,"2022-08-11",10.50), 
("Termoplastiche", 12345678911,"2022-07-07",5.70), 
("Carta", 12346578911,"2022-04-11",19.20), 
("Termoplastiche", 12346578911,"2022-08-27",8.40), 
("Termoplastiche", 12345678911,"2022-02-23",13.40);
-- SELECT * FROM eams.consegna;

-- -----------------------------------------------------
-- FORNITURA
-- -----------------------------------------------------
INSERT INTO fornitura (idProdotto, partitaIVA, data, quantitàFornita) 
VALUES ("DG56","12435678911","2022-01-19",30), 
("PA73","12345678191","2022-02-05",10), 
("NT39","12435678911","2022-04-19",15);
-- SELECT * FROM eams.fornitura;

-- -----------------------------------------------------
-- PRODUZIONE
-- -----------------------------------------------------
INSERT INTO produzione (idServizio, idProdotto, quantitàProdotta, materialeUsato, kgRifiutiUsati) 
VALUES ("S4-3","F002",13,"Termoplastiche",10.70), 
("S4-3","F001", 15, "Termoplastiche",13.52);
-- SELECT * FROM eams.produzione;

-- -----------------------------------------------------
-- VENDITA
-- -----------------------------------------------------
INSERT INTO vendita (idProdotto, idServizio, codiceFiscaleCliente, quantità) 
VALUES ("DG56","S1-1","GNMRZN78A01B354R",1), 
("EC51","S1-1","CLDMTR70A01G479X",2), 
("DG56","S1-1","NDRCSD00A01C573B",1), 
("F003","S1-1","CHRGTN88A01C352W",4);
-- SELECT * FROM eams.vendita;

-- -----------------------------------------------------
-- UTENTE
-- -----------------------------------------------------
INSERT INTO utente (codiceFiscaleDipendente, username, password) 
VALUES ("CLDMTR70A01G479X", "claudio", "claudio22"), 
("GNMRZN78A01B354R", "gianmarco", "gianmarco22");
-- SELECT * FROM eams.utente;




