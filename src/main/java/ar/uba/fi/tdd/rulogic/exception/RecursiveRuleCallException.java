package ar.uba.fi.tdd.rulogic.exception;

public class RecursiveRuleCallException extends RuntimeException {

	public RecursiveRuleCallException(String ruleName, Integer paramCount) {
		super("Recursive call found on Rule: " + ruleName + " with parameter count: " + paramCount);
	}

}
