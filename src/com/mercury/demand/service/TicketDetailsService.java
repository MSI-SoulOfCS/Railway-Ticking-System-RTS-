package com.mercury.demand.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.mercury.common.info.TicketsInfo;
import com.mercury.demand.persistence.dao.TicketDao;

public class TicketDetailsService {
	@Autowired
	private TicketDao hd;
	
	private static TicketDetailsService instance;
	
	private TicketDetailsService() {}
	
	public static TicketDetailsService getInstance() {
		if(instance == null) {
			synchronized(TicketDetailsService.class) {
				if(instance == null) instance = new TicketDetailsService();
			}
		}
		return instance;
	}
	
	public TicketDao getHd() {
		return hd;
	}

	public void setHd(TicketDao hd) {
		this.hd = hd;
	}
	
	public TicketsInfo getAllTickets(){
		TicketsInfo rs = new TicketsInfo();
		rs.setTickets(hd.getAllTickets());
		return rs;
	}
}
