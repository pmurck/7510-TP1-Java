package ar.uba.fi.tdd.rulogic.model;

import java.util.List;

public class Fact extends Method {

	public Fact(String name, List<String> params) {
		super(name, params);
	}

	@Override
	public void join(Method anotherMethod) {
		return;
	}

	@Override
	public boolean callUsing(List<String> arguments, Queryable queryable) {
		return false;
	}
}
