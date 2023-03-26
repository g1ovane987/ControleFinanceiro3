package com.controle.controlefinanceiro.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.controle.controlefinanceiro.model.Cliente;

@Service
public interface ClienteService {
	List<Cliente> findByNomeContainingIgnoreCase(String nome);
	
	void salvarCliente(Cliente cliente);

	Cliente getClienteById(Long id);

	void deletarClienteById(Long id);
	
	List<Cliente> pesquisar(String status, String pesquisa);
	
	Page<Cliente> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection, String status);
}
