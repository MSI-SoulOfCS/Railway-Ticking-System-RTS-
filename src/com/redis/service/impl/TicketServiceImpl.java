package com.redis.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import redis.clients.jedis.Jedis;

import com.redis.entity.CartItem;
import com.redis.entity.RedisRequest;
import com.redis.entity.RedisTicket;
import com.redis.entity.RedisTransaction;
import com.redis.service.TicketService;
import com.redis.util.DateFormatUtil;
import com.redis.util.RedisUtil;
import com.redis.util.RelationConverter;

public class TicketServiceImpl implements TicketService
{
	
	@Override
	public void adminAddTicket(RedisTicket ticket) 
	{
		Jedis jedis = RedisUtil.getJedis();
		String key = RelationConverter.ticketKeyGenerator(ticket);
		/*
		 * add value to ticket pool
		 */
		jedis.rpush(RedisTicket.TICKET_POOL_NAME,key);
		
		/*
		 * add ticket info
		 */
		jedis.hset(key, RedisTicket.ACTIVE, ticket.getActive());
		jedis.hset(key, RedisTicket.PRICE, ticket.getPrice());
		jedis.hset(key, RedisTicket.AMOUNT, Integer.valueOf(ticket.getAmount()).toString());
		
		/*
		 * set the seats id for seats pool
		 */
		String[] seats = ticket.getSeats();
		for(String seat:seats)
		{		
			jedis.rpush(
					RelationConverter.ticketSeatsGenerator(ticket), seat);
		}
		RedisUtil.close();
	}

	
	@Override
	public RedisRequest buyTicket(RedisRequest request, String ticketKey) 
	{
		Jedis jedis = RedisUtil.getJedis();
		String seatsKey = RelationConverter.ticketSeatsGenerator(ticketKey);
		
		long isAvailable = 
				jedis.llen(seatsKey);
		
		
		if( isAvailable <= 0 )
		{
			return null;
		}
		
		/*
		 * get a unique seat number from seats list
		 */
		String seatNo =  jedis.lpop(seatsKey);
		
		if(seatNo == null)
		{
			return null;
		}
		
		/*
		 * set value for request
		 */
	
		request.setSeatNo(seatNo);
		request.setDate(new Date());
		request.setTicketKey(ticketKey);

		
		/*
		 * put the request to request queue
		 */

		jedis.rpush(RedisRequest.REQUEST_LIST_NAME, 
				RelationConverter.requestKeyGenerator(request));
		
		/*
		 * push a record in cart by user_id
		 */
		
		jedis.rpush(request.getUserId(), 
				RelationConverter.cartInfoGenerator(request));
		RedisUtil.close();
		return request;

		
	}

	@Override
	public void transactionComplete(String keyAndDate) 
	{
		Jedis jedis = RedisUtil.getJedis();
		
		/*
		 * remove the record from request list
		 */
		jedis.lrem(RedisRequest.REQUEST_LIST_NAME, 1, keyAndDate);
		
		String[] arr = keyAndDate.split(RedisRequest.REQUEST_SPLITER);
		String key = arr[0];
		String date = arr[1];
		
		List<String> values = jedis.lrange(key, 0, -1);
		
		for(String str:values)
		{
			if(str.indexOf(date) != -1)
			{
				jedis.lrem(key, 1, str);
				break;
			}
		}

		RedisUtil.close();
	}

	@Override
	public void cartExpire(int expireTimeByMinute) 
	{
		Jedis jedis = RedisUtil.getJedis();
		
		/*
		 * get all of the values in request list
		 */
		List<String> values = 
				jedis.lrange(RedisRequest.REQUEST_LIST_NAME, 0, -1);
		
		/*
		 * go through the values, parse the values into 
		 * user_id, time 
		 */
		for(String str:values)
		{
			String[] arr = str.split(RedisRequest.REQUEST_SPLITER);
			String key = arr[0];
			Date date = DateFormatUtil.stringToDate(arr[1]);
			Date rightnow = new Date();
			
			long diff = rightnow.getTime() - date.getTime();
			if(diff >= 60000*expireTimeByMinute)
			{
				jedis.lrem(RedisRequest.REQUEST_LIST_NAME, 1, str);
				
				List<String> vals = jedis.lrange(key, 0, -1);
				for(String s:vals)
				{
					String[] ar = s.split(RedisRequest.REQUEST_SPLITER);
					
					if(s.indexOf(arr[1]) != -1)
					{
						jedis.lrem(key, 1, s);
						jedis.rpush(
								RelationConverter.ticketSeatsGenerator(ar[0]), ar[1]);
						System.out.println(ar[1]);
					}	
				}
			}
		}
		
		RedisUtil.close();
	}

