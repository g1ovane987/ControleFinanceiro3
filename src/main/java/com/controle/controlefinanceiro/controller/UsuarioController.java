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

import com.controle.controlefinanceiro.model.Carteira;
import com.controle.controlefinanceiro.model.Usuario;
import com.controle.controlefinanceiro.service.UsuarioService;

@Controller
public class UsuarioController {
	@Autowired
	UsuarioService usuarioservice;
	
	@GetMapping("/indexUsuario")
	public String viewHomePage(Model model) {
		String status = "ATIVO";
		return usuariosPaginacao(1, "login", "asc", model, status);
	}
	
	@GetMapping("/novousuario") 
	public String novousuario(Model usuarioModel) {
		Usuario usuario = new Usuario();
		usuarioModel.addAttribute("usuarioView", usuario);
		return "salvarUsuario";
	}
	
	
	@PostMapping("/salvarUsuario")
	public String salvarUsuario(@ModelAttribute("usuario") Usuario usuario) {
		usuario.setStatus("ATIVO");
		usuarioservice.salvarUsuario(usuario);
		return "redirect:/indexUsuario";
	}
	
	
	@GetMapping("/deletarUsuario/{id}")
	public String deletarUsuario(@PathVariable (value = "id") Long id) {
		try {
			Usuario usuario = usuarioservice.getUsuarioById(id);
			usuario.setStatus("INATIVO");
			usuarioservice.salvarUsuario(usuario);
			return "redirect:/indexUsuario";
		}catch (Exception $e)  {			
			return "redirect:/mensagemUsuario";	
		}
	}
	
	@GetMapping("/login/{login}/{senha}")
	public String login(@PathVariable (value = "login") String login, @PathVariable (value = "senha") String senha) {
		try {
			if (usuarioservice.LoginUsuario(login, senha) == true) {
				return "redirect:/index";
			}else {
				return "redirect:/login";
			}
		}catch (Exception $e)  {			
			return "redirect:/login";	
		}
	}
	
	@RequestMapping("/indexUsuario/{pesquisa}")
    public String pesquisar(Model model, @Param("status") String status, @Param("pesquisa") String pesquisa) {
        List<Usuario> listaUsuarios = usuarioservice.pesquisar(status, pesquisa);
        model.addAttribute("listaUsuarios", listaUsuarios);
        return "indexUsuario";
    }
	
	@GetMapping("/pageUsuario/{pageNo}")
	public String usuariosPaginacao(@PathVariable (value = "pageNo") int pageNoUsuario, 
			                        @RequestParam("sortField") String sortFieldUsuario,
		                        	@RequestParam("sortDir") String sortDirUsuario,
		                        	Model model,
		                         	@Param("status") String status) {
		int pageSizeUsuario = 7;
		if (status == null) {
			status = "ATIVO";
		}
			Page<Usuario> pageUsuario = usuarioservice.findPaginated(pageNoUsuario, pageSizeUsuario, sortFieldUsuario, sortDirUsuario, status);
			List<Usuario> listaUsuarios = pageUsuario.getContent();			
			model.addAttribute("currentPage", pageNoUsuario);
			model.addAttribute("totalPages", pageUsuario.getTotalPages());
			model.addAttribute("totalItems", pageUsuario.getTotalElements());
			model.addAttribute("sortField", sortFieldUsuario);
			model.addAttribute("sortDir", sortDirUsuario);
			model.addAttribute("reverseSortDir", sortDirUsuario.equals("asc") ? "desc" : "asc");
			model.addAttribute("listaUsuarios", listaUsuarios);		
		return "indexUsuario";
	}
}
