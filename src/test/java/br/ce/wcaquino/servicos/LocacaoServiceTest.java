package br.ce.wcaquino.servicos;


import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import org.junit.rules.ErrorCollector;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;

/*
 * O Junit para garantir que os testes sejam independentes, inicializa as vari�veis da Classe a cada
 * novo teste. Para que todos os testes estejam no mesmo ponto de partidas e com tudo zerado.
 * Junit n garante a execu��o dos testes na ordem em que foram declarados.
 */
public class LocacaoServiceTest {
	
	private LocacaoService service;
	
	
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	/**
	 * When writing tests, it is common to find that several tests need similar objects created 
	 * before they can run. Annotating a public void method with @Before causes that method to be run 
	 * before the org.junit.Test method
	 */
	@Before
	public void before() {
		service = new LocacaoService();
	}
	
	/**
	 * If you allocate external resources in a org.junit.Before method you need to release them after 
	 * the test runs. Annotating a public void method with @After causes that method to be run after 
	 * the org.junit.Test method. All @Aftermethods are guaranteed to run even if a org.junit.Before 
	 * or org.junit.Test method throws an exception.
	 */
	@After
	public void after() {
		
	}
	
	/**
	 * Sometimes several tests need to share computationally expensive setup(like logging into a database). 
	 * While this can compromise the independence of tests, sometimes it is a necessary optimization. 
	 * Annotating a public static void no-arg method with @BeforeClass causes it to be run once before 
	 * any of the test methods in the class.
	 */
	@BeforeClass
	public static void beforeClass() {
		
	}
	
	/*
	 * If you allocate expensive external resources in a org.junit.BeforeClass method you need to release 
	 * them after all the tests in the class have run. Annotating a public static void method with 
	 * @AfterClass causes that method to be run after all the tests in the class have been run.
	 */
	@AfterClass
	public static void afterClass() {
		
	}
	
