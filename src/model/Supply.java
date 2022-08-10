package model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class Supply {
	private final Integer idProdotto;
	private final BigDecimal partitaIVA;
    private final Date data;
    private final Integer quantit‡Fornita;
    
	public Supply(Integer idProdotto, BigDecimal partitaIVA, Date data, Integer quantit‡Fornita) {
		this.idProdotto = idProdotto;
		this.partitaIVA = partitaIVA;
		this.data = data;
		this.quantit‡Fornita = quantit‡Fornita;
	}

	public Integer getIdProdotto() {
		return idProdotto;
	}

	public BigDecimal getPartitaIVA() {
		return partitaIVA;
	}

	public Date getData() {
		return data;
	}

	public Integer getQuantit‡Fornita() {
		return quantit‡Fornita;
	}

	@Override
    public String toString() {
        return new StringBuilder()
            .append("(").append(idProdotto).append(" ")
			.append(partitaIVA).append(" ")
			.append(data).append(") ")
			.append(quantit‡Fornita).toString();
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof Supply)
        		&& ((Supply) other).getIdProdotto().equals(this.getIdProdotto())
                && ((Supply) other).getPartitaIVA().equals(this.getPartitaIVA())
                && ((Supply) other).getData().equals(this.getData())
                && ((Supply) other).getQuantit‡Fornita().equals(this.getQuantit‡Fornita());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idProdotto, this.partitaIVA, this.data, this.quantit‡Fornita);
    }
}