	@Override
	public void ticketExpire() 
	{
		
		Jedis jedis = RedisUtil.getJedis();
		
		List<String> values = 
				jedis.lrange(RedisTicket.TICKET_POOL_NAME, 0, -1);
		
		for(String str:values)
		{
			String[] arr = str.split(RedisTicket.TICKET_SPLITTER);
			
			Date date = DateFormatUtil.stringToDateBlur(arr[2]);
			Date rightnow = new Date();
			
			if((rightnow.getTime() - date.getTime())>=0)
			{
				jedis.lrem(RedisTicket.TICKET_POOL_NAME, 1, str);
				jedis.del(str);
				jedis.del(RelationConverter.ticketSeatsGenerator(str));
			}
		}
		RedisUtil.close();
	}

	@Override
	public void returnBackTicket(RedisTicket ticket, String seatNo)
	{
		Jedis jedis = RedisUtil.getJedis();
		
		if(jedis.exists(RelationConverter.ticketKeyGenerator(ticket)))
		{
			String seats = RelationConverter.ticketSeatsGenerator(ticket);
			jedis.rpush(seats, seatNo);
		}
		
		RedisUtil.close();
	}


	@Override
	public RedisRequest buyTicketSimple(RedisRequest request, String ticketKey) 
	{
		Jedis jedis = RedisUtil.getJedis();
		String seatsKey = 
				RelationConverter.ticketSeatsGenerator(ticketKey);
		String seatNo =  jedis.lpop(seatsKey);
		
		if(seatNo == null)
			return null;
		
		jedis.rpush(RedisRequest.REQUEST_LIST_NAME, 
				RelationConverter.requestKeyGenerator(request));
		
		request.setSeatNo(seatNo);
		request.setDate(new Date());
		request.setTicketKey(ticketKey);
		
		jedis.rpush(request.getUserId(), 
				RelationConverter.cartInfoGenerator(request));
		//request.se
		
		RedisUtil.close();
		return null;
		
	}

	/*
	 * 
	 * request assign values for fileds: userId, date,ticketId
	 */
	@Override
	public void removeCartItem(RedisRequest request,String seatNo) 
	{
		Jedis jedis = RedisUtil.getJedis();
		
		List<String> values = jedis.lrange(request.getUserId(), 0, -1);
		
		for(String str:values)
		{
			if(str.indexOf(seatNo) != -1)
			{
				String[] arr = str.split(RedisRequest.REQUEST_SPLITER);
				Date date = DateFormatUtil.stringToDate(arr[2]);
				request.setDate(date);
				
				jedis.lrem(RedisRequest.REQUEST_LIST_NAME, 
						1, RelationConverter.requestKeyGenerator(request));
				jedis.lrem(request.getUserId(), 1, str);
				jedis.rpush(
						RelationConverter.ticketSeatsGenerator(request.getTicketKey()),seatNo);
				break;
			}
		}
		RedisUtil.close();
	}
	
	@Override
	public void removeCartItem(String requestKey) 
	{
		Jedis jedis = RedisUtil.getJedis();
		
		String[] arr = requestKey.split(RedisRequest.REQUEST_SPLITER);
		String userId = arr[0];
		String addTime = arr[1];
		
		List<String> values = jedis.lrange(userId, 0, -1);
		
		for(String str:values)
		{
			String[] ar = str.split(RedisRequest.REQUEST_SPLITER);
			String ticketId = ar[0];
			String seatNo = ar[1];
			
			if(str.indexOf(addTime) != -1)
			{				
				jedis.lrem(RedisRequest.REQUEST_LIST_NAME, 
						1, requestKey);
				
				jedis.lrem(userId, 1, str);
				jedis.rpush(RelationConverter.ticketSeatsGenerator(ticketId),seatNo);
				break;
			}
		}
		RedisUtil.close();	
	}


