package com.hl7.util;

import java.util.regex.Pattern;

public class StringUtils {
	
	public static String escapeRegex(String escapeChars, String delim)
	{
	 String regex="(?<!"+Pattern.quote(escapeChars)+")"+Pattern.quote(delim);
	 return regex;
	 
	}
	
}
