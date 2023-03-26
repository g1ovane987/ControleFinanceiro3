package com.controle.controlefinanceiro.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.controle.controlefinanceiro.model.Pagamento;

@Service
public interface PagamentoService {
	void salvarPagamento(Pagamento pagamento);

	Pagamento getPagamentoById(Long id);

	void deletarPagamentoById(Long id);
	
	List<Pagamento> pesquisar(String status, String pesquisa, LocalDate dataIni, LocalDate dataFim);
	
	List<Pagamento> pesquisarPorData(String status, LocalDate dataIni, LocalDate dataFim);
	
	Page<Pagamento> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
