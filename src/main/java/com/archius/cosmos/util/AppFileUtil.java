package com.archius.cosmos.util;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
/**
 * @author Thulasiram
 *
 */
public class AppFileUtil {

	/*
	 * public static void main(String[] args) { FileUtil obj = new FileUtil();
	 * System.out.println(obj.getFile("data/barrels.json")); }
	 */

	private static AppFileUtil instance;

	private AppFileUtil() {

	}

	public static AppFileUtil getInstance() {
		if (instance == null) {
			instance = new AppFileUtil();
		}
		return instance;
	}

	public String getFile(String fileName) {

		fileName = "data/" + fileName + ".json";
		StringBuilder result = new StringBuilder("");
		// Get file from resources folder
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());

		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				result.append(line).append("\n");
			}
			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return result.toString();

	}

}