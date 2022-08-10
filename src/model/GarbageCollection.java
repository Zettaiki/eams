package model;

import java.math.BigDecimal;
import java.util.Objects;

public class GarbageCollection {
	private final String idServizio;
	private final String idEvento;
    private final String materiale;
    private final BigDecimal kg;
    
	public GarbageCollection(String idServizio, String idEvento, String materiale, BigDecimal kg) {
		this.idServizio = idServizio;
		this.idEvento = idEvento;
		this.materiale = materiale;
		this.kg = kg;
	}

	public String getIdServizio() {
		return idServizio;
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
            .append("(").append(idServizio).append(" ")
			.append(idEvento).append(" ")
			.append(materiale).append(") ")
			.append(kg).toString();
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof GarbageCollection)
        		&& ((GarbageCollection) other).getIdServizio().equals(this.getIdServizio())
                && ((GarbageCollection) other).getIdEvento().equals(this.getIdEvento())
                && ((GarbageCollection) other).getMateriale().equals(this.getMateriale())
                && ((GarbageCollection) other).getKg().equals(this.getKg());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idServizio, this.idEvento, this.materiale, this.kg);
    }
}
