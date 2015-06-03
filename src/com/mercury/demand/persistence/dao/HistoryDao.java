package com.mercury.demand.persistence.dao;

import java.util.Date;
import java.util.List;

import com.mercury.demand.persistence.model.Transaction;

public interface HistoryDao {
	public List<Transaction> getAllHistory();
	public List<Transaction> getUserTransaction(String username);
	public void addAHistory(String user_id, String ticket_id, String seat_no, Date date);
}
