package com.controle.controlefinanceiro.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.controle.controlefinanceiro.model.Fornecedor;

@Service
public interface FornecedorService {
	List<Fornecedor> findByNomeContainingIgnoreCase(String nome);
	
	void salvarFornecedor(Fornecedor fornecedor);

	Fornecedor getFornecedorById(Long id);

	void deletarFornecedorById(Long id);
	
	List<Fornecedor> pesquisar(String status, String pesquisa);
	
	Page<Fornecedor> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection, String status);
}
