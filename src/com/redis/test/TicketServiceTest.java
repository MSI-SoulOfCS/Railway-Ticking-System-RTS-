package com.redis.test;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;


import com.redis.entity.RedisRequest;
import com.redis.entity.RedisTicket;
import com.redis.service.TicketService;
import com.redis.service.impl.TicketServiceImpl;
import com.redis.util.DateFormatUtil;
import com.redis.util.RedisUtil;
import com.redis.util.RelationConverter;

public class TicketServiceTest 
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
			seats[i-1] = "X" + i;
		}
		
		RedisTicket ticket = new RedisTicket();
	
		ticket.setStart("FL,Orlando,Orlando Amtrak Train Station");
		ticket.setDestination("MA,Boston,Haymarket Station");
		ticket.setDate(DateFormatUtil.stringToDateBlur("201506011630"));
		ticket.setActive("true");
		ticket.setPrice("200");
		ticket.setSeats(seats);
		
		service.adminAddTicket(ticket);
	}
	
	public void addTicketConcurrent() 
	{
		
	}
	
	//@Test
	public void testBuyTicket()
	{
		RedisRequest request = new RedisRequest();
		
		request.setUserId("wangwu@gmail.com");
		String ticketKey = "beijing#xining#201505291023";
		service.buyTicket(request, ticketKey);
	}
	
	//@Test
	public void testBuyTicketAndTransaction()
	{
		RedisRequest request = new RedisRequest();
		
		request.setUserId("zhangsan@gmail.com");
		String ticketKey = "beijing#baotou#201505291023";
		RedisRequest returnedRequest = service.buyTicket(request, ticketKey);
		
		String keyAndDate = RelationConverter.requestKeyGenerator(returnedRequest);
		
		try 
		{
			Thread.sleep(60000);
		} 
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		service.transactionComplete(keyAndDate);
		
		/*
		OracleUtil.save(returnedRequest.getUserId(), 
				returnedRequest.getTicketKey(), returnedRequest.getSeatNo());*/
	}
	
	//@Test
	public void testBuyTicketMultiThread()
	{
		//final Random random = new Random();
		
		for(int i=0;i<600;i++)
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
								service.buyTicket(request, "beijing#baotou#201505291023");
						
						if(r != null)
						{
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
	}
	
	//@Test
	public void testRead()
	{
		for(int i=0;i<600;i++)
		{
			Thread t = new Thread(new Runnable(){

				@Override
				public void run() 
				{
					try 
					{
						long result = jedis.llen("beijing#baotou#201505291023-seats");
						System.out.println(result);
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
	
	//@Test
	public void testCartItems()
	{
		/*
		Jedis jedis = RedisUtil.getJedis();
		
		List<String> values = jedis.lrange("requestList", 0, -1);
		
		//System.out.println(values);
		for(String str:values)
		{
			System.out.println(str);
		}
		RedisUtil.close();
		*/
		service.transactionComplete("fangjun1213@gmail.com%20150528224125816");
	}
	
	//@Test
	public void testCartExpire()
	{
		service.cartExpire(2);
	}
	
	//@Test
	public void testTicketExpire()
	{
		service.ticketExpire();
	}
	
	//@Test
	public void testTicketExcpireMultiThread()
	{
		String str = "";
		while(true)
		{
			try 
			{
				Thread.sleep(60000);
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			service.cartExpire(2);
		}
	}
	
	//@Test
	public void testRemoveCartItem()
	{
		RedisRequest request = new RedisRequest();
		request.setUserId("wangwu@gmail.com");
		request.setTicketKey("beijing#baotou#201505291023");
		
		service.removeCartItem(request, "A6");
		
		//String str = RelationConverter.requestKeyGenerator(request);
	}
	
	//@Test
	public void testTransactionClose()
	{
		service.transactionComplete(" ");
	}
	
	//@Test
	public void stringToRedisTicket()
	{ 
		RedisTicket ticket = service.stringToRedisTicket("NY,NewYork,Penn Station"
				+ RedisTicket.TICKET_SPLITTER + "NJ,Hoboken,Hoboken terminal"
				+ RedisTicket.TICKET_SPLITTER + "201505291023");
		
		System.out.println(ticket.getStart());
		System.out.println(ticket.getDestination());
		System.out.println(ticket.getPrice());
		System.out.println(ticket.getActive());
		System.out.println(ticket.isAvailable());
		System.out.println(ticket.getDate());
	
	}
	
	//@Test
	public void testGetAllTicket()
	{
		List<RedisTicket> tickets = service.getAllTicket();
		
		for(RedisTicket t:tickets)
		{
			System.out.println(t.getStart() + ":" + 
					t.getDestination() + ":" + t.getDate() + ":" +
					t.getPrice() + ":" + t.getActive() + ":" + 
					t.isAvailable());
		}
	}
	
	//@Test
	public void testSearchTicket()
	{
		String start = "FL,Orlando,Orlando Amtrak Train Station";
		String destination = "MA,Boston,Haymarket Station";
		Date begin = DateFormatUtil.stringToDateBlur("201505291030");
		Date end = DateFormatUtil.stringToDateBlur("201505291045");
		
		List<RedisTicket> tickets = service.searchTicket(start, destination, begin, end);
		
		for(RedisTicket t:tickets)
		{
			System.out.println(t.getStart() + ":" + 
					t.getDestination() + ":" + t.getDate() + ":" +
					t.getPrice() + ":" + t.getActive() + ":" + 
					t.isAvailable());
		}
	}
}
