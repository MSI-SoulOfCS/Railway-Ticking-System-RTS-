package com.redis.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil 
{
	private static final ThreadLocal<Jedis> JEDIS = 
			new ThreadLocal<Jedis>();
	
	private static JedisPoolConfig config = 
			new JedisPoolConfig();
	
	private static JedisPool pool;
	
	static
	{
		
		config.setMaxActive(500);
		config.setMaxIdle(5);
		config.setMaxWait(200*1000);
		
		pool = new JedisPool(config,"127.0.0.1",6379);
	}
	
	public static Jedis getJedis()
	{
		Jedis jedis = JEDIS.get();
		if(jedis == null)
		{
			jedis = pool.getResource();
			JEDIS.set(jedis);
		}
		
		return jedis;
	}
	
	public static void close()
	{
		Jedis jedis = JEDIS.get();
		if(jedis != null)
		{
			pool.returnResource(jedis);
		}
		
		JEDIS.set(null);
	}

}
