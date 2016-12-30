/**
 * 
 */
package com.archius.cosmos.job;

import java.util.Map;

import com.archius.cosmos.EngineDAO;
import com.archius.cosmos.model.Engine;

/**
 * @author Thulasiram
 *
 */
public class EngineUpdateJob extends Thread {
	
	public String response;
	String message, key, intent, queryParams;
	Map<String,Engine> engineMap;
	
	/**
	 * @return the response
	 */
	public String getResponse() {
		return response;
	}

	/**
	 * @param response the response to set
	 */
	public void setResponse(String response) {
		this.response = response;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the intent
	 */
	public String getIntent() {
		return intent;
	}

	/**
	 * @param intent the intent to set
	 */
	public void setIntent(String intent) {
		this.intent = intent;
	}

	/**
	 * @return the queryParams
	 */
	public String getQueryParams() {
		return queryParams;
	}

	/**
	 * @param queryParams the queryParams to set
	 */
	public void setQueryParams(String queryParams) {
		this.queryParams = queryParams;
	}

	/**
	 * @return the engineMap
	 */
	public Map<String, Engine> getEngineMap() {
		return engineMap;
	}

	/**
	 * @param engineMap the engineMap to set
	 */
	public void setEngineMap(Map<String, Engine> engineMap) {
		this.engineMap = engineMap;
	}

	public void run(){
		EngineDAO dao = new EngineDAO();
		response = dao.updateEngine(message, key, intent, queryParams, engineMap);
	}
}
