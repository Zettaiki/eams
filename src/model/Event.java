package model;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

public class Event {
	private final String idEvento;
    private final String nome;
    private final Date data;
    private final Optional<String> descrizione;
    
	public Event(String idEvento, String nome, Date data, Optional<String> descrizione) {
		this.idEvento = idEvento;
		this.nome = nome;
		this.data = data;
		this.descrizione = Objects.requireNonNull(descrizione);
	}
    
	public Event(String idEvento, String nome, Date data) {
		this(idEvento, nome, data, Optional.empty());
	}

	public String getIdEvento() {
		return this.idEvento;
	}

	public String getNome() {
		return this.nome;
	}

	public Date getData() {
		return this.data;
	}

	public Optional<String> getDescrizione() {
		return this.descrizione;
	}

	@Override
    public String toString() {
        return new StringBuilder()
            .append("(").append(idEvento).append(") ")
			.append(nome).append(" ")
			.append(data).append(" ")
			.append(descrizione).toString();
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof Event)
        		&& ((Event) other).getIdEvento().equals(this.getIdEvento())
                && ((Event) other).getNome().equals(this.getNome())
                && ((Event) other).getData().equals(this.getData())
                && ((Event) other).getDescrizione().equals(this.getDescrizione());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idEvento, this.nome, this.data, this.descrizione);
    }
}
