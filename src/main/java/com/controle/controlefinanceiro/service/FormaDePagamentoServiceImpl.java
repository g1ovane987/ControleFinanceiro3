package com.controle.controlefinanceiro.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.controle.controlefinanceiro.model.FormaDePagamento;
import com.controle.controlefinanceiro.repository.FormaDePagamentoRepository;

@Service
public class FormaDePagamentoServiceImpl implements FormaDePagamentoService {
	
	@Autowired
	private FormaDePagamentoRepository formaDePagamentoRepository;

	@Override
	public List<FormaDePagamento> findBydescricaoContainingIgnoreCase(String descricao) {
		List<FormaDePagamento> formaDePagamento = formaDePagamentoRepository.findBydescricaoContainingIgnoreCase(descricao);
		return formaDePagamento;
	}

	@Override
	public void salvarFormaDePagamento(FormaDePagamento formaDePagamento) {
		formaDePagamentoRepository.save(formaDePagamento);

	}

	@Override
	public FormaDePagamento getFormaDePagamentoById(Long id) {
		Optional<FormaDePagamento> optional = formaDePagamentoRepository.findById(id);
		FormaDePagamento formaDePagamento = null;
		if (optional.isPresent()) {
			formaDePagamento = optional.get();
		} else {
			throw new RuntimeException("Forma de Pagamento nao encontrado com id = " + id);
		}
		return formaDePagamento;
	}

	@Override
	public void deletarFormaDePagamentoById(Long id) {
		formaDePagamentoRepository.deleteById(id);

	}

	@Override
	public List<FormaDePagamento> pesquisar(String status, String pesquisa) {
		return formaDePagamentoRepository.pesquisar(pesquisa);
	}

	@Override
	public Page<FormaDePagamento> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection,
			String status) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.formaDePagamentoRepository.findAllAtivos(status, pageable);
	}

}
