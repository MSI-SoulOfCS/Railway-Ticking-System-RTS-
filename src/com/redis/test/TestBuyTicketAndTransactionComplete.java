package com.redis.test;

import java.util.Random;


import com.redis.entity.RedisRequest;
import com.redis.service.TicketService;
import com.redis.service.impl.TicketServiceImpl;
import com.redis.util.RelationConverter;

public class TestBuyTicketAndTransactionComplete 
{
	public static void main(String[] args) 
	{
		final String[] tickets = {
				"beijing#baotou#201505291023",
				"beijing#tianjin#201505291023",
				"beijing#jining#201505291023",
				"beijing#shenyang#201505291023",
				"beijing#dalian#201505291023",
				"beijing#weihai#201505291023",
				"beijing#qingdao#201505291023",
				"beijing#yinchuan#201505291023",
				"beijing#lanzhou#201505291023",
				"beijing#xining#201505291023"
		};
		final TicketService service = new TicketServiceImpl();
		final Random random = new Random();
		
		/*
		 * 10000 users buy the ticket in 60 seconds
		 */
		
		
		for(int i=0;i<5000;i++)
		{
			Thread t = new Thread(new Runnable(){

				@Override
				public void run() 
				{
					try
					{
						RedisRequest request = new RedisRequest();
						
						request.setUserId(Thread.currentThread().getName() + "@gmail.com");
						
						//System.out.println(Thread.currentThread().getName());
						Thread.sleep(1000 * random.nextInt(60));
						RedisRequest r = 
								service.buyTicket(request, tickets[random.nextInt(10)]);
						
						/*
						 * randomly some one complete transaction
						 */

						if(r == null)
							return;
						
						Thread.sleep(1000*random.nextInt(50));
						service.transactionComplete(RelationConverter.requestKeyGenerator(r));
						
						/*
						OracleUtil.save(r.getUserId(), 
								r.getTicketKey(), r.getSeatNo());*/
				
						System.out.println(r.getUserId() + "---" + 
									r.getTicketKey() + "---" + 
									r.getSeatNo() + "---" + 
									r.getDate());
							
					}
					
					catch(Exception e)
					{
						e.printStackTrace();
					}
					
				}	
			});
			
			t.start();
		}		
		}

}
