package com.controle.controlefinanceiro.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.controle.controlefinanceiro.model.SaldoMensal;

@Service
public interface SaldoMensalService {
	void salvarSaldoMensal(SaldoMensal saldoMensal);

	SaldoMensal getSaldoMensalById(Long id);

	void deletarSaldoMensalById(Long id);
	
	List<SaldoMensal> pesquisar(String pesquisa);
	
	Page<SaldoMensal> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
