package com.controle.controlefinanceiro.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.controle.controlefinanceiro.model.DetalheVenda;
import com.controle.controlefinanceiro.repository.DetalheVendaRepository;

@Service
public class DetalheVendaServiceImpl implements DetalheVendaService {
	@Autowired
	private DetalheVendaRepository detalheVendaRepository;

	@Override
	public void salvarDetalheVenda(DetalheVenda detalheVenda) {
		detalheVendaRepository.save(detalheVenda);

	}

	@Override
	public DetalheVenda getDetalheVendaById(Long id) {
		Optional<DetalheVenda> optional = detalheVendaRepository.findById(id);
		DetalheVenda detalheVenda = null;
		if (optional.isPresent()) {
			detalheVenda = optional.get();
		} else {
			throw new RuntimeException("Detalhe Venda nao encontrado com id = " + id);
		}
		return detalheVenda;
	}

	@Override
	public void deletarDetalheVendaById(Long id) {
		detalheVendaRepository.deleteById(id);

	}

	@Override
	public Page<DetalheVenda> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection,
			Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}
