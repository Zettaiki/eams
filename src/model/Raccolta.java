package model;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Objects;

public class Raccolta {
	private final Time oraInizioServizio;
	private final String idEvento;
    private final String materiale;
    private final BigDecimal kg;
    
	public Raccolta(Time oraInizioServizio, String idEvento, String materiale, BigDecimal kg) {
		this.oraInizioServizio = oraInizioServizio;
		this.idEvento = idEvento;
		this.materiale = materiale;
		this.kg = kg;
	}

	public Time getOraInizioServizio() {
		return oraInizioServizio;
	}

	public String getIdEvento() {
		return idEvento;
	}

	public String getMateriale() {
		return materiale;
	}

	public BigDecimal getKg() {
		return kg;
	}

	@Override
    public String toString() {
        return new StringBuilder()
            .append("(").append(oraInizioServizio).append(") ")
			.append(idEvento).append(" ")
			.append(materiale).append(" ")
			.append(kg).toString();
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof Raccolta)
        		&& ((Raccolta) other).getOraInizioServizio().equals(this.getOraInizioServizio())
                && ((Raccolta) other).getIdEvento().equals(this.getIdEvento())
                && ((Raccolta) other).getMateriale().equals(this.getMateriale())
                && ((Raccolta) other).getKg().equals(this.getKg());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.oraInizioServizio, this.idEvento, this.materiale, this.kg);
    }
}
