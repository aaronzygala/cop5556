package edu.ufl.cise.plpfa21.assignment1;

import java.util.ArrayList;

import edu.ufl.cise.plpfa21.assignment1.PLPTokenKinds.Kind;

public class Lexer implements IPLPLexer{
	
	String input;
	ArrayList<IPLPToken> tokenList;
	int tokenPos;
	
	public Lexer(String input) throws LexicalException {
		this.tokenPos = 0;
		this.input = input;
		this.tokenList = readInput(input);
	}
	//Kind kind, String text, int line, int pos, String stringValue, int intValue
	public static ArrayList<IPLPToken> readInput(String input) throws LexicalException{
		ArrayList<IPLPToken> tokenList = new ArrayList<>();
		StringBuilder str = new StringBuilder();
		int currentLine = 1;
		int currentPos = 0;
		Kind k;
		String text;
		
		for(char c : input.toCharArray()) {
			switch(c) {
				case ' ', '\n', '\t', '\r':
					if(str.length() > 0) {
						text = str.toString();
						
						k = findKind(text, currentLine, currentPos);
						tokenList.add(new Token(k, text, currentLine, currentPos-text.length()));
						str.setLength(0);
					}
					
					if(c == '\n' || c == '\r') {
						currentLine++;
						currentPos = 0;
					}
					else {
						currentPos++;
					}
					continue;
					
				case ',', ':', ';', '(', ')', '[', ']', '+', '-', '<', '>':
					if(str.length() > 0) {
						text = str.toString();
						
						k = findKind(text, currentLine, currentPos);
						tokenList.add(new Token(k, text, currentLine, currentPos-text.length()));
					}
					text = Character.toString(c);
					k = findKind(text, currentLine, currentPos);
					tokenList.add(new Token(k, text, currentLine, currentPos));
					str.setLength(0);
					currentPos++;
					continue;
				case '!':
					if(str.length() > 0) {
						text = str.toString();
						
						k = findKind(text, currentLine, currentPos);
						tokenList.add(new Token(k, text, currentLine, currentPos-text.length()));
						str.setLength(0);
					}
					str.append(c);
					currentPos++;
					continue;
				default:
					text = str.toString();
					if(str.length() > 0 && Character.isDigit(str.charAt(0)) && !Character.isDigit(c)) {
						
						k = findKind(text, currentLine, currentPos);
						tokenList.add(new Token(k, text, currentLine, currentPos-text.length(), Integer.parseInt(text)));
						str.setLength(0);
					}
					else if(text.equals("!=") || text.equals("||") || text.equals("&&") || text.equals("==")) {						
						k = findKind(text, currentLine, currentPos);
						tokenList.add(new Token(k, text, currentLine, currentPos-text.length()));
						str.setLength(0);
					}
					str.append(c);
					currentPos++;
			}
		}
		
		if(str.length() > 0) {
			text = str.toString();
			
			k = findKind(text, currentLine, currentPos);
			tokenList.add(new Token(k, text, currentLine, currentPos-text.length()));
		}
		return tokenList;
	}
	private static Kind findKind(String s, int line, int pos) throws LexicalException {
		Character c = s.charAt(0);
		Kind rv = Kind.ERROR;
		if(Character.isLetter(c) || c == '$' || c == '_') {
			switch(s) { //keywords and identifiers
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
		else if(Character.isDigit(c)) { //int literals
			try {
				rv = Kind.INT_LITERAL;
			}
			catch(NumberFormatException e) {
				throw new LexicalException("ERROR! Number Format Exception!", line, pos);
			}
		}
		else { //symbols
			switch(s) {
			
			case"(": rv = Kind.LPAREN; break;
			case":": rv = Kind.COLON; break;
			case",": rv = Kind.COMMA; break;
			case ")": rv = Kind.RPAREN; break;
			case "=": rv = Kind.ASSIGN; break;
			case ";": rv = Kind.SEMI; break;
			case "&&": rv = Kind.AND; break;
			case "||": rv = Kind.OR; break;
			case "<": rv = Kind.LT; break;
			case ">": rv = Kind.GT; break;
			case "==": rv = Kind.EQUALS; break;
			case "!=": rv = Kind.NOT_EQUALS; break;
			case "+": rv = Kind.PLUS; break;
			case "-": rv = Kind.MINUS; break;
			case "*": rv = Kind.TIMES; break;
			case "/": rv = Kind.DIV; break;
			case "!": rv = Kind.BANG; break;
			case "[": rv = Kind.LSQUARE; break;
			case "]": rv = Kind.RSQUARE; break;
			}
		}
		return rv;
	}
	
	public IPLPToken nextToken() throws LexicalException {
		IPLPToken rv = new Token(Kind.EOF, "", 0, 0);
		if(tokenPos < tokenList.size()) {
			rv = tokenList.get(tokenPos);
			this.tokenPos++;
		}
		
		if(rv.getKind() == Kind.ERROR) {
			throw new LexicalException("Parsing Error! Illegal Character Detected", rv.getLine(), rv.getCharPositionInLine());
		}
		return rv;
	}
	
	public static void main(String[] args) throws LexicalException {
		String input = """
			    !=&&||
							""";
		IPLPLexer lexer = new Lexer(input);
		
		IPLPToken t = lexer.nextToken();
		System.out.println(t.getKind());
		t = lexer.nextToken();
		System.out.println(t.getKind());
		t = lexer.nextToken();
		System.out.println(t.getKind());
		t = lexer.nextToken();
		System.out.println(t.getKind());
		t = lexer.nextToken();
		System.out.println(t.getKind());
;	}
}
