package com.controle.controlefinanceiro.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.controle.controlefinanceiro.model.Compra;
import com.controle.controlefinanceiro.repository.CompraRepository;

@Service
public class CompraServiceImpl implements CompraService {
	
	@Autowired
	private CompraRepository compraRepository;

	@Override
	public void salvarCompra(Compra compra) {
		compraRepository.save(compra);	

	}

	@Override
	public Compra getCompraById(Long id) {
		Optional<Compra> optional = compraRepository.findById(id);
		Compra compra = null;
		if (optional.isPresent()) {
			compra = optional.get();
		} else {
			throw new RuntimeException("Compra nao encontrada com id = " + id);
		}
		return compra;
	}

	@Override
	public void deletarCompraById(Long id) {
		compraRepository.deleteById(id);

	}

	@Override
	public List<Compra> pesquisar(String status, String pesquisa) {
		return compraRepository.pesquisar(pesquisa);	
	}

	@Override
	public Page<Compra> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.compraRepository.findAll(pageable);
	}

}
