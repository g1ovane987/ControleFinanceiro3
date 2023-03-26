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

import com.controle.controlefinanceiro.model.ContaReceber;
import com.controle.controlefinanceiro.repository.ContaReceberRepository;

@Service
public class ContaReceberServiceImpl implements ContaReceberService {
	
	@Autowired
	private ContaReceberRepository contaReceberRepository;

	@Override
	public void salvarContaRebecer(ContaReceber contaReceber) {
		contaReceberRepository.save(contaReceber);

	}

	@Override
	public ContaReceber getContaReceberById(Long id) {
		Optional<ContaReceber> optional = contaReceberRepository.findById(id);
		ContaReceber contaReceber = null;
		if (optional.isPresent()) {
			contaReceber = optional.get();
		} else {
			throw new RuntimeException("Conta a Receber nao encontrada com id = " + id);
		}
		return contaReceber;
	}

	@Override
	public void deletarContaReceberById(Long id) {
		contaReceberRepository.deleteById(id);

	}

	@Override
	public List<ContaReceber> pesquisar(String status, String pesquisa, LocalDate dataIni, LocalDate dataFim) {
		return contaReceberRepository.pesquisar(status, pesquisa, dataIni, dataFim);
	}

	@Override
	public Page<ContaReceber> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.contaReceberRepository.findAll(pageable);
	}

}
