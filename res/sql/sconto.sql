CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `sconto` AS
    SELECT DISTINCT
        `p`.`codiceFiscale` AS `codiceFiscale`,
        IFNULL((SELECT 
                        20.00
                    FROM DUAL WHERE
                        `p`.`codiceFiscale` IN (SELECT 
                                `tesserasocio`.`codiceFiscale`
                            FROM
                                `tesserasocio`)),
                0.00) AS `sconto`
    FROM
        (`persona` `p`
        JOIN `tesserasocio` `ts`)