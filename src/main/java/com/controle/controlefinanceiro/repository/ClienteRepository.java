package com.controle.controlefinanceiro.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.controle.controlefinanceiro.model.Cliente;

@Repository   
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	List<Cliente> findByNomeContainingIgnoreCase(String nome);

	@Query("SELECT a FROM Cliente a WHERE a.status = :status AND CONCAT(a.nome, a.cpf_cnpj) LIKE %:pesquisa%")
	List<Cliente> pesquisar(@Param("status") String status, @Param("pesquisa") String pesquisa);
	
	@Query("SELECT a FROM Cliente a WHERE a.status = 'ATIVO'")
	List<Cliente> findAtivos();

	@Query("SELECT a FROM Cliente a")
	Page<Cliente> findAllAtivos(@Param("status") String status, Pageable pageable);
}
