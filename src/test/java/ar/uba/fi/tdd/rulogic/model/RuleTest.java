package ar.uba.fi.tdd.rulogic.model;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ar.uba.fi.tdd.rulogic.exception.RuleJoinException;

public class RuleTest {
	
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
	private KnowledgeBase KB;
	private final List<String> OK_args = Arrays.asList("pepe", "juan", "marcelo");
	
	@Rule
	public ExpectedException expExc = ExpectedException.none();
	
	
	@Before
	public void setUp() throws Exception {
		fact_1 = new Fact(f_name, f1_params);
		fact_2 = new Fact(f_name, f2_params);
		rule = new ar.uba.fi.tdd.rulogic.model.Rule(name, params, 
				Arrays.asList(new Call(f_name, call1_params), new Call(f_name, call2_params)));
		KB = new KnowledgeBase();
		KB.add(fact_1);
		KB.add(fact_2);
		KB.add(rule);
	}

	@Test
	public void testGetRuleName() {
		Assert.assertEquals(this.name, this.rule.getName());
	}
	
	@Test
	public void testGetRuleParams() {
		Assert.assertEquals(this.params, this.rule.getParams());
	}

	@Test
	public void testGetRuleParamCount() {
		Assert.assertEquals(this.params.size(), this.rule.getParamCount());
	}
	
	@Test
	public void testRuleJoinThrows() {
		expExc.expect(RuleJoinException.class);
		this.rule.join(fact_1);
	}
	
	@Test
	public void testCallRuleUsingCorrectArgs() {
		Assert.assertTrue(this.rule.callUsing(OK_args, this.KB));
	}
	
	@Test
	public void testCallRuleUsingIncorrectArgs() {
		Assert.assertFalse(this.rule.callUsing(Arrays.asList("za", "ra", "za"), this.KB));
	}	
}
