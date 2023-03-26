package com.controle.controlefinanceiro.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.controle.controlefinanceiro.model.ContaPagar;

@Service
public interface ContaPagarService {	
	void salvarContaPagar(ContaPagar contaPagar);

	ContaPagar getContaPagarById(Long id);

	void deletarContaPagarById(Long id);
	
	List<ContaPagar> pesquisar(String status, String pesquisa, LocalDate dataIni, LocalDate dataFim);
	
	Page<ContaPagar> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
