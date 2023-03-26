package com.controle.controlefinanceiro.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class Relatorio {
	private Long id_relatorio;
	private Long id_cliente;
	private String nome_cliente;
	private String tipoMovimento;
	private Double valor;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dt_movim;
	public Long getId_relatorio() {
		return id_relatorio;
	}
	public void setId_relatorio(Long id_relatorio) {
		this.id_relatorio = id_relatorio;
	}
	public Long getId_cliente() {
		return id_cliente;
	}
	public void setId_cliente(Long id_cliente) {
		this.id_cliente = id_cliente;
	}
	public String getNome_cliente() {
		return nome_cliente;
	}
	public void setNome_cliente(String nome_cliente) {
		this.nome_cliente = nome_cliente;
	}
	public String getTipoMovimento() {
		return tipoMovimento;
	}
	public void setTipoMovimento(String tipoMovimento) {
		this.tipoMovimento = tipoMovimento;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public LocalDate getDt_movim() {
		return dt_movim;
	}
	public void setDt_movim(LocalDate dt_movim) {
		this.dt_movim = dt_movim;
	}
	
	
}
