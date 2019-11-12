package HMIN102.TP5;

public class Compiler {
	private Lexer lexer;
	private Parser parser;
	private Generator generator;
	
	public Compiler (String lang){
		AbstractFactoryCompiler factory = AbstractFactoryCompiler.get(lang);
		lexer = factory.createLexer();
		parser = factory.createParser();
		generator = factory.createGenerator();
	}
}
