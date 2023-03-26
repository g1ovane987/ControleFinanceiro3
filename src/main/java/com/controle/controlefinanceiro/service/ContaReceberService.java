package com.controle.controlefinanceiro.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.controle.controlefinanceiro.model.ContaReceber;

@Service
public interface ContaReceberService {
	void salvarContaRebecer(ContaReceber contaReceber);

	ContaReceber getContaReceberById(Long id);

	void deletarContaReceberById(Long id);
	
	List<ContaReceber> pesquisar(String status, String pesquisa, LocalDate dataIni, LocalDate dataFim);
	
	Page<ContaReceber> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
