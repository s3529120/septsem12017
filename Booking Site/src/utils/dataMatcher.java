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
	private static final String postcPattern = "\\d{4}";
	
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
	
	public static boolean postcMatcher(String s, String state){
		if(Pattern.matches(postcPattern, s)){
			int postCode;
			// We can safely assume that is a 4 digit integer. Try and catch just for added safety
			// Maybe useless?
			try{
				postCode = Integer.parseInt(s);
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
			
			// Postcode ranges: 
			// https://en.wikipedia.org/wiki/Postcodes_in_Australia#Australia_States_and_territories
			if(800 <= postCode && postCode < 1000){
				// Compare to correct state. Consider taking this value from AccountController.
				if(state == "N.T"){
					return true;
				}else{
					return false;
				}
				//Some ranges intersect due to special cases of some towns
			}else if(1000 <= postCode && postCode < 2600 
					|| postCode == 2899){
				if(state == "N.S.W"){
					return true;
				}else{
					return false;
				}
			}else if(200 <= postCode && postCode < 300 || 2600 <= postCode && postCode <= 2620 
					|| 2900 <= postCode && postCode <= 2920){
				if(state == "A.C.T"){
					return true;
				}else{
					return false;
				}
			}else if(3000 <= postCode && postCode < 4000 || 8000 <= postCode && postCode < 9000){
				if(state == "Victoria"){
					return true;
				}else{
					return false;
				}
			}else if(7000 <= postCode && postCode < 8000 || postCode == 7151){
				if(state == "Tasmania"){
					return true;
				}else{
					return false;
				}
			}else if(7000 <= postCode && postCode < 8000){
				if(state == "South Australia"){
					return true;
				}else{
					return false;
				}
			}else if(6000 <= postCode && postCode <= 6979 || 6800 <= postCode && postCode < 7000
					|| postCode == 6798 || postCode == 6799){
				if(state == "Western Australia"){
					return true;
				}else{
					return false;
				}
			}else if(4000 <= postCode && postCode < 5000 || 9000 <= postCode && postCode < 10000 
					|| postCode == 2406){ //2406 lies in the NSW range but is shared by a border town in QLD
				if(state == "Queensland"){
					return true;
				}else{
					return false;
				}
			}
			
		}else{
			return false;
		}
	}
	
}