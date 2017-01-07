package com.archius.cosmos.util;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.archius.cosmos.model.Engine;
import com.archius.cosmos.model.UtilizationVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
/**
 * @author Thulasiram
 *
 */
public class JSONToObjectUtil {
	private static final Logger logger = LoggerFactory.getLogger(JSONToObjectUtil.class);

	private static JSONToObjectUtil instance;

	private JSONToObjectUtil() {

	}

	public static JSONToObjectUtil getInstance() {
		if (instance == null) {
			instance = new JSONToObjectUtil();
		}
		return instance;
	}

	public <T> List<T> convertJsonToObject(String jsonData, Class className) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			TypeFactory typeFactory = objectMapper.getTypeFactory();
			CollectionType collectionType = typeFactory.constructCollectionType(List.class, className);
			return objectMapper.readValue(jsonData, collectionType);
		} catch (IOException e) {
			logger.error("Failed to parse JSON to Object " + e.getMessage());
		}
		return null;
	}
	
}