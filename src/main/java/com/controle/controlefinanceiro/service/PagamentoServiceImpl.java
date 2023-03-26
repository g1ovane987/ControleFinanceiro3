package com.controle.controlefinanceiro.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.controle.controlefinanceiro.model.Pagamento;
import com.controle.controlefinanceiro.repository.PagamentoRepository;

@Service
public class PagamentoServiceImpl implements PagamentoService {
	
	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Override
	public void salvarPagamento(Pagamento pagamento) {
		pagamentoRepository.save(pagamento);

	}

	@Override
	public Pagamento getPagamentoById(Long id) {
		Optional<Pagamento> optional = pagamentoRepository.findById(id);
		Pagamento pagamento = null;
		if (optional.isPresent()) {
			pagamento = optional.get();
		} else {
			throw new RuntimeException("Pagamento nao encontrado com id = " + id);
		}
		return pagamento;
	}

	@Override
	public void deletarPagamentoById(Long id) {
		pagamentoRepository.deleteById(id);

	}

	@Override
	public List<Pagamento> pesquisarPorData(String status, LocalDate dataIni, LocalDate dataFim) {
		return pagamentoRepository.pesquisarPorData(status, dataIni, dataFim);
	}

	@Override
	public List<Pagamento> pesquisar(String status, String pesquisa, LocalDate dataIni, LocalDate dataFim) {
		return pagamentoRepository.pesquisar(status, pesquisa, dataIni, dataFim);	
	}

	@Override
	public Page<Pagamento> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.pagamentoRepository.findAll(pageable);
	}

}
