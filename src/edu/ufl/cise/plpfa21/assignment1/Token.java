package edu.ufl.cise.plpfa21.assignment1;

public class Token implements IPLPToken{

	Kind k;
	String text;
	int line;
	int pos;
	String stringValue;
	int intValue;
	
	public Token(Kind kind, String text, int line, int pos, String stringValue, int intValue) {
		this.k = kind;
		this.text = text;
		this.line = line;
		this.pos = pos;
		this.stringValue = stringValue;
		this.intValue = intValue;
	}
	
	public Kind getKind() {
		return this.k;
	}

	public String getText() {
		return this.text;
	}

	public int getLine() {
		return this.line;
	}

	public int getCharPositionInLine() {
		return this.pos;
	}

	public String getStringValue() {
		if(this.k == Kind.STRING_LITERAL)
		{
			return this.stringValue;
		}
		return null;
	}

	public int getIntValue() {
		if(this.k == Kind.INT_LITERAL)
		{
			return this.intValue;
		}
		return -1;
	}

}
