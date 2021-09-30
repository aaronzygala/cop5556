package edu.ufl.cise.plpfa21.assignment2;

import edu.ufl.cise.plpfa21.assignment1.IPLPLexer;
import edu.ufl.cise.plpfa21.assignment1.IPLPToken;
import edu.ufl.cise.plpfa21.assignment1.Lexer;
import edu.ufl.cise.plpfa21.assignment1.LexicalException;
import edu.ufl.cise.plpfa21.assignment1.PLPTokenKinds;
import edu.ufl.cise.plpfa21.assignment1.PLPTokenKinds.Kind;

public class Parser implements IPLPParser{
	
	IPLPToken t;
	IPLPLexer lexer;
	
	public Parser(IPLPLexer iplpLexer) {
		this.lexer = iplpLexer;
		try {
			this.t = iplpLexer.nextToken();
		} catch (LexicalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private boolean isKind(IPLPToken token, PLPTokenKinds.Kind Kind) {
		return token.getKind() == Kind;
	}
	private IPLPToken consume() {
		try {
			this.t = lexer.nextToken();
		} catch (LexicalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}
	private void match(PLPTokenKinds.Kind kind) throws SyntaxException {
		if(isKind(t, kind)) {
			consume();
			return;
		}
		else {
			throw new SyntaxException("Syntax Error at line: " + t.getLine() + " character: " + t.getCharPositionInLine(), t.getLine(), t.getCharPositionInLine());
		}
	}
	@Override
	public void parse() throws Exception {
		program();
		
	}
	private void program() throws SyntaxException {
		boolean next = true;
		while (next) {
			next = declaration();
		}
	}
	private boolean declaration() throws SyntaxException {
		boolean rv = true;
		if(isKind(t, Kind.KW_FUN))
			function();
		else if(isKind(t, Kind.KW_VAR)) {
			match(Kind.KW_VAR);
			nameDef();
			if(isKind(t, Kind.ASSIGN)) {
				match(Kind.ASSIGN);
				expression();
			}
			match(Kind.SEMI);
		}
		else if(isKind(t, Kind.KW_VAL)){
			match(Kind.KW_VAL);
			nameDef();
			match(Kind.ASSIGN);
			expression();
			match(Kind.SEMI);
			
		}
		else
			rv = false;
		
		return rv;
	}
	
	private void function() throws SyntaxException {
		match(Kind.KW_FUN);
		match(Kind.IDENTIFIER);
		match(Kind.LPAREN);
		if(!isKind(t, Kind.RPAREN)) {
			nameDef();
			while(!isKind(t, Kind.RPAREN)) {
				match(Kind.COMMA);
				nameDef();
			}
		}
		match(Kind.RPAREN);
		
		if(isKind(t, Kind.COLON)) {
			type();
		}
		match(Kind.KW_DO);
		block();
		match(Kind.KW_END);
	}
	
	private void block() throws SyntaxException {
		while(true)
			statement();
	}
	private void nameDef() throws SyntaxException {
		match(Kind.IDENTIFIER);
		if(isKind(t, Kind.COLON)) {
			match(Kind.COLON);
			type();
		}
	}
	private void statement() throws SyntaxException {
		switch(t.getKind()) {
		
			case KW_LET:
				match(Kind.KW_LET);
				nameDef();
				if(isKind(t, Kind.ASSIGN)) {
					expression();
				}
				match(Kind.SEMI);
				break;

			case KW_SWITCH:
				match(Kind.KW_SWITCH);
				expression();
				while(isKind(t, Kind.KW_CASE)) {
					match(Kind.KW_CASE);
					expression();
					match(Kind.COLON);
					block();
				}
				match(Kind.KW_DEFAULT);
				block();
				match(Kind.KW_END);
				break;

			case KW_IF:
				match(Kind.KW_IF);
				expression();
				match(Kind.KW_DO);
				block();
				match(Kind.KW_END);
				break;

			case KW_WHILE:
				match(Kind.KW_WHILE);
				expression();
				match(Kind.KW_DO);
				block();
				match(Kind.KW_END);
				break;

			case KW_RETURN:
				match(Kind.KW_RETURN);
				expression();
				match(Kind.SEMI);
				break;

			default:
				expression();
				if(isKind(t, Kind.ASSIGN)) {
					match(Kind.ASSIGN);
					expression();
				}
				match(Kind.SEMI);
				break;
		}
	}
	private void expression() throws SyntaxException {
		logicalExpression();
	}
	private void logicalExpression() throws SyntaxException {
		comparisonExpression();
		while(isKind(t, Kind.AND) || isKind(t, Kind.OR)) {
			if(isKind(t,  Kind.AND)) {
				match(Kind.AND);
			}
			else if(isKind(t, Kind.OR)) {
				match(Kind.OR);
			}
			comparisonExpression();
		}
	}
	private void comparisonExpression() throws SyntaxException {
		additiveExpression();
		while(isKind(t, Kind.LT) || isKind(t, Kind.GT) || isKind(t, Kind.EQUALS) || isKind(t, Kind.NOT_EQUALS)) {
			if(isKind(t,  Kind.LT)) {
				match(Kind.LT);
			}
			else if(isKind(t, Kind.GT)) {
				match(Kind.GT);
			}
			else if(isKind(t, Kind.EQUALS)) {
				match(Kind.EQUALS);
			}
			else if(isKind(t, Kind.NOT_EQUALS)) {
				match(Kind.NOT_EQUALS);
			}
			additiveExpression();
		}
	}
	private void additiveExpression() throws SyntaxException {
		multiplicativeExpression();
		while(isKind(t, Kind.PLUS) || isKind(t, Kind.MINUS)) {
			if(isKind(t,  Kind.PLUS)) {
				match(Kind.PLUS);
			}
			else if(isKind(t, Kind.MINUS)) {
				match(Kind.MINUS);
			}
			multiplicativeExpression();
		}
	}
	private void multiplicativeExpression() throws SyntaxException {
		unaryExpression();
		while(isKind(t, Kind.TIMES) || isKind(t, Kind.DIV)) {
			if(isKind(t,  Kind.TIMES)) {
				match(Kind.TIMES);
			}
			else if(isKind(t, Kind.DIV)) {
				match(Kind.DIV);
			}
			unaryExpression();
		}
	}
	private void unaryExpression() throws SyntaxException {
		if(isKind(t,  Kind.BANG))
			match(Kind.BANG);
		else if(isKind(t, Kind.MINUS))
			match(Kind.MINUS);
		primaryExpression();
	}
	private void primaryExpression() throws SyntaxException {
		switch(t.getKind()) {
		
			case KW_NIL:
				match(Kind.KW_NIL);
				break;

			case KW_TRUE:
				match(Kind.KW_TRUE);
				break;

			case KW_FALSE:
				match(Kind.KW_FALSE);
				break;

			case INT_LITERAL:
				match(Kind.INT_LITERAL);
				break;
				
			case STRING_LITERAL:
				match(Kind.STRING_LITERAL);
				break;
				
			case LPAREN:
				match(Kind.LPAREN);
				expression();
				match(Kind.RPAREN);
				break;

			case IDENTIFIER:
				match(Kind.IDENTIFIER);
				
				if(isKind(t, Kind.LPAREN)) {
					match(Kind.LPAREN);
					if(!isKind(t, Kind.RPAREN)) {
						expression();
						while(!isKind(t, Kind.RPAREN)) {
							match(Kind.COMMA);
							expression();
						}
					}
					match(Kind.RPAREN);
				}
				else if(isKind(t, Kind.LSQUARE)) {
					match(Kind.LSQUARE);
					expression();
					match(Kind.RSQUARE);
				}
				break;

			default:
				break;
		}
	}
	private void type() throws SyntaxException {
		switch(t.getKind()) {
		
			case KW_INT:
				match(Kind.KW_INT);
				break;
				
			case KW_STRING:
				match(Kind.KW_STRING);
				break;

			case KW_BOOLEAN:
				match(Kind.KW_BOOLEAN);
				break;

			case KW_LIST:
				match(Kind.KW_LIST);
				match(Kind.LSQUARE);
				if(!isKind(t, Kind.RSQUARE)) {
					type();
				}
				match(Kind.RSQUARE);
				break;

			default:
				break;
		}
	}
	
	public static void main(String[] args) throws LexicalException {
		String input = """
				VAL a: STRING = "hello";
				""";
		IPLPParser parser =  new Parser(new Lexer(input));

		try {
			parser.parse();
		} catch (Throwable e) {
			throw new RuntimeException(e); 
		}
		
	}
}
