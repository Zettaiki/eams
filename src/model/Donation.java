package model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

public class Donation {
	private final Optional<Integer> idDonazione;
    private final BigDecimal importo;
    private final String codiceFiscale;
    private final Date dataDonazione;
    private final String tipo;
    private final Optional<Integer> idProgetto;
    
	public Donation(Optional<Integer> idDonazione, BigDecimal importo, String codiceFiscale, Date dataDonazione, String tipo, Optional<Integer> idProgetto) {
		this.idDonazione = idDonazione;
		this.importo = importo;
		this.codiceFiscale = codiceFiscale;
		this.dataDonazione = dataDonazione;
		this.tipo = tipo;
		this.idProgetto = Objects.requireNonNull(idProgetto);
	}
	
	public Donation(Optional<Integer> idDonazione, BigDecimal importo, String codiceFiscale, String tipo, Date dataDonazione) {
		this(idDonazione, importo, codiceFiscale, dataDonazione, tipo, Optional.empty());
	}
    
	public Integer getIdDonazione() {
		if (this.idDonazione.isPresent())
			return this.idDonazione.get();
		return -1;
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
	
	public String getTipo() {
		return this.tipo;
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
			.append(tipo).append(" ")
			.append(idProgetto).toString();
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof Donation)
                && ((Donation) other).getIdDonazione().equals(this.getIdDonazione())
                && ((Donation) other).getImporto().equals(this.getImporto())
                && ((Donation) other).getCodiceFiscale().equals(this.getCodiceFiscale())
                && ((Donation) other).getDataDonazione().equals(this.getDataDonazione())
                && ((Donation) other).getTipo().equals(this.getTipo())
                && ((Donation) other).getIdProgetto().equals(this.getIdProgetto());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idDonazione, this.importo, this.codiceFiscale, this.dataDonazione, this.tipo, this.idProgetto);
    }
}
