package edu.ufl.cise.plpfa21.assignment1;

import java.util.ArrayList;

import edu.ufl.cise.plpfa21.assignment1.PLPTokenKinds.Kind;

public class Lexer implements IPLPLexer{
	
	String input;
	ArrayList<IPLPToken> tokenList;
	int tokenPos;
	
	public Lexer(String input) {
		this.tokenPos = 0;
		this.input = input;
		this.tokenList = readInput(input);
	}
	//Kind kind, String text, int line, int pos, String stringValue, int intValue
	public static ArrayList<IPLPToken> readInput(String input){
		ArrayList<IPLPToken> tokenList = new ArrayList<>();
		StringBuilder str = new StringBuilder();
		int currentLine = 1;
		int currentPos = 0;
		
		for(char c : input.toCharArray()) {
			switch(c) {
				case ' ', '\n', '\t', '\r':
					if(str.length() > 0) {
						Kind k = getKind(str.toString());
						tokenList.add(new Token(k, str.toString(), currentLine, currentPos, "", 0));
					}
					str.setLength(0);
					
					if(c == '\n' || c == '\r') {
						currentLine++;
						currentPos = 0;
					}
					currentPos++;
					continue;
				default:
					str.append(c);
					currentPos++;
			}
		}
		
		if(str.length() > 0) {
			Kind k = getKind(str.toString());
			tokenList.add(new Token(k, str.toString(), currentLine, currentPos, "", 0));
		}
		return tokenList;
	}
	
	private static Kind getKind(String s) {
		Character c = s.charAt(0);
		Kind rv = Kind.ERROR;
		if(Character.isLetter(c)) {
			switch(s) {
				case "FUN": rv = Kind.KW_FUN; break;
				case "DO":  rv = Kind.KW_DO; break;
				case "END": rv = Kind.KW_END; break;
				case "LET": rv = Kind.KW_LET; break;
				case "SWITCH": rv = Kind.KW_SWITCH; break;
				case "CASE": rv = Kind.KW_CASE; break;
				case "DEFAULT": rv = Kind.KW_DEFAULT; break;
				case "IF": rv = Kind.KW_IF; break;
				case "ELSE": rv = Kind.KW_ELSE; break;
				case "WHILE": rv = Kind.KW_WHILE; break;
				case "RETURN": rv = Kind.KW_RETURN; break;
				case "LIST": rv = Kind.KW_LIST; break;
				case "VAR": rv = Kind.KW_VAR; break;
				case "VAL": rv = Kind.KW_VAL; break;
				case "NIL": rv = Kind.KW_NIL; break;
				case "TRUE": rv = Kind.KW_TRUE; break;
				case "FALSE": rv = Kind.KW_FALSE; break;
				case "INT": rv = Kind.KW_INT; break;
				case "STRING": rv = Kind.KW_STRING; break;
				case "FLOAT": rv = Kind.KW_FLOAT; break;
				case "BOOLEAN": rv = Kind.KW_BOOLEAN; break;
				default: rv = Kind.IDENTIFIER;
			}
		}
		else if(Character.isDigit(c)) {
			
		}
		else {
			
		}
		return rv;
	}
	
	public IPLPToken nextToken() throws LexicalException {
		IPLPToken rv = new Token(Kind.EOF, "", 0, 0, "", 0);
		if(tokenPos < tokenList.size()) {
			rv = tokenList.get(tokenPos);
			this.tokenPos++;
		}
		return rv;
	}
	
	public static void main(String[] args) throws LexicalException {
		String input = """
				abc
				  def
				     ghi

				""";
		IPLPLexer lexer = new Lexer(input);
		
		IPLPToken t = lexer.nextToken();
		System.out.println(t.getCharPositionInLine());
;	}
}
