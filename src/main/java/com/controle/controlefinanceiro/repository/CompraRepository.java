package com.controle.controlefinanceiro.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.controle.controlefinanceiro.model.Compra;

@Repository  
public interface CompraRepository extends JpaRepository<Compra, Long> {
	@Query("SELECT a FROM Compra a, Fornecedor f WHERE a.id_for = f.id_for and CONCAT(f.nome, f.cpf_cnpj) LIKE %:pesquisa%")
	List<Compra> pesquisar(@Param("pesquisa") String pesquisa);

	@Query("SELECT a FROM Compra a")
	Page<Compra> findAll(Pageable pageable);
}
