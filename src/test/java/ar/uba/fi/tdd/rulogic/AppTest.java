package ar.uba.fi.tdd.rulogic;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ar.uba.fi.tdd.rulogic.exception.NonExistentMethodException;
import ar.uba.fi.tdd.rulogic.exception.WrongSyntaxException;

public class AppTest {
	
	private String parentFilename = "./src/main/resources/rules.db";
	private String edgeCaseFileName = "./src/main/resources/edge_case.db";
	private String incompleteFileName = "./src/main/resources/incomplete.db";
	private Interpreter interpreter;

	@Before
	public void setUp() throws Exception {
		this.interpreter = new Interpreter(parentFilename);
	}
	
	@Test
	public void testNoArgs() {
		String[] a = {};
		Assert.assertEquals("You must Specify ONE database filename as argument!", App.workWrapper(a));
	}
	
	@Test
	public void testMoreThan2Args() {
		String[] a = {"dos", "argumentos"};
		Assert.assertEquals("You must Specify ONE database filename as argument!", App.workWrapper(a));
	}
	
	@Test
	public void testWrongSyntaxDB() {
		String[] a = {incompleteFileName};
		Assert.assertEquals("Error on DB load -> Wrong Syntax on line content: varon", App.workWrapper(a));
	}
	
	@Test
	public void testNoFileForDB() {
		String[] a = {"zaraza"};
		Assert.assertTrue(App.workWrapper(a).contains("NoSuchFileException"));
	}
	
	@Test
	public void testWrongQuery() {
		Assert.assertEquals("Wrong Syntax on line content: varon", App.answer(this.interpreter, "varon"));
	}
	
	@Test
	public void testQueryNonExistentMethodName() {
		Assert.assertEquals("Cannot find Method with name: pepe", App.answer(this.interpreter, "pepe()"));
	}
	
	@Test
	public void testQueryNonExistentMethodParamCount() {
		Assert.assertEquals("Cannot find Method varon with argument count of 0", App.answer(this.interpreter, "varon()"));
	}
	
	@Test
	public void testQueryRecursiveRuleCall() throws IOException {
		Interpreter temp = new Interpreter(edgeCaseFileName);
		Assert.assertEquals("Recursive call found on Rule: error with parameter count: 3", App.answer(temp, "error(a,b,c)"));
	}

	@Test
	public void testParentDBFacts() {		
		//True:
		Assert.assertEquals("SI", App.answer(this.interpreter, "varon (nicolas)"));
		Assert.assertEquals("SI", App.answer(this.interpreter, "varon (juan)"));
		Assert.assertEquals("SI", App.answer(this.interpreter, "varon (nicolas)"));
		Assert.assertEquals("SI", App.answer(this.interpreter, "padre(juan,pepe)"));
		
		//False
		Assert.assertEquals("NO", App.answer(this.interpreter, "varon (maria)"));
		Assert.assertEquals("NO", App.answer(this.interpreter, "padre(  mario, pepe   )"));
	}
	
	@Test
	public void testParentDBRules() {
		//True:
		Assert.assertTrue(this.interpreter.answer("hijo ( pepe, juan)"));
		
		//False
		Assert.assertFalse(this.interpreter.answer("hija (maria, roberto)"));		
	}
}
