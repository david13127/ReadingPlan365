package com.thirdleave.readingplan.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class PropertiesConfig {

	private static URL CONFIG_URL = PropertiesConfig.class.getClassLoader().getResource("application.properties");

	public static String getProperties(String key) {
		Properties properties = new Properties();
		try {
			properties.load(new FileReader(CONFIG_URL.getPath()));
			return properties.getProperty(key);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Integer getIntProperties(String key) {
		Properties properties = new Properties();
		try {
			properties.load(new FileReader(CONFIG_URL.getPath()));
			return Integer.valueOf(properties.getProperty(key));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
