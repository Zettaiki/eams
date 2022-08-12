package model;

import java.util.Objects;

public class Participation {
	private final String codiceFiscaleVolontario;
    private final String idServizio;
    
	public Participation(String codiceFiscaleVolontario, String idServizio) {
		this.codiceFiscaleVolontario = codiceFiscaleVolontario;
		this.idServizio = idServizio;
	}

	public String getCodiceFiscaleVolontario() {
		return this.codiceFiscaleVolontario;
	}

	public String getIdServizio() {
		return this.idServizio;
	}

	@Override
    public String toString() {
        return new StringBuilder()
            .append("(").append(codiceFiscaleVolontario).append(" ")
			.append(idServizio).append(" ").toString();
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof Participation)
        		&& ((Participation) other).getCodiceFiscaleVolontario().equals(this.getCodiceFiscaleVolontario())
                && ((Participation) other).getIdServizio().equals(this.getIdServizio());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.codiceFiscaleVolontario, this.idServizio);
    }
}
