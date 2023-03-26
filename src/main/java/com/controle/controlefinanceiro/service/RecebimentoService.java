package com.controle.controlefinanceiro.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.controle.controlefinanceiro.model.Recebimento;

@Service
public interface RecebimentoService {
	void salvarRecebimento(Recebimento recebimento);

	Recebimento getRecebimentoById(Long id);

	void deletarRecebimentoById(Long id);
	
	List<Recebimento> pesquisar(String status, String pesquisa, LocalDate dataIni, LocalDate dataFim);
	
	List<Recebimento> pesquisarPorData(String status, LocalDate dataIni, LocalDate dataFim);
	
	Page<Recebimento> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
