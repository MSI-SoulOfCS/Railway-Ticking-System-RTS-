package com.mercury.demand.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mercury.demand.security.OracleTransaction;
import com.redis.entity.Transaction;
import com.redis.service.TicketService;
import com.redis.service.impl.TicketServiceImpl;
import com.redis.util.OracleUtil;


@Service
public class TransactionService 
{ 
	
	public static void completeTransaction()
	{
		TicketService service = 
				new TicketServiceImpl();
		
		List<Transaction> trans = service.getAllTransactionAndDelete();
		System.out.println("test");
		for(Transaction instance : trans) {
			System.out.println(instance);
			OracleTransaction.getInstance().addTransactionToOracle(instance.getUserId(), instance.getTicketId(), instance.getSeatNo(), new Date());
		}
//		OracleUtil.save(trans);
	}

}
