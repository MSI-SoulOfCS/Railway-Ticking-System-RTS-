package com.redis.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import redis.clients.jedis.Jedis;

import com.redis.util.RedisUtil;

public class Test111 {

	@Test
	public void test() {
		Jedis jedis = RedisUtil.getJedis();
		
		List<String> values = jedis.lrange("zhangsan", 0, -1);
		
		System.out.println(values.size());
	}

}
