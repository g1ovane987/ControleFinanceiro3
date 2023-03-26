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

import com.controle.controlefinanceiro.model.ContaPagar;
import com.controle.controlefinanceiro.repository.ContaPagarRepository;

@Service
public class ContaPagarServiceImpl implements ContaPagarService {
	
	@Autowired
	private ContaPagarRepository contaPagarRepository;

	@Override
	public void salvarContaPagar(ContaPagar contaPagar) {
		contaPagarRepository.save(contaPagar);

	}

	@Override
	public ContaPagar getContaPagarById(Long id) {
		Optional<ContaPagar> optional = contaPagarRepository.findById(id);
		ContaPagar contaPagar = null;
		if (optional.isPresent()) {
			contaPagar = optional.get();
		} else {
			throw new RuntimeException("Conta a Pagar nao encontrada com id = " + id);
		}
		return contaPagar;
	}

	@Override
	public void deletarContaPagarById(Long id) {
		contaPagarRepository.deleteById(id);

	}

	@Override
	public List<ContaPagar> pesquisar(String status, String pesquisa,  LocalDate dataIni, LocalDate dataFim) {
		return contaPagarRepository.pesquisar(status, pesquisa, dataIni, dataFim);
	}

	@Override
	public Page<ContaPagar> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.contaPagarRepository.findAll(pageable);
	}

}
