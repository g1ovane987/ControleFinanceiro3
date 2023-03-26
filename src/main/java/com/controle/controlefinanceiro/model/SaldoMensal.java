package com.controle.controlefinanceiro.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SALDO_MENSAL")
public class SaldoMensal {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id_saldo_mensal;
	private Integer mes;
	private Integer ano;
	private double saldo;
	private Long id_carteira;
	public Long getId_saldo_mensal() {
		return id_saldo_mensal;
	}
	public void setId_saldo_mensal(Long id_saldo_mensal) {
		this.id_saldo_mensal = id_saldo_mensal;
	}
	public Integer getMes() {
		return mes;
	}
	public void setMes(Integer mes) {
		this.mes = mes;
	}
	public Integer getAno() {
		return ano;
	}
	public void setAno(Integer ano) {
		this.ano = ano;
	}
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	public Long getId_carteira() {
		return id_carteira;
	}
	public void setId_carteira(Long id_carteira) {
		this.id_carteira = id_carteira;
	}
	
}
