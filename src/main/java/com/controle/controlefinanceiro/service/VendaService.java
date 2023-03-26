package com.controle.controlefinanceiro.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.controle.controlefinanceiro.model.Venda;

@Service
public interface VendaService {
	void salvarVenda(Venda venda);

	Venda getVendaById(Long id);

	void deletarVendaById(Long id);
	
	List<Venda> pesquisar(String status, String pesquisa);
	
	Page<Venda> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
