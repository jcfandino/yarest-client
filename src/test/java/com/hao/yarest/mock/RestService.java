package com.hao.yarest.mock;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/service")
public interface RestService {
	@GET
	@Path("/{keyword}")
	@Produces("application/json")
	public Result searchByKeyword(@PathParam("keyword") String keyword);
	
	@GET
	@Path("/all")
	@Produces("application/json")
	public List<Result> getMany();
	
	@GET
	@Path("/")
	@Produces("application/json")
	public Result searchNothing();
	
	@PUT
	@Path("/{id}")
	@Consumes("application/json")
	public void save(@PathParam("id") String id, Result result);
	
	@POST
	@Path("/")
	@Consumes("application/json")
	public void save(Result result);
	
	@DELETE
	@Path("/{id}")
	@Consumes("application/json")
	public void delete(@PathParam("id") String id);

}
