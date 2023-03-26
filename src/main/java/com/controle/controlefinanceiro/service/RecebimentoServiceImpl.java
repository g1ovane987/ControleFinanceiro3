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

import com.controle.controlefinanceiro.model.Recebimento;
import com.controle.controlefinanceiro.repository.RecebimentoRepository;

@Service
public class RecebimentoServiceImpl implements RecebimentoService {
	
	@Autowired
	private RecebimentoRepository recebimentoRepository;

	@Override
	public void salvarRecebimento(Recebimento recebimento) {
		recebimentoRepository.save(recebimento);
	}

	@Override
	public Recebimento getRecebimentoById(Long id) {
		Optional<Recebimento> optional = recebimentoRepository.findById(id);
		Recebimento recebimento = null;
		if (optional.isPresent()) {
			recebimento = optional.get();
		} else {
			throw new RuntimeException("Recebimento nao encontrado com id = " + id);
		}
		return recebimento;
	}

	@Override
	public void deletarRecebimentoById(Long id) {
		recebimentoRepository.deleteById(id);

	}

	@Override
	public List<Recebimento> pesquisarPorData(String status, LocalDate dataIni, LocalDate dataFim) {
		return recebimentoRepository.pesquisarPorDatas(status, dataIni, dataFim);	
	}

	@Override
	public List<Recebimento> pesquisar(String status, String pesquisa, LocalDate dataIni, LocalDate dataFim) {
		return recebimentoRepository.pesquisar(status, pesquisa, dataIni, dataFim);	
	}

	@Override
	public Page<Recebimento> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.recebimentoRepository.findAll(pageable);
	}

}
