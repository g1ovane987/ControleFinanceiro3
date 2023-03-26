package com.controle.controlefinanceiro.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "PAGAMENTO")
public class Pagamento {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id_pag;
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

	public Long getId_for() {
		return id_for;
	}

	public void setId_for(Long id_for) {
		this.id_for = id_for;
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
	
	public Long getId_carteira() {
		return id_carteira;
	}

	public void setId_carteira(Long id_carteira) {
		this.id_carteira = id_carteira;
	}
	
	public Long getId_compra() {
		return id_compra;
	}

	public void setId_compra(Long id_compra) {
		this.id_compra = id_compra;
	}



	private Long id_cap;
	private Long id_for;
	private String obs;
	private String status;
	private double vlr_pago;
	private Long id_carteira;
	private Long id_compra;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dt_pag;
}
