package com.controle.controlefinanceiro.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.controle.controlefinanceiro.model.FormaDePagamento;

@Repository
public interface FormaDePagamentoRepository extends JpaRepository<FormaDePagamento, Long> {
	List<FormaDePagamento> findBydescricaoContainingIgnoreCase(String descricao);

	@Query("SELECT a FROM FormaDePagamento a WHERE a.descricao LIKE %:pesquisa%")
	List<FormaDePagamento> pesquisar(@Param("pesquisa") String pesquisa);
	
	@Query("SELECT a FROM FormaDePagamento a WHERE a.status = 'ATIVO'")
	List<FormaDePagamento> findAtivos();

	@Query("SELECT a FROM FormaDePagamento a")
	Page<FormaDePagamento> findAllAtivos(@Param("status") String status, Pageable pageable);
}
