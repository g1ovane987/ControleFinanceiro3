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

import com.controle.controlefinanceiro.model.DetalheCompra;
import com.controle.controlefinanceiro.service.DetalheCompraService;

@Controller
public class DetalheCompraController {
	
	@Autowired
	DetalheCompraService detalheCompraService;
	
	@GetMapping("/indexDetalheCompra/{id}")
	public String viewHomePage(Model model,
			@PathVariable (value = "id") Integer id) {
		return detalheComprasPaginacao(1, "id_detalhe_compra", "desc", model, id);
	}
	
	@PostMapping("/salvarDetalheCompra")
	public String salvarDetalheCompra(@ModelAttribute("detalheCompra") DetalheCompra detalheCompra) {
		detalheCompraService.salvarDetalheCompra(detalheCompra);
		return "redirect:/indexDetalheCompra/"+detalheCompra.getId_compra();
	}
	
	@GetMapping("/deletarDetalheCompra/{id_detalhe}")
	public String deletarCompra(@PathVariable (value = "id_detalhe") Long id_detalhe) {
		try {
			detalheCompraService.deletarDetalheCompraById(id_detalhe);
			return "redirect:/indexCompra";
		}catch (Exception $e)  {			
			return "redirect:/mensagemDetalheCompra";	
		}
	}
	
	@GetMapping("/pageDetalheCompra/{pageNo}")
	public String detalheComprasPaginacao(@PathVariable (value = "pageNo") int pageNoDetalheCompra, 
			                        @RequestParam("sortField") String sortFieldDetalheCompra,
		                        	@RequestParam("sortDir") String sortDirDetalheCompra,
		                        	Model model,
		                        	@Param("id") Integer id) {
		int pageSizeDetalheCompra = 7;
			Page<DetalheCompra> pageDetalheCompra = detalheCompraService.findPaginated(pageNoDetalheCompra, 
					pageSizeDetalheCompra, sortFieldDetalheCompra, sortDirDetalheCompra, id);
			List<DetalheCompra> listaDetalheCompras = pageDetalheCompra.getContent();			
			model.addAttribute("currentPage", pageNoDetalheCompra);
			model.addAttribute("totalPages", pageDetalheCompra.getTotalPages());
			model.addAttribute("totalItems", pageDetalheCompra.getTotalElements());
			model.addAttribute("sortField", sortFieldDetalheCompra);
			model.addAttribute("sortDir", sortDirDetalheCompra);
			model.addAttribute("reverseSortDir", sortDirDetalheCompra.equals("asc") ? "desc" : "asc");
			model.addAttribute("listaDetalheCompras", listaDetalheCompras);		
		return "indexDetalheCompra";
	}

}
