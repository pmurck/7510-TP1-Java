package ar.uba.fi.tdd.rulogic.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Fact extends Method {
	
	private Set<List<String>> args;

	public Fact(String name, List<String> params) {
		super(name, params);
		this.args = new HashSet<>();
		this.args.add(params);
	}

	@Override
	public void join(Method anotherMethod) {
		this.args.add(anotherMethod.getParams());
	}

	@Override
	public boolean callUsing(List<String> arguments, Queryable queryable) {
		return this.args.contains(arguments);
	}
}
