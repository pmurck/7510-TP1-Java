package ar.uba.fi.tdd.rulogic.model;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FactTest {
	
	private final String name = "hijo"; 
	private final List<String> params = Arrays.asList("pepe", "juan");
	private final List<String> anotherParams = Arrays.asList("pepe", "marcelo");
	private Fact fact;
	private Fact anotherFact;

	@Before
	public void setUp() throws Exception {
		fact = new Fact(name, params);
		anotherFact = new Fact(name, anotherParams);
	}

	@Test
	public void testGetFactName() {
		Assert.assertEquals(this.name, this.fact.getName());
	}
	
	@Test
	public void testGetFactParams() {
		Assert.assertEquals(this.params, this.fact.getParams());
	}

	@Test
	public void testGetFactParamCount() {
		Assert.assertEquals(this.params.size(), this.fact.getParamCount());
	}
	
	@Test
	public void testCallFactUsingCorrectArgs() {
		Assert.assertTrue(this.fact.callUsing(params, null));
	}
	
	@Test
	public void testCallFactUsingIncorrectArgs() {
		Assert.assertFalse(this.fact.callUsing(Arrays.asList("za", "ra", "za"), null));
	}
	
	@Test
	public void testCallJoinedFactUsing1stCorrectArgs() {
		this.fact.join(this.anotherFact);
		Assert.assertTrue(this.fact.callUsing(params, null));
	}
	
	@Test
	public void testCallJoinedFactUsing2ndCorrectArgs() {
		this.fact.join(this.anotherFact);
		Assert.assertTrue(this.fact.callUsing(anotherParams, null));
	}
	
	@Test
	public void testCallJoinedFactUsingIncorrectArgs() {
		this.fact.join(this.anotherFact);
		Assert.assertFalse(this.fact.callUsing(Arrays.asList("juan", "marcelo"), null));
	}
}
