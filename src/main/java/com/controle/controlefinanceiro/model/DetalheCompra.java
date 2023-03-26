package com.controle.controlefinanceiro.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DETALHE_COMPRA")
public class DetalheCompra {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id_detalhe_compra;
	private Long id_compra;
	private Long id_prod;
	private double qtde;
	private double valor;
	public Long getId_detalhe_compra() {
		return id_detalhe_compra;
	}
	public void setId_detalhe_compra(Long id_detalhe_compra) {
		this.id_detalhe_compra = id_detalhe_compra;
	}
	public Long getId_compra() {
		return id_compra;
	}
	public void setId_compra(Long id_compra) {
		this.id_compra = id_compra;
	}
	public Long getId_prod() {
		return id_prod;
	}
	public void setId_prod(Long id_prod) {
		this.id_prod = id_prod;
	}
	public double getQtde() {
		return qtde;
	}
	public void setQtde(double qtde) {
		this.qtde = qtde;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	
	
}
