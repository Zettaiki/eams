package model;

import java.util.Objects;
import java.util.Optional;

public class Newsletter {
    private final Optional<Integer> idNewsletter;
	private final String argomento;
    private final Optional<String> descrizione;
    
	public Newsletter(Optional<Integer>  idNewsletter, String argomento, Optional<String> descrizione) {
		this.idNewsletter = idNewsletter;
		this.argomento = argomento;
		this.descrizione = descrizione;
	}
	
	public Newsletter(Optional<Integer>  idNewsletter, String argomento) {
		this(idNewsletter, argomento, Optional.empty());
	}
	   
	public Integer getIdNewsletter() {
		if (this.idNewsletter.isPresent())
			return this.idNewsletter.get();
		return -1;
	}

	public String getArgomento() {
		return this.argomento;
	}

	public Optional<String> getDescrizione() {
		return this.descrizione;
	}

	@Override
    public String toString() {
        return new StringBuilder()
            .append("(").append(idNewsletter).append(") ")
			.append(argomento).append(" ")
			.append(descrizione).toString();
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof Newsletter)
                && ((Newsletter) other).getIdNewsletter().equals(this.getIdNewsletter())
                && ((Newsletter) other).getArgomento().equals(this.getArgomento())
                && ((Newsletter) other).getDescrizione().equals(this.getDescrizione());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idNewsletter, this.argomento, this.descrizione);
    }
}
