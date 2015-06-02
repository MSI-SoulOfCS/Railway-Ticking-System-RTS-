package com.mercury.demand.service;

import java.util.List;


import com.redis.entity.Transaction;
import com.redis.service.TicketService;
import com.redis.service.impl.TicketServiceImpl;
import com.redis.util.OracleUtil;

public class TransactionService 
{
	public static void completeTransaction()
	{
		TicketService service = 
				new TicketServiceImpl();
		
		List<Transaction> trans = service.getAllTransactionAndDelete();
		
		OracleUtil.save(trans);
	}

}
