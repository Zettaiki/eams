package model;

import java.util.Objects;

public class Iscrizione {
	private final String codiceFiscale;
	private final Integer idNewsletter;
    
	public Iscrizione(String codiceFiscale, Integer idNewsletter) {
		this.codiceFiscale = codiceFiscale;
		this.idNewsletter = idNewsletter;
	}
	
	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}
	   
	public Integer getIdNewsletter() {
		return this.idNewsletter;
	}

	@Override
    public String toString() {
        return new StringBuilder()
            .append("(").append(codiceFiscale).append(") ")
            .append("(").append(idNewsletter).append(")").toString();
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof Iscrizione)                
                && ((Iscrizione) other).getCodiceFiscale().equals(this.getCodiceFiscale())
                && ((Iscrizione) other).getIdNewsletter().equals(this.getIdNewsletter());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.codiceFiscale, this.idNewsletter);
    }
}
