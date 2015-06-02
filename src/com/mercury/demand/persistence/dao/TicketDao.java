package com.mercury.demand.persistence.dao;

import java.util.Date;
import java.util.List;

import com.mercury.demand.persistence.model.Ticket;

public interface TicketDao {
	public List<Ticket> getAllTickets();
	public List<Ticket> getTicketsWithCondition(String fromLocation, String toLocation, Date date);
	public List<Ticket> getTicketsInPeroidOfTime(String fromLocation, String toLocation, Date beginDate, Date endDate);
}
