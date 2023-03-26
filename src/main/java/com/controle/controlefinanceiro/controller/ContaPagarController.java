package com.controle.controlefinanceiro.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.controle.controlefinanceiro.model.Carteira;
import com.controle.controlefinanceiro.model.ContaPagar;
import com.controle.controlefinanceiro.model.ContaReceber;
import com.controle.controlefinanceiro.model.Fornecedor;
import com.controle.controlefinanceiro.model.Pagamento;
import com.controle.controlefinanceiro.repository.CarteiraRepository;
import com.controle.controlefinanceiro.repository.ContaPagarRepository;
import com.controle.controlefinanceiro.repository.FornecedorRepository;
import com.controle.controlefinanceiro.service.ContaPagarService;
import com.controle.controlefinanceiro.service.PagamentoService;

@Controller
public class ContaPagarController {

	@Autowired
	ContaPagarService contaPagarService;
	
	@Autowired
	PagamentoService pagamentoService;
	
	@Autowired
	private CarteiraRepository carteiraRepository;
	
	@Autowired
	FornecedorRepository fornecedorRepository;
	
	@Autowired
	private ContaPagarRepository capRepository;
	
	@GetMapping("/indexContaPagar")
	public String viewHomePage(Model model) {
		List<Fornecedor> listFornecedor = fornecedorRepository.findAtivos();
		model.addAttribute("listFornecedor", listFornecedor);
        List<Carteira> listaCarteiras = carteiraRepository.findAtivos();
        model.addAttribute("listaCarteiras", listaCarteiras);
        
    	Double totalLancado = (double) 0;
    	Double totalQuitado = (double) 0;
    	Double totalCancelado = (double) 0;        
    	List<Double> listLancado = new ArrayList<>();
    	List<Double> listQuitado = new ArrayList<>();
    	List<Double> listCancelado = new ArrayList<>();
    	listLancado.add(totalLancado);
    	listQuitado.add(totalQuitado);
    	listCancelado.add(totalCancelado);
    	model.addAttribute("listLancado", listLancado);
    	model.addAttribute("listQuitado", listQuitado);
    	model.addAttribute("listCancelado", listCancelado);  
    	
		return contasPagarPaginacao(1, "id_cap", "desc", model);
	}
	
	@GetMapping("/novocontapagar") 
	public String novocontapagar(Model contaPagarModel) {
		ContaPagar contaPagar = new ContaPagar();
		List<Fornecedor> listFornecedor = fornecedorRepository.findAtivos();
		contaPagarModel.addAttribute("listFornecedor", listFornecedor);
        List<Carteira> listaCarteiras = carteiraRepository.findAtivos();
        contaPagarModel.addAttribute("listaCarteiras", listaCarteiras);
		contaPagarModel.addAttribute("contapagarView", contaPagar);
		return "salvarContaPagar";
	}
	

	@RequestMapping("/indexContaPagar/{pesquisa}")
    public String pesquisar(Model model, @Param("status") String status, @Param("pesquisa") String pesquisa, @Param("dataIni") String dataIni, @Param("dataFim") String dataFim) {
		List<Fornecedor> listFornecedor = fornecedorRepository.findAtivos();
		model.addAttribute("listFornecedor", listFornecedor);
        List<Carteira> listaCarteiras = carteiraRepository.findAtivos();
        model.addAttribute("listaCarteiras", listaCarteiras);
        LocalDate dataIniLocal = LocalDate.now();
        LocalDate dataFimLocal = LocalDate.now();
        if (dataIni.equals("")) {
        	System.out.println("null ini");
        	dataIniLocal = LocalDate.now();
        }else {
        	System.out.println(dataIni);
        	dataIniLocal = LocalDate.parse(dataIni);
        }
        if (dataFim.equals("")) {
        	dataFimLocal = LocalDate.now().plusYears(1);
        }else {
        	dataFimLocal = LocalDate.parse(dataFim);
        }
        if (status == null) {
        	status = "TODOS";
        }
        
        if (!status.equals("TODOS")) {
        	model.addAttribute("listaContasPagar", contaPagarService.pesquisar(status, pesquisa,dataIniLocal,dataFimLocal));
        	List<ContaPagar> list = contaPagarService.pesquisar(status, pesquisa, dataIniLocal, dataFimLocal);
        	Double totalLancado = (double) 0;
        	Double totalQuitado = (double) 0;
        	Double totalCancelado = (double) 0;
        	for (ContaPagar obj : list) {
        		if (obj.getStatus().equals("LANÇADO")){
        			totalLancado = totalLancado + obj.getValor();
        		}else if (obj.getStatus().equals("QUITADO")) {
        			totalQuitado = totalQuitado + obj.getValor();
        		}else {
        			totalCancelado = totalCancelado + obj.getValor();
        		}
        	}
        	List<Double> listLancado = new ArrayList<>();
        	List<Double> listQuitado = new ArrayList<>();
        	List<Double> listCancelado = new ArrayList<>();
        	listLancado.add(totalLancado);
        	listQuitado.add(totalQuitado);
        	listCancelado.add(totalCancelado);
        	model.addAttribute("listLancado", listLancado);
        	model.addAttribute("listQuitado", listQuitado);
        	model.addAttribute("listCancelado", listCancelado);        	
        }else {
        	model.addAttribute("listaContasPagar", capRepository.pesquisarSemStatus(pesquisa, dataIniLocal, dataFimLocal));
        	List<ContaPagar> list = capRepository.pesquisarSemStatus(pesquisa, dataIniLocal, dataFimLocal);
        	Double totalLancado = (double) 0;
        	Double totalQuitado = (double) 0;
        	Double totalCancelado = (double) 0;
        	for (ContaPagar obj : list) {
        		if (obj.getStatus().equals("LANÇADO")){
        			totalLancado = totalLancado + obj.getValor();
        		}else if (obj.getStatus().equals("QUITADO")) {
        			totalQuitado = totalQuitado + obj.getValor();
        		}else {
        			totalCancelado = totalCancelado + obj.getValor();
        		}
        	}
        	List<Double> listLancado = new ArrayList<>();
        	List<Double> listQuitado = new ArrayList<>();
        	List<Double> listCancelado = new ArrayList<>();
        	listLancado.add(totalLancado);
        	listQuitado.add(totalQuitado);
        	listCancelado.add(totalCancelado);
        	model.addAttribute("listLancado", listLancado);
        	model.addAttribute("listQuitado", listQuitado);
        	model.addAttribute("listCancelado", listCancelado);           	
        }
        return "indexContaPagar";
    }
	
