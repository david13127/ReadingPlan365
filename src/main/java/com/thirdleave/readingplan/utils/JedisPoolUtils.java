package com.thirdleave.readingplan.utils;

import javax.annotation.PostConstruct;

import com.thirdleave.readingplan.config.PropertiesConfig;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolUtils {

	private static volatile JedisPool jedisPool;

	private static String host = PropertiesConfig.getProperties("spring.redis.host");
	private static String password = PropertiesConfig.getProperties("spring.redis.password");
	private static int port = PropertiesConfig.getIntProperties("spring.redis.port");
	private static int timeout = PropertiesConfig.getIntProperties("spring.redis.timeout");
	private static int maxIdle = PropertiesConfig.getIntProperties("spring.redis.pool.max-idle");
	private static long maxWaitMillis = PropertiesConfig.getIntProperties("spring.redis.pool.max-wait");

	private static volatile JedisPoolUtils INSTANCE;

	private JedisPoolUtils() {
	}

	/**
	 * 获取实例
	 */
	public static JedisPoolUtils getInstance() {
		if (INSTANCE == null) {
			synchronized (JedisPoolUtils.class) {
				if (INSTANCE == null) {
					INSTANCE = new JedisPoolUtils();
				}
			}
		}
		if (jedisPool == null) {
			createJedisPool();
		}
		return INSTANCE;
	}

	@PostConstruct
	private static synchronized JedisPool createJedisPool() {
		if (jedisPool == null) {
			JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
			jedisPoolConfig.setMaxIdle(maxIdle);
			jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
			jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password);
		}
		return jedisPool;
	}

	public JedisPool getJedisPool() {
		return jedisPool;
	}
}