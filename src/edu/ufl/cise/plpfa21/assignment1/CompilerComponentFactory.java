package edu.ufl.cise.plpfa21.assignment1;

import edu.ufl.cise.plpfa21.assignment2.IPLPParser;
import edu.ufl.cise.plpfa21.assignment2.Parser;
import edu.ufl.cise.plpfa21.assignment3.ast.ASTVisitor;
import edu.ufl.cise.plpfa21.assignment4.TypeCheckVisitor;
import edu.ufl.cise.plpfa21.assignment5.StarterCodeGenVisitor;


public class CompilerComponentFactory {

	public static IPLPLexer getLexer(String input) {
		//Replace with whatever is needed for your lexer.
		IPLPLexer lexer = new Lexer(input);
		return lexer;
	}
	
	public static IPLPParser getParser(String input) {
		//Replace this with whatever is needed for your parser.
		IPLPParser parser = new Parser(getLexer(input));
		return parser;		
	}

	public static ASTVisitor getTypeCheckVisitor() {
		// Replace this with whatever is needed for your compiler
		return new TypeCheckVisitor();
	}
	
	public static ASTVisitor getCodeGenVisitor(String className, String packageName, String sourceFileName) {
		//Replace this with whatever is needed for your compiler
		return new StarterCodeGenVisitor(className,packageName, sourceFileName);
	}

}
