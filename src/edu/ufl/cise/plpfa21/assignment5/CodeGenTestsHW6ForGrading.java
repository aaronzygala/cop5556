package edu.ufl.cise.plpfa21.assignment5;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

public class CodeGenTestsHW6ForGrading extends CodeGenTestsBaseHW6ForGrading{

	@DisplayName("testLiteral_boolean_true")
	@Test
	public void testLiteral_boolean_true(TestInfo testInfo) throws Exception {
		String input = """
				FUN f():BOOLEAN DO RETURN TRUE; END
				""";

		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		boolean result = (boolean) loadClassAndRunMethod(bytecode, className, "f", null);
		assertEquals(true, result);
	}

	@DisplayName("testLiteral_boolean_false")
	@Test
	public void testLiteral_boolean(TestInfo testInfo) throws Exception {
		String input = """
				FUN f():BOOLEAN DO RETURN FALSE; END
				""";

		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		boolean result = (boolean) loadClassAndRunMethod(bytecode, className, "f", null);
		assertEquals(false, result);
	}

	@DisplayName("testLiteral_int")
	@Test
	public void testLiteral_int(TestInfo testInfo) throws Exception {
		String input = """
				FUN f():INT DO RETURN 42; END
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		int result = (int) loadClassAndRunMethod(bytecode, className, "f", null);
		assertEquals(42, result);
	}

	@DisplayName("testLiteral_string")
	@Test
	public void testLiteral_string(TestInfo testInfo) throws Exception {
		String input = """
				FUN f():STRING DO RETURN "hello"; END
				""";

		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		String result = (String) loadClassAndRunMethod(bytecode, className, "f", null);
		assertEquals("hello", result);
	}



	@DisplayName("initGlobal_int0")
	@Test
	public void initGlobal_int0(TestInfo testInfo) throws Exception {
		String input = """
				VAR x:INT = 100;
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Class<?> testClass = getClass(bytecode, className);
		int x = getInt(testClass, "x");
		assertEquals(100, x);
	}

	@DisplayName("initGlobal_String0")
	@Test
	public void initGlobal_String0(TestInfo testInfo) throws Exception {
		String input = """
				VAR x:STRING = "bonjour";
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Class<?> testClass = getClass(bytecode, className);
		String x = getString(testClass, "x");
		assertEquals("bonjour", x);
	}

	@DisplayName("initGlobal_boolean0")
	@Test
	public void initGlobal_boolean0(TestInfo testInfo) throws Exception {
		String input = """
				VAR x:BOOLEAN = TRUE;
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Class<?> testClass = getClass(bytecode, className);
		boolean x = getBoolean(testClass, "x");
		assertEquals(true, x);
	}

	@DisplayName("initGlobal_int1")
	@Test
	public void initGlobal_int1(TestInfo testInfo) throws Exception {
		String input = """
				VAR x = 100;
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Class<?> testClass = getClass(bytecode, className);
		int x = getInt(testClass, "x");
		assertEquals(100, x);
	}

	@DisplayName("initGlobal_String1")
	@Test
	public void initGlobal_String1(TestInfo testInfo) throws Exception {
		String input = """
				VAR x = "bonjour";
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Class<?> testClass = getClass(bytecode, className);
		String x = getString(testClass, "x");
		assertEquals("bonjour", x);
	}

	@DisplayName("initGlobal_boolean1")
	@Test
	public void initGlobal_boolean1(TestInfo testInfo) throws Exception {
		String input = """
				VAR x = TRUE;
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Class<?> testClass = getClass(bytecode, className);
		boolean x = getBoolean(testClass, "x");
		assertEquals(true, x);
	}

	@DisplayName("initGlobal_int2")
	@Test
	public void initGlobal_int2(TestInfo testInfo) throws Exception {
		String input = """
				VAL x:INT = 100;
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Class<?> testClass = getClass(bytecode, className);
		int x = getInt(testClass, "x");
		assertEquals(100, x);
	}

