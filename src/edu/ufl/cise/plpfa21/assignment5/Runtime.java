package edu.ufl.cise.plpfa21.assignment5;

public class Runtime {

	public static boolean not(boolean arg) {
		return !arg;
	}
	public static int opp(int arg) {
		return -1 * (arg);
	}
	public static int plus (int arg1, int arg2) {
		return arg1 + arg2;
	}
	public static int minus (int arg1, int arg2) {
		return arg1 - arg2;
	}
	public static int times (int arg1, int arg2) {
		return arg1 * arg2;
	}
	public static int div (int arg1, int arg2) {
		return arg1 / arg2;
	}
	public static String concat(String arg1, String arg2) {
		return arg1 + arg2;
	}
	public static boolean and (boolean arg1, boolean arg2) {
		return arg1 && arg2;
	}
	public static boolean or (boolean arg1, boolean arg2) {
		return arg1 || arg2;
	}
	public static boolean eq_int(int arg1, int arg2) {
		return arg1 == arg2;
	}
	public static boolean ne_int(int arg1, int arg2) {
		return arg1 != arg2;
	}
	public static boolean lt_int(int arg1, int arg2) {
		return arg1 < arg2;
	}
	public static boolean gt_int(int arg1, int arg2) {
		return arg1 > arg2;
	}
	
	public static boolean eq_bool(boolean arg1, boolean arg2) {
		return arg1 == arg2;
	}
	public static boolean ne_bool(boolean arg1, boolean arg2) {
		return arg1 != arg2;
	}
	public static boolean lt_bool(boolean arg1,boolean arg2) {
		if(arg1 == false && arg2 == true) 
			return true;
		return false;
	}
	public static boolean gt_bool(boolean arg1, boolean arg2) {
		if(arg1 == true && arg2 == false) 
			return true;
		return false;
	}
	
	public static boolean eq_string(String arg1, String arg2) {
		return arg1.equals(arg2);
	}
	public static boolean ne_string(String arg1, String arg2) {
		return !arg1.equals(arg2);
	}
	public static boolean lt_string(String arg1,String arg2) {
		return arg2.startsWith(arg1);
	}
	public static boolean gt_string(String arg1, String arg2) {
		return !arg2.startsWith(arg1);
	}
}
