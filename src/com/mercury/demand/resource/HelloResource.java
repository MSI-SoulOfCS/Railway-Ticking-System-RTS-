package com.mercury.demand.resource;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mercury.common.info.ResultInfo;
import com.mercury.demand.persistence.model.User1;
import com.mercury.result.Result;

@Path("/hello")
public class HelloResource {
	
	@POST
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<User1> execute(
			@FormParam("name") String name,
			@FormParam("age") int age) {
		return null;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})	
	public ResultInfo execute() {
		return Result.getInstance().getResult();
	}
}
