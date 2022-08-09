package model;

import java.util.Date;
import java.util.Objects;

public class Volunteer {
	private final String codiceFiscale;
	private final String sedeCitt�;
    private final Date dataIscrizione;
    
	public Volunteer(String codiceFiscale, String sedeCitt�, Date dataIscrizione) {
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
        return (other instanceof Volunteer)
                && ((Volunteer) other).getCodiceFiscale().equals(this.getCodiceFiscale())
                && ((Volunteer) other).getSedeCitt�().equals(this.getSedeCitt�())
                && ((Volunteer) other).getDataIscrizione().equals(this.getDataIscrizione());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.codiceFiscale, this.sedeCitt�, this.dataIscrizione);
    }
}
