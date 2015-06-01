package com.redis.test;

import java.util.Random;


import com.redis.service.TicketService;
import com.redis.service.impl.TicketServiceImpl;

public class Simulate1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		final String[] tickets = {
				"beijing#haerbin#201505291022",
				"beijing#tianjin#201505291021",
				"beijing#baotou#201505291023",
				"beijing#shijiazhuang#201505291020"
		};
		final TicketService service = new TicketServiceImpl();
		final Random random = new Random();
		
		/*
		 * 10000 users buy the ticket in 60 seconds
		 */
		
		/*
		for(int i=0;i<10000;i++)
		{
			Thread t = new Thread(new Runnable(){

				@Override
				public void run() 
				{
					try
					{
						Request request = new Request();
						
						request.setUserId(Thread.currentThread().getName() + "@gamil.com");
						
						//System.out.println(Thread.currentThread().getName());
						Thread.sleep(1000 * random.nextInt(60));
						Request r = 
								service.buyTicket(request, tickets[random.nextInt(4)]);
						
						if(r != null)
						{
							System.out.println(r.getUserId() + "---" + 
									r.getTicketKey() + "---" + 
									r.getSeatNo() + "---" + 
									r.getDate());
							
						}
					}
					
					catch(Exception e)
					{
						e.printStackTrace();
					}
					
				}	
			});
			
			t.start();
		}*/
		
		while(true)
		{
			try 
			{
				Thread.sleep(6000);
				
				Thread t1 = new Thread(new Runnable(){

					@Override
					public void run() 
					{
						service.cartExpire(3);
					}			
				});
				
				Thread t2 = new Thread(new Runnable(){

					@Override
					public void run() 
					{
						service.ticketExpire();	
					}
					
				});
				
				t1.start();
				t2.start();
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
