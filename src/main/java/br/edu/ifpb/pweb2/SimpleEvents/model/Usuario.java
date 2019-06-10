package br.edu.ifpb.pweb2.SimpleEvents.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import br.edu.ifpb.pweb2.SimpleEvents.util.Status;

@Entity
@Component
@Table(name = "tb_usuario")
public class Usuario implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "admin", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Evento> eventosAdministrados;
	private int notaDesempenho;
	private Status status;

	@ManyToOne
	private VagaEvento vaga;
	private Date dataNasc;
	private String nome;
	private String telefone;
	@NotNull(message = "Campo obrigatório!")
	private String email;
	@NotNull(message = "Campo obrigatório!")
	private String senha;
	private boolean admin;
	private boolean candidato;	
	
	public int getNotaDesempenho() {
		return notaDesempenho;
	}

	public void setNotaDesempenho(int notaDesempenho) {
		this.notaDesempenho = notaDesempenho;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Usuario() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setEventosAdministrados(ArrayList<Evento> eventosAdministrados) {
		this.eventosAdministrados = eventosAdministrados;
	}

	public Date getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(Date dataNasc) {
		this.dataNasc = dataNasc;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean isCandidato() {
		return candidato;
	}

	public void setCandidato(boolean candidato) {
		this.candidato = candidato;
	}

	public VagaEvento getVaga() {
		return vaga;
	}

	public void setVaga(VagaEvento vaga) {
		this.vaga = vaga;
	}
	
	public void addEvento(Evento evento) {
		this.eventosAdministrados.add(evento);
	}
	
	public List<Evento> getEventosAdministrados() {
		return eventosAdministrados;
	}

	public void setEventosAdministrados(List<Evento> eventosAdministrados) {
		this.eventosAdministrados = eventosAdministrados;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", eventosAdministrados=" + eventosAdministrados + ", notaDesempenho="
				+ notaDesempenho + ", status=" + status + ", vaga=" + vaga + ", dataNasc=" + dataNasc + ", nome=" + nome
				+ ", telefone=" + telefone + ", email=" + email + ", senha=" + senha + ", admin=" + admin
				+ ", candidato=" + candidato + "]";
	}
	
}