	@DisplayName("initGlobal_String2")
	@Test
	public void initGlobal_String2(TestInfo testInfo) throws Exception {
		String input = """
				VAL x:STRING = "bonjour";
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Class<?> testClass = getClass(bytecode, className);
		String x = getString(testClass, "x");
		assertEquals("bonjour", x);
	}

	@DisplayName("initGlobal_boolean2")
	@Test
	public void initGlobal_boolean2(TestInfo testInfo) throws Exception {
		String input = """
				VAL x:BOOLEAN = TRUE;
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Class<?> testClass = getClass(bytecode, className);
		boolean x = getBoolean(testClass, "x");
		assertEquals(true, x);
	}

	@DisplayName("initGlobal_int3")
	@Test
	public void initGlobal_int3(TestInfo testInfo) throws Exception {
		String input = """
				VAL x = 100;
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Class<?> testClass = getClass(bytecode, className);
		int x = getInt(testClass, "x");
		assertEquals(100, x);
	}

	@DisplayName("initGlobal_String3")
	@Test
	public void initGlobal_String3(TestInfo testInfo) throws Exception {
		String input = """
				VAL x = "bonjour";
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Class<?> testClass = getClass(bytecode, className);
		String x = getString(testClass, "x");
		assertEquals("bonjour", x);
	}

	@DisplayName("initGlobal_boolean3")
	@Test
	public void initGlobal_boolean3(TestInfo testInfo) throws Exception {
		String input = """
				VAL x = TRUE;
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Class<?> testClass = getClass(bytecode, className);
		boolean x = getBoolean(testClass, "x");
		assertEquals(true, x);
	}

	@DisplayName("accessGlobal_int0")
	@Test
	public void accessGlobal_int0(TestInfo testInfo) throws Exception {
		String input = """
				VAR x = 33;
				FUN f():INT
				DO
				   RETURN x;
				END
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		int x = (int) loadClassAndRunMethod(bytecode, className, "f", null);
		assertEquals(33, x);
	}

	@DisplayName("accessGlobal_String0")
	@Test
	public void accessGlobal_String0(TestInfo testInfo) throws Exception {
		String input = """
				VAR x = "guten Tag";
				FUN f():STRING
				DO
				   RETURN x;
				END
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		String x = (String) loadClassAndRunMethod(bytecode, className, "f", null);
		assertEquals("guten Tag", x);
	}

	@DisplayName("accessGlobal_boolean0")
	@Test
	public void accessGlobal_boolean0(TestInfo testInfo) throws Exception {
		String input = """
				VAR x = TRUE;
				FUN f():BOOLEAN
				DO
				   RETURN x;
				END
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		boolean x = (boolean) loadClassAndRunMethod(bytecode, className, "f", null);
		assertEquals(true, x);
	}

	@DisplayName("accessGlobal_int1")
	@Test
	public void accessGlobal_int1(TestInfo testInfo) throws Exception {
		String input = """
				VAL x = 33;
				FUN f():INT
				DO
				   RETURN x;
				END
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		int x = (int) loadClassAndRunMethod(bytecode, className, "f", null);
		assertEquals(33, x);
	}

	@DisplayName("accessGlobal_String1")
	@Test
	public void accessGlobal_String1(TestInfo testInfo) throws Exception {
		String input = """
				VAL x = "guten Tag";
				FUN f():STRING
				DO
				   RETURN x;
				END
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		String x = (String) loadClassAndRunMethod(bytecode, className, "f", null);
		assertEquals("guten Tag", x);
	}

	@DisplayName("accessGlobal_boolean1")
	@Test
	public void accessGlobal_boolean1(TestInfo testInfo) throws Exception {
		String input = """
				VAL x = TRUE;
				FUN f():BOOLEAN
				DO
				   RETURN x;
				END
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		boolean x = (boolean) loadClassAndRunMethod(bytecode, className, "f", null);
		assertEquals(true, x);
	}

	@DisplayName("updateGlobal_int0")
	@Test
	public void updateGlobal_int0(TestInfo testInfo) throws Exception {
		String input = """
				VAR x:INT;
				FUN f()
				DO
				   x=44;
				END
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Class<?> testClass = getClass(bytecode, className);
		runMethod(testClass, "f", null);
		assertEquals(44, getInt(testClass, "x"));
	}

