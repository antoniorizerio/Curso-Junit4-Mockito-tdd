package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoService {
	
	private static final Logger loggerFullyName = LogManager.getLogger(LocacaoService.class);
	
	public String vPublica; 		// public - acesso permitido de qualquer classe em qualquer lugar;
	protected String vProtegida;	// protected - acessível a partir de todas as classes no mesmo pacote 
									// 				e a partir de qualquer subclasse em qualquer lugar.
	private String vPrivada;		// private - sem acesso de fora da classe;
	String vDefault;				// default - Somente dentro do mesmo pacote;
	
	
	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException, 
			LocadoraException {

		if (usuario == null) {
			throw new LocadoraException("Usuario vazio");
		}

		if (filmes == null || filmes.isEmpty()) {
			throw new LocadoraException("Filme vazio");
		}

		for(Filme element : filmes) {
			if(element.getEstoque() == 0) {
				throw new FilmeSemEstoqueException("Filme sem estoque - " + element.getNome());
			}
		}
		
		Locacao locacao = new Locacao();
		locacao.setFilmes(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(calculoValorAlocacao(filmes));

		// Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		if(DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY)) {
			dataEntrega = adicionarDias(dataEntrega, 1);
		}
		locacao.setDataRetorno(dataEntrega);

		// Salvando a locacao...
		// TODO adicionar mÃ©todo para salvar

		return locacao;
	}

	private double calculoValorAlocacao(List<Filme> filmes) {
		
		if(filmes.size() > 2) {
		
			double total = 0d;
			
			int qtdFilmes = filmes.size();
			
			for(int aux = 0; aux < qtdFilmes-1; aux++) {
				total += filmes.get(aux).getPrecoLocacao();
			}
			total += filmes.get(qtdFilmes-1).getPrecoLocacao()
					*ValorPorcentagemPagarEnum.getPorcentPagarPorQtdFilme(qtdFilmes);
			return total;
		}
		return filmes.stream().mapToDouble(Filme::getPrecoLocacao).sum();
	}
	
	private enum ValorPorcentagemPagarEnum {
		TRES(3, 0.75), 
		QUATRO(4, 0.50), 
		CINCO(5, 0.25), 
		SEIS(6, 0.0);
		
		private double porcentPagar;
		private int qtdFilmes;
		
		ValorPorcentagemPagarEnum(int qtdFilmes, double porcentPagar) {
			this.porcentPagar = porcentPagar;
			this.qtdFilmes = qtdFilmes;
		}
		
		public double getPorcentPagar() {
			return porcentPagar;
		}
		
		public int getQtdFilmes() {
			return qtdFilmes;
		}
		
		public static double getPorcentPagarPorQtdFilme(int qtdFilmes) {
			for(ValorPorcentagemPagarEnum valorDesconto : values()) {
				if(valorDesconto.getQtdFilmes() == qtdFilmes) {
					return valorDesconto.getPorcentPagar();
				}
			}
			return 0.d;
		}
	}
}