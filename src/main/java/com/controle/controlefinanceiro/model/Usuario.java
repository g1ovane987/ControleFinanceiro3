package com.controle.controlefinanceiro.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USUARIO")
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id_usuario;
	private String login;
	private String senha;
	private String status;
	private String nivel_permissao;
	public Long getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(Long id_usuario) {
		this.id_usuario = id_usuario;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNivel_permissao() {
		return nivel_permissao;
	}
	public void setNivel_permissao(String nivel_permissao) {
		this.nivel_permissao = nivel_permissao;
	}
	
}
