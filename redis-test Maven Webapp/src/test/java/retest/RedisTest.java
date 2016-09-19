/**
 * 
 */
package retest;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <p><b>Description:</b> </p>
 * <p><b>Date:</b> 2016年9月19日下午11:56:30</p>
 * <p><b>Copyright:</b> Copyright (c) 2016</p>
 * <p><b>Email:</b> jstxwh868@126.com</p>
 * @author Saka
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-data-redis.xml")
@SuppressWarnings("all")
public class RedisTest {
	@Autowired
	private RedisTemplate redis;
	@Test
	public void testPing(){
		ValueOperations<String,String> value = redis.opsForValue();
		value.set("test", "Hello Redis");
	}
	@Test
	public void testValue(){
		ValueOperations<String,String> value = redis.opsForValue();
		System.out.println(value.get("test"));
	}
	@Test
	public void testDelete(){
		Set<String> set = redis.keys("*");
		System.out.println(set.size());
	}
}
