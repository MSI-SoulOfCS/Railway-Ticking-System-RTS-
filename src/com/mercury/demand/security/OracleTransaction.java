package com.mercury.demand.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.mercury.demand.service.HistoryDetailsService;
import com.redis.entity.RedisTransaction;

public class OracleTransaction {
	private static OracleTransaction instance;
	
	@Autowired
	private HistoryDetailsService historyDetailsService;

	public HistoryDetailsService getHistoryDetailsService() {
		return historyDetailsService;
	}

	public void setHistoryDetailsService(HistoryDetailsService historyDetailsService) {
		this.historyDetailsService = historyDetailsService;
	}
	
	private OracleTransaction() {
		
	}
	
	public static OracleTransaction getInstance() {
		if(instance == null) {
			synchronized(OracleTransaction.class) {
				if(instance == null) {
					instance = new OracleTransaction();
				}
			}
		}
		return instance;
	}
	
	public void addTransactionToOracle(List<RedisTransaction> trans) {
		historyDetailsService.addAHistory(trans);
	}
}
