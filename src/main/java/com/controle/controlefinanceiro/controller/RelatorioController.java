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
import com.controle.controlefinanceiro.controller.RelatorioControllerExtrato;

@Controller
public class RelatorioController {
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
	
	RelatorioControllerExtrato extratoController;
	
	@GetMapping("/transacoes-chamada")
	public String viewHomePage(Model model) {		
		List<Cliente> listCliente = clienteRepository.findAtivos();
	    model.addAttribute("listCliente", listCliente);	
	    List<Relatorio> listaRelatorio = new ArrayList<>();
	    model.addAttribute("listaRelatorio", listaRelatorio);
		List<Fornecedor> listFor = fornecedorRepository.findAtivos();
	    model.addAttribute("listFor", listFor);	    
		List<Carteira> listCarteira = carteiraRepository.findAtivos();
	    model.addAttribute("listCarteira", listCarteira);	
	    String nome_cli = "";
	    model.addAttribute("nome_cli",nome_cli);
	    String nome_for = "";
	    model.addAttribute("nome_for",nome_for);
	    Integer id_carteira = 0;
	    model.addAttribute("id_carteira",id_carteira);
	    LocalDate dataInicial = LocalDate.now();
	    model.addAttribute("dataInicial",dataInicial);
	    LocalDate dataFinal = LocalDate.now();
	    model.addAttribute("dataFinal",dataFinal);
    	Double totalReceb = (double) 0;
    	Double totalPag = (double) 0;
    	Double saldo = (double) 0;
    	List<Double> listsaldo = new ArrayList<>();  
    	List<Double> listReceb = new ArrayList<>();
    	List<Double> listPag = new ArrayList<>();        
    	listReceb.add(totalReceb);
    	listPag.add(totalPag);
    	listsaldo.add(saldo);
        model.addAttribute("listReceb", listReceb);
        model.addAttribute("listPag", listPag);	    
        model.addAttribute("listsaldo", listsaldo);
		return "transacoes";
	}
	
	@GetMapping("/gerarProjecao/{dataFinal}/{nome_cli}/{nome_for}/{id_carteira}")
    public String gerarProjecao(Model model, @Param("dataFinal") LocalDate dataFinal, @Param("nome_cli") String nome_cli, @Param("nome_for") String nome_for, @Param("id_carteira") Long id_carteira) {		
		LocalDate dataInicial = LocalDate.now();
        List<Relatorio> listaRelatorio = new ArrayList<>();
		Long i = (long) 0;
		Double saldo = carteiraController.obterSaldoCarteiraMesAtualInterno("", id_carteira);
		Cliente cliente;
		Fornecedor fornecedor;
		List<ContaReceber> listaCar = contaReceberService.pesquisar("LANÇADO", nome_cli, dataInicial, dataFinal);
		for(ContaReceber car : listaCar) {
				i++;
				Relatorio relatorio = new Relatorio();
				relatorio.setDt_movim(car.getDt_vencim());
				relatorio.setId_cliente(car.getId_cli());
				relatorio.setId_relatorio(i);
				cliente = clienteService.getClienteById(car.getId_cli());
				relatorio.setNome_cliente(cliente.getNome());
				relatorio.setTipoMovimento("CONTA A RECEBER");
				relatorio.setValor(car.getValor());
				saldo = saldo + car.getValor();
				listaRelatorio.add(relatorio);
		}
		List<ContaPagar> listaCap = contaPagarService.pesquisar("LANÇADO", nome_for, dataInicial, dataFinal);
		for(ContaPagar cap : listaCap) {
				i++;
				Relatorio relatorio = new Relatorio();
				relatorio.setDt_movim(cap.getDt_vencim());
				relatorio.setId_cliente(cap.getId_for());
				relatorio.setId_relatorio(i);
				fornecedor = fornecedorService.getFornecedorById(cap.getId_for());
				relatorio.setNome_cliente(fornecedor.getNome());
				relatorio.setTipoMovimento("CONTA A PAGAR");
				relatorio.setValor(cap.getValor());
				saldo = saldo - cap.getValor();
				listaRelatorio.add(relatorio);
		}
		listaRelatorio.sort(Comparator.comparing(Relatorio::getDt_movim));
        model.addAttribute("listaRelatorio", listaRelatorio);
        model.addAttribute("saldoProjetado", saldo);
        return "indexCarteira";
    }
	
