package com.controle.controlefinanceiro.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.controle.controlefinanceiro.model.Usuario;
import com.controle.controlefinanceiro.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public List<Usuario> findByLoginContainingIgnoreCase(String login) {
		List<Usuario> usuario = usuarioRepository.findByLoginContainingIgnoreCase(login);
		return usuario;
	}

	@Override
	public void salvarUsuario(Usuario usuario) {
		usuarioRepository.save(usuario);

	}

	@Override
	public Usuario getUsuarioById(Long id) {
		Optional<Usuario> optional = usuarioRepository.findById(id);
		Usuario usuario = null;
		if (optional.isPresent()) {
			usuario = optional.get();
		} else {
			throw new RuntimeException("Usuario nao encontrado com id = " + id);
		}
		return usuario;
	}

	@Override
	public void deletarUsuarioById(Long id) {
		usuarioRepository.deleteById(id);

	}

	@Override
	public Boolean LoginUsuario(String login, String senha) {
		List<Usuario> listaUsuarios;
		listaUsuarios = usuarioRepository.loginUsuario(login, senha);
		if (listaUsuarios.isEmpty()) {
			return false;
		}else {
			return true;
		}
	}

	@Override
	public List<Usuario> pesquisar(String status, String pesquisa) {
		return usuarioRepository.pesquisar(status, pesquisa);
	}

	@Override
	public Page<Usuario> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection,
			String status) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.usuarioRepository.findAllAtivos(status, pageable);
	}

}
