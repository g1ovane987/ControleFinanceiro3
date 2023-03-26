package com.controle.controlefinanceiro.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.controle.controlefinanceiro.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	List<Usuario> findByLoginContainingIgnoreCase(String login);

	@Query("SELECT a FROM Usuario a WHERE a.status = :status AND a.login LIKE %:pesquisa%")
	List<Usuario> pesquisar(@Param("status") String status, @Param("pesquisa") String pesquisa);
	
	@Query("SELECT a FROM Usuario a WHERE a.status = 'ATIVO' AND a.login LIKE %:login% AND a.senha LIKE %:senha%")
	List<Usuario> loginUsuario(@Param("login") String login, @Param("senha") String senha);

	@Query("SELECT a FROM Usuario a WHERE a.status = :status")
	Page<Usuario> findAllAtivos(@Param("status") String status, Pageable pageable);
}
