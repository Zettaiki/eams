package model;

import java.math.BigDecimal;
import java.util.Objects;

public class Production {
	private final String idServizio;
    private final String idEvento;
    private final Integer idProdotto;
    private final Integer quantitąProdotta;
    private final String materialeUsato;
    private final BigDecimal kgRifiutiUsati;
    
	public Production(String idServizio, String idEvento, Integer idProdotto, Integer quantitąProdotta,
			String materialeUsato, BigDecimal kgRifiutiUsati) {
		this.idServizio = idServizio;
		this.idEvento = idEvento;
		this.idProdotto = idProdotto;
		this.quantitąProdotta = quantitąProdotta;
		this.materialeUsato = materialeUsato;
		this.kgRifiutiUsati = kgRifiutiUsati;
	}
    
	public String getIdServizio() {
		return idServizio;
	}

	public String getIdEvento() {
		return idEvento;
	}
	
	public Integer getIdProdotto() {
		return idProdotto;
	}

	public Integer getQuantitąProdotta() {
		return quantitąProdotta;
	}

	public String getMaterialeUsato() {
		return materialeUsato;
	}

	public BigDecimal getKgRifiutiUsati() {
		return kgRifiutiUsati;
	}

	@Override
    public String toString() {
        return new StringBuilder()
            .append("(").append(idServizio).append(" ")
			.append(idEvento).append(" ")
			.append(idProdotto).append(") ")
			.append(quantitąProdotta).append(" ")
			.append(materialeUsato).append(" ")
			.append(kgRifiutiUsati).toString();
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof Production)
                && ((Production) other).getIdServizio().equals(this.getIdServizio())
                && ((Production) other).getIdEvento().equals(this.getIdEvento())
                && ((Production) other).getIdProdotto().equals(this.getIdProdotto())
                && ((Production) other).getQuantitąProdotta().equals(this.getQuantitąProdotta())
                && ((Production) other).getMaterialeUsato().equals(this.getMaterialeUsato())
                && ((Production) other).getKgRifiutiUsati().equals(this.getKgRifiutiUsati());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idServizio, this.idEvento, this.idProdotto, this.quantitąProdotta,
        		this.materialeUsato, this.kgRifiutiUsati);
    }
}
