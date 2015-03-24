package Utilities;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
public class IPValidator{

	private static final String IPADDRESS_PATTERN = 
			"^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
					"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
					"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
					"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

	private static Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
	private static Matcher ipMatcher;

	public static boolean validate(String ip){	
		if (ip == null || ip.length() == 0) return false;
		ipMatcher = pattern.matcher(ip);
		return ipMatcher.matches();	    	    
	}
}