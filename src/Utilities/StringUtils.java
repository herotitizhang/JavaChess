package Utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

	// for validating IP
	private static final String IPADDRESS_PATTERN = 
			"^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
					"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
					"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
					"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	private static Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
	private static Matcher ipMatcher;
	
	
	

	public static boolean validateIP(String ip){	
		if (ip == null || ip.length() == 0) return false;
		ipMatcher = pattern.matcher(ip);
		return ipMatcher.matches();	    	    
	}
	
	public static boolean isInteger(String str) {
	    int size = str.length();

	    for (int i = 0; i < size; i++) {
	        if (!Character.isDigit(str.charAt(i))) {
	            return false;
	        }
	    }

	    return size > 0;
	}
}
