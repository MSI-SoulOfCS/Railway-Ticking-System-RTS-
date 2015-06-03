package com.redis.test;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;

import com.mercury.demand.service.TransactionService;
import com.redis.entity.RedisRequest;
import com.redis.entity.RedisTicket;
import com.redis.service.TicketService;
import com.redis.service.impl.TicketServiceImpl;
import com.redis.util.DateFormatUtil;
import com.redis.util.RedisUtil;
import com.redis.util.RelationConverter;

public class TestTransactionPool1 
{
	private TicketService service;
	private Jedis jedis;

	@Before
	public void setUp() throws Exception 
	{
		service = new TicketServiceImpl();
		jedis = RedisUtil.getJedis();
	}

	@Test
	public void addTicket() 
	{
		String[] seats = new String[100];
		
		for(int i=1;i<=100;i++)
		{
			seats[i-1] = "B" + i;
		}
		
		RedisTicket ticket = new RedisTicket();
	
		ticket.setStart("beijing");
		ticket.setDestination("baotou");
		ticket.setDate(DateFormatUtil.stringToDateBlur("201505291023"));
		ticket.setActive("true");
		ticket.setAmount(30);
		ticket.setPrice("200");
		ticket.setSeats(seats);
		
		service.adminAddTicket(ticket);
	}
	
	//@Test
	public void testBuyTicket()
	{
		RedisRequest request = new RedisRequest();
		
		request.setUserId("wangwu@gmail.com");
		String ticketKey = "beijing#nanjing#201505291023";
		service.buyTicket(request, ticketKey);
	}
	
	//@Test
	public void testTransaction()
	{
		service.transactionCompleteWithPool("wangwu@gmail.com%20150602103424829");
	}
	
	//@Test
	public void testBuyTicketMultiThread()
	{
			//final Random random = new Random();
			
			for(int i=0;i<1500;i++)
			{
				Thread t = new Thread(new Runnable(){

					@Override
					public void run() 
					{
						try 
						{
							RedisRequest request = new RedisRequest();
							
							request.setUserId(Thread.currentThread().getName());
							
							//System.out.println(Thread.currentThread().getName());
							RedisRequest r = 
									service.buyTicket(request, "beijing#nanjing#201505291023");
							
							
							if(r != null)
							{
								//Thread.sleep(1000*random.nextInt(10));
								service.transactionCompleteWithPool(RelationConverter.requestKeyGenerator(r));
								System.out.println(r.getUserId() + "---" + 
										r.getTicketKey() + "---" + 
										r.getSeatNo() + "---" + 
										r.getDate());
							}	
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
			
			/*
			while(true)
			{
				try 
				{
					Thread.sleep(20000);
				} 
				catch (InterruptedException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//TransactionService.completeTransaction();
			}*/
			
		}
	
	//@Test
	public void testBuyTicketMultiThreadByMoreTicket()
	{
			final Random random = new Random();
			
			final String[] tickets = {
					"beijing#baotou#201505291023",
					"beijing#jining#201505291023",
					"beijing#haerbin#201505291023",
					"beijing#tianjin#201505291023",
					"beijing#xian#201505291023"
			};
			
			for(int i=0;i<1500;i++)
			{
				Thread t = new Thread(new Runnable(){

					@Override
					public void run() 
					{
						try 
						{
							RedisRequest request = new RedisRequest();
							
							request.setUserId(Thread.currentThread().getName());
							
							//System.out.println(Thread.currentThread().getName());
							RedisRequest r = 
									service.buyTicket(request, tickets[random.nextInt(5)]);
							
							
							if(r != null)
							{
								//Thread.sleep(1000*random.nextInt(10));
								service.transactionCompleteWithPool(RelationConverter.requestKeyGenerator(r));
								System.out.println(r.getUserId() + "---" + 
										r.getTicketKey() + "---" + 
										r.getSeatNo() + "---" + 
										r.getDate());
							}	
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
			
			/*
			while(true)
			{
				try 
				{
					Thread.sleep(20000);
				} 
				catch (InterruptedException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//TransactionService.completeTransaction();
			}*/
			
		}

}
