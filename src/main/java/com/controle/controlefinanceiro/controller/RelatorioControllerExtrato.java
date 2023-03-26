package com.controle.controlefinanceiro.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.controle.controlefinanceiro.model.Carteira;
import com.controle.controlefinanceiro.model.Cliente;
import com.controle.controlefinanceiro.model.ContaPagar;
import com.controle.controlefinanceiro.model.ContaReceber;
import com.controle.controlefinanceiro.model.Fornecedor;
import com.controle.controlefinanceiro.model.Pagamento;
import com.controle.controlefinanceiro.model.Recebimento;
import com.controle.controlefinanceiro.model.Relatorio;
import com.controle.controlefinanceiro.repository.CarteiraRepository;
import com.controle.controlefinanceiro.repository.ClienteRepository;
import com.controle.controlefinanceiro.repository.FornecedorRepository;
import com.controle.controlefinanceiro.service.CarteiraService;
import com.controle.controlefinanceiro.service.ClienteService;
import com.controle.controlefinanceiro.service.ContaPagarService;
import com.controle.controlefinanceiro.service.ContaReceberService;
import com.controle.controlefinanceiro.service.FornecedorService;
import com.controle.controlefinanceiro.service.PagamentoService;
import com.controle.controlefinanceiro.service.RecebimentoService;

@Controller
public class RelatorioControllerExtrato {
	@Autowired
	ContaReceberService contaReceberService;
	@Autowired
	ContaPagarService contaPagarService;
	@Autowired
	RecebimentoService recebimentoService;
	@Autowired
	PagamentoService pagamentoService;
	@Autowired
	CarteiraService carteiraService;
	@Autowired
	ClienteService clienteService;
	@Autowired
	FornecedorService fornecedorService;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private FornecedorRepository fornecedorRepository;
	@Autowired
	private CarteiraRepository carteiraRepository;
	CarteiraController carteiraController;
	
	@GetMapping("/transacoes-chamada-resultado")
	public String viewHomePage(Model model, List<Relatorio> listaRelatorio, 
			Double totalRecebido, Double totalPago) {		
        model.addAttribute("listaRelatorio", listaRelatorio);
        model.addAttribute("totalPago", totalPago);
        model.addAttribute("totalRecebido", totalRecebido);
		return "transacoesResultado";
	}
		
}
