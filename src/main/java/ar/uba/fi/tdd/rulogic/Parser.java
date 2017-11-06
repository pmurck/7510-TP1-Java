package ar.uba.fi.tdd.rulogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import ar.uba.fi.tdd.rulogic.exception.WrongSyntaxException;
import ar.uba.fi.tdd.rulogic.model.Call;
import ar.uba.fi.tdd.rulogic.model.Fact;
import ar.uba.fi.tdd.rulogic.model.Method;
import ar.uba.fi.tdd.rulogic.model.Rule;

public class Parser {
	
	private static String RULE_REGEX = "(.*):-(.*)";
	private static String METHOD_REGEX = "(\\s*\\w+\\s*)\\((\\s*[^,\\s()]*\\s*(?:,\\s*[^,\\s()]+\\s*)*)\\)\\s*";
	private static String ARG_DELIMITER = ",";
	private static String CALL_DELIMITER_REGEX = "\\)\\s*,";
	private static String CALL_DELIMITER_REPLACEMENT = ");";
	private static String CALL_DELIMITER = ";";

	// pasar nombre text a input:
	private static List<String> splitTrimAndFilterBlank(String text, String splitRegExp){
		List<String> result = Arrays.asList(text.split(splitRegExp));
		return result.stream().map(s -> s.trim()).filter((s) -> !s.isEmpty()).collect(Collectors.toList());
	}

	private static List<String> applyRegExpOrThrow(String text, String regExp){
		Pattern pattern = Pattern.compile(regExp);
		Matcher matcher = pattern.matcher(text);
		if (matcher.matches()){
			List<String> result = new ArrayList<>(matcher.groupCount());
			for(int i = 1; i <= matcher.groupCount(); ++i) {
				result.add(matcher.group(i));
			}
			return result;
		} else {
			throw new WrongSyntaxException(text);
		}
	}
	
	public static class MethodBean {
		private List<String> mtd;
		
		public MethodBean(String line) {
			this.mtd = Parser.applyRegExpOrThrow(line, METHOD_REGEX);
		}
		
		public String getName() {
			return this.mtd.get(0).trim();
		}
		
		public List<String> getParams() {
			return Parser.splitTrimAndFilterBlank(this.mtd.get(1), ARG_DELIMITER);
		}
	}

	public static Fact parseFact(String line){
		MethodBean mb = new MethodBean(line);
		return new Fact(mb.getName(), mb.getParams());
	}

	public static Rule parseRule(String line){
		List<String> result = Parser.applyRegExpOrThrow(line, RULE_REGEX);
		String rawMethod = result.get(0);
		String rawBody = result.get(1);
		
		MethodBean mb = new MethodBean(rawMethod);
		
		List<Call> calls = new ArrayList<>();
		for (String rawCall : Parser.splitTrimAndFilterBlank(rawBody.replaceAll(CALL_DELIMITER_REGEX, CALL_DELIMITER_REPLACEMENT), CALL_DELIMITER)) {
			MethodBean call = new MethodBean(rawCall);
			calls.add(new Call(call.getName(), call.getParams()));
		}
		
		return new Rule(mb.getName(), mb.getParams(), calls);
	}

	public static Method parse(String line){
		if (Pattern.matches(RULE_REGEX, line))
			return parseRule(line);
		return parseFact(line);
	}
}
