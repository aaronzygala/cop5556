package edu.ufl.cise.plpfa21.assignment1;

public class CompilerComponentFactory {

	static IPLPLexer getLexer(String input) throws LexicalException {
		//TODO  create and return a Lexer instance to parse the given input.
		IPLPLexer lexer = new Lexer(input);
		return lexer;
	}
	

}
