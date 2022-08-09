package model;

import java.util.Date;
import java.util.Objects;

public class Volontario {
	private final String codiceFiscale;
	private final String sedeCittą;
    private final Date dataIscrizione;
    
	public Volontario(String codiceFiscale, String sedeCittą, Date dataIscrizione) {
		this.codiceFiscale = codiceFiscale;
		this.sedeCittą = sedeCittą;
		this.dataIscrizione = dataIscrizione;
	}
	
	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}

	public String getSedeCittą() {
		return this.sedeCittą;
	}

	public Date getDataIscrizione() {
		return this.dataIscrizione;
	}
    
	@Override
    public String toString() {
        return new StringBuilder()
            .append("(").append(codiceFiscale).append(") ")
			.append(sedeCittą).append(" ")
			.append(dataIscrizione).toString();
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof Volontario)
                && ((Volontario) other).getCodiceFiscale().equals(this.getCodiceFiscale())
                && ((Volontario) other).getSedeCittą().equals(this.getSedeCittą())
                && ((Volontario) other).getDataIscrizione().equals(this.getDataIscrizione());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.codiceFiscale, this.sedeCittą, this.dataIscrizione);
    }
}
