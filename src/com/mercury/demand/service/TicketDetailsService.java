package com.mercury.demand.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercury.demand.persistence.dao.TicketDao;
import com.mercury.demand.persistence.model.Ticket;

@Service
public class TicketDetailsService {
	@Autowired
	private TicketDao hd;
		
	public TicketDao getHd() {
		return hd;
	}

	public void setHd(TicketDao hd) {
		this.hd = hd;
	}
	
	public List<Ticket> getAllTickets(){
		return hd.getAllTickets();
	}
	
	public List<Ticket> getPeroidTimeOfTikcets(String from, String to, Date fromDate, Date toDate) {
		return hd.getTicketsInPeroidOfTime(from, to, fromDate, toDate);
	}
}
