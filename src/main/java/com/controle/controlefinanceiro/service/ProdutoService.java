package com.controle.controlefinanceiro.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.controle.controlefinanceiro.model.Produto;

@Service
public interface ProdutoService {
	List<Produto> findByDescricaoContainingIgnoreCase(String descricao);
	
	void salvarProduto(Produto produto);

	Produto getProdutoById(Long id);

	void deletarProdutoById(Long id);
	
	List<Produto> pesquisar(String status, String pesquisa);
	
	Page<Produto> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection, String status);
}
