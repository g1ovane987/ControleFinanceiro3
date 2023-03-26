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
import com.controle.controlefinanceiro.model.ContaReceber;
import com.controle.controlefinanceiro.model.Recebimento;
import com.controle.controlefinanceiro.repository.CarteiraRepository;
import com.controle.controlefinanceiro.repository.ClienteRepository;
import com.controle.controlefinanceiro.repository.ContaReceberRepository;
import com.controle.controlefinanceiro.repository.FormaDePagamentoRepository;
import com.controle.controlefinanceiro.service.ContaReceberService;
import com.controle.controlefinanceiro.service.RecebimentoService;

@Controller
public class ContaReceberController {
	
	@Autowired
	ContaReceberService contaReceberService;
	
	@Autowired
	RecebimentoService recebimentoService;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CarteiraRepository carteiraRepository;
	
	@Autowired
	private ContaReceberRepository carRepository;
	
	@Autowired
	private FormaDePagamentoRepository formaDePagamentoRepository;
	
	@GetMapping("/indexContaReceber")
	public String viewHomePage(Model model) {
		List<Cliente> listCliente = clienteRepository.findAtivos();
		model.addAttribute("listCliente", listCliente);
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
    	
		return contasReceberPaginacao(1, "id_car", "desc", model);
	}
	
	@RequestMapping("/indexContaReceber/{pesquisa}")
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
        	model.addAttribute("listaContasReceber", contaReceberService.pesquisar(status, pesquisa, dataIniLocal, dataFimLocal));
        	List<ContaReceber> listcar = contaReceberService.pesquisar(status, pesquisa, dataIniLocal, dataFimLocal);
        	Double totalLancado = (double) 0;
        	Double totalQuitado = (double) 0;
        	Double totalCancelado = (double) 0;
        	for (ContaReceber car : listcar) {
        		if (car.getStatus().equals("LANÇADO")){
        			totalLancado = totalLancado + car.getValor();
        		}else if (car.getStatus().equals("QUITADO")) {
        			totalQuitado = totalQuitado + car.getValor();
        		}else {
        			totalCancelado = totalCancelado + car.getValor();
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
        	model.addAttribute("listaContasReceber", carRepository.pesquisarSemStatus(pesquisa, dataIniLocal, dataFimLocal));
        	List<ContaReceber> listcar = carRepository.pesquisarSemStatus(pesquisa, dataIniLocal, dataFimLocal);
        	Double totalLancado = (double) 0;
        	Double totalQuitado = (double) 0;
        	Double totalCancelado = (double) 0;
        	for (ContaReceber car : listcar) {
        		if (car.getStatus().equals("LANÇADO")){
        			totalLancado = totalLancado + car.getValor();
        		}else if (car.getStatus().equals("QUITADO")) {
        			totalQuitado = totalQuitado + car.getValor();
        		}else {
        			totalCancelado = totalCancelado + car.getValor();
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
        return "indexContaReceber";
    }
	
	@GetMapping("/novocontareceber") 
	public String novocontareceber(Model contaReceberModel) {
		ContaReceber contaReceber = new ContaReceber();
		List<Cliente> listCliente = clienteRepository.findAtivos();
		contaReceberModel.addAttribute("listCliente", listCliente);
        List<Carteira> listaCarteiras = carteiraRepository.findAtivos();
        contaReceberModel.addAttribute("listaCarteiras", listaCarteiras);
		contaReceberModel.addAttribute("contareceberView", contaReceber);
		return "salvarContaReceber";
	}

	@PostMapping("/salvarContaReceber")
	public String salvarContaReceber(@ModelAttribute("contaReceber") ContaReceber contaReceber) {
		if ((contaReceber.getValor() <= 0) || (contaReceber.getId_cli() == null) || (contaReceber.getDt_vencim().isBefore(LocalDate.now()))) {
			return "contaReceberErro";
		}else {
		contaReceber.setStatus("LANÇADO");
		contaReceberService.salvarContaRebecer(contaReceber);
		return "redirect:/indexContaReceber";
		}
	}
	
	@GetMapping("/gerarRecebimento/{id}")
	public String gerarRecebimento(@PathVariable (value = "id") Long id) {
		try {
		LocalDate dataContaReceber;
		ContaReceber contaReceber = contaReceberService.getContaReceberById(id);
		if (contaReceber.getId_carteira() > 0) {
			contaReceber.setStatus("QUITADO");
			contaReceberService.salvarContaRebecer(contaReceber);	
			dataContaReceber = LocalDate.now();
			Recebimento recebimento = new Recebimento();
			recebimento.setDt_pag(dataContaReceber);
			recebimento.setId_cli(contaReceber.getId_cli());
			recebimento.setId_car(contaReceber.getId_car());
			recebimento.setObs("GERADO ATRAVÉS DA CONTA A RECEBER");
			recebimento.setStatus("LANÇADO");
			recebimento.setId_carteira(contaReceber.getId_carteira());
			recebimento.setVlr_pago(contaReceber.getValor());
			recebimentoService.salvarRecebimento(recebimento);
		}
		return "redirect:/indexContaReceber";
		}catch (Exception $e)  {			
			return "redirect:/mensagemContaReceber";	
		}
	}	
	
	@GetMapping("/deletarContaReceber/{id}")
	public String deletarContaReceber(@PathVariable (value = "id") Long id) {
		try {
			ContaReceber contaReceber = contaReceberService.getContaReceberById(id);
			if (contaReceber.getStatus() != "QUITADO") {
				contaReceber.setStatus("CANCELADO");
				contaReceberService.salvarContaRebecer(contaReceber);
				
			}
			return "redirect:/indexContaReceber";
		}catch (Exception $e)  {			
			return "redirect:/mensagemContaReceber";	
		}
	}
//	@RequestMapping("/indexContaReceber/{pesquisa}")
//    public String pesquisar(Model model, @Param("status") String status, @Param("pesquisa") String pesquisa) {
//        List<ContaReceber> listaContasReceber = contaReceberService.pesquisar(status, pesquisa, null, null);
//        model.addAttribute("listaContasReceber", listaContasReceber);
//        return "indexContaReceber";
//    }
	@GetMapping("/pageContaReceber/{pageNo}")
	public String contasReceberPaginacao(@PathVariable (value = "pageNo") int pageNoContaReceber, 
			                        @RequestParam("sortField") String sortFieldContaReceber,
		                        	@RequestParam("sortDir") String sortDirContaReceber,
		                        	Model model) {
		int pageSizeContaReceber = 7;
			Page<ContaReceber> pageContaReceber = contaReceberService.findPaginated(pageNoContaReceber, pageSizeContaReceber, sortFieldContaReceber, sortDirContaReceber);
			List<ContaReceber> listaContasReceber = pageContaReceber.getContent();			
			model.addAttribute("currentPage", pageNoContaReceber);
			model.addAttribute("totalPages", pageContaReceber.getTotalPages());
			model.addAttribute("totalItems", pageContaReceber.getTotalElements());
			model.addAttribute("sortField", sortFieldContaReceber);
			model.addAttribute("sortDir", sortDirContaReceber);
			model.addAttribute("reverseSortDir", sortDirContaReceber.equals("asc") ? "desc" : "asc");
			model.addAttribute("listaContasReceber", listaContasReceber);		
		return "indexContaReceber";
	}
}
