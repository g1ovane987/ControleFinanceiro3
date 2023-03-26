package com.controle.controlefinanceiro.controller;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.controle.controlefinanceiro.model.Carteira;
import com.controle.controlefinanceiro.model.Pagamento;
import com.controle.controlefinanceiro.model.Recebimento;
import com.controle.controlefinanceiro.model.SaldoMensal;
import com.controle.controlefinanceiro.service.CarteiraService;
import com.controle.controlefinanceiro.service.SaldoMensalService;

@Controller
public class CarteiraController {
	@Autowired
	CarteiraService carteiraService;
	
	@Autowired
	SaldoMensalService saldoMensalService;
	
	@GetMapping("/indexCarteira")
	public String viewHomePage(Model model) {
		String status = "ATIVO";
		return carteirasPaginacao(1, "descricao", "asc", model, status);
	}
	
	@GetMapping("/novocarteira") 
	public String novocarteira(Model carteiraModel) {
		Carteira carteira = new Carteira();
		carteiraModel.addAttribute("carteiraView", carteira);
		return "salvarCarteira";
	}
	
	@GetMapping("/atualizarCarteira/{id}") 
	public String atualizarCarteira(@PathVariable ( value = "id") Long id, Model model) {
		Carteira carteira = carteiraService.getCarteiraById(id);
		model.addAttribute("carteiraView", carteira);
		return "salvarCarteira";
	}	
	
	@GetMapping("/alterarStatusCarteira/{id}")
	public String alterarStatusCarteira(@PathVariable (value = "id") Long id) {
			Carteira carteira = carteiraService.getCarteiraById(id);
			String status = carteira.getStatus();
			if (status.equals("ATIVO")) {
				carteira.setStatus("INATIVO");
			}else {
				carteira.setStatus("ATIVO");
			}
			carteiraService.salvarCarteira(carteira);
			return "redirect:/indexCarteira";
	}
	
	@PostMapping("/salvarCarteira")
	public String salvarCarteira(@ModelAttribute("carteira") Carteira carteira) {
		carteira.setStatus("ATIVO");
		carteiraService.salvarCarteira(carteira);
		return "redirect:/indexCarteira";
	}
	
	@Scheduled(cron = "0 23 59 28-31 * ?")
	public void fechaMes() {
	    final Calendar c = Calendar.getInstance();
	    List<Carteira> listaCarteiras = carteiraService.obterCarteiras("ATIVO");
	    Double valorTotal;
	    if (c.get(Calendar.DATE) == c.getActualMaximum(Calendar.DATE)) {
	        for (Carteira carteira : listaCarteiras) {
	        	valorTotal = (double) 0;
	            List<Recebimento> listaRecebimentos = carteiraService.obterRecebimentosCarteira("LANÇADO", carteira.getId_carteira(),LocalDate.now().withDayOfMonth(1), LocalDate.now().withMonth(LocalDate.now().getMonthValue()).with(TemporalAdjusters.lastDayOfMonth()));
	            List<Pagamento> listaPagamentos = carteiraService.obterPagamentosCarteira("LANÇADO", carteira.getId_carteira(),LocalDate.now().withDayOfMonth(1), LocalDate.now().withMonth(LocalDate.now().getMonthValue()).with(TemporalAdjusters.lastDayOfMonth()));        	
	            for (Recebimento recebimento : listaRecebimentos) {
	            	valorTotal = valorTotal + recebimento.getVlr_pago();
	            }
	            
	            for (Pagamento pagamento : listaPagamentos) {
	            	valorTotal = valorTotal - pagamento.getVlr_pago();
	            }
	            
	            SaldoMensal saldo = new SaldoMensal();
	            saldo.setId_carteira(carteira.getId_carteira());
	            saldo.setAno(LocalDate.now().getYear());
	            saldo.setMes(LocalDate.now().getMonthValue());
	            saldo.setSaldo(valorTotal);
	            saldoMensalService.salvarSaldoMensal(saldo);
	        }
	    }
	}
	
	@GetMapping("/deletarCarteira/{id}")
	public String deletarCarteira(@PathVariable (value = "id") Long id) {
		try {
			Carteira carteira = carteiraService.getCarteiraById(id);
			carteira.setStatus("INATIVO");
			carteiraService.salvarCarteira(carteira);
			return "redirect:/indexCarteira";
		}catch (Exception $e)  {			
			return "redirect:/mensagemCarteira";	
		}
	}
	
