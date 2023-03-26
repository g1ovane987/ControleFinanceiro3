package com.controle.controlefinanceiro.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.controle.controlefinanceiro.model.Carteira;
import com.controle.controlefinanceiro.model.Pagamento;
import com.controle.controlefinanceiro.model.Recebimento;
import com.controle.controlefinanceiro.model.SaldoMensal;

@Repository   
public interface CarteiraRepository extends JpaRepository<Carteira, Long> {
	List<Carteira> findByDescricaoContainingIgnoreCase(String descricao);

	@Query("SELECT a FROM Carteira a WHERE a.descricao LIKE %:pesquisa%")
	List<Carteira> pesquisar(@Param("pesquisa") String pesquisa);
	
	@Query("SELECT a FROM Carteira a WHERE a.status = :status")
	List<Carteira> obterCarteiras(@Param("status") String status);
	
	@Query("SELECT a FROM Carteira a WHERE a.status = 'ATIVO'")
	List<Carteira> findAtivos();
	
	@Query("SELECT a FROM SaldoMensal a WHERE a.mes = :mes AND a.ano = :ano AND a.id_carteira = :id_carteira")
	List<SaldoMensal> obterSaldoCarteira(@Param("mes") Integer mes, @Param("ano") Integer ano, @Param("id_carteira") Long id_carteira);

	@Query("SELECT a FROM Recebimento a WHERE a.status = :status and a.id_carteira = :id_carteira and a.dt_pag >= :dataIni and a.dt_pag <= :dataFim")
	List<Recebimento> obterRecebimentosCarteira(@Param("status") String status, @Param("id_carteira") Long id_carteira, @Param("dataIni") LocalDate dataIni, @Param("dataFim") LocalDate dataFim);
	
	@Query("SELECT a FROM Pagamento a WHERE a.status = :status and a.id_carteira = :id_carteira and a.dt_pag >= :dataIni and a.dt_pag <= :dataFim")
	List<Pagamento> obterPagamentosCarteira(@Param("status") String status, @Param("id_carteira") Long id_carteira, @Param("dataIni") LocalDate dataIni, @Param("dataFim") LocalDate dataFim);
	
	@Query("SELECT a FROM Carteira a")
	Page<Carteira> findAllAtivos(@Param("status") String status, Pageable pageable);
}
