package com.controle.controlefinanceiro.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.controle.controlefinanceiro.model.ContaReceber;

@Repository  
public interface ContaReceberRepository extends JpaRepository<ContaReceber, Long> {
	@Query("SELECT a FROM ContaReceber a, Cliente c, Carteira b WHERE a.id_cli = c.id_cli and a.id_carteira = b.id_carteira AND a.status = :status and CONCAT(c.nome, c.cpf_cnpj, b.descricao) LIKE %:pesquisa% and a.dt_vencim >= :dataIni and a.dt_vencim <= :dataFim order by dt_vencim asc")
	List<ContaReceber> pesquisar(@Param("status") String status, @Param("pesquisa") String pesquisa,@Param("dataIni") LocalDate dataIni, @Param("dataFim") LocalDate dataFim);
	
	@Query("SELECT a FROM ContaReceber a, Cliente c, Carteira b WHERE a.id_cli = c.id_cli and a.id_carteira = b.id_carteira AND CONCAT(c.nome, c.cpf_cnpj, b.descricao) LIKE %:pesquisa% and a.dt_vencim >= :dataIni and a.dt_vencim <= :dataFim order by dt_vencim asc")
	List<ContaReceber> pesquisarSemStatus(@Param("pesquisa") String pesquisa,@Param("dataIni") LocalDate dataIni, @Param("dataFim") LocalDate dataFim);

	@Query("SELECT a FROM ContaReceber a")
	Page<ContaReceber> findAll(Pageable pageable);
}
