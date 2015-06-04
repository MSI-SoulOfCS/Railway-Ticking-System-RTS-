package com.mercury.demand.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercury.demand.persistence.dao.HistoryDao;
import com.mercury.demand.persistence.model.Transaction;
import com.redis.entity.RedisTransaction;

@Service
public class HistoryDetailsService {
	@Autowired
	private HistoryDao hd;

		
	public HistoryDao getHd() {
		return hd;
	}

	public void setHd(HistoryDao hd) {
		this.hd = hd;
	}
	
	public List<Transaction> getAllHistory(){
		return hd.getAllHistory();
	}
	
	public List<Transaction> getTransactionByUsername(String username) {
		return hd.getUserTransaction(username);
	}
	public void addAHistory(List<RedisTransaction> trans) {		
		hd.addAHistory(trans);
	}
	public List<Transaction> cancelTicket(String ticket_id, String seat_no, String username) {
		return hd.cancelTransaction(ticket_id, seat_no, username);
	}
}
