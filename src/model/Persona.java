package model;

import java.util.Objects;

public class Persona {
	private final String codiceFiscale;
    private final String nome;
    private final String cognome;
    private final String telefono;
    private final String indirizzo;
    private final String città;
    private final String regione;
    private final String codicePostale;
    private final String tipo;
    
    public Persona(final String codiceFiscale, final String nome, final String cognome, final String telefono,
    		final String indirizzo, final String città, final String regione, final String codicePostale, final String tipo) {
        this.codiceFiscale = codiceFiscale;
        this.nome = nome;
        this.cognome = cognome;
        this.telefono = telefono;
        this.indirizzo = indirizzo;
        this.città = città;
        this.regione = regione;
        this.codicePostale = codicePostale;
		this.tipo = tipo;
    }
    
    //DECIDERE QUALI CAMPI NULLABLE
    /*public Persona(final String codiceFiscale, final String nome, final String cognome, final String telefono,
    		final String indirizzo, final String città, final String regione, final String codicePostale, final String tipo) {
        this(codiceFiscale, nome, cognome, telefono, indirizzo, città, regione, codicePostale, tipo);
    }*/
    
    public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public String getCittà() {
		return città;
	}

	public String getRegione() {
		return regione;
	}

	public String getCodicePostale() {
		return codicePostale;
	}

	public String getTipo() {
		return tipo;
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
				.append("(").append(codiceFiscale).append(") ")
				.append(nome).append(" ")
				.append(cognome).append(" ")
				.append(indirizzo).append(" ")
				.append(città).append(" ")
				.append(regione).append(" ")
				.append(codicePostale).append(" ")
				.append(tipo).toString();
	}

	@Override
    public boolean equals(final Object other) {
        return (other instanceof Persona)
                && ((Persona) other).getCodiceFiscale().equals(this.getCodiceFiscale())
                && ((Persona) other).getNome().equals(this.getNome())
                && ((Persona) other).getCognome().equals(this.getCognome())
                && ((Persona) other).getTelefono().equals(this.getTelefono())
                && ((Persona) other).getIndirizzo().equals(this.getIndirizzo())
                && ((Persona) other).getCittà().equals(this.getCittà())
                && ((Persona) other).getRegione().equals(this.getRegione())
                && ((Persona) other).getCodicePostale().equals(this.getCodicePostale())
                && ((Persona) other).getTipo().equals(this.getTipo());
    }

    // no hashcode method?
	@Override
    public int hashCode() {
        return Objects.hash(codiceFiscale, nome, cognome, telefono, indirizzo, città, regione, codicePostale, tipo);
    }
}
