package edu.ufl.cise.plpfa21.assignment5;


import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import edu.ufl.cise.plpfa21.assignment1.PLPTokenKinds.Kind;
import edu.ufl.cise.plpfa21.assignment3.ast.ASTVisitor;
import edu.ufl.cise.plpfa21.assignment3.ast.IAssignmentStatement;
import edu.ufl.cise.plpfa21.assignment3.ast.IBinaryExpression;
import edu.ufl.cise.plpfa21.assignment3.ast.IBlock;
import edu.ufl.cise.plpfa21.assignment3.ast.IBooleanLiteralExpression;
import edu.ufl.cise.plpfa21.assignment3.ast.IDeclaration;
import edu.ufl.cise.plpfa21.assignment3.ast.IExpression;
import edu.ufl.cise.plpfa21.assignment3.ast.IExpressionStatement;
import edu.ufl.cise.plpfa21.assignment3.ast.IFunctionCallExpression;
import edu.ufl.cise.plpfa21.assignment3.ast.IFunctionDeclaration;
import edu.ufl.cise.plpfa21.assignment3.ast.IIdentExpression;
import edu.ufl.cise.plpfa21.assignment3.ast.IIdentifier;
import edu.ufl.cise.plpfa21.assignment3.ast.IIfStatement;
import edu.ufl.cise.plpfa21.assignment3.ast.IImmutableGlobal;
import edu.ufl.cise.plpfa21.assignment3.ast.IIntLiteralExpression;
import edu.ufl.cise.plpfa21.assignment3.ast.ILetStatement;
import edu.ufl.cise.plpfa21.assignment3.ast.IListSelectorExpression;
import edu.ufl.cise.plpfa21.assignment3.ast.IListType;
import edu.ufl.cise.plpfa21.assignment3.ast.IMutableGlobal;
import edu.ufl.cise.plpfa21.assignment3.ast.INameDef;
import edu.ufl.cise.plpfa21.assignment3.ast.INilConstantExpression;
import edu.ufl.cise.plpfa21.assignment3.ast.IPrimitiveType;
import edu.ufl.cise.plpfa21.assignment3.ast.IProgram;
import edu.ufl.cise.plpfa21.assignment3.ast.IReturnStatement;
import edu.ufl.cise.plpfa21.assignment3.ast.IStatement;
import edu.ufl.cise.plpfa21.assignment3.ast.IStringLiteralExpression;
import edu.ufl.cise.plpfa21.assignment3.ast.ISwitchStatement;
import edu.ufl.cise.plpfa21.assignment3.ast.IType;
import edu.ufl.cise.plpfa21.assignment3.ast.IType.TypeKind;
import edu.ufl.cise.plpfa21.assignment3.ast.IUnaryExpression;
import edu.ufl.cise.plpfa21.assignment3.ast.IWhileStatement;
import edu.ufl.cise.plpfa21.assignment3.astimpl.Type__;
//import edu.ufl.cise.plpfa21.assignment5.ReferenceCodeGenVisitor.LocalVarInfo;
//import edu.ufl.cise.plpfa21.assignment5.ReferenceCodeGenVisitor.MethodVisitorLocalVarTable;
//import edu.ufl.cise.plpfa21.pLP.ListSelectorExpression;


public class StarterCodeGenVisitor implements ASTVisitor, Opcodes {
	
	public StarterCodeGenVisitor(String className, String packageName, String sourceFileName){
		this.className = className;
		this.packageName = packageName;	
		this.sourceFileName = sourceFileName;
	}
	

	ClassWriter cw;
	String className;
	String packageName;
	String classDesc;
	String sourceFileName; //



	public static final String stringClass = "java/lang/String";
	public static final String stringDesc = "Ljava/lang/String;";
	public static final String listClass = "java/util/ArrayList";
	public static final String listDesc = "Ljava/util/ArrayList;";
	public static final String runtimeClass = "edu/ufl/cise/plpfa21/assignment5/Runtime";
	
	
	
