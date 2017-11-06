package ar.uba.fi.tdd.rulogic.exception;

public class NonExistentMethodException extends RuntimeException {

	public NonExistentMethodException(String methodName) {
		super("Cannot find Method with name: " + methodName);
	}

	public NonExistentMethodException(String methodName, Integer argCount) {
		super("Cannot find Method " + methodName + " with argument count of " + argCount);
	}
}
