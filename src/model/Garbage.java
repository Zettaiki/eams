package model;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

public class Garbage {
	private final String materiale;
    private final String tipo;
    private final BigDecimal kgImagazzinati;
    private final Optional<String> note;
    
	public Garbage(String materiale, final String tipo, BigDecimal kgImagazzinati, final Optional<String> note) {
		this.materiale = materiale;
		this.tipo = tipo;
		this.kgImagazzinati = kgImagazzinati;
		this.note = Objects.requireNonNull(note);
	}
    
	public Garbage(String materiale, final String tipo, BigDecimal kgImagazzinati) {
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
        return (other instanceof Garbage)
        		&& ((Garbage) other).getMateriale().equals(this.getMateriale())
                && ((Garbage) other).getTipo().equals(this.getTipo())
                && ((Garbage) other).getKgImagazzinati().equals(this.getKgImagazzinati())
                && ((Garbage) other).getNote().equals(this.getNote());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.materiale, this.tipo, this.kgImagazzinati, this.note);
    }
}
