package com.controle.controlefinanceiro.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DETALHE_VENDA")
public class DetalheVenda {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id_detalhe_venda;
	private Long id_venda;
	private Long id_prod;
	private double qtde;
	private double valor;
	public Long getId_detalhe_venda() {
		return id_detalhe_venda;
	}
	public void setId_detalhe_venda(Long id_detalhe_venda) {
		this.id_detalhe_venda = id_detalhe_venda;
	}
	public Long getId_venda() {
		return id_venda;
	}
	public void setId_venda(Long id_venda) {
		this.id_venda = id_venda;
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
