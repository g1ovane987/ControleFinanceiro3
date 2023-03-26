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
import com.controle.controlefinanceiro.model.Cliente;
import com.controle.controlefinanceiro.service.ClienteService;

@Controller
public class ClienteController {

	@Autowired
	ClienteService clienteService;
	
	@GetMapping("/indexCliente")
	public String viewHomePage(Model model) {
		String status = "ATIVO";
		return clientesPaginacao(1, "nome", "asc", model, status);
	}
	
	@RequestMapping("/indexCliente/{pesquisa}")
    public String pesquisar(Model model, @Param("status") String status, @Param("pesquisa") String pesquisa) {
        List<Cliente> listaClientes = clienteService.pesquisar(status, pesquisa);
        model.addAttribute("listaClientes", listaClientes);
        return "indexCliente";
    }
	
	@GetMapping("/novocliente") 
	public String novoCliente(Model clienteModel) {
		Cliente cliente = new Cliente();
		clienteModel.addAttribute("clienteView", cliente);
		return "salvarCliente";
	}
	
	@GetMapping("/atualizarCliente/{id}")
	public String atualizarCliente(@PathVariable ( value = "id") Long id, Model model) {
		Cliente cliente = clienteService.getClienteById(id);
		model.addAttribute("clienteView", cliente);	    
		return "salvarCliente";
	}
	
	@PostMapping("/salvarCliente")
	public String salvarCliente(@ModelAttribute("cliente") Cliente cliente) {
		cliente.setStatus("ATIVO");
		clienteService.salvarCliente(cliente);
		return "redirect:/indexCliente";
	}
	
	@GetMapping("/pesquisarcliente/{nome}")
	public String pesquisarCliente(@Param("nome") String nome, Model model) {
		List<Cliente> cliente  = clienteService.findByNomeContainingIgnoreCase(nome);
		model.addAttribute("LstClientes", cliente);
		model.addAttribute("nome", nome);
		return "indexcliente";
	}
	
	@GetMapping("/alterarStatusCliente/{id}")
	public String alterarStatusCliente(@PathVariable (value = "id") Long id) {
		Cliente cliente = clienteService.getClienteById(id);
			String status = cliente.getStatus();
			if (status.equals("ATIVO")) {
				cliente.setStatus("INATIVO");
			}else {
				cliente.setStatus("ATIVO");
			}
			clienteService.salvarCliente(cliente);
			return "indexcliente";
	}
	
	@GetMapping("/deletarCliente/{id}")
	public String deletarCliente(@PathVariable (value = "id") Long id) {
		try {
			Cliente cliente = clienteService.getClienteById(id);
			cliente.setStatus("INATIVO");
			clienteService.salvarCliente(cliente);
			return "redirect:/indexCliente";
		}catch (Exception $e)  {			
			return "redirect:/mensagemCliente";	
		}
	}
	
	@GetMapping("/pageCliente/{pageNo}")
	public String clientesPaginacao(@PathVariable (value = "pageNo") int pageNoCliente, 
			                        @RequestParam("sortField") String sortFieldCliente,
		                        	@RequestParam("sortDir") String sortDirCliente,
		                        	Model model,
		                         	@Param("status") String status) {
		int pageSizeCliente = 7;
		if (status == null) {
			status = "ATIVO";
		}
			Page<Cliente> pageCliente = clienteService.findPaginated(pageNoCliente, pageSizeCliente, sortFieldCliente, sortDirCliente, status);
			List<Cliente> listaClientes = pageCliente.getContent();			
			model.addAttribute("currentPage", pageNoCliente);
			model.addAttribute("totalPages", pageCliente.getTotalPages());
			model.addAttribute("totalItems", pageCliente.getTotalElements());
			model.addAttribute("sortField", sortFieldCliente);
			model.addAttribute("sortDir", sortDirCliente);
			model.addAttribute("reverseSortDir", sortDirCliente.equals("asc") ? "desc" : "asc");
			model.addAttribute("listaClientes", listaClientes);		
		return "indexCliente";
	}
}
