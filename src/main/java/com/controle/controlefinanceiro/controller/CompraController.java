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
import com.controle.controlefinanceiro.model.Compra;
import com.controle.controlefinanceiro.model.ContaPagar;
import com.controle.controlefinanceiro.model.FormaDePagamento;
import com.controle.controlefinanceiro.model.Fornecedor;
import com.controle.controlefinanceiro.model.Pagamento;
import com.controle.controlefinanceiro.model.Produto;
import com.controle.controlefinanceiro.repository.CarteiraRepository;
import com.controle.controlefinanceiro.repository.FormaDePagamentoRepository;
import com.controle.controlefinanceiro.repository.FornecedorRepository;
import com.controle.controlefinanceiro.repository.ProdutoRepository;
import com.controle.controlefinanceiro.service.CompraService;
import com.controle.controlefinanceiro.service.ContaPagarService;
import com.controle.controlefinanceiro.service.PagamentoService;

@Controller
public class CompraController {
	
	@Autowired
	CompraService compraService;
	
	@Autowired
	ContaPagarService contaPagarService;
	
	@Autowired
	PagamentoService pagamentoService;
	
	@Autowired
	FornecedorRepository fornecedorRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CarteiraRepository carteiraRepository;
	
	@Autowired
	private FormaDePagamentoRepository formaDePagamentoRepository;
	
	@GetMapping("/indexCompra")
	public String viewHomePage(Model model) {
		List<Fornecedor> listFornecedor = fornecedorRepository.findAtivos();
		model.addAttribute("listFornecedor", listFornecedor);
		List<Produto> listProd = produtoRepository.findAll();
		model.addAttribute("listProd", listProd);		
		List<FormaDePagamento> listFPag = formaDePagamentoRepository.findAtivos();
		model.addAttribute("listFPag", listFPag);
		return comprasPaginacao(1, "id_compra", "desc", model);
	}
	
	@GetMapping("/novocompra") 
	public String novocompra(Model compraModel) {
		Compra compra = new Compra();
		compraModel.addAttribute("compraView", compra);
		List<Produto> listProduto = produtoRepository.findAll();
		compraModel.addAttribute("listProduto", listProduto);
		List<Fornecedor> listFornecedor = fornecedorRepository.findAtivos();
		compraModel.addAttribute("listFornecedor", listFornecedor);
		List<FormaDePagamento> listFPag = formaDePagamentoRepository.findAtivos();
		compraModel.addAttribute("listFPag", listFPag);
        List<Carteira> listaCarteiras = carteiraRepository.findAtivos();
        compraModel.addAttribute("listaCarteiras", listaCarteiras);
		return "salvarCompra";
	}
	
	@RequestMapping("/indexCompra/{pesquisa}")
    public String pesquisar(Model model, @Param("status") String status, @Param("pesquisa") String pesquisa) {
		List<Fornecedor> listFornecedor = fornecedorRepository.findAtivos();
		model.addAttribute("listFornecedor", listFornecedor);
		List<Produto> listProd = produtoRepository.findAll();
		model.addAttribute("listProd", listProd);		
		List<FormaDePagamento> listFPag = formaDePagamentoRepository.findAtivos();
		model.addAttribute("listFPag", listFPag);
        List<Compra> listaCompras = compraService.pesquisar(status, pesquisa);
        model.addAttribute("listaCompras", listaCompras);
        return "indexCompra";
    }
	
	@PostMapping("/salvarCompra")
	public String salvarCompra(@ModelAttribute("compra") Compra compra) {
		compra.setStatus("LANÇADO");
		compraService.salvarCompra(compra);
		return "redirect:/indexCompra";
	}
	
	@GetMapping("/gerarPagamentoCompra/{id}")
	public String gerarPagamentoCompra(@PathVariable (value = "id") Long id) {
		try {
		LocalDate dataCompra;
		Compra compra = compraService.getCompraById(id);
		if ((compra.getQtdeParcelas() == 0) && (compra.getId_carteira() > 0)) {
			compra.setStatus("FATURADO");
			compraService.salvarCompra(compra);
			dataCompra = LocalDate.now();
			Pagamento pagamento = new Pagamento();
			pagamento.setDt_pag(dataCompra);
			pagamento.setId_for(compra.getId_for());
			pagamento.setId_compra(compra.getId_compra());
			pagamento.setObs("GERADO ATRAVÉS DA COMPRA");
			pagamento.setStatus("LANÇADO");
			pagamento.setId_carteira(compra.getId_carteira());
			pagamento.setVlr_pago(compra.getValor());
			pagamentoService.salvarPagamento(pagamento);
		}
		return "redirect:/indexCompra";
		}catch (Exception $e)  {			
			return "redirect:/mensagemCompra";	
		}
	}	
	
	@GetMapping("/gerarContaPagar/{id}")
	public String gerarContaPagar(@PathVariable (value = "id") Long id) {
		try {
		Double valorParcela;
		LocalDate dataCompra;
		Compra compra = compraService.getCompraById(id);
		if (compra.getQtdeParcelas() > 0) {
			compra.setStatus("FATURADO");
			compraService.salvarCompra(compra);	
			valorParcela = compra.getValor()/compra.getQtdeParcelas();
			for(int i = 1;i<=compra.getQtdeParcelas();++i) {
				dataCompra = LocalDate.now();
				ContaPagar conta = new ContaPagar();
				conta.setId_for(compra.getId_for());
				conta.setObs("GERADO A PARTIR DE COMPRA");
				conta.setStatus("LANÇADO");
				conta.setId_carteira(compra.getId_carteira());
				conta.setValor(valorParcela);
				conta.setDt_vencim(dataCompra.plusMonths(i));
				conta.setId_compra(compra.getId_compra());
				contaPagarService.salvarContaPagar(conta);
			}
		}
		return "redirect:/indexCompra";
		}catch (Exception $e)  {			
			return "redirect:/mensagemCompra";	
		}
	}
	
	@GetMapping("/deletarCompra/{id}")
	public String deletarCompra(@PathVariable (value = "id") Long id) {
		try {
			Compra compra = compraService.getCompraById(id);
			if (compra.getStatus() != "FATURADO") {
				compra.setStatus("CANCELADO");
				compraService.salvarCompra(compra);
			}
			return "redirect:/indexCompra";
		}catch (Exception $e)  {			
			return "redirect:/mensagemCompra";	
		}
	}
	
	
	@GetMapping("/pageCompra/{pageNo}")
	public String comprasPaginacao(@PathVariable (value = "pageNo") int pageNoCompra, 
			                        @RequestParam("sortField") String sortFieldCompra,
		                        	@RequestParam("sortDir") String sortDirCompra,
		                        	Model model) {
		int pageSizeCompra = 7;
			Page<Compra> pageCompra = compraService.findPaginated(pageNoCompra, pageSizeCompra, sortFieldCompra, sortDirCompra);
			List<Compra> listaCompras = pageCompra.getContent();			
			model.addAttribute("currentPage", pageNoCompra);
			model.addAttribute("totalPages", pageCompra.getTotalPages());
			model.addAttribute("totalItems", pageCompra.getTotalElements());
			model.addAttribute("sortField", sortFieldCompra);
			model.addAttribute("sortDir", sortDirCompra);
			model.addAttribute("reverseSortDir", sortDirCompra.equals("asc") ? "desc" : "asc");
			model.addAttribute("listaCompras", listaCompras);		
		return "indexCompra";
	}

}
