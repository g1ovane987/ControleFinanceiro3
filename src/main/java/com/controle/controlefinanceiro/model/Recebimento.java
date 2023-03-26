package com.controle.controlefinanceiro.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "RECEBIMENTO")
public class Recebimento {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id_receb;
	private Long id_car;
	private Long id_cli;
	private String obs;
	private String status;
	private double vlr_pago;
	private Long id_carteira;
	private Long id_venda;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dt_pag;

	public Long getId_receb() {
		return id_receb;
	}

	
	public Long getId_carteira() {
		return id_carteira;
	}


	public void setId_carteira(Long id_carteira) {
		this.id_carteira = id_carteira;
	}


	public void setId_receb(Long id_receb) {
		this.id_receb = id_receb;
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

	public double getVlr_pago() {
		return vlr_pago;
	}

	public void setVlr_pago(double vlr_pago) {
		this.vlr_pago = vlr_pago;
	}

	public LocalDate getDt_pag() {
		return dt_pag;
	}

	public void setDt_pag(LocalDate dt_pag) {
		this.dt_pag = dt_pag;
	}


	public Long getId_venda() {
		return id_venda;
	}


	public void setId_venda(Long id_venda) {
		this.id_venda = id_venda;
	}
	
	
}
