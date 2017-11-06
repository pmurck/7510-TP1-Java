package ar.uba.fi.tdd.rulogic.model;

import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;

import ar.uba.fi.tdd.rulogic.exception.NonExistentMethodException;

public class KnowledgeBaseTest {

	@org.junit.Rule
	public ExpectedException expExc = ExpectedException.none();
	
	@InjectMocks
	private KnowledgeBase KB;
	private final String name = "hijos"; 
	private final List<String> params = Arrays.asList("X", "Y", "Z");
	private final List<String> call1_params = Arrays.asList("X", "Y");
	private final List<String> call2_params = Arrays.asList("X", "Z");
	private final String f_name = "hijo";
	private final List<String> f1_params = Arrays.asList("pepe", "juan");
	private final List<String> f2_params = Arrays.asList("pepe", "marcelo");
	private Fact fact_1;
	private Fact fact_2;
	private ar.uba.fi.tdd.rulogic.model.Rule rule;
	private final List<String> OK_args = Arrays.asList("pepe", "juan", "marcelo");

	@Before
	public void setUp() throws Exception {
		fact_1 = new Fact(f_name, f1_params);
		fact_2 = new Fact(f_name, f2_params);
		rule = new Rule(name, params, 
				Arrays.asList(new Call(f_name, call1_params), new Call(f_name, call2_params)));
		initMocks(this);
	}

	@Test
	public void testNonExistentFactOrRuleThrows() {
		expExc.expect(NonExistentMethodException.class);
		
		KB.query("nada", Arrays.asList("la","la"));
	}
	
	@Test
	public void testExistentFactOrRuleWithIncorrectParamCountThrows() {
		KB.add(fact_1);
		expExc.expect(NonExistentMethodException.class);
		
		KB.query(f_name, Arrays.asList("la","la","bo","sta"));
	}
	
	@Test
	public void testQueryForRuleWithNonExistentFactCallsThrows() {
		KB.add(rule);
		expExc.expect(NonExistentMethodException.class);
		
		KB.query(name, OK_args);
	}
	
	@Test
	public void testQueryForFactWithFalseArgs() {
		KB.add(fact_1);
		
		Assert.assertFalse(KB.query(f_name, f2_params));
	}
	
	@Test
	public void testQueryForRuleWithFalseArgs() {
		KB.add(fact_1);
		KB.add(fact_2);
		KB.add(rule);	
		Assert.assertFalse(KB.query(name, Arrays.asList("za","ra","za")));
	}
	
	@Test
	public void testQueryForFactWithTrueArgs() {
		KB.add(fact_1);
		Assert.assertTrue(KB.query(f_name, f1_params));
		KB.add(fact_2);
		Assert.assertTrue(KB.query(f_name, f2_params));
		Assert.assertTrue(KB.query(f_name, f1_params));
	}
	
	@Test
	public void testQueryForRuleWithTrueArgs() {
		KB.add(fact_1);
		KB.add(fact_2);
		KB.add(rule);
		Assert.assertTrue(KB.query(name, OK_args));
	}

}
