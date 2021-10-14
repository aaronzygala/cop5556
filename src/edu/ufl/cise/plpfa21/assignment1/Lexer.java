package edu.ufl.cise.plpfa21.assignment1;

import java.util.ArrayList;
import edu.ufl.cise.plpfa21.assignment1.PLPTokenKinds.Kind;

public class Lexer implements IPLPLexer{
	
	String input;
	ArrayList<IPLPToken> tokenList;
	int tokenPos;
	private enum State {START, HAVE_EQUAL, HAVE_NEQUAL, DIGITS, IDENT_PART, COMMENT, STRING, BOOL_OP}
	
	public Lexer(String input){
		this.tokenPos = 0;
		this.input = input;
		this.tokenList = new ArrayList<>();
		try {
			this.tokenList = readInput(input);
		} catch (LexicalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//Kind kind, String text, int line, int pos, String stringValue, int intValue
	public static ArrayList<IPLPToken> readInput(String input) throws LexicalException{
		ArrayList<IPLPToken> tokenList = new ArrayList<>();
		StringBuilder str = new StringBuilder();
		int currentLine = 1;
		int currentPos = 0;
		int firstLineOfString = 0;
		Kind k;
		State state = State.START;
		char prevChar=0;
		char nextChar=0;
		
		int idx = 0;
		char[] chars = input.toCharArray();
		
		while(idx < chars.length) {
			char c = chars[idx];
			
			if(idx > 0)
				prevChar = input.charAt(idx-1);
			if(idx < chars.length-1)
				nextChar = input.charAt(idx+1);
			switch(state) {
				case START:
					switch(c) {
						case ' ', '\t':
							idx++; currentPos++;
							continue;
						
						case '\n', '\r':
							idx++;
							currentPos = 0;
							currentLine++;
							continue;

						case '0':
							tokenList.add(new Token(Kind.INT_LITERAL, "0", currentLine, currentPos, 0));
							idx++; currentPos++;
							continue;

						case '+', ',', ';', ':', '(', ')', '[', ']', '<', '>', '-':
							String text = String.valueOf(c);
							k = findKind(text, currentLine, currentPos);
							tokenList.add(new Token(k, text, currentLine, currentPos));
							idx++; currentPos++;
							continue;

						case '*':
							tokenList.add(new Token(Kind.TIMES, "*", currentLine, currentPos));
							idx++; currentPos++;
							continue;

						case '=':
							if(nextChar != '=') {
								tokenList.add(new Token(Kind.ASSIGN, "=", currentLine, currentPos));
							}
							else {
								state = State.HAVE_EQUAL;
							}
							idx++; currentPos++;
							continue;

						case '!':
							if(nextChar != '=') {
								tokenList.add(new Token(Kind.BANG, "!", currentLine, currentPos));
							}
							else {
								state = State.HAVE_NEQUAL;
							}
							idx++; currentPos++;
							continue;

						case '/':
							if(nextChar == '*') {
								state = State.COMMENT;
							}
							else {
								tokenList.add(new Token(Kind.DIV, "/", currentLine, currentPos));
							}
							idx++; currentPos++;
							continue;

						case '&', '|':
							str.append(c);
							state = State.BOOL_OP;
							idx++; currentPos++;
							continue;

						case '1', '2', '3', '4', '5', '6', '7', '8', '9':
							str.append(c);
							state = State.DIGITS;
							idx++; currentPos++;
							continue;

						case '\"', '\'':
							firstLineOfString = currentLine;
							str.append(c);
							state = State.STRING;
							idx++; currentPos++;
							continue;

							
						default:
							if(Character.isJavaIdentifierStart(c)) {
								idx++;
								str.append(c);
								state = State.IDENT_PART;
								currentPos++;
							}
							else {
								if(c != 0) {
									tokenList.add(new Token(Kind.ERROR, "", currentLine, currentPos));		
									idx++;currentPos++;
								}
							}
							continue;
					}
				case STRING:
					if(c != str.charAt(0)) {
						idx++;
						if(c == '\n' || c == '\r') {
							currentLine++;
							currentPos = 0;
						}
						str.append(c);
						currentPos++;
					}
					else {
						str.append(c);
						idx++;currentPos++;
						state = State.START;
						String text = str.toString();
						
						k = findKind(text, currentLine, currentPos);
						tokenList.add(new Token(k, text, firstLineOfString, currentPos-text.length(), text.replaceAll(String.valueOf(str.charAt(0)), "")));
						str.setLength(0);
					}
					continue;
					
				case BOOL_OP:
					if(c == prevChar) {
						str.append(c);
						String text = str.toString();
						k = findKind(text, currentLine, currentPos);
						tokenList.add(new Token(k, text, currentLine, currentPos-1));
						str.setLength(0);
						state = State.START;
						currentPos++;
					}
					else {
						tokenList.add(new Token(Kind.ERROR, "", currentLine, currentPos));	
					}
					idx++;
					continue;

				case COMMENT:
					if(c == '/' && prevChar == '*') {
						state = State.START;
					}
					idx++;
					currentPos++;
					continue;

				case HAVE_EQUAL:
					if(c == '=') {
						tokenList.add(new Token(Kind.EQUALS, "==", currentLine, currentPos-1));
						currentPos++;
					}
					else {
						tokenList.add(new Token(Kind.ERROR, "", currentLine, currentPos));	
					}
					idx++;
					state = State.START;
					continue;

				case HAVE_NEQUAL:
					if(c == '=') {
						tokenList.add(new Token(Kind.NOT_EQUALS, "!=", currentLine, currentPos-1));
						currentPos++;
					}
					else {
						tokenList.add(new Token(Kind.ERROR, "", currentLine, currentPos));	
					}
					idx++;
					state = State.START;
					continue;

				case DIGITS:
					if(Character.isDigit(c)) {
						idx++;
						currentPos++;
						str.append(c);
					}
					else {
						String text = str.toString();
						tokenList.add(new Token(Kind.INT_LITERAL, text, currentLine, currentPos-text.length()));
						str.setLength(0);
						state = State.START;
						
					}
					continue;

				case IDENT_PART:
					if(Character.isDigit(c) || Character.isLetter(c) || c == '_' || c == '$') {
						idx++;
						currentPos++;
						str.append(c);
					}
					else {
						String text = str.toString();
						k = findKind(text, currentLine, currentPos);
						tokenList.add(new Token(k, text, currentLine, currentPos-text.length()));
						str.setLength(0);
						state = State.START;
					}
					continue;

			
			}
		}
		return tokenList;
	}
	private static Kind findKind(String s, int line, int pos){
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
				//case "ELSE": rv = Kind.KW_ELSE; break;
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
			rv = Kind.INT_LITERAL;
		}
		else if(c == '\'' || c == '\"') {
			rv = Kind.STRING_LITERAL;
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
		else if(rv.getKind() == Kind.INT_LITERAL) {
			try{
				Integer.parseInt(rv.getText());
				tokenList.set(tokenPos-1, new Token(rv.getKind(), rv.getText(), rv.getLine(), rv.getCharPositionInLine(), Integer.parseInt(rv.getText())));
				rv = tokenList.get(tokenPos-1);
			}catch(NumberFormatException e) {
				throw new LexicalException("Int Error! Integer out of range!", rv.getLine(), rv.getCharPositionInLine());
			}
		}
		return rv;
	}
	
	public static void main(String[] args) throws LexicalException {
		String input = """
			    FUN func() DO
				SWITCH x
				CASE 0 : y=0;
				CASE 1 : y=1;
				CASE 2 : y=2;
				DEFAULT y=3;
				END  /*SWITCH*/
				END  /*FUN*/
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



	}
}
