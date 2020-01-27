package HMIN102.TP5;

public class FactoryPython extends AbstractFactoryCompiler {

	@Override
	public Lexer createLexer() {
		return new LexerPython();
	}

	@Override
	public Parser createParser() {
		return new ParserPython();
	}

	@Override
	public Generator createGenerator() {
		return new GeneratorPython();
	}

}
