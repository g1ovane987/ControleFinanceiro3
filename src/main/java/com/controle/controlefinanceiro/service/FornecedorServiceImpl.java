package com.controle.controlefinanceiro.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.controle.controlefinanceiro.model.Fornecedor;
import com.controle.controlefinanceiro.repository.FornecedorRepository;

@Service
public class FornecedorServiceImpl implements FornecedorService {
	
	@Autowired
	private FornecedorRepository fornecedorRepository;

	@Override
	public List<Fornecedor> findByNomeContainingIgnoreCase(String nome) {
		List<Fornecedor> fornecedor = fornecedorRepository.findByNomeContainingIgnoreCase(nome);
		return fornecedor;
	}

	@Override
	public void salvarFornecedor(Fornecedor fornecedor) {
		fornecedorRepository.save(fornecedor);

	}

	@Override
	public Fornecedor getFornecedorById(Long id) {
		Optional<Fornecedor> optional = fornecedorRepository.findById(id);
		Fornecedor fornecedor = null;
		if (optional.isPresent()) {
			fornecedor = optional.get();
		} else {
			throw new RuntimeException("Fornecedor nao encontrado com id = " + id);
		}
		return fornecedor;
	}

	@Override
	public void deletarFornecedorById(Long id) {
		fornecedorRepository.deleteById(id);

	}

	@Override
	public List<Fornecedor> pesquisar(String status, String pesquisa) {
		return fornecedorRepository.pesquisar(pesquisa);
	}

	@Override
	public Page<Fornecedor> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection,
			String status) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.fornecedorRepository.findAllAtivos(status, pageable);
	}

}
