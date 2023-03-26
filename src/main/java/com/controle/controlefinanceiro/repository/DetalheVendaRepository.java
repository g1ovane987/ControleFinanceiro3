package com.controle.controlefinanceiro.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.controle.controlefinanceiro.model.DetalheVenda;

@Repository
public interface DetalheVendaRepository extends JpaRepository<DetalheVenda, Long> {
	@Query("SELECT a FROM DetalheVenda a WHERE a.id_venda = :id")
	Page<DetalheVenda> findAll(@Param("id") Integer id, Pageable pageable);
}
