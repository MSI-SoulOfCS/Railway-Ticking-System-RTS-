package com.mercury.demand.persistence.dao;

import java.util.List;

import com.mercury.demand.persistence.model.Transaction;
import com.redis.entity.RedisTransaction;

public interface HistoryDao {
	public List<Transaction> getAllHistory();
	public List<Transaction> getUserTransaction(String username);
	public void addAHistory(List<RedisTransaction> trans);
}