	/* Records for information passed to children, namely the methodVisitor and information about current methods Local Variables */
	record LocalVarInfo(String name, String typeDesc, Label start, Label end) {}
	record MethodVisitorLocalVarTable(MethodVisitor mv, List<LocalVarInfo> localVars) {};	

	/*  Adds local variables to a method
	 *  The information about local variables to add has been collected in the localVars table.  
	 *  This method should be invoked after all instructions for the method have been generated, immediately before invoking mv.visitMaxs.
	 */
	private void addLocals(MethodVisitorLocalVarTable arg, Label start, Label end) {
		MethodVisitor mv = arg.mv;
		List<LocalVarInfo> localVars = arg.localVars;
		for (int slot = 0; slot < localVars.size(); slot++) {
			LocalVarInfo varInfo = localVars.get(slot);
			String varName = varInfo.name;
			String localVarDesc = varInfo.typeDesc;
			Label range0 = varInfo.start == null ? start : varInfo.start;
		    Label range1 = varInfo.end == null ? end : varInfo.end;
		    mv.visitLocalVariable(varName, localVarDesc, null, range0, range1, slot);
		}
	}

	@Override
	public Object visitIBinaryExpression(IBinaryExpression n, Object arg) throws Exception {
		//get method visitor from arg
		MethodVisitor mv = ((MethodVisitorLocalVarTable) arg).mv;
		//generate code to leave value of expression on top of stack
		n.getLeft().visit(this, arg);
		n.getRight().visit(this, arg);
		//get the operator and types of operand and result
		Kind op = n.getOp();
		IType resultType = n.getType();
		IType operandType = n.getLeft().getType();
		switch(op) {
			case PLUS -> {
				if(operandType.isInt())
					mv.visitMethodInsn(INVOKESTATIC, runtimeClass, "plus", "(II)I",false);	
				else if(operandType.isString())
					mv.visitMethodInsn(INVOKESTATIC, runtimeClass, "concat", "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;",false);
			}
			case MINUS -> {
				mv.visitMethodInsn(INVOKESTATIC, runtimeClass, "minus", "(II)I",false);					
			}
			case TIMES -> {
				mv.visitMethodInsn(INVOKESTATIC, runtimeClass, "times", "(II)I",false);					
			}
			case DIV -> {
				mv.visitMethodInsn(INVOKESTATIC, runtimeClass, "div", "(II)I",false);					
			}
			case OR -> {
				mv.visitMethodInsn(INVOKESTATIC, runtimeClass, "or", "(ZZ)Z",false);					
			}
			case AND -> {
				if (operandType.isBoolean()) {
					mv.visitMethodInsn(INVOKESTATIC, runtimeClass, "and", "(ZZ)Z",false);
				}
				else {
					throw new UnsupportedOperationException("SKIP THIS");
				}
			}
			case EQUALS->{
				if(operandType.isInt())
					mv.visitMethodInsn(INVOKESTATIC, runtimeClass, "eq_int", "(II)Z",false);	
				else if(operandType.isString())
					mv.visitMethodInsn(INVOKESTATIC, runtimeClass, "eq_string", "(Ljava/lang/String;Ljava/lang/String;)Z",false);
				else if(operandType.isBoolean())
					mv.visitMethodInsn(INVOKESTATIC, runtimeClass, "eq_bool", "(ZZ)Z",false);
			}
			case NOT_EQUALS->{
				if(operandType.isInt())
					mv.visitMethodInsn(INVOKESTATIC, runtimeClass, "ne_int", "(II)Z",false);	
				else if(operandType.isString())
					mv.visitMethodInsn(INVOKESTATIC, runtimeClass, "ne_string", "(Ljava/lang/String;Ljava/lang/String;)Z",false);
				else if(operandType.isBoolean())
					mv.visitMethodInsn(INVOKESTATIC, runtimeClass, "ne_bool", "(ZZ)Z",false);
			}
			case LT->{
				if(operandType.isInt())
					mv.visitMethodInsn(INVOKESTATIC, runtimeClass, "lt_int", "(II)Z",false);	
				else if(operandType.isString())
					mv.visitMethodInsn(INVOKESTATIC, runtimeClass, "lt_string", "(Ljava/lang/String;Ljava/lang/String;)Z",false);
				else if(operandType.isBoolean())
					mv.visitMethodInsn(INVOKESTATIC, runtimeClass, "lt_bool", "(ZZ)Z",false);
			}
			case GT->{
				if(operandType.isInt())
					mv.visitMethodInsn(INVOKESTATIC, runtimeClass, "gt_int", "(II)Z",false);	
				else if(operandType.isString())
					mv.visitMethodInsn(INVOKESTATIC, runtimeClass, "gt_string", "(Ljava/lang/String;Ljava/lang/String;)Z",false);
				else if(operandType.isBoolean())
					mv.visitMethodInsn(INVOKESTATIC, runtimeClass, "gt_bool", "(ZZ)Z",false);
			}
			
			default -> throw new UnsupportedOperationException("compiler error");
		}
		return null;
	}


