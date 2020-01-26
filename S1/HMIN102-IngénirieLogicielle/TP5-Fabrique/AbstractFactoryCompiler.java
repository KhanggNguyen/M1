package HMIN102.TP5;

public abstract class AbstractFactoryCompiler {
	public static AbstractFactoryCompiler get(String lang){
		if(lang.equals("Java")) return new FactoryJava();
		else{
			return new FactoryPython();
		}
	}
	
	public abstract Lexer createLexer();
	public abstract Parser createParser();
	public abstract Generator createGenerator();
}
