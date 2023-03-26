package com.controle.controlefinanceiro.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.controle.controlefinanceiro.model.Venda;

@Repository 
public interface VendaRepository extends JpaRepository<Venda, Long> {
	@Query("SELECT a FROM Venda a, Cliente f WHERE a.id_cliente = f.id_cli and CONCAT(f.nome, f.cpf_cnpj) LIKE %:pesquisa%")
	List<Venda> pesquisar(@Param("pesquisa") String pesquisa);

	@Query("SELECT a FROM Venda a")
	Page<Venda> findAll(Pageable pageable);
}