	@GetMapping("/gerarExtrato/{dataInicial}/{dataFinal}/{nome_cli}/{nome_for}/{id_carteira}")
    public String pesquisar(Model model, @Param("dataInicial") String dataInicial, @Param("dataFinal") String dataFinal, @Param("nome_cli") String nome_cli, @Param("nome_for") String nome_for, @Param("id_carteira") Long id_carteira) {
        List<Relatorio> listaRelatorio = new ArrayList<>();
        LocalDate dataIniLocal = LocalDate.now();
        LocalDate dataFimLocal = LocalDate.now();
        if (dataInicial.equals("")) {
        	System.out.println("null ini");
        	dataIniLocal = LocalDate.now();
        }else {
        	System.out.println(dataInicial);
        	dataIniLocal = LocalDate.parse(dataInicial);
        }
        if (dataFinal.equals("")) {
        	dataFimLocal = LocalDate.now().plusYears(1);
        }else {
        	dataFimLocal = LocalDate.parse(dataFinal);
        }        
		Long i = (long) 0;
		Double totalRecebido = (double) 0;
		Double totalPago = (double) 0;
		Cliente cliente;
		Fornecedor fornecedor;		
		if (nome_cli != "") {
			for(Recebimento recebimento : recebimentoService.pesquisar("LANÇADO", nome_cli, dataIniLocal, dataFimLocal)) {
				if (recebimento.getId_carteira() == id_carteira) {
					i++;
					Relatorio relatorio = new Relatorio();
					relatorio.setDt_movim(recebimento.getDt_pag());
					relatorio.setId_cliente(recebimento.getId_cli());
					relatorio.setId_relatorio(i);
					cliente = clienteService.getClienteById(recebimento.getId_cli());
					relatorio.setNome_cliente(cliente.getNome());
					relatorio.setTipoMovimento("RECEBIMENTO");
					relatorio.setValor(recebimento.getVlr_pago());
					totalRecebido = totalRecebido + recebimento.getVlr_pago();
					listaRelatorio.add(relatorio);
				}
			}
		}else {
			for(Recebimento recebimento : recebimentoService.pesquisarPorData("LANÇADO", dataIniLocal, dataFimLocal)) {
				if (recebimento.getId_carteira() == id_carteira) {
					i++;
					Relatorio relatorio = new Relatorio();
					relatorio.setDt_movim(recebimento.getDt_pag());
					relatorio.setId_cliente(recebimento.getId_cli());
					relatorio.setId_relatorio(i);
					cliente = clienteService.getClienteById(recebimento.getId_cli());
					relatorio.setNome_cliente(cliente.getNome());
					relatorio.setTipoMovimento("RECEBIMENTO");
					relatorio.setValor(recebimento.getVlr_pago());
					totalRecebido = totalRecebido + recebimento.getVlr_pago();
					listaRelatorio.add(relatorio);
				}
			}			
		}
		
		
		if (nome_for != "") {
			for(Pagamento pagamento : pagamentoService.pesquisar("LANÇADO", nome_for, dataIniLocal, dataFimLocal)) {
				if (pagamento.getId_carteira() == id_carteira) {
					i++;
					Relatorio relatorio = new Relatorio();
					relatorio.setDt_movim(pagamento.getDt_pag());
					relatorio.setId_cliente(pagamento.getId_for());
					relatorio.setId_relatorio(i);
					fornecedor = fornecedorService.getFornecedorById(pagamento.getId_for());
					relatorio.setNome_cliente(fornecedor.getNome());
					relatorio.setTipoMovimento("PAGAMENTO");
					relatorio.setValor(pagamento.getVlr_pago());
					totalPago = totalPago + pagamento.getVlr_pago();
					listaRelatorio.add(relatorio);
				}
			}
		}else {
			for(Pagamento pagamento : pagamentoService.pesquisarPorData("LANÇADO", dataIniLocal, dataFimLocal)) {
				if (pagamento.getId_carteira() == id_carteira) {
					i++;
					Relatorio relatorio = new Relatorio();
					relatorio.setDt_movim(pagamento.getDt_pag());
					relatorio.setId_cliente(pagamento.getId_for());
					relatorio.setId_relatorio(i);
					fornecedor = fornecedorService.getFornecedorById(pagamento.getId_for());
					relatorio.setNome_cliente(fornecedor.getNome());
					relatorio.setTipoMovimento("PAGAMENTO");
					relatorio.setValor(pagamento.getVlr_pago());
					totalPago = totalPago + pagamento.getVlr_pago();
					listaRelatorio.add(relatorio);
				}
			}			
		}
		listaRelatorio.sort(Comparator.comparing(Relatorio::getDt_movim));
        model.addAttribute("listaRelatorio", listaRelatorio);
    	Double totalReceb = totalRecebido;
    	Double totalPag = totalPago;
    	Double saldo = totalReceb - totalPag;
    	List<Double> listReceb = new ArrayList<>();
    	List<Double> listPag = new ArrayList<>();  
    	List<Double> listsaldo = new ArrayList<>();  
    	listReceb.add(totalReceb);
    	listPag.add(totalPag);
    	listsaldo.add(saldo);
        model.addAttribute("listReceb", listReceb);
        model.addAttribute("listPag", listPag);
        model.addAttribute("listsaldo", listsaldo);
     //   extratoController = new RelatorioControllerExtrato();
      //  return extratoController.viewHomePage(model, listaRelatorio, totalRecebido, totalPago);
        return "transacoes";
    }
	
}
