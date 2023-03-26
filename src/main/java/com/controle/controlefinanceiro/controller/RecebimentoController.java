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
import com.controle.controlefinanceiro.model.Cliente;
import com.controle.controlefinanceiro.model.ContaPagar;
import com.controle.controlefinanceiro.model.ContaReceber;
import com.controle.controlefinanceiro.model.Recebimento;
import com.controle.controlefinanceiro.repository.CarteiraRepository;
import com.controle.controlefinanceiro.repository.ClienteRepository;
import com.controle.controlefinanceiro.repository.RecebimentoRepository;
import com.controle.controlefinanceiro.service.RecebimentoService;

@Controller
public class RecebimentoController {
	@Autowired
	RecebimentoService recebimentoService;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private RecebimentoRepository recebimentoRepository;
	
	@Autowired
	private CarteiraRepository carteiraRepository;
	
	@GetMapping("/indexRecebimento")
	public String viewHomePage(Model model) {
		List<Cliente> listCliente = clienteRepository.findAtivos();
		model.addAttribute("listCliente", listCliente);
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
        
		return recebimentosPaginacao(1, "id_receb", "desc", model);
	}
	
	@RequestMapping("/indexRecebimento/{pesquisa}")
    public String pesquisar(Model model, @Param("status") String status, @Param("pesquisa") String pesquisa, @Param("dataIni") String dataIni, @Param("dataFim") String dataFim) {
		List<Cliente> listCliente = clienteRepository.findAtivos();
		model.addAttribute("listCliente", listCliente);
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
        	model.addAttribute("listaRecebimentos", recebimentoService.pesquisar(status, pesquisa, dataIniLocal, dataFimLocal));
        	List<Recebimento> list = recebimentoService.pesquisar(status, pesquisa, dataIniLocal, dataFimLocal);
        	Double totalLancado = (double) 0;
        	Double totalCancelado = (double) 0;
        	for (Recebimento obj : list) {
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
        	model.addAttribute("listaRecebimentos", recebimentoRepository.pesquisarSemStatus(pesquisa, dataIniLocal, dataFimLocal));
        	List<Recebimento> list = recebimentoRepository.pesquisarSemStatus(pesquisa, dataIniLocal, dataFimLocal);
        	Double totalLancado = (double) 0;
        	Double totalCancelado = (double) 0;
        	for (Recebimento obj : list) {
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
        return "indexRecebimento";
    }
	
	@GetMapping("/novorecebimento") 
	public String novorecebimento(Model recebModel) {
		Recebimento recebimento = new Recebimento();
		List<Cliente> listCliente = clienteRepository.findAtivos();
		recebModel.addAttribute("listCliente", listCliente);
        List<Carteira> listaCarteiras = carteiraRepository.findAtivos();
        recebModel.addAttribute("listaCarteiras", listaCarteiras);
        recebModel.addAttribute("recebimentoView", recebimento);
		return "salvarRecebimento";
	}	
	
	@PostMapping("/salvarRecebimento")
	public String salvarRecebimento(@ModelAttribute("recebimento") Recebimento recebimento) {
		if ((recebimento.getVlr_pago() <= 0) || (recebimento.getId_cli() == null) || (recebimento.getDt_pag().isBefore(LocalDate.now()))) {
			return "recebimentoErro";
		}else {
			recebimento.setStatus("LANÇADO");
			recebimentoService.salvarRecebimento(recebimento);
			return "redirect:/indexRecebimento";
		}
	}
	
	@GetMapping("/deletarRecebimento/{id}")
	public String deletarRecebimento(@PathVariable (value = "id") Long id) {
		try {
			Recebimento recebimento = recebimentoService.getRecebimentoById(id);
			if (recebimento.getStatus() != "CANCELADO") {
				recebimento.setStatus("CANCELADO");
				recebimentoService.salvarRecebimento(recebimento);
			}
			return "redirect:/indexRecebimento";
		}catch (Exception $e)  {			
			return "redirect:/mensagemRecebimento";	
		}
	}
	
	@GetMapping("/pageRecebimento/{pageNo}")
	public String recebimentosPaginacao(@PathVariable (value = "pageNo") int pageNoRecebimento, 
			                        @RequestParam("sortField") String sortFieldRecebimento,
		                        	@RequestParam("sortDir") String sortDirRecebimento,
		                        	Model model) {
		int pageSizeRecebimento = 7;
			Page<Recebimento> pageRecebimento = recebimentoService.findPaginated(pageNoRecebimento, pageSizeRecebimento, sortFieldRecebimento, sortDirRecebimento);
			List<Recebimento> listaRecebimentos = pageRecebimento.getContent();			
			model.addAttribute("currentPage", pageNoRecebimento);
			model.addAttribute("totalPages", pageRecebimento.getTotalPages());
			model.addAttribute("totalItems", pageRecebimento.getTotalElements());
			model.addAttribute("sortField", sortFieldRecebimento);
			model.addAttribute("sortDir", sortDirRecebimento);
			model.addAttribute("reverseSortDir", sortDirRecebimento.equals("asc") ? "desc" : "asc");
			model.addAttribute("listaRecebimentos", listaRecebimentos);		
		return "indexRecebimento";
	}
}
