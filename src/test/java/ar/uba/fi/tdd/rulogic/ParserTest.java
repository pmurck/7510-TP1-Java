package ar.uba.fi.tdd.rulogic;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ar.uba.fi.tdd.rulogic.exception.WrongSyntaxException;
import ar.uba.fi.tdd.rulogic.model.Fact;
import ar.uba.fi.tdd.rulogic.model.Method;

public class ParserTest {
	
	@Rule
	public ExpectedException expExc = ExpectedException.none();
	
	private void testMethodParse(String str, String expectedName, List<String> expectedParams) {
		Method m = Parser.parseFact(str);
		Assert.assertEquals(m.getName(), expectedName);
		Assert.assertEquals(m.getParams(), expectedParams);
	}
	
	private void testMethodParseThrows(String str) {
		expExc.expect(WrongSyntaxException.class);
		Parser.parseFact(str);
	}
	

	@Test
	public void testSyntacticallyCorrectMethodParse() {
		testMethodParse("temp(one, two)", "temp", Arrays.asList("one", "two"));
		testMethodParse("t(o,t)", "t", Arrays.asList("o", "t"));
		testMethodParse("   t    (      o   , t   )     ", "t", Arrays.asList("o", "t"));
		testMethodParse("_hola_(x)", "_hola_", Arrays.asList("x"));
		testMethodParse("empty_args()", "empty_args", Arrays.asList());
	}
	
	@Test
	public void testSyntacticallyIncorrectMethodParse() {
		testMethodParseThrows("(x,y)");
		testMethodParseThrows("(x,)");
		testMethodParseThrows("(,)");
		testMethodParseThrows("()");
		testMethodParseThrows("(x)");
		testMethodParseThrows("");
		testMethodParseThrows("hola");
		testMethodParseThrows("x(");
		testMethodParseThrows("y)");
		testMethodParseThrows("(x");
		testMethodParseThrows("hola(x,");
	}
	
	@Test
	public void testSuccessfulParseToFact() {
		Method m = Parser.parse("hola(pepe,juan)");
		Assert.assertEquals(Fact.class, m.getClass());
	}
	
	@Test
	public void testSuccessfulParseToRule() {
		Method m = Parser.parse("self_saludo(x) :- hola(x,x)");
		Assert.assertEquals(ar.uba.fi.tdd.rulogic.model.Rule.class, m.getClass());
	}
	
	@Test
	public void testUnsuccessfulParseToFactThrows() {
		expExc.expect(WrongSyntaxException.class);
		Parser.parse("hola(pepe,)");
	}
	
	@Test
	public void testUnsuccessfulParseToRuleThrows() {
		expExc.expect(WrongSyntaxException.class);
		Parser.parse("self:-()");
	}

}
