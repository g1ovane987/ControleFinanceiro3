package com.controle.controlefinanceiro.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.controle.controlefinanceiro.model.SaldoMensal;
import com.controle.controlefinanceiro.repository.SaldoMensalRepository;

@Service
public class SaldoMensalServiceImpl implements SaldoMensalService {

	@Autowired
	private SaldoMensalRepository saldoMensalRepository;
	
	@Override
	public void salvarSaldoMensal(SaldoMensal saldoMensal) {
		saldoMensalRepository.save(saldoMensal);

	}


	@Override
	public SaldoMensal getSaldoMensalById(Long id) {
		Optional<SaldoMensal> optional = saldoMensalRepository.findById(id);
		SaldoMensal saldoMensal = null;
		if (optional.isPresent()) {
			saldoMensal = optional.get();
		} else {
			throw new RuntimeException("Saldo nao encontrado com id = " + id);
		}
		return saldoMensal;
	}


	@Override
	public void deletarSaldoMensalById(Long id) {
		saldoMensalRepository.deleteById(id);

	}

	@Override
	public List<SaldoMensal> pesquisar(String pesquisa) {
		return saldoMensalRepository.pesquisar(pesquisa);
	}

	@Override
	public Page<SaldoMensal> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.saldoMensalRepository.findAll(pageable);
	}

}
