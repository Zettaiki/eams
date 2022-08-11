package model;

import java.util.Date;
import java.util.Objects;

public class Volunteer {
	private final String codiceFiscale;
	private final String sedeCittà;
    private final Date dataIscrizione;
    
	public Volunteer(String codiceFiscale, String sedeCittà, Date dataIscrizione) {
		this.codiceFiscale = codiceFiscale;
		this.sedeCittà = sedeCittà;
		this.dataIscrizione = dataIscrizione;
	}
	
	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}

	public String getSedeCittà() {
		return this.sedeCittà;
	}

	public Date getDataIscrizione() {
		return this.dataIscrizione;
	}
    
	@Override
    public String toString() {
        return new StringBuilder()
            .append("(").append(codiceFiscale).append(") ")
			.append(sedeCittà).append(" ")
			.append(dataIscrizione).toString();
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof Volunteer)
                && ((Volunteer) other).getCodiceFiscale().equals(this.getCodiceFiscale())
                && ((Volunteer) other).getSedeCittà().equals(this.getSedeCittà())
                && ((Volunteer) other).getDataIscrizione().equals(this.getDataIscrizione());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.codiceFiscale, this.sedeCittà, this.dataIscrizione);
    }
}
