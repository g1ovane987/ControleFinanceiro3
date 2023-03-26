package com.controle.controlefinanceiro.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.controle.controlefinanceiro.model.Usuario;

@Service
public interface UsuarioService {
	List<Usuario> findByLoginContainingIgnoreCase(String login);
	
	void salvarUsuario(Usuario usuario);

	Usuario getUsuarioById(Long id);

	void deletarUsuarioById(Long id);
	
	List<Usuario> pesquisar(String status, String pesquisa);
	
	Page<Usuario> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection, String status);
	
	Boolean LoginUsuario(String login, String senha);
}