	@DisplayName("updateGlobal_String0")
	@Test
	public void updateGlobal_String0(TestInfo testInfo) throws Exception {
		String input = """
				VAR x:STRING;
				FUN f()
				DO
				   x="Gruetzi";
				END
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Class<?> testClass = getClass(bytecode, className);
		runMethod(testClass, "f", null);
		assertEquals("Gruetzi", getString(testClass, "x"));
	}

	@DisplayName("accessGlobal_boolean0")
	@Test
	public void updateGlobal_boolean0(TestInfo testInfo) throws Exception {
		String input = """
				VAR x:BOOLEAN;
				FUN f()
				DO
				   x=FALSE;
				END
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Class<?> testClass = getClass(bytecode, className);
		runMethod(testClass, "f", null);
		assertEquals(false, getBoolean(testClass, "x"));
	}

	@DisplayName("unaryMinus_int")
	@Test
	public void unaryMinus_int(TestInfo testInfo) throws Exception {
		String input = """
				VAR x:INT;
				FUN f()
				DO
				   x = -44;
				END
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Class<?> testClass = getClass(bytecode, className);
		runMethod(testClass, "f", null);
		assertEquals(-44, getInt(testClass, "x"));
	}

	@DisplayName("unaryNot0")
	@Test
	public void unaryNot0(TestInfo testInfo) throws Exception {
		String input = """
				FUN f():BOOLEAN
				DO
				   RETURN ! TRUE;
				END
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Class<?> testClass = getClass(bytecode, className);
		boolean result = (boolean) runMethod(testClass, "f", null);
		assertEquals(false, result);
	}
	
	
	@DisplayName("unaryNot1")
	@Test
	public void unaryNot1(TestInfo testInfo) throws Exception {
		String input = """
				VAR x:BOOLEAN = TRUE;
				FUN f()
				DO
				   x = !x;
				END
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Class<?> testClass = getClass(bytecode, className);
		runMethod(testClass, "f", null);
		assertEquals(false, getBoolean(testClass, "x"));
	}

	@DisplayName("binaryPlus_int")
	@Test
	public void binaryPlus_int(TestInfo testInfo) throws Exception {
		String input = """
				VAR x = 3;
				VAR y = 4;
				VAR z = x+y;
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Class<?> testClass = getClass(bytecode, className);
		assertEquals(7, getInt(testClass, "z"));
	}

	@DisplayName("binaryMinus_int")
	@Test
	public void binaryMinus_int(TestInfo testInfo) throws Exception {
		String input = """
				VAR z = 10-3;
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Class<?> testClass = getClass(bytecode, className);
		assertEquals(7, getInt(testClass, "z"));
	}

	@DisplayName("binaryTimes_int")
	@Test
	public void binaryTimes_int(TestInfo testInfo) throws Exception {
		String input = """
				VAR x = 3;
				VAR y = 4;
				VAR z = x*y;
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Class<?> testClass = getClass(bytecode, className);
		assertEquals(12, getInt(testClass, "z"));
	}

	@DisplayName("binaryDiv_int")
	@Test
	public void binaryDiv_int(TestInfo testInfo) throws Exception {
		String input = """
				VAR x = 3;
				VAR y = 4;
				VAR z = 12;
				VAR a = x/y;
				VAR b = y/x;
				VAR c = z/x;
				VAR d = z/y;
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Class<?> testClass = getClass(bytecode, className);
		assertEquals(0, getInt(testClass, "a"));
		assertEquals(1, getInt(testClass, "b"));
		assertEquals(4, getInt(testClass, "c"));
		assertEquals(3, getInt(testClass, "d"));
	}

	@DisplayName("binaryPlus_string")
	@Test
	public void binaryPlus_string(TestInfo testInfo) throws Exception {
		String input = """
				VAR x = "Go ";
				VAR y = "Gators!";
				VAR z = x+y;
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Class<?> testClass = getClass(bytecode, className);
		assertEquals("Go Gators!", getString(testClass, "z"));
	}

