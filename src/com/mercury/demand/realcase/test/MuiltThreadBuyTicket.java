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
				"NY,New York,Penn Station#MA,Boston,North Station#201506180708",
				"NY,New York,Penn Station#FL,Orlando,SunRail Station#201506191313",
				"NY,New York,Penn Station#GA,Appling County,Apple Station#201506201617",
				"NY,New York,Penn Station#NY,NewYork,Yonkers Amtrak#201506301113",
				"NY,New York,Penn Station#TX,Amarillo,Rick Husband Amarillo station#201506300111",
				"NY,New York,Penn Station#CA,Los Angeles,Pomona-North Metrolink Station#201506301406",
				"NY,New York,Penn Station#NC,Winston-Salem,Winston-Salem Amtrak Train Station#201506160703",
				"NY,New York,Penn Station#NJ,Princeton,Princeton Junction#201506161403",
				"NY,New York,Penn Station#FL,Orlando,Church Street Station#201506161403",
				"NY,New York,Penn Station#MA,Boston,Back Bay Station#201506161403"
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