	@Override
	public Object visitIBlock(IBlock n, Object arg) throws Exception {
		List<IStatement> statements = n.getStatements();
		for(IStatement statement: statements) {
			statement.visit(this, arg);
		}
		return null;
	}

	@Override
	public Object visitIBooleanLiteralExpression(IBooleanLiteralExpression n, Object arg) throws Exception {
		MethodVisitor mv = ((MethodVisitorLocalVarTable) arg).mv;
		mv.visitLdcInsn(n.getValue());
		return null;
	}


	
	@Override
	public Object visitIFunctionDeclaration(IFunctionDeclaration n, Object arg) throws Exception {
		String name = n.getName().getName();

		//Local var table
		List<LocalVarInfo> localVars = new ArrayList<LocalVarInfo>();
		//Add args to local var table while constructing type desc.
		List<INameDef> args = n.getArgs();

		//Iterate over the parameter list and build the function descriptor
		//Also assign and store slot numbers for parameters
		StringBuilder sb = new StringBuilder();	
		sb.append("(");
		for(INameDef def: args) {
			String desc = def.getType().getDesc();
			sb.append(desc);
			def.getIdent().setSlot(localVars.size());
			localVars.add(new LocalVarInfo(def.getIdent().getName(), desc, null, null));
		}
		sb.append(")");
		sb.append(n.getResultType().getDesc());
		String desc = sb.toString();
		
		// get method visitor
		MethodVisitor mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, name, desc, null, null);
		// initialize
		mv.visitCode();
		// mark beginning of instructions for method
		Label funcStart = new Label();
		mv.visitLabel(funcStart);
		MethodVisitorLocalVarTable context = new MethodVisitorLocalVarTable(mv, localVars);
		//visit block to generate code for statements
		n.getBlock().visit(this, context);
		
		//add return instruction if Void return type
		if(n.getResultType().equals(Type__.voidType)) {
			mv.visitInsn(RETURN);
		}
		
		//add label after last instruction
		Label funcEnd = new Label();
		mv.visitLabel(funcEnd);
		
		addLocals(context, funcStart, funcEnd);

		mv.visitMaxs(0, 0);
		
