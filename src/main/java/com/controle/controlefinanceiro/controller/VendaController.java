package com.controle.controlefinanceiro.controller;

import java.time.LocalDate;
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
import com.controle.controlefinanceiro.model.FormaDePagamento;
import com.controle.controlefinanceiro.model.Produto;
import com.controle.controlefinanceiro.model.Recebimento;
import com.controle.controlefinanceiro.model.Venda;
import com.controle.controlefinanceiro.repository.CarteiraRepository;
import com.controle.controlefinanceiro.repository.ClienteRepository;
import com.controle.controlefinanceiro.repository.FormaDePagamentoRepository;
import com.controle.controlefinanceiro.repository.ProdutoRepository;
import com.controle.controlefinanceiro.service.ContaReceberService;
import com.controle.controlefinanceiro.service.RecebimentoService;
import com.controle.controlefinanceiro.service.VendaService;

@Controller
public class VendaController {
	@Autowired
	VendaService vendaService;
	
	@Autowired
	ContaReceberService contaReceberService;
	
	@Autowired
	RecebimentoService recebimentoService;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CarteiraRepository carteiraRepository;
	
	@Autowired
	private FormaDePagamentoRepository formaDePagamentoRepository;
	
	@GetMapping("/indexVenda")
	public String viewHomePage(Model model) {
		List<Cliente> listCliente = clienteRepository.findAtivos();
		model.addAttribute("listCliente", listCliente);
		List<Produto> listProd = produtoRepository.findAll();
		model.addAttribute("listProd", listProd);
		List<FormaDePagamento> listFPag = formaDePagamentoRepository.findAtivos();
		model.addAttribute("listFPag", listFPag);
		return vendasPaginacao(1, "id_venda", "desc", model);
	}
	
	@RequestMapping("/indexVenda/{pesquisa}")
    public String pesquisar(Model model, @Param("status") String status, @Param("pesquisa") String pesquisa) {
		List<Cliente> listCliente = clienteRepository.findAtivos();
		model.addAttribute("listCliente", listCliente);
		List<Produto> listProd = produtoRepository.findAll();
		model.addAttribute("listProd", listProd);		
		List<FormaDePagamento> listFPag = formaDePagamentoRepository.findAtivos();
		model.addAttribute("listFPag", listFPag);
		List<Venda> listaVendas = vendaService.pesquisar(status, pesquisa);
        model.addAttribute("listaVendas", listaVendas);
        return "indexVenda";
    }
	
	@PostMapping("/salvarVenda")
	public String salvarVenda(@ModelAttribute("venda") Venda venda) {
		venda.setStatus("LANÇADO");
		vendaService.salvarVenda(venda);
		return "redirect:/indexVenda";
	}
	
	@GetMapping("/novovenda") 
	public String novovenda(Model vendaModel) {
		Venda venda = new Venda();
		vendaModel.addAttribute("vendaView", venda);
		List<Produto> listProduto = produtoRepository.findAll();
		vendaModel.addAttribute("listProduto", listProduto);
		List<Cliente> listCliente = clienteRepository.findAtivos();
		vendaModel.addAttribute("listCliente", listCliente);
		List<FormaDePagamento> listFPag = formaDePagamentoRepository.findAtivos();
		vendaModel.addAttribute("listFPag", listFPag);
        List<Carteira> listaCarteiras = carteiraRepository.findAtivos();
        vendaModel.addAttribute("listaCarteiras", listaCarteiras);
		return "salvarVenda";
	}
	
	@GetMapping("/gerarRecebimentoVenda/{id}")
	public String gerarRecebimentoVenda(@PathVariable (value = "id") Long id) {
		try {
		LocalDate dataVenda;
		Venda venda = vendaService.getVendaById(id);
		if ((venda.getQtdeParcelas() == 0) && (venda.getId_carteira() > 0)) {
			venda.setStatus("FATURADO");
			vendaService.salvarVenda(venda);	
			dataVenda = LocalDate.now();			
			Recebimento recebimento = new Recebimento();
			recebimento.setDt_pag(dataVenda);
			recebimento.setId_cli(venda.getId_cliente());
			recebimento.setId_venda(venda.getId_venda());
			recebimento.setObs("GERADO ATRAVÉS DA VENDA");
			recebimento.setStatus("LANÇADO");
			recebimento.setId_carteira(venda.getId_carteira());
			recebimento.setVlr_pago(venda.getValor());
			recebimentoService.salvarRecebimento(recebimento);
		}
		return "redirect:/indexVenda";
		}catch (Exception $e)  {			
			return "redirect:/mensagemVenda";	
		}
	}	
	
	@GetMapping("/gerarContaReceber/{id}")
	public String gerarContaReceber(@PathVariable (value = "id") Long id) {
		try {
		Double valorParcela;
		LocalDate dataVenda;
		Venda venda = vendaService.getVendaById(id);
		if (venda.getQtdeParcelas() > 0) {
			venda.setStatus("FATURADO");
			vendaService.salvarVenda(venda);	
			valorParcela = venda.getValor()/venda.getQtdeParcelas();
			for(int i = 1;i<=venda.getQtdeParcelas();++i) {
				dataVenda = LocalDate.now();
				ContaReceber conta = new ContaReceber();
				conta.setId_cli(venda.getId_cliente());
				conta.setId_carteira(venda.getId_carteira());
				conta.setObs("GERADO A PARTIR DE VENDA");
				conta.setStatus("LANÇADO");
				conta.setValor(valorParcela);
				conta.setDt_vencim(dataVenda.plusMonths(i));
				conta.setId_venda(venda.getId_venda());
				contaReceberService.salvarContaRebecer(conta);
			}
		}
		return "redirect:/indexVenda";
		}catch (Exception $e)  {			
			return "redirect:/mensagemVenda";	
		}
	}
	
	@GetMapping("/deletarVenda/{id}")
	public String deletarVenda(@PathVariable (value = "id") Long id) {
		try {
			Venda venda = vendaService.getVendaById(id);
			if (venda.getStatus() != "FATURADO")
				venda.setStatus("CANCELADO");
				vendaService.salvarVenda(venda);
			return "redirect:/indexVenda";
		}catch (Exception $e)  {			
			return "redirect:/mensagemVenda";	
		}
	}
	
	
	@GetMapping("/pageVenda/{pageNo}")
	public String vendasPaginacao(@PathVariable (value = "pageNo") int pageNoVenda, 
			                        @RequestParam("sortField") String sortFieldVenda,
		                        	@RequestParam("sortDir") String sortDirVenda,
		                        	Model model) {
		int pageSizeVenda = 7;
			Page<Venda> pageVenda = vendaService.findPaginated(pageNoVenda, pageSizeVenda, sortFieldVenda, sortDirVenda);
			List<Venda> listaVendas = pageVenda.getContent();			
			model.addAttribute("currentPage", pageNoVenda);
			model.addAttribute("totalPages", pageVenda.getTotalPages());
			model.addAttribute("totalItems", pageVenda.getTotalElements());
			model.addAttribute("sortField", sortFieldVenda);
			model.addAttribute("sortDir", sortDirVenda);
			model.addAttribute("reverseSortDir", sortDirVenda.equals("asc") ? "desc" : "asc");
			model.addAttribute("listaVendas", listaVendas);		
		return "indexVenda";
	}
}
