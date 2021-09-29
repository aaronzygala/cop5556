package edu.ufl.cise.plpfa21.assignment2;

import edu.ufl.cise.plpfa21.assignment1.IPLPLexer;
import edu.ufl.cise.plpfa21.assignment1.IPLPToken;
import edu.ufl.cise.plpfa21.assignment1.Lexer;
import edu.ufl.cise.plpfa21.assignment1.LexicalException;
import edu.ufl.cise.plpfa21.assignment1.PLPTokenKinds;
import edu.ufl.cise.plpfa21.assignment1.PLPTokenKinds.Kind;

public class Parser {
	
	IPLPToken t;
	IPLPLexer lexer;
	
	public Parser(Lexer l) {
		this.lexer = l;
		try {
			this.t = l.nextToken();
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
	private void match(PLPTokenKinds.Kind kind) {
		if(isKind(t, kind)) {
			consume();
			return;
		}
		else {
			//handle error
		}
	}
	
	public void factor() {
		if(isKind(t, Kind.INT_LITERAL)) {
			match(Kind.INT_LITERAL);
		}
		else if(isKind(t, Kind.LPAREN)) {
			match(Kind.LPAREN);
			expr();
			match(Kind.RPAREN);
		}
		else {
			//handle error
		}
	}
	public void term() {
		factor();
		while(isKind(t, Kind.TIMES) || isKind(t, Kind.DIV)) {
			consume();
			factor();
		}
	}
	public void expr() {
		term();
		while(isKind(t, Kind.PLUS) || isKind(t, Kind.MINUS)) {
			if(isKind(t, Kind.PLUS))
				match(Kind.PLUS);
			else if(isKind(t, Kind.MINUS))
				match(Kind.MINUS);
			term();
		}
	}
}
