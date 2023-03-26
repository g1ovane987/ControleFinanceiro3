package com.controle.controlefinanceiro.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FORMA_PAGAMENTO")
public class FormaDePagamento {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id_fpag;
	private String descricao;
	private String parcelado;
	private String status;
	public Long getId_fpag() {
		return id_fpag;
	}
	public void setId_fpag(Long id_fpag) {
		this.id_fpag = id_fpag;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getParcelado() {
		return parcelado;
	}
	public void setParcelado(String parcelado) {
		this.parcelado = parcelado;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
