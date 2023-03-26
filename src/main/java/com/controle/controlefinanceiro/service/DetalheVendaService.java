package com.controle.controlefinanceiro.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.controle.controlefinanceiro.model.DetalheVenda;

@Service
public interface DetalheVendaService {
	void salvarDetalheVenda(DetalheVenda detalheVenda);

	DetalheVenda getDetalheVendaById(Long id);

	void deletarDetalheVendaById(Long id);
	
	Page<DetalheVenda> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection, Integer id);
}
