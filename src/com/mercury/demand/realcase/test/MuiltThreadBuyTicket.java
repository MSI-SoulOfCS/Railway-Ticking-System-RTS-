package com.test;

import java.util.Random;

import com.util.RequestUtil;

public class MuiltThreadBuyTicket {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		final String serverUrl = "http://localhost:8088/Demand1/buyTicket.html";
		final String[] tickets = {
				"beijing#baotou#201505291023",
				"beijing#jining#201505291023",
				"beijing#haerbin#201505291023",
				"beijing#tianjin#201505291023",
				"beijing#xian#201505291023",
				"beijing#weihai#201505291023",
				"beijing#changshun#201505291023",
				"beijing#shijiazhuang#201505291023",
				"beijing#guangzhou#201505291023",
				"beijing#kunming#201505291023"
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
