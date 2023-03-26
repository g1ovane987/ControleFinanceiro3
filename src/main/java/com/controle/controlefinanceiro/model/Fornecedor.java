package com.controle.controlefinanceiro.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FORNECEDOR")
public class Fornecedor {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id_for;
	private String cpf_cnpj;
	private String telefone;
	private String nome;
	private String status;
	private String endereco;
	private String email;
	private String repres;
	public Long getId_for() {
		return id_for;
	}
	public void setId_for(Long id_for) {
		this.id_for = id_for;
	}
	public String getCpf_cnpj() {
		return cpf_cnpj;
	}
	public void setCpf_cnpj(String cpf_cnpj) {
		this.cpf_cnpj = cpf_cnpj;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRepres() {
		return repres;
	}
	public void setRepres(String repres) {
		this.repres = repres;
	}
	
}
