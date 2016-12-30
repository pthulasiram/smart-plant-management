package com.archius.cosmos.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author Thulasiram
 *
 */
public class ClassUtil {

	private static final Logger logger = LoggerFactory.getLogger(ClassUtil.class);

	private static ClassUtil instance;

	private ClassUtil() {

	}

	public static ClassUtil getInstance() {
		if (instance == null) {
			instance = new ClassUtil();
		}
		return instance;
	}

	public List<String> listInstanceFieldsByClass(Object obj) throws Exception {
		Class<?> objClass = obj.getClass();
		List<String> list = new ArrayList<String>();
		Field[] fields = objClass.getFields();
		for (Field field : fields) {
			String name = field.getName();
			Object value = field.get(obj);
			list.add(name);
		}
		return list;
	}
}
