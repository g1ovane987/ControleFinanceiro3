package com.controle.controlefinanceiro.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.controle.controlefinanceiro.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	List<Produto> findByDescricaoContainingIgnoreCase(String descricao);

	@Query("SELECT a FROM Produto a WHERE a.descricao LIKE %:pesquisa%")
	List<Produto> pesquisar(@Param("pesquisa") String pesquisa);
	
	@Query("SELECT a FROM Produto a WHERE a.status = 'ATIVO'")
	List<Produto> findAll();

	@Query("SELECT a FROM Produto a")
	Page<Produto> findAllAtivos(@Param("status") String status, Pageable pageable);
}
