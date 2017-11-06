package ar.uba.fi.tdd.rulogic;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ar.uba.fi.tdd.rulogic.exception.NonExistentMethodException;
import ar.uba.fi.tdd.rulogic.exception.RecursiveRuleCallException;
import ar.uba.fi.tdd.rulogic.exception.RuleJoinException;

public class EdgeCaseTest {

	@Rule
	public ExpectedException expExc = ExpectedException.none();
	
	private Interpreter interpreter;

	@Before
	public void setUp() throws IOException {
		this.interpreter = new Interpreter("./src/main/resources/edge_case.db");
	}
	
	@Test
	public void testRuleWithFixedArgSetIsAccepted() {
		Assert.assertTrue(this.interpreter.answer("hijo_de_juan(pepe)"));
	}
	
	@Test
	public void testRuleCallingAnotherRuleIsAccepted() {
		Assert.assertTrue(this.interpreter.answer("hija_de_hector(maria)"));
	}
	
	@Test
	public void testRuleParamsAreCaseSensitive() {
		Assert.assertTrue(this.interpreter.answer("wrapper(pepe, juan)"));
	}
	
	@Test
	public void testSameNameRuleAndFactOKIfTheyDiferInParamLength() {
		Assert.assertTrue(this.interpreter.answer("varon(juan, pepe, juan)"));
	}
	
	@Test
	public void testRuleWithUnusedParamsIsAccepted() {
		Assert.assertTrue(this.interpreter.answer("unused(juan, zaraza)"));
	}
	
	@Test
	public void testRuleWithNoParamsIsAccepted() {
		Assert.assertTrue(this.interpreter.answer("empty_rule()"));
	}
	
	@Test
	public void testFactWithNoArgsIsAccepted() {
		Assert.assertTrue(this.interpreter.answer("OK_fact()"));
	}
	
	@Test
	public void testRuleAndFactWithSameSignatureThrows() throws IOException {
		expExc.expect(RuleJoinException.class);
		this.interpreter = new Interpreter("./src/main/resources/same_signature_rule_fact.db");
	}
	
	@Test
	public void testRuleEvaluatingNonExistentFactOrRuleCallThrows() {
		expExc.expect(NonExistentMethodException.class);
		this.interpreter.answer("bad_rule(zaraza)");
	}
	
	@Test
	public void testRuleCallingItselfThrows() {
		expExc.expect(RecursiveRuleCallException.class);
		this.interpreter.answer("error(test,this,error)");
	}
}
