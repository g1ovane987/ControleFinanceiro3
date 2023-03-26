package com.controle.controlefinanceiro.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.controle.controlefinanceiro.model.DetalheCompra;

@Service
public interface DetalheCompraService {
	void salvarDetalheCompra(DetalheCompra detalheCompra);

	DetalheCompra getDetalheCompraById(Long id);

	void deletarDetalheCompraById(Long id);
	
	Page<DetalheCompra> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection, Integer id);
}
