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

import com.controle.controlefinanceiro.model.Fornecedor;
import com.controle.controlefinanceiro.service.FornecedorService;

@Controller
public class FornecedorController {

	@Autowired
	FornecedorService fornecedorService;
	
	@GetMapping("/indexFornecedor")
	public String viewHomePage(Model model) {
		String status = "ATIVO";
		return fornecedoresPaginacao(1, "nome", "asc", model, status);
	}
	
	@RequestMapping("/indexFornecedor/{pesquisa}")
    public String pesquisar(Model model, @Param("status") String status, @Param("pesquisa") String pesquisa) {
        List<Fornecedor> listaFornecedores = fornecedorService.pesquisar(status, pesquisa);
        model.addAttribute("listaFornecedores", listaFornecedores);
        return "indexFornecedor";
    }
	
	@GetMapping("/alterarStatusFornecedor/{id}")
	public String alterarStatusFornecedor(@PathVariable (value = "id") Long id) {
			Fornecedor fornecedor = fornecedorService.getFornecedorById(id);
			String status = fornecedor.getStatus();
			if (status.equals("ATIVO")) {
				fornecedor.setStatus("INATIVO");
			}else {
				fornecedor.setStatus("ATIVO");
			}
			fornecedorService.salvarFornecedor(fornecedor);
			return "redirect:/indexFornecedor";
	}
	
	@GetMapping("/novofornecedor") 
	public String novoFornecedor(Model fornecedorModel) {
		Fornecedor fornecedor = new Fornecedor();
		fornecedorModel.addAttribute("fornecedorView", fornecedor);
		return "salvarFornecedor";
	}
	
	@PostMapping("/salvarFornecedor")
	public String salvarFornecedor(@ModelAttribute("fornecedor") Fornecedor fornecedor) {
		fornecedor.setStatus("ATIVO");
		fornecedorService.salvarFornecedor(fornecedor);
		return "redirect:/indexFornecedor";
	}
	
	@GetMapping("/atualizarfornecedor/{id}")
	public String atualizarCliente(@PathVariable ( value = "id") Long id, Model model) {
		Fornecedor fornecedor = fornecedorService.getFornecedorById(id);
		model.addAttribute("fornecedorView", fornecedor);	    
		return "salvarFornecedor";
	}
	
	@GetMapping("/deletarFornecedor/{id}")
	public String deletarFornecedor(@PathVariable (value = "id") Long id) {
		try {
			Fornecedor fornecedor = fornecedorService.getFornecedorById(id);
			fornecedor.setStatus("INATIVO");
			fornecedorService.salvarFornecedor(fornecedor);
			return "redirect:/indexFornecedor";
		}catch (Exception $e)  {			
			return "redirect:/mensagemFornecedor";	
		}
	}
	
	@GetMapping("/pageFornecedor/{pageNo}")
	public String fornecedoresPaginacao(@PathVariable (value = "pageNo") int pageNoFornecedor, 
			                        @RequestParam("sortField") String sortFieldFornecedor,
		                        	@RequestParam("sortDir") String sortDirFornecedor,
		                        	Model model,
		                         	@Param("status") String status) {
		int pageSizeFornecedor = 7;
		if (status == null) {
			status = "ATIVO";
		}
			Page<Fornecedor> pageFornecedor = fornecedorService.findPaginated(pageNoFornecedor, pageSizeFornecedor, sortFieldFornecedor, sortDirFornecedor, status);
			List<Fornecedor> listaFornecedores = pageFornecedor.getContent();			
			model.addAttribute("currentPage", pageNoFornecedor);
			model.addAttribute("totalPages", pageFornecedor.getTotalPages());
			model.addAttribute("totalItems", pageFornecedor.getTotalElements());
			model.addAttribute("sortField", sortFieldFornecedor);
			model.addAttribute("sortDir", sortDirFornecedor);
			model.addAttribute("reverseSortDir", sortDirFornecedor.equals("asc") ? "desc" : "asc");
			model.addAttribute("listaFornecedores", listaFornecedores);		
		return "indexFornecedor";
	}
	
}
