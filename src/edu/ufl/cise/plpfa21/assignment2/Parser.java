package edu.ufl.cise.plpfa21.assignment2;

import java.util.ArrayList;
import java.util.List;

import edu.ufl.cise.plpfa21.assignment1.IPLPLexer;
import edu.ufl.cise.plpfa21.assignment1.IPLPToken;
import edu.ufl.cise.plpfa21.assignment1.Lexer;
import edu.ufl.cise.plpfa21.assignment1.LexicalException;
import edu.ufl.cise.plpfa21.assignment1.PLPTokenKinds;
import edu.ufl.cise.plpfa21.assignment1.PLPTokenKinds.Kind;
import edu.ufl.cise.plpfa21.assignment3.ast.IASTNode;
import edu.ufl.cise.plpfa21.assignment3.ast.IBlock;
import edu.ufl.cise.plpfa21.assignment3.ast.IDeclaration;
import edu.ufl.cise.plpfa21.assignment3.ast.IExpression;
import edu.ufl.cise.plpfa21.assignment3.ast.IFunctionDeclaration;
import edu.ufl.cise.plpfa21.assignment3.ast.IIdentifier;
import edu.ufl.cise.plpfa21.assignment3.ast.INameDef;
import edu.ufl.cise.plpfa21.assignment3.ast.IStatement;
import edu.ufl.cise.plpfa21.assignment3.ast.IType;
import edu.ufl.cise.plpfa21.assignment3.ast.IType.TypeKind;
import edu.ufl.cise.plpfa21.assignment3.astimpl.AssignmentStatement__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.BinaryExpression__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.Block__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.BooleanLiteralExpression__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.FunctionDeclaration___;
import edu.ufl.cise.plpfa21.assignment3.astimpl.IdentExpression__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.Identifier__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.IfStatement__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.MutableGlobal__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.NameDef__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.NilConstantExpression__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.PrimitiveType__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.ImmutableGlobal__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.IntLiteralExpression__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.LetStatement__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.Program__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.ReturnStatement__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.StringLiteralExpression__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.SwitchStatement__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.UnaryExpression__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.WhileStatement__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.FunctionCallExpression__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.ListSelectorExpression__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.ListType__;

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
	public IASTNode parse() throws Exception {
		return program();
	}
	private IASTNode program() throws SyntaxException {
		List<IDeclaration> declarations = new ArrayList<>();
		
		boolean hasDeclaration = true;
		while (hasDeclaration) {
			IDeclaration d = declaration();
			if(d == null) {
				hasDeclaration = false;
			}
			else {
				declarations.add(d);
			}
		}
		return new Program__(0, 0, "", declarations);
	}
	private IDeclaration declaration() throws SyntaxException { //return Function, MutableGlobal, ImmutableGlobal
		IDeclaration d = null;//new Declaration__(t.getLine(), t.getCharPositionInLine(), t.getText());
		if(isKind(t, Kind.KW_FUN))
			d = function();
		else if(isKind(t, Kind.KW_VAR)) {
			INameDef varDef = null;
			IExpression expression = null;
			match(Kind.KW_VAR);
			varDef = nameDef();
			if(isKind(t, Kind.ASSIGN)) {
				match(Kind.ASSIGN);
				expression = expression();
			}
			match(Kind.SEMI);
			d = new MutableGlobal__(t.getLine(), t.getCharPositionInLine(), t.getText(), varDef, expression);
		}
		else if(isKind(t, Kind.KW_VAL)){
			INameDef varDef = null;
			IExpression expression = null;
			
			match(Kind.KW_VAL);
			varDef = nameDef();
			match(Kind.ASSIGN);
			expression = expression();
			match(Kind.SEMI);
			d = new ImmutableGlobal__(t.getLine(), t.getCharPositionInLine(), t.getText(), varDef, expression);
		}		
		return d;
	}
	
	private IFunctionDeclaration function() throws SyntaxException {
		IIdentifier name = null;
		IBlock block = null;
		List<INameDef> args = new ArrayList<>();
		IType type = null;
		
		match(Kind.KW_FUN);
		name = new Identifier__(t.getLine(), t.getCharPositionInLine(), t.getText(), t.getText());
		match(Kind.IDENTIFIER);
		match(Kind.LPAREN);
		if(!isKind(t, Kind.RPAREN)) {
			args.add(nameDef());
			while(!isKind(t, Kind.RPAREN)) {
				match(Kind.COMMA);
				args.add(nameDef());
			}
		}
		match(Kind.RPAREN);
		
		if(isKind(t, Kind.COLON)) {
			match(Kind.COLON);
			type = type();
		}
		match(Kind.KW_DO);
		block = block();
		match(Kind.KW_END);
		return new FunctionDeclaration___(t.getLine(), t.getCharPositionInLine(), t.getText(), name, args, type, block);
	}
	
	private IBlock block() throws SyntaxException {
		List<IStatement> statements = new ArrayList<>();
		while(!(isKind(t, Kind.KW_END) || isKind(t, Kind.KW_DEFAULT))) {
			statements.add(statement());
		}
		return new Block__(t.getLine(), t.getCharPositionInLine(), t.getText(), statements);
	}
	
	private INameDef nameDef() throws SyntaxException {
		IIdentifier name = null;
		IType type = null;
		name = new Identifier__(t.getLine(), t.getCharPositionInLine(), t.getText(), t.getText());
		match(Kind.IDENTIFIER);
		
		if(isKind(t, Kind.COLON)) {
			match(Kind.COLON);
			type = type();
		}
		return new NameDef__(t.getLine(), t.getCharPositionInLine(), t.getText(), name, type);
	}
	private IStatement statement() throws SyntaxException { //return LetStatement, SwitchStatement, IfStatement, WhileStatement, ReturnStatement, AssignmentStatement
		IStatement rv = null;
		switch(t.getKind()) {
		
			case KW_LET:
				INameDef localDef = null;
				IExpression expr = null;
				IBlock block = null;
				
				match(Kind.KW_LET);
				localDef = nameDef();
				if(isKind(t, Kind.ASSIGN)) {
					match(Kind.ASSIGN);
					expr = expression();
				}
				match(Kind.KW_DO);
				block = block();
				match(Kind.KW_END);
				rv = new LetStatement__(t.getLine(), t.getCharPositionInLine(), t.getText(), block, expr, localDef);
				break;

			case KW_SWITCH:
				IExpression switchExpr = null;
				List<IExpression> branchExprs = new ArrayList<>();
				List<IBlock> blocks = new ArrayList<>();
				IBlock defaultBlock = null;
				
				match(Kind.KW_SWITCH);
				switchExpr = expression();
				while(isKind(t, Kind.KW_CASE)) {
					match(Kind.KW_CASE);
					branchExprs.add(expression());
					match(Kind.COLON);
					blocks.add(block());
				}
				match(Kind.KW_DEFAULT);
				defaultBlock = block();
				match(Kind.KW_END);
				rv = new SwitchStatement__(t.getLine(), t.getCharPositionInLine(), t.getText(), switchExpr, branchExprs, blocks, defaultBlock);
				break;

			case KW_IF:
				IExpression guardExpr = null;
				IBlock ifBlock = null;
				match(Kind.KW_IF);
				guardExpr = expression();
				match(Kind.KW_DO);
				ifBlock = block();
				match(Kind.KW_END);
				rv = new IfStatement__(t.getLine(), t.getCharPositionInLine(), t.getText(), guardExpr, ifBlock);
				break;

			case KW_WHILE:
				IExpression guardExpression = null;
				IBlock whileBlock = null;
				
				match(Kind.KW_WHILE);
				guardExpression = expression();
				match(Kind.KW_DO);
				whileBlock = block();
				match(Kind.KW_END);
				rv = new WhileStatement__(t.getLine(), t.getCharPositionInLine(), t.getText(), guardExpression, whileBlock);

				break;

			case KW_RETURN:
				IExpression returnExpr = null;
				
				match(Kind.KW_RETURN);
				returnExpr = expression();
				match(Kind.SEMI);
				rv = new ReturnStatement__(t.getLine(), t.getCharPositionInLine(), t.getText(), returnExpr);
				break;

			default:
				IExpression leftExpr = null;
				IExpression rightExpr = null;
				
				leftExpr = expression();
				if(isKind(t, Kind.ASSIGN)) {
					match(Kind.ASSIGN);
					rightExpr = expression();
				}
				match(Kind.SEMI);
				rv = new AssignmentStatement__(t.getLine(), t.getCharPositionInLine(), t.getText(), leftExpr, rightExpr);
				break;
		}
		return rv;
	}
	private IExpression expression() throws SyntaxException {
		return logicalExpression();
	}
	private IExpression logicalExpression() throws SyntaxException {
		IExpression leftExpr = null;
		IExpression rightExpr = null;
		Kind op = null;
		
		leftExpr = comparisonExpression();
		while(isKind(t, Kind.AND) || isKind(t, Kind.OR)) {
			if(isKind(t,  Kind.AND)) {
				op = Kind.AND;
				match(Kind.AND);
			}
			else if(isKind(t, Kind.OR)) {
				op = Kind.OR;
				match(Kind.OR);
			}
			rightExpr = comparisonExpression();
		}
		return new BinaryExpression__(t.getLine(), t.getCharPositionInLine(), t.getText(), leftExpr, rightExpr, op);
	}
	private IExpression comparisonExpression() throws SyntaxException {
		IExpression leftExpr = null;
		IExpression rightExpr = null;
		Kind op = null;
		
		leftExpr = additiveExpression();
		while(isKind(t, Kind.LT) || isKind(t, Kind.GT) || isKind(t, Kind.EQUALS) || isKind(t, Kind.NOT_EQUALS)) {
			if(isKind(t,  Kind.LT)) {
				op = Kind.LT;
				match(Kind.LT);
			}
			else if(isKind(t, Kind.GT)) {
				op = Kind.GT;
				match(Kind.GT);
			}
			else if(isKind(t, Kind.EQUALS)) {
				op = Kind.EQUALS;
				match(Kind.EQUALS);
			}
			else if(isKind(t, Kind.NOT_EQUALS)) {
				op = Kind.NOT_EQUALS;
				match(Kind.NOT_EQUALS);
			}
			rightExpr = additiveExpression();
		}
		return new BinaryExpression__(t.getLine(), t.getCharPositionInLine(), t.getText(), leftExpr, rightExpr, op);
	}
	private IExpression additiveExpression() throws SyntaxException {
		IExpression leftExpr = null;
		IExpression rightExpr = null;
		Kind op = null;
		
		leftExpr = multiplicativeExpression();
		while(isKind(t, Kind.PLUS) || isKind(t, Kind.MINUS)) {
			if(isKind(t,  Kind.PLUS)) {
				op = Kind.PLUS;
				match(Kind.PLUS);
			}
			else if(isKind(t, Kind.MINUS)) {
				op = Kind.MINUS;
				match(Kind.MINUS);
			}
			rightExpr = multiplicativeExpression();
		}
		return new BinaryExpression__(t.getLine(), t.getCharPositionInLine(), t.getText(), leftExpr, rightExpr, op);
	}
	private IExpression multiplicativeExpression() throws SyntaxException {
		IExpression leftExpr = null;
		IExpression rightExpr = null;
		Kind op = null;
		
		leftExpr = unaryExpression();
		while(isKind(t, Kind.TIMES) || isKind(t, Kind.DIV)) {
			if(isKind(t,  Kind.TIMES)) {
				op = Kind.TIMES;
				match(Kind.TIMES);
			}
			else if(isKind(t, Kind.DIV)) {
				op = Kind.DIV;
				match(Kind.DIV);
			}
			rightExpr = unaryExpression();
		}
		return new BinaryExpression__(t.getLine(), t.getCharPositionInLine(), t.getText(), leftExpr, rightExpr, op);
	}
	private IExpression unaryExpression() throws SyntaxException {
		IExpression expr = null;
		Kind op = null;
		
		if(isKind(t,  Kind.BANG)) {
			op = Kind.BANG;
			match(Kind.BANG);
		}
		else if(isKind(t, Kind.MINUS)) {
			op = Kind.MINUS;
			match(Kind.MINUS);
		}
		expr = primaryExpression();
		return new UnaryExpression__(t.getLine(), t.getCharPositionInLine(), t.getText(), expr, op);
	}
	private IExpression primaryExpression() throws SyntaxException {
		switch(t.getKind()) {
		
			case KW_NIL:
				match(Kind.KW_NIL);
				return new NilConstantExpression__(t.getLine(), t.getCharPositionInLine(), t.getText());
				
			case KW_TRUE:
				match(Kind.KW_TRUE);
				return new BooleanLiteralExpression__(t.getLine(), t.getCharPositionInLine(), t.getText(), true);

			case KW_FALSE:
				match(Kind.KW_FALSE);
				return new BooleanLiteralExpression__(t.getLine(), t.getCharPositionInLine(), t.getText(), false);

			case INT_LITERAL:
				int val = t.getIntValue();
				match(Kind.INT_LITERAL);
				return new IntLiteralExpression__(t.getLine(), t.getCharPositionInLine(), t.getText(), val);
				
			case STRING_LITERAL:
				String s = t.getStringValue();
				match(Kind.STRING_LITERAL);
				return new StringLiteralExpression__(t.getLine(), t.getCharPositionInLine(), t.getText(), s);
				
			case LPAREN:
				IExpression expr = null;
				match(Kind.LPAREN);
				expr = expression();
				match(Kind.RPAREN);
				return expr;

			case IDENTIFIER:
				IIdentifier name = new Identifier__(t.getLine(), t.getCharPositionInLine(), t.getText(), t.getText());
				match(Kind.IDENTIFIER);
				
				if(isKind(t, Kind.LPAREN)) {
					List<IExpression> args = new ArrayList<>();
					match(Kind.LPAREN);
					if(!isKind(t, Kind.RPAREN)) {
						args.add(expression());
						while(!isKind(t, Kind.RPAREN)) {
							match(Kind.COMMA);
							args.add(expression());
						}
					}
					match(Kind.RPAREN);
					return new FunctionCallExpression__(t.getLine(), t.getCharPositionInLine(), t.getText(), name, args);
				}
				else if(isKind(t, Kind.LSQUARE)) {
					IExpression index = null;
					match(Kind.LSQUARE);
					index = expression();
					match(Kind.RSQUARE);
					return new ListSelectorExpression__(t.getLine(), t.getCharPositionInLine(), t.getText(), name, index);
				}
				else {
					return new IdentExpression__(t.getLine(), t.getCharPositionInLine(), t.getText(), name);
				}

			default:
				return null;
		}
	}
	private IType type() throws SyntaxException {
		switch(t.getKind()) {
		
			case KW_INT:
				match(Kind.KW_INT);
				return new PrimitiveType__(t.getLine(), t.getCharPositionInLine(), t.getText(), TypeKind.INT);
				
			case KW_STRING:
				match(Kind.KW_STRING);
				return new PrimitiveType__(t.getLine(), t.getCharPositionInLine(), t.getText(), TypeKind.STRING);

			case KW_BOOLEAN:
				match(Kind.KW_BOOLEAN);
				return new PrimitiveType__(t.getLine(), t.getCharPositionInLine(), t.getText(), TypeKind.BOOLEAN);

			case KW_LIST:
				IType type = null;
				match(Kind.KW_LIST);
				match(Kind.LSQUARE);
				if(!isKind(t, Kind.RSQUARE)) {
					type = type();
				}
				match(Kind.RSQUARE);
				return new ListType__(t.getLine(), t.getCharPositionInLine(), t.getText(), type);

			default:
				break;
		}
		return null;
	}
	
	public static void main(String[] args) throws LexicalException {
		String input = """
				VAL a: INT = 0;
				""";
		IPLPParser parser =  new Parser(new Lexer(input));

		try {
			parser.parse();
		} catch (Throwable e) {
			throw new RuntimeException(e); 
		}
		
	}
}
