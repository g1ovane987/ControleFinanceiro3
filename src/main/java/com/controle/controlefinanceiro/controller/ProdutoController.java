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

import com.controle.controlefinanceiro.model.Produto;
import com.controle.controlefinanceiro.service.ProdutoService;

@Controller
public class ProdutoController {
	@Autowired
	ProdutoService produtoService;
	
	@GetMapping("/indexProduto")
	public String viewHomePage(Model model) {
		String status = "ATIVO";
		return produtosPaginacao(1, "descricao", "asc", model, status);
	}
	
	@PostMapping("/salvarProduto")
	public String salvarProduto(@ModelAttribute("produto") Produto produto) {
		produto.setStatus("ATIVO");
		produtoService.salvarProduto(produto);
		return "redirect:/indexProduto";
	}
	
	@GetMapping("/novoproduto") 
	public String novoProduto(Model produtoModel) {
		Produto produto = new Produto();
		produtoModel.addAttribute("produtoView", produto);
		return "salvarProduto";
	}
	
	@GetMapping("/atualizarProduto/{id}")
	public String atualizarProduto(@PathVariable ( value = "id") Long id, Model model) {
		Produto produto = produtoService.getProdutoById(id);
		model.addAttribute("produtoView", produto);	    
		return "salvarProduto";
	}
	
	@GetMapping("/deletarProduto/{id}")
	public String deletarProduto(@PathVariable (value = "id") Long id) {
		try {
			Produto produto = produtoService.getProdutoById(id);
			produto.setStatus("INATIVO");
			produtoService.salvarProduto(produto);
			return "redirect:/indexProduto";
		}catch (Exception $e)  {			
			return "redirect:/mensagemProduto";	
		}
	}
	
	@GetMapping("/alterarStatusProduto/{id}")
	public String alterarStatusProduto(@PathVariable (value = "id") Long id) {
			Produto produto = produtoService.getProdutoById(id);
			String status = produto.getStatus();
			if (status.equals("ATIVO")) {
				produto.setStatus("INATIVO");
			}else {
				produto.setStatus("ATIVO");
			}
			produtoService.salvarProduto(produto);
			return "redirect:/indexProduto";
	}
	
	@RequestMapping("/indexProduto/{pesquisa}")
    public String pesquisar(Model model, @Param("status") String status, @Param("pesquisa") String pesquisa) {
        List<Produto> listaProdutos = produtoService.pesquisar(status, pesquisa);
        model.addAttribute("listaProdutos", listaProdutos);
        return "indexProduto";
    }
	
	@GetMapping("/pageProduto/{pageNo}")
	public String produtosPaginacao(@PathVariable (value = "pageNo") int pageNoProduto, 
			                        @RequestParam("sortField") String sortFieldProduto,
		                        	@RequestParam("sortDir") String sortDirProduto,
		                        	Model model,
		                         	@Param("status") String status) {
		int pageSizeProduto = 7;
		if (status == null) {
			status = "ATIVO";
		}
			Page<Produto> pageProduto = produtoService.findPaginated(pageNoProduto, pageSizeProduto, sortFieldProduto, sortDirProduto, status);
			List<Produto> listaProdutos = pageProduto.getContent();			
			model.addAttribute("currentPage", pageNoProduto);
			model.addAttribute("totalPages", pageProduto.getTotalPages());
			model.addAttribute("totalItems", pageProduto.getTotalElements());
			model.addAttribute("sortField", sortFieldProduto);
			model.addAttribute("sortDir", sortDirProduto);
			model.addAttribute("reverseSortDir", sortDirProduto.equals("asc") ? "desc" : "asc");
			model.addAttribute("listaProdutos", listaProdutos);		
		return "indexProduto";
	}
}