	@DisplayName("binaryEquals_int")
	@Test
	public void binaryEquals_int(TestInfo testInfo) throws Exception {
		String input = """
				VAR x = 3;
				VAR y = (3 == x);
				VAR z = (4 == x);
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Class<?> testClass = getClass(bytecode, className);
		assertEquals(true, getBoolean(testClass, "y"));
		assertEquals(false, getBoolean(testClass, "z"));
	}

	@DisplayName("binaryEquals_boolean")
	@Test
	public void binaryEquals_boolean(TestInfo testInfo) throws Exception {
		String input = """
				VAR x = TRUE;
				VAR y = (TRUE == x);
				VAR z = (FALSE == x);
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Class<?> testClass = getClass(bytecode, className);
		assertEquals(true, getBoolean(testClass, "y"));
		assertEquals(false, getBoolean(testClass, "z"));
	}

	@DisplayName("binaryEquals_string")
	@Test
	public void binaryEquals_string(TestInfo testInfo) throws Exception {
		String input = """
				VAR x = "x";
				VAR y = ("x" == x);
				VAR z = ("y" == x);
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Class<?> testClass = getClass(bytecode, className);
		assertEquals(true, getBoolean(testClass, "y"));
		assertEquals(false, getBoolean(testClass, "z"));
	}

	@DisplayName("binaryNotEquals_int")
	@Test
	public void binaryNotEquals_int(TestInfo testInfo) throws Exception {
		String input = """
				VAR x = 3;
				VAR y = (3 != x);
				VAR z = (4 != x);
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Class<?> testClass = getClass(bytecode, className);
		assertEquals(true, getBoolean(testClass, "z"));
		assertEquals(false, getBoolean(testClass, "y"));
	}

	@DisplayName("binaryNotEquals_boolean")
	@Test
	public void binaryNotEquals_boolean(TestInfo testInfo) throws Exception {
		String input = """
				VAR x = TRUE;
				VAR y = (TRUE != x);
				VAR z = (FALSE != x);
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Class<?> testClass = getClass(bytecode, className);
		assertEquals(true, getBoolean(testClass, "z"));
		assertEquals(false, getBoolean(testClass, "y"));
	}

	@DisplayName("binaryNotEquals_string")
	@Test
	public void binaryNotEquals_string(TestInfo testInfo) throws Exception {
		String input = """
				VAR x = "x";
				VAR y = ("x" != x);
				VAR z = ("y" != x);
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Class<?> testClass = getClass(bytecode, className);
		assertEquals(true, getBoolean(testClass, "z"));
		assertEquals(false, getBoolean(testClass, "y"));
	}

	@DisplayName("binaryLT_int")
	@Test
	public void binaryLT_int(TestInfo testInfo) throws Exception {
		String input = """
				VAR x = 3;
				VAR y = (3 < x);
				VAR z = (4 < x);
				VAR w = (2 < x);
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Class<?> testClass = getClass(bytecode, className);
		assertEquals(false, getBoolean(testClass, "y"));
		assertEquals(false, getBoolean(testClass, "z"));
		assertEquals(true, getBoolean(testClass, "w"));
	}

	@DisplayName("binaryGT_int")
	@Test
	public void binaryGT_int(TestInfo testInfo) throws Exception {
		String input = """
				VAR x = 3;
				VAR y = (3 > x);
				VAR z = (4 > x);
				VAR w = (2 > x);
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Class<?> testClass = getClass(bytecode, className);
		assertEquals(false, getBoolean(testClass, "y"));
		assertEquals(true, getBoolean(testClass, "z"));
		assertEquals(false, getBoolean(testClass, "w"));
	}

