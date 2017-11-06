package ar.uba.fi.tdd.rulogic.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Call {
	
	final String methodName;
	final List<String> methodParams;

	public Call(String methodName, List<String> methodParams) {
		this.methodName = methodName;
		this.methodParams = methodParams;
	}
	
	public boolean evaluate(Map<String, String> paramToArg, Queryable queryable) {
		List<String> args = this.methodParams.stream().map(p -> paramToArg.getOrDefault(p, p)).collect(Collectors.toList());
		return queryable.query(this.methodName, args);
	}

}
