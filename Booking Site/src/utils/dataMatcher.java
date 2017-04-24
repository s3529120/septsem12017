package utils;

import java.util.regex.*;

public class dataMatcher {
	
	// Declare sets and patterns to match
	// For origin of this code see http://jregex.sourceforge.net/examples-email.html
	private static final String emailChars="[\\w.\\-]+"; //letters, dots, hyphens
	private static final String alpha="[a-zA-Z]+";
	private static final String digit="\\d+";
	private static final String space="\\s";
	private static final String hyphen="\\-";
	private static final String alphaNums="\\w+";
	private static final String dot="\\.";
	private static final String symbols="[\\W\\S]+"; //all non letter or number characters + non spaces

	// Create a new pattern to match email format: chars@(chars.)*chars
	// Create similar patterns for username, name, password, address etc.
	private static final String emailPattern=
			emailChars + "@" + alphaNums + "(?:" + "\\." + alphaNums + ")+";
	private static final String unamePattern = alpha + "\\d*"; // Just one or more letters and any numbers
	private static final String pnamePattern = alpha + "(?:" + space + alpha + ")*"; // Just letters, space and hyphen
	private static final String passPattern = "(?:" + alphaNums + "||" + symbols + ")+"; // Should be letters + numbers + Symbols
	private static final String addPattern = digit + "(?:" + space + alpha + ")+";
	private static final String phonePattern = "\\d" + "(?:" + "\\d" + "||" + space + ")+";// atleast one digit or space
	
	
	public static boolean emailMatcher(String s){
		if(Pattern.matches(emailPattern, s)){
			return true;
		} else {
			return false;
		}		
	}
	
	public static boolean nameMatcher(String s){
		if(Pattern.matches(pnamePattern, s)){
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean unameMatcher(String s){
		if(Pattern.matches(unamePattern, s)){
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean passMatcher(String s){
		if(Pattern.matches(passPattern, s)){
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean addMatcher(String s){
		if(Pattern.matches(addPattern, s)){
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean phoneMatcher(String s){
		if(Pattern.matches(phonePattern, s)){
			return true;
		} else {
			return false;
		}
	}	
	
}