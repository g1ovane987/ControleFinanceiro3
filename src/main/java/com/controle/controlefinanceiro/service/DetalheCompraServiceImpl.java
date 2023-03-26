package com.controle.controlefinanceiro.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.controle.controlefinanceiro.model.DetalheCompra;
import com.controle.controlefinanceiro.repository.DetalheCompraRepository;

@Service
public class DetalheCompraServiceImpl implements DetalheCompraService {
	@Autowired
	private DetalheCompraRepository detalheCompraRepository;

	@Override
	public void salvarDetalheCompra(DetalheCompra detalheCompra) {
		detalheCompraRepository.save(detalheCompra);

	}

	@Override
	public DetalheCompra getDetalheCompraById(Long id) {
		Optional<DetalheCompra> optional = detalheCompraRepository.findById(id);
		DetalheCompra detalheCompra = null;
		if (optional.isPresent()) {
			detalheCompra = optional.get();
		} else {
			throw new RuntimeException("Detalhe Compra nao encontrado com id = " + id);
		}
		return detalheCompra;
	}

	@Override
	public void deletarDetalheCompraById(Long id) {
		detalheCompraRepository.deleteById(id);

	}

	@Override
	public Page<DetalheCompra> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection,
			Integer id) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.detalheCompraRepository.findAll(id, pageable);
	}

}
