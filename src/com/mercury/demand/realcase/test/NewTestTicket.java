package com.mercury.demand.realcase.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.redis.entity.RedisTicket;
import com.redis.service.TicketService;
import com.redis.service.impl.TicketServiceImpl;
import com.redis.util.RelationConverter;

public class NewTestTicket {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Random rand = new Random();
		TicketService ticketService = new TicketServiceImpl();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try 
		{
			for(int i = 0 ; i < 20 ; i+=2) {
				String time = "2015-08-01 "+ (i < 10 ? "0" : "") + i + ":00"; 
				Date date = dateFormat.parse(time);
				System.out.println(date);
				RedisTicket ticket = new RedisTicket();
				ticket.setStart("NY,New York,Penn Station");
				ticket.setDestination("MA,Boston,North Station");
				ticket.setDate(date);
				ticket.setPrice(Double.valueOf(rand.nextInt(100) + 50).toString());
				ticket.setAmount(30);
				ticket.setAvailable(true);
				ticket.setActive("true");
				//A=65 Z=90
				String seat = "" + ((char)(i/2+65));
				ticket.setSeats(RelationConverter.seatGenerator(seat, 30));
				
				ticketService.adminAddTicket(ticket);
			}
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
