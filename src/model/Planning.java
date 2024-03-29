package model;

import java.util.Objects;

public class Planning {
	private final String codiceFiscaleDipendente;
    private final String idEvento;
    
	public Planning(String codiceFiscaleDipendente, String idEvento) {
		this.codiceFiscaleDipendente = codiceFiscaleDipendente;
		this.idEvento = idEvento;
	}

	public String getCodiceFiscaleDipendente() {
		return this.codiceFiscaleDipendente;
	}

	public String getIdEvento() {
		return this.idEvento;
	}

	@Override
    public String toString() {
        return new StringBuilder()
            .append("(").append(codiceFiscaleDipendente).append(" ")
			.append(idEvento).append(")").toString();
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof Planning)
        		&& ((Planning) other).getCodiceFiscaleDipendente().equals(this.getCodiceFiscaleDipendente())
                && ((Planning) other).getIdEvento().equals(this.getIdEvento());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.codiceFiscaleDipendente, this.idEvento);
    }
}
