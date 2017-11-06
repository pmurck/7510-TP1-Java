package ar.uba.fi.tdd.rulogic.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CallTest {
	
	private final String name = "hijo";
	private final List<String> params = Arrays.asList("X", "Y");
	private final List<String> args = Arrays.asList("juan", "pepe");
	private final List<String> paramWithFixedArg = Arrays.asList("Z", "pepito");
	private final List<String> fixedArgs = Arrays.asList("test", "pepito");
	private Call call;
	private Call fixedCall;
	private Map<String, String> paramToArg;
	
	private static class TransformedArgsIdentity implements Queryable {
		
		private List<String> expectedArgs;

		public TransformedArgsIdentity(List<String> expectedArgs) {
			this.expectedArgs = expectedArgs;
		}
		
		@Override
		public boolean query(String methodName, List<String> methodArgs) {
			return expectedArgs.equals(methodArgs);
		}
		
	}

	@Before
	public void setUp() throws Exception {
		call = new Call(name, params);
		fixedCall = new Call(name, paramWithFixedArg);
	}

	@Test
	public void testCallEvalArgSetInOrder() {
		paramToArg = new HashMap<>();
		paramToArg.put(params.get(0), args.get(0));
		paramToArg.put(params.get(1), args.get(1));
		Assert.assertTrue(call.evaluate(paramToArg, new TransformedArgsIdentity(args)));
	}
	
	@Test
	public void testCallEvalArgSetInDisorder() {
		paramToArg = new HashMap<>();
		paramToArg.put(params.get(1), args.get(0));
		paramToArg.put(params.get(0), args.get(1));
		Assert.assertTrue(call.evaluate(paramToArg, new TransformedArgsIdentity(Arrays.asList(args.get(1), args.get(0)))));
	}
	
	@Test
	public void testCallEvalArgSetWithFixedArg() {
		paramToArg = new HashMap<>();
		paramToArg.put(paramWithFixedArg.get(0), fixedArgs.get(0));
		Assert.assertTrue(fixedCall.evaluate(paramToArg, new TransformedArgsIdentity(fixedArgs)));
	}
}
