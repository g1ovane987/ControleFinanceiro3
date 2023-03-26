package com.controle.controlefinanceiro.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.controle.controlefinanceiro.model.Carteira;
import com.controle.controlefinanceiro.model.Pagamento;
import com.controle.controlefinanceiro.model.Recebimento;
import com.controle.controlefinanceiro.model.SaldoMensal;

@Service
public interface CarteiraService {
	
	List<Carteira> findByDescricaoContainingIgnoreCase(String descricao);
	
	void salvarCarteira(Carteira carteira);

	Carteira getCarteiraById(Long id);
	
	List<SaldoMensal> obterSaldoCarteira(Integer mes, Integer ano, Long id_carteira);
	
	List<Recebimento> obterRecebimentosCarteira(String status, Long id_carteira, LocalDate dataIni, LocalDate datafim);

	List<Pagamento> obterPagamentosCarteira(String status, Long id_carteira, LocalDate dataIni, LocalDate datafim);
	
	void deletarCarteiraById(Long id);
	
	List<Carteira> pesquisar(String status, String pesquisa);
	
	List<Carteira> obterCarteiras(String status);
	
	Page<Carteira> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection, String status);
}