	@Override
	public List<RedisTicket> searchTicket(String start, String destination,
			Date begin, Date end) 
	{
		List<RedisTicket> tickets = getAllTicket();
		
		Iterator<RedisTicket> iter = tickets.iterator();
		while(iter.hasNext())
		{
			RedisTicket ticket = iter.next();
			Date ticketDate = ticket.getDate();
			
			if(!(ticketDate.getTime() >= begin.getTime() 
					&& ticketDate.getTime()<=end.getTime()))
			{
				iter.remove();
				continue;
			}
			
			if(!(ticket.getStart().equals(start) 
					&& ticket.getDestination().equals(destination)))
			{
				iter.remove();
				continue;
			}
		}
		
		return tickets;
	}


	@Override
	public List<RedisTicket> getAllTicket() 
	{
		Jedis jedis = RedisUtil.getJedis();
		
		List<String> result = jedis.lrange(RedisTicket.TICKET_POOL_NAME, 0, -1); 
		List<RedisTicket> tickets = new ArrayList<RedisTicket>();
		
		for(String str:result)
		{
			tickets.add(stringToRedisTicket(str));
		}
		
		return tickets;
	}


	@Override
	public RedisTicket stringToRedisTicket(String ticketKey) 
	{
		Jedis jedis = RedisUtil.getJedis();
		RedisTicket ticket = null;
		
		if(jedis.exists(ticketKey))
		{
			ticket = new RedisTicket();
			String[] arr = ticketKey.split(RedisTicket.TICKET_SPLITTER);
			ticket.setStart(arr[0]);
			ticket.setDestination(arr[1]);
			ticket.setDate(DateFormatUtil.stringToDateBlur(arr[2]));
			
			int amount = Integer.parseInt(jedis.hget(ticketKey, RedisTicket.AMOUNT));
			boolean available = false;
			long avaiNumber = jedis.llen(RelationConverter.ticketSeatsGenerator(ticketKey));
			
			if(avaiNumber >0)
			{
				available = true;
			}
			
			ticket.setAmount(amount);
			ticket.setAvaiNumber(avaiNumber);
			ticket.setAvailable(available);
			ticket.setActive(jedis.hget(ticketKey, RedisTicket.ACTIVE));
			ticket.setPrice(jedis.hget(ticketKey, RedisTicket.PRICE));
		}
		
		return ticket;
	}


	@Override
	public void transactionCompleteWithPool(String keyAndDate) 
	{
		Jedis jedis = RedisUtil.getJedis();
		
		/*
		 * remove the record from request list
		 * keyAndDate: userId + splitter + request time
		 */
		jedis.lrem(RedisRequest.REQUEST_LIST_NAME, 1, keyAndDate);
		
		String[] arr = keyAndDate.split(RedisRequest.REQUEST_SPLITER);
		String key = arr[0];
		String date = arr[1];
		
		List<String> values = jedis.lrange(key, 0, -1);
		
		String ticketId = "";
		String seatNo = "";
		
		/*
		 * remove the item that make transaction from cart 
		 */
		for(String str:values)
		{
			if(str.indexOf(date) != -1)
			{
				/*
				 * get request info
				 */
				String[] tempArr = str.split(RedisRequest.REQUEST_SPLITER);
				ticketId = tempArr[0];
				seatNo = tempArr[1];
				
				jedis.lrem(key, 1, str);
				break;
			}
		}
		
		/*
		 * add the transaction to transaction pool
		 * userid | ticketid | seatNo| date 
		 */
		StringBuffer buffer = new StringBuffer();
		buffer.append(key);
		buffer.append(RedisTransaction.TRANSACTION_INFO_SPLITTER);
		buffer.append(ticketId);
		buffer.append(RedisTransaction.TRANSACTION_INFO_SPLITTER);
		buffer.append(seatNo);
		buffer.append(RedisTransaction.TRANSACTION_INFO_SPLITTER);
		buffer.append(DateFormatUtil.dateToString(new Date()));
		
		jedis.rpush(RedisTransaction.TRANSACTION_POOL_NAME, buffer.toString());
		
		RedisUtil.close();
		
	}


