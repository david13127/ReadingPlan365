package com.thirdleave.readingplan.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志
 */
public class LogUtil {

	private static Logger Log = LoggerFactory.getLogger(LogUtil.class);

	public static <T> Logger getLogger(Class<T> clazz) {
		return LoggerFactory.getLogger(clazz);
	}

	public static void trace(String msg) {
		Log.trace(msg);
	}

	public static void debug(String msg) {
		Log.debug(msg);
	}

	public static void info(String msg) {
		Log.info(msg);
	}

	public static void warn(String msg) {
		Log.warn(msg);
	}

	public static void error(String msg) {
		Log.error(msg);
	}
}
