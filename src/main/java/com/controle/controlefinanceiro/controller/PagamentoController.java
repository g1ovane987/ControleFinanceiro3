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
import com.controle.controlefinanceiro.model.Fornecedor;
import com.controle.controlefinanceiro.model.Pagamento;
import com.controle.controlefinanceiro.repository.CarteiraRepository;
import com.controle.controlefinanceiro.repository.FornecedorRepository;
import com.controle.controlefinanceiro.repository.PagamentoRepository;
import com.controle.controlefinanceiro.service.PagamentoService;

@Controller
public class PagamentoController {
	@Autowired
	PagamentoService pagamentoService;
	
	@Autowired
	FornecedorRepository fornecedorRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private CarteiraRepository carteiraRepository;
	
	@GetMapping("/indexPagamento")
	public String viewHomePage(Model model) {
		List<Fornecedor> listFornecedor = fornecedorRepository.findAtivos();
		model.addAttribute("listFornecedor", listFornecedor);
        List<Carteira> listaCarteiras = carteiraRepository.findAtivos();
        model.addAttribute("listaCarteiras", listaCarteiras);
        
    	Double totalLancado = (double) 0;
    	Double totalCancelado = (double) 0;        
    	List<Double> listLancado = new ArrayList<>();
    	List<Double> listCancelado = new ArrayList<>();
    	listLancado.add(totalLancado);
    	listCancelado.add(totalCancelado);
    	model.addAttribute("listLancado", listLancado);
    	model.addAttribute("listCancelado", listCancelado);           
        
		return pagamentosPaginacao(1, "id_pag", "desc", model);
	}
	
	@RequestMapping("/indexPagamento/{pesquisa}")
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
        	model.addAttribute("listaPagamentos", pagamentoService.pesquisar(status, pesquisa,dataIniLocal,dataFimLocal));
        	List<Pagamento> list = pagamentoService.pesquisar(status, pesquisa, dataIniLocal, dataFimLocal);
        	Double totalLancado = (double) 0;
        	Double totalCancelado = (double) 0;
        	for (Pagamento obj : list) {
        		if (obj.getStatus().equals("LANÇADO")){
        			totalLancado = totalLancado + obj.getVlr_pago();
        		}else {
        			totalCancelado = totalCancelado + obj.getVlr_pago();
        		}
        	}
        	List<Double> listLancado = new ArrayList<>();
        	List<Double> listCancelado = new ArrayList<>();
        	listLancado.add(totalLancado);
        	listCancelado.add(totalCancelado);
        	model.addAttribute("listLancado", listLancado);
        	model.addAttribute("listCancelado", listCancelado);     
        }else {
        	model.addAttribute("listaPagamentos", pagamentoRepository.pesquisarSemStatus(pesquisa, dataIniLocal, dataFimLocal));
        	List<Pagamento> list = pagamentoRepository.pesquisarSemStatus(pesquisa, dataIniLocal, dataFimLocal);
        	Double totalLancado = (double) 0;
        	Double totalCancelado = (double) 0;
        	for (Pagamento obj : list) {
        		if (obj.getStatus().equals("LANÇADO")){
        			totalLancado = totalLancado + obj.getVlr_pago();
        		}else {
        			totalCancelado = totalCancelado + obj.getVlr_pago();
        		}
        	}
        	List<Double> listLancado = new ArrayList<>();
        	List<Double> listCancelado = new ArrayList<>();
        	listLancado.add(totalLancado);
        	listCancelado.add(totalCancelado);
        	model.addAttribute("listLancado", listLancado);
        	model.addAttribute("listCancelado", listCancelado);         	
        }        
        
        return "indexPagamento";
    }
	
	@GetMapping("/novopagamento") 
	public String novopagamento(Model pagModel) {
		Pagamento pagamento = new Pagamento();
		List<Fornecedor> listFornecedor = fornecedorRepository.findAtivos();
		pagModel.addAttribute("listFornecedor", listFornecedor);
        List<Carteira> listaCarteiras = carteiraRepository.findAtivos();
        pagModel.addAttribute("listaCarteiras", listaCarteiras);
        pagModel.addAttribute("pagamentoView", pagamento);
		return "salvarPagamento";
	}	
	
	@PostMapping("/salvarPagamento")
	public String salvarPagamento(@ModelAttribute("pagamento") Pagamento pagamento) {
		if ((pagamento.getVlr_pago() <= 0) || (pagamento.getId_for() == null) || (pagamento.getDt_pag().isBefore(LocalDate.now()))) {
			return "pagamentoErro";
		}else {
			pagamento.setStatus("LANÇADO");
			pagamentoService.salvarPagamento(pagamento);
			return "redirect:/indexPagamento";
		}
	}
	
	@GetMapping("/deletarPagamento/{id}")
	public String deletarPagamento(@PathVariable (value = "id") Long id) {
		try {
			Pagamento pagamento = pagamentoService.getPagamentoById(id);
			if (pagamento.getStatus() != "CANCELADO") {
				pagamento.setStatus("CANCELADO");
				pagamentoService.salvarPagamento(pagamento);
			}
			return "redirect:/indexPagamento";
		}catch (Exception $e)  {			
			return "redirect:/mensagemPagamento";	
		}
	}
	
	@GetMapping("/pagePagamento/{pageNo}")
	public String pagamentosPaginacao(@PathVariable (value = "pageNo") int pageNoPagamento, 
			                        @RequestParam("sortField") String sortFieldPagamento,
		                        	@RequestParam("sortDir") String sortDirPagamento,
		                        	Model model) {
		int pageSizePagamento = 7;
			Page<Pagamento> pagePagamento = pagamentoService.findPaginated(pageNoPagamento, pageSizePagamento, sortFieldPagamento, sortDirPagamento);
			List<Pagamento> listaPagamentos = pagePagamento.getContent();			
			model.addAttribute("currentPage", pageNoPagamento);
			model.addAttribute("totalPages", pagePagamento.getTotalPages());
			model.addAttribute("totalItems", pagePagamento.getTotalElements());
			model.addAttribute("sortField", sortFieldPagamento);
			model.addAttribute("sortDir", sortDirPagamento);
			model.addAttribute("reverseSortDir", sortDirPagamento.equals("asc") ? "desc" : "asc");
			model.addAttribute("listaPagamentos", listaPagamentos);		
		return "indexPagamento";
	}
}
