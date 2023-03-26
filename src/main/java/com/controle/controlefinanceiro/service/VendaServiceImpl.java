package com.controle.controlefinanceiro.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.controle.controlefinanceiro.model.Venda;
import com.controle.controlefinanceiro.repository.VendaRepository;

@Service
public class VendaServiceImpl implements VendaService {
	
	@Autowired
	private VendaRepository vendaRepository;

	@Override
	public void salvarVenda(Venda venda) {
		vendaRepository.save(venda);

	}

	@Override
	public Venda getVendaById(Long id) {
		Optional<Venda> optional = vendaRepository.findById(id);
		Venda venda = null;
		if (optional.isPresent()) {
			venda = optional.get();
		} else {
			throw new RuntimeException("Venda nao encontrada com id = " + id);
		}
		return venda;
	}

	@Override
	public void deletarVendaById(Long id) {
		vendaRepository.deleteById(id);

	}

	@Override
	public List<Venda> pesquisar(String status, String pesquisa) {
		return vendaRepository.pesquisar(pesquisa);
	}

	@Override
	public Page<Venda> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.vendaRepository.findAll(pageable);
	}

}
