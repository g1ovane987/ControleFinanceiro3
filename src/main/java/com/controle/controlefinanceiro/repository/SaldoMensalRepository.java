package com.controle.controlefinanceiro.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.controle.controlefinanceiro.model.SaldoMensal;

@Repository
public interface SaldoMensalRepository extends JpaRepository<SaldoMensal, Long> {
	@Query("SELECT a FROM SaldoMensal a WHERE CONCAT(a.mes, a.ano) LIKE %:pesquisa%")
	List<SaldoMensal> pesquisar(@Param("pesquisa") String pesquisa);

	@Query("SELECT a FROM SaldoMensal a")
	Page<SaldoMensal> findAll(Pageable pageable);
}
