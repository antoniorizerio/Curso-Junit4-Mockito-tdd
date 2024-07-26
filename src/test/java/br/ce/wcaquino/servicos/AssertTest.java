package br.ce.wcaquino.servicos;

import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.entidades.Usuario;

public class AssertTest {
	
	@Test
	public void teste() {
		Assert.assertTrue(true);
		Assert.assertFalse(false);
		
		Assert.assertEquals("Erro de comparacao", 1, 1);
	
		
		/**
		 * Então, por exemplo, se você passar um valor de delta de 0.01, o JUnit vai lançar 
		 * um erro se abs(expected - actual) > 0.01.
		 * Assert.assertEquals(1.5, 1.0, 0.5); - toleravel uma diferença de 0.5. Delta é uma margem de erro
		 * de comparação.
		 */
		Assert.assertEquals("", 1.5, 1.0, 0.5);
		Assert.assertEquals(0.51, 0.51, 0.01);
		Assert.assertEquals(0.51234, 0.512, 0.001);
		Assert.assertEquals(0.51234, 0.5122, 0.0002);
		Assert.assertEquals(Math.PI, 3.14, 0.01);
		
		int i = 5;
		Integer i2 = 5;
		
		Assert.assertEquals(Integer.valueOf(i), i2);
		Assert.assertEquals(i, i2.intValue());
		
		Assert.assertEquals("bola", "bola");
		Assert.assertNotEquals("bola", "casa");
		Assert.assertTrue("bolA".equalsIgnoreCase("Bola"));
		Assert.assertTrue("bolA".startsWith("bo"));
		
		Usuario u1 = new Usuario("Usu 1");
		Usuario u2 = new Usuario("Usu 1");
		Usuario u3 = u2;
		Usuario u4 = null;
		
		Assert.assertEquals(u1, u2);
		
		Assert.assertSame(u2, u3);
		Assert.assertNotSame(u1, u2);
		
		Assert.assertTrue(u4 == null);
		Assert.assertNull(u4);
		Assert.assertNotNull(u3);
	}
}