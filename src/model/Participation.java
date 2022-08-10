package model;

import java.util.Objects;

public class Participation {
	private final String codiceFiscaleVolontario;
    private final String idServizio;
    private final String idEvento;
    
	public Participation(String codiceFiscaleVolontario, String idServizio, String idEvento) {
		this.codiceFiscaleVolontario = codiceFiscaleVolontario;
		this.idServizio = idServizio;
		this.idEvento = idEvento;
	}

	public String getCodiceFiscaleVolontario() {
		return this.codiceFiscaleVolontario;
	}

	public String getIdServizio() {
		return this.idServizio;
	}

	public String getIdEvento() {
		return this.idEvento;
	}

	@Override
    public String toString() {
        return new StringBuilder()
            .append("(").append(codiceFiscaleVolontario).append(" ")
			.append(idServizio).append(" ")
			.append(idEvento).append(")").toString();
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof Participation)
        		&& ((Participation) other).getCodiceFiscaleVolontario().equals(this.getCodiceFiscaleVolontario())
                && ((Participation) other).getIdServizio().equals(this.getIdServizio())
                && ((Participation) other).getIdEvento().equals(this.getIdEvento());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.codiceFiscaleVolontario, this.idServizio, this.idEvento);
    }
}