	@RequestMapping("/obterSaldoCarteira/{id_carteira}")
    public String obterSaldoCarteiraMesAtual(Model model, @Param("status") String status, @Param("id_carteira") Long id_carteira) {
        LocalDate dataAtual = LocalDate.now();
    
        Integer mes = dataAtual.minusMonths(1).getMonthValue();
        dataAtual = LocalDate.now();
        
        Double saldo = (double) 0;
        List<Recebimento> listaRecebimentos = carteiraService.obterRecebimentosCarteira("LANÇADO", id_carteira, dataAtual.withDayOfMonth(1), dataAtual);
        List<Pagamento> listaPagamentos = carteiraService.obterPagamentosCarteira("LANÇADO", id_carteira, dataAtual.withDayOfMonth(1), dataAtual);
        List<SaldoMensal> listaSaldoMensal = carteiraService.obterSaldoCarteira(mes, dataAtual.getYear(), id_carteira);
        
        for (SaldoMensal saldoMensal : listaSaldoMensal) {
        	saldo = saldo + saldoMensal.getSaldo();
        }
        
        for (Recebimento recebimento : listaRecebimentos) {
        	saldo = saldo + recebimento.getVlr_pago();
        }
        
        for (Pagamento pagamento : listaPagamentos) {
        	saldo = saldo - pagamento.getVlr_pago();
        }
        
        model.addAttribute("saldo", saldo);
        return "indexCarteira";
    }
	
    public Double obterSaldoCarteiraMesAtualInterno(@Param("status") String status, Long id_carteira) {
        LocalDate dataAtual = LocalDate.now();
    
        Integer mes = dataAtual.minusMonths(1).getMonthValue();
        dataAtual = LocalDate.now();
        
        Double saldo = (double) 0;
        List<Recebimento> listaRecebimentos = carteiraService.obterRecebimentosCarteira("LANÇADO", id_carteira, dataAtual.withDayOfMonth(1), dataAtual);
        List<Pagamento> listaPagamentos = carteiraService.obterPagamentosCarteira("LANÇADO", id_carteira, dataAtual.withDayOfMonth(1), dataAtual);
        List<SaldoMensal> listaSaldoMensal = carteiraService.obterSaldoCarteira(mes, dataAtual.getYear(), id_carteira);
        
        for (SaldoMensal saldoMensal : listaSaldoMensal) {
        	saldo = saldo + saldoMensal.getSaldo();
        }
        
        for (Recebimento recebimento : listaRecebimentos) {
        	saldo = saldo + recebimento.getVlr_pago();
        }
        
        for (Pagamento pagamento : listaPagamentos) {
        	saldo = saldo - pagamento.getVlr_pago();
        }
        
        return saldo;
    }
	
	@RequestMapping("/indexCarteira/{pesquisa}")
    public String pesquisar(Model model, @Param("pesquisa") String pesquisa) {
        List<Carteira> listaCarteiras = carteiraService.pesquisar("", pesquisa);
        model.addAttribute("listaCarteiras", listaCarteiras);
        return "indexCarteira";
    }
	
	@GetMapping("/pageCarteira/{pageNo}")
	public String carteirasPaginacao(@PathVariable (value = "pageNo") int pageNoCarteira, 
			                        @RequestParam("sortField") String sortFieldCarteira,
		                        	@RequestParam("sortDir") String sortDirCarteira,
		                        	Model model,
		                         	@Param("status") String status) {
		int pageSizeCarteira = 7;
		if (status == null) {
			status = "ATIVO";
		}
			Page<Carteira> pageCarteira = carteiraService.findPaginated(pageNoCarteira, pageSizeCarteira, sortFieldCarteira, sortDirCarteira, status);
			List<Carteira> listaCarteiras = pageCarteira.getContent();			
			model.addAttribute("currentPage", pageNoCarteira);
			model.addAttribute("totalPages", pageCarteira.getTotalPages());
			model.addAttribute("totalItems", pageCarteira.getTotalElements());
			model.addAttribute("sortField", sortFieldCarteira);
			model.addAttribute("sortDir", sortDirCarteira);
			model.addAttribute("reverseSortDir", sortDirCarteira.equals("asc") ? "desc" : "asc");
			model.addAttribute("listaCarteiras", listaCarteiras);		
		return "indexCarteira";
	}
}
