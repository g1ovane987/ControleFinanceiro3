package com.controle.controlefinanceiro.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.controle.controlefinanceiro.model.Fornecedor;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
	List<Fornecedor> findByNomeContainingIgnoreCase(String nome);

	@Query("SELECT a FROM Fornecedor a WHERE CONCAT(a.nome, a.cpf_cnpj) LIKE %:pesquisa%")
	List<Fornecedor> pesquisar(@Param("pesquisa") String pesquisa);
	
	@Query("SELECT a FROM Fornecedor a WHERE a.status = 'ATIVO'")
	List<Fornecedor> findAtivos();

	@Query("SELECT a FROM Fornecedor a")
	Page<Fornecedor> findAllAtivos(@Param("status") String status, Pageable pageable);
}
