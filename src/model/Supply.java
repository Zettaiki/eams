package model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class Supply {
	private final String idProdotto;
	private final BigDecimal partitaIVA;
    private final Date data;
    private final Integer quantitàFornita;
    
	public Supply(String idProdotto, BigDecimal partitaIVA, Date data, Integer quantitàFornita) {
		this.idProdotto = idProdotto;
		this.partitaIVA = partitaIVA;
		this.data = data;
		this.quantitàFornita = quantitàFornita;
	}

	public String getIdProdotto() {
		return idProdotto;
	}

	public BigDecimal getPartitaIVA() {
		return partitaIVA;
	}

	public Date getData() {
		return data;
	}

	public Integer getQuantitàFornita() {
		return quantitàFornita;
	}

	@Override
    public String toString() {
        return new StringBuilder()
            .append("(").append(idProdotto).append(" ")
			.append(partitaIVA).append(" ")
			.append(data).append(") ")
			.append(quantitàFornita).toString();
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof Supply)
        		&& ((Supply) other).getIdProdotto().equals(this.getIdProdotto())
                && ((Supply) other).getPartitaIVA().equals(this.getPartitaIVA())
                && ((Supply) other).getData().equals(this.getData())
                && ((Supply) other).getQuantitàFornita().equals(this.getQuantitàFornita());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idProdotto, this.partitaIVA, this.data, this.quantitàFornita);
    }
}
