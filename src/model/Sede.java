package model;

import java.util.Objects;

public class Sede {
	private final String citt�;
    private final String indirizzo;
    private final String regione;
    private final String codicePostale;
    private final String telefono;
    
    public Sede(final String citt�, final String indirizzo, final String regione, final String codicePostale,
    		final String telefono) {
    	this.citt� = citt�;
    	this.indirizzo = indirizzo;
        this.regione = regione;
        this.codicePostale = codicePostale;
        this.telefono = telefono;
    }   
	
	public String getCitt�() {
		return this.citt�;
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
				.append("(").append(this.citt�).append(") ")
				.append(this.indirizzo).append(" ")
				.append(this.regione).append(" ")
				.append(this.codicePostale).append(" ")
				.append(this.telefono).append(" ").toString();
	}

	@Override
    public boolean equals(final Object other) {
        return (other instanceof Sede)
                && ((Sede) other).getCitt�().equals(this.getCitt�())
                && ((Sede) other).getIndirizzo().equals(this.getIndirizzo())
                && ((Sede) other).getRegione().equals(this.getRegione())
                && ((Sede) other).getCodicePostale().equals(this.getCodicePostale())
                && ((Sede) other).getTelefono().equals(this.getTelefono());
    }

	@Override
    public int hashCode() {
        return Objects.hash(this.citt�, this.indirizzo, this.regione, this.codicePostale, this.telefono);
    }
}
