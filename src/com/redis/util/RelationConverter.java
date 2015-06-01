package com.redis.util;

import java.util.Date;

import com.redis.entity.RedisRequest;
import com.redis.entity.RedisTicket;

public class RelationConverter 
{
	/*
	 * to generate a key for ticket info hash
	 * by start station, destination, start time
	 */
	public static String ticketKeyGenerator(RedisTicket ticket)
	{
		return ticket.getStart() + RedisTicket.TICKET_SPLITTER
				+ ticket.getDestination() + RedisTicket.TICKET_SPLITTER
				+ DateFormatUtil.dateToStringBlur(ticket.getDate());
	}
	
	public static String ticketSeatsGenerator(RedisTicket ticket)
	{
		return ticketKeyGenerator(ticket) + RedisTicket.SEAT_SUFFIX;
	}
	
	public static String ticketSeatsGenerator(String ticketKey)
	{
		return ticketKey + RedisTicket.SEAT_SUFFIX;
	}
	
	public static String requestKeyGenerator(RedisRequest request)
	{
		return request.getUserId() + RedisRequest.REQUEST_SPLITER + 
				DateFormatUtil.dateToString(request.getDate());			
	}
	
	public static String cartInfoGenerator(RedisRequest request)
	{
		return request.getTicketKey() + RedisRequest.REQUEST_SPLITER + 
				request.getSeatNo() + RedisRequest.REQUEST_SPLITER + 
				DateFormatUtil.dateToString(request.getDate());
	}

}
