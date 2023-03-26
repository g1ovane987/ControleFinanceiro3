package com.controle.controlefinanceiro.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.controle.controlefinanceiro.model.Compra;

@Service
public interface CompraService {	
	void salvarCompra(Compra compra);

	Compra getCompraById(Long id);

	void deletarCompraById(Long id);
	
	List<Compra> pesquisar(String status, String pesquisa);
	
	Page<Compra> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
