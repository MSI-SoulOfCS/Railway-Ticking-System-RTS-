package com.mercury.demand.persistence.dao;

import java.util.List;

import com.mercury.demand.persistence.model.History;

public interface HistoryDao {
	public List<History> getAllHistory();
}
