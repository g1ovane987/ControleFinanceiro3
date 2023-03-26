package com.controle.controlefinanceiro.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "CONTA_RECEBER")
public class ContaReceber {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id_car;
	private Long id_cli;
	private String obs;
	private String status;
	private double valor;
	private Long id_venda;
	private Long id_carteira;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dt_vencim;
	
	public Long getId_carteira() {
		return id_carteira;
	}

	public void setId_carteira(Long id_carteira) {
		this.id_carteira = id_carteira;
	}

	public Long getId_venda() {
		return id_venda;
	}

	public void setId_venda(Long id_venda) {
		this.id_venda = id_venda;
	}

	public Long getId_car() {
		return id_car;
	}

	public void setId_car(Long id_car) {
		this.id_car = id_car;
	}

	public Long getId_cli() {
		return id_cli;
	}

	public void setId_cli(Long id_cli) {
		this.id_cli = id_cli;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
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

	public LocalDate getDt_vencim() {
		return dt_vencim;
	}

	public void setDt_vencim(LocalDate dt_vencim) {
		this.dt_vencim = dt_vencim;
	}
	
}
