package edu.ufl.cise.plpfa21.assignment1;

import edu.ufl.cise.plpfa21.assignment2.IPLPParser;
import edu.ufl.cise.plpfa21.assignment2.Parser;

public class CompilerComponentFactory {

	public static IPLPLexer getLexer(String input){
		//TODO  create and return a Lexer instance to parse the given input.
		IPLPLexer lexer =  new Lexer(input);
		return lexer;
	}
	
	public static IPLPParser getParser(String input){
		//TODO  create and return a Parser instance to parse the given input.
		 //Implement this in Assignment 2
	   	 //Your parser will create a lexer.
		IPLPParser parser =  new Parser(getLexer(input));
		return parser;
	}
	

}
