package com.controle.controlefinanceiro.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VENDA")
public class Venda {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id_venda;
	private Long id_cliente;
	private Long id_pag;
	private Long id_car;
	private Long id_carteira;
	private String status;
	private double valor;
	private Long id_prod;
	private Long qtdeParcelas;
	public Long getId_venda() {
		return id_venda;
	}
	public void setId_venda(Long id_venda) {
		this.id_venda = id_venda;
	}
	public Long getId_cliente() {
		return id_cliente;
	}
	
	public Long getId_carteira() {
		return id_carteira;
	}
	public void setId_carteira(Long id_carteira) {
		this.id_carteira = id_carteira;
	}
	public void setId_cliente(Long id_cliente) {
		this.id_cliente = id_cliente;
	}
	public Long getId_pag() {
		return id_pag;
	}
	public void setId_pag(Long id_pag) {
		this.id_pag = id_pag;
	}
	public Long getId_car() {
		return id_car;
	}
	public void setId_car(Long id_car) {
		this.id_car = id_car;
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
