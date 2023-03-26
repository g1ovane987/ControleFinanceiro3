package com.controle.controlefinanceiro.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CARTEIRA")
public class Carteira {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id_carteira;
	private String descricao;
	private String cc;
	private String banco;
	private String status;
	public Long getId_carteira() {
		return id_carteira;
	}
	public void setId_carteira(Long id_carteira) {
		this.id_carteira = id_carteira;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}
	public String getBanco() {
		return banco;
	}
	public void setBanco(String banco) {
		this.banco = banco;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
