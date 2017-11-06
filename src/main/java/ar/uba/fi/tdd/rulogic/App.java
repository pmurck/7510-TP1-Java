package ar.uba.fi.tdd.rulogic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import ar.uba.fi.tdd.rulogic.exception.NonExistentMethodException;
import ar.uba.fi.tdd.rulogic.exception.RecursiveRuleCallException;
import ar.uba.fi.tdd.rulogic.exception.RuleJoinException;
import ar.uba.fi.tdd.rulogic.exception.WrongSyntaxException;
/**
 * Console application.
 *
 */
public class App {
	
	public static void main(String[] args) {
		
		if (args.length != 1) {
			System.out.println("You must Specify ONE database filename as argument!");
			return;
		}
		
		Interpreter interpreter;
		try {
			interpreter = new Interpreter(args[0]);
			System.out.println("Using DB from file: " + args[0]);
			
			try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
				System.out.println("Query your DB (or ':q' to quit): ");
				for(String q = br.readLine(); !q.equals(":q"); q = br.readLine()) {
					try {
		                System.out.println(interpreter.answer(q) ? "SI" : "NO");
		            } catch (WrongSyntaxException|NonExistentMethodException|RecursiveRuleCallException ex) {
		                System.err.println(ex.getMessage());
		            }
				}
			}
			
		} catch (WrongSyntaxException|RuleJoinException ex) {
			System.err.println("Error on DB load -> " + ex.getMessage());
		} catch (IOException ex) {
			ex.printStackTrace();
		}		
    }
}
