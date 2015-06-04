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
	private TicketService service;
	public TransactionService() {
		System.out.println("New Transaction Service");
		service = new TicketServiceImpl();
	}
	public void complete()
	{		
		List<RedisTransaction> trans = this.service.getAllTransactionAndDelete();
		if(trans != null) {
			OracleTransaction.getInstance().addTransactionToOracle(trans);
			System.out.println("Insert transaction record to Oracle");
		}

			

//		OracleUtil.save(trans);
	}

}
