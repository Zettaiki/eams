package model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

public class Donazione {
	private final Integer idDonazione;
    private final BigDecimal importo;
    private final String codiceFiscale;
    private final Date dataDonazione;
    private final Optional<Integer> idProgetto;
    
	public Donazione(BigDecimal importo, String codiceFiscale, Date dataDonazione, Optional<Integer> idProgetto) {
		this.idDonazione = 0;
		this.importo = importo;
		this.codiceFiscale = codiceFiscale;
		this.dataDonazione = dataDonazione;
		this.idProgetto = Objects.requireNonNull(idProgetto);
	}
	
	public Donazione(BigDecimal importo, String codiceFiscale, Date dataDonazione) {
		this(importo, codiceFiscale, dataDonazione, Optional.empty());
	}
    
	public Integer getIdDonazione() {
		return this.idDonazione;
	}

	public BigDecimal getImporto() {
		return this.importo;
	}

	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}

	public Date getDataDonazione() {
		return this.dataDonazione;
	}

	public Optional<Integer> getIdProgetto() {
		return this.idProgetto;
	}

	@Override
    public String toString() {
        return new StringBuilder()
            .append("(").append(idDonazione).append(") ")
			.append(importo).append(" ")
			.append(codiceFiscale).append(" ")
			.append(dataDonazione).append(" ")
			.append(idProgetto).toString();
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof Donazione)
                && ((Donazione) other).getIdDonazione().equals(this.getIdDonazione())
                && ((Donazione) other).getImporto().equals(this.getImporto())
                && ((Donazione) other).getCodiceFiscale().equals(this.getCodiceFiscale())
                && ((Donazione) other).getDataDonazione().equals(this.getDataDonazione())
                && ((Donazione) other).getIdProgetto().equals(this.getIdProgetto());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idDonazione, this.importo, this.codiceFiscale, this.dataDonazione, this.idProgetto);
    }
}
