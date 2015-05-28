package com.mercury.common.info;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.mercury.demand.persistence.model.Ticket;

@XmlRootElement
public class TicketsInfo {
	private List<Ticket> tickets;	
	
	@XmlElement(name="tickets")
	public List<Ticket> getTickets() {
			return tickets;
	}
	public void setTickets(List<Ticket> tickets) {
			this.tickets = tickets;
	}
}
