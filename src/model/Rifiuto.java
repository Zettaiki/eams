package model;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

public class Rifiuto {
	private final String materiale;
    private final String tipo;
    private final BigDecimal kgImagazzinati;
    private final Optional<String> note;
    
	public Rifiuto(String materiale, final String tipo, BigDecimal kgImagazzinati, final Optional<String> note) {
		this.materiale = materiale;
		this.tipo = tipo;
		this.kgImagazzinati = kgImagazzinati;
		this.note = Objects.requireNonNull(note);
	}
    
	public Rifiuto(String materiale, final String tipo, BigDecimal kgImagazzinati) {
		this(materiale, tipo, kgImagazzinati, Optional.empty());
	}
	
	public String getMateriale() {
		return materiale;
	}

	public String getTipo() {
		return tipo;
	}

	public BigDecimal getKgImagazzinati() {
		return kgImagazzinati;
	}
	
	public Optional<String> getNote() {
		return note;
	}

	@Override
    public String toString() {
        return new StringBuilder()
            .append("(").append(materiale).append(") ")
			.append(tipo).append(" ")
			.append(kgImagazzinati).append(" ")
			.append(note).toString();
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof Rifiuto)
        		&& ((Rifiuto) other).getMateriale().equals(this.getMateriale())
                && ((Rifiuto) other).getTipo().equals(this.getTipo())
                && ((Rifiuto) other).getKgImagazzinati().equals(this.getKgImagazzinati())
                && ((Rifiuto) other).getNote().equals(this.getNote());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.materiale, this.tipo, this.kgImagazzinati, this.note);
    }
}
