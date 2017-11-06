package ar.uba.fi.tdd.rulogic.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ar.uba.fi.tdd.rulogic.exception.RecursiveRuleCallException;
import ar.uba.fi.tdd.rulogic.exception.RuleJoinException;

public class Rule extends Method {
	
	private List<Call> methodCalls;

	public Rule(String name, List<String> params, List<Call> methodCalls) {
		super(name, params);
		this.methodCalls = methodCalls;
	}

	@Override
	public void join(Method anotherMethod) {
		throw new RuleJoinException();
	}

	@Override
	public boolean callUsing(List<String> arguments, Queryable queryable) {
		if (this.methodCalls.stream().anyMatch(c -> c.methodName.equals(this.getName()) && c.methodParams.size() == this.getParamCount())) {
	        throw new RecursiveRuleCallException(this.getName(), this.getParamCount());
	    }
		Map<String, String> paramToArg = IntStream.range(0, this.getParamCount()).boxed().collect(Collectors.toMap(this.getParams()::get, arguments::get));
		return this.methodCalls.stream().noneMatch(c -> !c.evaluate(paramToArg, queryable));
	}

}