	@DisplayName("binaryAND_boolean")
	@Test
	public void binaryAND_boolean(TestInfo testInfo) throws Exception {
		String input = """
				VAR x = TRUE;
				VAR y = (TRUE && x);
				VAR z = (FALSE && x);
				VAR a = (x && TRUE);
				VAR b = (x && FALSE);
				VAR c = (FALSE && b);
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Class<?> testClass = getClass(bytecode, className);
		assertEquals(true, getBoolean(testClass, "y"));
		assertEquals(false, getBoolean(testClass, "z"));
		assertEquals(true, getBoolean(testClass, "a"));
		assertEquals(false, getBoolean(testClass, "b"));
		assertEquals(false, getBoolean(testClass, "c"));
	}

	@DisplayName("binaryOR_boolean")
	@Test
	public void binaryOR_boolean(TestInfo testInfo) throws Exception {
		String input = """
				VAR x = FALSE;
				VAR t = TRUE;
				VAR y = (TRUE || x);
				VAR z = (x || TRUE);
				VAR a = (FALSE || x);
				VAR b = (x || FALSE);
				VAR c = (t || z);
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Class<?> testClass = getClass(bytecode, className);
		assertEquals(true, getBoolean(testClass, "y"));
		assertEquals(true, getBoolean(testClass, "z"));
		assertEquals(false, getBoolean(testClass, "a"));
		assertEquals(false, getBoolean(testClass, "b"));
		assertEquals(true, getBoolean(testClass, "c"));
	}

	@DisplayName("binaryLT_string")
	@Test
	public void binaryLT_string(TestInfo testInfo) throws Exception {
		String input = """
				VAR x = "x";
				VAR y = (x < "xyz");
				VAR z = (x < "x");
				VAR w = ("y" < x );
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Class<?> testClass = getClass(bytecode, className);
		assertEquals(true, getBoolean(testClass, "y"));
		assertEquals(true, getBoolean(testClass, "z"));
		assertEquals(false, getBoolean(testClass, "w"));
	}

	@DisplayName("binaryGT_string")
	@Test
	public void binaryGT_string(TestInfo testInfo) throws Exception {
		String input = """
				VAR x = "x";
				VAR y = (x > "xyz");
				VAR z = (x > "x");
				VAR w = ("y" > x );
				VAR a = ("xyz" > x);
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Class<?> testClass = getClass(bytecode, className);
		assertEquals(false, getBoolean(testClass, "y"));
		assertEquals(true, getBoolean(testClass, "z"));
		assertEquals(false, getBoolean(testClass, "w"));
		assertEquals(true, getBoolean(testClass, "a"));
	}

	@DisplayName("ifStatement0")
	@Test
	public void ifStatement0(TestInfo testInfo) throws Exception {
		String input = """
				VAR x = 0;
				FUN g():STRING
				DO
				IF x==0 DO RETURN "zero"; END
				RETURN "not zero";
				END
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		String result = (String) loadClassAndRunMethod(bytecode, className, "g", null);
		assertEquals("zero", result);
	}

	@DisplayName("ifStatement1")
	@Test
	public void ifStatement1(TestInfo testInfo) throws Exception {
		String input = """
				VAR x = 0;
				FUN g():STRING
				DO
				IF x!=0 DO RETURN "zero"; END
				RETURN "not zero";
				END
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		String result = (String) loadClassAndRunMethod(bytecode, className, "g", null);
		assertEquals("not zero", result);
	}
	
	@DisplayName("while_noparams0")
	@Test
	public void while_noparams0(TestInfo testInfo) throws Exception {
		String input = """
				VAR sum = 0;
				VAR i = 1;
				VAR end = 5;
				VAR by = 1;
				FUN a():INT
				DO
				  WHILE i < end DO
				     sum = sum + i;
				     i = i + by;
				     END
				  RETURN sum;
				END
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Object[] params = { };
		int result = (int) loadClassAndRunMethod(bytecode, className, "a", params);
		assertEquals(10, result);
	}
	
	@DisplayName("while_noparams1")
	@Test
	public void while_noparams1(TestInfo testInfo) throws Exception {
		String input = """
				VAR sum = 0;
				VAR i = 5;
				VAR end = 1;
				VAR by = 1;
				FUN a():INT
				DO
				  WHILE i < end DO
				     sum = sum + i;
				     i = i + by;
				     END
				  RETURN sum;
				END
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Object[] params = { };
		int result = (int) loadClassAndRunMethod(bytecode, className, "a", params);
		assertEquals(0, result);
	}	
	
