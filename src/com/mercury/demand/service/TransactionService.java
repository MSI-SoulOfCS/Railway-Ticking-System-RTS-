package com.mercury.demand.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mercury.demand.security.OracleTransaction;
import com.redis.entity.RedisTransaction;
import com.redis.service.TicketService;
import com.redis.service.impl.TicketServiceImpl;
//import com.redis.util.OracleUtil;


@Service
public class TransactionService 
{ 
	
	public static void complete()
	{
		TicketService service = new TicketServiceImpl();
		
		List<RedisTransaction> trans = service.getAllTransactionAndDelete();
		if(trans != null)
			OracleTransaction.getInstance().addTransactionToOracle(trans);
		else
			System.out.println("no data");
			

//		OracleUtil.save(trans);
	}

}
