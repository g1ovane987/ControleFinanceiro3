package com.controle.controlefinanceiro.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.controle.controlefinanceiro.model.DetalheCompra;

@Repository
public interface DetalheCompraRepository extends JpaRepository<DetalheCompra, Long> {
	@Query("SELECT a FROM DetalheCompra a WHERE a.id_compra = :id")
	Page<DetalheCompra> findAll(@Param("id") Integer id, Pageable pageable);
}
