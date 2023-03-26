package com.controle.controlefinanceiro.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUTO")
public class Produto {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id_prod;
	private String descricao;
	private double vlr_padrao;
	private String status;
	public Long getId_prod() {
		return id_prod;
	}
	public void setId_prod(Long id_prod) {
		this.id_prod = id_prod;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public double getVlr_padrao() {
		return vlr_padrao;
	}
	public void setVlr_padrao(double vlr_padrao) {
		this.vlr_padrao = vlr_padrao;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
