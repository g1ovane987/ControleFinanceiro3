package com.controle.controlefinanceiro.controller;

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

import com.controle.controlefinanceiro.model.FormaDePagamento;

import com.controle.controlefinanceiro.service.FormaDePagamentoService;

@Controller
public class FormaDePagamentoController {

	@Autowired
	FormaDePagamentoService formaDePagamentoService;
	
	@GetMapping("/indexFormaDePagamento")
	public String viewHomePage(Model model) {
		String status = "ATIVO";
		return formasDePagamentoPaginacao(1, "descricao", "asc", model, status);
	}
	
	@PostMapping("/salvarFormaDePagamento")
	public String salvarFormaDePagamento(@ModelAttribute("formaDePagamento") FormaDePagamento formaDePagamento) {
		formaDePagamento.setStatus("ATIVO");
		formaDePagamentoService.salvarFormaDePagamento(formaDePagamento);
		return "redirect:/indexFormaDePagamento";
	}
	
	@GetMapping("/novoformaDePagamento") 
	public String novoFormaDePagamento(Model formaDePagamentoModel) {
		FormaDePagamento formaDePagamento = new FormaDePagamento();
		formaDePagamentoModel.addAttribute("formaDePagamentoView", formaDePagamento);
		return "salvarFormaDePagamento";
	}
	
	@GetMapping("/atualizarFpag/{id}") 
	public String atualizarFpag(@PathVariable ( value = "id") Long id, Model model) {
		FormaDePagamento formaDePagamento = formaDePagamentoService.getFormaDePagamentoById(id);
		model.addAttribute("formaDePagamentoView", formaDePagamento);
		return "salvarFormaDePagamento";
	}	
	
	@GetMapping("/deletarFormaDePagamento/{id}")
	public String deletarFormaDePagamento(@PathVariable (value = "id") Long id) {
		try {
			FormaDePagamento formaDePagamento = formaDePagamentoService.getFormaDePagamentoById(id);
			formaDePagamento.setStatus("INATIVO");
			formaDePagamentoService.salvarFormaDePagamento(formaDePagamento);
			return "redirect:/indexFormaDePagamento";
		}catch (Exception $e)  {			
			return "redirect:/mensagemFormaDePagamento";	
		}
	}
	
	@GetMapping("/alterarStatusFpag/{id}")
	public String alterarStatusFpag(@PathVariable (value = "id") Long id) {
			FormaDePagamento formaDePagamento = formaDePagamentoService.getFormaDePagamentoById(id);
			String status = formaDePagamento.getStatus();
			if (status.equals("ATIVO")) {
				formaDePagamento.setStatus("INATIVO");
			}else {
				formaDePagamento.setStatus("ATIVO");
			}
			formaDePagamentoService.salvarFormaDePagamento(formaDePagamento);
			return "redirect:/indexFormaDePagamento";
	}
	
	@RequestMapping("/indexFormaDePagamento/{pesquisa}")
    public String pesquisar(Model model, @Param("status") String status, @Param("pesquisa") String pesquisa) {
        List<FormaDePagamento> listaFormaDePagamentos = formaDePagamentoService.pesquisar(status, pesquisa);
        model.addAttribute("listaFormaDePagamentos", listaFormaDePagamentos);
        return "indexFormaDePagamento";
    }
	
	@GetMapping("/pageFormaDePagamento/{pageNo}")
	public String formasDePagamentoPaginacao(@PathVariable (value = "pageNo") int pageNoFormaDePagamento, 
			                        @RequestParam("sortField") String sortFieldFormaDePagamento,
		                        	@RequestParam("sortDir") String sortDirFormaDePagamento,
		                        	Model model,
		                         	@Param("status") String status) {
		int pageSizeFormaDePagamento = 7;
		if (status == null) {
			status = "ATIVO";
		}
			Page<FormaDePagamento> pageFormaDePagamento = formaDePagamentoService.findPaginated(pageNoFormaDePagamento, pageSizeFormaDePagamento, sortFieldFormaDePagamento, sortDirFormaDePagamento, status);
			List<FormaDePagamento> listaFormaDePagamentos = pageFormaDePagamento.getContent();			
			model.addAttribute("currentPage", pageNoFormaDePagamento);
			model.addAttribute("totalPages", pageFormaDePagamento.getTotalPages());
			model.addAttribute("totalItems", pageFormaDePagamento.getTotalElements());
			model.addAttribute("sortField", sortFieldFormaDePagamento);
			model.addAttribute("sortDir", sortDirFormaDePagamento);
			model.addAttribute("reverseSortDir", sortDirFormaDePagamento.equals("asc") ? "desc" : "asc");
			model.addAttribute("listaFormaDePagamentos", listaFormaDePagamentos);		
		return "indexFormaDePagamento";
	}
}
