package ar.uba.fi.tdd.rulogic;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ar.uba.fi.tdd.rulogic.exception.NonExistentMethodException;
import ar.uba.fi.tdd.rulogic.exception.WrongSyntaxException;

public class InterpreterTest {

	@Rule
	public ExpectedException expExc = ExpectedException.none();
	
	private Interpreter interpreter;
	private Interpreter numbers_interpreter;

	@Before
	public void setUp() throws Exception {
		this.interpreter = new Interpreter("./src/main/resources/rules.db");
		this.numbers_interpreter =  new Interpreter("./src/main/resources/numbers.db");
	}
	
	@Test
	public void testEmptyQueryThrows() {
		expExc.expect(WrongSyntaxException.class);
		
		this.interpreter.answer("");	
	}
	
	@Test
	public void testBadDBFileNameThrows() {
		try {
			new Interpreter("zaraza");
		} catch (IOException e) {
			Assert.assertTrue(true);
		}
	}
	
	@Test
	public void testWrongQueryThrows() {
		expExc.expect(WrongSyntaxException.class);
		
		this.interpreter.answer("varon");		
	}
	
	@Test
	public void testQueryNonExistentMethodNameThrows() {
		expExc.expect(NonExistentMethodException.class);
		
		this.interpreter.answer("lala(pepe,juan)");		
	}
	
	@Test
	public void testQueryNonExistentMethodParamCountThrows() {
		expExc.expect(NonExistentMethodException.class);
		
		this.interpreter.answer("hijo(pepe,juan, roberto, maria)");		
	}
	
	
	@Test
	public void testIncompleteDBThrows() throws IOException {
		expExc.expect(WrongSyntaxException.class);
		new Interpreter("./src/main/resources/incomplete.db");
	}

	@Test
	public void testParentDBFacts() {		
		//True:
		Assert.assertTrue(this.interpreter.answer("varon (nicolas)"));
		Assert.assertTrue(this.interpreter.answer("varon (juan)"));
		Assert.assertTrue(this.interpreter.answer("varon (nicolas)"));
		Assert.assertTrue(this.interpreter.answer("padre(juan,pepe)"));
		
		//False
		Assert.assertFalse(this.interpreter.answer("varon (maria)"));
		Assert.assertFalse(this.interpreter.answer("padre(  mario, pepe   )"));
	}
	
	@Test
	public void testParentDBRules() {
		//True:
		Assert.assertTrue(this.interpreter.answer("hijo ( pepe, juan)"));
		
		//False
		Assert.assertFalse(this.interpreter.answer("hija (maria, roberto)"));		
	}
	
	@Test
	public void testNumbersDBFacts() {
		//True:
		Assert.assertTrue(this.numbers_interpreter.answer("add (one, one, two)"));
		
		//False
		Assert.assertFalse(this.numbers_interpreter.answer("add (two, one, one)"));
	}
	
	@Test
	public void testNumbersDBRules() {
		//True:
		Assert.assertTrue(this.numbers_interpreter.answer("subtract (two, one, one)"));
		
		//False
		Assert.assertFalse(this.numbers_interpreter.answer("subtract (one, one, two)"));
	}

}
