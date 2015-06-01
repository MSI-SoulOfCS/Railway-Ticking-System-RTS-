package com.redis.test;

import java.util.Date;

import redis.clients.jedis.Jedis;

import com.redis.util.RedisUtil;

public class TestBuyTicket {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		for(int i=0;i<30000;i++)
		{
			Thread t = new Thread(new Runnable()
			{

				public void run()
				{
					try 
					{
						//Thread.sleep(100);
						//Thread.sleep(1000*random.nextInt(8));
						Jedis jedis = RedisUtil.getJedis();
						
						String seatNo = jedis.rpop("beijing#tianjin#201505281959-seats");
						
						if(seatNo != null)
						{
							String request = Thread.currentThread().getName() + " " + seatNo + " " + new Date();
							System.out.println(request);
							jedis.rpush("requestQueue", request);
						}	
						
						RedisUtil.close();
					}
					catch (Exception e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			
			t.start();
		}

	}

}
