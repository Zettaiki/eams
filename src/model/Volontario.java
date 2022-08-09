package model;

import java.util.Date;
import java.util.Objects;

public class Volontario {
	private final String codiceFiscale;
	private final String sedeCitt�;
    private final Date dataIscrizione;
    
	public Volontario(String codiceFiscale, String sedeCitt�, Date dataIscrizione) {
		this.codiceFiscale = codiceFiscale;
		this.sedeCitt� = sedeCitt�;
		this.dataIscrizione = dataIscrizione;
	}
	
	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}

	public String getSedeCitt�() {
		return this.sedeCitt�;
	}

	public Date getDataIscrizione() {
		return this.dataIscrizione;
	}
    
	@Override
    public String toString() {
        return new StringBuilder()
            .append("(").append(codiceFiscale).append(") ")
			.append(sedeCitt�).append(" ")
			.append(dataIscrizione).toString();
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof Volontario)
                && ((Volontario) other).getCodiceFiscale().equals(this.getCodiceFiscale())
                && ((Volontario) other).getSedeCitt�().equals(this.getSedeCitt�())
                && ((Volontario) other).getDataIscrizione().equals(this.getDataIscrizione());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.codiceFiscale, this.sedeCitt�, this.dataIscrizione);
    }
}