	/**
	 * Agora com a anota��o @Test Junit reconhece como um m�todo de Teste.
	 * Pode ser qualquer nome, n�o importa.
	 */
	@Test
	public void deveAlugarFilmeComSucesso() {
		// Assume basically means "don't run this test if these conditions don't apply" //
		Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		try {
			
			
			// Cen�rio
			
			/**
			 *  Basicamente um teste se divide em 3 etapas - Cen�rio: onde as vari�veis s�o
			 *  inicializadas; A��o: onde vamos invocar o m�todo que queremos testar;
			 *  Valida��o: onde vamos coletar os resultados da a��o com aquele cen�rio
			 *  especificado e podemos avaliar se o resultado est� de acordo com o esperado.
			 */
			
			Usuario usuario = new Usuario("Antonio");
			List<Filme> listFilme = Arrays.asList(new Filme("Cavaleiros do Zod", 2, 5.0),
					new Filme("DBZ", 1, 4.3));
			
			// A��o - onde executo o m�todo que desejo testar //
			Locacao locacao = service.alugarFilme(usuario, listFilme);
			
			// Verifica��o - um teste deve ser verific�vel
			// 0.1 � minha margem de erro - Delta //
			assertEquals(9.3, locacao.getValor(), 0.1);
			
			// Uso de MatcherAssert.assertThat melhora a leitura //
			// Confirme que o valor tal � igual a 5 //
			// Confirme que o valor tal n�o � igual a 6 //
			// Confirme que o valor tal � 5 //
			assertThat(locacao.getValor(), is(equalTo(9.3)));
			assertThat(locacao.getValor(), is(not(6.0)));
			assertThat(locacao.getValor(), is(9.3));
			
			assertTrue(isMesmaData(locacao.getDataLocacao(), new Date()));
			
			assertThat(isMesmaData(locacao.getDataLocacao(), new Date()), equalTo(true));
			
			assertTrue(isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));
			
			assertThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), equalTo(true));
			
			// � um teste pq atende as 3 etapas, Cen�rio, A��o e Verifica��o - e o principio FIRST
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail("Nao deveria lancar exce��o");
		}
	}
	
	
	// Utilizando uma cole��o de erros, testando macrofuncionalidade //
	
	@Test
	public void testeLocacaoErrorCollector() throws Exception {
		
		// Assume basically means "don't run this test if these conditions don't apply" //
		Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		// Cen�rio	
		Usuario usuario = new Usuario("Antonio");
		List<Filme> listFilme = Arrays.asList(new Filme("Cavaleiros do Zod", 2, 5.0),
				new Filme("DBZ", 1, 4.3));
		
		// A��o - onde executo o m�todo que desejo testar //
		Locacao locacao = service.alugarFilme(usuario, listFilme);
		
		// Verifica��o - um teste deve ser verific�vel
		error.checkThat(locacao.getValor(), is(equalTo(9.3)));
		error.checkThat(locacao.getValor(), is(not(6.0)));
		error.checkThat(locacao.getValor(), is(9.3));
		
		error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), equalTo(true));
		
		error.checkThat(isMesmaData(locacao.getDataRetorno(), 
				obterDataComDiferencaDias(1)), equalTo(true));
		
		// � um teste pq atende as 3 etapas, Cen�rio, A��o e Verifica��o - e o principio FIRST
	}
	
	// Se o teste n est� esperando exce��o alguma deixa que o Junit gerencia pra vc.
	// Quem vai tratar a exce�ao lan�ada sera o pr�prio Junit - throws Exception
	// Se eu n estou esperando exce��o deixe que o JUnit trate a exce��o pra mim 
	/**
	 * Estou utilizando uma exce��o muito gen�rica - Exception, e � importante testar a mensagem lan�ada
	 * na exce��o, nessa forma isso n � poss�vel. Por isso foi criada a Exce��o FilmeSemEstoqueException.
	 * Importante quando somente o nome da Exce��o importa pra vc, garantindo o motivo pelo qual a
	 * exce��o foi lan�ada.
	 * @throws Exception
	 */
	@Test(expected = FilmeSemEstoqueException.class)
	public void deveLancarExcecaoFilmeSemEstoque() throws Exception {
		// Cen�rio
		Usuario usuario = new Usuario("Antonio");
		List<Filme> listFilme = Arrays.asList(new Filme("Cavaleiros do Zod", 0, 5.0),
				new Filme("DBZ", 0, 4.3));
		
		// A��o - onde executo o m�todo que desejo testar //
		service.alugarFilme(usuario, listFilme);
	}
	
	// Consigo verificar a mensagem que vem da exce��o
	// MatcherAssert.assertThat(e.getMessage(), is("Filme sem estoque"));
	@Test
	public void testLocacaoFilmeSemEstoque_2() {
		// Cen�rio
		Usuario usuario = new Usuario("Antonio");
		List<Filme> listFilme = Arrays.asList(new Filme("Cavaleiros do Zod", 0, 5.0),
				new Filme("DBZ", 1, 4.3));
		try {
			
			
			// A��o - onde executo o m�todo que desejo testar //
			service.alugarFilme(usuario, listFilme);
			fail("Deveria ter lan�ado uma exce��o");
		} catch (Exception e) {
			assertThat(e.getMessage(), is("Filme sem estoque - "+listFilme.get(0).getNome()));
		}
	}
	
	/**
	 * Asserts that runnable throws an exception of type expectedThrowable when executed. 
	 * If it does, the exception object is returned
	 * @throws Exception
	 */
	@Test
	public void testLocacaoFilmeSemEstoque_3() {
		
		// Cen�rio
		Usuario usuario = new Usuario("Antonio");
		List<Filme> listFilme = Arrays.asList(new Filme("Cavaleiros do Zod", 2, 5.0),
				new Filme("DBZ", 0, 4.3));
		
		Exception excep = Assert.assertThrows(Exception.class, () -> {
			
			
			// A��o - onde executo o m�todo que desejo testar //
			Locacao locacao = service.alugarFilme(usuario, listFilme);
		});
		assertThat(excep.getMessage(), is("Filme sem estoque - "+listFilme.get(1).getNome()));
	}
	
	@Test
	public void testLocacaoFilmeSemEstoque_4() {
		
		// Cen�rio
		Usuario usuario = new Usuario("Antonio");
		List<Filme> listFilme = Arrays.asList(new Filme("Cavaleiros do Zod", 0, 5.0),
				new Filme("DBZ", 1, 4.3));
		
		Exception excep = Assert.assertThrows(Exception.class, new ThrowingRunnable() {
			
			@Override
			public void run() throws Throwable {
				
				
				// A��o - onde executo o m�todo que desejo testar //
				service.alugarFilme(usuario, listFilme);
				
			}
		});
		
		assertThat(excep.getMessage(), is("Filme sem estoque - "+listFilme.get(0).getNome()));
	}
	
	/**
	 * Eu desejo tratar a exce��o do tipo LocadoraException, ent�o a FilmeSemEstoqueException
	 * eu deixo para o Junit tratar.
	 * 
	 * @throws FilmeSemEstoqueException
	 */
	@Test
	public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException {
		try {
			// cen�rio
			List<Filme> listFilme = Arrays.asList(new Filme("Cavaleiros do Zod", 2, 5.0),
					new Filme("DBZ", 1, 4.3));

			// acao
			service.alugarFilme(null, listFilme);
			fail("msg=Exce��o - LocadoraException ('Usuario vazio') - n�o lan�ada");
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usuario vazio"));
		}
	}
	
	@Test
	public void naoDeveAlugarFilmeSemFilme() {
		LocadoraException exception = assertThrows(LocadoraException.class, () -> {
			
			// cen�rio
			Usuario usuario = new Usuario("Antonio");
			
			// acao
			service.alugarFilme(usuario, null);
			
		});
		
		assertThat(exception.getMessage(), is("Filme vazio"));	
	}
	
	@Test
	public void testLocacaoFilmeVazio() {
		LocadoraException exception = assertThrows(LocadoraException.class, () -> {
			
			// cen�rio
			Usuario usuario = new Usuario("Antonio");
			
			// acao
			service.alugarFilme(usuario, new ArrayList<>());
			
		});
		
		assertThat(exception.getMessage(), is("Filme vazio"));	
	}
	
	/*
	 * Utilizando TDD, primeiro vc pensa nos comportamentos.
	 * 25% no 3 filme
	 * 50% no 4 filme
	 * 75% no 5 filme
	 * 100% no 6 filme
	 */
	@Test
	public void deveTerDescontoVinteCincoPorcentTerceiroFilme() 
			throws FilmeSemEstoqueException, LocadoraException {
		// cen�rio
		Usuario usuario = new Usuario("Antonio");
		// cen�rio
		List<Filme> listFilme = Arrays.asList(
				new Filme("Cavaleiros do Zod", 2, 10.0),
				new Filme("DBZ", 1, 10.0),
				new Filme("DBZ - Brolly", 1, 10.0));
		
		// acao
		Locacao locacao = service.alugarFilme(usuario, listFilme);
		assertEquals(27.5, locacao.getValor(), 0.1);
		assertThat(locacao.getValor(), is(27.5));
		assertThat(locacao.getValor(), is(not(30.0)));
	}
	
	@Test
	public void deveTerDescontoCinquentaPorcentQuartoFilme() 
			throws FilmeSemEstoqueException, LocadoraException {
		// cen�rio
		Usuario usuario = new Usuario("Antonio");
		// cen�rio
		List<Filme> listFilme = Arrays.asList(
				new Filme("Cavaleiros do Zod", 2, 10.0),
				new Filme("DBZ", 1, 10.0),
				new Filme("DBZ - Brolly", 1, 10.0),
				new Filme("DBZ - Brolly", 1, 10.0));
		
		// acao
		Locacao locacao = service.alugarFilme(usuario, listFilme);
		assertEquals(35.0, locacao.getValor(), 0.1);
		assertThat(locacao.getValor(), is(35.0));
		assertThat(locacao.getValor(), is(not(40.0)));
	}
	
	@Test
	public void deveTerDescontoSetentaCincoPorcentQuintoFilme() 
			throws FilmeSemEstoqueException, LocadoraException {
		// cen�rio
		Usuario usuario = new Usuario("Antonio");
		// cen�rio
		List<Filme> listFilme = Arrays.asList(
				new Filme("Cavaleiros do Zod", 2, 10.0),
				new Filme("DBZ", 1, 10.0),
				new Filme("DBZ - Brolly", 1, 10.0),
				new Filme("DBZ - Brolly", 1, 10.0),
				new Filme("DBZ - Brolly", 1, 10.0));
		
		// acao
		Locacao locacao = service.alugarFilme(usuario, listFilme);
		assertEquals(42.50, locacao.getValor(), 0.1);
		assertThat(locacao.getValor(), is(42.50));
		assertThat(locacao.getValor(), is(not(50.0)));
	}
	
	@Test
	public void deveTerDescontoCemPorcentSextoFilme() 
			throws FilmeSemEstoqueException, LocadoraException {
		// cen�rio
		Usuario usuario = new Usuario("Antonio");
		// cen�rio
		List<Filme> listFilme = Arrays.asList(
				new Filme("Cavaleiros do Zod", 2, 10.0),
				new Filme("DBZ", 1, 10.0),
				new Filme("DBZ - Brolly", 1, 10.0),
				new Filme("DBZ - Brolly", 1, 10.0),
				new Filme("DBZ - Brolly", 1, 10.0),
				new Filme("DBZ - Brolly", 1, 10.0));
		
		// acao
		Locacao locacao = service.alugarFilme(usuario, listFilme);
		assertEquals(50.0, locacao.getValor(), 0.1);
		assertThat(locacao.getValor(), is(50.0));
		assertThat(locacao.getValor(), is(not(60.0)));
	}
	
	@Test
	public void naoDeveTerDescontoFilme() 
			throws FilmeSemEstoqueException, LocadoraException {
		// cen�rio
		Usuario usuario = new Usuario("Antonio");
		// cen�rio
		List<Filme> listFilme = Arrays.asList(
				new Filme("Cavaleiros do Zod", 2, 10.0),
				new Filme("DBZ", 1, 10.0));
		
		// acao
		Locacao locacao = service.alugarFilme(usuario, listFilme);
		assertEquals(20.0, locacao.getValor(), 0.1);
		assertThat(locacao.getValor(), is(20.0));
	}
	
	@Test
	// @Ignore // Deixar o teste em standby
	public void deveDevolverNaSegundaAoAlugarNoSabado() 
			throws FilmeSemEstoqueException, LocadoraException {

		// Assume basically means "don't run this test if these conditions don't apply" //
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		Usuario user = new Usuario("Usuario 1");
		List<Filme> listFilme = Arrays.asList(new Filme("Cavaleiros do Zod", 2, 10.0), 
				new Filme("DBZ", 1, 10.0));

		Locacao retorno = service.alugarFilme(user, listFilme);
		
		boolean ehSegunda = DataUtils.verificarDiaSemana(retorno.getDataRetorno(), Calendar.MONDAY);
		Assert.assertTrue(ehSegunda);
	}
}