package ar.uba.fi.tdd.rulogic.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.uba.fi.tdd.rulogic.exception.NonExistentMethodException;

public class KnowledgeBase implements Queryable {
	
	private Map<String, Map<Integer, Method>> methods; 
	
	public KnowledgeBase() {
		methods = new HashMap<>();
	}
	
	public void add(Method method) {
		
		Map<Integer, Method> methodsByParamCount = methods.getOrDefault(method.getName(), new HashMap<>());
		methodsByParamCount.merge(method.getParamCount(), method, (m1, m2) -> {m1.join(m2); return m1;});
		methods.putIfAbsent(method.getName(), methodsByParamCount);
	}
	
	@Override
	public boolean query(String methodName, List<String> methodArgs) {
		Map<Integer, Method> methodsByParamCount = methods.computeIfAbsent(methodName, name -> {throw new NonExistentMethodException(name);});
		Method method = methodsByParamCount.computeIfAbsent(methodArgs.size(), argCount -> {throw new NonExistentMethodException(methodName, argCount);});
		return method.callUsing(methodArgs, this);
	}

}
