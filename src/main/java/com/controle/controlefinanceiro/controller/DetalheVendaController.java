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
import org.springframework.web.bind.annotation.RequestParam;

import com.controle.controlefinanceiro.model.DetalheVenda;
import com.controle.controlefinanceiro.service.DetalheVendaService;

@Controller
public class DetalheVendaController {

	@Autowired
	DetalheVendaService detalheVendaService;
	
	@GetMapping("/indexDetalheVenda/{id}")
	public String viewHomePage(Model model,
			@PathVariable (value = "id") Integer id) {
		return detalheVendasPaginacao(1, "id_detalhe_venda", "desc", model, id);
	}
	
	@PostMapping("/salvarDetalheVenda")
	public String salvarDetalheVenda(@ModelAttribute("detalheVenda") DetalheVenda detalheVenda) {
		detalheVendaService.salvarDetalheVenda(detalheVenda);
		return "redirect:/indexDetalheVenda/"+detalheVenda.getId_venda();
	}
	
	@GetMapping("/deletarDetalheVenda/{id_detalhe}")
	public String deletarVenda(@PathVariable (value = "id_detalhe") Long id_detalhe) {
		try {
			detalheVendaService.deletarDetalheVendaById(id_detalhe);
			return "redirect:/indexVenda";
		}catch (Exception $e)  {			
			return "redirect:/mensagemDetalheVenda";	
		}
	}
	
	@GetMapping("/pageDetalheVenda/{pageNo}")
	public String detalheVendasPaginacao(@PathVariable (value = "pageNo") int pageNoDetalheVenda, 
			                        @RequestParam("sortField") String sortFieldDetalheVenda,
		                        	@RequestParam("sortDir") String sortDirDetalheVenda,
		                        	Model model,
		                        	@Param("id") Integer id) {
		int pageSizeDetalheVenda = 7;
			Page<DetalheVenda> pageDetalheVenda = detalheVendaService.findPaginated(pageNoDetalheVenda, 
					pageSizeDetalheVenda, sortFieldDetalheVenda, sortDirDetalheVenda, id);
			List<DetalheVenda> listaDetalheVendas = pageDetalheVenda.getContent();			
			model.addAttribute("currentPage", pageNoDetalheVenda);
			model.addAttribute("totalPages", pageDetalheVenda.getTotalPages());
			model.addAttribute("totalItems", pageDetalheVenda.getTotalElements());
			model.addAttribute("sortField", sortFieldDetalheVenda);
			model.addAttribute("sortDir", sortDirDetalheVenda);
			model.addAttribute("reverseSortDir", sortDirDetalheVenda.equals("asc") ? "desc" : "asc");
			model.addAttribute("listaDetalheVendas", listaDetalheVendas);		
		return "indexDetalheVenda";
	}
}
