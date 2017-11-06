package ar.uba.fi.tdd.rulogic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;

import ar.uba.fi.tdd.rulogic.exception.NonExistentMethodException;
import ar.uba.fi.tdd.rulogic.exception.RecursiveRuleCallException;
import ar.uba.fi.tdd.rulogic.exception.RuleJoinException;
import ar.uba.fi.tdd.rulogic.exception.WrongSyntaxException;
/**
 * Console application.
 *
 */
public class App {
	
	public static String answer(Interpreter interpreter, String query) {
		try {
            return interpreter.answer(query) ? "SI" : "NO";
        } catch (WrongSyntaxException|NonExistentMethodException|RecursiveRuleCallException ex) {
            return ex.getMessage();
        }
	}
	
	public static String workWrapper(String[] args) {
		if (args.length != 1) {
			return "You must Specify ONE database filename as argument!";
		}
		
		Interpreter interpreter;
		try {
			interpreter = new Interpreter(args[0]);
			System.out.println("Using DB from file: " + args[0]);
			
			try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
				System.out.println("Query your DB (or ':q' to quit): ");
				for(String q = br.readLine(); !q.equals(":q"); q = br.readLine()) {
					System.out.println(answer(interpreter, q));
				}
			}
			
		} catch (WrongSyntaxException|RuleJoinException ex) {
			return "Error on DB load -> " + ex.getMessage();
		} catch (IOException ex) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			return sw.toString();
		}
		
		return "";
    }
	
	public static void main(String[] args) {
		System.out.println(workWrapper(args));
	}
}