	@PostMapping("/salvarContaPagar")
	public String salvarContaPagar(@ModelAttribute("contaPagar") ContaPagar contaPagar) {
		if ((contaPagar.getValor() <= 0) || (contaPagar.getId_for() == null) || (contaPagar.getDt_vencim().isBefore(LocalDate.now()))) {
			return "contaPagarErro";
		}else {
			contaPagar.setStatus("LANÇADO");
		}
		contaPagarService.salvarContaPagar(contaPagar);
		return "redirect:/indexContaPagar";
	}
//	@RequestMapping("/indexContaPagar/{pesquisa}")
//    public String pesquisar(Model model, @Param("status") String status, @Param("pesquisa") String pesquisa) {
//        List<ContaPagar> listaContasPagar = ContaPagarService.pesquisar(status, pesquisa);
//        model.addAttribute("listaContasPagar", listaContasPagar);
//        return "indexContaPagar";
//    }
	
	@GetMapping("/gerarPagamento/{id}")
	public String gerarPagamento(@PathVariable (value = "id") Long id) {
		try {
		LocalDate dataContaPagar;
		ContaPagar contaPagar = contaPagarService.getContaPagarById(id);
		if (contaPagar.getId_carteira() > 0) {
			contaPagar.setStatus("QUITADO");
			contaPagarService.salvarContaPagar(contaPagar);	
			dataContaPagar = LocalDate.now();
			Pagamento pagamento = new Pagamento();
			pagamento.setDt_pag(dataContaPagar);
			pagamento.setId_for(contaPagar.getId_for());
			pagamento.setId_cap(contaPagar.getId_cap());
			pagamento.setObs("GERADO ATRAVÉS DA CONTA A PAGAR");
			pagamento.setStatus("LANÇADO");
			pagamento.setId_carteira(contaPagar.getId_carteira());
			pagamento.setVlr_pago(contaPagar.getValor());
			pagamentoService.salvarPagamento(pagamento);
		}
		return "redirect:/indexContaPagar";
		}catch (Exception $e)  {			
			return "redirect:/mensagemContaPagar";	
		}
	}	
	
	@GetMapping("/deletarContaPagar/{id}")
	public String deletarContaPagar(@PathVariable (value = "id") Long id) {
		try {
			ContaPagar contaPagar = contaPagarService.getContaPagarById(id);
			if (contaPagar.getStatus() != "QUITADO") {
				contaPagar.setStatus("CANCELADO");
				contaPagarService.salvarContaPagar(contaPagar);
			}
			return "redirect:/indexContaPagar";
		}catch (Exception $e)  {			
			return "redirect:/mensagemContaPagar";	
		}
	}
	
	@GetMapping("/pageContaPagar/{pageNo}")
	public String contasPagarPaginacao(@PathVariable (value = "pageNo") int pageNoContaPagar, 
			                        @RequestParam("sortField") String sortFieldContaPagar,
		                        	@RequestParam("sortDir") String sortDirContaPagar,
		                        	Model model) {
		int pageSizeContaPagar = 7;
			Page<ContaPagar> pageContaPagar = contaPagarService.findPaginated(pageNoContaPagar, pageSizeContaPagar, sortFieldContaPagar, sortDirContaPagar);
			List<ContaPagar> listaContasPagar = pageContaPagar.getContent();			
			model.addAttribute("currentPage", pageNoContaPagar);
			model.addAttribute("totalPages", pageContaPagar.getTotalPages());
			model.addAttribute("totalItems", pageContaPagar.getTotalElements());
			model.addAttribute("sortField", sortFieldContaPagar);
			model.addAttribute("sortDir", sortDirContaPagar);
			model.addAttribute("reverseSortDir", sortDirContaPagar.equals("asc") ? "desc" : "asc");
			model.addAttribute("listaContasPagar", listaContasPagar);		
		return "indexContaPagar";
	}
	
}
