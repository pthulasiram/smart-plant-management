package com.archius.cosmos.service;

import java.util.Map;
/**
 * @author Thulasiram
 *
 */
public interface NLPService {

	public String exchangeChat(String message);
	
	public Map<String,String> getNlpOdataMap();

}
