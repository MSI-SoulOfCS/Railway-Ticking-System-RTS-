package com.mercury.demand.service;

import com.redis.service.TicketService;
import com.redis.service.impl.TicketServiceImpl;

public class RequestManager 
{
	/*
	 * if the item stay at cart over 2 minutes 
	 * it will be removed 
	 */
	private TicketService service;
	
	public RequestManager() {
		System.out.println("New Request Manager");
		service = new TicketServiceImpl();
	}
	
	public void removeExpiredRequest()
	{	
		this.service.cartExpire(2);
		
		System.out.println("request manager running");
	}

}
