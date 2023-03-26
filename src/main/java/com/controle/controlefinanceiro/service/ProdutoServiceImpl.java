package com.controle.controlefinanceiro.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.controle.controlefinanceiro.model.Produto;
import com.controle.controlefinanceiro.repository.ProdutoRepository;

@Service
public class ProdutoServiceImpl implements ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;

	@Override
	public List<Produto> findByDescricaoContainingIgnoreCase(String descricao) {
		List<Produto> produto = produtoRepository.findByDescricaoContainingIgnoreCase(descricao);
		return produto;
	}

	@Override
	public void salvarProduto(Produto produto) {
		produtoRepository.save(produto);

	}

	@Override
	public Produto getProdutoById(Long id) {
		Optional<Produto> optional = produtoRepository.findById(id);
		Produto produto = null;
		if (optional.isPresent()) {
			produto = optional.get();
		} else {
			throw new RuntimeException("Produto nao encontrado com id = " + id);
		}
		return produto;
	}

	@Override
	public void deletarProdutoById(Long id) {
		produtoRepository.deleteById(id);

	}

	@Override
	public List<Produto> pesquisar(String status, String pesquisa) {
		return produtoRepository.pesquisar(pesquisa);
	}

	@Override
	public Page<Produto> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection,
			String status) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.produtoRepository.findAllAtivos(status, pageable);
	}

}
