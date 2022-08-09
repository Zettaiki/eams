package model;

import java.util.Objects;
import java.util.Optional;

public class Person {
	private final String codiceFiscale;
    private final String nome;
    private final String cognome;
    private final String mail;
    private final Optional<String> telefono;
    private final String indirizzo;
    private final String città;
    private final String regione;
    private final String codicePostale;
    private final Optional<String> tipo;
    
    public Person(final String codiceFiscale, final String nome, final String cognome, final String mail, final Optional<String> telefono,
    		final String indirizzo, final String città, final String regione, final String codicePostale, final Optional<String> tipo) {
        this.codiceFiscale = codiceFiscale;
        this.nome = nome;
        this.cognome = cognome;
        this.mail = mail;
        this.telefono = Objects.requireNonNull(telefono);
        this.indirizzo = indirizzo;
        this.città = città;
        this.regione = regione;
        this.codicePostale = codicePostale;
		this.tipo = Objects.requireNonNull(tipo);
    }
    
    public Person(final String codiceFiscale, final String nome, final String cognome, final String mail,
    		final String indirizzo, final String città, final String regione, final String codicePostale, final Optional<String> tipo) {
        this(codiceFiscale, nome, cognome, mail, Optional.empty(), indirizzo, città, regione, codicePostale, Objects.requireNonNull(tipo));
    }
    
    public Person(final String codiceFiscale, final String nome, final String cognome, final String mail,
    		final String indirizzo, final String città, final String regione, final String codicePostale) {
        this(codiceFiscale, nome, cognome, mail, Optional.empty(), indirizzo, città, regione, codicePostale, Optional.empty());
    }
    
    public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}
	
	public String getMail() {
		return mail;
	}

	public Optional<String> getTelefono() {
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

	public Optional<String> getTipo() {
		return tipo;
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
				.append("(").append(this.codiceFiscale).append(") ")
				.append(this.nome).append(" ")
				.append(this.cognome).append(" ")
				.append(this.mail).append(" ")
				.append(this.indirizzo).append(" ")
				.append(this.città).append(" ")
				.append(this.regione).append(" ")
				.append(this.codicePostale).append(" ")
				.append(this.tipo).toString();
	}

	@Override
    public boolean equals(final Object other) {
        return (other instanceof Person)
                && ((Person) other).getCodiceFiscale().equals(this.getCodiceFiscale())
                && ((Person) other).getNome().equals(this.getNome())
                && ((Person) other).getCognome().equals(this.getCognome())
                && ((Person) other).getMail().equals(this.getMail())
                && ((Person) other).getTelefono().equals(this.getTelefono())
                && ((Person) other).getIndirizzo().equals(this.getIndirizzo())
                && ((Person) other).getCittà().equals(this.getCittà())
                && ((Person) other).getRegione().equals(this.getRegione())
                && ((Person) other).getCodicePostale().equals(this.getCodicePostale())
                && ((Person) other).getTipo().equals(this.getTipo());
    }

	@Override
    public int hashCode() {
        return Objects.hash(this.codiceFiscale, this.nome, this.cognome, this.mail, this.telefono, this.indirizzo,
        		this.città, this.regione, this.codicePostale, this.tipo);
    }
}
