package com.controle.controlefinanceiro.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "COMPRA")
public class Compra {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id_compra;
	private Long id_for;
	private Long id_pag;
	private Long id_cap;
	private Long id_carteira;
	private String status;
	private double valor;
	private Long id_prod;
	private Long qtdeParcelas;
	public Long getId_compra() {
		return id_compra;
	}
	public void setId_compra(Long id_compra) {
		this.id_compra = id_compra;
	}
	public Long getId_carteira() {
		return id_carteira;
	}
	public void setId_carteira(Long id_carteira) {
		this.id_carteira = id_carteira;
	}
	public Long getId_for() {
		return id_for;
	}
	public void setId_for(Long id_for) {
		this.id_for = id_for;
	}
	public Long getId_pag() {
		return id_pag;
	}
	public void setId_pag(Long id_pag) {
		this.id_pag = id_pag;
	}
	public Long getId_cap() {
		return id_cap;
	}
	public void setId_cap(Long id_cap) {
		this.id_cap = id_cap;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public Long getId_prod() {
		return id_prod;
	}
	public void setId_prod(Long id_prod) {
		this.id_prod = id_prod;
	}
	public Long getQtdeParcelas() {
		return qtdeParcelas;
	}
	public void setQtdeParcelas(Long qtdeParcelas) {
		this.qtdeParcelas = qtdeParcelas;
	}
	
	
}
