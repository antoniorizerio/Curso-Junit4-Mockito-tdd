package br.ce.wcaquino.servicos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.ce.wcaquino.exceptions.NaoPodeDividirPorZeroException;


/**
 * TDD - primeiro criamos as classes de Teste. Baseado no comportamento esperado.
 * Como a classe se chama CalculadoraTest, quer dizer que estou testando a classe
 * Calculadora
 * @author kakab
 *
 */
public class CalculadoraTest {
	
	private Calculadora calc;
	
	@Before
	public void setup() {
		calc = new Calculadora();
	}

	@Test
	public void deveSomarDoisValores() {
		//cenario
		int a = 5;
		int b = 3;
		
		//acao
		int resultado = calc.somar(a, b);
		
		//verificacao
		Assert.assertEquals(8, resultado);
	}
	
	@Test
	public void deveSubtrairDoisValores() {
		//cenario
		int a = 8;
		int b = 5;
		
		//acao
		int resultado = calc.subtrair(8, 5);
		
		//verificacao
		Assert.assertEquals(3, resultado);
	}
	
	@Test
	public void deveDividirDoisValores() throws NaoPodeDividirPorZeroException {
		//cenario
		int a = 6;
		int b = 3;
		
		//acao
		int resultado = calc.divide(a, b);
	
		//verificacao
		Assert.assertEquals(2, resultado);
	}
	
	@Test(expected = NaoPodeDividirPorZeroException.class)
	public void deveLancarExcecaoAoDividirPorZero() throws NaoPodeDividirPorZeroException {
		int a = 6;
		int b = 0;
		
		calc.divide(a, b);
	}
	
	@Test
	public void deveLancarExcecaoAoDividirPorZero_1() {
	
		Assert.assertThrows(NaoPodeDividirPorZeroException.class, () -> {
			
			int a = 6;
			int b = 0;
			
			calc.divide(a, b);
			
		});
	}
}
