package com.redis.service;

import java.util.Date;
import java.util.List;

import com.redis.entity.RedisRequest;
import com.redis.entity.RedisTicket;



public interface TicketService 
{
	/*
	 * admin release a ticket:
	 * 1. register the ticket in the ticket pool
	 * 	  value is ticket key(it can find ticket seat and ticket info)
	 * 	  and time(it can determine the ticket is expired or not)
	 * 2. set a hash map for ticket info: 
	 * 	  it includes: price and active
	 * 3. set a list for ticket seats 
	 * 	  and the seats name can be converted by ticket key
	 */
	public void adminAddTicket(RedisTicket ticket);
	
	/*
	 * 1. add a request to request queue
	 *    value is user_id and date specify to milli-second
	 * 2. add record to user_id list
	 * 3. seats decr 1 and add it to user queue
	 */
	public RedisRequest buyTicket(RedisRequest request,String ticketKey);
	
	public RedisRequest buyTicketSimple(RedisRequest request,String ticketKey);
	
	public void transactionComplete(String keyAndDate);
	
	/*
	 * complete transaction with transaction pool
	 */
	public void transactionCompleteWithPool(String keyAndDate);
	
	/*
	 * Quartz will use this this method to iterator the 
	 * request list 
	 */
	public void cartExpire(int expireTimeByMinute);
	
	public void removeCartItem(RedisRequest request,String seatNo);
	
	public void ticketExpire();
	public void returnBackTicket(RedisTicket ticket,String seatNo);
	
	/*
	 * search ticket by period of time
	 */
	public List<RedisTicket> searchTicket(String start,String destination,Date begin,Date end);
	
	//public List<RedisTicket> getTicketByStatAndDesi(String start,String destination);
	
	/*
	 * get all of the ticket
	 */
	public List<RedisTicket> getAllTicket();
	
	/*
	 * parse string to RedisTicket object
	 */
	public RedisTicket stringToRedisTicket(String ticketKey);
}
