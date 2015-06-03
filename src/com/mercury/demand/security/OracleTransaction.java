package com.mercury.demand.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.mercury.demand.service.HistoryDetailsService;

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
	
	public void addTransactionToOracle(String user_id, String ticket_id, String seat_no, Date date) {
		historyDetailsService.addAHistory(user_id, ticket_id, seat_no, date);
	}
}
