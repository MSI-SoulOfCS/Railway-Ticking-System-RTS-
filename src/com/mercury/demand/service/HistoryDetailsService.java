package com.mercury.demand.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercury.demand.persistence.dao.HistoryDao;
import com.mercury.demand.persistence.model.Transaction;

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
	public void addAHistory(String user_id, String ticket_id, String seat_no, Date date) {
		System.out.println(user_id + " " + ticket_id);
		hd.addAHistory(user_id, ticket_id, seat_no, date);
	}

}
