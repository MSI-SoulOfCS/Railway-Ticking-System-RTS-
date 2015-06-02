package com.mercury.demand.persistence.dao;

import java.util.List;

import com.mercury.demand.persistence.model.History;
import com.mercury.demand.persistence.model.Person;
import com.mercury.demand.persistence.model.Ticket;

public interface HistoryDao {
	public List<History> getAllHistory();
	public List<Ticket> getTicketsHistoryByUser(Person person);
}
