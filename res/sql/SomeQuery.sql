-- 1	Controllo progetti attivi
SELECT * FROM progetto p
WHERE p.dataInizio >= current_date();
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
SELECT s.idServizio, s.oraInizio, s.oraFine, s.tipo, s.idProgetto, s.idEvento ,e.data
FROM servizio s, evento e
WHERE e.idEvento = s.idEvento 
AND s.tipo = "Stand di vendita";
-- 5	classifica delle newsletter più seguite
SELECT *, COUNT(*) as iscritti
FROM iscrizione i
GROUP BY i.idNewsletter
ORDER BY Iscritti DESC;
-- 7	volontario con > ore di servizio
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
/********	TROVARE MODO MIGLIORE e se riesco mostrare anche nome donatore anziché solo cf **********/
SELECT maxDonazione.codiceFiscale, MAX(maxDonazione.maxDonato)
FROM (SELECT *, SUM(d.importo) AS maxDonato
		FROM donazione d
		WHERE d.dataDonazione BETWEEN DATE_SUB(NOW(),INTERVAL 1 YEAR) AND CURRENT_DATE()
		GROUP BY d.codiceFiscale) as maxDonazione;
        




-- 9	elenco volontari per una certa sede
SELECT * FROM volontario v
WHERE v.sedeCittà = ?;
-- 10	percentuali e totale donazioni rivolte ad ogni progetto
-- 11	quantità venduta di un prodotto in un mese






-- 15	lista donatori di un progetto
SELECT DISTINCT d.codiceFiscale 
FROM donazione d
WHERE d.idProgetto = 4;
-- 16	acquisto prodotto (sconto su prezzo se socio acquista)
-- 17	media annuale quantità rifiuti raccolti (tramite date eventi risaliamo per la query, valutare analisi ridondanze)
-- 18	sede con volontari più partecipativi








