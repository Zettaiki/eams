package model;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

public class Azienda {
	private final BigDecimal partitaIVA;
    private final String denominazioneSociale;
    private final String telefono;
    private final String indirizzo;
    private final String citt�;
    private final String regione;
    private final String codicePostale;
    private final String mail;
    private final Optional<String> fax;
    
    public Azienda(final BigDecimal partitaIVA, final String denominazioneSociale, final String telefono, 
    		final String indirizzo, final String citt�, final String regione, final String codicePostale,
    		final String mail, final Optional<String> fax) {
        this.partitaIVA = partitaIVA;
        this.denominazioneSociale = denominazioneSociale;
        this.telefono = telefono;
        this.indirizzo = indirizzo;
        this.citt� = citt�;
        this.regione = regione;
        this.codicePostale = codicePostale;
        this.mail = mail;
        this.fax = Objects.requireNonNull(fax);
    }
    
	public Azienda(final BigDecimal partitaIVA, final String denominazioneSociale, final String telefono,
			final String indirizzo, final String citt�, final String regione, final String codicePostale,
			final String mail) {
		this(partitaIVA, denominazioneSociale, telefono, indirizzo, citt�, regione, codicePostale, mail,
				Optional.empty());
	}
    
    public BigDecimal getPartitaIVA() {
		return partitaIVA;
	}

	public String getDenominazioneSociale() {
		return denominazioneSociale;
	}	

	public String getTelefono() {
		return telefono;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public String getCitt�() {
		return citt�;
	}

	public String getRegione() {
		return regione;
	}

	public String getCodicePostale() {
		return codicePostale;
	}
	
	public String getMail() {
		return mail;
	}

	public Optional<String> getFax() {
		return fax;
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
				.append("(").append(this.partitaIVA).append(") ")
				.append(this.denominazioneSociale).append(" ")
				.append(this.telefono).append(" ")
				.append(this.indirizzo).append(" ")
				.append(this.citt�).append(" ")
				.append(this.regione).append(" ")
				.append(this.codicePostale).append(" ")
				.append(this.mail).append(" ")
				.append(this.fax).toString();
	}

	@Override
    public boolean equals(final Object other) {
        return (other instanceof Azienda)
                && ((Azienda) other).getPartitaIVA().equals(this.getPartitaIVA())
                && ((Azienda) other).getDenominazioneSociale().equals(this.getDenominazioneSociale())
                && ((Azienda) other).getTelefono().equals(this.getTelefono())
                && ((Azienda) other).getIndirizzo().equals(this.getIndirizzo())
                && ((Azienda) other).getCitt�().equals(this.getCitt�())
                && ((Azienda) other).getRegione().equals(this.getRegione())
                && ((Azienda) other).getCodicePostale().equals(this.getCodicePostale())
                && ((Azienda) other).getMail().equals(this.getMail())
                && ((Azienda) other).getFax().equals(this.getFax());
    }

	@Override
    public int hashCode() {
		return Objects.hash(this.partitaIVA, this.denominazioneSociale, this.telefono, this.indirizzo, this.citt�,
				this.regione, this.codicePostale, this.mail, this.fax);
	}
}
