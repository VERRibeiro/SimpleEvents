package br.edu.ifpb.pweb2.SimpleEvents.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import br.edu.ifpb.pweb2.SimpleEvents.util.Status;

@Entity
@Table(name = "tb_usuario")
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class Usuario implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, orphanRemoval = true)
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
	private boolean isAdmin;
	private boolean isCandidato;

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

	public boolean isCandidato() {
		return isCandidato;
	}

	public void setCandidato(boolean isCandidato) {
		this.isCandidato = isCandidato;
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

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", eventosAdministrados=" + eventosAdministrados + ", dataNasc=" + dataNasc
				+ ", nome=" + nome + ", telefone=" + telefone + ", email=" + email + ", senha=" + senha + ", isAdmin="
				+ isAdmin + "]";
	}

}
