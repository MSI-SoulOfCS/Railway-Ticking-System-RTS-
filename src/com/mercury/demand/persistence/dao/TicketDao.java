package com.mercury.demand.persistence.dao;

import java.util.List;

import com.mercury.demand.persistence.model.Ticket;

public interface TicketDao {
	public List<Ticket> getAllTickets();
}
