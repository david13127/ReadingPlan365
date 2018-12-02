package com.thirdleave.readingplan;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import com.thirdleave.readingplan.utils.redis.RedisCmd;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReadingPlan365ApplicationTests {

	@Autowired
	private RedisTemplate redisTemplate;

	@Resource
	private ValueOperations<String, Object> valueOperations;

	@Resource
	private RedisCmd redisService;

	@Test
	public void testObj() throws Exception {
		ValueOperations<String, Object> operations = redisTemplate.opsForValue();
		String key = "username";
		String name = (String) operations.get(key);
		System.out.println(name);
	}

}
