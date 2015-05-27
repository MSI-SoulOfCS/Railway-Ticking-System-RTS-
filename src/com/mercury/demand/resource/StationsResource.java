package com.mercury.demand.resource;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mercury.common.info.StationsInfo;
import com.mercury.demand.persistence.model.Station;
import com.mercury.demand.service.StationDetailsService;

@Path("/Stations")
public class StationsResource {
	
	@POST
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<Station> execute(
			@FormParam("name") String name,
			@FormParam("age") int age) {
		return null;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})	
	public StationsInfo execute() {
		return StationDetailsService.getInstance().getAllStations();
	}
}
