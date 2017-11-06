package ar.uba.fi.tdd.rulogic.model;

import java.util.List;

public abstract class Method {

	private final String name;
	private final List<String> params;
	
	public Method(String name, List<String> params) {
		this.name = name;
		this.params = params;
	}
	
	public String getName() {
		return name;
	}
	
	public List<String> getParams(){
		return params;
	}
	
	public int getParamCount() {
		return params.size();
	}
	
	public abstract void join(Method anotherMethod);
	
	public abstract boolean callUsing(List<String> arguments, Queryable queryable);
}
