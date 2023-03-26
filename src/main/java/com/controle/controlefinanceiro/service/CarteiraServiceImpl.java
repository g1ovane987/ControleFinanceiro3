package com.controle.controlefinanceiro.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.controle.controlefinanceiro.model.Carteira;
import com.controle.controlefinanceiro.model.Pagamento;
import com.controle.controlefinanceiro.model.Recebimento;
import com.controle.controlefinanceiro.model.SaldoMensal;
import com.controle.controlefinanceiro.repository.CarteiraRepository;

@Service
public class CarteiraServiceImpl implements CarteiraService {
	
	@Autowired
	private CarteiraRepository carteiraRepository;

	@Override
	public List<Carteira> findByDescricaoContainingIgnoreCase(String descricao) {
		List<Carteira> carteira = carteiraRepository.findByDescricaoContainingIgnoreCase(descricao);
		return carteira;
	}

	@Override
	public List<Carteira> pesquisar(String status, String pesquisa) {
		return carteiraRepository.pesquisar(pesquisa);	
	}
	
	@Override
	public void salvarCarteira(Carteira carteira) {
		carteiraRepository.save(carteira);	
	}
	
	@Override
	public List<Carteira> obterCarteiras(String status) {
		return carteiraRepository.obterCarteiras(status);
	}

	@Override
	public List<SaldoMensal> obterSaldoCarteira(Integer mes, Integer ano, Long id_carteira) {
		return carteiraRepository.obterSaldoCarteira(mes, ano, id_carteira);
	}

	@Override
	public List<Recebimento> obterRecebimentosCarteira(String status, Long id_carteira, LocalDate dataIni,
			LocalDate datafim) {
		return carteiraRepository.obterRecebimentosCarteira(status, id_carteira, dataIni, datafim);
	}

	@Override
	public List<Pagamento> obterPagamentosCarteira(String status, Long id_carteira, LocalDate dataIni,
			LocalDate datafim) {
		return carteiraRepository.obterPagamentosCarteira(status, id_carteira, dataIni, datafim);
	}

	@Override
	public Carteira getCarteiraById(Long id) {
		Optional<Carteira> optional = carteiraRepository.findById(id);
		Carteira carteira = null;
		if (optional.isPresent()) {
			carteira = optional.get();
		} else {
			throw new RuntimeException("Carteira nao encontrada com id = " + id);
		}
		return carteira;
	}

	@Override
	public void deletarCarteiraById(Long id) {
		carteiraRepository.deleteById(id);
		
	}

	@Override
	public Page<Carteira> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection,
			String status) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.carteiraRepository.findAllAtivos(status, pageable);
	}

}