	@DisplayName("while_noparams2")
	@Test
	public void while_noparams2(TestInfo testInfo) throws Exception {
		String input = """
				VAR sum = 0;
				VAR i = 1;
				VAR end = 5;
				VAR by = 2;
				FUN a():INT
				DO
				  WHILE i < end DO
				     sum = sum + i;
				     i = i + by;
				     END
				  RETURN sum;
				END
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Object[] params = { };
		int result = (int) loadClassAndRunMethod(bytecode, className, "a", params);
		assertEquals(4, result);
	}		


	@DisplayName("funcWithParams0")
	@Test
	public void funcWithParams0(TestInfo testInfo) throws Exception {
		String input = """
				FUN g(x:INT):INT
				DO
				   RETURN x + 1;
				END
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Object[] params = { 55 };
		int result = (int) loadClassAndRunMethod(bytecode, className, "g", params);
		assertEquals(56, result);
	}

	@DisplayName("funcWithParams1")
	@Test
	public void funcWithParams1(TestInfo testInfo) throws Exception {
		String input = """
				FUN g(x:STRING):STRING
				DO
				   RETURN x + "!!!";
				END
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Object[] params = { "hey there" };
		String result = (String) loadClassAndRunMethod(bytecode, className, "g", params);
		assertEquals("hey there!!!", result);
	}

	@DisplayName("funcWithParams2")
	@Test
	public void funcWithParams2(TestInfo testInfo) throws Exception {
		String input = """
				FUN g(x:BOOLEAN):STRING
				DO
				   IF x DO RETURN "x is true"; END
				   RETURN "x is false";
				END
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Object[] params = { true };
		String result = (String) loadClassAndRunMethod(bytecode, className, "g", params);
		assertEquals("x is true", result);
	}

	@DisplayName("funcWithParams3")
	@Test
	public void funcWithParams3(TestInfo testInfo) throws Exception {
		String input = """
				FUN g(x:INT, y:INT):INT
				DO
				   RETURN x+y;
				END
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Object[] params = { 5, 6 };
		int result = (int) loadClassAndRunMethod(bytecode, className, "g", params);
		assertEquals(11, result);
	}

	@DisplayName("while0")
	@Test
	public void while0(TestInfo testInfo) throws Exception {
		String input = """
				VAR sum = 0;
				FUN a(i:INT, end:INT, by:INT):INT
				DO
				  WHILE i < end DO
				     sum = sum + i;
				     i = i + by;
				     END
				  RETURN sum;
				END
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Object[] params = { 1, 5, 1 };
		int result = (int) loadClassAndRunMethod(bytecode, className, "a", params);
		assertEquals(10, result);
	}

	@DisplayName("while1")
	@Test
	public void while1(TestInfo testInfo) throws Exception {
		String input = """
				VAR sum = 0;
				FUN a(i:INT, end:INT, by:INT):INT
				DO
				  WHILE i < end DO
				     sum = sum + i;
				     i = i + by;
				     END
				  RETURN sum;
				END
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Object[] params = { 1, 5, 2 };
		int result = (int) loadClassAndRunMethod(bytecode, className, "a", params);
		assertEquals(4, result);
	}

	@DisplayName("while2")
	@Test
	public void while2(TestInfo testInfo) throws Exception {
		String input = """
				VAR sum = 0;
				FUN a(i:INT, end:INT, by:INT):INT
				DO
				  WHILE i < end DO
				     sum = sum + i;
				     i = i + by;
				     END
				  RETURN sum;
				END
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Object[] params = { 5, 5, 1 };
		int result = (int) loadClassAndRunMethod(bytecode, className, "a", params);
		assertEquals(0, result);
	}

