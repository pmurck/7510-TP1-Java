package ar.uba.fi.tdd.rulogic.model;

import java.util.List;

interface Queryable {
	
	public boolean query(String methodName, List<String> methodArgs);

}
