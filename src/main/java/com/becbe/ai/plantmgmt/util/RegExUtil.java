package com.archius.cosmos.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author Thulasiram
 *
 */
public class RegExUtil {
	// private static String EXTRACT_KEY_VALUE =
	// "((\\d{2}):(.+?(?=(?:,\\d)|$)))+";
	private static String EXTRACT_KEY_VALUE = "(\\w+)=([^\\s]+)";
	private static final Logger logger = LoggerFactory.getLogger(RegExUtil.class);
	/*
	 * public String Pressure; // =-0.0011000000000000001, public String
	 * Speed_RPM;// = 200; public String Flowrate;// = 0; public String
	 * cycle_no;// = 128; public String engine_no;// = 91; public String
	 * test_rf;
	 */

	private static RegExUtil instance;

	private RegExUtil() {

	}

	public static RegExUtil getInstance() {
		if (instance == null) {
			instance = new RegExUtil();
		}
		return instance;
	}

	public Map<String, String> fetchKeyValuePair(String message) {
		Map<String, String> keyValueMap = new HashMap<String, String>();
		Pattern p = Pattern.compile(EXTRACT_KEY_VALUE);
		Matcher m = p.matcher(message);
		while (m.find()) {
			logger.info("key and vlue pairs " + m.group(1) + " --> " + m.group(2));
			keyValueMap.put(m.group(1), m.group(2));
		}
		return keyValueMap;
	}
	/*
	 * public static void main(String[] args) { String input =
	 * "01:aa,bb,02:cc,03:dd,04:ee"; String sim =
	 * "Re-simulate engine_no:14 with Pressure=45 , Flowrate=40  Speed_RPM =200 and	 let	me	know	the	results?"
	 * ; String exInput ="joe:Look over there it's a shark!,sam:I like fish"; //
	 * | group 1 // || group 2: 2 digit // || | separator // || | | group 3: any
	 * character reluctantly quantified... // || | | | ... followed by ... // ||
	 * | | | | ... comma and next digit as // || | | | | non-capturing group...
	 * // || | | | | | ... or... // || | | | | || ... end of input // || | | | |
	 * || | multiple matches in input Pattern p =
	 * Pattern.compile(EXTRACT_KEY_VALUE); Matcher m = p.matcher(sim); while
	 * (m.find()) { System.out.println(m.group(1) + " --> " + m.group(2)); } }
	 */

}
