-- 1	Controllo progetti attivi
SELECT * FROM progetto p
WHERE current_date() BETWEEN p.dataInizio AND p.dataFine;

-- 2	Mostra eventi attivi
SELECT * FROM evento e
WHERE e.data >= current_date();

-- 3	Elenco servizi di un evento
SELECT * FROM servizio s
WHERE s.idEvento = ?; -- es. input "e3"

-- 4	Cerca servizi attivi per tipo (mostra anche data evento)
/*SELECT s.*, e.idEvento, e.data
FROM servizio s
JOIN evento e ON (e.idEvento = s.idEvento)
WHERE s.tipo = "Stand di vendita";*/
SELECT s.idServizio, s.oraInizio, s.oraFine, s.idProgetto, s.idEvento, e.data
FROM servizio s, evento e
WHERE e.idEvento = s.idEvento 
AND s.tipo = "Stand di vendita";

-- 5	classifica delle newsletter più seguite
SELECT i.idNewsletter, COUNT(*) as iscritti
FROM iscrizione i
GROUP BY i.idNewsletter
ORDER BY iscritti DESC;

-- 7	volontario con > ore di servizio
/********/
/*SELECT *, COUNT(*) as oreServizioTot
FROM partecipazione p
GROUP BY p.idServizio
ORDER BY oreServizioTot DESC;*/

/*SELECT *
FROM partecipazione p
GROUP BY p.codiceFiscaleVolontario IN (SELECT *
									   FROM
									   )*/

/********** ore di servizio per ogni servizio

SELECT *, TIMEDIFF(s.oraFine,s.oraInizio) AS OreServizio
FROM servizio s*/

SELECT p.codiceFiscaleVolontario, COUNT(*) AS OreServizioTot
FROM partecipazione p
WHERE p.idServizio IN (SELECT TIMEDIFF(s.oraFine,s.oraInizio) AS OreServizio
						FROM servizio s
                        WHERE p.idServizio = s.idServizio)
GROUP BY p.codiceFiscaleVolontario
ORDER BY OreServizioTot DESC;

-- 8	donatore > quantità donata in un certo anno
SELECT maxDonazione.codiceFiscale, p.nome, p.cognome, MAX(maxDonazione.maxDonato) AS importoMaxDonato
FROM (SELECT *, SUM(d.importo) AS maxDonato
		FROM donazione d
		WHERE d.dataDonazione BETWEEN DATE_SUB(NOW(),INTERVAL 1 YEAR) AND CURRENT_DATE()
		GROUP BY d.codiceFiscale) as maxDonazione, persona p
WHERE p.codiceFiscale = maxDonazione.codiceFiscale;

-- 9	elenco volontari per una certa sede
SELECT * FROM volontario v
WHERE v.sedeCittà = ?;

-- 10	percentuali e totale donazioni rivolte ad ogni progetto 
SELECT p.idProgetto, SUM(d.importo) AS donazioniProgetto, concat(round((SUM(d.importo)/(
	SELECT SUM(d.importo) as totDonazioni
	FROM donazione d
    WHERE d.idProgetto IS NOT NULL
) * 100 ),2),'%') AS percentuale
FROM donazione AS d JOIN progetto AS p ON d.idProgetto = p.idProgetto
GROUP BY p.idProgetto
ORDER BY percentuale DESC;

-- 10 bis (same but percentuale tot è dei progetti attivi)
SELECT p.idProgetto, SUM(d.importo) AS donazioniProgetto, concat(round((SUM(d.importo)/(
	SELECT SUM(d.importo) as totDonazioni
	FROM donazione d
    WHERE d.idProgetto IS NOT NULL
) * 100 ),2),'%') AS percentuale
FROM donazione AS d JOIN progetto AS p ON d.idProgetto = p.idProgetto
WHERE p.dataInizio >= current_date()
GROUP BY p.idProgetto
ORDER BY percentuale DESC;

-- 11	quantità venduta di un prodotto in un mese
/********/

-- 15	lista donatori di un progetto
SELECT DISTINCT d.codiceFiscale, p.nome, p.cognome
FROM donazione d JOIN persona AS p ON d.codiceFiscale = p.codiceFiscale
WHERE d.idProgetto = ?;

-- 16	acquisto prodotto (sconto su prezzo se socio acquista)
/**** è  la save quindi già fatta ****/
/*SELECT IFNULL((SELECT 20.00 WHERE 'CHRGTN88A01C352W' IN (SELECT codiceFiscale FROM tesserasocio)),0.00);
SELECT IFNULL((SELECT 20.00 WHERE 'NDRCSD00A01C573B' IN (SELECT codiceFiscale FROM tesserasocio)),0.00);

-- IF tesserasocio.codiceFiscale = vendita.codiceFiscaleCliente THEN 20.00 ELSE 00.00;

SELECT (CASE WHEN (tesserasocio.codiceFiscale = vendita.codiceFiscaleCliente) 
 THEN
      20.00 
 ELSE
      00.00 
 END) AS bo
 FROM vendita as v, tesserasocio as ts;*/

-- 16 bis mostra vendite con prezzo di vendita (uso query perché non ci serve memo nel db e view perché non sono riuscita diverso)
-- prezzo di vendita senza sconto, sconto e prezzo scontato
SELECT v.idProdotto, v.codiceFiscaleCliente, (v.quantità * p.prezzo) AS prezzoVendita, s.sconto, 
(((p.prezzo / 100) * (100-s.sconto))*v.quantità) AS prezzoScontato
FROM vendita v, prodotto p, sconto s
WHERE p.idProdotto = v.idProdotto
AND s.codiceFiscale = v.codiceFiscaleCliente;

SELECT *
FROM sconto;



-- 17	media annuale quantità rifiuti raccolti (tramite date eventi risaliamo per la query, valutare analisi ridondanze)
/********/
-- 18	sede con volontari più partecipativi
/********/








