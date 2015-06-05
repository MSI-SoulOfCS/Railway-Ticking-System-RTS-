package com.mercury.demand.realcase.test;

import java.util.Random;

import com.redis.util.RequestUtil;

public class MuiltThreadBuyTicket {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		final String serverUrl = "http://localhost:8080/Demand1/buyTicket.html";
		final String[] tickets = {
				"NY,New York,Penn Station#MA,Boston,North Station#201508010000",
				"NY,New York,Penn Station#MA,Boston,North Station#201508010200",
				"NY,New York,Penn Station#MA,Boston,North Station#201508010400",
				"NY,New York,Penn Station#MA,Boston,North Station#201508010600",
				"NY,New York,Penn Station#MA,Boston,North Station#201508010800",
				"NY,New York,Penn Station#MA,Boston,North Station#201508011000",
				"NY,New York,Penn Station#MA,Boston,North Station#201508011200",
				"NY,New York,Penn Station#MA,Boston,North Station#201508011400",
				"NY,New York,Penn Station#MA,Boston,North Station#201508011600",
				"NY,New York,Penn Station#MA,Boston,North Station#201508011800"
		};
		final Random random = new Random();
		
		for(int i=0;i<1000;i++)
		{
			Thread t = new Thread(new Runnable(){

				@Override
				public void run() 
				{
					
					
					RequestUtil.sendTicketInfoRequest(serverUrl, 
							Thread.currentThread().getName() + "@gmail.com", 
							tickets[random.nextInt(10)]);
					
					System.out.println(tickets[random.nextInt(10)]);
					
				}			
			});
			
			t.start();
		}

	}

}