	@DisplayName("let0")
	@Test
	public void let0(TestInfo testInfo) throws Exception {
		String input = """
				VAR x = 5;
				FUN a(): INT
				DO
				   LET y:INT = 4 DO RETURN x+y; END
				END
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Object[] params = {};
		int result = (int) loadClassAndRunMethod(bytecode, className, "a", params);
		assertEquals(9, result);
	}

	@DisplayName("let1")
	@Test
	public void let1(TestInfo testInfo) throws Exception {
		String input = """
				FUN a(): INT
				DO
				   LET y:INT = 4 DO LET x = 96 DO RETURN x+y; END END
				END
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Object[] params = {};
		int result = (int) loadClassAndRunMethod(bytecode, className, "a", params);
		assertEquals(100, result);
	}

	@DisplayName("let2")
	@Test
	public void let2(TestInfo testInfo) throws Exception {
		String input = """
				FUN a(): STRING
				DO
				   LET y:STRING DO LET x = "hello" DO y = x; RETURN y+"!!!"; END END
				END
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Object[] params = {};
		String result = (String) loadClassAndRunMethod(bytecode, className, "a", params);
		assertEquals("hello!!!", result);
	}

	@DisplayName("let3")
	@Test
	public void let3(TestInfo testInfo) throws Exception {
		String input = """
				FUN a(b:BOOLEAN): STRING
				DO
				   LET y:STRING =""
				   DO
				       LET x = "hello"
				       DO
				           IF b
				           DO
				              y = x;
				              RETURN y+"!!!";
				           END
				           RETURN x;
				       END
				   END
				END
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Object[] params = { true };
		String result = (String) loadClassAndRunMethod(bytecode, className, "a", params);
		assertEquals("hello!!!", result);
	}

	@DisplayName("let4")
	@Test
	public void let4(TestInfo testInfo) throws Exception {
		String input = """
				FUN a(b:BOOLEAN): STRING
				DO
				   LET y:STRING =""
				   DO
				       LET x = "hello"
				       DO
				           IF b
				           DO
				              y = x;
				              RETURN y+"!!!";
				           END
				           RETURN x;
				       END
				   END
				END
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Object[] params = { false };
		String result = (String) loadClassAndRunMethod(bytecode, className, "a", params);
		assertEquals("hello", result);
	}

	@DisplayName("paramMod0")
	@Test
	public void paramMod0(TestInfo testInfo) throws Exception {
		String input = """
				FUN a(b:BOOLEAN): BOOLEAN
				DO
				  b = !b;
				  RETURN b;
				END
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Object[] params = { false };
		boolean result = (boolean) loadClassAndRunMethod(bytecode, className, "a", params);
		assertEquals(true, result);
	}

	@DisplayName("paramMod1")
	@Test
	public void paramMod1(TestInfo testInfo) throws Exception {
		String input = """
				FUN a(b:BOOLEAN): BOOLEAN
				DO
				  b = !b;
				  RETURN b;
				END
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Object[] params = { true };
		boolean result = (boolean) loadClassAndRunMethod(bytecode, className, "a", params);
		assertEquals(false, result);
	}

	@DisplayName("funcCallExpr0")
	@Test
	public void funcCallExpr0(TestInfo testInfo) throws Exception {
		String input = """
				FUN a(x:INT): INT
				DO
				   RETURN x+1;
				END

				FUN main(y: INT): INT
				DO
				   RETURN a(y)+2;
				END
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Object[] params = { 7 };
		int result = (int) loadClassAndRunMethod(bytecode, className, "main", params);
		assertEquals(10, result);
	}

	@DisplayName("funcCallExpr1")
	@Test
	public void funcCallExpr1(TestInfo testInfo) throws Exception {
		String input = """
				VAR y:INT;
				FUN a(x:INT)
				DO
				   y = x+1;
				END

				FUN main()
				DO
				   a(4);
				END
				""";
		byte[] bytecode = compile(input, className, packageName);
		show(CodeGenUtils.bytecodeToString(bytecode));
		Object[] params = {};
		Class<?> testClass = getClass(bytecode, className);
		runMethod(testClass, "main", params);
		assertEquals(5, getInt(testClass, "y"));
	}


}