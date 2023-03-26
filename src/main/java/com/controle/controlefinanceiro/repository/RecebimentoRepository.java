package com.controle.controlefinanceiro.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.controle.controlefinanceiro.model.Recebimento;

@Repository
public interface RecebimentoRepository extends JpaRepository<Recebimento, Long> {
	@Query("SELECT a FROM Recebimento a, Cliente f, Carteira b WHERE a.id_cli = f.id_cli and a.id_carteira = b.id_carteira and a.status = :status and CONCAT(f.nome, f.cpf_cnpj, b.descricao) LIKE %:pesquisa% and a.dt_pag >= :dataIni and a.dt_pag <= :dataFim")
	List<Recebimento> pesquisar(@Param("status") String status, @Param("pesquisa") String pesquisa, @Param("dataIni") LocalDate dataIni, @Param("dataFim") LocalDate dataFim);

	@Query("SELECT a FROM Recebimento a, Cliente f, Carteira b WHERE a.id_cli = f.id_cli and a.id_carteira = b.id_carteira and CONCAT(f.nome, f.cpf_cnpj, b.descricao) LIKE %:pesquisa% and a.dt_pag >= :dataIni and a.dt_pag <= :dataFim")
	List<Recebimento> pesquisarSemStatus(@Param("pesquisa") String pesquisa, @Param("dataIni") LocalDate dataIni, @Param("dataFim") LocalDate dataFim);	
	
	@Query("SELECT a FROM Recebimento a WHERE a.status = :status and a.dt_pag >= :dataIni and a.dt_pag <= :dataFim")
	List<Recebimento> pesquisarPorDatas(@Param("status") String status, @Param("dataIni") LocalDate dataIni, @Param("dataFim") LocalDate dataFim);

	@Query("SELECT a FROM Recebimento a")
	Page<Recebimento> findAll(Pageable pageable);
}
