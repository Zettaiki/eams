package model;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Objects;

public class Produzione {
	private final Time oraInizioServizio;
    private final String idEvento;
    private final Integer idProdotto;
    private final Integer quantitąProdotta;
    private final String materialeUsato;
    private final BigDecimal kgRifiutiUsati;
    
	public Produzione(Time oraInizioServizio, String idEvento, Integer idProdotto, Integer quantitąProdotta,
			String materialeUsato, BigDecimal kgRifiutiUsati) {
		this.oraInizioServizio = oraInizioServizio;
		this.idEvento = idEvento;
		this.idProdotto = idProdotto;
		this.quantitąProdotta = quantitąProdotta;
		this.materialeUsato = materialeUsato;
		this.kgRifiutiUsati = kgRifiutiUsati;
	}
    
	public Time getOraInizioServizio() {
		return oraInizioServizio;
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
            .append("(").append(oraInizioServizio).append(") ")
			.append(idEvento).append(" ")
			.append(idProdotto).append(" ")
			.append(quantitąProdotta).append(" ")
			.append(materialeUsato).append(" ")
			.append(kgRifiutiUsati).toString();
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof Produzione)
                && ((Produzione) other).getOraInizioServizio().equals(this.getOraInizioServizio())
                && ((Produzione) other).getIdEvento().equals(this.getIdEvento())
                && ((Produzione) other).getIdProdotto().equals(this.getIdProdotto())
                && ((Produzione) other).getQuantitąProdotta().equals(this.getQuantitąProdotta())
                && ((Produzione) other).getMaterialeUsato().equals(this.getMaterialeUsato())
                && ((Produzione) other).getKgRifiutiUsati().equals(this.getKgRifiutiUsati());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.oraInizioServizio, this.idEvento, this.idProdotto, this.quantitąProdotta,
        		this.materialeUsato, this.kgRifiutiUsati);
    }
}