	@Override
	public List<CartItem> getCartItem(String userId) 
	{
		Jedis jedis = RedisUtil.getJedis();
		List<String> values = jedis.lrange(userId, 0, -1);
		
		if(values.size() == 0)
			return null;
		
		List<CartItem> items = new ArrayList<CartItem>();
		
		for(String str : values)
		{
			CartItem item = new CartItem();
			
			String[] arr = str.split(RedisRequest.REQUEST_SPLITER);
			
			String ticketId = arr[0];
			String seatNo = arr[1];
			String time = arr[2];
			
			String[] ar = ticketId.split(RedisTicket.TICKET_SPLITTER);
			String from = ar[0];
			String to = ar[1];
			String start = ar[2];
			
			String price = jedis.hget(ticketId, RedisTicket.PRICE);
			
			/*
			 *  private String itemId;
				private String from;
				private String to;
				private Date start;
				private String seatNo;
				private double price;
				private Date addTime;
			 */
			
			item.setItemId(RelationConverter
					.requestKeyGeneratorByIdAndTime(userId, time));
			item.setFrom(from);
			item.setTo(to);
			item.setStart(DateFormatUtil.stringToDateBlur(start));
			item.setSeatNo(seatNo);
			item.setPrice(Double.parseDouble(price));
			item.setAddTime(DateFormatUtil.stringToDate(time));
	
			items.add(item);
		}
		
		RedisUtil.close();
		return items;
	}




	@Override
	public void enableTicket(String ticketKey) 
	{
		Jedis jedis = RedisUtil.getJedis();
		
		jedis.hset(ticketKey, RedisTicket.ACTIVE, "true");
		
		RedisUtil.close();	
	}


	@Override
	public void disableTicket(String ticketKey) 
	{
		Jedis jedis = RedisUtil.getJedis();
		
		jedis.hset(ticketKey, RedisTicket.ACTIVE, "false");
		
		RedisUtil.close();		
	}


	@Override
	public void removeTicket(String ticketKey) 
	{
		Jedis jedis = RedisUtil.getJedis();
		
		jedis.lrem(RedisTicket.TICKET_POOL_NAME, 1, ticketKey);
		jedis.del(ticketKey);
		jedis.del(RelationConverter.ticketSeatsGenerator(ticketKey));
		
		RedisUtil.close();
	}


	@Override
	public RedisTransaction stringToTransaction(String transactionInfo) 
	{
		RedisTransaction transaction = new RedisTransaction();
		
		String[] arr = transactionInfo.split(RedisTransaction.TRANSACTION_INFO_SPLITTER);
		
		transaction.setUserId(arr[0]);
		transaction.setTicketId(arr[1]);
		transaction.setSeatNo(arr[2]);
		transaction.setDate(DateFormatUtil.stringToDate(arr[3]));
		
		return transaction;
	}


	@Override
	public List<RedisTransaction> getAllTransaction() 
	{
		Jedis jedis = RedisUtil.getJedis();
		
		List<String> values = jedis.lrange(RedisTransaction.TRANSACTION_POOL_NAME, 0, -1);
		
		if(values.size() == 0)
			return null;

		List<RedisTransaction> trans = new ArrayList<RedisTransaction>();
		
		for(String str:values)
		{
	
			RedisTransaction tran = stringToTransaction(str);
			trans.add(tran);	
			System.out.println(str);
	
		}
		
		RedisUtil.close();
		
		return trans;
	}


	@Override
	public List<RedisTransaction> getAllTransactionAndDelete() 
	{
		Jedis jedis = RedisUtil.getJedis();
		
		List<String> values = jedis.lrange(RedisTransaction.TRANSACTION_POOL_NAME, 0, -1);
		
		if(values.size() == 0)
			return null;

		List<RedisTransaction> trans = new ArrayList<RedisTransaction>();
		
		for(String str:values)
		{
	
			RedisTransaction tran = stringToTransaction(str);
			trans.add(tran);		
			jedis.lrem(RedisTransaction.TRANSACTION_POOL_NAME, 1, str);
		}
		
		RedisUtil.close();
		
		return trans;
	}


	@Override
	public void returnBackTicket(String ticketKey, String seatNo) {
		// TODO Auto-generated method stub
		Jedis jedis = RedisUtil.getJedis();
		
		if(jedis.exists(ticketKey))
		{
			String seats = RelationConverter.ticketSeatsGenerator(ticketKey);
			jedis.rpush(seats, seatNo);
		}
		
		RedisUtil.close();		
	}


	@Override
	public void deleteTicket(String ticketKey) {
		// TODO Auto-generated method stub
		Jedis jedis = RedisUtil.getJedis();
		
		jedis.lrem(RedisTicket.TICKET_POOL_NAME, 1, ticketKey);
		jedis.del(ticketKey);
		jedis.del(RelationConverter.ticketSeatsGenerator(ticketKey));
		
		RedisUtil.close();		
		
	}


}
