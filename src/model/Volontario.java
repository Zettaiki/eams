package model;

import java.util.Date;
import java.util.Objects;

public class Volontario {
	private final String codiceFiscale;
	private final String sedeCittà;
    private final Date dataIscrizione;
    
	public Volontario(String codiceFiscale, String sedeCittà, Date dataIscrizione) {
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
        return (other instanceof Volontario)
                && ((Volontario) other).getCodiceFiscale().equals(this.getCodiceFiscale())
                && ((Volontario) other).getSedeCittà().equals(this.getSedeCittà())
                && ((Volontario) other).getDataIscrizione().equals(this.getDataIscrizione());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.codiceFiscale, this.sedeCittà, this.dataIscrizione);
    }
}