		//terminate construction of method
		mv.visitEnd();
		return null;

	}




	@Override
	public Object visitIFunctionCallExpression(IFunctionCallExpression n, Object arg) throws Exception {
		MethodVisitor mv = ((MethodVisitorLocalVarTable)arg).mv;
		IIdentifier identifier = n.getName();
		String methodDesc = "";
		
		List<IExpression> args = n.getArgs();
		if(n.getArgs().size() > 0) {
			methodDesc = "(";
			for (IExpression a : args){
				a.visit(this, arg);
				methodDesc += a.getType().getDesc();
			}
			methodDesc += ")" + n.getType().getDesc();
		}
		else {
			methodDesc = "()" + n.getType().getDesc();
		}
		mv.visitMethodInsn(INVOKESTATIC, className, identifier.getName(), methodDesc,false);

		return null;
	}

	@Override
	public Object visitIIdentExpression(IIdentExpression n, Object arg) throws Exception {
		MethodVisitor mv = ((MethodVisitorLocalVarTable)arg).mv;
		IIdentifier ident = n.getName();
		ident.visit(this, arg);

		if (!ident.isLocal()){
			mv.visitFieldInsn(GETSTATIC, className, ident.getName(), n.getType().getDesc());
		}
		else{
			if(n.getType().isString())
				mv.visitVarInsn(ALOAD, ident.getSlot());
			else
				mv.visitVarInsn(ILOAD, ident.getSlot());
		}

		return null;
	}

	@Override
	public Object visitIIdentifier(IIdentifier n, Object arg) throws Exception {
		List<LocalVarInfo> localVars = ((MethodVisitorLocalVarTable)arg).localVars;
		int i = 0;
		for (LocalVarInfo localVar : localVars){
			if (n.getName().equals(localVars.get(i).name)){
				n.setLocal(true);
				n.setSlot(i);
				break;
			}
			i++;
		}

		return null;
	}

	@Override
	public Object visitIIfStatement(IIfStatement n, Object arg) throws Exception {
		MethodVisitor mv = ((MethodVisitorLocalVarTable)arg).mv;
		IExpression e = n.getGuardExpression();
		
		if(e != null) {
			e.visit(this, arg);
		}
		Label l0 = new Label();
		mv.visitJumpInsn(IFEQ, l0);
		n.getBlock().visit(this, arg);
		mv.visitLabel(l0);
		
		return null;
	}

	@Override
	public Object visitIImmutableGlobal(IImmutableGlobal n, Object arg) throws Exception {
		MethodVisitor mv = ((MethodVisitorLocalVarTable)arg).mv;
		INameDef nameDef = n.getVarDef();
		String varName = nameDef.getIdent().getName();
		String typeDesc = nameDef.getType().getDesc();
		FieldVisitor fieldVisitor = cw.visitField(ACC_PUBLIC | ACC_STATIC | ACC_FINAL, varName, typeDesc, null, null);
		fieldVisitor.visitEnd();
		//generate code to initialize field.  
		IExpression e = n.getExpression();
		e.visit(this, arg);  //generate code to leave value of expression on top of stack
		mv.visitFieldInsn(PUTSTATIC, className, varName, typeDesc);	
		return null;
	}

	@Override
	public Object visitIIntLiteralExpression(IIntLiteralExpression n, Object arg) throws Exception {
		MethodVisitor mv = ((MethodVisitorLocalVarTable)arg).mv;
		mv.visitLdcInsn(n.getValue());
		return null;
	}

	@Override
	public Object visitILetStatement(ILetStatement n, Object arg) throws Exception {
		MethodVisitor mv = ((MethodVisitorLocalVarTable)arg).mv;
		n.getLocalDef().visit(this, arg);

		IExpression e = n.getExpression();
		if (e != null){
			e.visit(this, arg);
			if(e.getType().isString())
				mv.visitVarInsn(ASTORE, n.getLocalDef().getIdent().getSlot());
			else
				mv.visitVarInsn(ISTORE, n.getLocalDef().getIdent().getSlot());
		}
		n.getBlock().visit(this, arg);
		return null;
	}
		


	@Override
	public Object visitIListSelectorExpression(IListSelectorExpression n, Object arg) throws Exception {
		throw new UnsupportedOperationException("SKIP THIS");
	}

	@Override
	public Object visitIListType(IListType n, Object arg) throws Exception {
		throw new UnsupportedOperationException("SKIP THIS!!");
	}

	@Override
	public Object visitINameDef(INameDef n, Object arg) throws Exception {
		MethodVisitor mv = ((MethodVisitorLocalVarTable)arg).mv;		
		IIdentifier ident = n.getIdent();
		
		if(ident.isLocal()) {
			List<LocalVarInfo> localVars = ((MethodVisitorLocalVarTable)arg).localVars;
			ident.setSlot(localVars.size());
			localVars.add(new LocalVarInfo(ident.getName(), n.getType().getDesc(), null, null));
		}
		return null;
	}

	@Override
	public Object visitINilConstantExpression(INilConstantExpression n, Object arg) throws Exception {
		throw new UnsupportedOperationException("SKIP THIS");
	}

	@Override
	public Object visitIProgram(IProgram n, Object arg) throws Exception {
		cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		/*
		 * If the call to mv.visitMaxs(1, 1) crashes, it is sometime helpful to temporarily try it without COMPUTE_FRAMES. You won't get a runnable class file
		 * but you can at least see the bytecode that is being generated. 
		 */
//	    cw = new ClassWriter(0); 
		classDesc = "L" + className + ";";
		cw.visit(V16, ACC_PUBLIC + ACC_SUPER, className, null, "java/lang/Object", null);
		if (sourceFileName != null) cw.visitSource(sourceFileName, null);
		
		// create MethodVisitor for <clinit>  
		//  This method is the static initializer for the class and contains code to initialize global variables.
		// get a MethodVisitor
		MethodVisitor clmv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "<clinit>", "()V", null, null);
		// visit the code first
		clmv.visitCode();
		//mark the beginning of the code
		Label clinitStart = new Label();
		clmv.visitLabel(clinitStart);
		//create a list to hold local var info.  This will remain empty for <clinit> but is shown for completeness.  Methods with local variable need this.
		List<LocalVarInfo> initializerLocalVars = new ArrayList<LocalVarInfo>();
		//pair the local var infor and method visitor to pass into visit routines
		MethodVisitorLocalVarTable clinitArg = new MethodVisitorLocalVarTable(clmv,initializerLocalVars);
		//visit all the declarations. 
		List<IDeclaration> decs = n.getDeclarations();
		for (IDeclaration dec : decs) {
			dec.visit(this, clinitArg);  //argument contains local variable info and the method visitor.  
		}
		//add a return method
		clmv.visitInsn(RETURN);
		//mark the end of the bytecode for <clinit>
		Label clinitEnd = new Label();
		clmv.visitLabel(clinitEnd);
		//add the locals to the class
		addLocals(clinitArg, clinitStart, clinitEnd);  //shown for completeness.  There shouldn't be any local variables in clinit.
		//required call of visitMaxs.  Since we created the ClassWriter with  COMPUTE_FRAMES, the parameter values don't matter. 
		clmv.visitMaxs(0, 0);
		//finish the method
		clmv.visitEnd();
	
		//finish the clas
		cw.visitEnd();

		//generate classfile as byte array and return
		return cw.toByteArray();
	}

	@Override
	public Object visitIReturnStatement(IReturnStatement n, Object arg) throws Exception {
		//get the method visitor from the arg
		MethodVisitor mv = ((MethodVisitorLocalVarTable)arg).mv;
		IExpression e = n.getExpression();
		if (e != null) {  //the return statement has an expression
			e.visit(this, arg);  //generate code to leave value of expression on top of stack.
			//use type of expression to determine which return instruction to use
			IType type = e.getType();
			if (type.isInt() || type.isBoolean()) {mv.visitInsn(IRETURN);}
			else  {mv.visitInsn(ARETURN);}
		}
		else { //there is no argument, (and we have verified duirng type checking that function has void return type) so use this return statement.  
			mv.visitInsn(RETURN);
		}
		return null;
	}

	@Override
	public Object visitIStringLiteralExpression(IStringLiteralExpression n, Object arg) throws Exception {
		MethodVisitor mv = ((MethodVisitorLocalVarTable)arg).mv;
		mv.visitLdcInsn(n.getValue());
		return null;
	}

	@Override
	public Object visitISwitchStatement(ISwitchStatement n, Object arg) throws Exception {
		throw new UnsupportedOperationException("SKIP THIS");

	}

	@Override
	public Object visitIUnaryExpression(IUnaryExpression n, Object arg) throws Exception {
		//get method visitor from arg
		MethodVisitor mv = ((MethodVisitorLocalVarTable) arg).mv;
		//generate code to leave value of expression on top of stack
		n.getExpression().visit(this, arg);
		//get the operator and types of operand and result
		Kind op = n.getOp();
		IType resultType = n.getType();
		IType operandType = n.getExpression().getType();
		switch(op) {
		case MINUS -> {
			mv.visitMethodInsn(INVOKESTATIC, runtimeClass, "opp", "(I)I",false);
		}
		case BANG -> {
			if (operandType.isBoolean()) {
				mv.visitMethodInsn(INVOKESTATIC, runtimeClass, "not", "(Z)Z",false);
			}
			else { //argument is List
				throw new UnsupportedOperationException("SKIP THIS");
		}
		}
		default -> throw new UnsupportedOperationException("compiler error");
		}
		return null;
	}

	@Override
	public Object visitIWhileStatement(IWhileStatement n, Object arg) throws Exception {
		MethodVisitor mv = ((MethodVisitorLocalVarTable)arg).mv;
		IExpression e = n.getGuardExpression();
		Label l0 = new Label();
		mv.visitJumpInsn(GOTO, l0);
		Label l1 = new Label();
		mv.visitLabel(l1);
		n.getBlock().visit(this,  arg);
		mv.visitLabel(l0);
		e.visit(this,  arg);
		mv.visitJumpInsn(IFNE, l1);
		return null;
	}


	@Override
	public Object visitIMutableGlobal(IMutableGlobal n, Object arg) throws Exception {
		MethodVisitor mv = ((MethodVisitorLocalVarTable)arg).mv;				
		INameDef nameDef = n.getVarDef();
		String varName = nameDef.getIdent().getName();
		String typeDesc = nameDef.getType().getDesc();
		FieldVisitor fieldVisitor = cw.visitField(ACC_PUBLIC | ACC_STATIC, varName, typeDesc, null, null);
		fieldVisitor.visitEnd();
		if(n.getExpression() != null) {
			IExpression e = n.getExpression();
			e.visit(this, arg); 
			mv.visitFieldInsn(PUTSTATIC, className, varName, typeDesc);	
		}
		return null;
	}

	@Override
	public Object visitIPrimitiveType(IPrimitiveType n, Object arg) throws Exception {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}


	@Override
	public Object visitIAssignmentStatement(IAssignmentStatement n, Object arg) throws Exception {
		MethodVisitor mv = ((MethodVisitorLocalVarTable)arg).mv;
		
		if(n.getRight() == null) {
			((IFunctionCallExpression)n.getLeft()).visit(this, arg);
		}
		else {
			n.getRight().visit(this,  arg);
			IType rightType = n.getRight().getType();
			IIdentifier leftIdent = ((IIdentExpression) n.getLeft()).getName();
			leftIdent.visit(this,  arg);
			if(leftIdent.isLocal()) {
				if(rightType.isString())
					mv.visitVarInsn(ASTORE, leftIdent.getSlot());
				else
					mv.visitVarInsn(ISTORE, leftIdent.getSlot());
			}
			else {
				mv.visitFieldInsn(PUTSTATIC, className, leftIdent.getName(), rightType.getDesc());
			}
		}		
		return null;
	}

	@Override
	public Object visitIExpressionStatement(IExpressionStatement n, Object arg) throws Exception {
		throw new UnsupportedOperationException("TO IMPLEMENT");
	}
}
