package model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class Delivery {
	private final String materiale;
	private final BigDecimal partitaIVA;
    private final Date data;
    private final BigDecimal kgConsegnati;
    
	public Delivery(String materiale, BigDecimal partitaIVA, Date data, BigDecimal kgConsegnati) {
		this.materiale = materiale;
		this.partitaIVA = partitaIVA;
		this.data = data;
		this.kgConsegnati = kgConsegnati;
	}

	public String getMateriale() {
		return materiale;
	}

	public BigDecimal getPartitaIVA() {
		return partitaIVA;
	}

	public Date getData() {
		return data;
	}

	public BigDecimal getKgConsegnati() {
		return kgConsegnati;
	}

	@Override
    public String toString() {
        return new StringBuilder()
            .append("(").append(materiale).append(") ")
			.append(partitaIVA).append(" ")
			.append(data).append(" ")
			.append(kgConsegnati).toString();
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof Delivery)
        		&& ((Delivery) other).getMateriale().equals(this.getMateriale())
                && ((Delivery) other).getPartitaIVA().equals(this.getPartitaIVA())
                && ((Delivery) other).getData().equals(this.getData())
                && ((Delivery) other).getKgConsegnati().equals(this.getKgConsegnati());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.materiale, this.partitaIVA, this.data, this.kgConsegnati);
    }
}
