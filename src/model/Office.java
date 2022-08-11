package model;

import java.util.Objects;

public class Office {
	private final String città;
    private final String indirizzo;
    private final String regione;
    private final String codicePostale;
    private final String telefono;
    
    public Office(final String città, final String indirizzo, final String regione, final String codicePostale,
    		final String telefono) {
    	this.città = città;
    	this.indirizzo = indirizzo;
        this.regione = regione;
        this.codicePostale = codicePostale;
        this.telefono = telefono;
    }   
	
	public String getCittà() {
		return this.città;
	}

	public String getIndirizzo() {
		return this.indirizzo;
	}

	public String getRegione() {
		return this.regione;
	}

	public String getCodicePostale() {
		return this.codicePostale;
	}
	
	public String getTelefono() {
		return this.telefono;
	}

	@Override
	public String toString() {
		return new StringBuilder()
				.append("(").append(this.città).append(") ")
				.append(this.indirizzo).append(" ")
				.append(this.regione).append(" ")
				.append(this.codicePostale).append(" ")
				.append(this.telefono).append(" ").toString();
	}

	@Override
    public boolean equals(final Object other) {
        return (other instanceof Office)
                && ((Office) other).getCittà().equals(this.getCittà())
                && ((Office) other).getIndirizzo().equals(this.getIndirizzo())
                && ((Office) other).getRegione().equals(this.getRegione())
                && ((Office) other).getCodicePostale().equals(this.getCodicePostale())
                && ((Office) other).getTelefono().equals(this.getTelefono());
    }

	@Override
    public int hashCode() {
        return Objects.hash(this.città, this.indirizzo, this.regione, this.codicePostale, this.telefono);
    }
}
