package model;

import java.util.Date;
import java.util.Objects;

public class TesseraSocio {
	private final String idSocio;
    private final String codiceFiscale;
    private final Date dataAssociazione;
    
	public TesseraSocio(String idSocio, String codiceFiscale, Date dataAssociazione) {
		this.idSocio = idSocio;
		this.codiceFiscale = codiceFiscale;
		this.dataAssociazione = dataAssociazione;
	}

	public String getIdSocio() {
		return this.idSocio;
	}

	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}

	public Date getDataAssociazione() {
		return this.dataAssociazione;
	}
    
	@Override
    public String toString() {
        return new StringBuilder()
            .append("(").append(idSocio).append(") ")
			.append(codiceFiscale).append(" ")
			.append(dataAssociazione).toString();
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof TesseraSocio)
                && ((TesseraSocio) other).getIdSocio().equals(this.getIdSocio())
                && ((TesseraSocio) other).getCodiceFiscale().equals(this.getCodiceFiscale())
                && ((TesseraSocio) other).getDataAssociazione().equals(this.getDataAssociazione());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idSocio, this.codiceFiscale, this.dataAssociazione);
    }
}
