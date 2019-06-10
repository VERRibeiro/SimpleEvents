package br.edu.ifpb.pweb2.SimpleEvents.model;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class UsuarioLogado {
	
	private int id;
	private String nome;
	private String email;
	private boolean admin;
	private boolean candidato;
	private List<Evento> eventosAdministrados;
	private VagaEvento vaga;
	
	public UsuarioLogado() {};
	public UsuarioLogado(int id, String nome, String email, boolean admin, boolean candidato) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.admin = admin;
		this.candidato = candidato;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
		
	public List<Evento> getEventosAdministrados() {
		return eventosAdministrados;
	}
	public void setEventosAdministrados(List<Evento> eventosAdministrados) {
		this.eventosAdministrados = eventosAdministrados;
	}
	public VagaEvento getVaga() {
		return vaga;
	}
	public void setVaga(VagaEvento vaga) {
		this.vaga = vaga;
	}
	@Override
	public String toString() {
		return "UsuarioLogado [id=" + id + ", nome=" + nome + ", email=" + email + ", admin=" + admin + ", candidato="
				+ candidato + "]";
	}
	
}
