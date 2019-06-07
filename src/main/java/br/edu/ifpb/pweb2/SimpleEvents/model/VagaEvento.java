package br.edu.ifpb.pweb2.SimpleEvents.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_vaga_evento")
public class VagaEvento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int qtdeEvento;
	
	@ManyToOne
	private Evento evento;
	
	@OneToMany(mappedBy = "vaga", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Usuario> candidatos;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "vagaId", referencedColumnName = "id")
	private Vaga vaga;

	public VagaEvento() {
	};

	public VagaEvento(Vaga vaga, Evento evento, int qtdeEvento) {
		super();
		this.qtdeEvento = qtdeEvento;
		this.evento = evento;
		this.vaga = vaga;
	}

	public int getQtdeEvento() {
		return qtdeEvento;
	}

	public void setQtdeEvento(int qtdeEvento) {
		this.qtdeEvento = qtdeEvento;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Vaga getVaga() {
		return vaga;
	}

	public void setVaga(Vaga vaga) {
		this.vaga = vaga;
	}

	
}
