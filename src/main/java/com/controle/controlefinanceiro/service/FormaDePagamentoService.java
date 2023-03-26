package com.controle.controlefinanceiro.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.controle.controlefinanceiro.model.FormaDePagamento;

@Service
public interface FormaDePagamentoService {
	List<FormaDePagamento> findBydescricaoContainingIgnoreCase(String descricao);
	
	void salvarFormaDePagamento(FormaDePagamento formaDePagamento);

	FormaDePagamento getFormaDePagamentoById(Long id);

	void deletarFormaDePagamentoById(Long id);
	
	List<FormaDePagamento> pesquisar(String status, String pesquisa);
	
	Page<FormaDePagamento> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection, String status);
}
