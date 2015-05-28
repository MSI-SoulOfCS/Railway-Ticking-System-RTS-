package com.mercury.demand.resource;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mercury.common.info.TicketsInfo;
import com.mercury.demand.persistence.model.Ticket;
import com.mercury.demand.service.TicketDetailsService;

@Path("/Tickets")
public class TicketsResource {
	
	@POST
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<Ticket> execute(
			@FormParam("name") String name,
			@FormParam("age") int age) {
		return null;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})	
	public TicketsInfo execute() {
		List<Ticket> list = TicketDetailsService.getInstance().getAllTickets().getTickets();
		for(Ticket ticket : list) {
			System.out.println(ticket.getFrom_loc().getStation());
		}
		return TicketDetailsService.getInstance().getAllTickets();
	}
}
