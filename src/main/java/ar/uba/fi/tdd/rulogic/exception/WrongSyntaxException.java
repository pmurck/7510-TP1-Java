package ar.uba.fi.tdd.rulogic.exception;

public class WrongSyntaxException extends RuntimeException {

	public WrongSyntaxException(String text) {
		super("Wrong Syntax on line content: " + text);
	}

}
