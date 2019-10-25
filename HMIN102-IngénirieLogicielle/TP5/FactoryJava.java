package HMIN102.TP5;

public class FactoryJava extends AbstractFactoryCompiler {

	@Override
	public Lexer createLexer() {
		return new LexerJava();
	}

	@Override
	public Parser createParser() {
		return new ParserJava();
	}

	@Override
	public Generator createGenerator() {
		return new GeneratorJava();
	}

}
