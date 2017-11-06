package ar.uba.fi.tdd.rulogic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import ar.uba.fi.tdd.rulogic.model.KnowledgeBase;

public class Interpreter {

	private static final String LINE_END = ".";
	private KnowledgeBase KB;
	
	public Interpreter(String dbFileName) throws IOException {
		this.KB = new KnowledgeBase();
		try (Stream<String> stream = Files.lines(Paths.get(dbFileName))) {
			stream.map(s -> s.replace(LINE_END, "")).forEach(s -> KB.add(Parser.parse(s)));
		}
	}
	
	public boolean answer(String query) {
		Parser.MethodBean mb = new Parser.MethodBean(query);
		return KB.query(mb.getName(), mb.getParams());
	}
}
