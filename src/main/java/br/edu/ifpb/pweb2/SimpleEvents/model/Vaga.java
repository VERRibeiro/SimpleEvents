package br.edu.ifpb.pweb2.SimpleEvents.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tb_vaga")
public class Vaga {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int qtdeVagas;
	
	
	@OneToMany(mappedBy = "vaga", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<VagaEvento> vagaEvento;
	
	@NotNull(message = "Campo obrigatório!")
	private String nome;
	@NotNull(message = "Campo obrigatório!")
	private String descricao;
	
	public Vaga() {};
		
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQtdeVagas() {
		return qtdeVagas;
	}

	public void setQtdeVagas(int qtdeVagas) {
		this.qtdeVagas = qtdeVagas;
	}

	
}
